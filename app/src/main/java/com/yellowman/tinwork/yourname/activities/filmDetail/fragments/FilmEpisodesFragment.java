package com.yellowman.tinwork.yourname.activities.filmDetail.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.EpisodesAdapter;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.entity.Episode;
import com.yellowman.tinwork.yourname.entity.EpisodeMisc;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.series.EpisodeSummary;
import com.yellowman.tinwork.yourname.network.api.series.ListEpisodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class FilmEpisodesFragment extends Fragment implements FragmentListener {

    private RecyclerView recyclerView;
    private List<Episode[]> episodesList = new ArrayList<>();
    private int idx = 0;

    /**
     * Film Episodes Fragment::Constructor
     */
    public void FilmEpisodesFragment() {}

    /**
     * On Create View
     *
     * @param inflater
     * @param container
     * @param savedInstanceBundle
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View episodes = inflater.inflate(R.layout.details_episodes_fragment, container, false);
        // set recyclerview
        this.recyclerView = episodes.findViewById(R.id.seasons_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                episodes.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        ));
        recyclerView.setHasFixedSize(true);

        return episodes;
    }

    /**
     * Notify Data
     *
     * @param data
     */
    @Override
    public void notifyData(HashMap<String, Parcelable> data) {
        if (data == null){
            // should handle something here
        } else {
            Series serie = (Series) data.get("serie");
            getSeasons(serie.getId());
        }
    }

    /**
     * Bind Recycler View
     *
     * @param data
     */
    public void bindRecycleView(List<?> data) {}

    /**
     * Get Seasons
     *
     * @param serie_id
     */
    public void getSeasons(String serie_id) {
        HashMap<String, String> data = new HashMap<>();
        data.put("series_id", serie_id);

        EpisodeSummary summary = new EpisodeSummary(getContext());
        summary.get(data, new GsonCallback<EpisodeMisc>() {

            @Override
            public void onSuccess(EpisodeMisc response) {
                notifySeasonsLoaded(response, serie_id);
            }

            @Override
            public void onError(String err) {
                // should handle error by a toast
            }
        });
    }

    /**
     * Load All Episodes By Seasons
     *
     * @param seasons
     * @param serie_id
     */
    public void loadAllEpisodesBySeasons(String[] seasons, String serie_id) {
        if (seasons.length == idx) {
            return;
        }
        // this list need to be save using realm
        HashMap<String, String> data = new HashMap<>();

        data.put("series_id", serie_id);
        data.put("season", seasons[idx]);

        ListEpisodes episodes = new ListEpisodes(getContext());
        episodes.get(data, new GsonCallback<Episode[]>() {

            @Override
            public void onSuccess(Episode[] response) {
                episodesList.add(response);
                idx++;

                if (seasons.length == idx) {
                    notifySeasonsReady();
                } else {
                    loadAllEpisodesBySeasons(seasons, serie_id);
                }
            }

            @Override
            public void onError(String err) {
                 if (err.contains("404")) {
                    // Fail silently --> we assumed that no more seasons existed
                    notifySeasonsReady();
                }
            }
        });
    }


    /**
     * Handle Mul Seasons
     *      Thread that get the episodes by seasons
     *      /!\ Can take a long time ! though we may have use the AsyncTask but as the method is already asynchronious..
     *
     * @param seasons
     * @param serie_id
     * @return
     */
    public Thread handleMulSeasons(String[] seasons, String serie_id) {
        Thread thread = new Thread(() -> {
            loadAllEpisodesBySeasons(seasons, serie_id);
        });

        return thread;
    }

    /**
     * Notify Seasons Ready
     *
     */
    private void notifySeasonsReady() {

        // Maybe it's a good idea to do that as it's called from a runnable ?
        this.recyclerView.post(() -> {
            EpisodesAdapter adapter = new EpisodesAdapter(episodesList);
            this.recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Notify Seasons Loaded
     *
     * @param misc
     */
    private void notifySeasonsLoaded(EpisodeMisc misc, String serie_id) {
        Log.d("Debug", "HAVE ALL SEASONS");

        handleMulSeasons(misc.getAiredSeasons(), serie_id).start();
    }
}