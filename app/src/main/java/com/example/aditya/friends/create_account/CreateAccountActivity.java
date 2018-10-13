package com.example.aditya.friends.create_account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aditya.friends.R;
import com.example.aditya.friends.api.ApiManager;
import com.example.aditya.friends.api.OldPerson;
import com.example.aditya.friends.home.HomeActivity;
import com.example.aditya.friends.utils.FriendsUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateAccountActivity extends AppCompatActivity implements NameFragment.NameFragmentListner,
        NumberFragment.NumberFragmentListner,
        GenderPreferenceFragment.GenderPreferenceFragmentListener,
        GenderFragment.GenderFragmentListener,
        InterestFragment.InterestFragmentListener,
        BirthdayFragment.BirthdayFragmentListener,
        PasswordFragment.PasswordFragmentListener,
        ProfilePictureFragment.ProfilePictureFragmentListener,
        IdentityVerificationFragment.IdentityVerificationFragmentListener{

    private OldPerson mOldPersonData;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static ApiManager mApiManager;

    private ProgressBar mProgressBar;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        FirebaseApp.initializeApp(this);

        mProgressBar = (ProgressBar) findViewById(R.id.create_account_progress_bar);
        mFrameLayout = (FrameLayout) findViewById(R.id.create_account_frameLayout);

        mOldPersonData = new OldPerson();

        TypeFragment typeFragment = new TypeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.create_account_frameLayout, typeFragment)
                .commit();

        mApiManager = ApiManager.getInstance();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mOldPersonData.setLatitude(location.getLatitude());
                                mOldPersonData.setLongitude(location.getLongitude());
                            }
                        }
                    });
        } else {
            Toast.makeText(CreateAccountActivity.this, "Allow Location Access to  create account (Settings)", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onNameSubmit(String name) {
        mOldPersonData.setName(name);
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        mOldPersonData.setUniqueId(randomUUIDString);
    }


    @Override
    public void onPasswordSubmit(String password) {
        mOldPersonData.setPassword(password);
    }

    @Override
    public void onGenderSubmit(String gender) {
        mOldPersonData.setGender(gender);
    }

    @Override
    public void onInterestSubmit(ArrayList<String> interests) {
        mOldPersonData.setInterests(interests);
    }

    @Override
    public void onNumberSubmit(String number) {
        mOldPersonData.setContactNumber(number);
    }

    @Override
    public void onBirthdaySubmit(String birthday) {
        mOldPersonData.setBirthday(birthday);
        Toast.makeText(CreateAccountActivity.this, birthday, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onGenderPreferenceSubmit(String genderPreference) {
        mOldPersonData.setGenderPreference(genderPreference);
    }

    private void sendDataToServer(){
        mFrameLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        mApiManager.createOldUser(mOldPersonData, new Callback<OldPerson>() {
            @Override
            public void onResponse(Call<OldPerson> call, Response<OldPerson> response) {
                mProgressBar.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);

                OldPerson oldPerson = response.body();
                if (response.isSuccessful() && oldPerson != null){
                    Toast.makeText(CreateAccountActivity.this, "onResponse : successful", Toast.LENGTH_SHORT).show();
                    FriendsUtils.mOldPersonData = mOldPersonData;
                    Intent homeIntent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Response is : " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OldPerson> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);

                Toast.makeText(CreateAccountActivity.this, "onFailure " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSuccessfulUploadImage(String url) {
        mOldPersonData.setVerificationImageUrl(url);
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        sendDataToServer();
    }

    @Override
    public void onProfilePictureUpload(String url) {
        mOldPersonData.setProfileImageUrl(url);
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }
}
