package com.yellowman.tinwork.yourname.activities.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.CardSeriesAdapter;
import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentBinder;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentCommunication;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.misc.ProgressSpinner;
import com.yellowman.tinwork.yourname.UIKit.misc.SwipeController;
import com.yellowman.tinwork.yourname.application.YourName;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.decorator.FavoriteRealmDecorator;
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

public class FavoriteFragment extends Fragment implements FragmentListener, FragmentBinder {

    @Inject
    @Named("FetchFavorite")
    FavoriteRealmDecorator realmDecorator;

    protected RecyclerView recyclerView;
    protected View spinner;
    protected FragmentCommunication mLink;
    protected TextView noFavorite;
    protected ConnectivityHelper conHelper;

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
     * @param savedInstanceBundle bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        // Inject dependencies
        ((YourName) getActivity().getApplicationContext()).getmNetworkComponent().inject(this);
        // UIErrorMananager
        this.uiErrorManager = new UIErrorManager(getContext());
    }

    /**
     * On Create View
     *
     * @param inflater LayoutInflater
     * @param container ViewContainer
     * @param savedInstanceState Bundle
     * @return View favorite
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View favorite = inflater.inflate(R.layout.favorite_fragment, container, false);
        // Create the recycler view
        recyclerView = favorite.findViewById(R.id.favoriteFrag_recycler_view);

        if (this.getId() == R.id.user_frag) {
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    favorite.getContext(),
                    LinearLayout.VERTICAL,
                    false
            ));
            // Set the swipe controller
            attachSwipeController();
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    favorite.getContext(),
                    LinearLayout.HORIZONTAL,
                    false
            ));
        }

        // Improve performance
        recyclerView.setHasFixedSize(true);
        // Spinner
        this.spinner = favorite.findViewById(R.id.favorite_spinner);
        // set the textview
        this.noFavorite = favorite.findViewById(R.id.no_favorite);

        return favorite;
    }

    /**
     * On Activity Created
     *
     * @param savedInstanceBundle Bundle
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceBundle) {
        super.onActivityCreated(savedInstanceBundle);
    }

    /**
     * Notify Data
     *
     * @param parcel Parcelable of series
     */
    @Override
    public void notifyData(List<Series> parcel) {}

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
            uiErrorManager.setError("", e.getMessage()).setErrorStrategy(UIErrorManager.TOAST);
        }
    }

    /**
     * On Start
     *
     */
    @Override
    public void onStart() {
        super.onStart();

        // retrieve the preference
        String username  = AppUtils.getSharedPreference(getContext(), "username");
        String accountID = AppUtils.getSharedPreference(getContext(), "accountID");

        if (username.isEmpty() || accountID.isEmpty()) {
            noFavorite.setVisibility(View.VISIBLE);
        } else {
            getFavoriteSeries();
            setUnsavedSeries();
        }
    }

    /**
     * Bind Recycle View
     *
     * @param data List of unknown type
     */
    public void bindRecycleView(List<?> data) {
        CardSeriesAdapter adapter;
        int layoutID;

        if (this.getId() == R.id.user_frag) {
            layoutID = R.layout.card_favorite_hor;
        } else {
            layoutID = R.layout.card_favorite;
        }

        if (data == null) {
            adapter = new CardSeriesAdapter(null, layoutID);
        } else {
            adapter = new CardSeriesAdapter((List<Series>) data, layoutID);
        }

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Get Favorite Series
     *
     */
    private void getFavoriteSeries() {
        ProgressSpinner.setVisible(spinner);

        realmDecorator.get(new GsonCallback<List<Series>>() {
            @Override
            public void onSuccess(List<Series> response) {
                if (response.size() == 0) {
                    bindRecycleView(null);
                    noFavorite.setVisibility(View.VISIBLE);
                } else {
                    bindRecycleView(response);
                    mLink.setParcelable(response, parcelID);
                    noFavorite.setVisibility(View.GONE);
                }

                ProgressSpinner.setHidden(spinner);
            }

            @Override
            public void onError(String err) {
                bindRecycleView(null);
                ProgressSpinner.setHidden(spinner);
                noFavorite.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     *
     * @param payload List of series
     */
    private void restoreData(List<Series> payload) { bindRecycleView(payload); }

    /**
     * Attach Swipe Controller
     *
     */
    private void attachSwipeController() {
        SwipeController controller = new SwipeController(getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Set Unsaved Series
     *
     * /!\ This save the unsave series of the tvdb apis
     * use case: Offline and you want to save series --> no internet connexion
     * Therefore we need to save and do a update somewhere..
     */
    private void setUnsavedSeries() {
        List<Series> series = realmDecorator.getUnsavedSeriesStack();

        if (series == null) {
            return;
        }

        // update the favorite series
        for (Series serie: series) {
            realmDecorator.setSerieAsFavorite(serie);
        }
    }

}
