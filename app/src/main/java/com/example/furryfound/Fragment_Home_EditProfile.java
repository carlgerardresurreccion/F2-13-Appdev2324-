package com.example.furryfound;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Home_EditProfile extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";
    private static final int PICK_IMAGE_REQUEST = 1;

    private CircleImageView profilePicture;
    private EditText firstName, lastName, address, phoneNumber;
    private Button save;
    private ImageView backButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_edit_profile);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("adopters");
        storageReference = FirebaseStorage.getInstance().getReference("adopter_profile_pictures");

        profilePicture = findViewById(R.id.imageView_Profile);
        firstName = findViewById(R.id.EditField_Firstname);
        lastName = findViewById(R.id.EditField_Lastname);
        address = findViewById(R.id.EditField_Address);
        phoneNumber = findViewById(R.id.EditField_PhoneNumber);
        backButton = findViewById(R.id.backButton);
        save = findViewById(R.id.Save_btn);

        setOnClickListenerForEditText(firstName);
        setOnClickListenerForEditText(lastName);
        setOnClickListenerForEditText(address);
        setOnClickListenerForEditText(phoneNumber);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
                finish(); // Finish the current activity and go back
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        showAndSetUserData();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(v);
            }
        });
    }

    private void setOnClickListenerForEditText(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    public void openImagePicker() {
        Log.d(TAG, "openImagePicker method called");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            // Upload the image to Firebase Storage
            uploadImageToFirebaseStorage(selectedImageUri);
        }
    }

    private void uploadImageToFirebaseStorage(Uri imageUri) {
        // Create a unique filename for the image
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            StorageReference imageRef = storageReference.child(user.getUid() + ".jpg");

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, get download URL
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Update the profile picture URL in the database
                            updateProfilePictureInDatabase(uri.toString());
                        });
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error uploading image to Firebase Storage", e);
                        Toast.makeText(this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void updateProfilePictureInDatabase(String downloadUrl) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = databaseReference.child(user.getUid());
            userRef.child("profile_picture").setValue(downloadUrl);

            // Set the new image URL to the ImageView
            Glide.with(this)
                    .load(downloadUrl)
                    .placeholder(R.drawable.default_profile_picture)
                    .into(profilePicture);

            Toast.makeText(this, "Profile picture has been updated", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "User not authenticated");
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }


    private void showAndSetUserData() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = databaseReference.child(user.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        User_Class userData = snapshot.getValue(User_Class.class);
                        if (userData != null) {
                            firstName.setText(userData.getFirst_name());
                            lastName.setText(userData.getLast_name());
                            address.setText(userData.getAddress());
                            phoneNumber.setText(userData.getPhone_number());

                            if (userData.getProfile_picture() != null) {
                                // Load the profile picture using a library like Glide or Picasso
                                // Example using Glide:
                                Glide.with(Fragment_Home_EditProfile.this)
                                        .load(userData.getProfile_picture())
                                        .into(profilePicture);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Error retrieving user data", error.toException());
                    Toast.makeText(Fragment_Home_EditProfile.this, "Error retrieving user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updateProfile(View view) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            DatabaseReference userRef = databaseReference.child(user.getUid());
            Map<String, Object> updates = new HashMap<>();
            updates.put("first_name", firstName.getEditableText().toString());
            updates.put("last_name", lastName.getEditableText().toString());
            updates.put("address", address.getEditableText().toString());
            updates.put("phone_number", phoneNumber.getEditableText().toString());
            userRef.updateChildren(updates);

            // Clear focus from any focused view
            clearFocusFromAllViews();

            Toast.makeText(this, "Profile has been updated", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "User not authenticated");
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFocusFromAllViews() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }
    }


    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(save.getWindowToken(), 0);
    }
}
