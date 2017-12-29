package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.activities.singleEpisode.SingleEpisodeActivity;
import com.yellowman.tinwork.yourname.entity.Episode;

/**
 * Created by Marc Intha-amnouay on 29/12/2017.
 * Created by Didier Youn on 29/12/2017.
 * Created by Abdel-Atif Mabrouck on 29/12/2017.
 * Created by Antoine Renault on 29/12/2017.
 */

public class EpisodeHolder extends RecyclerView.ViewHolder {

    protected TextView episodeNb;
    protected TextView episodeTitle;
    protected TextView episodeContent;
    protected Helper helper;
    private final View view;

    /**
     * Episode Holder::constructor
     *
     * @param itemView
     */
    public EpisodeHolder(final View itemView) {
        super(itemView);
        this.view   = itemView;
        this.helper = new Helper();
        prepareElements();
    }

    /**
     * Prepare Elements
     *
     * @void
     */
    protected void prepareElements() {
        this.episodeNb      = view.findViewById(R.id.episode_number);
        this.episodeTitle   = view.findViewById(R.id.episode_title);
        this.episodeContent = view.findViewById(R.id.episode_content);
    }

    /**
     * Bind Data
     *
     * @void
     */
    public void bindData(final Episode episode) {
        episodeNb.setText(String.valueOf(episode.getAiredEpisodeNumber()));
        episodeTitle.setText(episode.getEpisodeName());
        episodeContent.setText(episode.getOverview());

        view.setOnClickListener(event -> {
            helper.launchNewViewWithModel(episode, view.getContext(), SingleEpisodeActivity.class);
        });
    }
}
