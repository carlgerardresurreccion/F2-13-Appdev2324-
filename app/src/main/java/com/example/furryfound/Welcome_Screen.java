package com.example.furryfound;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Welcome_Screen extends AppCompatActivity {
    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;
    TextView skip;
    //Button started;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        skip = findViewById(R.id.SkipText);
        //started = findViewById(R.id.startedButton);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Welcome_Screen.this, User_Authentication_Screen.class);
                startActivity(i);
                finish();
            }
        });

        /*started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Welcome_Screen.this, User_Authentication_Screen.class);
                startActivity(i);
                finish();
            }
        });*/

        mSlideViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.PageIndicator);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSlideViewPager.setAdapter(viewPagerAdapter.getAdapter());

        setUpIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpIndicator(int position) {
        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for(int i = 0; i<dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
        }

        dots[position].setTextColor(getResources().getColor(R.color.green, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);

            /*if(position < 3) {
                started.setVisibility(View.INVISIBLE);
            } else {
                started.setVisibility(View.VISIBLE);
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}