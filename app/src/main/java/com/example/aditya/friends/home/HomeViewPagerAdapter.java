package com.example.aditya.friends.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            }
            case 2 : {
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
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
            case 2 : {
                return "Notification";
            }
        }
        return null;
    }
}
