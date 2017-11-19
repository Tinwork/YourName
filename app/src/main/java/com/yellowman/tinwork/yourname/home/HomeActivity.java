package com.yellowman.tinwork.yourname.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.network.fetch.FetchToken;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        testFetch();
    }


    protected void testFetch() {
        FetchToken fetch = FetchToken.getInstance(this.getApplicationContext());
        fetch.makeToken();
        //fetch.getToken();
    }
}
