package com.detroitlabs.kyleofori.teachertools.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApi;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApiCallback;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.parsers.KhanAcademyJSONParser;
import com.detroitlabs.kyleofori.teachertools.tags.GlobalTags;
import com.parse.Parse;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by kyleofori on 11/30/14.
 */
public class HomepageActivity extends Activity implements KhanAcademyApiCallback {

    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();
    public static List<LessonModel> khanAcademyLessonModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "t7JTeQx4arA61XvUhQa1CjJhA2VrKWv9mMj3p44G", "jzKFoFbkf2FYwMf9sNwIegGFPt5oNBWwbsffNqa3");
        setContentView(R.layout.activity_homepage);
        khanAcademyApi.getKhanAcademyPlaylists(this);
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
        openSearchResultsListFragment();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "There was an error loading lesson plans from Khan Academy.", Toast.LENGTH_SHORT).show();
        openSearchResultsListFragment();
    }

    public void openSearchResultsListFragment() {
        Intent toResultsActivity = new Intent(this, ResultsActivity.class);
        startActivity(toResultsActivity);
    }
}


