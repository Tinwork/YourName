package com.yellowman.tinwork.yourname.activities.filmDetail.fragments;

import android.os.AsyncTask;
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
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.entity.Episode;
import com.yellowman.tinwork.yourname.model.Serie.Episodes;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
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
            AsyncTaskSeasons task = new AsyncTaskSeasons(serie.getId());
            task.execute();
        }
    }

    /**
     * Bind Recycler View
     *
     * @param data
     */
    public void bindRecycleView(List<?> data) {}


    /**
     * Async Task Seasons
     */
    public class AsyncTaskSeasons extends AsyncTask<List<Episode[]>, Integer, List<Episode[]>> {

        private String serie_id;
        private List<Episode[]> episodesList;
        private int retry;
        private Boolean isFinished;

        public AsyncTaskSeasons(String serie_id) {
            this.serie_id = serie_id;
            this.retry    = 0;
            this.episodesList = new ArrayList<Episode[]>();
            this.isFinished   = false;
        }


        /**
         * Get List of Seasons
         *
         */
        public void getListOfSeasons() {
            // this list need to be save using realm
            HashMap<String, String> data = new HashMap<>();

            data.put("series_id", serie_id);
            data.put("season", Integer.toString(retry));

            ListEpisodes episodes = new ListEpisodes(getContext());
            episodes.get(data, new GsonCallback<Episode[]>() {

                @Override
                public void onSuccess(Episode[] response) {
                    episodesList.add(retry, response);
                    retry++;
                    publishProgress(retry);

                    Log.d("Info", "DATA RECEIVED "+response[0].getEpisodeName());
                    // long retry though..
                    if (retry < 64) {
                        // we try to get other seasons here
                        getListOfSeasons();
                    }
                }

                @Override
                public void onError(String err) {
                    isFinished = true;
                }
            });
        }

        /**
         * Do In Background
         *
         * @param params
         * @return
         */
        @Override
        protected List<Episode[]> doInBackground(List<Episode[]>... params) {

            try {
                getListOfSeasons();
            } catch(Exception e) {
                // Seasons
                Log.d("Error", "EEEERRRROOO "+e.toString());
            }

            if (isFinished) {
                // transform the list to an array
                return episodesList;
            }

            return null;
        }

        /**
         * On Progress Update
         *
         * @param result
         */
        @Override
        protected void onProgressUpdate(Integer... result) {
            //Log.d("Debug", "loading percent "+result.);
        }

        /**
         * On Pre Executes
         *
         */
        @Override
        protected void onPreExecute() {
            Log.d("Debug", "PREEE EXECUTE");
        }


        /**
         * On Post Execute
         *
         * @param result
         */
        @Override
        protected void onPostExecute(List<Episode[]> result) {
            Log.d("Debug", "RESSSSUUULTTTTTT");
        }
    }
}
