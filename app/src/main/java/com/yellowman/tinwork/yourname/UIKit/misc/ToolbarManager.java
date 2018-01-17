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
import com.yellowman.tinwork.yourname.UIKit.iface.ToolbarActionCallback;
import com.yellowman.tinwork.yourname.activities.login.LoginActivity;
import com.yellowman.tinwork.yourname.activities.user.UserActivity;
import com.yellowman.tinwork.yourname.realm.decorator.SeriesRealmDecorator;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

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
    private HashMap<String, String> data = new HashMap<>();
    protected Helper helper;

    /**
     * Toolbar Manager::Constructor
     *
     * @param instance Activity
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
     * @param title String
     */
    public ToolbarManager setTitle(String title) {
        toolbar.setTitle(title);

        return this;
    }

    /**
     * Get Toolbar
     *
     * @return Toolbar
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Toolbar Item Select Action
     *
     * /!\ Might have been better to pass callback in order to make actions
     *
     * @param item MenuItem
     * @return boolean
     */
    public Boolean toolbarItemSelectAction(MenuItem item, ToolbarActionCallback callback) {
        switch (item.getItemId()) {
            case R.id.account:
                accountAction();
                return true;
            case R.id.reload:
            case R.id.logout:
            case R.id.favorite_item:
            case R.id.shared_item:
                callback.doItemAction();
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
    private Boolean accountAction() {
        Intent intent = new Intent(this.activity, UserActivity.class);
        this.activity.startActivity(intent);

        return true;
    }
}
