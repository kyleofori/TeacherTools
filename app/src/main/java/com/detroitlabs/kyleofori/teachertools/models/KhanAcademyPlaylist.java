package com.detroitlabs.kyleofori.teachertools.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class KhanAcademyPlaylist implements Parcelable {

    private String title, kaUrl, description;

    public KhanAcademyPlaylist(String title, String kaUrl, String description){
        this.title = title;
        this.kaUrl = kaUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getKaUrl() {
        return kaUrl;
    }

    public String getDescription() {
        return description;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.kaUrl);
        dest.writeString(this.description);
    }

    private KhanAcademyPlaylist(Parcel in) {
        this.title = in.readString();
        this.kaUrl = in.readString();
        this.description = in.readString();
    }

    public static final Creator<KhanAcademyPlaylist> CREATOR = new Creator<KhanAcademyPlaylist>() {
        public KhanAcademyPlaylist createFromParcel(Parcel source) {
            return new KhanAcademyPlaylist(source);
        }

        public KhanAcademyPlaylist[] newArray(int size) {
            return new KhanAcademyPlaylist[size];
        }
    };


    /*public static List<KhanAcademyPlaylist> parseJSONObject(JSONArray jsonArray) {

        try {
            JSONArray playlistsArray = jsonArray.getJSONArray(0);

            List<KhanAcademyPlaylist> khanAcademyPlaylists = new ArrayList<KhanAcademyPlaylist>();

            for (int index = 0; index < playlistsArray.length(); index++) {

                JSONObject playlistObject = playlistsArray.getJSONObject(index);

                KhanAcademyPlaylist newKhanAcademyPlaylist = new KhanAcademyPlaylist(playlistObject);

                khanAcademyPlaylists.add(newKhanAcademyPlaylist);
            }

            return khanAcademyPlaylists;
        } catch (JSONException e) {
            return new ArrayList<KhanAcademyPlaylist>();
        }
    }*/


/*    public KhanAcademyPlaylist(JSONObject playlistObject) throws JSONException {
        kaUrl = playlistObject.optString("url", "Unknown URL");
        title = playlistObject.optString("title", "Unknown Title");
        description = playlistObject.optString("description", "Unknown Description");*/
    }
