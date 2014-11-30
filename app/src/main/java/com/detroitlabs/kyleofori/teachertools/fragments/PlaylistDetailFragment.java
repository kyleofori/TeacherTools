package com.detroitlabs.kyleofori.teachertools.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.KhanAcademyPlaylist;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class PlaylistDetailFragment extends Fragment {

    private static final String ARG_PLAYLIST = "arg_khan_academy_playlist";

    public static PlaylistDetailFragment newInstance(KhanAcademyPlaylist khanAcademyPlaylist) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYLIST, khanAcademyPlaylist);

        PlaylistDetailFragment playlistDetailFragment = new PlaylistDetailFragment();
        playlistDetailFragment.setArguments(args);

        return playlistDetailFragment;
    }

    private TextView titleTextView, kaUrlTextView, descriptionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_detail, container, false);

        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        kaUrlTextView = (TextView) view.findViewById(R.id.kaUrlTextView);
        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        KhanAcademyPlaylist khanAcademyPlaylist = getArguments().getParcelable(ARG_PLAYLIST);

        if (khanAcademyPlaylist != null) {

            titleTextView.setText(khanAcademyPlaylist.getTitle());
            kaUrlTextView.setText(khanAcademyPlaylist.getKaUrl());
            descriptionTextView.setText(khanAcademyPlaylist.getDescription());

        } else {
            throw new IllegalStateException("Must supply a KhanAcademyPlaylist to PlaylistDetailFragment");
        }
    }
}
