package com.yellowman.tinwork.yourname.activities.home.fragments;

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
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 16/12/2017.
 * Created by Didier Youn on 16/12/2017.
 * Created by Abdel-Atif Mabrouck on 16/12/2017.
 * Created by Antoine Renault on 16/12/2017.
 */

public class FavoriteFragment extends Fragment implements FragmentListener {

    protected RecyclerView recyclerView;

    /**
     * Favorite Fragment :: Constructor
     * A default constructor is always need when creating a fragment
     */
    public FavoriteFragment() {}

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
                Utils.getLinearLayoutOrientation(this.getActivity()),
                false
        ));

        // Improve performance
        recyclerView.setHasFixedSize(true);

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
        // /!\ Just for testing purposes
        getFavoriteSeries();
    }

    /**
     * Notify Data
     *
     * @param parcel
     */
    @Override
    public void notifyData(Parcelable parcel) {

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
        FavoriteFragment self = this;
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name", "lollipop");

        SearchSeries searchQuery = new SearchSeries(getActivity());
        searchQuery.get(payload, new GsonCallback<Search>() {
            @Override
            public void onSuccess(Search response) {
                Log.d("Debug", "Response length "+response.getData().size());
                if (response == null) {
                    return;
                }

                self.bindRecycleView(response.getData());
            }

            @Override
            public void onError(String err) {

            }
        });
    }
}
