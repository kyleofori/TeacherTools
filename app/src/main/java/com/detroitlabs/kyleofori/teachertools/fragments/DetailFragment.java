package com.detroitlabs.kyleofori.teachertools.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.teachertools.R;
import com.detroitlabs.kyleofori.teachertools.models.LessonModel;
import com.detroitlabs.kyleofori.teachertools.utils.SharedPreference;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PLAYLIST = "arg_khan_academy_playlist";

    public static DetailFragment newInstance(LessonModel lessonModel) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYLIST, lessonModel);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        return detailFragment;
    }

    private TextView txtTitle, txtKaUrl, txtDescription;
    private SharedPreference sharedPreference = new SharedPreference();
    private LessonModel lessonModel;
    private ImageView imgFavoritesStar;

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

        imgFavoritesStar = (ImageView) view.findViewById(R.id.img_detail_star);
        imgFavoritesStar.setOnClickListener(this);

        lessonModel = getArguments().getParcelable(ARG_PLAYLIST);

        if (lessonModel != null) {

            txtTitle.setText(lessonModel.getTitle());
            txtKaUrl.setText(lessonModel.getLessonUrl());
            txtDescription.setText(lessonModel.getDescription());

        } else {
            throw new IllegalStateException("Must supply a KhanAcademyPlaylist to DetailFragment");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_detail_star:
                Toast.makeText(getActivity(), R.string.toast_favorite_added, Toast.LENGTH_SHORT).show();
                sharedPreference.addFavorite(getActivity(), lessonModel);
                imgFavoritesStar.setEnabled(false);
                break;
        }
    }
}
