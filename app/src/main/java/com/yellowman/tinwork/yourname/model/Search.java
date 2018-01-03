package com.yellowman.tinwork.yourname.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 23/11/2017.
 * Created by Didier Youn on 23/11/2017.
 * Created by Abdel-Latif Mabrouck on 23/11/2017.
 * Created by Antoine Renault on 23/11/2017.
 */

public class Search implements Parcelable {

    public Search() {}

    private List<Series> data;
    public static final Parcelable.Creator<Search> CREATOR = new Parcelable.Creator<Search>() {

        /**
         *
         * @param parcel
         * @return
         */
        @Override
        public Search createFromParcel(Parcel parcel) {
            return new Search(parcel);
        }

        /**
         *
         * @param i
         * @return
         */
        @Override
        public Search[] newArray(int i) {
            return new Search[0];
        }
    };

    /**
     * Search::Constructor
     * /!\ Caution this constructor use by the Parcel
     */
    public Search(Parcel parcel) {
        parcel.readTypedList(data, Series.CREATOR);
    }

    /**
     * Set Data
     * @param series
     */
    public void setData(List<Series> series) {
        this.data = series;
    }

    /**
     * Get Data
     * @return
     */
    public List<Series> getData() {
        return this.data;
    }

    /**
     * Describe Contents
     *
     * @return
     */
    @Override
    public int describeContents() { return 0; }

    /**
     * Write To Parcel
     *
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(data);
    }
}
