package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.holder.EpisodesHolder;
import com.yellowman.tinwork.yourname.entity.Episode;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class EpisodesAdapter extends RecyclerView.Adapter {

    protected final List<Episode[]> episodesList;

    /**
     * Episodes Adapter :: Constructor
     *
     * @param episodesList
     */
    public EpisodesAdapter(List<Episode[]> episodesList) {
        this.episodesList = episodesList;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seasons_layout, parent, false);

        // return new viewholder
        return new EpisodesHolder(v, episodesList.size());
    }

    /**
     * On Bind View Holder
     *
     * @param holder
     * @param position
     */
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EpisodesHolder) holder).bindData(episodesList.get(position));
    }

    /**
     * Get Item Count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (episodesList == null) {
            return 0;
        }

        return episodesList.size();
    }
}
