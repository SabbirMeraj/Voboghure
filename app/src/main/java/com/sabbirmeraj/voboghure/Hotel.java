package com.sabbirmeraj.voboghure;

public class Hotel {
    String name, place, phoneNumber, address, review;


    public Hotel() {
    }

    public Hotel(String name, String place, String phoneNumber, String address, String review) {
        this.name = name;
        this.place = place;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
