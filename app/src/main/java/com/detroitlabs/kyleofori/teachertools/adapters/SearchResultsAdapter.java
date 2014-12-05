package com.detroitlabs.kyleofori.teachertools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.KhanAcademyPlaylist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsAdapter extends BaseAdapter {

    private Context context;
    private List<KhanAcademyPlaylist> khanAcademyPlaylists = new ArrayList<>();

    public void setPlaylistsInAdapter(List<KhanAcademyPlaylist> khanAcademyPlaylists) {
        this.khanAcademyPlaylists = khanAcademyPlaylists;
    }

    public void clear() {
        khanAcademyPlaylists.clear();
    }
    public SearchResultsAdapter(Context context) {
        super();
        this.context = context;
    }

    public void putPlaylistsIntoView() {
        //THIS DOES NOTHING BUT NEEDS TO DO SOMETHING
    }
    @Override
    public int getCount() {
        return khanAcademyPlaylists.size();
    }

    @Override
    public KhanAcademyPlaylist getItem(int i) {
        return khanAcademyPlaylists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_playlist_row, parent, false);

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
