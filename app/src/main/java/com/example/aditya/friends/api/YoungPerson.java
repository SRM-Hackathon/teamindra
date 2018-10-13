package com.example.aditya.friends.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class YoungPerson {

    @SerializedName("unique_id")
    String uniqueId;

    @SerializedName("name")
    String name;

    @SerializedName("password")
    String password;

    @SerializedName("birthdate")
    String birthday;

    @SerializedName("gender")
    String gender;

    @SerializedName("gender_preference")
    String genderPreference;

    @SerializedName("contact")
    String contactNumber;

    @SerializedName("latitude")
    Double latitude;

    @SerializedName("longitude")
    Double longitude;

    @SerializedName("interests")
    ArrayList<String> interests;

    @SerializedName("profile_image")
    String profileImageUrl;

    @SerializedName("other_image")
    String verificationImageUrl;

    @SerializedName("message")
    String message;

    public YoungPerson(){
        this.name = "Aditya Kumar";
        this.birthday = "30/04/1999";
        this.password = "abcdefghijk";
        this.gender = "M";
        this.genderPreference = "M";
        this.contactNumber = "84499XXXXX";
        this.latitude = 10.2341234;
        this.longitude = 78.9873491;
        this.interests = new ArrayList<>();
        this.interests.add("AOJDG");
        this.interests.add("AOJDGafsa");
        this.uniqueId = "9we7e9f";
        this.profileImageUrl = "https://asdgoasdhfuoa.com";
        this.verificationImageUrl = "https://sadkjlhasdjk.com";
        this.message = "";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(String genderPreference) {
        this.genderPreference = genderPreference;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getInterests() {
        if (interests != null){
            return interests;
        } else {
            return null;
        }
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
