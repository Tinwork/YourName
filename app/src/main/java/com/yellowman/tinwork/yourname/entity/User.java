package com.yellowman.tinwork.yourname.entity;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public class User extends RealmObject {

    @PrimaryKey
    private String userName;
    private String language;
    private RealmList<String> favorites;

    /**
     * User::Constructor
     *
     */
    public User() {}

    /**
     * Get Language
     *
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set Language
     *
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Get User Name
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set User Name
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get Favorites
     *
     * @return
     */
    public RealmList<String> getFavorites() {
        return favorites;
    }

    /**
     * Set Favorites
     *
     * @param favorites
     */
    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = new RealmList<>();
        this.favorites.addAll(favorites);
    }
}
