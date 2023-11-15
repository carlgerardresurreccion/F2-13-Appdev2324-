package com.example.furryfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_Screen extends AppCompatActivity {
    private EditText firstNameField, lastNameField, registerEmailField, registerPasswordField, registerConfirmPasswordField, registerAddressField;
    private Button RegisterFr, RegisterLoginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        firstNameField = findViewById(R.id.FirstNameField);
        lastNameField = findViewById(R.id.LastNameField);
        registerEmailField = findViewById(R.id.RegisterEmailField);
        registerPasswordField = findViewById(R.id.RegisterPasswordField);
        registerConfirmPasswordField = findViewById(R.id.RegisterConfirmPasswordField);
        registerAddressField = findViewById(R.id.RegisterAddressField);
        databaseHelper = new DatabaseHelper(this);
        RegisterFr = (Button) findViewById(R.id.RegisterFr);
        RegisterLoginButton = (Button) findViewById(R.id.RegisterLoginButton);
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
                } else {
                    long userId = saveUserData(firstName, lastName, email, password, address);

                    if (userId != -1) {
                        Toast.makeText(Register_Screen.this, "Registration success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register_Screen.this, LogIn_Screen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register_Screen.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
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
    private long saveUserData(String firstName, String lastName, String email, String password, String address) {
        //MOSAVE SA USER INFO
        long userId = -1;
        try (SQLiteDatabase db = databaseHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_FIRST_NAME, firstName);
            values.put(DatabaseHelper.COLUMN_LAST_NAME, lastName);
            values.put(DatabaseHelper.COLUMN_EMAIL, email);
            values.put(DatabaseHelper.COLUMN_PASSWORD, password);
            values.put(DatabaseHelper.COLUMN_ADDRESS, address);

            userId = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }
}
