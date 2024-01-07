package com.example.furryfound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment_NotificationDetails extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Extracting the arguments passed to this Fragment
        Bundle args = getArguments();
        int remarks = -1;
        final String applicationId = args != null ? args.getString("application_id", "") : "";
        if (args != null) {
            remarks = args.getInt("remarks", -1);
            Log.d("NotificationDetails", "Retrieved Remarks: " + remarks); // Debug log
        }

        // Choosing the layout based on the remarks
        View view;
        if (remarks == 1) { // Approved (remarks == 1)
            view = inflater.inflate(R.layout.notification_item, container, false);
        } else if (remarks == 2) { // To be confirmed (remarks == 2)
            view = inflater.inflate(R.layout.notification_item_confirm, container, false);
        } else { // Disapproved (remarks == 0) or Cancelled (remarks == -1)
            view = inflater.inflate(R.layout.notification_item_not_approved, container, false);
        }

        String petName = args.getString("petName", "Unknown");
        String shelterName = args.getString("shelterName", "Unknown");
        String message = args.getString("message", "");
        String feedback = args.getString("feedback", "");

        EditText petNameView = view.findViewById(R.id.petName);
        EditText shelterNameView = view.findViewById(R.id.shelterName);
        EditText messageView = view.findViewById(R.id.evaluation);
        EditText feedbackView = view.findViewById(R.id.feedback);

        // Setting the extracted data to the views
        if (args != null) {
            petNameView.setText(args.getString("petName", "Unknown"));
            shelterNameView.setText(args.getString("shelterName", "Unknown"));
            messageView.setText(args.getString("message", ""));
            feedbackView.setText(args.getString("feedback", ""));
        }

        // Button logic
        Button dismissBtn = view.findViewById(R.id.dismissBtn);
        if (dismissBtn != null) {
            dismissBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Logic for dismiss button
                    if (getFragmentManager() != null) {
                        getFragmentManager().popBackStack();
                    }
                }
            });
        }

        Button cancelBtn = view.findViewById(R.id.cancelBtn);
        if (cancelBtn != null && remarks == 1) { // Cancel button logic only for approved applications

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelUpdateStatus(applicationId, true);
                    Fragment_CancelApplication cancelFragment = new Fragment_CancelApplication();

                    Bundle cancelBundle = new Bundle();
                    cancelBundle.putString("application_id", applicationId);
                    cancelFragment.setArguments(cancelBundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FragmentContainer, cancelFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        ImageView backButton = view.findViewById(R.id.backButton);
        if (backButton != null && remarks == 2) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Pop the back stack to go back to the previous fragment
                    if (getFragmentManager() != null) {
                        getFragmentManager().popBackStack();
                    }
                }
            });
        }

        Button confirmBtn = view.findViewById(R.id.confirmBtn);
        if (confirmBtn != null && remarks == 2) {
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmUpdateStatus(applicationId);

                    Fragment_ConfirmApplication confirmFragment = new Fragment_ConfirmApplication();

                    Bundle confirmBtn = new Bundle();
                    confirmBtn.putString("application_id", applicationId);
                    confirmFragment.setArguments(confirmBtn);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FragmentContainer, confirmFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
        return view;
    }

    private void cancelUpdateStatus(String applicationId, boolean isCancellation) {
        DatabaseReference applicationRef = FirebaseDatabase.getInstance().getReference("applicationform").child(applicationId);

        applicationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot applicationSnapshot) {
                if (applicationSnapshot.exists()) {
                    // Get the pet_id from the application data
                    String petId = applicationSnapshot.child("pet_id").getValue(String.class);

                    if (isCancellation && petId != null && !petId.isEmpty()) {
                        // Update the pet status in the pets node to 0 (available)
                        DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
                        petRef.child("status").setValue(0)
                                .addOnSuccessListener(aVoid -> {
                                    // Handle success for pet status update
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failure
                                });

                        // Update the application status to 1 and remarks to -1
                        applicationRef.child("status").setValue(1);
                        applicationRef.child("remarks").setValue(-1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void confirmUpdateStatus(String applicationId) {
        DatabaseReference applicationRef = FirebaseDatabase.getInstance().getReference("applicationform").child(applicationId);

        applicationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot applicationSnapshot) {
                if (applicationSnapshot.exists()) {
                    // Update the application status
                    applicationRef.child("status").setValue(1)
                            .addOnSuccessListener(aVoid -> {
                                // Handle success for application status update
                            })
                            .addOnFailureListener(e -> {
                                // Handle failure
                            });

                    // Get the pet_id from the application data
                    String petId = applicationSnapshot.child("pet_id").getValue(String.class);
                    if (petId != null && !petId.isEmpty()) {
                        // Update the pet status in the pets node
                        DatabaseReference petRef = FirebaseDatabase.getInstance().getReference("pets").child(petId);
                        petRef.child("status").setValue(2)
                                .addOnSuccessListener(aVoid -> {
                                    // Handle success for pet status update
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failure
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
