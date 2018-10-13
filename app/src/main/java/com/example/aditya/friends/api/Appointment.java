package com.example.aditya.friends.api;

import com.google.gson.annotations.SerializedName;

public class Appointment  {

    @SerializedName("")
    String uniqueIdOld;


    @SerializedName("")
    String uniqueIdYoung;

    @SerializedName("date")
    String date;

    @SerializedName("time")
    String time;

    @SerializedName("location")
    String location;

    public String getUniqueIdOld() {
        return uniqueIdOld;
    }

    public void setUniqueIdOld(String uniqueIdOld) {
        this.uniqueIdOld = uniqueIdOld;
    }

    public String getUniqueIdYoung() {
        return uniqueIdYoung;
    }

    public void setUniqueIdYoung(String uniqueIdYoung) {
        this.uniqueIdYoung = uniqueIdYoung;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
