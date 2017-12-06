package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;

/**
 * Created by Marc Intha-amnouay on 06/12/2017.
 * Created by Didier Youn on 06/12/2017.
 * Created by Abdel-Atif Mabrouck on 06/12/2017.
 * Created by Antoine Renault on 06/12/2017.
 */

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.ViewHolder>{
    private String[] mDataset;

    /**
     * TrendAdapter::Constructor
     * @param dataset
     */
    public TrendAdapter(String[] dataset) {
        dataset = mDataset;
    }

    /**
     * On Create View Holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public TrendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new text view
        TextView title = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trending, parent, false);

        ViewHolder vh = new ViewHolder(title);
        return vh;
    }

    /**
     *
     * @param holder
     * @param pos
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        holder.title.setText(mDataset[pos]);
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    /**
     * View Holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ViewHolder(TextView t) {
            super(t);
            title = t;
        }
    }
}
