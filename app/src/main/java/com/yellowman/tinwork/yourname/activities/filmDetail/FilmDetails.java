package com.yellowman.tinwork.yourname.activities.filmDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.model.Series;

public class FilmDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        getIntentData();
    }

    protected void getIntentData() {
        Intent intent = getIntent();
        Series serie = intent.getParcelableExtra("Entity");

        Log.d("Debug", "Series name is "+serie.getSeriesName());
    }
}
