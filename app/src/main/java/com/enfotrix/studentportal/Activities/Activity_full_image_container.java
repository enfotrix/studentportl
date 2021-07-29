package com.enfotrix.studentportal.Activities;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.enfotrix.studentportal.R;

import java.io.IOException;

public class Activity_full_image_container extends AppCompatActivity {

    private ImageView fullImage;
    private AppCompatButton apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_container);

        // hide actionbar and statusBar
        getSupportActionBar().hide();

        fullImage = findViewById(R.id.fullImage);
        apply = findViewById(R.id.apply);
        Glide.with(this).load(getIntent().getStringExtra("path")).into(fullImage);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground();
            }
        });
    }

    private void setBackground() {
        Bitmap bitmap = ((BitmapDrawable) fullImage.getDrawable()).getBitmap();
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        try {
            manager.setBitmap(bitmap);
            Toast.makeText(this, "Background Image Applied", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}