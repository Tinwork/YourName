package com.yellowman.tinwork.yourname.UIKit.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yellowman.tinwork.yourname.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Marc Intha-amnouay on 11/12/2017.
 * Created by Didier Youn on 11/12/2017.
 * Created by Abdel-Atif Mabrouck on 11/12/2017.
 * Created by Antoine Renault on 11/12/2017.
 */

public class Helper<T> {

    /**
     * Launch New View With Model
     *
     * @param model Parcelable
     * @param fromAct Context
     * @param toAct Class
     */
    public void launchNewViewWithModel(Parcelable model, Context fromAct, Class<T> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra("Entity", model);
        fromAct.startActivity(intent);
    }

    /**
     * Launch New View With Array Model
     *
     * @param model Parcelable
     * @param fromAct Context
     * @param toAct Class
     */
    public void launchNewViewWithArrayModel(Parcelable[] model, Context fromAct, Class<T> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra("EntityArray", model);
        fromAct.startActivity(intent);
    }

    /**
     * Launch With Empty Data
     *
     * @param from From
     * @param dest Destination
     */
    public void launchWithEmptyData(Context from, Class<T> dest) {
        Intent intent = new Intent(from, dest);

        from.startActivity(intent);
    }

    /**
     * Send Intent To Choosers
     *
     * @param intent Intent
     * @param ctx Context
     */
    public void sendIntentToChooser(Intent intent, Context ctx) {
        String title = ctx.getResources().getString(R.string.choose_title);
        Intent chooser = Intent.createChooser(intent, title);
        ctx.startActivity(chooser);
    }
}
