package com.example.furryfound;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_PetDetails extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__pet_details, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("petDetails")) {
            PetItem pet = (PetItem) bundle.getSerializable("petDetails");

            TextView petNameTextView = view.findViewById(R.id.PetName);
            petNameTextView.setText(pet.getName());
        }

        return view;
    }
}