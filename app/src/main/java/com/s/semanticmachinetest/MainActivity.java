package com.s.semanticmachinetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.s.semanticmachinetest.view.activity.auth.AuthActivity;

//    Refereance TOP => 1055

public class MainActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                finish();
            }
        }, 3000);
    }
}