package com.example.furryfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Screen_Welcome extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicator;
    private Button buttonOnboardingAction;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        skip = findViewById(R.id.SkipText);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Screen_Welcome.this, Screen_User_Authentication.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnBoardingAction);

        setOnboardingItem();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setOnboadingIndicator();
        setCurrentOnboardingIndicators(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);

                if (position == onboardingAdapter.getItemCount() - 1) {
                    buttonOnboardingAction.setVisibility(View.VISIBLE);
                } else {
                    buttonOnboardingAction.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonOnboardingAction.setOnClickListener(view -> {
            /*if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
            } else {
                */startActivity(new Intent(getApplicationContext(), Screen_User_Authentication.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            //}
        });

    }

    private void setOnboadingIndicator() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_active
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void setCurrentOnboardingIndicators(int index) {
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Get Started");
        }else {
            buttonOnboardingAction.setText("Next");
        }
    }
    private void setOnboardingItem() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem page1 = new OnBoardingItem();
        page1.setDescription(getString(R.string.pageOneText));
        page1.setImage(R.drawable.welcome_1);

        OnBoardingItem page2 = new OnBoardingItem();
        page2.setDescription(getString(R.string.pageTwoText));
        page2.setImage(R.drawable.welcome_2);

        OnBoardingItem page3 = new OnBoardingItem();
        page3.setDescription(getString(R.string.pageThreeText));
        page3.setImage(R.drawable.welcome_3);

        OnBoardingItem page4 = new OnBoardingItem();
        page4.setDescription(getString(R.string.pageFourText));
        page4.setImage(R.drawable.welcome_4);

        onBoardingItems.add(page1);
        onBoardingItems.add(page2);
        onBoardingItems.add(page3);
        onBoardingItems.add(page4);

        onboardingAdapter = new OnboardingAdapter(onBoardingItems);

    }
}
