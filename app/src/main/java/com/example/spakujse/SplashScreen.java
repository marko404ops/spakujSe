package com.example.spakujse;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

//        getSupportActionBar().hide();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageView appIcon = findViewById(R.id.appIcon);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(appIcon, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(appIcon, "scaleY", 1f, 1.2f, 1f);
        scaleX.setDuration(1000);
        scaleY.setDuration(1000);
        scaleX.setInterpolator(new AccelerateInterpolator());
        scaleY.setInterpolator(new AccelerateInterpolator());

        TextView appIme = findViewById(R.id.appIme);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(appIme, "alpha", 0f, 1f);
        fadeIn.setDuration(500);
        fadeIn.setStartDelay(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.play(fadeIn).after(scaleY);
        animatorSet.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2500);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}