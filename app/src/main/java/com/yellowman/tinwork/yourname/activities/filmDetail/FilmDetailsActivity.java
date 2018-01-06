package com.yellowman.tinwork.yourname.activities.filmDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmContentFragment;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmEpisodesFragment;
import com.yellowman.tinwork.yourname.entity.Image;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.api.series.ImagesSeries;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * MERRY CHRISTMAS !!!!! ✨ L~~~~~~~~~MM~~~~~~~~~L ✨
 *
 * Created by Marc Intha-amnouay a few months.
 * Created by Didier Youn a few months.
 * Created by Abdel-Atif Mabrouck a few months.
 * Created by Antoine Renault a few months.
 */

public class FilmDetailsActivity extends AppCompatActivity {

    protected ImageView banner;
    protected FragmentListener fg;
    protected FragmentListener fe;
    private GradientGenerator gd;
    private UIErrorManager uiErrorManager;

    /**
     * On Create
     *
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        setToolbar();
        initActivityComponent();
        getIntentData();
    }

    /**
     * Init Activity Component
     *
     */
    protected void initActivityComponent() {
        this.banner = findViewById(R.id.film_banner);
        // Create fragment listener
        this.fg = (FilmContentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_film_detail);
        this.fe = (FilmEpisodesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_episodes_detail);

        this.gd = new GradientGenerator(this, findViewById(R.id.film_details_view), null);
        gd.buildBackgroundGradientColor();
        this.uiErrorManager = new UIErrorManager(this);
    }

    /**
     * Set ToolBar
     */
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle("TRENDING");
        setSupportActionBar(toolbar);
    }

    /**
     * Get Intent Data
     *
     *
     */
    protected void getIntentData() {
        Intent intent = getIntent();
        Series serie = intent.getParcelableExtra("Entity");

        Log.d("Debug", "Series name is "+serie.getSeriesName());

        // Call our fragment and notify that data is available
        if (!serie.getId().isEmpty()) {
            // Get actor
            List<Series> data = new ArrayList<>();
            data.add(serie);
            fg.notifyData(data);
            fe.notifyData(data);
            getImageForSerie(serie.getId());
        }
    }

    /**
     * Get Image For Serie
     *
     * @param id id_seriess
     */
    private void getImageForSerie(String id) {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("series_id", id);
        ImagesSeries query = new ImagesSeries(this);
        query.get(payload, new GsonCallback<Image[]>() {

            @Override
            public void onSuccess(Image[] response) {
                // set the image for the banner
                if (response.length > 0) {
                    Glide.with(FilmDetailsActivity.this).load(AppUtils.buildMiscURI(Routes.IMG_PATH, response[0].getFileName())).into(banner);
                } else {
                    Glide.with(FilmDetailsActivity.this).load(R.drawable.yourname_bg).into(banner);
                }
            }

            @Override
            public void onError(String err) {
                if (err != null) {
                    // Handle error here
                    uiErrorManager
                            .setError("", err)
                            .setErrorStrategy(UIErrorManager.SNACKBAR);
                }

                Glide.with(FilmDetailsActivity.this).load(R.drawable.yourname_bg).into(banner);
            }
        });
    }
}
