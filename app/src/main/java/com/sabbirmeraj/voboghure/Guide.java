package com.sabbirmeraj.voboghure;

public class Guide {
    String name, place, phoneNumber, review;

    public Guide() {
    }

    public Guide(String name, String place, String phoneNumber, String review) {
        this.name = name;
        this.place = place;
        this.phoneNumber = phoneNumber;
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
