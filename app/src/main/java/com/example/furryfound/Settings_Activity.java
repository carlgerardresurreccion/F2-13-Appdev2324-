package com.example.furryfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Settings_Activity extends AppCompatActivity {

    Button logout, editprof, changepass, terms;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        logout = findViewById(R.id.Logout);
        editprof = findViewById(R.id.EditProfile);
        changepass= findViewById(R.id.ChangePass);
        terms = findViewById(R.id.TermsAndConditions);

        editprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings_Activity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings_Activity.this, ChangePassword_Activity.class);
                startActivity(intent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings_Activity.this, TermsConditions_Activity.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Settings_Activity.this, Screen_LogIn.class)); 
                finish();
            }
        });
    }
}