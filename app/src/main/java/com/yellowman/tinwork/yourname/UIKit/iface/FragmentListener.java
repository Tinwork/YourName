package com.yellowman.tinwork.yourname.UIKit.iface;

import android.os.Parcelable;
import android.support.annotation.Nullable;

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
     * @param parcel
     */
    void notifyData(Parcelable parcel);

    /**
     * Bind Recycle View
     */
    void bindRecycleView(List<?> data);
}
