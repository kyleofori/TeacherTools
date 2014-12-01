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

/**
 * Created by kyleofori on 11/30/14.
 */
public class ResultsActivity extends Activity implements FragmentController/*, View.OnClickListener*/ {

    public static final String EXTRA_SEARCH_KEYWORD = "search keyword";
    private Button btnPrevious, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
//        btnPrevious = (Button) findViewById(R.id.btn_previous);
//        btnPrevious.setOnClickListener(this);
//        btnNext = (Button) findViewById(R.id.btn_next);
//        btnNext.setOnClickListener(this);
        loadSubredditFragment();
    }

//    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                break;
            case R.id.btn_next:
                break;
        }
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
    public void changeFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackstack) {
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
