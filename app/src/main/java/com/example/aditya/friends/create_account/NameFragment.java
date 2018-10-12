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

public class NameFragment extends Fragment {

    private TextView mErrorMessageTextView;
    private TextView mNextTextView;

    private EditText mNameEditText;

    public interface NameFragmentListner{
        void onNameSubmit(String name);
    }

    private NameFragmentListner mListner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NameFragmentListner){
            mListner = (NameFragmentListner) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_name, container, false);

        mErrorMessageTextView = (TextView) view.findViewById(R.id.create_account_error_name_textView);
        mNextTextView = (TextView) view.findViewById(R.id.create_account_name_next);

        mNameEditText = (EditText) view.findViewById(R.id.create_account_name_editText);

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNameEditText.getText().toString().equals("")){
                    mErrorMessageTextView.setVisibility(View.VISIBLE);
                } else {

                    mListner.onNameSubmit(mNameEditText.getText().toString());

                    NumberFragment numberFragment = new NumberFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                            .replace(R.id.create_account_frameLayout, numberFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        return view;
    }
}
