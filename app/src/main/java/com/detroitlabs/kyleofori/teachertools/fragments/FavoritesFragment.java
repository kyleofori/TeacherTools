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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.adapters.FavoritesAdapter;
import com.detroitlabs.kyleofori.teachertools.interfaces.FragmentController;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.tags.GlobalTags;
import com.detroitlabs.kyleofori.teachertools.utils.SharedPreference;

/**
 * Created by kyleofori on 12/10/14.
 */
public class FavoritesFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    public static final String ARG_ITEM_ID = "favorite_list";

    private FragmentController fragmentController;
    private ListView favoriteList;
    private SharedPreference sharedPreference;
    private List<LessonModel> favorites;
    private Button btnClearFavorites;


    Activity activity;
    FavoritesAdapter favoritesAdapter;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container,
                false);

        btnClearFavorites = (Button) view.findViewById(R.id.btn_clear_favorites);
        btnClearFavorites.setOnClickListener(this);

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

            favoriteList = (ListView) view.findViewById(R.id.list_favorites);
            if (favorites != null) {
                favoritesAdapter = new FavoritesAdapter(activity, favorites);
                favoriteList.setAdapter(favoritesAdapter);

                favoriteList.setOnItemClickListener(new OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View arg1,
                                            int position, long arg3) {
                        LessonModel lessonModel = (LessonModel) parent.getAdapter().getItem(position);
                        DetailFragment detailFragment = DetailFragment.newInstance(lessonModel);
                        fragmentController.changeFragment(detailFragment, true);

                    }
                });

                favoriteList
                        .setOnItemLongClickListener(new OnItemLongClickListener() {

                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                ImageView button = (ImageView) view.findViewById(R.id.img_star_fav);

                                String tag = button.getTag().toString();
                                if (tag.equalsIgnoreCase(GlobalTags.TAG_OFF)) {
                                    sharedPreference.addFavorite(activity,
                                            favorites.get(position));
                                    Toast.makeText(
                                            activity,
                                            activity.getResources().getString(
                                                    R.string.added_to_favorites),
                                            Toast.LENGTH_SHORT).show();

                                    button.setTag(GlobalTags.TAG_ON);
                                    button.setImageResource(R.drawable.favestar);
                                } else {
                                    sharedPreference.removeFavorite(activity,
                                            favorites.get(position));
                                    button.setTag(GlobalTags.TAG_OFF);
                                    button.setImageResource(R.drawable.star_none);
                                    favoritesAdapter.remove(favorites
                                            .get(position));
                                    Toast.makeText(
                                            activity,
                                            activity.getResources().getString(
                                                    R.string.removed_from_favorites),
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_favorites:
                favoritesAdapter.clear();
                sharedPreference.clearSharedPreferencesWhichClearsAdapterIHope(activity);
                favoritesAdapter.notifyDataSetChanged();
                break;
        }



    }
}


