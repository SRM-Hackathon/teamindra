package com.example.aditya.friends.create_account;

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

public class PasswordFragment extends Fragment {

    private TextView mErrorMessageTextView;
    private TextView mPasswordStrength;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private TextView mNextTextView;

    public interface PasswordFragmentListener{
        void onPasswordSubmit(String password);
    }

    private PasswordFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PasswordFragmentListener){
            mListener = (PasswordFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_password, container, false);

        mErrorMessageTextView = (TextView) view.findViewById(R.id.create_account_error_password_textView);
        mPasswordStrength = (TextView) view.findViewById(R.id.create_acccount_password_strength);
        mPasswordEditText = (EditText) view.findViewById(R.id.create_account_password_password_editText);
        mConfirmPasswordEditText = (EditText) view.findViewById(R.id.create_account_password_confirm_editText);
        mNextTextView = (TextView) view.findViewById(R.id.create_account_password_next);

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();
                if(password.equals(confirmPassword)){
                    if (password.length() >= 6){
                        mListener.onPasswordSubmit(password);
                        BirthdayFragment birthdayFragment = new BirthdayFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                                .replace(R.id.create_account_frameLayout, birthdayFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                } else {
                    mErrorMessageTextView.setVisibility(View.VISIBLE);
                }
            }
        });


        return view;
    }
}
