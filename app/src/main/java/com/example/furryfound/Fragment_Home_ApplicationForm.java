package com.example.furryfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
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

    private EditText  reasonEditText, fullNameText, addressText, phoneNumberText, emailText;
    String  petnameET, petIDET;
    Button sendButton;
    ImageButton backButton;
    FirebaseDatabase fbd;
    DatabaseReference df;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_form);

        sendButton = findViewById(R.id.SendApplication);
        fullNameText = findViewById(R.id.FullName);
        addressText = findViewById(R.id.Address);
        phoneNumberText = findViewById(R.id.phnum);
        emailText = findViewById(R.id.af_email);
        reasonEditText = findViewById(R.id.reason);
        auth = FirebaseAuth.getInstance();
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                String fullname = fullNameText.getEditableText().toString();
                String address = addressText.getEditableText().toString();
                String phonenum = phoneNumberText.getEditableText().toString();
                String email = emailText.getEditableText().toString();
                String reason = reasonEditText.getEditableText().toString();

                df.child(applicationID).child("application_id").setValue(applicationID);
                df.child(applicationID).child("adopter_id").setValue(adopterID);
                //df.child(applicationID).child("pet_id").setValue(petID);
                //df.child(applicationID).child("petName").setValue(petname);
                df.child(applicationID).child("fullname").setValue(fullname);
                df.child(applicationID).child("address").setValue(address);
                df.child(applicationID).child("phone_number").setValue(phonenum);
                df.child(applicationID).child("email").setValue(email);
                df.child(applicationID).child("reason").setValue(reason);
                df.child(applicationID).child("date_applied").setValue(date_applied);
                df.child(applicationID).child("status").setValue(status);

                //AppFormDets appform = new AppFormDets(petID, petname, fullname, address, phonenum, email, reason);

                // Use the unique key instead of the full name as the child node
                //df.child(fullname).setValue(appform);
                Toast.makeText(Fragment_Home_ApplicationForm.this, "Application Form Sent!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Fragment_Home_ApplicationForm.this, Fragment_LogIn_Home.class));
                finish();

            }
        });
    }

    private String getCurrentDate() {
        // Create a SimpleDateFormat object to format the date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Get the current date as a Date object
        Date currentDate = new Date(System.currentTimeMillis());

        // Format the date using the SimpleDateFormat
        return sdf.format(currentDate);
    }
}