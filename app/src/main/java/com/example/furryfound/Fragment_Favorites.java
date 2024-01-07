package com.example.furryfound;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Favorites extends Fragment implements PetDetailsOnClick {
    MyAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<PetItem> favoritePets;
    ArrayList<String> petIDs;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets");
    final private DatabaseReference petsReference = FirebaseDatabase.getInstance().getReference("pets");
    final private DatabaseReference favoritePetsReference = FirebaseDatabase.getInstance().getReference("favorites");
    private static final int PET_DETAILS_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__favorites, container, false);

        recyclerView = view.findViewById(R.id.GridFavoritePets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoritePets = new ArrayList<>();
        petIDs = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        adapter = new MyAdapter(favoritePets, getContext(), this);
        recyclerView.setAdapter(adapter);

        fetchAndDisplayPets();

        return view;
    }

    private void fetchAndDisplayPets() {
        DatabaseReference favoritesReference = FirebaseDatabase.getInstance().getReference("favorites");
        favoritesReference.orderByChild("adopter_id").equalTo(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String petId = dataSnapshot.child("pet_id").getValue(String.class);
                            if (petId != null) {
                                petIDs.add(petId);
                            }
                        }
                        fetchPetsDetails(petIDs);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseError", "Error fetching data: " + error.getMessage());
                    }
                });
    }

    private void fetchPetsDetails(ArrayList<String> petIds) {
        DatabaseReference petsReference = FirebaseDatabase.getInstance().getReference("pets");

        for (String petId : petIds) {
            petsReference.child(petId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PetItem pet = snapshot.getValue(PetItem.class);
                    if (pet != null && pet.getStatus() == 0) {
                        favoritePets.add(pet);

                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseError", "Error fetching pet data: " + error.getMessage());
                }
            });
        }
    }

    @Override
    public void onFavoriteChanged() {

    }

    @Override
    public void onItemClick(int position, PetItem pet) {
        Intent intent = new Intent(getContext(), Fragment_Home_PetDetails.class);
        intent.putExtra("selectedPet", pet);
        startActivityForResult(intent, PET_DETAILS_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PET_DETAILS_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("petUnfavorited", false)) {
                // A pet was unfavorited, refresh the list
                favoritePets.clear();
                petIDs.clear();
                fetchAndDisplayPets();
            }
        }
    }
}