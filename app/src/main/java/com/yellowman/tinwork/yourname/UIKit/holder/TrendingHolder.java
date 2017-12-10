package com.yellowman.tinwork.yourname.UIKit.holder;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.utils.Utils;

/**
 * Created by Marc Intha-amnouay on 10/12/2017.
 * Created by Didier Youn on 10/12/2017.
 * Created by Abdel-Atif Mabrouck on 10/12/2017.
 * Created by Antoine Renault on 10/12/2017.
 */

public class TrendingHolder extends RecyclerView.ViewHolder {

    private TextView filmTextView;
    private ImageView imgView;
    private View v;

    /**
     * TrendingHolder::Constructor
     * @param itemView
     */
    public TrendingHolder(final View itemView) {
        super(itemView);

        v = itemView;
        imgView = (ImageView) itemView.findViewById(R.id.banner);
        filmTextView = (TextView) itemView.findViewById(R.id.film_name);
    }



    /**
     * Bind Data
     * @param seriesModel
     */
    public void bindData(final Series seriesModel) {
        filmTextView.setText(seriesModel.getSeriesName());

        if (seriesModel.getBanner() != null) {
            Log.d("Debug", seriesModel.getBanner());
           // Uri img = Uri.parse("https://www.thetvdb.com/banners/"+seriesModel.getBanner());
            Glide.with(v).load(Utils.buildMiscURI(Routes.IMG_PATH, seriesModel.getBanner())).into(imgView);
        }
    }

}
