package com.example.furryfound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends ViewPager {

    Context context;

    int images[] = {
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
    };

    int pageTexts[] = {
            R.string.pageOneText,
            R.string.pageTwoText,
            R.string.pageThreeText,
            R.string.pageFourText,
    };

    public ViewPagerAdapter(@NonNull Context context) {
        super(context);
    }

    //@Override
    public int getCount() {
        return images.length;
    }

    //@Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    //@Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.page_slider, container, false);

        ImageView pageVector = (ImageView) view.findViewById(R.id.PageOneVector);
        TextView pageText = (TextView) view.findViewById(R.id.PageOneText);

        pageVector.setImageResource(images[position]);
        pageText.setText(pageTexts[position]);

        container.addView(view);

        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
