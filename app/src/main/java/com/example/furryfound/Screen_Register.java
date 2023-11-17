package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Screen_Register extends AppCompatActivity {
    private EditText registerUsernameField, registerEmailField, registerPasswordField;
    private Button RegisterFr;

    private ImageButton backButton;
    private TextView RegisterLoginButton;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        mAuth = FirebaseAuth.getInstance();

        registerUsernameField = findViewById(R.id.RegisterUsernameField);
        registerEmailField = findViewById(R.id.RegisterEmailField);
        registerPasswordField = findViewById(R.id.RegisterPasswordField);

        backButton = (ImageButton) findViewById(R.id.backButton);

        RegisterFr = findViewById(R.id.RegisterFr);
        RegisterLoginButton = findViewById(R.id.RegisterLoginButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RegisterFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmailField.getText().toString();
                String password = registerPasswordField.getText().toString();
                String username = registerUsernameField.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Screen_Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(Screen_Register.this, "Passwords too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(username, email, password);
                }
            }
        });

        RegisterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Screen_Register.this, Screen_LogIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    private void registerUser(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Screen_Register.this, "Registration success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Screen_Register.this, Screen_LogIn.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        } else {
                            Toast.makeText(Screen_Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
