package com.example.furryfound;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, Welcome_Screen.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            finish();
        }, 3000);
    }
}