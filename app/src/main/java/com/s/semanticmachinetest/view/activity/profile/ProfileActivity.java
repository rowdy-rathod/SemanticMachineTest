package com.s.semanticmachinetest.view.activity.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.s.semanticmachinetest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mEditTextFirstName)
    EditText mEditTextFirstName;
    @BindView(R.id.mEditTextLastName)
    EditText mEditTextLastName;
    @BindView(R.id.mEditTextUserName)
    EditText mEditTextUserName;
    @BindView(R.id.mEditTextEmailAddress)
    EditText mEditTextEmailAddress;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFeMale)
    RadioButton radioFeMale;
    @BindView(R.id.lineView)
    View lineView;
    @BindView(R.id.mButtonLogout)
    Button mButtonLogout;
    @BindView(R.id.mButtonUpdate)
    Button mButtonUpdate;
    @BindView(R.id.layoutInput)
    LinearLayout layoutInput;
    @BindView(R.id.imageProfileImage)
    CircleImageView imageProfileImage;
    @BindView(R.id.mTextUploadImage)
    TextView mTextUploadImage;
    @BindView(R.id.progressBar3)
    ProgressBar progressBar3;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar3.setVisibility(View.GONE);
        setToolbarTitle(getString(R.string.profile));
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
}