package com.yellowman.tinwork.yourname.activities.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.CardSeriesAdapter;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentBinder;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.ProgressSpinner;
import com.yellowman.tinwork.yourname.application.YourName;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.realm.decorator.SeriesRealmDecorator;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Marc Intha-amnouay on 16/12/2017.
 * Created by Didier Youn on 16/12/2017.
 * Created by Abdel-Atif Mabrouck on 16/12/2017.
 * Created by Antoine Renault on 16/12/2017.
 */

public class RandomFragment extends Fragment implements FragmentListener, FragmentBinder {

    @Inject
    @Named("SearchSeries")
    SeriesRealmDecorator searchSeries;

    protected final String parcelID = "popular";
    protected RecyclerView recyclerView;
    protected View spinner;
    private FragmentCommunication mLink;
    private UIErrorManager uiErrorManager;

    /**
     * On Create
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((YourName) getActivity().getApplicationContext()).getmNetworkComponent().inject(this);
    }

    /**
     * On Create View
     *
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View popular = inflater.inflate(R.layout.popular_fragment, container, false);

        // Set the recycler view
        recyclerView = popular.findViewById(R.id.popularFrag_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                popular.getContext(),
                LinearLayout.HORIZONTAL,
                false
        ));

        recyclerView.setHasFixedSize(true);
        // Set the spinner here
        spinner = popular.findViewById(R.id.popular_spinner);
        // UIErrorManager
        this.uiErrorManager = new UIErrorManager(getContext());

        return popular;
    }

    /**
     * On Attach
     *
     * @param ctx Context
     */
    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);

        try {
            mLink = (FragmentCommunication) ctx;
        } catch (ClassCastException e) {
            uiErrorManager
                    .setError("100", "Error while converting datas")
                    .setOptsMode(UIErrorManager.RETRY)
                    .setErrorStrategy(UIErrorManager.ALERT);
        }
    }

    /**
     * Notify Data
     *
     * @param parcel List of series
     */
    @Override
    public void notifyData(List<Series> parcel) {
        // dumb test
        if (parcel == null) {
            getPopularSeries();
        } else {
            restoreData(parcel);
        }
    }

    /**
     * Bind Recycle View
     *
     * @param data List of datas
     */
    @Override
    public void bindRecycleView(List<?> data) {
        CardSeriesAdapter adapter;
        if (data == null) {
            adapter = new CardSeriesAdapter(null, R.layout.card_generic);
        } else {
            adapter = new CardSeriesAdapter((List<Series>) data, R.layout.card_generic);
        }

        recyclerView.setAdapter(adapter);
    }

    /**
     * Get Popular Series
     *
     */
    private void getPopularSeries() {
        ProgressSpinner.setVisible(spinner);
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name",AppUtils.getRandomChar());

        // get the reference of our class so we can use in our callback..
        RandomFragment self = this;

        searchSeries.get(payload, new GsonCallback<List<Series>>() {
            @Override
            public void onSuccess(List<Series> response) {
                if (response == null) {
                    return;
                }

                if (response.size() > 10) {
                    List<Series> sub = response.subList(0, 10);
                    self.bindRecycleView(sub);
                    // Set parcelable too
                    mLink.setParcelable(sub, parcelID);
                } else {
                    self.bindRecycleView(response);
                    // Set the parcelable here
                    mLink.setParcelable(response, parcelID);
                }

                // Hide the spinner here
                ProgressSpinner.setHidden(spinner);
            }

            @Override
            public void onError(String err) {
                uiErrorManager
                        .setError("", err)
                        .setErrorStrategy(UIErrorManager.TOAST);
            }
        });
    }

    /**
     * Restore Data
     *
     * @param payload List of series
     */
    private void restoreData(List<Series> payload) {  bindRecycleView(payload);  }
}
