package com.example.furryfound;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Home extends Fragment implements PetDetailsOnClick {
    ImageButton avatarImage;
    TextView usernameT;
    RecyclerView recyclerView;
    ArrayList<PetItem> dataList;
    ArrayList<PetItem> allPetsList;
    MyAdapter adapter;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets");
    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("adopters").child(user.getUid());

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__home, container, false);

        avatarImage = view.findViewById(R.id.avatarImage);
        usernameT = view.findViewById(R.id.usernameTextView);

        recyclerView = view.findViewById(R.id.GridDisplayPets);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        allPetsList = new ArrayList<>(); // Initialize full list
        dataList = new ArrayList<>(); // List for adapter
        adapter = new MyAdapter(dataList, getContext(), this);
        recyclerView.setAdapter(adapter);

        String GuserPhoto = String.valueOf(user.getPhotoUrl());
        String Gusername = user.getDisplayName();

        Glide.with(this).load(GuserPhoto).into(avatarImage);
        String capitalizedFirstName = capitalizeFirstLetter(Gusername);
        usernameT.setText("Hi, " + capitalizedFirstName + "!");

        if (user != null) {
            // Initialize reference to user in Firebase Realtime Database
            userReference = FirebaseDatabase.getInstance().getReference("adopters").child(user.getUid());
            loadUserProfile();
        }
        fetchAndDisplayPets();

        SearchView searchView = view.findViewById(R.id.searchView);

// Set a query listener for the search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPets(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPets(newText);
                return true;
            }
        });

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User_Class userData = snapshot.getValue(User_Class.class);
                    if (userData != null) {
                        String capitalizedFirstName = capitalizeFirstLetter(userData.getFirst_name());
                        usernameT.setText("Hi, " + capitalizedFirstName + "!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Fragment_Home_EditProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    @Override
    public void onFavoriteChanged() {

    }

    @Override
    public void onItemClick(int position, PetItem pet) {
        Intent intent = new Intent(getContext(), Fragment_Home_PetDetails.class);
        intent.putExtra("selectedPet", pet);
        startActivity(intent);
    }

    private void fetchAndDisplayPets() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allPetsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PetItem pet = dataSnapshot.getValue(PetItem.class);
                    if (pet != null) {
                        allPetsList.add(pet);
                    }
                }
                dataList.clear();
                dataList.addAll(allPetsList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching data: " + error.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserProfile(); // Refresh user profile when fragment resumes
        fetchAndDisplayPets(); // Fetch and display pets
    }

    private void loadUserProfile() {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User_Class userData = snapshot.getValue(User_Class.class);
                    if (userData != null) {
                        if (userData.getProfile_picture() != null) {
                            Glide.with(Fragment_Home.this)
                                    .load(userData.getProfile_picture())
                                    .placeholder(R.drawable.default_profile_picture)
                                    .error(R.drawable.profile)
                                    .circleCrop() // This will crop the image in a circular shape
                                    .into(avatarImage);
                        }
                        String capitalizedFirstName = capitalizeFirstLetter(userData.getFirst_name());
                        usernameT.setText("Hi, " + capitalizedFirstName + "!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching user profile", error.toException());
            }
        });
    }

    private void searchPets(String query) {
        if (query == null || query.isEmpty()) {
            dataList.clear();
            dataList.addAll(allPetsList); // Restore original data if query is empty
        } else {
            ArrayList<PetItem> searchResults = new ArrayList<>();
            for (PetItem pet : allPetsList) {
                if (pet.getBreed().toLowerCase().contains(query.toLowerCase()) || pet.getType().toLowerCase().contains(query.toLowerCase())) {
                    searchResults.add(pet);
                }
            }
            dataList.clear();
            dataList.addAll(searchResults); // Filtered results
        }
        adapter.notifyDataSetChanged();
    }


}