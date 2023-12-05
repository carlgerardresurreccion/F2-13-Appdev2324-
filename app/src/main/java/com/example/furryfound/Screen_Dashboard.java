package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class Screen_Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        BottomNavigationView bottomNavigationView = findViewById(R.id.NavbarView);
        //bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /*@Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(Screen_Dashboard.this, Screen_Dashboard.class);
                        startActivity(intent);
                        break;
                    case R.id.favorite:
                        intent = new Intent(Screen_Dashboard.this, FavoritesScreen_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.notification:
                        intent = new Intent(Screen_Dashboard.this, NotificationScreen_Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.settings:
                        intent = new Intent(Screen_Dashboard.this, Settings_Activity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });*/
    }
}