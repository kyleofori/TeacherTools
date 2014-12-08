package com.detroitlabs.kyleofori.teachertools.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.adapters.SearchResultsAdapter;
import com.detroitlabs.kyleofori.teachertools.interfaces.FragmentController;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApi;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApiCallback;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.ParseDataset;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.parsers.KhanAcademyJSONParser;
import com.detroitlabs.kyleofori.teachertools.parsers.ParseObjectParser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsFragment extends Fragment implements KhanAcademyApiCallback, AdapterView.OnItemClickListener {

    private static final String EXTRA_SEARCH_KEYWORD = "extra_search_keyword";
    private static final long REFRESH_INTERVAL = TimeUnit.SECONDS.toMillis(30);

    private FragmentController fragmentController;
    private SearchResultsAdapter searchResultsAdapter;
    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();
    private Timer refreshTimer;
    private ParseDataset parseDataset = new ParseDataset();
    private List<LessonModel> lessonModels = new ArrayList<>();
    private List<ParseObject> parseObjects = new ArrayList<>();
    private ParseObjectParser parseObjectParser;
    private EditText edtInputSearch;
    private String searchKeyword;

    public static SearchResultsFragment newInstance(String searchKeyword) {

        Bundle args = new Bundle();
        args.putString(EXTRA_SEARCH_KEYWORD, searchKeyword);

        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        searchResultsFragment.setArguments(args);

        return searchResultsFragment;
    }

    public List<ParseObject> getParseObjects() {
        return parseObjects;
    }

    public void setParseObjects(List<ParseObject> parseObjects) {
        this.parseObjects = parseObjects;
    }

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

        loadKhanAcademyPlaylists();
        retrieveParseObjectsFromDatastore(); //slow method. I won't get results in time to use.


        searchKeyword = getArguments().getString(EXTRA_SEARCH_KEYWORD);
        Log.i("You have the search keyword? ", searchKeyword);

        edtInputSearch = (EditText) view.findViewById(R.id.edt_input_search);
        edtInputSearch.setText(searchKeyword);
        edtInputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                searchResultsAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        LessonModel exampleLessonModel = new LessonModel("TeacherTools", "http://www.example.com", "You know what it is");
        lessonModels.add(exampleLessonModel);
        parseDataset.prepopulateParseDataset();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.itm_search_results:
                    LessonModel lessonModel = (LessonModel) adapterView.getAdapter().getItem(i);
                    DetailFragment detailFragment = DetailFragment.newInstance(lessonModel);
                    fragmentController.changeFragment(detailFragment, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        startRefreshTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
//        stopRefreshTimer();
    }

    @Override
    public void onSuccess(JSONArray response) {
        if (isAdded()) {
            List<LessonModel> khanAcademyLessonModels = KhanAcademyJSONParser.parseJSONObject(response);
            lessonModels.addAll(khanAcademyLessonModels);
            searchResultsAdapter.clear();
            searchResultsAdapter.setLessonsInAdapter(lessonModels);
            searchResultsAdapter.notifyDataSetChanged();
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

            loadKhanAcademyPlaylists();
        }
    }

    private void loadKhanAcademyPlaylists() {
        String searchTerm = getArguments().getString(EXTRA_SEARCH_KEYWORD);

        if (searchTerm != null) {
            khanAcademyApi.getKhanAcademyPlaylists(searchTerm, this);
        } else {
            throw new IllegalStateException("Must supply a search term to SearchResultsListFragment");
        }
    }

    private void retrieveParseObjectsFromDatastore() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("LessonPlan");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    setParseObjects(objects);
                    Log.d("SearchResultsFragment", "List of Parse objects created successfully");
                    Log.d("SearchResultsFragment", objects.get(0).getString("title"));
                    Log.d(this.getClass().getSimpleName(), getParseObjects().get(0).getString("title"));
                    onCompletion(getParseObjects());
                } else {
                    Log.d("SearchResultsFragment","No Parse objects were found");
                }
            }
        });
    }

    public void onCompletion(List<ParseObject> parseObjects) {
        if(isAdded()) {
            parseObjectParser = new ParseObjectParser();
            ArrayList<LessonModel> testList = new ArrayList<>();
            Log.i("SearchResultsFragment How big is the parseObjects list? ", String.valueOf(parseObjects.size()));
            Log.i("SearchResultsFragment How big is the getParseObjects list? ", String.valueOf(getParseObjects().size()));
            List<LessonModel> parsedLessonModels = parseObjectParser.parseParseObject(getParseObjects());
            testList.addAll(parsedLessonModels);
            Log.d("Test", testList.get(0).getDescription());
        }
    }
}
