package com.yellowman.tinwork.yourname.activities.filmDetail;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.iface.ToolbarActionCallback;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.UIKit.misc.ToolbarManager;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmContentFragment;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmEpisodesFragment;
import com.yellowman.tinwork.yourname.application.YourName;
import com.yellowman.tinwork.yourname.entity.Image;
import com.yellowman.tinwork.yourname.entity.Rating;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.api.series.ImagesSeries;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.realm.decorator.FavoriteRealmDecorator;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * MERRY CHRISTMAS !!!!! ✨ L~~~~~~~~~MM~~~~~~~~~L ✨
 *
 * Created by Marc Intha-amnouay a few months.
 * Created by Didier Youn a few months.
 * Created by Abdel-Atif Mabrouck a few months.
 * Created by Antoine Renault a few months.
 */

public class FilmDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Inject
    @Named("FetchFavorite")
    FavoriteRealmDecorator realmDecorator;

    @Inject
    @Named("FetchUserSetRatingSeries")
    Fetch setRatingSeries;

    protected ImageView banner;
    protected FragmentListener fg;
    protected FragmentListener fe;
    private GradientGenerator gd;
    private UIErrorManager uiErrorManager;
    private ToolbarManager toolbarManager;
    private ToolbarActionCallback callbackSetFavorite;
    private ToolbarActionCallback callbackSetUnfavorite;
    private ToolbarActionCallback callbackShared;
    private Series serie;
    private int check;

    /**
     * On Create
     *
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        this.check = 0;
        ((YourName) getApplicationContext()).getmNetworkComponent().inject(this);
        initActivityComponent();
        getIntentData();
        setToolbar();
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
        int color = gd.buildBackgroundGradientColor();
        this.uiErrorManager = new UIErrorManager(this);
        // set the toolbar
        this.toolbarManager = new ToolbarManager(this);
        AppUtils.colorizeStatusBar(this.getWindow(), this, color);

        // Set collapsing toolbar layout scrim color
        CollapsingToolbarLayout cl = findViewById(R.id.collapsing_toolbar_layout);
        Utils.setCollapsingToolbarScrimColor(cl, ContextCompat.getColor(this, color));
    }

    /**
     * Set ToolBar
     */
    private void setToolbar() {
        Toolbar toolbar = toolbarManager.setToolbar().getToolbar();
        setSupportActionBar(toolbar);
    }

    /**
     * On Create Options Menu
     *
     * @param menu Menu
     * @return Boolean
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);

        if (!serie.getFavorite()) {
            menu.findItem(R.id.favorite_outline_item).setVisible(true);
        } else {
            menu.findItem(R.id.favorite_item).setVisible(true);
        }

        toolbarManager.setMenuSpinner();
        Spinner spinner = toolbarManager.getSpinner();
        spinner.setOnItemSelectedListener(this);

        return true;
    }

    /**
     *
     * @param item MenuItem
     * @return Boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        String username = AppUtils.getSharedPreference(this, "username");
        String accountID= AppUtils.getSharedPreference(this, "accountID");

        if (item.getItemId() == R.id.shared_item) {
            toolbarManager.toolbarItemSelectAction(item, callbackShared);
            return true;
        }

        if (username.isEmpty() || accountID.isEmpty()) {
            uiErrorManager
                    .setError("401", "Anonymous can't save favorite series")
                    .setErrorStrategy(UIErrorManager.SNACKBAR);

            return true;
        }

        if (item.getItemId() == R.id.favorite_item) {
            toolbarManager.toolbarItemSelectAction(item, callbackSetUnfavorite);
        } else if (item.getItemId() == R.id.favorite_outline_item){
            toolbarManager.toolbarItemSelectAction(item, callbackSetFavorite);
        }

        return true;
    }

    /**
     * Get Intent Data
     *
     */
    private void getIntentData() {
        Intent intent = getIntent();
        serie = intent.getParcelableExtra("Entity");

        // Call our fragment and notify that data is available
        if (!serie.getId().isEmpty()) {
            // Get actor
            List<Series> data = new ArrayList<>();
            data.add(serie);
            fg.notifyData(data);
            fe.notifyData(data);
            getImageForSerie(serie.getId());

            // create extra data for the toolbar...
            callbackSetFavorite = () -> {
                realmDecorator.setSerieAsFavorite(serie);
            };

            callbackSetUnfavorite = () -> {
                realmDecorator.removeFromFavorite("id", serie.getId());
            };

            callbackShared = () -> {
                String text = getString(
                        R.string.shared_action_template,
                        serie.getSeriesName(),
                        Routes.TVDB_SITE_URL+serie.getId()
                );

                Helper helper = new Helper();
                helper.sendIntentToChooser(AppUtils.buildShareIntent(text), this);
            };
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
                    Glide.with(FilmDetailsActivity.this).load(R.drawable.totoro_error).into(banner);
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

                Glide.with(FilmDetailsActivity.this.getApplicationContext()).load(R.drawable.totoro_error).into(banner);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (check == 0) {
            check = 1;
            return;
        }

        String rate = (String) parent.getItemAtPosition(position);

        HashMap<String, String> payload = new HashMap<>();
        payload.put("serie_id", serie.getId());
        payload.put("rating", String.valueOf(rate));

        // set update
        setRatingSeries.get(payload, new GsonCallback<Rating[]>() {
            @Override
            public void onSuccess(Rating[] response) {
                uiErrorManager
                        .setError("", "Update series rating")
                        .setErrorStrategy(UIErrorManager.SNACKBAR);
            }

            @Override
            public void onError(String err) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
