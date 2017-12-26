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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.ActorAdapter;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.GradientGenerator;
import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.model.Serie.Actors;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.series.ListActors;

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

    protected RecyclerView recyclerView;
    protected GradientGenerator gd;
    protected final String parcelID = "serie";
    protected View view;

    // Fragment element
    private TextView filmTitle;
    private TextView filmLang;
    private TextView synopsis;

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
        // Add gradient
        makeGradient();

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
            Log.d("Debug", "RECEIVE DATA");
            Series serie = (Series) data.get(parcelID);
            // display the already loaded data
            setFragmentElementData(serie);
            // Get the serie actor
            getSeriesActorById(serie.getId());
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
                Log.d("Error", "Network error !");
            }
        });
    }

    /**
     * Init Fragment Element
     *
     * @void
     */
    private void initFragmentElement() {
        this.filmTitle = view.findViewById(R.id.film_title);
        this.filmLang  = view.findViewById(R.id.film_lang);
        this.synopsis  = view.findViewById(R.id.synopsis);
    }

    /**
     * Set Fragment Elements Datas
     *
     * @param serie
     */
    private void setFragmentElementData(Series serie) {
        filmTitle.setText(serie.getSeriesName());
        synopsis.setText(serie.getOverview());
    }

    /**
     * Make Gradient
     *
     * @void
     * @private
     */
    private void makeGradient() {
        this.gd = new GradientGenerator(getActivity(), (RelativeLayout) this.view, null);
        gd.buildBackgroundGradientColor();
    }
}
