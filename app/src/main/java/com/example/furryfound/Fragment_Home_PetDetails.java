package com.example.furryfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Home_PetDetails extends AppCompatActivity {
    ImageButton back, favButton;
    Button adoptButton;
    private String petId;
    FirebaseDatabase fbd;
    DatabaseReference df;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_pet_details);

        fbd = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
        df = fbd.getReference("favorites");
        auth = FirebaseAuth.getInstance();

        back = findViewById(R.id.backButton);
        adoptButton = findViewById(R.id.AdoptionButton);
        favButton = findViewById(R.id.favoriteButton);

        ImageView image = findViewById(R.id.PetProfile);
        TextView name = findViewById(R.id.PetName);
        TextView genderandtype = findViewById(R.id.PetGenderAndType);
        EditText age = findViewById(R.id.PetAge);
        EditText breed = findViewById(R.id.PetBreed);
        EditText weight = findViewById(R.id.PetWeight);
        EditText dateArrived = findViewById(R.id.PetDateArrived);
        EditText daysAtShelter = findViewById(R.id.PetDaysAtTheShelter);
        EditText description = findViewById(R.id.PetDescription);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedPet")) {
            PetItem pet = intent.getParcelableExtra("selectedPet");
            petId = pet.getPetID();

            Glide.with(this)
                    .load(pet.getImageUrl())
                    .into(image);

            name.setText(pet.getName());
            age.setText(pet.getAge());
            genderandtype.setText(pet.getGender() + ", " + pet.getType());
            breed.setText(pet.getBreed());
            weight.setText(pet.getWeight());
            dateArrived.setText(String.valueOf(pet.getDateArrived()));
            daysAtShelter.setText(String.valueOf(pet.getDaysAtShelter()));
            description.setText(pet.getDescription());
        }

        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String adopter = user.getUid();
                String pet = petId;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FavoriteItem favoriteItem = dataSnapshot.getValue(FavoriteItem.class);
                    if (favoriteItem != null) {
                        String adopterIdInDatabase = favoriteItem.getAdopterID();
                        String petIdInDatabase = favoriteItem.getPetID();

                        if (adopter.equals(adopterIdInDatabase) && pet.equals(petIdInDatabase)) {
                            favButton.setSelected(true);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Fragment_Home_PetDetails.this, Fragment_Home_ApplicationForm.class);
                intent1.putExtra("petId", petId);
                startActivity(intent1);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = !v.isSelected();
                v.setSelected(isSelected);
                df = fbd.getReference("favorites");
                FirebaseUser currentUser = auth.getCurrentUser();
                String adopterID = currentUser.getUid();
                String petID = petId;

                Query query = df.orderByChild("adopter_id").equalTo(adopterID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isFavorite = false;
                        String favoritesID = "";
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.child("pet_id").getValue(String.class).equals(petID)) {
                                isFavorite = true;
                                favoritesID = dataSnapshot.child("favorites_id").getValue(String.class);
                                break;
                            }
                        }

                        if (isFavorite) {
                            // If it was a favorite, remove it
                            df.child(favoritesID).removeValue().addOnSuccessListener(aVoid -> {
                                // Notify that unfavoriting was successful
                                Intent data = new Intent();
                                data.putExtra("petUnfavorited", true);
                                setResult(RESULT_OK, data);
                                // You can finish() here if you want to close the detail view
                                // finish();
                            });
                        } else {
                            // If it wasn't a favorite, add it
                            favoritesID = SecureRandomIdGenerator.generateSecureRandomId();
                            df.child(favoritesID).child("favorites_id").setValue(favoritesID);
                            df.child(favoritesID).child("adopter_id").setValue(adopterID);
                            df.child(favoritesID).child("pet_id").setValue(petID).addOnSuccessListener(aVoid -> {
                                // Notify that favoriting was successful
                                Intent data = new Intent();
                                data.putExtra("petFavorited", true);
                                setResult(RESULT_OK, data);
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseError", "Error fetching data: " + error.getMessage());
                    }
                });
            }
        });
    }
}