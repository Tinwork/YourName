package com.yellowman.tinwork.yourname.activities.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.iface.ToolbarActionCallback;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.UIKit.misc.ToolbarManager;
import com.yellowman.tinwork.yourname.activities.home.fragments.FavoriteFragment;
import com.yellowman.tinwork.yourname.activities.home.fragments.RandomFragment;
import com.yellowman.tinwork.yourname.activities.home.fragments.TrendingFragment;
import com.yellowman.tinwork.yourname.activities.login.LoginActivity;
import com.yellowman.tinwork.yourname.application.YourName;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.realm.decorator.SeriesRealmDecorator;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Marc Intha-amnouay two months ago.
 * Created by Didier Youn two months ago.
 * Created by Abdel-Atif Mabrouck two months ago.
 * Created by Antoine Renault two months ago.
 */
public class HomeActivity extends AppCompatActivity implements FragmentCommunication {

    @Inject
    @Named("SearchSeries")
    SeriesRealmDecorator searchSeries;

    protected ArrayList<FragmentListener> listeners;
    protected ToolbarManager toolbarManager;
    private GradientGenerator gd;
    private HashMap<String, List<Series>> parcelMap;
    private ToolbarActionCallback cb;

    /**
     *
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((YourName) this.getApplicationContext()).getmNetworkComponent().inject(this);

        setToolbar();

        // Init the view by adding bg colors, status bar..
        isUserSubscribe(savedInstanceState);
    }

    /**
     * On Create Options Menu
     * @param menu Menu
     *
     * @return Boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Associate the searchbar with the activity
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        return true;
    }

    /**
     * On Options Item Selected
     * @param item an item
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       toolbarManager.toolbarItemSelectAction(item, cb);

       return true;
    }

    /**
     * Set Parcelable
     *
     * @param parcel List<Series> (used to be a parcel)
     * @param key identifier
     */
    @Override
    public void setParcelable(List<Series> parcel, String key) {
        if (parcelMap == null) {
            parcelMap = new HashMap<>();
        }

        parcelMap.put(key, parcel);
    }

    /**
     * On Restore Instance State
     *
     * @param savedInstanceBundle bundle
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceBundle) {
        super.onRestoreInstanceState(savedInstanceBundle);

        // restore data
        if (savedInstanceBundle != null) {
            parcelMap = (HashMap<String, List<Series>>) savedInstanceBundle.getSerializable("HomeFragmentData");
            fireFragmentEvent(parcelMap);
        } else {
            fireFragmentEvent(null);
        }
    }

    /**
     * On Save Instance State
     *
     * @param savedInstancedBundle bundle
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstancedBundle) {
        savedInstancedBundle.putString("username", "mintha");
        savedInstancedBundle.putString("yourname_token", AppUtils.getSharedPreference(this, "yourname_token"));
        savedInstancedBundle.putSerializable("HomeFragmentData", parcelMap);

        // call the super method to save the view
        super.onSaveInstanceState(savedInstancedBundle);
    }

    /**
     * On Back Pressed
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Init Fragment Listeners
     *      Listeners are used by each of these 3 fragments
     */
    @Override
    public void initFragmentListeners() {
        TrendingFragment trFg = (TrendingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_trending);
        RandomFragment poFg = (RandomFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_popular);
        FavoriteFragment faFg = (FavoriteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_favorite);

        // Put the listeners in the ArrayList which will be used later..
        listeners.add(trFg);
        listeners.add(poFg);
        listeners.add(faFg);
    }

    /**
     * Fire Fragment Event
     *
     * @param parcels Hashmap
     */
    @Override
    public void fireFragmentEvent(HashMap<String, List<Series>> parcels) {
        int idx = 0;
        String[] parcelID = {"trending", "popular", "favorite"};

        for (FragmentListener listener: listeners) {
            if (parcels == null) {
                listener.notifyData(null);
            } else {
                List<Series> data = parcels.get(parcelID[idx]);
                listener.notifyData(data);
                idx++;
            }
        }
    }

    /**
     * Init View
     *      Init the Activity
     */
    private void initView() {
        // Parcel map
        this.parcelMap = new HashMap<>();
        this.listeners = new ArrayList<>();

        // Set the gradient background color to the LinearLayout
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        this.gd = new GradientGenerator(this, null, mainLayout);
        int color = this.gd.buildBackgroundGradientColor();

        // Set the bg color of the status bar
        AppUtils.colorizeStatusBar(this.getWindow(), this, color);

        // Prepare the listeners
        initFragmentListeners();
        // Flush the realm data except the favorite
        searchSeries.flushPreviousSeries();
    }

    /**
     * Set ToolBar
     */
    private void setToolbar() {
        this.toolbarManager = new ToolbarManager(this);
        Toolbar toolbar = toolbarManager.setToolbar().setTitle("YourName").getToolbar();
        setSupportActionBar(toolbar);
    }

    /**
     * Is User Subscribe
     */
    private void isUserSubscribe(Bundle savedInstanceState) {
        cb = () -> {
            fireFragmentEvent(null);
        };

        String token  = AppUtils.getSharedPreference(this, "yourname_token");

        // Create an intent to redirect to an other view
        Intent view = new Intent(this, LoginActivity.class);

        if (token.isEmpty()) {
            startActivity(view);
            finish();
        } else {
            initView();

            if (savedInstanceState == null) {
              fireFragmentEvent(null);
            }
        }
    }
}
