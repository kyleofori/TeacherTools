package com.detroitlabs.kyleofori.teachertools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.KhanAcademyPlaylist;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsListAdapter extends ArrayAdapter<KhanAcademyPlaylist> {

    public SearchResultsListAdapter(Context context) {
        super(context, R.layout.list_item_playlist_row);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_playlist_row, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        KhanAcademyPlaylist khanAcademyPlaylist = getItem(position);

        viewHolder.titleTextView.setText(khanAcademyPlaylist.getTitle());
        viewHolder.kaUrlTextView.setText(khanAcademyPlaylist.getKaUrl());
        viewHolder.descriptionTextView.setText(khanAcademyPlaylist.getDescription());

        return convertView;
    }

    private static class ViewHolder {

        private TextView titleTextView;
        private TextView kaUrlTextView;
        private TextView descriptionTextView;

        public ViewHolder(View rootView) {
            this.titleTextView = (TextView) rootView.findViewById(R.id.titleTextView);
            this.kaUrlTextView = (TextView) rootView.findViewById(R.id.kaUrlTextView);
            this.descriptionTextView = (TextView) rootView.findViewById(R.id.descriptionTextView);

        }

    }
}
