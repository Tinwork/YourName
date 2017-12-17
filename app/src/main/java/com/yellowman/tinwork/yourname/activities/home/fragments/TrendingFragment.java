package com.yellowman.tinwork.yourname.activities.home.fragments;

import android.content.Context;
import android.net.Uri;
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
import com.yellowman.tinwork.yourname.UIKit.adapters.CardSeriesAdapter;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
import com.yellowman.tinwork.yourname.UIKit.misc.ProgressSpinner;
import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 29/11/2017.
 * Created by Didier Youn on 29/11/2017.
 * Created by Abdel-Atif Mabrouck on 29/11/2017.
 * Created by Antoine Renault on 29/11/2017.
 */

public class TrendingFragment extends Fragment implements FragmentListener {

    protected final String parcelID = "trending";
    protected View spinner;
    private FragmentCommunication mCommunication;
    private RecyclerView recyclerView;

    /**
     *  TrendingFragment::Constructor
     */
    public TrendingFragment() {
        // Required empty public constructor
    }

    /**
     * On Create
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * On Create View
     *      Inflate the Fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View trending = inflater.inflate(R.layout.fragment_trending, container, false);
        recyclerView  = (RecyclerView) trending.findViewById(R.id.trendingFrag_recycler_view);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        trending.getContext(),
                        Utils.getLinearLayoutOrientation(this.getActivity()),
                        false
                )
        );

        // Set it to fix so android can do some optimization
        recyclerView.setHasFixedSize(true);
        bindRecycleView(null);
        // Instantiate the Loader
        spinner = trending.findViewById(R.id.trending_spinner);

        return trending;
    }

    /**
     * On Activity Created
     *      Pass the adapter to bind the component
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) { super.onActivityCreated(savedInstanceState); }

    /**
     * On Attach
     *
     * @param context
     * @public
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCommunication = (FragmentCommunication) context;
        } catch (ClassCastException e) {
            Log.d("Error", e.getMessage());
        }
    }

    /**
     * On Detach
     *
     * @public
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Notify Data
     *
     * @param parcels
     */
    @Override
    public void notifyData(HashMap<String, Parcelable> parcels) {
        if (parcels == null) {
            getSeries(getActivity());
        } else if (parcels.get(parcelID) == null) {
            getSeries(getActivity());
        } else {
            Search data = (Search) parcels.get(parcelID);
            restoreData(data);
        }
    }

    /**
     * Bind Recycler View
     *
     * @param data
     */
    @Override
    public void bindRecycleView(List<?> data) {
        CardSeriesAdapter adapter;
        if (data == null) {
            adapter = new CardSeriesAdapter(null, R.layout.card_home);
        } else {
            adapter = new CardSeriesAdapter((List<Series>) data, R.layout.card_home);
        }

        recyclerView.setAdapter(adapter);
    }

    /**
     * Restore Data
     *
     * @param payload
     */
    public void restoreData(Search payload) {
        if (payload == null) {
            getSeries(getActivity());
        } else {
            Log.d("Debug", "Restoring series");
            bindRecycleView(payload.getData());
            ProgressSpinner.setHidden(spinner);
        }
    }

    /**
     * Get Series
     *
     * @param ctx
     */
    private void getSeries(Context ctx) {
        ProgressSpinner.setVisible(spinner);
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name", "star wars");

        // Ok i guess this is not a good thing
        TrendingFragment self = this;

        SearchSeries search = new SearchSeries(ctx);
        search.get(payload, new GsonCallback<Search>() {
            @Override
            public void onSuccess(Search response) {
                // Create adapter
                if (response.getData() == null)
                    return;

                self.bindRecycleView(response.getData());
                mCommunication.setParcelable(response, parcelID);
                ProgressSpinner.setHidden(spinner);
            }

            public void onError(String err) {
                Log.d("Error", "err: "+err);
            }
        });
    }
}
