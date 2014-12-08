package com.detroitlabs.kyleofori.teachertools.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
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
public class ResultsActivity extends Activity implements FragmentController, View.OnClickListener {

    public static final String EXTRA_SEARCH_KEYWORD = "search keyword";

    private Button btnSeeFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "t7JTeQx4arA61XvUhQa1CjJhA2VrKWv9mMj3p44G", "jzKFoFbkf2FYwMf9sNwIegGFPt5oNBWwbsffNqa3");

        setContentView(R.layout.activity_results);
        btnSeeFavorites = (Button) findViewById(R.id.btn_see_favorites);
        btnSeeFavorites.setOnClickListener(this);
        loadSearchResultsFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_see_favorites:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
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
    public void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void loadSearchResultsFragment() {
        String searchKeyword = getIntent().getStringExtra(EXTRA_SEARCH_KEYWORD);

        if (searchKeyword != null) {

            SearchResultsFragment searchResultsFragment = SearchResultsFragment.newInstance(searchKeyword);

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, searchResultsFragment)
                    .commit();

        } else {
            throw new IllegalStateException("Must supply a search term to ResultsActivity");
        }
    }
}
