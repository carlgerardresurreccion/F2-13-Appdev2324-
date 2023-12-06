package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Screen_Dashboard extends AppCompatActivity {

    BottomNavigationView navbar;

    Fragment_Home home = new Fragment_Home();
    Fragment_Favorites favorites = new Fragment_Favorites();
    Fragment_Notifications notifications = new Fragment_Notifications();
    Fragment_Settings settings = new Fragment_Settings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        navbar = findViewById(R.id.NavbarView);

        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, home).commit();

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.navbar_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, home).commit();
                    return true;
                } else if(id == R.id.navbar_favorites) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, favorites).commit();
                    return true;
                } else if(id == R.id.navbar_notifications) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, notifications).commit();
                    return true;
                } else if(id == R.id.navbar_settings){
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, settings).commit();
                    return true;
                }
                return false;
            }
        });
    }
}