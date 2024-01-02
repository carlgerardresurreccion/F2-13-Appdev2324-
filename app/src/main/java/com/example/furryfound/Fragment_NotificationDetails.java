package com.example.furryfound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

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
        return view;
    }
}
