package com.enfotrix.cgs.Activities;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.enfotrix.cgs.R;

import java.io.IOException;
import java.io.OutputStream;

public class Activity_full_image_container extends AppCompatActivity {

    private ImageView fullImage;
    private AppCompatButton apply, btn_download;
    OutputStream outputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_container);

        // hide actionbar and statusBar
        getSupportActionBar().setTitle("Image");

        fullImage = findViewById(R.id.fullImage);
        apply = findViewById(R.id.apply);
        btn_download = findViewById(R.id.btn_download);
        Glide.with(this).load(getIntent().getStringExtra("path")).into(fullImage);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();
            }
        });
    }

    private void downloadImage() {

        BitmapDrawable drawable = (BitmapDrawable) fullImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Bird",
                "Image of bird"
        );

        Toast.makeText(this, "Image Downloaded", Toast.LENGTH_SHORT).show();

        //MediaStore.Images.Media.insertImage(getContentResolver(), fullImage,"Image title"  , yourDescription);
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