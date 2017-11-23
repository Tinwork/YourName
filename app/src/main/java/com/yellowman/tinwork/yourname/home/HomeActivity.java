package com.yellowman.tinwork.yourname.home;

import android.content.Intent;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.yellowman.tinwork.yourname.filmDetails.FilmDetails;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.networkTest.NetworkActivity;
import com.yellowman.tinwork.yourname.utils.Utils;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        launchActivity();
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


}
