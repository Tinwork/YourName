package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
import com.yellowman.tinwork.yourname.activities.episodeDetail.EpisodeListActivity;
import com.yellowman.tinwork.yourname.activities.filmDetail.FilmDetailsActivity;
import com.yellowman.tinwork.yourname.entity.Episode;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class SeasonsHolder extends RecyclerView.ViewHolder {

    private View v;
    private Helper helper;
    private TextView seasons;
    private TextView episodes_nb;
    protected RelativeLayout layout;
    private int dpi;
    private int listSize;

    /**
     * Episodes Holder
     *
     * @param v
     */
    public SeasonsHolder(View v, int size) {
        super(v);
        this.v = v;
        this.listSize = size;
        this.helper = new Helper<>();
        prepareElements();
    }

    /**
     * Bind Data
     *
     * @param episodes Episode[]
     */
    public void bindData(Episode[] episodes) {
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        int season      = episodes[0].getAiredSeason();
        int episodesNb  = episodes.length;


        seasons.setText(v.getContext().getString(R.string.season_text, String.valueOf(season)));
        episodes_nb.setText(v.getContext().getString(R.string.episode_text, String.valueOf(episodesNb)));


        switch (listSize) {
            case 1:
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case 2:
                params.width = this.dpi + 50;
                break;
            case 3:
                params.width = this.dpi / 2 + 50;
                break;
            default:
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        v.setOnClickListener(event -> {
            helper.launchNewViewWithArrayModel(episodes, v.getContext(), EpisodeListActivity.class);
        });
    }

    /**
     * Prepare Elements
     *
     */
    private void prepareElements() {
        this.seasons = v.findViewById(R.id.season_number);
        this.episodes_nb = v.findViewById(R.id.number_episodes);
        this.layout  = v.findViewById(R.id.season_parent_layout);
        this.dpi     = Utils.getScreenDPI(((FilmDetailsActivity) v.getContext()).getWindow());
    }
}
