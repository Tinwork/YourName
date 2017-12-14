package com.yellowman.tinwork.yourname.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.communication.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.communication.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.home.fragments.TrendingFragment;
import com.yellowman.tinwork.yourname.login.LoginActivity;
import com.yellowman.tinwork.yourname.utils.Utils;

public class HomeActivity extends AppCompatActivity implements TrendingFragment.OnFragmentInteractionListener, FragmentCommunication{

    private GradientGenerator gd;
    private Parcelable searchParcel;
    private TrendingFragment trendFrag;
    private FragmentListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbar();

        // Init the view by adding bg colors, status bar..
        initView();
        isUserSubscribe();

        if (savedInstanceState == null) {
            listener.notifyData(null);
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
        return true;
    }

    /**
     * On Options Item Selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param parcel
     */
    @Override
    public void setParcelable(Parcelable parcel) {
        Log.d("Debug", "Set parcel");
        searchParcel = parcel;
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
            searchParcel = savedInstanceBundle.getParcelable("Search::entity");
            listener.notifyData(searchParcel);
        } else {
            Log.d("Debug", "Notify data w/o payload");
            listener.notifyData(null);
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
        savedInstancedBundle.putParcelable("Search::entity", searchParcel);

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
     * Init View
     */
    private void initView() {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        this.gd = new GradientGenerator(this, null, mainLayout);
        int color = this.gd.buildBackgroundGradientColor();
        Utils.colorizeStatusBar(this.getWindow(), this, color);

        // Get the fragment
        trendFrag = (TrendingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_trending);
        listener  = (FragmentListener) trendFrag;
    }

    /**
     * Set ToolBar
     */
    private void setToolbar() {
        Log.d("Debug", "set toolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbar.setTitle("TRENDING");
        setSupportActionBar(toolbar);
    }

    /**
     * Is User Subscribe
     */
    private void isUserSubscribe() {
        String token  = Utils.getSharedPreference(this, "yourname_token");

        // Create an intent to redirect to an other view
        Intent view = new Intent();

        if (token.isEmpty()) {
            view.setClass(this, LoginActivity.class);
            startActivity(view);
        }
    }


    public void onFragmentInteraction(Uri uri) {}

}
