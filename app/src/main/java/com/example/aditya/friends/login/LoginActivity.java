package com.example.aditya.friends.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aditya.friends.R;
import com.example.aditya.friends.api.ApiManager;
import com.example.aditya.friends.api.LoginCredential;
import com.example.aditya.friends.api.OldPerson;
import com.example.aditya.friends.create_account.CreateAccountActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements NumberFragment.NumberFragmentListener,
        PasswordFragment.PasswordFragmentListener{

    private LoginCredential mLoginCredentials;

    private ApiManager mApiManager;

    private ProgressBar mProgressBar;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        mFrameLayout = (FrameLayout) findViewById(R.id.login_frameLayout);

        mLoginCredentials = new LoginCredential();

        mApiManager = ApiManager.getInstance();

        NumberFragment numberFragment = new NumberFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.login_frameLayout, numberFragment)
                .commit();

    }

    @Override
    public void onNumberSubmit(String number) {
        mLoginCredentials.setContactNumber(number);
    }

    @Override
    public void onPasswordSubmit(String password) {
        mLoginCredentials.setPassword(password);
        checkDataFromServer();
    }

    private void checkDataFromServer(){
        mFrameLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        //TODO : Send request to the server to check for password and number

        mApiManager.verifyCredentials(mLoginCredentials, new Callback<OldPerson>() {
            @Override
            public void onResponse(Call<OldPerson> call, Response<OldPerson> response) {
                mProgressBar.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);

                OldPerson oldPerson = response.body();
                if (response.isSuccessful() && oldPerson != null){
                    Toast.makeText(LoginActivity.this, "onResponse : successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Response is : " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OldPerson> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);

                Toast.makeText(LoginActivity.this, "onFailure " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
