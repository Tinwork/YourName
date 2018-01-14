package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.holder.SeasonsHolder;
import com.yellowman.tinwork.yourname.entity.Episode;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class SeasonsAdapter extends RecyclerView.Adapter {

    protected final List<Episode[]> episodesList;

    /**
     * Episodes Adapter :: Constructor
     *
     * @param episodesList List<Episode[]>
     */
    public SeasonsAdapter(List<Episode[]> episodesList) {
        this.episodesList = episodesList;
    }

    /**
     * On Create View Holder
     *
     * @param parent ViewHolder
     * @param viewType int
     * @return ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seasons_layout, parent, false);

        // return new viewholder
        return new SeasonsHolder(v, episodesList.size());
    }

    /**
     * On Bind View Holder
     *
     * @param holder ViewHolder
     * @param position int
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SeasonsHolder) holder).bindData(episodesList.get(position));
    }

    /**
     * Get Item Count
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        if (episodesList == null) {
            return 0;
        }

        return episodesList.size();
    }
}
