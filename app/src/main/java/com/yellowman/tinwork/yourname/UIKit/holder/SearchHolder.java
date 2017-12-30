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
import com.yellowman.tinwork.yourname.utils.Utils;

/**
 * Created by Marc Intha-amnouay on 30/12/2017.
 * Created by Didier Youn on 30/12/2017.
 * Created by Abdel-Atif Mabrouck on 30/12/2017.
 * Created by Antoine Renault on 30/12/2017.
 */

public class SearchHolder extends RecyclerView.ViewHolder {

    private ImageView imgView;
    private TextView  serieTitle;
    private TextView  serieStatus;
    protected View view;
    protected Helper helper;

    /**
     * Search Holder::Constructor
     *
     * @param viewItem
     */
    public SearchHolder(final View viewItem) {
        super(viewItem);
        this.view   = viewItem;
        this.helper = new Helper();
        prepareElements();
    }

    /**
     * Prepare Elements
     *
     * @void
     * @private
     */
    private void prepareElements() {
        this.imgView     = view.findViewById(R.id.serie_image);
        this.serieTitle  = view.findViewById(R.id.serie_title);
        this.serieStatus = view.findViewById(R.id.serie_status);
    }

    /**
     * Bind Data
     *
     * @param serie
     */
    public void bindData(Series serie) {
        if (!serie.getBanner().isEmpty()) {
            Glide.with(view.getContext()).load(Utils.buildMiscURI(Routes.IMG_PATH, serie.getBanner())).into(imgView);
        } else {
            Glide.with(view.getContext()).load(R.drawable.ic_account_circle_white_18dp).into(imgView);
        }

        serieTitle.setText(serie.getSeriesName());
        serieStatus.setText(serie.getStatus());

        this.view.setOnClickListener(event -> {
            helper.launchNewViewWithModel(serie, view.getContext(), FilmDetailsActivity.class);
        });
    }
}
