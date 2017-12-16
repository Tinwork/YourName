package com.yellowman.tinwork.yourname.UIKit.helpers;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Marc Intha-amnouay on 14/12/2017.
 * Created by Didier Youn on 14/12/2017.
 * Created by Abdel-Atif Mabrouck on 14/12/2017.
 * Created by Antoine Renault on 14/12/2017.
 */

public class Utils {

    /**
     * Set Text View Icon
     * @param textview
     * @param resources
     */
    public static void setTextViewIcon(TextView textview, int resources, int[] offset) {
        if (offset == null) {
            textview.setCompoundDrawablesWithIntrinsicBounds(resources, 0, 0, 0);
        } else {
            textview.setCompoundDrawablesWithIntrinsicBounds(resources, offset[0], offset[1], offset[2]);
        }
    }

    /**
     * Get Linear Layout Orientation
     *
     * @param ly
     * @return
     */
    public static int getLinearLayoutOrientation(Activity ly) {
        if (ly.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return LinearLayout.VERTICAL;

        return LinearLayout.HORIZONTAL;
    }
}
