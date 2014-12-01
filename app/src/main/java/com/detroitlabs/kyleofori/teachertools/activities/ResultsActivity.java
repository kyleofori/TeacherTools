package com.detroitlabs.kyleofori.teachertools.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.fragments.SearchResultsFragment;
import com.detroitlabs.kyleofori.teachertools.interfaces.FragmentController;
import com.parse.Parse;

/**
 * Created by kyleofori on 11/30/14.
 */
public class ResultsActivity extends Activity implements FragmentController {

    public static final String EXTRA_SEARCH_KEYWORD = "search keyword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "t7JTeQx4arA61XvUhQa1CjJhA2VrKWv9mMj3p44G", "jzKFoFbkf2FYwMf9sNwIegGFPt5oNBWwbsffNqa3");

        setContentView(R.layout.activity_results);
        loadSubredditFragment();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void loadSubredditFragment() {
        String subreddit = getIntent().getStringExtra(EXTRA_SEARCH_KEYWORD);

        if (subreddit != null) {

            SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(subreddit);

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, searchResultsFragment)
                    .commit();

        } else {
            throw new IllegalStateException("Must supply a subreddit to RedditNavigationActivity");
        }
    }


}
