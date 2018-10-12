package com.example.aditya.friends.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FriendsApi {

    @POST("/register/old")
    Call<OldPerson> saveOldUser(@Body OldPerson oldPerson);

    @POST("/login/old")
    Call<OldPerson> verifyCredentials(@Body LoginCredential loginCredential);

}
