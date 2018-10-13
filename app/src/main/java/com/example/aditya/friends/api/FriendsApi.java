package com.example.aditya.friends.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface FriendsApi {

    @POST("/register/old")
    Call<OldPerson> saveOldUser(@Body OldPerson oldPerson);

    @POST("/login/old")
    Call<OldPerson> verifyCredentials(@Body LoginCredential loginCredential);

    @POST("/fetch")
    Call<YoungPerson> getYoungPeople(@Field("unique_id") String uniqueId);

}
