package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.activities.filmDetail.FilmDetailsActivity;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.utils.AppUtils;

/**
 * Created by Marc Intha-amnouay on 16/12/2017.
 * Created by Didier Youn on 16/12/2017.
 * Created by Abdel-Atif Mabrouck on 16/12/2017.
 * Created by Antoine Renault on 16/12/2017.
 */

public class FavoriteHolder extends RecyclerView.ViewHolder {

    private ImageView imgView;
    private TextView  filmTextView;
    private TextView  rateTextView;
    private TextView  runtimeTextView;
    protected View viewItem;
    protected Helper helper;
    protected String serieID;


    /**
     *
     * @param viewItem View
     */
    public FavoriteHolder(final View viewItem) {
        super(viewItem);
        this.viewItem = viewItem;
        this.helper = new Helper();

        // Prepare the Layout element
        prepareElements();
    }

    /**
     * Bind Data
     *
     * @param serie Serie
     */
    public void bindData(final Series serie) {
        serieID = serie.getId();
        filmTextView.setText(serie.getSeriesName());

        if (!serie.getBanner().isEmpty()) {
            Glide.with(viewItem.getContext().getApplicationContext()).load(AppUtils.buildMiscURI(Routes.IMG_PATH, serie.getBanner())).into(imgView);
        } else {
            Glide.with(viewItem.getContext().getApplicationContext()).load(R.drawable.totoro_error).into(imgView);
        }

        // Set other kind of props with 'FIX' Data in the meantime
        rateTextView.setText(serie.getSiteRating());

        String g = "";
        for (String genre: serie.getGenre()) {
            g += " "+genre;
        }
        runtimeTextView.setText(g+serie);

        // OnClick Listener
        viewItem.setOnClickListener(event -> {
            helper.launchNewViewWithModel(serie, viewItem.getContext(), FilmDetailsActivity.class);
        });
    }

    /**
     * Prepare Elements
     */
    private void prepareElements() {
        imgView = viewItem.findViewById(R.id.banner);
        filmTextView = viewItem.findViewById(R.id.film_name);
        rateTextView = viewItem.findViewById(R.id.rating);
        runtimeTextView = viewItem.findViewById(R.id.genre_time);
    }

    /**
     * Get Series From Holder
     *
     * @return String serie ID
     */
    public String getSeriesFromHolder() {
        return serieID;
    }
}
