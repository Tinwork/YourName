package com.yellowman.tinwork.yourname.UIKit.iface;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 14/12/2017.
 * Created by Didier Youn on 14/12/2017.
 * Created by Abdel-Atif Mabrouck on 14/12/2017.
 * Created by Antoine Renault on 14/12/2017.
 *
 * @TODO rename this as this name is nonsense
 */

public interface FragmentCommunication {
    /**
     * Set Parcelable
     *
     * @param parcel
     * @param key
     */
    void setParcelable(Parcelable parcel, String key);

    /**
     * Fire Fragment Event
     *      Fire an event to the listener of the Holder
     */
    void fireFragmentEvent(@Nullable HashMap<String, Parcelable> parcels);

    /**
     * Init Fragment Listeners
     *      Implement the listeners of each fragment into the Activity
     */
    void initFragmentListeners();
}
