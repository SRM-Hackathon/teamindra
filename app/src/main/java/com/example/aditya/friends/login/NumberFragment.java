package com.example.aditya.friends.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aditya.friends.R;
import com.example.aditya.friends.create_account.BirthdayFragment;

public class NumberFragment extends Fragment{

    private EditText mNumberEditText;
    private TextView mNextTextView;
    private TextView mErrorMessageTextView;

    public interface NumberFragmentListener{
        void onNumberSubmit(String number);
    }

    private NumberFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NumberFragmentListener){
            mListener = (NumberFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_number, container, false);

        mNumberEditText = (EditText) view.findViewById(R.id.login_number_editText);
        mNextTextView = (TextView) view.findViewById(R.id.login_number_next);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.login_error_number_textView);

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mNumberEditText.getText().toString().trim();
                if(number.length() == 10){

                    mListener.onNumberSubmit(number);

                    OtpVerficationFragment otpVerficationFragment = new OtpVerficationFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                            .replace(R.id.login_frameLayout, otpVerficationFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    mErrorMessageTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}
