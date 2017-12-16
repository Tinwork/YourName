package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.utils.Utils;

/**
 * Created by Marc Intha-amnouay on 16/12/2017 (Fan de Lydie).
 * Created by Didier Youn on 16/12/2017.
 * Created by Abdel-Atif Mabrouck on 16/12/2017.
 * Created by Antoine Renault on 16/12/2017.
 */

public class PopularHolder extends RecyclerView.ViewHolder {

    private View viewItem;
    private ImageView imgView;
    private TextView filmTextView;

    /**
     * Popular Holder
     *
     * @param viewItem
     */
    public PopularHolder(final View viewItem) {
        super(viewItem);
        this.viewItem = viewItem;

        // Prepare the Layout Elements
        prepareElements();
    }

    /**
     * Bind Data
     *
     * @param serie
     */
    public void bindData(final Series serie) {
        filmTextView.setText(serie.getSeriesName());

        if (!serie.getBanner().isEmpty()) {
            Glide.with(viewItem).load(Utils.buildMiscURI(Routes.IMG_PATH, serie.getBanner())).into(imgView);
        }
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
