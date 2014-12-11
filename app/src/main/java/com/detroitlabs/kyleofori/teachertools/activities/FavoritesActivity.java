package com.detroitlabs.kyleofori.teachertools.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.fragments.FavoritesFragment;
import com.detroitlabs.kyleofori.teachertools.interfaces.FragmentController;

/**
 * Created by kyleofori on 12/7/14.
 */
public class FavoritesActivity extends Activity implements FragmentController {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new FavoritesFragment())
                .commit();
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
}


/*
package com.androidopentutorials.spfavorites;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.view.Menu;
        import android.view.MenuItem;

        import com.androidopentutorials.spfavorites.fragment.FavoriteListFragment;
        import com.androidopentutorials.spfavorites.fragment.ProductListFragment;

public class MainActivity extends FragmentActivity {

    private Fragment contentFragment;
    ProductListFragment pdtListFragment;
    FavoriteListFragment  favListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

		*/
/*
		 * This is called when orientation is changed.
		 *//*

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(FavoriteListFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.favorites);
                        contentFragment = fragmentManager
                                .findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(ProductListFragment.ARG_ITEM_ID) != null) {
                pdtListFragment = (ProductListFragment) fragmentManager
                        .findFragmentByTag(ProductListFragment.ARG_ITEM_ID);
                contentFragment = pdtListFragment;
            }
        } else {
            pdtListFragment = new ProductListFragment();
            setFragmentTitle(R.string.app_name);
            switchContent(pdtListFragment, ProductListFragment.ARG_ITEM_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof FavoriteListFragment) {
            outState.putString("content", FavoriteListFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", ProductListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorites:
                setFragmentTitle(R.string.favorites);
                favListFragment = new FavoriteListFragment();
                switchContent(favListFragment, FavoriteListFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate());

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            //Only FavoriteListFragment is added to the back stack.
            if (!(fragment instanceof ProductListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getActionBar().setTitle(resourseId);

    }

    */
/*
     * We call super.onBackPressed(); when the stack entry count is > 0. if it
     * is instanceof ProductListFragment or if the stack entry count is == 0, then
     * we finish the activity.
     * In other words, from ProductListFragment on back press it quits the app.
     *//*

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof ProductListFragment
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }
}*/
