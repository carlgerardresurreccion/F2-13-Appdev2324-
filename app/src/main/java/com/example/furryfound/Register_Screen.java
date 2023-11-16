package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Register_Screen extends AppCompatActivity {
    private EditText firstNameField, lastNameField, registerEmailField, registerPasswordField, registerConfirmPasswordField, registerAddressField;
    private Button RegisterFr, RegisterLoginButton;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        mAuth = FirebaseAuth.getInstance();

        firstNameField = findViewById(R.id.FirstNameField);
        lastNameField = findViewById(R.id.LastNameField);
        registerEmailField = findViewById(R.id.RegisterEmailField);
        registerPasswordField = findViewById(R.id.RegisterPasswordField);
        registerConfirmPasswordField = findViewById(R.id.RegisterConfirmPasswordField);
        registerAddressField = findViewById(R.id.RegisterAddressField);

        RegisterFr = findViewById(R.id.RegisterFr);
        RegisterLoginButton = findViewById(R.id.RegisterLoginButton);
        RegisterFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameField.getText().toString();
                String lastName = lastNameField.getText().toString();
                String email = registerEmailField.getText().toString();
                String password = registerPasswordField.getText().toString();
                String confirmPassword = registerConfirmPasswordField.getText().toString();
                String address = registerAddressField.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || address.isEmpty()) {
                    Toast.makeText(Register_Screen.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(Register_Screen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(Register_Screen.this, "Passwords too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password, firstName, lastName, address);
                }
            }
        });

        RegisterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Screen.this, LogIn_Screen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser(String email, String password, final String firstName, final String lastName, final String address) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register_Screen.this, "Registration success!", Toast.LENGTH_SHORT).show();
                            // You can save additional user information to Firestore or Realtime Database if needed
                            // For simplicity, we'll just go to the login screen for now
                        } else {
                            Toast.makeText(Register_Screen.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
