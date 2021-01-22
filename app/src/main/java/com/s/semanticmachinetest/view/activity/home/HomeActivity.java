package com.s.semanticmachinetest.view.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.s.semanticmachinetest.R;
import com.s.semanticmachinetest.view.activity.profile.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar_animation)
    Toolbar toolbarAnimation;
    @BindView(R.id.lineView1)
    View lineView1;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int entryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        HomeFragment homeFragment = new HomeFragment();
        addFragment(homeFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        HomeFragment homeFragment = new HomeFragment();
                        addFragment(homeFragment);
                        break;
                    case R.id.action_list:
                        EmployeeListFragment employeeListFragment = new EmployeeListFragment();
                        addFragment(employeeListFragment);
                        break;
                    case R.id.action_profile:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
    }


    public void addFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();
    }
}