package com.yellowman.tinwork.yourname.UIKit.iface;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.yellowman.tinwork.yourname.model.Series;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 14/12/2017.
 * Created by Didier Youn on 14/12/2017.
 * Created by Abdel-Atif Mabrouck on 14/12/2017.
 * Created by Antoine Renault on 14/12/2017.
 */

public interface FragmentListener {
    /**
     * Notify Data
     *
     * /!\ Need review though...
     * @param parcels
     */
    void notifyData(List<Series> parcels);
}
