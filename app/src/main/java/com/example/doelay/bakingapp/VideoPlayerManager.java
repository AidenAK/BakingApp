package com.example.doelay.bakingapp;

import android.content.Context;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.doelay.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoPlayerManager implements ExoPlayer.EventListener {

    public static final String TAG = ExoPlayer.class.getSimpleName();

    private Context context;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder playbackStateBuilder;


    public VideoPlayerManager(Context context, SimpleExoPlayerView exoPlayerView) {
        this.context = context;
        this.exoPlayerView = exoPlayerView;
        initializeMediaSession();

    }

    public VideoPlayerManager() {
        super();
    }

    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(context, TAG);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);

        playbackStateBuilder = new PlaybackStateCompat.Builder().setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE);
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
    }
    public void initializeExoPlayer (Uri videoUri) {
        if(exoPlayer == null) {
            TrackSelector trackSelector =  new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            exoPlayerView.setPlayer(exoPlayer);
//            exoPlayer.addListener(this);
        }

        String userAgent = Util.getUserAgent(context, context.getResources().getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource(
                videoUri,
                new DefaultDataSourceFactory(context,userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);

    }

    public void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
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
    public void onPlayerError(ExoPlaybackException error) {

    }


    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
    @Override
    public void onSeekProcessed() {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }



    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }
}
