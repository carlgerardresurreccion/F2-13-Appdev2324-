package com.example.furryfound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Notifications extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference df;
    private List<NotificationItem> notificationList;
    private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__notifications, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(notificationAdapter);

        database = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
        updateNotificationList();

        return view;
    }

    private void updateNotificationList() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String adopterId = currentUser.getUid();
            DatabaseReference applicationsRef = database.getReference("applicationform");

            applicationsRef.orderByChild("adopter_id").equalTo(adopterId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot applicationSnapshot : dataSnapshot.getChildren()) {
                            Integer remarks = applicationSnapshot.child("remarks").getValue(Integer.class);
                            Integer status = applicationSnapshot.child("status").getValue(Integer.class);
                            String petId = applicationSnapshot.child("pet_id").getValue(String.class);
                            if (remarks != null && remarks == 1 && petId != null || remarks == 0 && status == 1 && petId != null || remarks == -1 && status == 1 && petId != null) {
                                DatabaseReference petsRef = database.getReference("pets").child(petId);
                                petsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot petSnapshot) {
                                        String shelterId = petSnapshot.child("shelter_id").getValue(String.class);
                                        if (shelterId != null) {
                                            DatabaseReference sheltersRef = database.getReference("shelters").child(shelterId);
                                            sheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot shelterSnapshot) {
                                                    String shelterName = shelterSnapshot.child("shelter_name").getValue(String.class);
                                                    String profilePictureUrl = shelterSnapshot.child("profile_picture").getValue(String.class);
                                                    String message;
                                                    if (remarks == 0) {
                                                        message = "Your application has been disapproved.";
                                                    } else if (remarks == 1) {
                                                        message = "Your application has been approved.";
                                                    } else if (remarks == -1) {
                                                        message = "Your application has been cancelled.";
                                                    } else {
                                                        message = "";
                                                    }


                                                    NotificationItem newItem = new NotificationItem(
                                                            shelterName,
                                                            profilePictureUrl,
                                                            message
                                                    );
                                                    notificationList.add(newItem);
                                                    notificationAdapter.notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    Log.w("FirebaseDatabase", "loadShelter:onCancelled", databaseError.toException());
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.w("FirebaseDatabase", "loadPet:onCancelled", databaseError.toException());
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("FirebaseDatabase", "loadApplicationForm:onCancelled", databaseError.toException());
                    }
                });
        }
    }
}
