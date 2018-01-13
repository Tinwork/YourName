package com.yellowman.tinwork.yourname.UIKit.misc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.activities.login.LoginActivity;
import com.yellowman.tinwork.yourname.activities.user.UserActivity;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

/**
 * Sometimes you have to stop over thinking and make the big step
 * otherwise you'll keep thinking and do nothing...
 *
 * Created by Marc Intha-amnouay on 30/12/2017.
 * Created by Didier Youn on 30/12/2017.
 * Created by Abdel-Atif Mabrouck on 30/12/2017.
 * Created by Antoine Renault on 30/12/2017.
 */

public class ToolbarManager {

    private AppCompatActivity activity;
    private Toolbar toolbar;
    protected Menu menu;
    protected Helper helper;

    /**
     * Toolbar Manager::Constructor
     *
     * @param instance
     */
    public ToolbarManager(AppCompatActivity instance) {
        this.helper   = new Helper();
        this.activity = instance;
    }

    /**
     * Set Toolbar
     *
     */
    public ToolbarManager setToolbar() {
        this.toolbar = (Toolbar) activity.findViewById(R.id.mytoolbar);

        return this;
    }

    /**
     * Set Title
     *
     * @param title
     */
    public ToolbarManager setTitle(String title) {
        toolbar.setTitle(title);

        return this;
    }

    /**
     * Get Toolbar
     *
     * @return
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Toolbar Item Select Action
     *
     * @param item
     * @return
     */
    public Boolean toolbarItemSelectAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                accountAction();
                return true;
            case R.id.logout:
                logoutAction();
                return true;
            default:
                return true;
        }
    }

    /**
     * Account Action
     *
     * @return Boolean
     */
    public Boolean accountAction() {
        Intent intent = new Intent(this.activity, UserActivity.class);
        this.activity.startActivity(intent);

        return true;
    }

    /**
     * Logout Action
     *
     * @return boolean
     */
    public Boolean logoutAction() {
        CommonManager realmManager = new CommonManager();
        realmManager.destroyAll();

        AppUtils.saveSharedPreference(activity, "yourname_token", "");
        AppUtils.saveSharedPreference(activity, "username", "");
        AppUtils.saveSharedPreference(activity, "accountID", "");

        // Redirect the user to the Login Activity
        helper.launchWithEmptyData(activity, LoginActivity.class);

        return true;
    }

    /**
     * Set Menu Component
     *
     * @param menu
     */
    public void setMenuComponent(Menu menu) {
        this.menu = menu;
    }
}
