package com.yellowman.tinwork.yourname.entity;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marc Intha-amnouay on 11/12/2017.
 * Created by Didier Youn on 11/12/20177.
 * Created by Abdel-Latif Mabrouck on 11/12/2017.
 * Created by Antoine Renault on 11/12/2017.
 */

public class Actor extends RealmObject implements Parcelable {

    public static final Parcelable.Creator<Actor> CREATOR = new Parcelable.Creator<Actor>() {

        @Override
        public Actor createFromParcel(Parcel source) {
            return new Actor(source);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[0];
        }
    };

    @PrimaryKey
    private int id;
    private String name;
    private String role;
    private String image;

    /**
     * Empty Constructor
     *
     */
    public Actor() {}

    /**
     * Actor::Constructor
     *   /!\   Constructor used by Realm
     *
     * @param in
     */
    public Actor(Parcel in) {
        id    = in.readInt();
        name  = in.readString();
        role  = in.readString();
        image = in.readString();
    }

    /**
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * @param name string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return string
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role string
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return string
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image string
     */
    public void setImage(String image) {
        this.image = image;
    }


    /**
     * Describe Contents
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write To Parcel
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(role);
        dest.writeString(image);
    }
}
