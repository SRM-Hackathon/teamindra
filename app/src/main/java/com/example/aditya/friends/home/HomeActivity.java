package com.example.aditya.friends.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.aditya.friends.R;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mHomeDrawerLayout;
    private NavigationView mHomeNavigationView;

    private ViewPager mHomeViewPager;
    private TabLayout mHomeTabLayout;
    private HomeViewPagerAdapter mAdapter;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);


        mHomeDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawerLayout);
        mHomeNavigationView = (NavigationView) findViewById(R.id.home_navigationView);

        mHomeViewPager = (ViewPager) findViewById(R.id.home_viewPager);
        mHomeTabLayout = (TabLayout) findViewById(R.id.home_tabLayout);

        mAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mHomeViewPager.setAdapter(mAdapter);

        mHomeNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // close drawer when item is tapped
                mHomeDrawerLayout.closeDrawers();
                menuItem.setChecked(false);

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mHomeDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
