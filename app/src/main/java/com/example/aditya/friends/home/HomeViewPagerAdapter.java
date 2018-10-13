package com.example.aditya.friends.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.aditya.friends.home.MatchFragment;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter{

    public HomeViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : {
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            }
            case 1 : {
                MatchFragment matchFragment = new MatchFragment();
                return matchFragment;
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : {
                return "Profile";
            }
            case 1 : {
                return "Matching";
            }
        }
        return null;
    }
}
