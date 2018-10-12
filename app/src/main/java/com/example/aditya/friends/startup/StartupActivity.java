package com.example.aditya.friends.startup;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.friends.R;

public class StartupActivity extends AppCompatActivity {


    private TextView mCreateAccountTextView;
    private TextView mLoginTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        mCreateAccountTextView = (TextView) findViewById(R.id.createAccount_textView);
        mLoginTextView = (TextView) findViewById(R.id.login_textView);

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to create an account activity
            }
        });

        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to login activity
            }
        });

        String ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Toast.makeText(this, ID, Toast.LENGTH_LONG).show();

    }
}
