package com.sabbirmeraj.voboghure;

public class UserProfile{
    String id,userID, criteria, value;

    public UserProfile() {
    }

    public UserProfile(String id, String userID, String criteria, String value) {
        this.id = id;
        this.userID = userID;
        this.criteria = criteria;
        this.value = value;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
