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

import java.io.Serializable;
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
        PetItem pet = dataList.get(position);

        Glide.with(context).load(pet.getImageUrl()).into(holder.gridImage);
        //Glide.with(context).load(dataList.get(position).getImageUrl()).into(holder.gridImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPetDetailsFragment(pet);
            }
        });
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

    private void navigateToPetDetailsFragment(PetItem pet) {
        Fragment_PetDetails petDetailsFragment = new Fragment_PetDetails();

        Bundle bundle = new Bundle();
        bundle.putSerializable("petDetails", (Serializable) pet);
        petDetailsFragment.setArguments(bundle);

        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentContainer, petDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}