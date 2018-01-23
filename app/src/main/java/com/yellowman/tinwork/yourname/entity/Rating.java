package com.yellowman.tinwork.yourname.entity;

/**
 * Created by Marc Intha-amnouay on 23/01/2018.
 * Created by Didier Youn on 23/01/2018.
 * Created by Abdel-Atif Mabrouck on 23/01/2018.
 * Created by Antoine Renault on 23/01/2018.
 */

public class Rating {
    private int rating;
    private int ratingItemId;
    private String ratingType;


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRatingItemId() {
        return ratingItemId;
    }

    public void setRatingItemId(int ratingItemId) {
        this.ratingItemId = ratingItemId;
    }

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
    }
}
