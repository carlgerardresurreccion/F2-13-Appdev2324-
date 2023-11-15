package com.example.furryfound;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogIn_Screen extends AppCompatActivity {
    private EditText loginEmailField, loginPassField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_screen);

        loginEmailField = findViewById(R.id.EmailField); // Use the correct ID for email
        loginPassField = findViewById(R.id.PassField);

        Button LogInFr = findViewById(R.id.LogInFr);
        LogInFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmailField.getText().toString().trim();
                String password = loginPassField.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    // Display an error message if either field is empty
                    Toast.makeText(LogIn_Screen.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Check the login credentials (for demonstration purposes)
                    if (checkLoginCredentials(email, password)) {
                        // Display a toast message for successful login
                        Toast.makeText(LogIn_Screen.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogIn_Screen.this, Register_Screen.class);
                        startActivity(intent);
                        finish();
                        // Optionally, navigate to another screen or perform other actions
                    } else {
                        // Display an error message for unsuccessful login
                        Toast.makeText(LogIn_Screen.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
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
    private boolean checkLoginCredentials(String email, String password) {
    //DILI FINAL
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedEmail = sharedPreferences.getString("user_email", "");
        String storedPassword = sharedPreferences.getString("user_password", "");

        return email.equals(storedEmail) && password.equals(storedPassword);
    }
}
