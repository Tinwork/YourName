package com.yellowman.tinwork.yourname.UIKit.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.LinearLayout;
import android.widget.TextView;

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
     * @param model
     * @param fromAct
     * @param toAct
     */
    public void launchNewViewWithModel(Parcelable model, Context fromAct, Class<T> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra("Entity", model);
        fromAct.startActivity(intent);
    }

    /**
     * Launch New View With Array Model
     *
     * @param model
     * @param fromAct
     * @param toAct
     */
    public void launchNewViewWithArrayModel(Parcelable[] model, Context fromAct, Class<T> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra("EntityArray", model);
        fromAct.startActivity(intent);
    }
}
