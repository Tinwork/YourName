package com.yellowman.tinwork.yourname.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       testFetch();
    }


    protected void testFetch() {
        Fetch fetch = new Fetch(this.getApplicationContext());

        HashMap<String, String> userProps = new HashMap<String, String>();
        userProps.put("route", "https://api.thetvdb.com/login");
        userProps.put("apiKey", "4EA2639295D9AFEB");
        userProps.put("username", "mintha");
        userProps.put("userKey", "103BDD2CB28301F3");

        fetch.Post(userProps);
    }
}
