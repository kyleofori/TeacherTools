package com.detroitlabs.kyleofori.teachertools.fragments;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.adapters.FavoritesAdapter;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.utils.SharedPreference;

/**
 * Created by kyleofori on 12/10/14.
 */
public class FavoritesFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

        public static final String ARG_ITEM_ID = "favorite_list";

        ListView favoriteList;
        SharedPreference sharedPreference;
        List<LessonModel> favorites;

        Activity activity;
        FavoritesAdapter favoritesAdapter;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            activity = getActivity();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_favorites, container,
                    false);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.chk_favorite);
            checkBox.setOnCheckedChangeListener(this);
            // Get favorite items from SharedPreferences.
            sharedPreference = new SharedPreference();
            favorites = sharedPreference.getFavorites(activity);

            if (favorites == null) {
                showAlert(getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            } else {

                if (favorites.size() == 0) {
                    showAlert(
                            getResources().getString(R.string.no_favorites_items),
                            getResources().getString(R.string.no_favorites_msg));
                }

                favoriteList = (ListView) view.findViewById(R.id.itm_favorites);
                if (favorites != null) {
                    favoritesAdapter = new FavoritesAdapter(activity, favorites);
                    favoriteList.setAdapter(favoritesAdapter);

                    favoriteList.setOnItemClickListener(new OnItemClickListener() {

                        public void onItemClick(AdapterView<?> parent, View arg1,
                                                int position, long arg3) {

                        }
                    });

                    favoriteList
                            .setOnItemLongClickListener(new OnItemLongClickListener() {

                                @Override
                                public boolean onItemLongClick(
                                        AdapterView<?> parent, View view,
                                        int position, long id) {

                                    ImageView button = (ImageView) view
                                            .findViewById(R.id.imgbtn_favorite);

                                    String tag = button.getTag().toString();
                                    if (tag.equalsIgnoreCase("grey")) {
                                        sharedPreference.addFavorite(activity,
                                                favorites.get(position));
                                        Toast.makeText(
                                                activity,
                                                activity.getResources().getString(
                                                        R.string.add_to_favorites),
                                                Toast.LENGTH_SHORT).show();

                                        button.setTag("red");
                                        button.setImageResource(R.drawable.favestar);
                                    } else {
                                        sharedPreference.removeFavorite(activity,
                                                favorites.get(position));
                                        button.setTag("grey");
                                        button.setImageResource(R.drawable.star_none);
                                        favoritesAdapter.remove(favorites
                                                .get(position));
                                        Toast.makeText(
                                                activity,
                                                activity.getResources().getString(
                                                        R.string.remove_favorite),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    return true;
                                }
                            });
                }
            }
            return view;
        }

        public void showAlert(String title, String message) {
            if (activity != null && !activity.isFinishing()) {
                AlertDialog alertDialog = new AlertDialog.Builder(activity)
                        .create();
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.setCancelable(false);

                // setting OK Button
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // activity.finish();
                                getFragmentManager().popBackStackImmediate();
                            }
                        });
                alertDialog.show();
            }
        }

        @Override
        public void onResume() {
            getActivity().setTitle(R.string.favorites);
            getActivity().getActionBar().setTitle(R.string.favorites);
            super.onResume();
        }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        
    }
}


