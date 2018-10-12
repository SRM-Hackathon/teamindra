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

public class NumberFragment extends Fragment{

    private EditText mNumberEditText;
    private TextView mNextTextView;
    private TextView mErrorMessageTextView;

    public interface NumberFragmentListner{
        void onNumberSubmit(String number);
    }

    private NumberFragmentListner mListner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NumberFragmentListner){
            mListner = (NumberFragmentListner) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_number, container, false);

        mNumberEditText = (EditText) view.findViewById(R.id.create_account_number_editText);
        mNextTextView = (TextView) view.findViewById(R.id.create_account_number_next);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.create_account_error_number_textView);

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNumberEditText.getText().toString().trim().length() == 10){

                    mListner.onNumberSubmit(mNumberEditText.getText().toString());

                    OtpVerficationFragment otpVerficationFragment = new OtpVerficationFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                            .replace(R.id.create_account_frameLayout, otpVerficationFragment)
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
