package com.example.trailermate;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import com.airbnb.lottie.LottieAnimationView;
import com.example.trailermate.DashBoadNew.DashboadmainActivity;
import com.example.trailermate.databinding.ActivityMainBinding;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GifImageView gifImageView = findViewById(R.id.gifImageView);
        gifImageView.setImageResource(R.drawable.animationsplashv);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, DashboadmainActivity.class));
                finish();
            }
        }, 3000);
    }
}