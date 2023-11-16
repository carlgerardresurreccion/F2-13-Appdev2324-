package com.example.furryfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.furryfound.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>{

    private final List<OnBoardingItem> onBoardingItems;

    public OnboardingAdapter(List<OnBoardingItem> onBoardingItems) {
        this.onBoardingItems = onBoardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.welcome_viewpager_container, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnBoardingData(onBoardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onBoardingItems.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private final TextView textDescription;
        private final ImageView imageOnboarding;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);

        }

        void setOnBoardingData(OnBoardingItem onBoardingItem){
            textDescription.setText(onBoardingItem.getDescription());
            imageOnboarding.setImageResource(onBoardingItem.getImage());
        }
    }
}
