package com.example.aditya.friends.create_account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aditya.friends.R;

import java.util.ArrayList;

public class InterestFragment extends Fragment{

    private TextView mNextTextView;
    private TextView mAddInterestTextView;
    private TextView mErrorMessageTextView;

    private ListView mInterestListView;
    private InterestAdaper mAdapter;

    private ArrayList<String> mInterests = new ArrayList<>();

    public interface InterestFragmentListener{
        void onInterestSubmit(ArrayList<String> interests);
    }

    private InterestFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof InterestFragmentListener){
            mListener = (InterestFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Error creating listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_interests, container, false);

        mNextTextView = (TextView) view.findViewById(R.id.create_account_interests_next);
        mAddInterestTextView = (TextView) view.findViewById(R.id.create_account_add_interests_button);
        mErrorMessageTextView = (TextView) view.findViewById(R.id.create_account_error_interests_textView);
        mInterestListView = (ListView) view.findViewById(R.id.create_account_interests_listView);

        mAdapter = new InterestAdaper(getContext(), mInterests);
        mInterestListView.setAdapter(mAdapter);

        mInterestListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder deleteInterestDialog = new AlertDialog.Builder(getContext());
                deleteInterestDialog.setTitle("Delete this interest?");
                deleteInterestDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mInterests.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                deleteInterestDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                deleteInterestDialog.show();

                return true;
            }
        });

        mAddInterestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.interest_alertdialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setView(view);

                final EditText interestEditText = (EditText) view.findViewById(R.id.interest_alertDialog_editText);

                builder.setPositiveButton("Add Interest", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String interest = interestEditText.getText().toString();
                        if(interest.isEmpty()){
                            // dont add it
                        } else {
                            mInterests.add(interest);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        mNextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterests.size() < 1){
                    mErrorMessageTextView.setVisibility(View.VISIBLE);
                } else {
                    mListener.onInterestSubmit(mInterests);

                    GenderPreferenceFragment genderPreferenceFragment = new GenderPreferenceFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                            .replace(R.id.create_account_frameLayout, genderPreferenceFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }
}
