package com.yellowman.tinwork.yourname.UIKit.iface;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.yellowman.tinwork.yourname.model.Series;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 14/12/2017.
 * Created by Didier Youn on 14/12/2017.
 * Created by Abdel-Atif Mabrouck on 14/12/2017.
 * Created by Antoine Renault on 14/12/2017.
 *
 */

public interface FragmentCommunication {
    /**
     * Set Parcelable
     *
     * @param parcel List<Series><
     * @param key String
     */
    void setParcelable(List<Series> parcel, String key);

    /**
     * Fire Fragment Event
     *      Fire an event to the listener of the Holder
     */
    void fireFragmentEvent(HashMap<String, List<Series>> parcels);

    /**
     * Init Fragment Listeners
     *      Implement the listeners of each fragment into the Activity
     */
    void initFragmentListeners();
}
