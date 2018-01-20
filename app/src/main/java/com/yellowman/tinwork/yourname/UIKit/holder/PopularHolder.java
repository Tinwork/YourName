package com.yellowman.tinwork.yourname.UIKit.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
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

public class PopularHolder extends RecyclerView.ViewHolder {

    private View viewItem;
    private ImageView imgView;
    private TextView filmTextView;
    protected Helper helper;

    /**
     * Popular Holder
     *
     * @param viewItem View
     */
    public PopularHolder(final View viewItem) {
        super(viewItem);
        this.viewItem = viewItem;
        this.helper = new Helper();

        // Prepare the Layout Elements
        prepareElements();
    }

    /**
     * Bind Data
     *
     * @param serie Series
     */
    public void bindData(final Series serie) {
        Context ctx = viewItem.getContext().getApplicationContext();
        filmTextView.setText(serie.getSeriesName());

        // Set image
        String bannerURL = serie.getId()+"-1.jpg";
        Utils.setImgView(ctx, bannerURL, imgView);

        // Set listeners
        viewItem.setOnClickListener(event -> {
            helper.launchNewViewWithModel(serie, viewItem.getContext(), FilmDetailsActivity.class);
        });
    }

    /**
     * Prepare Elements
     */
    private void prepareElements() {
        // Prepare elements of the Holder
        imgView = viewItem.findViewById(R.id.banner);
        filmTextView = viewItem.findViewById(R.id.film_name);
    }
}
