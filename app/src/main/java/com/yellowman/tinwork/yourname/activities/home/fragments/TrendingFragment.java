package com.yellowman.tinwork.yourname.activities.home.fragments;

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
import android.widget.LinearLayout;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.CardSeriesAdapter;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
import com.yellowman.tinwork.yourname.UIKit.misc.ProgressSpinner;
import com.yellowman.tinwork.yourname.application.YourName;
import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.realm.manager.SeriesRealmManager;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Marc Intha-amnouay on 29/11/2017.
 * Created by Didier Youn on 29/11/2017.
 * Created by Abdel-Atif Mabrouck on 29/11/2017.
 * Created by Antoine Renault on 29/11/2017.
 */

public class TrendingFragment extends Fragment implements FragmentListener {

    @Inject
    @Named("SearchSeries")
    SeriesRealmManager searchSeries;

    protected final String parcelID = "trending";
    protected View spinner;
    protected int viewResources;
    private FragmentCommunication mCommunication;
    private RecyclerView recyclerView;
    private UIErrorManager uiErrorManager;


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
        ((YourName) getActivity().getApplicationContext()).getmNetworkComponent().inject(this);
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
                        LinearLayout.HORIZONTAL,
                        false
                )
        );

        // Set it to fix so android can do some optimization
        recyclerView.setHasFixedSize(true);
        bindRecycleView(null);
        // Instantiate the Loader
        spinner = trending.findViewById(R.id.trending_spinner);
        // Set the Layout ID to be use
        getResourcesID();
        // set the UIErrorManager
        this.uiErrorManager = new UIErrorManager(getContext());

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
            uiErrorManager
                    .setError("", e.toString())
                    .setOptsMode(UIErrorManager.RETRY)
                    .setErrorStrategy(UIErrorManager.ALERT);
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
    public void notifyData(List<Series> parcels) {
        if (parcels == null) {
            getSeries();
        } else {
            restoreData(parcels);
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
            adapter = new CardSeriesAdapter(null, viewResources);
        } else {
            adapter = new CardSeriesAdapter((List<Series>) data, viewResources);
        }

        recyclerView.setAdapter(adapter);
    }

    /**
     * Restore Data
     *
     * @param payload
     */
    public void restoreData(List<Series> payload) {
        if (payload == null) {
            getSeries();
        } else {
            Log.d("Debug", "Restoring series");
            bindRecycleView(payload);
            ProgressSpinner.setHidden(spinner);
        }
    }

    /**
     * Get Series
     *
     */
    private void getSeries() {
        ProgressSpinner.setVisible(spinner);
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name", "star wars");

        // Ok i guess this is not a good thing
        TrendingFragment self = this;
        searchSeries.get(payload, new GsonCallback<List<Series>>() {
            @Override
            public void onSuccess(List<Series> response) {
                if (response == null) {
                    return;
                }

                self.bindRecycleView(response);
                mCommunication.setParcelable(response, parcelID);
                ProgressSpinner.setHidden(spinner);
            }

            public void onError(String err) {
                uiErrorManager
                        .setError("", err)
                        .setErrorStrategy(UIErrorManager.TOAST);

                ProgressSpinner.setHidden(spinner);
                com.yellowman.tinwork.yourname.utils.Utils.outputRealmFile();
            }
        });
    }

    /**
     * Get Resources ID
     *
     * @void
     */
    private void getResourcesID() {
        if (Utils.getLinearLayoutOrientation(getActivity()) == LinearLayout.HORIZONTAL) {
            this.viewResources = R.layout.card_home;
            return;
        }

        this.viewResources = R.layout.card_home_landscape;
    }
}
