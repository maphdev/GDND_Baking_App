package com.example.manon.bakingapp.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.manon.bakingapp.Models.Step;
import com.example.manon.bakingapp.R;
import com.example.manon.bakingapp.Utils.NetworkUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaPlayerFragment extends Fragment {

    @BindView(R.id.exoplayer) SimpleExoPlayerView playerView;
    private SimpleExoPlayer exoPlayer;

    public MediaPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_media_player, container, false);

        ButterKnife.bind(this, rootView);

        Step step = getActivity().getIntent().getParcelableExtra(getString(R.string.PARCELABLE_STEP));

        // exoplayer
        //playerView.setDefaultArtwork(NetworkUtils.getBitmapFromURL(step.getThumbnailURL()));

        Toast.makeText(getContext(), step.getVideoURL(), Toast.LENGTH_LONG).show();
        Log.i("TRY", step.getVideoURL());
        initializePlayer(Uri.parse(step.getVideoURL()));
        return rootView;
    }

    private void initializePlayer(Uri uri){
        if (exoPlayer == null){
            // create instance of ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            // Prepare the media source
            String userAgent = Util.getUserAgent(getContext(), "VideoStep");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer(){
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }
}
