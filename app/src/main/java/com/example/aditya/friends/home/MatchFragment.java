package com.example.aditya.friends.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.aditya.friends.R;
import com.example.aditya.friends.api.YoungPerson;

import java.util.ArrayList;

public class MatchFragment extends Fragment {

    private MatchingView mMatchingView;
    private MatchAdapter mAdapter;

    private ImageButton mRejectImageButton;
    private ImageButton mSendRequestImageButton;

    private ArrayList<YoungPerson> mYoungPeople;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_match, container, false);

        mRejectImageButton = (ImageButton) view.findViewById(R.id.home_match_close_imageButton);
        mSendRequestImageButton = (ImageButton) view.findViewById(R.id.home_match_check_imageButton);

        mYoungPeople = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            YoungPerson youngPerson = new YoungPerson();
            youngPerson.setName(i*172946 + "sdfgysaf");
            youngPerson.setBirthday("0" + i + "/12/201" + i);
            mYoungPeople.add(youngPerson);
        }

        mMatchingView = (MatchingView) view.findViewById(R.id.home_match_matchingView);
        mAdapter = new MatchAdapter(getActivity(), mYoungPeople);
        mMatchingView.setAdapter(mAdapter);

        mRejectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMatchingView.swipeTopViewToLeft();
            }
        });

        mSendRequestImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMatchingView.swipeTopViewToRight();
            }
        });

        return view;
    }
}
