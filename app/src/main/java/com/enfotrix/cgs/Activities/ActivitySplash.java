package com.enfotrix.cgs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.databinding.ActivityOnBoardingScreenBinding;

public class ActivitySplash extends AppCompatActivity {
    private Utils utils;
    ProgressBar progressBar;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // hide actionbar and statusBar
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        progressBar = findViewById(R.id.progressBar);

        utils = new Utils(this);

        delay();

    }

    private void delay() {


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (utils.isLoggedIn()) {
                    Intent intent = new Intent(ActivitySplash.this, ActivityOnBoardingScreen.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);

    }
}