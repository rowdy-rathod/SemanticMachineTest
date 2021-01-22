package com.s.semanticmachinetest.view.activity.auth;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.s.semanticmachinetest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int entryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        if (savedInstanceState == null) {
            MNumberVerificationFragment fragment = new MNumberVerificationFragment();
            addFragment(fragment);
            setToolbarTitle("MainFragment");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void addFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();
    }


    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount == 1) {
            setToolbarTitle(getString(R.string.enter_number_label));
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onBackStackChanged() {
        entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount > 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
    }

    public void onBackPressButton() {
        entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount == 1) {
            setToolbarTitle(getString(R.string.enter_number_label));
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
