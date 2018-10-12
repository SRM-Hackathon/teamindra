package com.example.aditya.friends.create_account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aditya.friends.R;

import java.util.ArrayList;

public class InterestAdaper extends ArrayAdapter<String> {

    public InterestAdaper(Context context, ArrayList<String> interests){
        super(context, 0, interests);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if( listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.interest_list_view_item, parent, false);
        }

        String currentInterest = getItem(position);

        TextView interestTextView = (TextView) listViewItem.findViewById(R.id.interest_list_item_textView);
        interestTextView.setText(currentInterest);

        return listViewItem;
    }
}
