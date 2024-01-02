package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Fragment_Home_ApplicationForm extends AppCompatActivity {

    private EditText livingEnvironmentText, ownOrRentText, petsOwnedText, employmentStatusText, reasonEditText;
    Button sendButton;
    ImageButton backButton;
    FirebaseDatabase fbd;
    DatabaseReference df;
    FirebaseAuth auth;
    private String petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_form);

        sendButton = findViewById(R.id.SendApplication);
        reasonEditText = findViewById(R.id.reason);
        livingEnvironmentText = findViewById(R.id.livingEnvironment);
        ownOrRentText = findViewById(R.id.ownOrRent);
        petsOwnedText = findViewById(R.id.petsOwned);
        employmentStatusText = findViewById(R.id.employmentStatus);
        auth = FirebaseAuth.getInstance();
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            petId = intent.getStringExtra("petId");
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbd = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
                df = fbd.getReference("applicationform");
                FirebaseUser currentUser = auth.getCurrentUser();

                String adopterID = currentUser.getUid();
                String date_applied = getCurrentDate();
                int status = 0;
                String applicationID = SecureRandomIdGenerator.generateSecureRandomId();
                String livingEnvironment = livingEnvironmentText.getEditableText().toString();
                String ownOrRent = ownOrRentText.getEditableText().toString();
                String petsOwned = petsOwnedText.getEditableText().toString();
                String employmentStatus = employmentStatusText.getEditableText().toString();
                String reason = reasonEditText.getEditableText().toString();

                // Check if the user has already submitted an application for this pet
                Query query = df.orderByChild("adopter_id").equalTo(adopterID);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String existingPetId = snapshot.child("pet_id").getValue(String.class);
                            if (existingPetId != null && existingPetId.equals(petId)) {
                                // User has already submitted an application for this pet
                                Toast.makeText(Fragment_Home_ApplicationForm.this, "You have already submitted an application for this pet.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        // User has not submitted an application for this pet, proceed to submit
                        df.child(applicationID).child("application_id").setValue(applicationID);
                        df.child(applicationID).child("adopter_id").setValue(adopterID);
                        df.child(applicationID).child("pet_id").setValue(petId);
                        df.child(applicationID).child("living_environment").setValue(livingEnvironment);
                        df.child(applicationID).child("own_rent").setValue(ownOrRent);
                        df.child(applicationID).child("pets_owned").setValue(petsOwned);
                        df.child(applicationID).child("employment_status").setValue(employmentStatus);
                        df.child(applicationID).child("reason").setValue(reason);
                        df.child(applicationID).child("date_applied").setValue(date_applied);
                        df.child(applicationID).child("status").setValue(status);

                        Toast.makeText(Fragment_Home_ApplicationForm.this, "Application Form Sent!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Fragment_Home_ApplicationForm.this, Fragment_LogIn_Home.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                        Toast.makeText(Fragment_Home_ApplicationForm.this, "Error checking application status.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date(System.currentTimeMillis());
        return sdf.format(currentDate);
    }
}

