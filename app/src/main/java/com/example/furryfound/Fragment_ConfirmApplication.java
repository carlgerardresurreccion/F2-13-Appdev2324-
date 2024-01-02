package com.example.furryfound;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_ConfirmApplication extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_thankyou, container, false);

        Button dismissBtn = view.findViewById(R.id.dismissBtn);
        if (dismissBtn != null) {
            dismissBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment_Notifications notificationsFragment = new Fragment_Notifications();

                    // Assuming you're using the support FragmentManager and R.id.FragmentContainer is your container ID
                    if (getFragmentManager() != null) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.FragmentContainer, notificationsFragment)
                                .commit();
                    }
                }
            });

        }

        return view;
    }
}
