package com.yellowman.tinwork.yourname.FilmDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.network.entity.TokenEntity;
import com.yellowman.tinwork.yourname.network.fetch.FetchToken;

public class FilmDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        getToken();
    }

    /**
     * Get Token
     *      Test if we have the token instance there so we don't have to reuse the token for the whole lifetime of the app
     */
    protected void getToken() {
        FetchToken fetch = FetchToken.getInstance(this.getApplicationContext());
        TokenEntity token = fetch.getToken();

        if (token != null) {
            Log.d("token value", token.getToken());
        } else {
            System.out.println("token is null");
        }
    }
}
