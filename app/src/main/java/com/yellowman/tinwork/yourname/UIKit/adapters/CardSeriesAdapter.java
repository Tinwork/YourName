package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.holder.FavoriteHolder;
import com.yellowman.tinwork.yourname.UIKit.holder.PopularHolder;
import com.yellowman.tinwork.yourname.UIKit.holder.TrendingHolder;
import com.yellowman.tinwork.yourname.UIKit.iface.AdapterHolder;
import com.yellowman.tinwork.yourname.model.Series;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 06/12/2017.
 * Created by Didier Youn on 06/12/2017.
 * Created by Abdel-Atif Mabrouck on 06/12/2017.
 * Created by Antoine Renault on 06/12/2017.
 */

public class CardSeriesAdapter extends RecyclerView.Adapter {

    public List<Series> series;
    private int layoutID;
    private AdapterHolder iCommunication;


    /**
     * Trend Adapter
     * @param seriesList List of Series
     */
    public CardSeriesAdapter(List<Series> seriesList, int layoutID) {
        this.series   = seriesList;
        this.layoutID = layoutID;
        this.iCommunication = (int idx) -> {
            seriesList.remove(idx);
            this.notifyDataSetChanged();
        };
    }

    /**
     *
     * @param parent ViewGroup
     * @param viewType ViewType
     * @return ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);

        // Create viewHolder here
        return getHolderBaseFromLayoutID(view);
    }

    /**
     * On Bind View Holder
     *
     * @param holder ViewHolder
     * @param position cursor
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (layoutID) {
            case R.layout.card_home:
            case R.layout.card_home_landscape:
                ((TrendingHolder) holder).bindData(series.get(position));
                break;
            case R.layout.card_generic:
                ((PopularHolder) holder).bindData(series.get(position));
                break;
            case R.layout.card_favorite:
            case R.layout.card_favorite_hor:
                ((FavoriteHolder) holder).bindData(series.get(position), position);
        }
    }


    /**
     * Get Item Count
     *
     * @return int size
     */
    @Override
    public int getItemCount() {
        if (series == null) {
            return 0;
        }

        return series.size();
    }

    /**
     * Get Item View Type
     *
     * @param position int
     * @return Layout
     */
    @Override
    public int getItemViewType(final int position) {
        return R.layout.card_home;
    }


    /**
     * Get Holder Based From Layout ID
     *
     * @param v View
     * @return ViewHolder
     */
    private RecyclerView.ViewHolder getHolderBaseFromLayoutID(View v) {
        RecyclerView.ViewHolder holder = null;

        switch (layoutID) {
            case R.layout.card_home:
            case R.layout.card_home_landscape:
                holder = new TrendingHolder(v);
                break;
            case R.layout.card_generic:
                holder = new PopularHolder(v);
                break;
            case R.layout.card_favorite:
            case R.layout.card_favorite_hor:
                holder = new FavoriteHolder(v, iCommunication);
                break;
            default:
                holder = new TrendingHolder(v);
        }

        return holder;
    }
}
