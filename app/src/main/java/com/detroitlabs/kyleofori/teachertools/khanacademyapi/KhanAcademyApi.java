package com.detroitlabs.kyleofori.teachertools.khanacademyapi;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class KhanAcademyApi {

    private static final String ROOT_URL = "www.khanacademy.org";

    //Singleton!
    private static KhanAcademyApi khanAcademyApi;

    public static KhanAcademyApi getKhanAcademyApi() {
        if (khanAcademyApi == null) {
            khanAcademyApi = new KhanAcademyApi();
        }

        return khanAcademyApi;
    }

    private KhanAcademyApi() {
    }

    public void getSubredditEntries(String searchTerm, KhanAcademyApiCallback callback) {

        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority(ROOT_URL)
                .appendPath("api")
                .appendPath("v1")
                .appendPath("playlists")
//                .appendPath(searchTerm)
//                .appendPath("videos")
                .build();

        new LoadDataInBackground(callback).execute(uri);
    }

    private JSONArray getJSONObjectFromUri(Uri uri) throws IOException, JSONException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();

        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        int bytesRead;
        StringBuilder stringBuilder = new StringBuilder();

        while ((bytesRead = bufferedInputStream.read()) != -1) {
            stringBuilder.append((char)bytesRead);
        }

        bufferedInputStream.close();
        httpURLConnection.disconnect();
        return new JSONArray(stringBuilder.toString());
    }

    private class LoadDataInBackground extends AsyncTask<Uri, Void, JSONArray> {

        private KhanAcademyApiCallback khanAcademyApiCallback;

        private LoadDataInBackground(KhanAcademyApiCallback khanAcademyApiCallback) {
            this.khanAcademyApiCallback = khanAcademyApiCallback;
        }

        @Override
        protected JSONArray doInBackground(Uri... params) {

            try {
                Uri uri = params[0];
                Log.e("hey", "made another call");
                return getJSONObjectFromUri(uri);
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if (result != null) {
                this.khanAcademyApiCallback.onSuccess(result);
            } else {
                this.khanAcademyApiCallback.onError();
            }
        }
    }

}
