package com.detroitlabs.kyleofori.teachertools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.KhanAcademyPlaylist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<KhanAcademyPlaylist> khanAcademyPlaylists = new ArrayList<>();
    private List<KhanAcademyPlaylist> temporaryPlaylists = new ArrayList<>();

    public void setPlaylistsInAdapter(List<KhanAcademyPlaylist> khanAcademyPlaylists) {
        this.khanAcademyPlaylists = khanAcademyPlaylists;
    }

    public SearchResultsAdapter(Context context) {
        super();
        this.context = context;
        this.khanAcademyPlaylists = khanAcademyPlaylists;
        temporaryPlaylists = khanAcademyPlaylists;
    }

    public void putPlaylistsIntoView() {
        //THIS DOES NOTHING BUT NEEDS TO DO SOMETHING
    }

    public void clear() {
        khanAcademyPlaylists.clear();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<String> FilteredList = new ArrayList<String>();
                if (constraint == null || constraint.length() == 0) {
                    results.values = khanAcademyPlaylists;
                    results.count = khanAcademyPlaylists.size();
                } else {
                    for (int i = 0; i < getCount(); i++) {
                        String data = getItem(i).getTitle();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredList.add(data);
                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();
                }
                return results;
            }



            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                temporaryPlaylists = (List<KhanAcademyPlaylist>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
