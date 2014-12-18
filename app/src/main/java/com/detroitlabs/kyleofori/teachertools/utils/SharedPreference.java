package com.detroitlabs.kyleofori.teachertools.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.detroitlabs.kyleofori.teachertools.models.LessonModel;

import com.google.gson.Gson;

/**
 * Created by kyleofori on 12/10/14.
 */

public class SharedPreference {

    public static final String PREFS_NAME = "TEACHER_TOOLS_APP";
    public static final String FAVORITES = "Lesson Plan Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.

    //Converts List<LessonModel> favorites to JSON string
    public void saveFavorites(Context context, List<LessonModel> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, LessonModel lessonModel) {
        List<LessonModel> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<LessonModel>();
        boolean isGoingIn = true;
        for (LessonModel x: favorites) {
            if (x.getLessonId().equals(lessonModel.getLessonId())) {
                isGoingIn = false;
            }
        }
        if(isGoingIn) {
            favorites.add(lessonModel);
        }
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, LessonModel lessonModel) {
        ArrayList<LessonModel> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(lessonModel);
            saveFavorites(context, favorites);
        }
    }

    //Converts JSON string to List<LessonModel> favorites
    public ArrayList<LessonModel> getFavorites(Context context) {
        SharedPreferences settings;
        List<LessonModel> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            LessonModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    LessonModel[].class);

            if(favoriteItems != null) {
                favorites = Arrays.asList(favoriteItems);
                favorites = new ArrayList<LessonModel>(favorites);
                return (ArrayList<LessonModel>) favorites;

            }
        }
            return null;

    }

    public void clearSharedPreferencesWhichClearsAdapterIHope(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor;
        editor = settings.edit();

        if(settings.contains(FAVORITES)) {
            editor.putString(FAVORITES, null);
            editor.commit();
        }
    }
}