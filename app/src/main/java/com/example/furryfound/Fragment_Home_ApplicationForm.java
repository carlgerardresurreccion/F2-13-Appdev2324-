package com.example.furryfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Fragment_Home_ApplicationForm extends AppCompatActivity {

    private EditText  reasonEditText, petnameET, petIDET, fullNameText, addressText, phoneNumberText, emailText;
    Button sendButton;

    FirebaseDatabase fbd;
    DatabaseReference df;

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
        petnameET = findViewById(R.id.PetName);
        petIDET = findViewById(R.id.PetID);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fbd = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
                df = fbd.getReference("applicationform");


                String petname = petnameET.getEditableText().toString();
                String petID = petIDET.getEditableText().toString();
                String fullname = fullNameText.getEditableText().toString();
                String address = addressText.getEditableText().toString();
                String phonenum = phoneNumberText.getEditableText().toString();
                String email = emailText.getEditableText().toString();
                String reason = reasonEditText.getEditableText().toString();


                df.child(fullname).child("petID").setValue(petID);
                df.child(fullname).child("petName").setValue(petname);
                df.child(fullname).child("fullname").setValue(fullname);
                df.child(fullname).child("address").setValue(address);
                df.child(fullname).child("phone_number").setValue(phonenum);
                df.child(fullname).child("email").setValue(email);
                df.child(fullname).child("reason").setValue(reason);

                //AppFormDets appform = new AppFormDets(petID, petname, fullname, address, phonenum, email, reason);

                // Use the unique key instead of the full name as the child node
                //df.child(fullname).setValue(appform);
                Toast.makeText(Fragment_Home_ApplicationForm.this, "Application Form Sent!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Fragment_Home_ApplicationForm.this, LogIn.class));
                finish();

            }
        });
    }
}