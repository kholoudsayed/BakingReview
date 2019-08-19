package com.example.kholoud.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.example.kholoud.bakingapp.DetailActivity;
import com.example.kholoud.bakingapp.R;
import com.example.kholoud.bakingapp.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {
//DEF *****************************
    //Using Blind View
    @BindView(R.id.navigation_container) FrameLayout container;
    @BindView(R.id.current_step_text) TextView StepText;
    @BindView(R.id.video_pv) PlayerView PlayerView;
    @BindView(R.id.thumbnail_container) ImageView ImageView;
    @BindView(R.id.prev_step_button) Button PreviousButton;

    @BindView(R.id.instruction_text) TextView InstructionText;
    @BindView(R.id.next_step_button) Button NextButton;

//*****
private SimpleExoPlayer ExoPlayer;

    private DetailActivity detActivity;
    private int CurrentPos;
    private long LastPlayerPos;
    private boolean LastVedioPlayState;
    //**********************************************
    //COnstractor  empty ***************************************************************

    public InstructionFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ///****************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_instruction, container, false);
        ButterKnife.bind(this, view);
        detActivity = (DetailActivity) getActivity();
        CurrentPos = detActivity.getCurrentPosition();
        //Get Descriptiion
        String instruction = detActivity.getRecipeStepList().get(CurrentPos).getDescription();
        InstructionText.setText(instruction);
        setView(extractThumbnailUrl());
        StartVedio(extractUrl());
        ResetListeners();
        Updated();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            CurrentPos = savedInstanceState.getInt(getString(R.string.position_value));
            LastPlayerPos = savedInstanceState.getLong(getString(R.string.player_position));
            LastVedioPlayState = savedInstanceState.
                    getBoolean(getString(R.string.player_play_state));
            onPosChanged(CurrentPos);
        }
    }

    @Override
    public void onStart() {
        //Start Vedio
        super.onStart();
        if (ExoPlayer == null) {
            StartVedio(extractUrl());
        }
    }

    @Override
    public void onResume() {
        // Start Vedio
        super.onResume();
        if (ExoPlayer == null) {
            StartVedio(extractUrl());
        }
    }

    @Override
    public void onPause() {
        //Realse Vedio
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseVedio();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseVedio();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.position_value), CurrentPos);
        if (ExoPlayer != null) {
            outState.putLong(getString(R.string.player_position), ExoPlayer.getCurrentPosition());
            outState.putBoolean(getString(R.string.player_play_state),
                    ExoPlayer.getPlayWhenReady());
        }
    }

   // for Return
    public void ResetListeners() {
        PreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrease pos to return
                if (CurrentPos > 0) CurrentPos--;

                Updated();
                resetVedio();
// **************************change
                onPosChanged(CurrentPos);
            }
        });
////*******************************************************
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentPos < detActivity.getRecipeStepList().size() - 1) CurrentPos++;

                Updated();
                //Reset fo vedio
                resetVedio();

                onPosChanged(CurrentPos);
            }
        });
    }

    public void onPosChanged(int newPosition) {
        CurrentPos = newPosition;

        releaseVedio();
        StartVedio(extractUrl());
        ExoPlayer.seekTo(LastPlayerPos);
        ExoPlayer.setPlayWhenReady(LastVedioPlayState);

        if (CurrentPos > -1 && CurrentPos < detActivity.getRecipeStepList().size()) {
            RecipeStep step = detActivity.getRecipeStepList().get(CurrentPos);
            InstructionText.setText(step.getDescription());
        }

        Updated();
    }
//*************************************
    public void Updated() {
        //Update page number view
        String currentPositionText = "Step " + Integer.toString(CurrentPos);
        StepText.setText(currentPositionText);

        if (CurrentPos == 0) {
            PreviousButton.setVisibility(View.INVISIBLE);
            NextButton.setVisibility(View.VISIBLE);
        }
        else if (CurrentPos == detActivity.getRecipeStepList().size() - 1) {
            PreviousButton.setVisibility(View.VISIBLE);
            NextButton.setVisibility(View.INVISIBLE);
        }
        else {
            PreviousButton.setVisibility(View.VISIBLE);
            NextButton.setVisibility(View.VISIBLE);
        }
    }
//****************************
    public String extractUrl() {
        RecipeStep step = detActivity.getRecipeStepList().get(CurrentPos);
        String videoUrl = step.getVideoUrl();
        if (TextUtils.isEmpty(videoUrl)) {
            videoUrl = step.getImageUrl(); //Error
        }
        return videoUrl;
    }

    public String extractThumbnailUrl() {
        RecipeStep currentStep = detActivity.getRecipeStepList().get(CurrentPos);
        return currentStep.getImageUrl();
    }
//*************************************************
    public void StartVedio(String videoUrl) {

        if (TextUtils.isEmpty(videoUrl)) {
            ImageView.setVisibility(View.VISIBLE);
            PlayerView.setVisibility(View.GONE);

        } else {
            Uri videoUri = Uri.parse(videoUrl);
            PlayerView.setVisibility(View.VISIBLE);

            ImageView.setVisibility(View.GONE);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoFactory);
            ExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            PlayerView.setPlayer(ExoPlayer);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
            ExoPlayer.prepare(mediaSource);
        }
    }
//**********************************************************
    //Load Poto
    public void setView(String VeioUrl) {
        Picasso.get()
                .load(Uri.parse(VeioUrl))
                .placeholder(R.drawable.cake)
                .error(R.drawable.cake)
                .into(ImageView);
    }

    public void releaseVedio() {
        if (ExoPlayer != null) { ExoPlayer.release(); }
    }

    public void resetVedio() {
        LastPlayerPos = 0;
        LastVedioPlayState = false;
    }
}
