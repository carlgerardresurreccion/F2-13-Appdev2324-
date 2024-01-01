package com.example.furryfound;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Notifications extends Fragment {
    private FirebaseDatabase databaseReference;
    private DatabaseReference df;
    //private Context context;

    private List<NotificationItem> notificationList;
    private NotificationAdapter notificationAdapter;
    private int notif = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__notifications, container, false);

        // Initialize notificationList and adapter
        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notificationList);

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(notificationAdapter);

        // Set up Firebase
        databaseReference = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
        df = databaseReference.getReference("applicationform");

        // Other initialization code

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up Firebase
        databaseReference = FirebaseDatabase.getInstance("https://furry-found-default-rtdb.asia-southeast1.firebasedatabase.app");
        df = databaseReference.getReference("applicationform");

        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // The user is signed in
            df.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    // Handle new child added (if needed)
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    // Handle changes to the child data
                    String applicationId = snapshot.getKey();

                    if (snapshot.hasChild("remarks")) {
                        Integer remarks = snapshot.child("remarks").getValue(Integer.class);

                        // Check if remarks exist and process accordingly
                        if (remarks != null) {
                            if (remarks == 1) {
                                // Generate a message box for approval
                                notif = 1;
                                Log.d("Fragment_Notifications", "YourVariableName: " + notif);
                                generateMessageBox(requireContext(), "Application Approved");

                                // Show notification for approval
                                NotificationUtils.showNotification(requireContext(), "Application Approved", 1);
                            } else if (remarks == 0) {
                                // Generate a message box for disapproval
                                notif = 1;
                                Log.d("Fragment_Notifications", "YourVariableName: " + notif);
                                generateMessageBox(requireContext(), "Application Disapproved");

                                // Show notification for disapproval
                                NotificationUtils.showNotification(requireContext(), "Application Disapproved", 2);
                            }
                        }
                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    // Handle child removal (if needed)
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    // Handle child movement (if needed)
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors
                }
            });
        } else {
            // No user is signed in
            // Handle the situation accordingly, e.g., redirect to the login screen
        }
    }


    // Custom method to generate a message box
    private void generateMessageBox(Context context, String message) {
        NotificationItem newItem = new NotificationItem(message);

        // Add it to the list
        notificationList.add(newItem);

        // Notify the adapter of the change
        requireActivity().runOnUiThread(() -> notificationAdapter.notifyDataSetChanged());

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}