package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.helpers.Helper;
import com.yellowman.tinwork.yourname.filmDetails.FilmDetails;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.utils.Utils;

/**
 * Created by Marc Intha-amnouay on 10/12/2017.
 * Created by Didier Youn on 10/12/2017.
 * Created by Abdel-Atif Mabrouck on 10/12/2017.
 * Created by Antoine Renault on 10/12/2017.
 */

public class TrendingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView filmTextView;
    private ImageView imgView;
    private Series series;
    private View v;

    /**
     * TrendingHolder::Constructor
     * @param itemView
     */
    public TrendingHolder(final View itemView) {
        super(itemView);

        v = itemView;
        v.setOnClickListener(this);
        imgView = (ImageView) itemView.findViewById(R.id.banner);
        filmTextView = (TextView) itemView.findViewById(R.id.film_name);
    }



    /**
     * Bind Data
     * @param seriesModel
     */
    public void bindData(final Series seriesModel) {
        series = seriesModel;
        filmTextView.setText(seriesModel.getSeriesName());

        String bannerURL = seriesModel.getBanner();
        if (!bannerURL.isEmpty()) {
            Log.d("Debug", seriesModel.getBanner());
            Glide.with(v).load(Utils.buildMiscURI(Routes.IMG_PATH, bannerURL)).into(imgView);
        }

        // set the
    }

    /**
     * Set On Click Listener
     *      Set the onclick listener on a ViewHolder
     */
    @Override
    public void onClick(View vs) {
        Helper helper = new Helper();
        v.setOnClickListener(event -> helper.launchNewViewWithModel(series, vs.getContext(), FilmDetails.class));
    }
}
