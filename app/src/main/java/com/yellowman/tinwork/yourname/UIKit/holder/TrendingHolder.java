package com.yellowman.tinwork.yourname.UIKit.holder;

import android.support.v7.widget.RecyclerView;
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

public class TrendingHolder extends RecyclerView.ViewHolder{

    private TextView filmTextView;
    private TextView typeTextView;
    private TextView airedTextView;
    private ImageView imgView;
    private Series series;
    private View v;
    protected Helper helper;

    /**
     * TrendingHolder::Constructor
     * @param itemView
     */
    public TrendingHolder(final View itemView) {
        super(itemView);


        v = itemView;
        imgView = (ImageView) itemView.findViewById(R.id.banner);
        filmTextView  = (TextView) itemView.findViewById(R.id.film_name);
        typeTextView  = (TextView) itemView.findViewById(R.id.status);
        airedTextView = (TextView) itemView.findViewById(R.id.firstAired);

        // Set the Icon of the Date
        helper = new Helper();
        com.yellowman.tinwork.yourname.UIKit.helpers.Utils.setTextViewIcon(airedTextView, R.drawable.ic_access_time_black_24dp, null);
    }

    /**
     * Bind Data
     * @param seriesModel
     */
    public void bindData(final Series seriesModel) {
        series = seriesModel;
        filmTextView.setText(seriesModel.getSeriesName());
        airedTextView.setText(seriesModel.getFirstAired());

        String bannerURL = seriesModel.getBanner();
        if (!bannerURL.isEmpty()) {
            Glide.with(v).load(Utils.buildMiscURI(Routes.IMG_PATH, bannerURL)).into(imgView);
        }

        String status = seriesModel.getStatus().toLowerCase().contains("ended") ? "End" : "On going";
        typeTextView.setText(status);

        // Set the listener of the item view
        v.setOnClickListener(event -> {
            helper.launchNewViewWithModel(series, v.getContext(), FilmDetails.class);
        });
    }
}
