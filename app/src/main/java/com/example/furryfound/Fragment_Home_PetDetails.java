package com.example.furryfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Fragment_Home_PetDetails extends AppCompatActivity {
    ImageButton back;
    Button adoptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_pet_details);

        back = findViewById(R.id.backButton);
        adoptButton = findViewById(R.id.AdoptionButton);
        ImageView image = findViewById(R.id.PetProfile);
        TextView name = findViewById(R.id.PetName);
        TextView genderandbreed = findViewById(R.id.PetGenderAndBreed);
        EditText age = findViewById(R.id.PetAge);
        EditText color = findViewById(R.id.PetColor);
        EditText weight = findViewById(R.id.PetWeight);
        EditText dateArrived = findViewById(R.id.PetDateArrived);
        EditText daysAtShelter = findViewById(R.id.PetDaysAtTheShelter);
        EditText description = findViewById(R.id.PetDescription);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedPet")) {
            PetItem pet = intent.getParcelableExtra("selectedPet");

            Glide.with(this)
                    .load(pet.getImageUrl())
                    .into(image);

            name.setText(pet.getName());
            age.setText(pet.getAge());
            genderandbreed.setText(pet.getGender() + ", " + pet.getType());
            color.setText(pet.getColor());
            weight.setText(String.valueOf(pet.getWeight()));
            dateArrived.setText(String.valueOf(pet.getDateArrived()));
            daysAtShelter.setText(String.valueOf(pet.getDaysAtShelter()));
            description.setText(pet.getDescription());
        }

        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Fragment_Home_PetDetails.this, Fragment_Home_ApplicationForm.class);
                startActivity(intent1);
            }
        });
    }
}