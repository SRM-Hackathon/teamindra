package com.example.aditya.friends.create_account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aditya.friends.R;

public class GenderPreferenceFragment extends Fragment {

    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    //private RadioButton mOtherRadioButton;
    private RadioButton mAnyRadioButton;
    private LinearLayout mMaleLinearLayout;
    private LinearLayout mFemaleLinearLayout;
    //private LinearLayout mOtherLinearLayout;
    private LinearLayout mAnyLinearLayout;
    private TextView mNextTextView;
    private TextView mErrorMessageTextView;

    public interface GenderPreferenceFragmentListener{
        void onGenderPreferenceSubmit(String genderPreference);
    }

    private GenderPreferenceFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof GenderPreferenceFragmentListener){
            mListener = (GenderPreferenceFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_gender_preference, container, false);

        mMaleRadioButton = (RadioButton) view.findViewById(R.id.create_account_gender_preference_male_radioButton);
        mFemaleRadioButton = (RadioButton) view.findViewById(R.id.create_account_gender_preference_female_radioButton);
        //mOtherRadioButton = (RadioButton) view.findViewById(R.id.create_account_gender_preference_other_radioButton);
        mAnyRadioButton = (RadioButton) view.findViewById(R.id.create_account_gender_preference_any_radioButton);
        mMaleLinearLayout = (LinearLayout) view.findViewById(R.id.create_account_gender_preference_male_container);
        mFemaleLinearLayout = (LinearLayout) view.findViewById(R.id.create_account_gender_preference_female_container);
        //mOtherLinearLayout = (LinearLayout) view.findViewById(R.id.create_account_gender_preference_other_container);
        mAnyLinearLayout = (LinearLayout) view.findViewById(R.id.create_account_gender_preference_any_container);
        mNextTextView = (TextView) view.findViewById(R.id.create_account_gender_preference_name_next);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.create_account_error_gender_preference_textView);

        mMaleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMaleRadioButton.isChecked()){
                    mMaleRadioButton.setChecked(false);
                } else {
                    mMaleRadioButton.setChecked(true);
                    mFemaleRadioButton.setChecked(false);
                    //mOtherRadioButton.setChecked(false);
                    mAnyRadioButton.setChecked(false);
                }
            }
        });

        mFemaleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFemaleRadioButton.isChecked()){
                    mFemaleRadioButton.setChecked(false);
                } else {
                    mMaleRadioButton.setChecked(false);
                    mFemaleRadioButton.setChecked(true);
                    //mOtherRadioButton.setChecked(false);
                    mAnyRadioButton.setChecked(false);
                }
            }
        });

        /*mOtherLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOtherRadioButton.isChecked()){
                    mOtherRadioButton.setChecked(false);
                } else {
                    mMaleRadioButton.setChecked(false);
                    mFemaleRadioButton.setChecked(false);
                    mOtherRadioButton.setChecked(true);
                    mAnyRadioButton.setChecked(false);
                }
            }
        });*/

        mAnyLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnyRadioButton.isChecked()){
                    mAnyRadioButton.setChecked(false);
                } else {
                    mMaleRadioButton.setChecked(false);
                    mFemaleRadioButton.setChecked(false);
                    //mOtherRadioButton.setChecked(false);
                    mAnyRadioButton.setChecked(true);
                }
            }
        });

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMaleRadioButton.isChecked() || mFemaleRadioButton.isChecked() || mAnyRadioButton.isChecked() /*|| mOtherRadioButton.isChecked() */){

                    if(mMaleRadioButton.isChecked()){
                        mListener.onGenderPreferenceSubmit("m");
                    } else if(mFemaleRadioButton.isChecked()){
                        mListener.onGenderPreferenceSubmit("f");
                    } else if(mAnyRadioButton.isChecked()){
                        mListener.onGenderPreferenceSubmit("a");
                    } /* else if(mOtherRadioButton.isChecked()){
                        mListener.onGenderPreferenceSubmit("o");
                    } */

                    ProfilePictureFragment profilePictureFragment = new ProfilePictureFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                            .replace(R.id.create_account_frameLayout, profilePictureFragment)
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
