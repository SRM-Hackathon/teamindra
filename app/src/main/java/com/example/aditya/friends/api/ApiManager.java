package com.example.aditya.friends.api;

import com.example.aditya.friends.utils.FriendsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static FriendsApi mService;
    private static ApiManager mApiManager;

    private ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FriendsUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(FriendsApi.class);
    }

    public static ApiManager getInstance(){
        if(mApiManager == null){
            mApiManager = new ApiManager();
        }
        return mApiManager;
    }

    public void createOldUser(OldPerson oldPerson, Callback<OldPerson> callback){
        Call<OldPerson> oldPersonCall = mService.saveOldUser(oldPerson);
        oldPersonCall.enqueue(callback);
    }

    public void verifyCredentials(LoginCredential loginCredential, Callback<OldPerson> callback){
        Call<OldPerson> verifyCredentialCall = mService.verifyCredentials(loginCredential);
        verifyCredentialCall.enqueue(callback);
    }

    public void getYoungPeople(String uniqueId, Callback<YoungPerson> callback){
        Call<YoungPerson> youngPersonCall = mService.getYoungPeople(uniqueId);
        youngPersonCall.enqueue(callback);
    }

}
