package com.detroitlabs.kyleofori.teachertools.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class DetailFragment extends Fragment {

    private static final String ARG_PLAYLIST = "arg_khan_academy_playlist";

    public static DetailFragment newInstance(LessonModel lessonModel) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYLIST, lessonModel);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        return detailFragment;
    }

    private TextView txtTitle, txtKaUrl, txtDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        txtTitle = (TextView) view.findViewById(R.id.titleTextView);
        txtKaUrl = (TextView) view.findViewById(R.id.lessonUrlTextView);
        txtDescription = (TextView) view.findViewById(R.id.descriptionTextView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LessonModel lessonModel = getArguments().getParcelable(ARG_PLAYLIST);

        if (lessonModel != null) {

            txtTitle.setText(lessonModel.getTitle());
            txtKaUrl.setText(lessonModel.getLessonUrl());
            txtDescription.setText(lessonModel.getDescription());

        } else {
            throw new IllegalStateException("Must supply a KhanAcademyPlaylist to PlaylistDetailFragment");
        }
    }
}
