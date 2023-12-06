package com.example.furryfound;

import static android.widget.Toast.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.furryfound.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment {

    FragmentHomeBinding binding;
    GridView gridView;
    // ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("pets");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> imageUrls = new ArrayList<>();

                for (DataSnapshot petSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = petSnapshot.child("imageUrl").getValue(String.class);
                    if (imageUrl != null) {
                        imageUrls.add(imageUrl);
                    }
                }

                Log.d("ImageUrls", "Image URLs: " + imageUrls);
                // Create and set the adapter with image URLs
                PetAdapter petAdapter = new PetAdapter(Fragment_Home.this, imageUrls.toArray(new String[0]));
                binding.GridDisplayPets.setAdapter(petAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        return view;

    /*FragmentHomeBinding binding;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment__home, container, false);
        //gridView = view.findViewById(R.id.GridDisplayPets);

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        //setContentView(binding.getRoot());

        int[] image = {R.drawable.a_1, R.drawable.a_2};

        PetAdapter petAdapter = new PetAdapter(Fragment_Home.this, image);
        binding.GridDisplayPets.setAdapter(petAdapter);

        binding.GridDisplayPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Fragment_Home.this, "Pag click jud dae", LENGTH_SHORT).show();
            }
        });

        List<String> imageUrls = new ArrayList<>();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("pets");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot petSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = petSnapshot.child("imageUrl").getValue(String.class);
                    if (imageUrl != null) {
                        imageUrls.add(imageUrl);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //makeText(Fragment_Home.this, "Error occured", LENGTH_SHORT).show();
            }
        });

        PetAdapter adapter = new PetAdapter(view.getContext(), imageUrls);
        gridView.setAdapter(adapter);

        return view;*/
    }
}