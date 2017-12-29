package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.holder.EpisodeHolder;
import com.yellowman.tinwork.yourname.entity.Episode;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 29/12/2017.
 * Created by Didier Youn on 29/12/2017.
 * Created by Abdel-Atif Mabrouck on 29/12/2017.
 * Created by Antoine Renault on 29/12/2017.
 */

public class EpisodeAdapter extends RecyclerView.Adapter {

    protected List<Episode> listEpisode;

    /**
     * Episode Adapter::Constructor
     */
    public EpisodeAdapter(List<Episode> episode) {
        this.listEpisode = episode;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodes_list, parent, false);

        return new EpisodeHolder(view);
    }

    /**
     * On Bind View Holder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EpisodeHolder) holder).bindData(listEpisode.get(position));
    }

    /**
     * Get Item Count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (listEpisode == null) {
            return 0;
        }

        return listEpisode.size();
    }
}
