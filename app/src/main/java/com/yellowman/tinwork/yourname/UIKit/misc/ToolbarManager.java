package com.yellowman.tinwork.yourname.UIKit.misc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.activities.user.UserActivity;

/**
 * Created by Marc Intha-amnouay on 30/12/2017.
 * Created by Didier Youn on 30/12/2017.
 * Created by Abdel-Atif Mabrouck on 30/12/2017.
 * Created by Antoine Renault on 30/12/2017.
 */

public class ToolbarManager {

    private AppCompatActivity activity;
    private Toolbar toolbar;
    protected EditText searchBar;
    protected Menu menu;

    /**
     * Toolbar Manager::Constructor
     *
     * @param instance
     */
    public ToolbarManager(AppCompatActivity instance) {
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
     * Set Menu Component
     *
     * @param menu
     */
    public void setMenuComponent(Menu menu) {
        this.menu = menu;
    }
}
