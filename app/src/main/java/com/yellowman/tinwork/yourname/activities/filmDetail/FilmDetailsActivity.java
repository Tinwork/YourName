package com.yellowman.tinwork.yourname.activities.filmDetail;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmContentFragment;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.api.Routes;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        //setToolbar();
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

        // set the image for the banner
        if (!serie.getBanner().isEmpty()) {
            Glide.with(this).load(Utils.buildMiscURI(Routes.IMG_PATH, serie.getBanner())).into(banner);
        } else {
            Glide.with(this).load(R.drawable.yourname_bg).into(banner);
        }

        // Call our fragment and notify that data is available
        if (!serie.getId().isEmpty()) {
            // Get actor
            HashMap<String, Parcelable> data = new HashMap<>();
            data.put("serie", serie);
            fg.notifyData(data);
        }
    }
}
