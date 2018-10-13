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
import com.example.aditya.friends.api.ApiManager;

public class PasswordFragment extends Fragment {

    private EditText mPasswordEditText;
    private TextView mErrorMessage;
    private TextView mNextTextView;
    private TextView mPasswordStrengthTextView;

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
        View view = inflater.inflate(R.layout.fragment_login_password , container, false);


        mPasswordEditText = (EditText) view.findViewById(R.id.login_password_password_editText);
        mErrorMessage = (TextView) view.findViewById(R.id.login_error_password_textView);
        mNextTextView = (TextView) view.findViewById(R.id.login_password_next);
        mPasswordStrengthTextView = (TextView) view.findViewById(R.id.login_password_strength);

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPasswordEditText.getText().toString().trim();
                if (password.length() >= 6){
                    mListener.onPasswordSubmit(password);
                } else {
                    mErrorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

}
