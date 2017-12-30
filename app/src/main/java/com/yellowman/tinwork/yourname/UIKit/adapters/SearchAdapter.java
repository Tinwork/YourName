package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.holder.SearchHolder;
import com.yellowman.tinwork.yourname.model.Series;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 30/12/2017.
 * Created by Didier Youn on 30/12/2017.
 * Created by Abdel-Atif Mabrouck on 30/12/2017.
 * Created by Antoine Renault on 30/12/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter {

    protected List<Series> data;

    /**
     * Search Adapter::Constructor
     *
     * @param data
     */
    public SearchAdapter(List<Series> data) {
        this.data = data;
    }

    /**
     * On Create View Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_view, parent, false);

        return new SearchHolder(view);
    }

    /**
     * On Bind View Holder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("Debug", "DEBUUUGG "+data.get(position).getSeriesName());
        ((SearchHolder) holder).bindData(data.get(position));
    }

    /**
     * Get Item Count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (data == null) {
            Log.d("Debug", "DATAAAA NULL");
            return 0;
        }

        return data.size();
    }
}
