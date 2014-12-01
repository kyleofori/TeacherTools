package com.detroitlabs.kyleofori.teachertools.khanacademyapi;

import org.json.JSONArray;

/**
 * Created by bobbake4 on 11/13/14.
 */
public interface KhanAcademyApiCallback {

    public void onSuccess(JSONArray response);
    public void onError();

}
