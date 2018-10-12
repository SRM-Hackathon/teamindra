package com.example.aditya.friends.api;

import com.google.gson.annotations.SerializedName;

public class LoginCredential {

    @SerializedName("contact")
    String contactNumber;

    @SerializedName("password")
    String password;

    public LoginCredential(){
        this.contactNumber = "1234567890";
        this.password = "12345678";
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
