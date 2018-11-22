package com.sabbirmeraj.voboghure;

public class Post {
    String place, description, id, userID, uname, totalRating="0", totalPerson="0";

    int budget, duration;


    public Post(String id, String userID, String uname, String place, int budget, int duration, String description){
        this.id=id;
        this.userID=userID;
        this.uname =uname;
        this.place=place;
        this.budget=budget;
        this.duration=duration;
        this.description=description;
    }

    public Post(){

    }



    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTotalRating() {
        return totalRating;
    }








    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public void setTotalPerson(String totalPerson) {
        this.totalPerson = totalPerson;
    }

    public String getTotalPerson() {
        return totalPerson;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
