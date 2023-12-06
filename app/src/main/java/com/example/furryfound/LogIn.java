package com.example.furryfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    private EditText loginUserField, loginPassField;
    TextView forgetpass;

    private ImageButton backButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_screen);

        mAuth = FirebaseAuth.getInstance();

        loginUserField = findViewById(R.id.UsernameField);
        loginPassField = findViewById(R.id.PassField);
        forgetpass = findViewById(R.id.ForgotPass);
        backButton = (ImageButton) findViewById(R.id.backButton);

        Button LogInFr = findViewById(R.id.Reset_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, LogIn_ForgotPassword.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        LogInFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUserField.getText().toString().trim();
                String password = loginPassField.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogIn.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(username, password);
                }
            }
        });

        TextView LogInRegisterButton = findViewById(R.id.LogInRegisterButton);

        LogInRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register_Email.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    private void signInUser(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LogIn.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogIn.this, Fragment_LogIn_Home.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
