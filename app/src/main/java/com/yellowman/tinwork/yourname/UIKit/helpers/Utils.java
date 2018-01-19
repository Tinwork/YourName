package com.yellowman.tinwork.yourname.UIKit.helpers;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by Marc Intha-amnouay on 14/12/2017.
 * Created by Didier Youn on 14/12/2017.
 * Created by Abdel-Atif Mabrouck on 14/12/2017.
 * Created by Antoine Renault on 14/12/2017.
 */

public class Utils {

    /**
     * Set Text View Icon
     * @param textview TextView
     * @param resources int
     * @param offset int[]
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
     * @param ly Activity
     * @return int
     */
    public static int getLinearLayoutOrientation(Activity ly) {
        if (ly.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return LinearLayout.VERTICAL;

        return LinearLayout.HORIZONTAL;
    }

    /**
     * Get Screen DPI
     *
     * @param w Window
     * @return int
     */
    public static int getScreenDPI(Window w) {
        DisplayMetrics metrics = new DisplayMetrics();
        w.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.densityDpi;
    }

    /**
     * Get Array List From Realm
     *
     * @param data RealmList<String>
     * @return ArrayList<String>
     */
    public static ArrayList<String> getArrayListFromRealm(RealmList<String> data) {
        ArrayList<String> list = new ArrayList<>();

        for (String d: data) {
            list.add(d);
        }

        return list;
    }

    /**
     * Set Collapsing Toolbar Scrim Color
     *
     * @param l Collapsing Toolbar Layout
     * @param color color
     */
    public static void setCollapsingToolbarScrimColor(CollapsingToolbarLayout l, int color) {
        l.setContentScrimColor(color);
        l.setStatusBarScrimColor(color);
    }
}
