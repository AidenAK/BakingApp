package com.example.doelay.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailStepFragment extends Fragment implements Player.EventListener {

    public static final String TAG = DetailStepFragment.class.getSimpleName();
    public static final String STATE_PLAYBACK_POSITION = "state_playback_position";
    public static final String STATE_CURRENT_WINDOW = "state_current_window";
    public static final String STATE_PLAY_WHEN_READY = "state_play_when_ready";

    @BindView(R.id.tv_description)
    TextView description;
    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.no_video)
    ImageView noVideoIcon;


    private Step step;
    private String videoURL;
    private String thumbnailURL;

    private SimpleExoPlayer exoPlayer;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder playbackStateBuilder;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

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

        if(savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(STATE_CURRENT_WINDOW);
            playWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);
        } else {
            playbackPosition = C.INDEX_UNSET;
            currentWindow = C.INDEX_UNSET;
            playWhenReady = true;
        }

        //set up if it should display no video icon or not
        if(videoExists() ){
            //set up the video player
            initializeMediaSession();
        } else {
            exoPlayerView.setVisibility(View.INVISIBLE);
            noVideoIcon.setVisibility(View.VISIBLE);
        }

        description.setText(step.getDescription());
        return view;
    }

    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeExoPlayer(videoExists());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        hideSystemUi();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initializeExoPlayer(videoExists());
        }
    }

    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(getContext(), TAG);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);

        playbackStateBuilder = new PlaybackStateCompat.Builder().
                setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE );
        mediaSession.setPlaybackState(playbackStateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                exoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                exoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
            }
        });
        mediaSession.setActive(true);
    }
    private void initializeExoPlayer (Boolean videoExits) {

        if (videoExits) {
            if(exoPlayer == null) {
                TrackSelector trackSelector =  new DefaultTrackSelector();
                exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                exoPlayerView.setPlayer(exoPlayer);

                exoPlayer.setPlayWhenReady(playWhenReady);
            }

            exoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), getContext().getResources().getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(
                    getVideoUri(),
                    new DefaultDataSourceFactory(getContext(),userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);

            boolean hasStartPosition = currentWindow != C.INDEX_UNSET;
            if(hasStartPosition) {
                exoPlayer.seekTo(currentWindow, playbackPosition);
            }

            exoPlayer.prepare(mediaSource, !hasStartPosition, false);
        }
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
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if(playbackState == ExoPlayer.STATE_READY && playWhenReady) {
            playbackStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    exoPlayer.getCurrentPosition(), 1f);
        } else if(playbackState == ExoPlayer.STATE_READY) {
            playbackStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    exoPlayer.getCurrentPosition(), 1f);
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        updateCurrentPlaybackPosition();
        outState.putLong(STATE_PLAYBACK_POSITION, playbackPosition);
        outState.putInt(STATE_CURRENT_WINDOW, currentWindow);
        outState.putBoolean(STATE_PLAY_WHEN_READY, playWhenReady);

    }

    private void updateCurrentPlaybackPosition() {
        playbackPosition = exoPlayer.getCurrentPosition();
        currentWindow = exoPlayer.getCurrentWindowIndex();
        playWhenReady = exoPlayer.getPlayWhenReady();
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            updateCurrentPlaybackPosition();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            // release player
            if(exoPlayer != null) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            // release player
            if(exoPlayer != null) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaSession != null){
            mediaSession.setActive(false);
        }

    }
}
