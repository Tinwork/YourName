package com.yellowman.tinwork.yourname.networkTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.api.search.GetSerie;
import com.yellowman.tinwork.yourname.network.api.user.UserToken;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        listenButton();
    }

    /**
     * Test Fetch
     */
    protected void testFetch() {
        UserToken fetch = new UserToken(this.getApplicationContext());

        fetch.get(null, new GsonCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                // Save the token in the sharedPreference
                Utils.saveSharedPreference(NetworkActivity.this, "yourname_token", response.getToken());
                Log.d("Debug", "Token "+response.getToken());
            }

            public void onError(String err) {}
        });
    }

    /**
     * Test Refresh Token API
     */
    protected void testRefreshTokenAPI() {
        UserToken fetch = new UserToken(this.getApplicationContext());
        // Get back the token saved from shared preference
        String token = Utils.getSharedPreference(this, "yourname_token");

        fetch.refreshToken(token, new GsonCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                Log.d("Debug", response.getToken());
            }
            @Override
            public void onError(String t) {}
        });
    }

    /**
     * Test Get Series API
     */
    protected void testGetSeriesAPI() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name", "your name");

        SearchSeries search = new SearchSeries(this);
        search.get(payload, new GsonCallback<Search>() {
            @Override
            public void onSuccess(Search response) {
                String serie = response.getData().get(0).getSeriesName();
                Log.d("Debug", "Serie name for search API "+serie);
            }

            public void onError(String err) {}
        });
    }

    /**
     * Test Placeholder URI
     * @protected
     * @void
     */
    protected void testPlaceholderURI() {
        //String[] params = {"328840"};
        String[] single = {"foo"};

        HashMap<String, String> params = new HashMap<>();
        params.put("series_id", "328840");

        GetSerie serie = new GetSerie(this);
        serie.get(params, new GsonCallback<Search>() {
            @Override
            public void onSuccess(Search response) {

                Log.d("Debug", "Serie name for search API "+response.getData());
            }

            public void onError(String err) {}
        });

        //String paramsURI = Utils.buildPlaceholderUrl(Routes.SERIES, params, null);
        //String paramURI  = Utils.buildPlaceholderUrl(Routes.SEARCH_SERIES, single, "yourname/lyly/mama");
        //Log.d("Debug", paramsURI);
        //Log.d("Debug", paramURI);
    }

    /**
     * Listen Button
     * @void
     */
    public void listenButton() {
        // Get the buttons
        Button refresh     = (Button) findViewById(R.id.refreshToken);
        Button getToken    = (Button) findViewById(R.id.getToken);
        Button getSeries   = (Button) findViewById(R.id.getSeries);
        Button placeHolder = (Button) findViewById(R.id.testPlaceHolder);

        // Add a listeners
        refresh.setOnClickListener(event -> NetworkActivity.this.testRefreshTokenAPI());
        getToken.setOnClickListener(event -> NetworkActivity.this.testFetch());
        getSeries.setOnClickListener(event -> NetworkActivity.this.testGetSeriesAPI());
        placeHolder.setOnClickListener(event -> NetworkActivity.this.testPlaceholderURI());
    }

}
