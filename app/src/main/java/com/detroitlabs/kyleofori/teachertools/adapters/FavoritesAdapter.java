package com.detroitlabs.kyleofori.teachertools.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.utils.SharedPreference;

/**
 * Created by kyleofori on 12/10/14.
 */
public class FavoritesAdapter extends ArrayAdapter<LessonModel> {

    private Context context;
    List<LessonModel> lessonModels;
    SharedPreference sharedPreference;

    public FavoritesAdapter (Context context, List<LessonModel> lessonModels) {
        super(context, R.layout.list_item_favorite, lessonModels);
        this.context = context;
        this.lessonModels = lessonModels;
        sharedPreference = new SharedPreference();
    }

    private class ViewHolder {
        TextView txtTitleFavorite;
        TextView txtLessonUrlFavorite;
        TextView txtDescriptionFavorite;
        ImageView favoriteImg;
    }

    @Override
    public int getCount() {
        return lessonModels.size();
    }

    @Override
    public LessonModel getItem(int position) {
        return lessonModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_favorite, null);
            holder = new ViewHolder();
            holder.txtTitleFavorite = (TextView) convertView
                    .findViewById(R.id.txt_title_fav);
            holder.txtLessonUrlFavorite = (TextView) convertView
                    .findViewById(R.id.txt_description_fav);
            holder.txtDescriptionFavorite = (TextView) convertView
                    .findViewById(R.id.txt_lessonUrl_fav);
            holder.favoriteImg = (ImageView) convertView
                    .findViewById(R.id.imgbtn_favorite);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LessonModel lessonModel = (LessonModel) getItem(position);
        holder.txtTitleFavorite.setText(lessonModel.getTitle());
        holder.txtLessonUrlFavorite.setText(lessonModel.getLessonUrl());
        holder.txtDescriptionFavorite.setText(lessonModel.getDescription() + "");

        /*If a lessonModel exists in shared preferences then set heart_red drawable
         * and set a tag*/
        if (checkFavoriteItem(lessonModel)) {
            holder.favoriteImg.setImageResource(R.drawable.favestar);
            holder.favoriteImg.setTag("red");
        } else {
            holder.favoriteImg.setImageResource(R.drawable.star_none);
            holder.favoriteImg.setTag("grey");
        }

        return convertView;
    }

    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(LessonModel checkLessonModel) {
        boolean check = false;
        List<LessonModel> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (LessonModel product : favorites) {
                if (product.equals(checkLessonModel)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void add(LessonModel lessonModel) {
        super.add(lessonModel);
        lessonModels.add(lessonModel);
        notifyDataSetChanged();
    }

    @Override
    public void remove(LessonModel lessonModel) {
        super.remove(lessonModel);
        lessonModels.remove(lessonModel);
        notifyDataSetChanged();
    }
}