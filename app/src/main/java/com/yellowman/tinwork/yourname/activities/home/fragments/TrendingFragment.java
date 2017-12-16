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


public class TrendingFragment extends Fragment implements FragmentListener {

    private OnFragmentInteractionListener mListener;
    private FragmentCommunication mCommunication;
    private RecyclerView recyclerView;
    protected View spinner;

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mCommunication = (FragmentCommunication) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        mListener = null;
    }

    /**
     * Notify Data
     *
     * @param parcel
     */
    @Override
    public void notifyData(Parcelable parcel) {
        if (parcel == null) {
            Log.d("Debug", "Parcel is null");
            getSeries(getActivity());
        } else {
            Search data = (Search) parcel;
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
     * On Fragment Interaction Listener
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
                mCommunication.setParcelable(response);
                ProgressSpinner.setHidden(spinner);
            }

            public void onError(String err) {
                Log.d("Error", "err: "+err);
            }
        });
    }
}
