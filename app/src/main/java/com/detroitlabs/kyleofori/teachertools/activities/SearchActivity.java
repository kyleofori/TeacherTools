package com.detroitlabs.kyleofori.teachertools.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.detroitlabs.kyleofori.teachertools.R;

/**
 * Created by kyleofori on 11/30/14.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private EditText edtSearchBar;
    private Button btnSearch;

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
    }
}
