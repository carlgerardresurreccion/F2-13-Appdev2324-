package com.example.furryfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private ArrayList<PetItem> favoritePets;
    Context context;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName;
        TextView petGenderAndBreed;

        ViewHolder(View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.favoritePetImage);
            petName = itemView.findViewById(R.id.favoritePetName);
            petGenderAndBreed = itemView.findViewById(R.id.favoriteGenderAndBreed);
        }
    }

    public FavoritesAdapter(ArrayList<PetItem> favoritePets, Context context) {
        this.favoritePets = favoritePets;
        this.context = context;
    }

    // Method to update the data in the adapter
    void setFavoritePets(ArrayList<PetItem> pets) {
        favoritePets = pets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_griditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetItem favoritePet = favoritePets.get(position);

        if (favoritePet != null) {
            Glide.with(context).load(favoritePet.getImageUrl()).into(holder.petImage);
            holder.petName.setText(favoritePet.getName());
            holder.petGenderAndBreed.setText(favoritePet.getGender() + ", " + favoritePet.getType());
        }
    }

    @Override
    public int getItemCount() {
        return favoritePets.size();
    }
}

