package com.example.aditya.friends.login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aditya.friends.R;
import com.example.aditya.friends.create_account.BirthdayFragment;

public class OtpVerficationFragment extends Fragment {

    EditText mOtpEditText;
    TextView mResendOtpTextView;
    TextView mVerifyOtpTextView;
    TextView mErrorMessageTextView;

    private final static String CHANNEL_ID = "OTP";

    private int mOtp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_otp_verification, container, false);

        mOtpEditText = (EditText) view.findViewById(R.id.login_otp_verification_editText);
        mResendOtpTextView = (TextView) view.findViewById(R.id.login_otp_verification_resend_otp_textView);
        mVerifyOtpTextView = (TextView) view.findViewById(R.id.login_otp_verification_next);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.login_error_otp_verification_textView);

        createNotificationChannel();
        mVerifyOtpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(mOtpEditText.getText().toString().trim()) != mOtp){
                    mErrorMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    PasswordFragment passwordFragment = new PasswordFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                            .replace(R.id.login_frameLayout, passwordFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        mResendOtpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtp = generateOtp();
                sendOtpNotification(mOtp);
            }
        });

        mOtp = generateOtp();
        sendOtpNotification(mOtp);

        return view;
    }

    private int generateOtp(){
        int otp = (int)(10000*Math.random());
        if(otp < 1000){
            otp += 1000;
        }
        return otp;
    }

    private void sendOtpNotification(int otp){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_security_grey_24dp)
                .setContentTitle("OTP")
                .setContentText("Your otp is : " + otp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(100, mBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "OTP Verification";
            String description = "Sends OTP for mobile number verification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
