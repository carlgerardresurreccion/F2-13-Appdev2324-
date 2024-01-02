package com.example.furryfound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_CancelApplication extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_cancel_application, container, false);

        Bundle args = getArguments();
        String applicationId = args != null ? args.getString("application_id", "") : "";

        EditText reasonForCancellationEditText = view.findViewById(R.id.reasonForCancellation);
        Button cancelApplicationBtn = view.findViewById(R.id.cancelApplicationBtn);

        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pop the back stack to go back to the previous fragment
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        cancelApplicationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reasonForCancellation = reasonForCancellationEditText.getText().toString();
                updateCancellationReason(applicationId, reasonForCancellation);
            }
        });

        return view;
    }

    private void updateCancellationReason(String applicationId, String reason) {
        DatabaseReference applicationRef = FirebaseDatabase.getInstance().getReference("applicationform").child(applicationId);
        applicationRef.child("reason_for_cancellation").setValue(reason)
                .addOnSuccessListener(aVoid -> {
                    // Navigate back to notifications screen
                    if (getFragmentManager() != null) {
                        getFragmentManager().popBackStack();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }
}
