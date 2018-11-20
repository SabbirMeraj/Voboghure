package com.sabbirmeraj.voboghure;

public class Rating {
    String ratingID, postyID,userID;
    float rating;

    public Rating() {
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Rating(String ratingId, String postyId, String userID, float rating) {
        this.ratingID = ratingId;
        this.postyID = postyId;
        this.userID = userID;
        this.rating=rating;

    }


    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public String getPostyID() {
        return postyID;
    }

    public void setPostyID(String postyID) {
        this.postyID = postyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
