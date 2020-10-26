package com.example.bakingapplication.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bakingapplication.R;
import com.example.bakingapplication.models.Step;
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

public class PlayerFragment extends Fragment {

  SimpleExoPlayer player;
  DefaultBandwidthMeter bandwidthMeter;
  TrackSelection.Factory videoTrackSelectionFactory;
  TrackSelector trackSelector;
  DataSource.Factory dataSourceFactory;
  MediaSource videoSource;
  PlayerView playerView;
  Step step;
  private final String RECIPE_STEP = "recipe";
  Uri uri;

  public PlayerFragment() {
  }

  public void initialisePlayer(Uri videoUri){
    if(player == null){
      bandwidthMeter = new DefaultBandwidthMeter();
      videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
      trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
      player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
      playerView.setPlayer(player);
      dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingApplication"), bandwidthMeter);
      videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
      player.prepare(videoSource);
    }
  }

  private void terminatePlayer() {
    if (player != null) {
      player.stop();
      player.release();
      player = null;
      dataSourceFactory = null;
      videoSource = null;
      trackSelector = null;
    }
  }

  public static PlayerFragment newInstance() {
    PlayerFragment fragment = new PlayerFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

      if (getArguments() != null) {
        step = getArguments().getParcelable(RECIPE_STEP);
        if (step.getVideoURL() != null) {
          uri = Uri.parse(step.getVideoURL());
        }
      }

      //if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
      //} else if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().show();
      //}

    return inflater.inflate(R.layout.fragment_player, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    playerView = view.findViewById(R.id.player_view);
    initialisePlayer(uri);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (Util.SDK_INT > 23) {
      initialisePlayer(uri);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (Util.SDK_INT <= 23 || player == null) {
      initialisePlayer(uri);
    }
  }

  @Override public void onPause() {
    super.onPause();
    if (player != null) {
      terminatePlayer();
    }
  }

  @Override public void onStop() {
    super.onStop();
    if (player != null) {
      terminatePlayer();
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (player != null) {
      terminatePlayer();
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    if (player != null) {
      terminatePlayer();
    }
  }
}