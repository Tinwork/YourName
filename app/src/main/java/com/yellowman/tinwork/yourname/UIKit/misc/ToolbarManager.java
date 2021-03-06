package com.yellowman.tinwork.yourname.UIKit.misc;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
    private Spinner spinner;

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
            case R.id.shared_item:
                callback.doItemAction();
                return true;
            case R.id.favorite_item:
                callback.doItemAction();
                toggleElementVisible(false, true);
                return true;
            case R.id.favorite_outline_item:
                callback.doItemAction();
                toggleElementVisible(true, false);
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

    /**
     * Toggle Element Visible
     *
     * @param v int
     * @param h int
     */
    private void toggleElementVisible(Boolean v, Boolean h) {
        Menu menu = toolbar.getMenu();
        MenuItem item_o = menu.findItem(R.id.favorite_item);
        MenuItem item_h = menu.findItem(R.id.favorite_outline_item);

        item_o.setVisible(v);
        item_h.setVisible(h);
    }

    public void setMenuSpinner() {
        Menu menu = toolbar.getMenu();

        spinner = (Spinner) menu.findItem(R.id.spinner).getActionView();

        if (spinner == null) {
            return;
        }

        // hydrate the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getApplicationContext(), R.array.rating_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public Spinner getSpinner() {
        return this.spinner;
    }
}
