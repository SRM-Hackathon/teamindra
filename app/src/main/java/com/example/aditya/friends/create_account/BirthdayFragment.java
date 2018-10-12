package com.example.aditya.friends.create_account;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.aditya.friends.R;

import java.util.Calendar;
import java.util.Date;

public class BirthdayFragment extends Fragment {

    private TextView mBirthdayTextView;
    private TextView mNextTextView;

    private int mCurrentDay, mCurrentMonth, mCurrentYear;

    public interface BirthdayFragmentListener{
        void onBirthdaySubmit(String birthday);
    }

    private BirthdayFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BirthdayFragmentListener){
            mListener = (BirthdayFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    private String birthDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_birthday, container, false);

        mBirthdayTextView = (TextView) view.findViewById(R.id.create_account_birthday_select_textView);
        mNextTextView = (TextView) view.findViewById(R.id.create_account_birthday_next);

        Calendar calendar = Calendar.getInstance();
        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);
        mCurrentMonth = calendar.get(Calendar.MONTH);
        mCurrentYear = calendar.get(Calendar.YEAR);

        final String currentDateText = mCurrentDay + "-" + mCurrentMonth + "-" + mCurrentYear;
        mBirthdayTextView.setText(currentDateText);

        mBirthdayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthDate = dayOfMonth + "-" + month + "-" + year;
                        mBirthdayTextView.setText(birthDate);
                    }
                }, mCurrentYear, mCurrentMonth, mCurrentDay);
                datePickerDialog.getDatePicker().setMaxDate((new Date()).getTime());
                datePickerDialog.show();

            }
        });

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBirthdaySubmit(birthDate);

                GenderFragment genderFragment = new GenderFragment();
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.create_account_frameLayout, genderFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }
}
