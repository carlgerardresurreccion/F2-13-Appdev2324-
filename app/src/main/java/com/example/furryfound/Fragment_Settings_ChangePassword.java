package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Fragment_Settings_ChangePassword extends AppCompatActivity {

    EditText currPass, newPass;
    Button savenewpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_changepassword);

        currPass = findViewById(R.id.currPass);
        newPass = findViewById(R.id.newPass);
        savenewpass = findViewById(R.id.savenewpass);

        savenewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String currentPassword = currPass.getText().toString().trim();
                    String newPassword = newPass.getText().toString().trim();

                    AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(user.getEmail()), currentPassword);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Change password
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(Fragment_Settings_ChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Fragment_Settings_ChangePassword.this, Fragment_Settings.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(Fragment_Settings_ChangePassword.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(Fragment_Settings_ChangePassword.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }
        });
    }
}