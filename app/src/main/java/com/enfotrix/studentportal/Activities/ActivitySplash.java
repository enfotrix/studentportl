package com.enfotrix.studentportal.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;

public class ActivitySplash extends AppCompatActivity {
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        utils = new Utils(this);

        delay();

}

    private void delay() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(utils.isLoggedIn()){
                    Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 3000);

    }
}