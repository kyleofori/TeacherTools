package com.detroitlabs.kyleofori.teachertools.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.adapters.SearchResultsAdapter;
import com.detroitlabs.kyleofori.teachertools.interfaces.FragmentController;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApi;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApiCallback;
import com.detroitlabs.kyleofori.teachertools.models.KhanAcademyPlaylist;
import com.detroitlabs.kyleofori.teachertools.parsers.KhanAcademyJSONParser;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsFragment extends Fragment implements KhanAcademyApiCallback, View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String ARG_SEARCH_TERM = "arg_search_term";
    private static final long REFRESH_INTERVAL = TimeUnit.SECONDS.toMillis(30);
    private static final int NUM_ENTRIES = 10;
    private FragmentController fragmentController;
    public static Integer startEntry = 0;
    private List<KhanAcademyPlaylist> redditEntries;

    public static SearchResultsFragment newInstance(String searchTerm) {

        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_TERM, searchTerm);

        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        searchResultsFragment.setArguments(args);

        return searchResultsFragment;
    }

    private SearchResultsAdapter searchResultsAdapter;
    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();
    private Timer refreshTimer;
    private Button btnPrevious;
    private Button btnNext;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof FragmentController) {
            fragmentController = (FragmentController) activity;
        } else {
            throw new IllegalArgumentException(getString(R.string.fragment_controller_interface_error));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchResultsAdapter = new SearchResultsAdapter(getActivity());
        ListView listView = (ListView) view.findViewById(R.id.itm_search_results);
        listView.setAdapter(searchResultsAdapter);
        listView.setOnItemClickListener(this);
        btnPrevious = (Button) view.findViewById(R.id.btn_previous);
        if(startEntry == 0) {
            btnPrevious.setEnabled(false);
        }
        btnPrevious.setOnClickListener(this);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        loadRedditEntries();


        ParseObject lessonPlan = new ParseObject("LessonPlan");
        lessonPlan.put("author", "Danzig Leonidas");
        lessonPlan.put("title", "The History of My People");
        lessonPlan.put("description", "This resource concerns the history of the city of Free " +
                "Danzig or the time of the movie 300, I forget which");
        lessonPlan.put("subject", "Social Studies");
        lessonPlan.put("url", "http://www.leonidas.com");
        lessonPlan.put("hostingSite","LessonPlahnet");
        lessonPlan.put("gradeLevels", "9th-12th");
        lessonPlan.saveInBackground();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("LessonPlan");
        try {
            ParseObject objectA = query.getFirst();
            btnPrevious.setText(objectA.getString("hostingSite"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.itm_search_results:
                    KhanAcademyPlaylist khanAcademyPlaylist = (KhanAcademyPlaylist) adapterView.getAdapter().getItem(i);
                    PlaylistDetailFragment playlistDetailFragment = PlaylistDetailFragment.newInstance(khanAcademyPlaylist);
                    fragmentController.changeFragment(playlistDetailFragment, true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                if(startEntry >= 10) {
                    startEntry-=10;
                    updateEntriesShown(redditEntries);
                    if(startEntry == 0) {
                        btnPrevious.setEnabled(false);
                    }
                }
                break;
            case R.id.btn_next:
                startEntry+=10;
                updateEntriesShown(redditEntries);
                if(!btnPrevious.isEnabled()) {
                    btnPrevious.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startRefreshTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRefreshTimer();
    }

    @Override
    public void onSuccess(JSONArray response) {
        if (isAdded()) {
            redditEntries = KhanAcademyJSONParser.parseJSONObject(response);
            updateEntriesShown(redditEntries);
        }
    }

    public void updateEntriesShown(List<KhanAcademyPlaylist> redditEntries) {
        searchResultsAdapter.clear();
        int indexLimit;
        if (startEntry + NUM_ENTRIES > redditEntries.size()) {
            indexLimit = redditEntries.size();
        } else {
            indexLimit = startEntry + NUM_ENTRIES;
        }
        for (int index = startEntry; index < indexLimit; index++) {
            searchResultsAdapter.add(redditEntries.get(index));
        }
    }

    @Override
    public void onError() {
        if (isAdded()) {
            Toast.makeText(getActivity(), "Error loading search results list", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRefreshTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                autoRefreshList();
            }
        };

        refreshTimer = new Timer();
        refreshTimer.schedule(timerTask, REFRESH_INTERVAL, REFRESH_INTERVAL);
    }

    private void stopRefreshTimer() {
        refreshTimer.cancel();
        refreshTimer = null;
    }

    private void autoRefreshList() {
        if (isAdded()) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Auto refreshing list", Toast.LENGTH_SHORT).show();
                }
            });

            loadRedditEntries();
        }
    }

    private void loadRedditEntries() {
        String searchTerm = getArguments().getString(ARG_SEARCH_TERM);

        if (searchTerm != null) {
            khanAcademyApi.getSubredditEntries(searchTerm, this);
        } else {
            throw new IllegalStateException("Must supply a search term to SearchResultsListFragment");
        }
    }


}
