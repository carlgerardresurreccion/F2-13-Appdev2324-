package com.example.furryfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<PetItem> dataList;
    Context context;

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
}

/*public class MyAdapter extends BaseAdapter {
    private ArrayList<PetItem> dataList;
    private Context context;
    LayoutInflater layoutInflater;

    public MyAdapter(ArrayList<PetItem> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View  view, ViewGroup parent) {
        if(layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null) {
            view = layoutInflater.inflate(R.layout.home_screen_griditem, null);
        }

        ImageView gridImage = view.findViewById(R.id.GridImage);

        Glide.with(context)
                .load(dataList.get(position).getImageUrl())
                .placeholder(R.drawable.a_1)
                .error(R.drawable.a_2)
                .into(gridImage);
        return view;
    }
}*/
