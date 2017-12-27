package com.yellowman.tinwork.yourname.activities.filmDetail;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmContentFragment;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmEpisodesFragment;
import com.yellowman.tinwork.yourname.entity.Image;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.api.series.ImagesSeries;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

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
    private int dpi;

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
     * @void
     */
    protected void initActivityComponent() {
        this.banner = findViewById(R.id.film_banner);
        // Create fragment listener
        this.fg = (FilmContentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_film_detail);
        this.fe = (FilmEpisodesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_episodes_detail);

        this.gd = new GradientGenerator(this, findViewById(R.id.film_details_view), null);
        gd.buildBackgroundGradientColor();
    }

    /**
     * Set ToolBar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbar.setTitle("TRENDING");
        setSupportActionBar(toolbar);
    }

    /**
     * Get Intent Data
     *
     * @void
     */
    protected void getIntentData() {
        Intent intent = getIntent();
        Series serie = intent.getParcelableExtra("Entity");

        Log.d("Debug", "Series name is "+serie.getSeriesName());

        // Call our fragment and notify that data is available
        if (!serie.getId().isEmpty()) {
            // Get actor
            HashMap<String, Parcelable> data = new HashMap<>();
            data.put("serie", serie);
            fg.notifyData(data);
            fe.notifyData(data);
            getImageForSerie(serie.getId());
        }
    }

    /**
     * Get Image For Serie
     *
     * @param id
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
                    Glide.with(FilmDetailsActivity.this).load(Utils.buildMiscURI(Routes.IMG_PATH, response[0].getFileName())).into(banner);
                } else {
                    Glide.with(FilmDetailsActivity.this).load(R.drawable.yourname_bg).into(banner);
                }
            }

            @Override
            public void onError(String err) {
                // Handle error here
                Log.d("Error", err);
            }
        });
    }
}
