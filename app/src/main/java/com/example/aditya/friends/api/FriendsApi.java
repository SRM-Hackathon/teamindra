package com.example.aditya.friends.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FriendsApi {

    @POST("/register/old")
    Call<OldPerson> saveOldUser(@Body OldPerson oldPerson);

    @POST("/login/old")
    Call<OldPerson> verifyCredentials(@Body LoginCredential loginCredential);

    @GET("/fetch")
    Call<ArrayList<YoungPerson>> getYoungPeople(@Query("unique_id") String uniqueId);

}
