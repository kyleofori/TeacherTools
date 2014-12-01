package com.detroitlabs.kyleofori.teachertools.parsers;

import com.detroitlabs.kyleofori.teachertools.models.KhanAcademyPlaylist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyleofori on 11/26/14.
 */
public class KhanAcademyJSONParser {

    public static List<KhanAcademyPlaylist> parseJSONObject(JSONArray jsonArray) {

        try {
            List<KhanAcademyPlaylist> khanAcademyPlaylists = new ArrayList<KhanAcademyPlaylist>();

            for (int index = 0; index < 10; index++) {

                JSONObject playlistObject = jsonArray.getJSONObject(index);
                String title = playlistObject.optString("title", "unknown title");
                String kaUrl = playlistObject.optString("ka_url", "unknown url");
                String description = playlistObject.optString("description", "unknown description");

                KhanAcademyPlaylist currentKhanAcademyPlaylist = new KhanAcademyPlaylist(title, kaUrl, description);

                khanAcademyPlaylists.add(currentKhanAcademyPlaylist);
            }

            return khanAcademyPlaylists;
        } catch (JSONException e) {
            return new ArrayList<>();
        }
    }

}
