package com.detroitlabs.kyleofori.teachertools.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApi;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApiCallback;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.parsers.KhanAcademyJSONParser;
import com.detroitlabs.kyleofori.teachertools.parsers.ParseObjectParser;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyleofori on 11/30/14.
 */
public class HomepageActivity extends Activity implements KhanAcademyApiCallback, View.OnClickListener {

    public static List<LessonModel> khanAcademyLessonModels;

    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();
    private Button btnGoToSearch;
    private TextView txtResourceLoadingStatus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "t7JTeQx4arA61XvUhQa1CjJhA2VrKWv9mMj3p44G", "jzKFoFbkf2FYwMf9sNwIegGFPt5oNBWwbsffNqa3");
        setContentView(R.layout.activity_homepage);
        khanAcademyApi.getKhanAcademyPlaylists(this);
        btnGoToSearch = (Button) findViewById(R.id.btn_go_to_search);
        btnGoToSearch.setEnabled(false);
        btnGoToSearch.setOnClickListener(this);
        txtResourceLoadingStatus = (TextView) findViewById(R.id.resource_loading_status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(JSONArray response) {
        khanAcademyLessonModels = KhanAcademyJSONParser.parseJSONObject(response);
        txtResourceLoadingStatus.setText(R.string.resources_loaded);
        btnGoToSearch.setEnabled(true);
    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.error_loading_lesson_plans, Toast.LENGTH_SHORT).show();
        btnGoToSearch.setEnabled(true);
        btnGoToSearch.setText(getString(R.string.search_on_error));
    }

    public void openSearchResultsListFragment() {
        Intent toResultsActivity = new Intent(this, ResultsActivity.class);
        startActivity(toResultsActivity);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_go_to_search:
                openSearchResultsListFragment();
                break;
        }
    }

    /*private void retrieveParseObjectsFromCloud() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("LessonPlan");
        retrieveParseObjects(query);
    }

    private void retrieveParseObjectsFromDatastore() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("LessonPlan");
        query.fromLocalDatastore();
        retrieveParseObjects(query);
    }

    private void retrieveParseObjects(ParseQuery<ParseObject> query) {
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("SearchResultsFragment", "Parse objects found");
                    Log.d("SearchResultsFragment", objects.get(0).getString("title"));
                    onCompletion(objects);
                } else {
                    Log.d("SearchResultsFragment", "No Parse objects were found");
                }
            }
        });
    }

//    public void onCompletion(List<ParseObject> objects) {
//        if(isAdded()) {
//            parseObjectParser = new ParseObjectParser();
//            ArrayList<LessonModel> testList = new ArrayList<>();
//            Log.i("SearchResultsFragment How big is the objects list? ", String.valueOf(objects.size()));
//            List<LessonModel> parsedLessonModels = parseObjectParser.parseParseObject(objects);
//            testList.addAll(parsedLessonModels);
//            Log.d("Test", testList.get(0).getDescription());
//            lessonModels.addAll(testList);
//            notifyUser();
//        }
//    }


    private void unpinParseObjectsFromDatastore() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("LessonPlan");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    ParseObject.unpinAllInBackground(objects);
                } else {
                    Log.d("SearchResultsFragment", "No Parse objects to unpin from local storage.");
                }
            }
        });*/
    }



