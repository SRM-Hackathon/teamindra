package com.example.aditya.friends.create_account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aditya.friends.R;

public class GenderFragment extends Fragment {

    private RadioButton mMaleRadioButton;
    private RadioButton  mFemaleRadioButton;
    private TextView mNextTextView;
    private TextView mErrorMessageTextView;

    public interface GenderFragmentListener{
        void onGenderSubmit(String gender);
    }

    private GenderFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof GenderFragmentListener){
            mListener = (GenderFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_gender, container, false);

        mMaleRadioButton = (RadioButton) view.findViewById(R.id.create_account_gender_male_radioButton);;
        mFemaleRadioButton = (RadioButton) view.findViewById(R.id.create_account_gender_female_radioButton);
        mNextTextView = (TextView) view.findViewById(R.id.create_account_name_next);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.create_account_error_gender_textView);

        mMaleRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFemaleRadioButton.setChecked(false);
                } else {
                    mFemaleRadioButton.setChecked(true);
                }
            }
        });

        mFemaleRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mMaleRadioButton.setChecked(false);
                } else {
                    mMaleRadioButton.setChecked(true);
                }
            }
        });

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMaleRadioButton.isChecked() || mFemaleRadioButton.isChecked()){

                    if(mMaleRadioButton.isChecked()){
                        mListener.onGenderSubmit("m");
                    } else if(mFemaleRadioButton.isChecked()){
                        mListener.onGenderSubmit("f");
                    }

                    InterestFragment interestFragment = new InterestFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                            .replace(R.id.create_account_frameLayout, interestFragment)
                            .addToBackStack(null)
                            .commit();
                } else  {
                    mErrorMessageTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}
