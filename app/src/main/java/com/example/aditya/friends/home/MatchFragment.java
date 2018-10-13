package com.example.aditya.friends.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aditya.friends.R;
import com.example.aditya.friends.api.ApiManager;
import com.example.aditya.friends.api.OldPerson;
import com.example.aditya.friends.api.YoungPerson;
import com.example.aditya.friends.create_account.CreateAccountActivity;
import com.example.aditya.friends.utils.FriendsUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aditya.friends.utils.FriendsUtils.mOldPersonData;

public class MatchFragment extends Fragment {

    private LinearLayout mLinearLayoutContainer;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    private MatchingView mMatchingView;
    private MatchAdapter mAdapter;

    private ImageButton mRejectImageButton;
    private ImageButton mSendRequestImageButton;
    private ImageButton mSearchImageButton;

    private ArrayList<YoungPerson> mYoungPeople;

    private ApiManager mApiManager;

    private boolean mTimeBuffer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_match, container, false);

        mLinearLayoutContainer = (LinearLayout) view.findViewById(R.id.home_match_container_linearLayout);
        mProgressBar = (ProgressBar) view.findViewById(R.id.home_match_progressBar);
        mTextView = (TextView) view.findViewById(R.id.home_match_textView);
        mRejectImageButton = (ImageButton) view.findViewById(R.id.home_match_close_imageButton);
        mSendRequestImageButton = (ImageButton) view.findViewById(R.id.home_match_check_imageButton);
        mSearchImageButton = (ImageButton) view.findViewById(R.id.home_match_search_imageButton);
        mTimeBuffer = true;

        mYoungPeople = new ArrayList<>();

        mApiManager = ApiManager.getInstance();

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

        mSearchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimeBuffer){
                    getYoungPeopleAround();
                    mTimeBuffer = false;
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTimeBuffer = true;
                        }
                    }, 10000);
                } else {
                    Toast.makeText(getContext(), "Wait for few second to enable thsi feature..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getYoungPeopleAround(){
        mProgressBar.setVisibility(View.VISIBLE);
        mLinearLayoutContainer.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);

        mApiManager.getYoungPeople(mOldPersonData.getUniqueId(), new Callback<ArrayList<YoungPerson>>() {
            @Override
            public void onResponse(Call<ArrayList<YoungPerson>> call, Response<ArrayList<YoungPerson>> response) {
                mProgressBar.setVisibility(View.GONE);
                mLinearLayoutContainer.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.GONE);

                ArrayList<YoungPerson> youngPerson = response.body();
                if (response.isSuccessful() && youngPerson != null){
                    Toast.makeText(getContext(), "onResponse : successful", Toast.LENGTH_SHORT).show();
                    ;
                } else {
                    Toast.makeText(getContext(), "Response is : " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<YoungPerson>> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                mLinearLayoutContainer.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.GONE);

                Toast.makeText(getContext(), "onFailure " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
