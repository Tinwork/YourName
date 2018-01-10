package com.yellowman.tinwork.yourname.networkTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.entity.IdSeries;
import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.entity.Episode;
import com.yellowman.tinwork.yourname.model.Serie.SerieWrapper;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.series.AddFavorites;
import com.yellowman.tinwork.yourname.network.api.series.ListActors;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.api.series.ListEpisodes;
import com.yellowman.tinwork.yourname.network.api.series.SingleSerie;
import com.yellowman.tinwork.yourname.network.api.user.UserToken;
import com.yellowman.tinwork.yourname.utils.AppUtils;

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
                AppUtils.saveSharedPreference(NetworkActivity.this, "yourname_token", response.getToken());
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
        String token = AppUtils.getSharedPreference(this, "yourname_token");

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
     * Test Get OneSerie API
     */
    protected void testGetSerieAPI() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("series_id", "328840");

        SingleSerie serie = new SingleSerie(this);
        serie.get(payload, new GsonCallback<SerieWrapper>() {
            @Override
            public void onSuccess(SerieWrapper response) {
                Log.d("Debug", "Serie name for search API "+response.getData().getSeriesName());
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
        String[] params = {"bar", "lol"};
        String[] single = {"foo"};

        String paramsURI = AppUtils.buildPlaceholderUrl(Routes.SEARCH_SERIES, params, null);
        String paramURI  = AppUtils.buildPlaceholderUrl(Routes.SEARCH_SERIES, single, "yourname/lyly/mama");
        Log.d("Debug", paramsURI);
        Log.d("Debug", paramURI);
    }

    /**
     * Test Get Actor API
     * @protected
     * @void
     */
    protected void testGetActorsAPI() {
        String[] single = {"foo"};

        HashMap<String, String> params = new HashMap<>();
        params.put("series_id", "328840");

        ListActors actors = new ListActors(this);
        actors.get(params, new GsonCallback<Actor[]>() {
            @Override
            public void onSuccess(Actor[] response) {
                Log.d("Debug", "Serie name for search API "+response.toString());
            }

            public void onError(String err) {}
        });
    }

    /**
     * Test API Request [GET]
     * List episodes from series by ID
     */
    protected void testGetEpisodesFromSeriesById() {
        HashMap<String, String> params = new HashMap<>();

        params.put("series_id", "252534");

        ListEpisodes episodes = new ListEpisodes(this);
        episodes.get(params, new GsonCallback<Episode[]>() {
            @Override
            public void onSuccess(Episode[] response) {
                Log.d("Debug", "Serie name for search API " + response.toString());
            }

            public void onError(String err) {}
        });
    }

    /**
     * Test API Request [Put]
     * Add favorites from series by ID
     */
    protected void testPutAddFavoritesBySeriesId() {
        HashMap<String, String> params = new HashMap<>();
        params.put("series_id", "252534");

        AddFavorites favorites = new AddFavorites(this);
        favorites.get(params, new GsonCallback<IdSeries[]>() {
            @Override
            public void onSuccess(IdSeries[] response) {
                Log.d("Debug", "Serie name for search API " + response);
            }

            public void onError(String err) {
                Log.d("Debug test", "Test "+err.toString());
            }
        });

        /*DeleteSerie series = new DeleteSerie(this);
        series.get(params, new GsonCallback<IdSeries[]>() {
            @Override
            public void onSuccess(IdSeries[] response) {
                Log.d("Debug", "Serie name for search API " + response);
            }

            public void onError(String err) {
                Log.d("Debug test", "Test "+err.toString());
            }
        });*/
    }

    /**
     * Listen Button
     * @void
     */
    public void listenButton() {
        // Get the buttons
        Button refresh               = (Button) findViewById(R.id.refreshToken);
        Button getToken              = (Button) findViewById(R.id.getToken);
        Button getSeries             = (Button) findViewById(R.id.getSeries);
        Button getSerie              = (Button) findViewById(R.id.getOneSeries);
        Button placeHolder           = (Button) findViewById(R.id.testPlaceHolder);
        Button getActors             = (Button) findViewById(R.id.getActors);
        Button getEpisodesFromSeries = (Button) findViewById(R.id.getEpisodesFromSeries);
        Button putFavoriteFromSeries = (Button) findViewById(R.id.putFavoritesFromSeries);

        // Add a listeners
        refresh.setOnClickListener(event -> NetworkActivity.this.testRefreshTokenAPI());
        getToken.setOnClickListener(event -> NetworkActivity.this.testFetch());
        getSeries.setOnClickListener(event -> NetworkActivity.this.testGetSeriesAPI());
        getSerie.setOnClickListener(event -> NetworkActivity.this.testGetSerieAPI());
        placeHolder.setOnClickListener(event -> NetworkActivity.this.testPlaceholderURI());
        getActors.setOnClickListener(event -> NetworkActivity.this.testGetActorsAPI());
        getEpisodesFromSeries.setOnClickListener(event -> NetworkActivity.this.testGetEpisodesFromSeriesById());
        putFavoriteFromSeries.setOnClickListener(event -> NetworkActivity.this.testPutAddFavoritesBySeriesId());
    }

}

