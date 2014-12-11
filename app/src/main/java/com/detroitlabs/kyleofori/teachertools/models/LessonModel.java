package com.detroitlabs.kyleofori.teachertools.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bobbake4 on 11/13/14.
 */

public class LessonModel implements Parcelable {

    private String title;
    private String lessonUrl;
    private String description;
    private boolean isFavorited;

    public LessonModel(String title, String lessonUrl, String description, boolean isFavorited){
        this.title = title;
        this.lessonUrl = lessonUrl;
        this.description = description;
        this.isFavorited = isFavorited;
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

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    protected LessonModel(Parcel in) {
        title = in.readString();
        lessonUrl = in.readString();
        description = in.readString();
        isFavorited = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(lessonUrl);
        dest.writeString(description);
        dest.writeByte((byte) (isFavorited ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LessonModel> CREATOR = new Parcelable.Creator<LessonModel>() {
        @Override
        public LessonModel createFromParcel(Parcel in) {
            return new LessonModel(in);
        }

        @Override
        public LessonModel[] newArray(int size) {
            return new LessonModel[size];
        }
    };
}
