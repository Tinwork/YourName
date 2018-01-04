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

public class FavoriteFragment extends Fragment implements FragmentListener, FragmentBinder {

    @Inject
    @Named("SearchSeries")
    SeriesRealmDecorator searchSeries;

    protected RecyclerView recyclerView;
    protected View spinner;
    protected FragmentCommunication mLink;
    private final String parcelID = "favorite";
    private UIErrorManager uiErrorManager;


    /**
     * Favorite Fragment :: Constructor
     * A default constructor is always need when creating a fragment
     */
    public FavoriteFragment() {}

    /**
     * On Create
     *
     * @param savedInstanceBundle
     */
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        // Inject dependencies
        ((YourName) getActivity().getApplicationContext()).getmNetworkComponent().inject(this);
    }

    /**
     * On Create View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View favorite = inflater.inflate(R.layout.favorite_fragment, container, false);

        // Create the recycler view
        recyclerView = favorite.findViewById(R.id.favoriteFrag_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                favorite.getContext(),
                LinearLayout.HORIZONTAL,
                false
        ));

        // Improve performance
        recyclerView.setHasFixedSize(true);
        // Spinner
        spinner = favorite.findViewById(R.id.favorite_spinner);
        // UIErrorMananager
        this.uiErrorManager = new UIErrorManager(getContext());

        return favorite;
    }

    /**
     * On Activity Created
     *
     * @param savedInstanceBundle
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);
    }

    /**
     * Notify Data
     *
     * @param parcel
     */
    @Override
    public void notifyData(List<Series> parcel) {
        if (parcel == null) {
            getFavoriteSeries();
        } else {
            restoreData(parcel);
        }
    }

    /**
     * On Attach
     *
     * @param ctx
     */
    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);

        try {
            mLink = (FragmentCommunication) ctx;
        } catch (ClassCastException e) {
            uiErrorManager.setError("", e.getMessage()).setErrorStrategy(UIErrorManager.TOAST);
        }
    }

    /**
     * Bind Recycle View
     *
     * @TODO Handle cast error
     * @param data
     */
    public void bindRecycleView(List<?> data) {
        CardSeriesAdapter adapter;

        if (data == null) {
            adapter = new CardSeriesAdapter(null, R.layout.card_favorite);
        } else {
            adapter = new CardSeriesAdapter((List<Series>) data, R.layout.card_favorite);
        }

        recyclerView.setAdapter(adapter);
    }

    /**
     * Get Favorite Series
     *
     */
    private void getFavoriteSeries() {
        ProgressSpinner.setVisible(spinner);
        FavoriteFragment self = this;
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name", "lollipop");

        searchSeries.get(payload, new GsonCallback<List<Series>>() {
            @Override
            public void onSuccess(List<Series> response) {
                if (response == null) {
                    return;
                }

                self.bindRecycleView(response);
                mLink.setParcelable(response, parcelID);
                ProgressSpinner.setHidden(spinner);
            }

            @Override
            public void onError(String err) {
                uiErrorManager.setError("", err).setErrorStrategy(UIErrorManager.TOAST);
            }
        });
    }

    /**
     *
     * @param payload
     */
    private void restoreData(List<Series> payload) { bindRecycleView(payload); }
}
