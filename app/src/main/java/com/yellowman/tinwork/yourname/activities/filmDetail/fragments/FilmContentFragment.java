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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.ActorAdapter;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.series.ListActors;
import com.yellowman.tinwork.yourname.network.api.series.SingleSerie;

import java.util.HashMap;
import java.util.List;

/**
 * MERRY CHRISTMAS !!!!! ✨ L~~~~~~~~~MM~~~~~~~~~L ✨
 *
 * Created by Marc Intha-amnouay on 24/12/2017.
 * Created by Didier Youn on 24/12/2017.
 * Created by Abdel-Atif Mabrouck on 24/12/2017.
 * Created by Antoine Renault on 24/12/2017.
 */

public class FilmContentFragment extends Fragment implements FragmentListener {

    // protected fields
    protected RecyclerView recyclerView;
    protected final String parcelID = "serie";
    protected View view;

    // private fields
    private Series serie;
    private UIErrorManager uiErrorManager;

    // Fragment element
    private TextView filmTitle;
    private TextView filmNetwork;
    private TextView filmGenre;
    private TextView filmDate;
    private TextView synopsis;
    private TextView siteRating;

    /**
     * Film Content Fragment::Constructor
     */
    public FilmContentFragment() {}

    /**
     * On Create View
     *
     * @param inflater
     * @param container
     * @param savedInstanceBundle
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        this.view = inflater.inflate(R.layout.details_fragment, container, false);
        this.uiErrorManager = new UIErrorManager(getContext());
        // Set recycler view properties
        this.recyclerView = view.findViewById(R.id.actors_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                view.getContext(),
                LinearLayout.HORIZONTAL,
                false
        ));

        recyclerView.setHasFixedSize(true);
        // Init the other component
        initFragmentElement();

        return view;
    }

    /**
     * Notify Data
     *
     * @param data
     */
    @Override
    public void notifyData(HashMap<String, Parcelable> data) {

        if (data == null) {
            // handle that no actor exist
        } else {
            this.serie = (Series) data.get(parcelID);
            // display the already loaded data
            setFragmentElementData();
            // Get the serie actor
            getSeriesActorById(serie.getId());
            // aggregate the serie
            agreggateSerie(serie.getId());
        }
    }

    /**
     * Bind Recycler View
     *  Not used in this case
     * @param data
     */
    @Override
    public void bindRecycleView(List<?> data) {}

    /**
     * Get Series Actor By Id
     *
     * @param serieID
     */
    private void getSeriesActorById(String serieID) {
        HashMap<String, String> params = new HashMap<>();
        params.put("series_id", serieID);

        ListActors actors = new ListActors(this.getContext());
        actors.get(params, new GsonCallback<Actor[]>() {
            @Override
            public void onSuccess(Actor[] response) {
                // Create our adapter
                ActorAdapter adapter = new ActorAdapter(response);
                recyclerView.setAdapter(adapter);
            }

            public void onError(String err) {
                uiErrorManager
                        .setError("", err)
                        .setErrorStrategy(UIErrorManager.SNACKBAR);
            }
        });
    }

    /**
     * Get Upgrade Series Info
     *
     * @param serie_id
     */
    private void agreggateSerie(String serie_id) {
        HashMap<String, String> data = new HashMap<>();
        data.put("series_id", serie_id);

        // Call our service
        SingleSerie serieServices = new SingleSerie(getContext());
        serieServices.get(data, new GsonCallback<Series>() {

            @Override
            public void onSuccess(Series response) {
                // Aggregate the datas
                serie.setGenre(response.getGenre());
                serie.setSiteRating(response.getSiteRating());
                // update the related textview
                setUpdateElementData();
            }

            @Override
            public void onError(String err) {
                // Handle the error
            }
        });
    }

    /**
     * Init Fragment Element
     *
     * @void
     */
    private void initFragmentElement() {
        this.filmTitle    = view.findViewById(R.id.film_title);
        this.filmNetwork  = view.findViewById(R.id.film_network);
        this.filmGenre    = view.findViewById(R.id.genre);
        this.filmDate     = view.findViewById(R.id.date);
        this.synopsis     = view.findViewById(R.id.synopsis);
        this.siteRating   = view.findViewById(R.id.site_rating);
    }

    /**
     * Set Fragment Elements Datas
     *
     */
    private void setFragmentElementData() {
        filmTitle.setText(serie.getSeriesName());
        filmNetwork.setText(serie.getNetwork());
        filmDate.setText(serie.getFirstAired());
        synopsis.setText(serie.getOverview());
    }

    /**
     * Set Update Element Data
     *
     */
    private void setUpdateElementData() {
        String genreStr = "";

       for (String s : serie.getGenre()) {
            genreStr += s+" ";
       }

        filmGenre.setText(genreStr);
        siteRating.setText(serie.getSiteRating());
    }
}
