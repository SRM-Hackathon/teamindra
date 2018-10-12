package com.example.aditya.friends.create_account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.friends.R;

public class TypeFragment extends Fragment{

    private RelativeLayout mCompanionExpert;
    private RelativeLayout mOldPerson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_type, container,false);

        mCompanionExpert = (RelativeLayout) view.findViewById(R.id.create_account_type_companion);
        mOldPerson = (RelativeLayout) view.findViewById(R.id.create_account_type_old);

        mOldPerson.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NameFragment nameFragment = new NameFragment();
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.create_account_frameLayout, nameFragment)
                        .addToBackStack(null)
                        .commit();

                return true;
            }
        });

        mCompanionExpert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(), "Working on it..", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return view;
    }


}
