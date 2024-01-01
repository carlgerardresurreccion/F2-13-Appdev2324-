package com.example.furryfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final PetDetailsOnClick petDetailsOnClick;
    ArrayList<PetItem> dataList;
    Context context;

    public MyAdapter(ArrayList<PetItem> dataList, Context context, PetDetailsOnClick petDetailsOnClick) {
        this.dataList = dataList;
        this.context = context;
        this.petDetailsOnClick = petDetailsOnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_screen_griditem, parent, false);
        return new MyViewHolder(view, dataList, petDetailsOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PetItem pet = dataList.get(position);

        Glide.with(context.getApplicationContext())
                .load(pet.getImageUrl())
                .override(Target.SIZE_ORIGINAL)
                .into(holder.gridImage);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView gridImage;
        public MyViewHolder(@NonNull View itemView, ArrayList<PetItem> dataList, PetDetailsOnClick petDetailsOnClick) {
            super(itemView);
            gridImage = itemView.findViewById(R.id.GridImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(petDetailsOnClick != null) {
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION) {
                            PetItem pet = dataList.get(position);
                            petDetailsOnClick.onItemClick(position, pet);
                        }
                    }
                }
            });
        }
    }
}