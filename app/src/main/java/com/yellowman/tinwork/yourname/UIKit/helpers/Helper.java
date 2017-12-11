package com.yellowman.tinwork.yourname.UIKit.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

/**
 * Created by Marc Intha-amnouay on 11/12/2017.
 * Created by Didier Youn on 11/12/2017.
 * Created by Abdel-Atif Mabrouck on 11/12/2017.
 * Created by Antoine Renault on 11/12/2017.
 */

public class Helper<T> {

    /**
     * Launch New View With Model
     * @param model
     */
    public void launchNewViewWithModel(Parcelable model, Context fromAct, Class<T> toAct) {
        Intent intent = new Intent(fromAct, toAct);
        intent.putExtra("Entity", model);
        fromAct.startActivity(intent);
    }
}
