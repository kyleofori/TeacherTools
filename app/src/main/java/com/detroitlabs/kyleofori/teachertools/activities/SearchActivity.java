package com.detroitlabs.kyleofori.teachertools.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;

/**
 * Created by kyleofori on 11/30/14.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private EditText edtSearchBar;
    private Button btnSearch;
    private static final String EXTRA_SEARCH_KEYWORD = "search keyword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edtSearchBar = (EditText) findViewById(R.id.edt_search_bar);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                trySearchTermSubmit();
                break;
        }
    }

    public void trySearchTermSubmit() {
        String searchKeyword = edtSearchBar.getText().toString().trim();
        if(searchKeyword.isEmpty()) {
            Toast.makeText(this, R.string.toast_search_error, Toast.LENGTH_SHORT).show();
        } else {
            openSearchResultsListFragment(searchKeyword);
        }
    }

    public void openSearchResultsListFragment(String searchKeyword) {
        Intent toResultsActivity = new Intent(this, ResultsActivity.class);
        toResultsActivity.putExtra(EXTRA_SEARCH_KEYWORD, searchKeyword);
        startActivity(toResultsActivity);
    }
}
