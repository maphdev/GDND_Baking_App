package com.example.manon.bakingapp.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manon.bakingapp.Models.Recipe;
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
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment {

    @BindView(R.id.exoplayer) SimpleExoPlayerView playerView;
    @BindView(R.id.step_instruction_title_txtView) TextView titleTxtView;
    @BindView(R.id.step_details_instruction_txtView) TextView instructionTxtView;
    @BindView(R.id.buttonRight) Button buttonRight;
    @BindView(R.id.buttonLeft) Button buttonLeft;

    private SimpleExoPlayer exoPlayer;
    private Recipe recipe;
    private Step step;
    private int id;
    private boolean navigationButtons = true;

    // Required empty public constructor
    public StepDetailsFragment() {}

    // create a fragment with a recipe
    public static StepDetailsFragment newInstance(Recipe recipe, int id, boolean navigationButtons){
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("RECIPE", recipe);
        args.putInt("ID", id);
        args.putBoolean("NAVIGATION_BUTTONS", navigationButtons);
        stepDetailsFragment.setArguments(args);
        return stepDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        ButterKnife.bind(this, rootView);

        recipe = getArguments().getParcelable("RECIPE");
        id = getArguments().getInt("ID");
        step = recipe.getSteps().get(id);
        navigationButtons = getArguments().getBoolean("NAVIGATION_BUTTONS");

        populateViews(step);

        return rootView;
    }

    public void populateViews(Step step){
        //exoplayer's video
        String urlVideoString = step.getVideoURL();
        if (urlVideoString.equals("") || !NetworkUtils.isNetworkAvailable(getContext())){
            playerView.setVisibility(View.GONE);
        } else {
            playerView.setVisibility(View.VISIBLE);
            URL url = NetworkUtils.getURLfromUri(Uri.parse(step.getVideoURL()));
            initializePlayer(Uri.parse(urlVideoString));
        }
        // exoplayer's thumbnail
        String urlThumbnailString = step.getThumbnailURL();
        if(!urlThumbnailString.equals("") & NetworkUtils.isNetworkAvailable(getContext())){
            if (NetworkUtils.isAnImage(urlThumbnailString)){
                playerView.setDefaultArtwork(NetworkUtils.getBitmapFromURL(step.getThumbnailURL()));
            }
        }
        // txtViews
        titleTxtView.setText(step.getShortDescription());
        instructionTxtView.setText(step.getDescription());

        setButtons();
    }

    // initialize exoplayer
    private void initializePlayer(Uri uri){
        if (exoPlayer == null){
            // create instance of ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            // Prepare the media source
            String userAgent = Util.getUserAgent(getContext(), getResources().getString(R.string.name_exoplayer));
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    // release exoplayer
    public void releasePlayer(){
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    // set and display the buttons
    public void setButtons(){
        if(!navigationButtons){
            buttonRight.setVisibility(View.INVISIBLE);
            buttonLeft.setVisibility(View.INVISIBLE);
            return;
        }
        if(step.getId()+1 >= recipe.getSteps().size()){
            buttonRight.setVisibility(View.INVISIBLE);
        } else if(step.getId()-1 < 0){
            buttonLeft.setVisibility(View.INVISIBLE);
        } else {
            buttonRight.setVisibility(View.VISIBLE);
            buttonLeft.setVisibility(View.VISIBLE);
        }
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment.increaseStep(recipe, id, navigationButtons);
            }
        });
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment.decreaseStep(recipe, id, navigationButtons);
            }
        });
    }

    // change fragments with button's action, implemented by activity
    ChangeFragment changeFragment;

    public interface ChangeFragment{
        void increaseStep(Recipe recipe, int id, boolean navigationButton);
        void decreaseStep(Recipe recipe, int id, boolean navigationButton);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            changeFragment = (ChangeFragment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ChangeFragment");

        }
    }

    // when a phone is a landscape mode, the exoplayer is in full screen
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            SimpleExoPlayerView.LayoutParams params = (SimpleExoPlayerView.LayoutParams) playerView.getLayoutParams();
            params.width = SimpleExoPlayerView.LayoutParams.MATCH_PARENT;
            params.height = SimpleExoPlayerView.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            SimpleExoPlayerView.LayoutParams params = (SimpleExoPlayerView.LayoutParams) playerView.getLayoutParams();
            params.width = SimpleExoPlayerView.LayoutParams.MATCH_PARENT;
            params.height = 300;
            playerView.setLayoutParams(params);
        }
    }

    // what happens when the activity is destroyed?
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null)
            releasePlayer();
    }
}
