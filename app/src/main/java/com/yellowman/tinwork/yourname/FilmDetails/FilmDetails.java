package com.yellowman.tinwork.yourname.FilmDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.utils.Utils;

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
        String token = Utils.getSharedPreference(this, "yourname_token");
        Log.d("token in second thread", token);
    }
}
