package com.detroitlabs.kyleofori.teachertools.parsers;

import com.detroitlabs.kyleofori.teachertools.models.LessonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyleofori on 11/26/14.
 */
public class KhanAcademyJSONParser {

    public static List<LessonModel> parseJSONObject(JSONArray jsonArray) {

        try {
            List<LessonModel> lessonModels = new ArrayList<>();

            for (int index = 0; index < jsonArray.length(); index++) {

                JSONObject playlistObject = jsonArray.getJSONObject(index);
                String title = playlistObject.optString("title", "unknown title");
                String kaUrl = playlistObject.optString("ka_url", "unknown url");
                String description = playlistObject.optString("description", "unknown description");

                LessonModel currentLessonModel = new LessonModel(title, kaUrl, description, false);

                lessonModels.add(currentLessonModel);
            }

            return lessonModels;
        } catch (JSONException e) {
            return new ArrayList<>();
        }
    }

}
