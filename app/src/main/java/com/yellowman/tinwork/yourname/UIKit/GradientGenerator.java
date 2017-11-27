package com.yellowman.tinwork.yourname.UIKit;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.utils.Utils;

/**
 * Created by Marc Intha-amnouay on 27/11/2017.
 * Created by Didier Youn on 27/11/2017.
 * Created by Abdel-Atif Mabrouck on 27/11/2017.
 * Created by Antoine Renault on 27/11/2017.
 */

public class GradientGenerator {

    private Context ctx;
    private int[] colorsID;
    private LinearLayout layout;

    /**
     * Constructor
     * @param ctx
     */
    public GradientGenerator(Context ctx, LinearLayout layout) {
        this.ctx = ctx;
        this.layout = layout;
        this.colorsID = this.getShadowColors();
    }

    /**
     * Get Shadow Colors
     * @return
     * @protected
     */
    protected int[] getShadowColors() {
        // Get a reference of every color use for the shadow generator
        int starBlue   = ContextCompat.getColor(ctx, R.color.starBlue);
        int orchidPink = ContextCompat.getColor(ctx, R.color.orchidPink);
        int deepPurple = ContextCompat.getColor(ctx, R.color.deepPurple);
        int neonPurple = ContextCompat.getColor(ctx, R.color.neonPurple);

        int[] colors = {starBlue, orchidPink, deepPurple, neonPurple};
        return colors;
    }

    /**
     * Build Background Shadow Color
     */
    public void buildBackgroundGradientColor() {
        int firstColor  = Utils.getRandomNumber(0, 3);
        int secondColor = Utils.getRandomNumber(0, 3);

        int colors[] = {colorsID[firstColor], colorsID[secondColor]};

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors
        );

        // In case if the App is use by old android device, use the first background color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout.setBackground(gd);
        } else {
            layout.setBackgroundColor(firstColor);
        }
    }
}
