package com.detroitlabs.kyleofori.teachertools.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<LessonModel> originalLessons = new ArrayList<>();
    private List<LessonModel> filteredLessons = new ArrayList<>();
    private Intent intent;

    public void setLessonsInAdapter(List<LessonModel> lessonModels) {
        this.originalLessons = lessonModels;
        this.filteredLessons = lessonModels;
    }

    public SearchResultsAdapter(Context context) {
        super();
        this.context = context;
    }

    public void clear() {
        originalLessons.clear();
        filteredLessons.clear();
    }

    @Override
    public int getCount() {
        return filteredLessons.size();
    }

    @Override
    public LessonModel getItem(int i) {
        return filteredLessons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_favorite, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LessonModel lessonModel = getItem(position);

        viewHolder.txtTitleFav.setText(lessonModel.getTitle());
        viewHolder.txtLessonUrlFav.setText(lessonModel.getLessonUrl());
        viewHolder.txtDescriptionFav.setText(lessonModel.getDescription());
        Drawable starNone = context.getResources().getDrawable(R.drawable.star_none);
        viewHolder.imgStarFav.setImageDrawable(starNone);

        return convertView;
    }

    private static class ViewHolder {

        private TextView txtTitleFav;
        private TextView txtLessonUrlFav;
        private TextView txtDescriptionFav;
        private ImageView imgStarFav;

        public ViewHolder(View rootView) {
            this.txtTitleFav = (TextView) rootView.findViewById(R.id.txt_title_fav);
            this.txtLessonUrlFav = (TextView) rootView.findViewById(R.id.txt_lessonUrl_fav);
            this.txtDescriptionFav = (TextView) rootView.findViewById(R.id.txt_description_fav);
            this.imgStarFav = (ImageView) rootView.findViewById(R.id.img_star);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.values = originalLessons;
                    results.count = originalLessons.size();
                } else {
                    ArrayList<LessonModel> filteredList = new ArrayList<LessonModel>();

                    for (LessonModel lessonModel : originalLessons) {
                        if (lessonModel.getTitle() != null && lessonModel.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(lessonModel);
                        }
                    }
                    results.values = filteredList;
                    results.count = filteredList.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filteredLessons = (ArrayList<LessonModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
