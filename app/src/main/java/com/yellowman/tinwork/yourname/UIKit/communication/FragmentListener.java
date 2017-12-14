package com.yellowman.tinwork.yourname.UIKit.communication;

import android.os.Parcelable;

/**
 * Created by Marc Intha-amnouay on 14/12/2017.
 * Created by Didier Youn on 14/12/2017.
 * Created by Abdel-Atif Mabrouck on 14/12/2017.
 * Created by Antoine Renault on 14/12/2017.
 */

public interface FragmentListener {
    /**
     *
     * @param parcel
     */
    void notifyData(Parcelable parcel);
}
