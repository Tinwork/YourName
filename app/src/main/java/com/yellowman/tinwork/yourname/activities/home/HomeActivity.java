package com.yellowman.tinwork.yourname.activities.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.UIKit.misc.ToolbarManager;
import com.yellowman.tinwork.yourname.activities.home.fragments.FavoriteFragment;
import com.yellowman.tinwork.yourname.activities.home.fragments.PopularFragment;
import com.yellowman.tinwork.yourname.activities.home.fragments.TrendingFragment;
import com.yellowman.tinwork.yourname.activities.login.LoginActivity;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeActivity extends AppCompatActivity implements FragmentCommunication {

    protected ArrayList<FragmentListener> listeners;
    protected ToolbarManager toolbarManager;
    private GradientGenerator gd;
    private HashMap<String, Parcelable> parcelMap;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbar();

        // Init the view by adding bg colors, status bar..
        initView();
        isUserSubscribe();

        if (savedInstanceState == null) {
            fireFragmentEvent(null);
        }
    }

    /**
     * On Create Options Menu
     * @param menu
     * @return
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

        // save the toolbar
        toolbarManager.setMenuComponent(menu);
        return true;
    }

    /**
     * On Options Item Selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // return toolbarManager.toolbarItemSelectAction(item);
       return true;
    }

    /**
     *
     * @param parcel
     */
    @Override
    public void setParcelable(Parcelable parcel, String key) {
        if (parcelMap == null) {
            parcelMap = new HashMap<>();
        }

        Log.d("Debug", "Set parcel");
        Log.d("Debug", "Parcel list is empty ?? "+parcelMap.isEmpty());
        parcelMap.put(key, parcel);
    }

    /**
     *
     * @param savedInstanceBundle
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceBundle) {
        super.onRestoreInstanceState(savedInstanceBundle);

        // restore data
        if (savedInstanceBundle != null) {
            parcelMap = (HashMap<String, Parcelable>) savedInstanceBundle.getSerializable("HomeFragmentData");
            fireFragmentEvent(parcelMap);
        } else {
            Log.d("Debug", "Notify data w/o payload");
            fireFragmentEvent(null);
        }
    }

    /**
     *
     * @param savedInstancedBundle
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstancedBundle) {
        savedInstancedBundle.putString("username", "mintha");
        savedInstancedBundle.putString("yourname_token", Utils.getSharedPreference(this, "yourname_token"));
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
    public void initFragmentListeners() {
        TrendingFragment trFg = (TrendingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_trending);
        PopularFragment  poFg = (PopularFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_popular);
        FavoriteFragment faFg = (FavoriteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_favorite);

        // Put the listeners in the ArrayList which will be used later..
        listeners.add((FragmentListener) trFg);
        listeners.add((FragmentListener) poFg);
        listeners.add((FragmentListener) faFg);
    }

    /**
     * Fire Fragment Event
     *
     * @param parcels
     * @TODO should upgrade min version of the App
     */
    public void fireFragmentEvent(@Nullable HashMap<String, Parcelable> parcels) {
        listeners.forEach(listener -> {
            listener.notifyData(parcels);
        });
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
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        this.gd = new GradientGenerator(this, null, mainLayout);
        int color = this.gd.buildBackgroundGradientColor();

        // Set the bg color of the status bar
        Utils.colorizeStatusBar(this.getWindow(), this, color);

        // Prepare the listeners
        initFragmentListeners();
    }

    /**
     * Set ToolBar
     */
    private void setToolbar() {
        this.toolbarManager = new ToolbarManager(this);
        Toolbar toolbar = toolbarManager.setToolbar().setTitle("TRENDING").getToolbar();
        setSupportActionBar(toolbar);
    }

    /**
     * Is User Subscribe
     */
    private void isUserSubscribe() {
        String token  = Utils.getSharedPreference(this, "yourname_token");

        // Create an intent to redirect to an other view
        Intent view = new Intent(this, LoginActivity.class);

        if (token.isEmpty()) {
            //view.setClass(this, LoginActivity.class);
            startActivity(view);
        }
    }


}
