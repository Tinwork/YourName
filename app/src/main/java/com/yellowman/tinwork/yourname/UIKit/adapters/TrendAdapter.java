package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.holder.TrendingHolder;
import com.yellowman.tinwork.yourname.model.Series;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 06/12/2017.
 * Created by Didier Youn on 06/12/2017.
 * Created by Abdel-Atif Mabrouck on 06/12/2017.
 * Created by Antoine Renault on 06/12/2017.
 */

public class TrendAdapter extends RecyclerView.Adapter{

    public List<Series> series;

    /**
     * Trend Adapter
     * @param seriesList
     */
    public TrendAdapter(List<Series> seriesList) {
        series = seriesList;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home, parent, false);

        // Create viewHolder here
        return new TrendingHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TrendingHolder) holder).bindData(series.get(position));
    }



    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (series == null) {
            return 0;
        }

        return series.size();
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(final int position) {
        return R.layout.card_home;
    }
}
