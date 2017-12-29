package com.yellowman.tinwork.yourname.activities.EpisodeDetail;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.entity.Episode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class EpisodeDetailActivity extends AppCompatActivity {

    protected FragmentListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);
        // Set the listener
        this.listener = (FragmentListener) getSupportFragmentManager().findFragmentById(R.id.fragment_episodes_season_detail);
        notifyFragment();
    }

    /**
     * Get Intent Data
     *
     * @void
     */
    protected List<Episode> getIntentData() {
        List<Episode> listEpisode = new ArrayList<>();
        Intent intent = getIntent();
        Parcelable[] data =  intent.getParcelableArrayExtra("EntityArray");

        if (data == null) {
            Log.d("Info", "PARCEL EPISODE IS NULL");
            return null;
        }

        for (int idx = 0; idx < data.length; idx++) {
            listEpisode.add((Episode) data[idx]);
        }

        return listEpisode;
    }

    /**
     * Notify Fragment
     *      Notify the fragment by passing a list data
     * @void
     */
    protected void notifyFragment() {
        List<Episode> listEpisodes = getIntentData();
        listener.bindRecycleView(listEpisodes);
    }
}
