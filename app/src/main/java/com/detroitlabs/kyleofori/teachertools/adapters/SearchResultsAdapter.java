package com.detroitlabs.kyleofori.teachertools.adapters;

import android.content.Context;
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
    private List<LessonModel> lessonModels = new ArrayList<>();
    public List<LessonModel> filteredLessons = new ArrayList<>();


    public SearchResultsAdapter(Context context, List<LessonModel> lessonModels) {
        super();
        this.context = context;
        this.lessonModels = lessonModels;
        filteredLessons = this.lessonModels;
    }

    public void clear() {
        lessonModels.clear();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_lesson_plan, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LessonModel lessonModel = getItem(position);

        viewHolder.txtTitleFav.setText(lessonModel.getTitle());
        viewHolder.txtLessonUrlFav.setText(lessonModel.getLessonUrl());
        viewHolder.txtDescriptionFav.setText(lessonModel.getDescription());
        viewHolder.imgStarFav.setImageDrawable(getStar(lessonModel));

        return convertView;
    }

    public void add(LessonModel lessonModel) {
        lessonModels.add(lessonModel);
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        private TextView txtTitleFav;
        private TextView txtLessonUrlFav;
        private TextView txtDescriptionFav;
        private ImageView imgStarFav;

        public ViewHolder(View rootView) {
            this.txtTitleFav = (TextView) rootView.findViewById(R.id.txt_title_result);
            this.txtLessonUrlFav = (TextView) rootView.findViewById(R.id.txt_lessonUrl_result);
            this.txtDescriptionFav = (TextView) rootView.findViewById(R.id.txt_description_result);
            this.imgStarFav = (ImageView) rootView.findViewById(R.id.img_star_result);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.values = lessonModels;
                    results.count = lessonModels.size();
                } else {
                    ArrayList<LessonModel> filteredList = new ArrayList<LessonModel>();

                    for (LessonModel lessonModel : lessonModels) {
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

    private Drawable getStar(LessonModel lessonModel) {
        Drawable star;
        if(lessonModel.isFavorited()) {
            star = context.getResources().getDrawable(R.drawable.favestar);
            notifyDataSetChanged();
        } else {
            star = context.getResources().getDrawable(R.drawable.star_none);
            notifyDataSetChanged();
        }
        return star;
    }

}
