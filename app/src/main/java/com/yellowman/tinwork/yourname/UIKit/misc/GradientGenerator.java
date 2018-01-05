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
        int starBlue   = ContextCompat.getColor(ctx, R.color.starBlue);
        int orchidPink = ContextCompat.getColor(ctx, R.color.orchidPink);
        int deepPurple = ContextCompat.getColor(ctx, R.color.deepPurple);
        int neonPurple = ContextCompat.getColor(ctx, R.color.neonPurple);

        int[][] colors = {{starBlue, deepPurple}, {orchidPink, neonPurple}};
        return colors;
    }

    /**
     * Build Background Shadow Color
     *
     * @return
     */
    public int buildBackgroundGradientColor() {
        int idx  = AppUtils.getRandomNumber(0, 1);

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

        return idx == 0 ? R.color.starBlue : R.color.orchidPink;
    }
}
