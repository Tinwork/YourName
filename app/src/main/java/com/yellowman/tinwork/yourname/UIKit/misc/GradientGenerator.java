package com.yellowman.tinwork.yourname.UIKit.misc;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.utils.AppUtils;

/**
 * Created by Marc Intha-amnouay on 27/11/2017.
 * Created by Didier Youn on 27/11/2017.
 * Created by Abdel-Atif Mabrouck on 27/11/2017.
 * Created by Antoine Renault on 27/11/2017.
 */

public class GradientGenerator {

    private Context ctx;
    private int[][] colorsID;
    private RelativeLayout rLayout;
    private LinearLayout lLayout;
    private final String ID = "yourname::gradient::idx";

    /**
     * Constructor
     * @param ctx
     */
    public GradientGenerator(Context ctx, RelativeLayout rLayout, LinearLayout lLayout) {
        this.ctx = ctx;
        this.rLayout = rLayout;
        this.lLayout = lLayout;
        this.colorsID = this.getShadowColors();
    }

    /**
     * Get Shadow Colors
     *
     * @return
     * @protected
     */
    protected int[][] getShadowColors() {
        // Get a reference of every color use for the shadow generator
        int starBlue     = ContextCompat.getColor(ctx, R.color.starBlue);
        int orchidPink   = ContextCompat.getColor(ctx, R.color.orchidPink);
        int deepPurple   = ContextCompat.getColor(ctx, R.color.deepPurple);
        int neonPurple   = ContextCompat.getColor(ctx, R.color.neonPurple);
        int luminousStar = ContextCompat.getColor(ctx, R.color.luminousStar);
        int deepBrightSp = ContextCompat.getColor(ctx, R.color.deepBrightSpace);


        int[][] colors = {{starBlue, deepPurple}, {orchidPink, neonPurple}, {luminousStar, deepBrightSp}};
        return colors;
    }

    /**
     * Build Background Shadow Color
     *
     * @return
     */
    public int buildBackgroundGradientColor() {
        int idx;
        String savedIdx = AppUtils.getSharedPreference(ctx, ID);

        if (savedIdx.isEmpty()) {
            idx = AppUtils.getRandomNumber(0, 2);
            AppUtils.saveSharedPreference(ctx, ID, String.valueOf(idx));
        } else {
            idx = Integer.valueOf(savedIdx);
        }


        int colors[] = {colorsID[idx][0], colorsID[idx][1]};

        GradientDrawable gd = new GradientDrawable();

        // In case if the App is use by old android device, use the first background color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            gd.setColors(colors);
            gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            gd.setShape(GradientDrawable.RECTANGLE);

            if (rLayout != null)
                rLayout.setBackground(gd);
            else
                lLayout.setBackground(gd);
        } else {
            if (rLayout != null)
                rLayout.setBackgroundColor(colorsID[idx][0]);
            else
                lLayout.setBackgroundColor(colorsID[idx][0]);
        }


        if (idx == 0) {
            return R.color.starBlue;
        } else if (idx == 1) {
            return R.color.orchidPink;
        } else {
            return R.color.luminousStar;
        }
    }
}
