package com.detroitlabs.kyleofori.teachertools.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.activities.ResultsActivity;
import com.detroitlabs.kyleofori.teachertools.adapters.FavoritesAdapter;
import com.detroitlabs.kyleofori.teachertools.adapters.SearchResultsAdapter;
import com.detroitlabs.kyleofori.teachertools.interfaces.FragmentController;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApi;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.KhanAcademyApiCallback;
import com.detroitlabs.kyleofori.teachertools.khanacademyapi.ParseDataset;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.parsers.KhanAcademyJSONParser;
import com.detroitlabs.kyleofori.teachertools.parsers.ParseObjectParser;
import com.detroitlabs.kyleofori.teachertools.tags.GlobalTags;
import com.detroitlabs.kyleofori.teachertools.utils.SharedPreference;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsFragment extends Fragment implements KhanAcademyApiCallback,
        AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {


    private static final String EXTRA_SEARCH_KEYWORD = "extra_search_keyword";
    private static final long REFRESH_INTERVAL = TimeUnit.SECONDS.toMillis(30);

    private FragmentController fragmentController;
    private SearchResultsAdapter searchResultsAdapter;
    private KhanAcademyApi khanAcademyApi = KhanAcademyApi.getKhanAcademyApi();
    private Timer refreshTimer;
    private ParseDataset parseDataset = new ParseDataset();
    private List<LessonModel> lessonModels = new ArrayList<>();
    private List<ParseObject> parseObjects = new ArrayList<>();
    private ParseObjectParser parseObjectParser;
    private EditText edtInputSearch;
    private String searchKeyword;
    private int preResourceCount, postResourceCount;
    private ImageView imgStar;

    private ProgressDialog loadingDialog;

    public static final String ARG_ITEM_ID = "favorite_list";

    private CheckBox chkFavorites;
    private ListView favoriteList;
    private SharedPreference sharedPreference;
    private List<LessonModel> favorites;

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
        sharedPreference = new SharedPreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        imgStar = (ImageView) view.findViewById(R.id.img_star);
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.show();
        /*chkFavorites = (CheckBox) view.findViewById(R.id.chk_favorite);
        chkFavorites.setOnCheckedChangeListener(this);
        Button btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);*/
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
/*
                favoritesAdapter = new FavoritesAdapter(activity, favorites);
                favoriteList.setAdapter(favoritesAdapter);

                favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View arg1,
                                            int position, long arg3) {

                    }
                });

                favoriteList
                        .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                ImageView imgStar = (ImageView) view.findViewById(R.id.imgbtn_favorite);

                                String tag = imgStar.getTag().toString();
                                if (tag.equalsIgnoreCase("grey")) {
                                    sharedPreference.addFavorite(activity,
                                            favorites.get(position));
                                    Toast.makeText(
                                            activity,
                                            activity.getResources().getString(
                                                    R.string.add_to_favorites),
                                            Toast.LENGTH_SHORT).show();

                                    imgStar.setTag("red");
                                    imgStar.setImageResource(R.drawable.favestar);
                                } else {
                                    sharedPreference.removeFavorite(activity,
                                            favorites.get(position));
                                    imgStar.setTag("grey");
                                    imgStar.setImageResource(R.drawable.star_none);
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
*/
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchResultsAdapter = new SearchResultsAdapter(getActivity());
        ListView listView = (ListView) view.findViewById(R.id.list_search_results);
        listView.setAdapter(searchResultsAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        khanAcademyApi.getKhanAcademyPlaylists(this);
        retrieveParseObjectsFromCloud();
        //retrieveParseObjectsFromDatastore or unpinParseObjectFromDatastore were here.

        edtInputSearch = (EditText) view.findViewById(R.id.edt_input_search);
        edtInputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                searchResultsAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });


//        parseDataset.prepopulateParseDataset();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.list_search_results:
                    LessonModel lessonModel = (LessonModel) adapterView.getAdapter().getItem(i);
                    DetailFragment detailFragment = DetailFragment.newInstance(lessonModel);
                    fragmentController.changeFragment(detailFragment, true);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                   int position, long arg3) {
        imgStar = (ImageView) view.findViewById(R.id.img_star);

        if (lessonModels.get(position).isFavorited()) {
            Log.i(getClass().getSimpleName(), "isFavorited() was " + lessonModels.get(position).isFavorited());
            sharedPreference.removeFavorite(activity, lessonModels.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.removed_from_favorites),
                    Toast.LENGTH_SHORT).show();
            imgStar.setTag(GlobalTags.TAG_OFF);
            imgStar.setImageResource(R.drawable.star_none);
            lessonModels.get(position).setFavorited(false);
            Log.i(getClass().getSimpleName(), "isFavorited() is now " + lessonModels.get(position).isFavorited());
        } else {
            Log.i(getClass().getSimpleName(), "isFavorited() was " + lessonModels.get(position).isFavorited());
            sharedPreference.addFavorite(activity, lessonModels.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.added_to_favorites),
                    Toast.LENGTH_SHORT).show();

            imgStar.setTag(GlobalTags.TAG_ON);
            imgStar.setImageResource(R.drawable.favestar);
            lessonModels.get(position).setFavorited(true);
            Log.i(getClass().getSimpleName(), "isFavorited() is now " + lessonModels.get(position).isFavorited());

        }

        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.favorites);
        getActivity().getActionBar().setTitle(R.string.favorites);
//        startRefreshTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
//        stopRefreshTimer();
    }

    @Override
    public void onSuccess(JSONArray response) {
        if (isAdded()) {
            List<LessonModel> khanAcademyLessonModels = KhanAcademyJSONParser.parseJSONObject(response);
            lessonModels.addAll(khanAcademyLessonModels);
            loadingDialog.dismiss();

            searchResultsAdapter.clear();
            searchResultsAdapter.setLessonsInAdapter(lessonModels);
            searchResultsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {
        if (isAdded()) {
            Toast.makeText(getActivity(), "Error loading search results list", Toast.LENGTH_SHORT).show();
        }
    }

    private void startRefreshTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                autoRefreshList();
            }
        };

        refreshTimer = new Timer();
        refreshTimer.schedule(timerTask, REFRESH_INTERVAL, REFRESH_INTERVAL);
    }

    private void stopRefreshTimer() {
        refreshTimer.cancel();
        refreshTimer = null;
    }

    private void autoRefreshList() {
        if (isAdded()) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Auto refreshing list", Toast.LENGTH_SHORT).show();
                }
            });

            loadKhanAcademyPlaylists();
        }
    }

    private void loadKhanAcademyPlaylists() {
        String searchTerm = getArguments().getString(EXTRA_SEARCH_KEYWORD);

        if (searchTerm != null) {
            khanAcademyApi.getKhanAcademyPlaylists(this);
        } else {
            throw new IllegalStateException("Must supply a search term to SearchResultsListFragment");
        }
    }

    private void retrieveParseObjectsFromCloud() {
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

    public void onCompletion(List<ParseObject> objects) {
        if(isAdded()) {
            parseObjectParser = new ParseObjectParser();
            ArrayList<LessonModel> testList = new ArrayList<>();
            Log.i("SearchResultsFragment How big is the objects list? ", String.valueOf(objects.size()));
            List<LessonModel> parsedLessonModels = parseObjectParser.parseParseObject(objects);
            testList.addAll(parsedLessonModels);
            Log.d("Test", testList.get(0).getDescription());
            lessonModels.addAll(testList);
            notifyUser();
        }
    }


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
        });
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear_favorites:
                if (chkFavorites.isChecked()) {
                    //delete the thing from favorites
                    Log.i(this.getClass().getSimpleName(), "A checkbox is checked");
                }
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notifyUser() {
        Notification.Builder builder =
                new Notification.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("New lesson plans added")
                        .setContentText("x more n-subject lesson plans are available in TeacherTools.");
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getActivity(), ResultsActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ResultsActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(getId(), builder.build());
    }
}
