package com.yellowman.tinwork.yourname.activities.EpisodeDetail;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.entity.Episode;

import java.util.Iterator;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class EpisodeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);
        getIntentData();
    }

    /**
     * Get Intent Data
     *
     * @void
     */
    protected void getIntentData() {
        Intent intent = getIntent();
        Parcelable[] data =  intent.getParcelableArrayExtra("EntityArray");

        if (data == null) {
            Log.d("Info", "PARCEL EPISODE IS NULL");
            return;
        }

        Episode[] episodes = new Episode[data.length];
        for (int idx = 0; idx < data.length; idx++) {
            episodes[idx] = (Episode) data[idx];
        }
        Log.d("Debug", "GET EPISODE "+episodes[0].getEpisodeName());
    }
}
