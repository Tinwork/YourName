package com.yellowman.tinwork.yourname.activities.filmDetail.fragments;

import android.content.Context;
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
import com.yellowman.tinwork.yourname.UIKit.adapters.SeasonsAdapter;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentBinder;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.application.YourName;
import com.yellowman.tinwork.yourname.entity.Episode;
import com.yellowman.tinwork.yourname.entity.EpisodeMisc;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.series.EpisodeSummary;
import com.yellowman.tinwork.yourname.network.api.series.ListEpisodes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class FilmEpisodesFragment extends Fragment implements FragmentListener, FragmentBinder {

    @Inject
    @Named("EpisodeSummary")
    Fetch episodeSummary;

    @Inject
    @Named("ListEpisodes")
    Fetch listEpisodes;

    private RecyclerView recyclerView;
    private List<Episode[]> episodesList = new ArrayList<>();
    private int idx = 0;
    private UIErrorManager uiErrorManager;
    private String serie_id;
    private Context ctx;

    /**
     * Film Episodes Fragment::Constructor
     */
    public void FilmEpisodesFragment() {}

    /**
     * On Create
     *
     * @param savedInstanceBundle
     */
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        ((YourName) getActivity().getApplicationContext()).getmNetworkComponent().inject(this);
    }

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
        // get the UIErrorManager
        this.uiErrorManager = new UIErrorManager(getContext());
        this.ctx = getContext();

        return episodes;
    }

    /**
     * Notify Data
     *
     * @param data
     */
    @Override
    public void notifyData(List<Series> data) {
        if (data == null){
            // should handle something here
        } else {
            Series serie = data.get(0);
            this.serie_id = serie.getId();
            getSeasons();
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
     */
    public void getSeasons() {
        HashMap<String, String> data = new HashMap<>();
        data.put("series_id", serie_id);

        episodeSummary.get(data, new GsonCallback<EpisodeMisc>() {
            @Override
            public void onSuccess(EpisodeMisc response) {
                notifySeasonsLoaded(response);
            }

            @Override
            public void onError(String err) {
                uiErrorManager.setError("", err).setErrorStrategy(UIErrorManager.TOAST);
            }
        });
    }

    /**
     * Load All Episodes By Seasons
     *
     * @param seasons
     */
    public void loadAllEpisodesBySeasons(String[] seasons) {
        if (seasons.length == idx) {
            return;
        }
        // this list need to be save using realm
        HashMap<String, String> data = new HashMap<>();

        data.put("series_id", serie_id);
        data.put("season", seasons[idx]);

        listEpisodes.get(data, new GsonCallback<Episode[]>() {

            @Override
            public void onSuccess(Episode[] response) {
                episodesList.add(response);
                idx++;

                if (seasons.length == idx) {
                    notifySeasonsReady();
                } else {
                    loadAllEpisodesBySeasons(seasons);
                }
            }

            @Override
            public void onError(String err) {

                if (err.contains("404")) {
                    // Fail silently --> we assumed that no more seasons existed
                    notifySeasonsReady();
                } else {
                     uiErrorManager.setError("", err).setErrorStrategy(UIErrorManager.TOAST);
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
     * @return
     */
    public Thread handleMulSeasons(String[] seasons) {
        Thread thread = new Thread(() -> {
            loadAllEpisodesBySeasons(seasons);
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
            SeasonsAdapter adapter = new SeasonsAdapter(episodesList);
            this.recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Notify Seasons Loaded
     *
     * @param misc
     */
    private void notifySeasonsLoaded(EpisodeMisc misc) {
        handleMulSeasons(misc.getAiredSeasons()).start();
    }
}
