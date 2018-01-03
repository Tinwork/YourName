package com.yellowman.tinwork.yourname.UIKit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.holder.ActorHolder;
import com.yellowman.tinwork.yourname.entity.Actor;

import java.util.List;

/**
 * MERRY CHRISTMAS !!!!! ✨ L~~~~~~~~~MM~~~~~~~~~L ✨
 *
 * Created by Marc Intha-amnouay on 24/12/2017.
 * Created by Didier Youn on 24/12/2017.
 * Created by Abdel-Atif Mabrouck on 24/12/2017.
 * Created by Antoine Renault on 24/12/2017.
 */

public class ActorAdapter extends RecyclerView.Adapter {

    protected final List<Actor> actors;

    /**
     * Actor Adapter::Constructor
     *
     * @param actors
     */
    public ActorAdapter(List<Actor> actors) {
        this.actors = actors;
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
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_view, parent, false);

        return new ActorHolder(view);
    }

    /**
     * On Bind View Holder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ActorHolder) holder).bindData(actors.get(position));
    }

    /**
     * Get Item Count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (actors == null) {
            return 0;
        }

        return actors.size();
    }
}

