package com.example.justdoit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private boolean isFromBackground = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        // Start animations

        TextView splashText = findViewById(R.id.logoText);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        splashText.startAnimation(fadeIn); // Apply animation to TextView

        // Check if the splash screen should display
        if (!isFromBackground) {
            showSplashScreen();
        } else {
            // If coming from background, go directly to MainActivity
            startMainActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the activity was not fully destroyed
        if (!isFinishing()) {
            isFromBackground = true; // Set flag when coming back from background
        }
    }

    private void showSplashScreen() {
        // Show splash screen for a fixed duration
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity(); // Redirect to MainActivity after splash screen
            }
        }, 3000); // 3-second delay
    }

    private void startMainActivity() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }
}