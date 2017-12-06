package com.yellowman.tinwork.yourname.home;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.home.fragments.TrendingFragment;
import com.yellowman.tinwork.yourname.login.LoginActivity;
import com.yellowman.tinwork.yourname.networkTest.NetworkActivity;
import com.yellowman.tinwork.yourname.utils.Utils;

public class HomeActivity extends AppCompatActivity implements TrendingFragment.OnFragmentInteractionListener{

    private GradientGenerator gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbar();
        initView();
        isUserSubscribe();
        launchActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Init View
     */
    private void initView() {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        this.gd = new GradientGenerator(this, mainLayout);
        this.gd.buildBackgroundGradientColor();
    }

    /**
     * Set ToolBar
     */
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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


    /**
     * Launch Activity
     */
    protected void launchActivity() {
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NetworkActivity.class);
            startActivity(intent);
        });
    }

    /**
     *
     * @param savedInstanceBundle
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceBundle) {
        super.onRestoreInstanceState(savedInstanceBundle);

        // restore data
        Log.d("data restored username", savedInstanceBundle.getString("username"));
        Log.d("restore token", savedInstanceBundle.getString("yourname_token"));

    }

    /**
     *
     * @param savedInstancedBundle
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstancedBundle) {
        Log.d("save user", "mintha");
        savedInstancedBundle.putString("username", "mintha");
        savedInstancedBundle.putString("yourname_token", Utils.getSharedPreference(this, "yourname_token"));

        // call the super method to save the view
        super.onSaveInstanceState(savedInstancedBundle);
    }

    public void onFragmentInteraction(Uri uri) {

    }

}
