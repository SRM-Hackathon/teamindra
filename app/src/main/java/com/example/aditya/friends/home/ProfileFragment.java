package com.example.aditya.friends.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aditya.friends.R;
import com.example.aditya.friends.utils.FriendsUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {

    private ImageView mDisplayPictureImageView;
    private TextView mNameTextView;
    private TextView mAgeTextView;
    private TextView mLocationDistanceDataTextView;
    private TextView mDescriptionInterestTextView;
    private ImageButton mEditProfileImageButton;
    private ImageButton mSettingImageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_profile, container, false);

        mDisplayPictureImageView = (ImageView) view.findViewById(R.id.home_profile_display_picture);
        mNameTextView = (TextView) view.findViewById(R.id.home_profile_name);
        mAgeTextView = (TextView) view.findViewById(R.id.home_profile_age);
        mLocationDistanceDataTextView = (TextView) view.findViewById(R.id.home_profile_location_distance_data);
        mDescriptionInterestTextView = (TextView) view.findViewById(R.id.home_profile_interest_description);
        mEditProfileImageButton = (ImageButton) view.findViewById(R.id.home_profile_edit_profile_imageButton);
        mSettingImageButton = (ImageButton) view.findViewById(R.id.home_profile_setting_imageButton);

        mNameTextView.setText(FriendsUtils.mOldPersonData.getName());
        mAgeTextView.setText(FriendsUtils.getAgeFromBirthday(FriendsUtils.mOldPersonData.getBirthday()));
        //TODO : Update Location Text View
        String description = "";
        for(int i = 0; i < FriendsUtils.mOldPersonData.getInterests().size(); i++){
            if (i == 0){
                description += FriendsUtils.mOldPersonData.getInterests().get(i);
            } else {
                description += ", " + FriendsUtils.mOldPersonData.getInterests().get(i);
            }
        }
        mDescriptionInterestTextView.setText(description);


        mEditProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Intent to EditProfle Activity
            }
        });

        mSettingImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Intent to Setting Activity
            }
        });

        return view;
    }
}
