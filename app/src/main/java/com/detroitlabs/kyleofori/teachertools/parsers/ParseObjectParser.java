package com.detroitlabs.kyleofori.teachertools.parsers;

import android.util.Log;

import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyleofori on 11/26/14.
 */
public class ParseObjectParser {

    public List<LessonModel> parseParseObject(List<ParseObject> parseObjects) {

        try {
            List<LessonModel> lessonModels = new ArrayList<>();

            Log.i("ParseObjectsParser How big is the parseObjects list? ", String.valueOf(parseObjects.size()));
            for (int index = 0; index < parseObjects.size(); index++) {

                //get the Parse object that matches the index
                ParseObject currentParseObject = parseObjects.get(index);
                //save the title as String title
                String title = currentParseObject.getString("title");
                String lessonUrl = currentParseObject.getString("lessonUrl");
                String description = currentParseObject.getString("description");

                LessonModel currentLessonModel = new LessonModel(title, lessonUrl, description);

                lessonModels.add(currentLessonModel);
            }

            return lessonModels;
        } catch (NullPointerException e) {
            Log.e("ParseObjectParser", "There was a problem parsing the ParseObject into a LessonModel.");
            return new ArrayList<>();
        }
    }

}
