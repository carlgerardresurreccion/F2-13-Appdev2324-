package com.example.furryfound;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<PetItem> dataList;
    Context context;
    private ViewGroup parent;
    private int viewType;

    public MyAdapter(ArrayList<PetItem> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_griditem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImageUrl()).into(holder.gridImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView gridImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gridImage = itemView.findViewById(R.id.GridImage);
        }
    }

    /*@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment__pet_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetItem pet = dataList.get(position);

        // Set your pet details to the views in the ViewHolder
        holder.petNameTextView.setText(pet.getName());
        holder.petImageView.setImageResource(pet.getImageResource());

        // Set click listener for the pet item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click and navigate to the details fragment
                handleItemClick(pet);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declare your views here
        public TextView petNameTextView;
        public ImageView petImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize your views here
            petNameTextView = itemView.findViewById(R.id.petNameTextView);
            petImageView = itemView.findViewById(R.id.petImageView);
        }
    }

    private void handleItemClick(PetItem pet) {
        // Handle the item click event here
        // You can navigate to the details fragment or perform any other action
        // For example, navigate to the details fragment:
        navigateToPetDetailsFragment(pet);
    }

    private void navigateToPetDetailsFragment(PetItem pet) {
        Fragment_PetDetails fragment = Fragment_PetDetails.newInstance(pet);
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment with the details fragment
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/
}