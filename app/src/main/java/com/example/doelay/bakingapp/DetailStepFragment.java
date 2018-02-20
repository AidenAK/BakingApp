package com.example.doelay.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doelay.bakingapp.model.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 11/13/17.
 */

public class DetailStepFragment extends Fragment{
    public static final String TAG = DetailStepFragment.class.getSimpleName();

    @BindView(R.id.tv_description)
    TextView description;
    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.no_video)
    ImageView noVideoIcon;


    // TODO: 2/14/18 save video playback state

    private Step step;
    private VideoPlayerManager videoPlayerManager;
    private String videoURL;
    private String thumbnailURL;

    public DetailStepFragment() {

    }
    public static DetailStepFragment newInstance(Step step) {
        DetailStepFragment detailStepFragment = new DetailStepFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        detailStepFragment.setArguments(bundle);

        return detailStepFragment;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            step = getArguments().getParcelable("step");

            //set title
            ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();
            actionBar.setTitle(step.getShortDescription());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_step, container, false);
        ButterKnife.bind(this, view);
        boolean videoLink = videoExists();
        if(videoExists() ){
            //set up the video player
            videoPlayerManager = new VideoPlayerManager(getContext(), exoPlayerView);
            videoPlayerManager.initializeExoPlayer(getVideoUri());
        } else {
            exoPlayerView.setVisibility(View.INVISIBLE);
            noVideoIcon.setVisibility(View.VISIBLE);
        }


        description.setText(step.getDescription());


        return view;
    }




    private boolean videoExists(){
        videoURL = step.getVideoURL();
        thumbnailURL = step.getThumbnailURL();

        if(videoURL.equals("") && thumbnailURL.equals("")){
            return false;
        } else {
            return true;
        }
    }

    private Uri getVideoUri() {
        Uri uri;

        try {
            if(videoURL.equals("")) {
                uri = Uri.parse(thumbnailURL);
            } else {
                uri = Uri.parse(videoURL);
            }

        } catch (NullPointerException e){
            throw new NullPointerException();
        }

        return uri;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(videoPlayerManager != null) {
            videoPlayerManager.releasePlayer();
        }
    }
}
