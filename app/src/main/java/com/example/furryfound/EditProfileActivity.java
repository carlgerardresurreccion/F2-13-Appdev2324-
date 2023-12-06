package com.example.furryfound;

import static com.google.android.gms.common.util.DeviceProperties.isPhone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class EditProfileActivity extends AppCompatActivity {

    EditText username, address, phonenum, email;
    Button save;
    String _USERNAME, _ADDRESS, _PHONENUM, _EMAIL;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference("adopters");

        username = findViewById(R.id.EditField_Username);
        address = findViewById(R.id.EditField_Address);
        phonenum = findViewById(R.id.EditField_PhoneNumber);
        email = findViewById(R.id.EditField_Email);

        showAllUserData();
    }

    private void showAllUserData() {
        Intent intent  = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _ADDRESS = intent.getStringExtra("address");
        _PHONENUM = intent.getStringExtra("phonenum");
        _EMAIL = intent.getStringExtra("email");

        username.setText(_USERNAME);
        address.setText(_ADDRESS);
        phonenum.setText((_PHONENUM));
        email.setText(_EMAIL);
    }

    public void updateprof(View view){
        
        if(isUser() || isAddress() || isPhoneNum() || isEmail()){
            Toast.makeText(this, "Profile has been updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Profile has no changes", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmail() {
        if(!_EMAIL.equals(email.getEditableText().toString())){
            databaseReference.child(_EMAIL).child("email").setValue(email.getEditableText().toString());
            _EMAIL = email.getEditableText().toString();
            return true;
        } else
            return false;
    }

    private boolean isPhoneNum() {
        if(!_PHONENUM.equals(phonenum.getEditableText().toString())){
            databaseReference.child(_PHONENUM).child("phone_number").setValue(phonenum.getEditableText().toString());
            _PHONENUM = phonenum.getEditableText().toString();
            return true;
        } else
            return false;
    }

    private boolean isAddress() {
        if(!_ADDRESS.equals(address.getEditableText().toString())){
            databaseReference.child(_ADDRESS).child("address").setValue(address.getEditableText().toString());
            _ADDRESS = address.getEditableText().toString();
            return true;
        } else
            return false;
    }

    private boolean isUser() {
        if(!_USERNAME.equals(username.getEditableText().toString())){
            databaseReference.child(_USERNAME).child("username").setValue(username.getEditableText().toString());
            _USERNAME = username.getEditableText().toString();
            return true;
        } else
            return false;
    }
}