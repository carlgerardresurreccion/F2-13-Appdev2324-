package com.example.furryfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn_Screen extends AppCompatActivity {
    private EditText loginEmailField, loginPassField;
    TextView forgetpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_screen);

        mAuth = FirebaseAuth.getInstance();

        loginEmailField = findViewById(R.id.EmailField);
        loginPassField = findViewById(R.id.PassField);
        forgetpass = findViewById(R.id.ForgotPass);

        Button LogInFr = findViewById(R.id.Reset_btn);

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn_Screen.this, ForgotPassword_Screen.class);
                startActivity(intent);
            }
        });

        LogInFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmailField.getText().toString().trim();
                String password = loginPassField.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogIn_Screen.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(email, password);
                }
            }
        });

        Button LogInRegisterButton = findViewById(R.id.LogInRegisterButton);
        LogInRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn_Screen.this, Register_Screen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LogIn_Screen.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogIn_Screen.this, LogIn_Screen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LogIn_Screen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
