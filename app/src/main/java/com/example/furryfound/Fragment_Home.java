package com.example.furryfound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

public class Fragment_Home extends Fragment implements PetDetailsOnClick {
    ImageButton avatarImage;
    TextView usernameT;
    RecyclerView recyclerView;
    ArrayList<PetItem> dataList;
    MyAdapter adapter;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pets");
    final private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("adopters").child(user.getUid());

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__home, container, false);

        avatarImage = view.findViewById(R.id.avatarImage);
        usernameT = view.findViewById(R.id.usernameTextView);

        recyclerView = view.findViewById(R.id.GridDisplayPets);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        dataList = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(dataList, getContext(), this);
        recyclerView.setAdapter(adapter);

        String GuserPhoto = String.valueOf(user.getPhotoUrl());
        String Gusername = user.getDisplayName();

        Glide.with(this).load(GuserPhoto).into(avatarImage);
        String capitalizedFirstName = capitalizeFirstLetter(Gusername);
        usernameT.setText("Hi, " + capitalizedFirstName + "!");

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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PetItem pet = dataSnapshot.getValue(PetItem.class);
                    dataList.add(pet);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching data: " + error.getMessage());
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
    public void onItemClick(int position, PetItem pet) {
        Intent intent = new Intent(getContext(), Fragment_Home_PetDetails.class);
        intent.putExtra("selectedPet", pet);
        startActivity(intent);
    }
}