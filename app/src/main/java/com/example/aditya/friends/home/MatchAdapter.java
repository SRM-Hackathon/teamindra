package com.example.aditya.friends.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aditya.friends.R;
import com.example.aditya.friends.api.YoungPerson;
import com.example.aditya.friends.utils.FriendsUtils;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends BaseAdapter {

    private List<YoungPerson> mYoungPeople;
    private Activity mActivity;

    public MatchAdapter(Activity activity, ArrayList<YoungPerson> youngPeople){
        mYoungPeople = youngPeople;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mYoungPeople.size();
    }

    @Override
    public YoungPerson getItem(int position) {
        return mYoungPeople.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_matches, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        YoungPerson youngPerson = mYoungPeople.get(position);

        viewHolder.mProfileName.setText(youngPerson.getName());
        String description = "";
        for(int i = 0; i < youngPerson.getInterests().size(); i++){
            if (i == 0){
                description += youngPerson.getInterests().get(i);
            } else {
                description += ", " + youngPerson.getInterests().get(i);
            }
        }
        viewHolder.mInterests.setText(description);
        viewHolder.mAge.setText(FriendsUtils.getAgeFromBirthday(youngPerson.getBirthday()) + " Years");
        //TODO : Get location data and apply cover photo and profile pic

        return convertView;
    }

    private class ViewHolder{
        private TextView mProfileName;
        private TextView mInterests;
        private TextView mAge;
        private TextView mLocation;
        private ImageView mCoverPicture;
        private ImageView mCircularProfilePicture;

        public ViewHolder(View view){
            mProfileName = (TextView) view.findViewById(R.id.home_match_card_profile_name);
            mInterests = (TextView) view.findViewById(R.id.home_match_card_interest);
            mAge = (TextView) view.findViewById(R.id.home_match_card_profile_age);
            mLocation = (TextView) view.findViewById(R.id.home_match_card_profile_address);
            mCircularProfilePicture = (ImageView) view.findViewById(R.id.home_match_card_circular_profile_picture);
            mCoverPicture = (ImageView) view.findViewById(R.id.home_match_card_cover_pic);
        }
    }
}
