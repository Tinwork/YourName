package com.yellowman.tinwork.yourname.entity;

import io.realm.RealmObject;

/**
 * Created by Marc Intha-amnouay on 11/12/2017.
 * Created by Didier Youn on 11/12/20177.
 * Created by Abdel-Latif Mabrouck on 11/12/2017.
 * Created by Antoine Renault on 11/12/2017.
 */

public class Actor extends RealmObject {
    private int id;
    private String name;
    private String role;
    private String image;

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
}
