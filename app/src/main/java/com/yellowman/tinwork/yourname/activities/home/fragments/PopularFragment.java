package com.yellowman.tinwork.yourname.activities.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.CardSeriesAdapter;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
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

public class PopularFragment extends Fragment implements FragmentListener {

    protected RecyclerView recyclerView;

    /**
     * On Create
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

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
        View popular = inflater.inflate(R.layout.popular_fragment, container, false);

        // Set the recycler view
        recyclerView = popular.findViewById(R.id.popularFrag_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                popular.getContext(),
                Utils.getLinearLayoutOrientation(this.getActivity()),
                false
        ));

        recyclerView.setHasFixedSize(true);

        return popular;
    }

    /**
     * On Activity Created
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // /!\ Just for testing
        getPopularSeries(getActivity());
    }

    /**
     * On Attach
     *
     * @param ctx
     */
    @Override
    public void onAttach(Context ctx) {
       super.onAttach(ctx);
    }

    /**
     * On Detach
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Notify Data
     *
     * @param parcel
     */
    @Override
    public void notifyData(Parcelable parcel) {
        // dumb test
    }

    /**
     * Bind Recycle View
     *
     * @param data
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
     * @param ctx
     */
    private void getPopularSeries(Context ctx) {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("name","your name");

        // get the reference of our class so we can use in our callback..
        PopularFragment self = this;

        SearchSeries querySeries = new SearchSeries(ctx);
        querySeries.get(payload, new GsonCallback<Search>() {
            @Override
            public void onSuccess(Search response) {
                if (response.getData() == null) {
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
