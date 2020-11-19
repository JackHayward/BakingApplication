package com.example.bakingapplication.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bakingapplication.R;
import com.example.bakingapplication.models.Step;
import com.google.android.exoplayer2.C;
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

public class PlayerFragment extends Fragment implements View.OnClickListener {

  SimpleExoPlayer player;
  DefaultBandwidthMeter bandwidthMeter;
  TrackSelection.Factory videoTrackSelectionFactory;
  TrackSelector trackSelector;
  DataSource.Factory dataSourceFactory;
  MediaSource videoSource;
  Step step;
  private final String RECIPE_STEP = "recipe";
  Uri uri;
  View view;
  private OnOptionClickListener callBack;
  public static final String PLAY_WHEN_READY =  "play_when_ready";
  public static final String URI =  "step_uri";
  public static final String STEP =  "step";
  public static final String VIDEO_POSITION =  "video_position";
  private long playerPosition;
  private int windowIndex;
  private boolean shouldPlayWhenReady;

  @Nullable
  @BindView(R.id.btn_next_step) Button nextButton;
  @Nullable
  @BindView(R.id.btn_previous_step) Button previousButton;
  @BindView(R.id.placeholder_image) ImageView placeholder;
  @BindView(R.id.player_view) PlayerView playerView;

  public PlayerFragment() {
  }

  public void initialisePlayer(Uri videoUri) {
    if (player == null) {
      bandwidthMeter = new DefaultBandwidthMeter();
      videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
      trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
      player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
      playerView.setPlayer(player);
      dataSourceFactory = new DefaultDataSourceFactory(getContext(),
          Util.getUserAgent(getContext(), "BakingApplication"), bandwidthMeter);
      videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

      if (playerPosition != C.TIME_UNSET) {
        player.seekTo(playerPosition);
      }

      player.prepare(videoSource);
      player.setPlayWhenReady(shouldPlayWhenReady);
    }
  }

  private void terminatePlayer() {
    if (player != null) {
      shouldPlayWhenReady = player.getPlayWhenReady();
      playerPosition = player.getCurrentPosition();
      windowIndex = player.getCurrentWindowIndex();
      player.stop();
      player.release();
      player = null;
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
    view = inflater.inflate(R.layout.fragment_player, container, false);
    ButterKnife.bind(this, view);
    placeholder.setVisibility(View.GONE);

    if(savedInstanceState != null){
      step = savedInstanceState.getParcelable(STEP);
      playerPosition = savedInstanceState.getLong(VIDEO_POSITION);
      uri = Uri.parse(savedInstanceState.getString(URI));
    }

    if (getArguments() != null) {
      step = getArguments().getParcelable(RECIPE_STEP);
      if ((step.getVideoURL().equals(""))) {
        placeholder.setVisibility(View.VISIBLE);
        playerView.setVisibility(View.GONE);
      } else {
        playerView.setVisibility(View.VISIBLE);
        uri = Uri.parse(step.getVideoURL());
      }
    }
    if (nextButton != null && previousButton != null) {
      nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          callBack.onOptionSelected("next", step);
        }
      });
      previousButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          callBack.onOptionSelected("previous", step);
        }
      });
    }

    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

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
    if (player != null) {
      player.setPlayWhenReady(shouldPlayWhenReady);
      player.seekTo(playerPosition);
    }
  }

  @Override public void onPause() {
    super.onPause();
    if(player != null){
      shouldPlayWhenReady = player.getPlayWhenReady();
      playerPosition = player.getCurrentPosition();
      windowIndex = player.getCurrentWindowIndex();
      if (Util.SDK_INT > 23) {
        terminatePlayer();
      }
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if(player != null){
      shouldPlayWhenReady = player.getPlayWhenReady();
      playerPosition = player.getCurrentPosition();
      windowIndex = player.getCurrentWindowIndex();
      if (Util.SDK_INT > 23) {
        terminatePlayer();
      }
    }
  }

  public interface OnOptionClickListener {
    void onOptionSelected(String option, Step step);
  }

  @Override public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    if (player != null) {
      shouldPlayWhenReady = player.getPlayWhenReady();
      playerPosition = player.getCurrentPosition();
      windowIndex = player.getCurrentWindowIndex();
    }
    outState.putLong(VIDEO_POSITION, playerPosition);
    outState.putString(URI, step.getVideoURL());
    outState.putParcelable(STEP, step);
  }

  public void setCallBack(OnOptionClickListener callBack) {
    this.callBack = callBack;
  }

  @Override public void onClick(View view) {
    int id = view.getId();
  }
}