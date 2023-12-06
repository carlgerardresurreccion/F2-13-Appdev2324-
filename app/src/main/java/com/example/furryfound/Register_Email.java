package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Register_Email extends AppCompatActivity {

    private EditText registerFullNameField, registerAddressField, registerPhoneNumberField, registerEmailField, registerPasswordField;
    private Button RegisterFr;

    private ImageButton backButton;
    private TextView RegisterLoginButton;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference; // Reference to user node in Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference("adopters"); // Reference to user node in Realtime Database

        registerFullNameField = findViewById(R.id.RegisterFullNameField);
        registerAddressField = findViewById(R.id.RegisterAddressField);
        registerPhoneNumberField = findViewById(R.id.RegisterPhoneNumberField);
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
                signUpUser();
            }
        });

        RegisterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Email.this, LogIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    public void signUpUser() {
        String full_name = registerFullNameField.getText().toString().trim();
        String address = registerAddressField.getText().toString().trim();
        String phone_number = registerPhoneNumberField.getText().toString().trim();
        String email = registerEmailField.getText().toString().trim();
        String password = registerPasswordField.getText().toString();

        if (full_name.isEmpty() || address.isEmpty() || phone_number.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Register_Email.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(Register_Email.this, "Passwords too short", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(Register_Email.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            saveUserToDatabase(userId, full_name, address, phone_number, email, password);
                        }
                    } else {
                        Toast.makeText(Register_Email.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveUserToDatabase(String userId, String full_name, String address, String phone_number, String email, String password) {
        DatabaseReference userRef = databaseReference.child(full_name);

        userRef.child("user_id").setValue(userId);
        userRef.child("address").setValue(address);
        userRef.child("phone_number").setValue(phone_number);
        userRef.child("email").setValue(email);
        userRef.child("password").setValue(password);

        Toast.makeText(Register_Email.this, "User created!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Register_Email.this, LogIn.class));
        finish();
    }
}