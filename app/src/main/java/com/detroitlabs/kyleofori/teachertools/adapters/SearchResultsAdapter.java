package com.detroitlabs.kyleofori.teachertools.adapters;

import android.content.Context;
import android.content.Intent;
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
    private List<KhanAcademyPlaylist> originalPlaylists = new ArrayList<>();
    private List<KhanAcademyPlaylist> filteredPlaylists = new ArrayList<>();
    private Intent intent;

    public void setPlaylistsInAdapter(List<KhanAcademyPlaylist> khanAcademyPlaylists) {
        this.originalPlaylists = khanAcademyPlaylists;
        this.filteredPlaylists = khanAcademyPlaylists;
    }

    public SearchResultsAdapter(Context context) {
        super();
        this.context = context;
    }

    public void clear() {
        originalPlaylists.clear();
        filteredPlaylists.clear();
    }

    @Override
    public int getCount() {
        return filteredPlaylists.size();
    }

    @Override
    public KhanAcademyPlaylist getItem(int i) {
        return filteredPlaylists.get(i);
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
                if (constraint == null || constraint.length() == 0) {
                    results.values = originalPlaylists;
                    results.count = originalPlaylists.size();
                } else {
                    ArrayList<KhanAcademyPlaylist> filteredList = new ArrayList<KhanAcademyPlaylist>();

                    for (KhanAcademyPlaylist khanAcademyPlaylist : originalPlaylists) {
                        if (khanAcademyPlaylist.getTitle() != null && khanAcademyPlaylist.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(khanAcademyPlaylist);
                        }
                    }
                    results.values = filteredList;
                    results.count = filteredList.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filteredPlaylists = (ArrayList<KhanAcademyPlaylist>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
