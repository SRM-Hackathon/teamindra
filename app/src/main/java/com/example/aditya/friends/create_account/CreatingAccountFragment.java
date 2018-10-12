package com.example.aditya.friends.create_account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.aditya.friends.R;

public class CreatingAccountFragment extends Fragment {

    private ProgressBar mProgressBar;

    public interface CreatingAccountFragmentListener{
        void sendData();
        void supplyProgressBar(ProgressBar progressBar);
        }

    private CreatingAccountFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CreatingAccountFragmentListener){
            mListener = (CreatingAccountFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_creating_account , container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.create_account_creating_account_progressBar);

        mListener.supplyProgressBar(mProgressBar);
        mListener.sendData();

        return view;
    }
}
