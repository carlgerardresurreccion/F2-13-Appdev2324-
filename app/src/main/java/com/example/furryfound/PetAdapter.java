package com.example.furryfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PetAdapter extends BaseAdapter {

    private Context context;
    //private List<String> imageUrls;
    private String[] imageUrls;

    public PetAdapter(Fragment_Home fragmentHome, String[] imageUrls) {
        this.context = fragmentHome.requireContext();
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_screen_griditem, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.gridImage);
        String imageUrl = imageUrls[position];

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.a_1)
                .into(imageView);
        return convertView;
    }


    //LayoutInflater inflater;

    /*public PetAdapter(Fragment_Home fragmentHome, int[] image) {
        this.context = fragmentHome.requireContext();
        this.image = image;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int[] getImage() {
        return image;
    }

    public void setImage(int[] image) {
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.home_screen_griditem, null);
        }

        ImageView imageView = convertView.findViewById(R.id.gridImage);

        imageView.setImageResource(image[position]);

        return convertView;
    }

    public PetAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.fragment__home, null);

            ImageView imageView = gridView.findViewById(R.id.gridImage);

            String imageUrl = imageUrls.get(position);
            // Replace 'R.drawable.placeholder' with your placeholder resource
            // Use Glide or Picasso to load the image from the URL
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.a_1)
                    .into(imageView);
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }*/
}
