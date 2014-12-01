package com.detroitlabs.kyleofori.teachertools.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.adapters.SearchResultsListAdapter;
import com.detroitlabs.kyleofori.teachertools.interfaces.FragmentController;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApi;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApiCallback;
import com.detroitlabs.kyleofori.teachertools.models.KhanAcademyPlaylist;
import com.detroitlabs.kyleofori.teachertools.parsers.KhanAcademyJSONParser;

import org.json.JSONArray;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsListFragment extends ListFragment implements KhanAcademyApiCallback, View.OnClickListener {

    private static final String ARG_SEARCH_TERM = "arg_search_term";
    private static final long REFRESH_INTERVAL = TimeUnit.SECONDS.toMillis(6);

    public static SearchResultsListFragment newInstance(String searchTerm) {

        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_TERM, searchTerm);

        SearchResultsListFragment searchResultsListFragment = new SearchResultsListFragment();
        searchResultsListFragment.setArguments(args);

        return searchResultsListFragment;
    }

    private SearchResultsListAdapter searchResultsListAdapter;
    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();
    private Timer refreshTimer;
    private Button btnPrevious, btnNext;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnPrevious = (Button) view.findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(this);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        searchResultsListAdapter = new SearchResultsListAdapter(getActivity());
        setListAdapter(searchResultsListAdapter);
        loadRedditEntries();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                break;
            case R.id.btn_next:
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
    public void onListItemClick(ListView listView, View row, int position, long id) {

        if (getActivity() instanceof FragmentController) {

            KhanAcademyPlaylist khanAcademyPlaylist = (KhanAcademyPlaylist) listView.getAdapter().getItem(position);
            PlaylistDetailFragment playlistDetailFragment = PlaylistDetailFragment.newInstance(khanAcademyPlaylist);

            FragmentController fragmentController = (FragmentController) getActivity();
            fragmentController.changeFragment(playlistDetailFragment, true);

        } else {
            throw new IllegalArgumentException("Your activity must implement the FragmentController interface");
        }

    }

    @Override
    public void onSuccess(JSONArray response) {
        if (isAdded()) {
            List<KhanAcademyPlaylist> redditEntries = KhanAcademyJSONParser.parseJSONObject(response);
            searchResultsListAdapter.clear();
            searchResultsListAdapter.addAll(redditEntries);
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
