package com.yellowman.tinwork.yourname.activities.user;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.iface.ToolbarActionCallback;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.UIKit.misc.ToolbarManager;
import com.yellowman.tinwork.yourname.activities.login.LoginActivity;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by Marc Intha-amnouay on 05/01/2018.
 * Created by Didier Youn on 05/01/2018.
 * Created by Abdel-Atif Mabrouck on 05/01/2018.
 * Created by Antoine Renault on 05/01/2018.
 */

public class UserActivity extends AppCompatActivity implements FragmentCommunication {

    private ImageView badge;
    private LinearLayout container;
    private ToolbarManager toolbarManager;
    private TextView usernameTx;
    private AppBarLayout layout;
    private Helper helper;
    private ToolbarActionCallback callback;

    /**
     * On Create
     *
     * @param savedInstanceBundle Bundle
     */
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.user_layout_activity);
        initFragmentListeners();

        this.helper     = new Helper();
        // set the element here
        this.badge      = findViewById(R.id.badge);
        this.container  = findViewById(R.id.containerUser);
        this.usernameTx = findViewById(R.id.username);
        this.layout     = findViewById(R.id.app_bar_layout);
        // set the toolbar manager
        this.toolbarManager = new ToolbarManager(this);
        // set the params for the view
        initComponent();
    }


    @Override
    public void setParcelable(List<Series> parcel, String key) {

    }

    @Override
    public void fireFragmentEvent(HashMap<String, List<Series>> parcels) {

    }

    @Override
    public void initFragmentListeners() {
        FragmentListener frag = (FragmentListener) getSupportFragmentManager().findFragmentById(R.id.user_frag);
        frag.notifyData(null);
    }

    /**
     * On Create Options Menu
     *
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    /**
     * On Options Item Selected
     *
     * @param item Menu Item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.toolbarManager.toolbarItemSelectAction(item, callback);
        return true;
    }

    /**
     * Set Badge
     *      Set a badge
     *
     * @param username string username
     */
    private void setBadge(String username) {
        int usrSize = username.length();

        if (usrSize % 2 == 0) {
            // For the moment we only set it to glide
            Glide.with(this).load(R.drawable.kanahei).apply(RequestOptions.circleCropTransform()).into(badge);
        } else {
            Glide.with(this).load(R.drawable.totoro).apply(RequestOptions.circleCropTransform()).into(badge);
        }
    }

    /**
     * Init Component
     *
     */
    private void initComponent() {
        // Set the background color
        GradientGenerator gd = new GradientGenerator(this, null,  this.container);
        gd.buildBackgroundGradientColor();
        int colorID = gd.getRelatedColor();

        // set the toolbar to the gradient ouput color
        // init the toolbar
        Toolbar toolbar = toolbarManager.setToolbar().setTitle(getResources().getString(R.string.profile)).getToolbar();
        toolbar.setBackgroundColor(getResources().getColor(colorID));
        setSupportActionBar(toolbar);
        // set the layout bg
        layout.setBackgroundColor(getResources().getColor(colorID));
        // set the component
        setElement();
        // set the callback
        callback = () -> {
            CommonManager realmManager = new CommonManager();
            realmManager.destroyAll();

            AppUtils.saveSharedPreference(this, "yourname_token", "");
            AppUtils.saveSharedPreference(this, "username", "");
            AppUtils.saveSharedPreference(this, "accountID", "");

            // Redirect the user to the Login Activity
            helper.launchWithEmptyData(this, LoginActivity.class);
        };
    }

    /**
     * Set Element
     *
     */
    private void setElement() {
        String username = AppUtils.getSharedPreference(this, "username");

        if (username.isEmpty()) {
            username = "anonymous";
        }

        setBadge(username);
        String txt = String.format(getResources().getString(R.string.welcome), username);
        usernameTx.setText(txt);
    }
}
