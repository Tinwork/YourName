package com.yellowman.tinwork.yourname.activities.singleEpisode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.entity.Episode;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.api.series.EpisodeData;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 29/12/2017.
 * Created by Didier Youn on 29/12/2017.
 * Created by Abdel-Atif Mabrouck on 29/12/2017.
 * Created by Antoine Renault on 29/12/2017.
 */

public class SingleEpisodeActivity extends AppCompatActivity {

    private TextView episodeTitle;
    private TextView seasonId;
    private TextView overview;
    private TextView rating;
    private TextView directors;
    private ImageView imgview;
    private UIErrorManager uiErrorManager;

    private boolean fabSubMenuIsExpanded = false;
    private FloatingActionButton fabMenu;
    private LinearLayout layoutFabFavorite;
    private LinearLayout layoutFabShare;

    /**
     * On Create
     *
     * @param savedInstanceBundle bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.episode_single_layout);
        // put some colors in the bg
        GradientGenerator gd = new GradientGenerator(this, findViewById(R.id.single_episode_detail), null);
        gd.buildBackgroundGradientColor();
        initComponent();
        // get the datas
        getIntentData();

    }

    /**
     * Init Components
     *
     */
    private void initComponent() {
        this.episodeTitle = findViewById(R.id.episode_title);
        this.seasonId     = findViewById(R.id.season_id);
        this.overview     = findViewById(R.id.overview);
        this.rating       = findViewById(R.id.episode_rating);
        this.directors    = findViewById(R.id.directors);
        this.imgview      = findViewById(R.id.img_episode);
        this.uiErrorManager = new UIErrorManager(this);
    }

    /**
     * Get Intent Data
     *
     */
    private void getIntentData() {
        Intent intent = getIntent();
        Parcelable episodeParcel = intent.getParcelableExtra("Entity");

        // Cast the Parcelable to an Episode Entity
        Episode episode = (Episode) episodeParcel;

        // Bind the current data
        setCurrentData(episode);
        // Retrieve the other data
        getExtraInforamtion(episode.getId());
    }

    /**
     * Set Current Data
     *
     * @param episode Episode
     */
    private void setCurrentData(Episode episode) {
        episodeTitle.setText(episode.getEpisodeName());
        seasonId.setText(String.valueOf(episode.getAiredSeason()));
        overview.setText(episode.getOverview());
    }

    /**
     * Set Extra Data
     *
     * @param episode Episode
     */
    private void setExtraData(Episode episode) {
        String directorsStr = "";

        for (String s : episode.getDirectors()) {
            directorsStr += s+" ";
        }

        directors.setText(directorsStr);
        rating.setText("Rating: "+episode.getSiteRating());

        // Set the image https://www.thetvdb.com/banners/episodes/
        if (episode.getFilename() != null) {
            Glide.with(this).load(AppUtils.buildMiscURI(Routes.IMG_PATH, episode.getFilename())).into(imgview);
        } else {
            Glide.with(this).load(R.drawable.yourname_bg).into(imgview);
        }
    }

    /**
     * Get Extra Information
     *
     * @param id Int
     */
    private void getExtraInforamtion(int id) {
        // Prepare the data to be send
        HashMap<String, String> data = new HashMap<>();
        data.put("episode_id", String.valueOf(id));
        // Call the episode API if available
        EpisodeData aggregateRequest = new EpisodeData(this);
        aggregateRequest.get(data, new GsonCallback<Episode>(){

            @Override
            public void onSuccess(Episode response) {
                SingleEpisodeActivity.this.setExtraData(response);
            }

            @Override
            public void onError(String err) {
                uiErrorManager
                        .setError("", err)
                        .setErrorStrategy(UIErrorManager.SNACKBAR);
            }
        });
    }
}
