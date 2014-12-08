package com.detroitlabs.kyleofori.teachertools.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class LessonModel implements Parcelable {

    private String title, lessonUrl, description;

    public LessonModel(String title, String lessonUrl, String description){
        this.title = title;
        this.lessonUrl = lessonUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getLessonUrl() {
        return lessonUrl;
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
        dest.writeString(this.lessonUrl);
        dest.writeString(this.description);
    }

    private LessonModel(Parcel in) {
        this.title = in.readString();
        this.lessonUrl = in.readString();
        this.description = in.readString();
    }

    public static final Creator<LessonModel> CREATOR = new Creator<LessonModel>() {
        public LessonModel createFromParcel(Parcel source) {
            return new LessonModel(source);
        }

        public LessonModel[] newArray(int size) {
            return new LessonModel[size];
        }
    };
}
