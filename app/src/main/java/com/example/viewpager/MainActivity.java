package com.example.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    LinearLayout sliderDotspanel;
    ViewPager viewPager;
    SlidingImageAdapter slidingImageadapter;
    private int dotscount;
    private ImageView[] dots;
    Handler handler = new Handler();
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sliderDotspanel = findViewById(R.id.SliderDots);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(20);

        slidingImageadapter = new SlidingImageAdapter(this);
        viewPager.setAdapter(slidingImageadapter);


        dotscount = slidingImageadapter.getCount();
        dots = new ImageView[dotscount];

        TimerTask timer = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int numPages = viewPager.getAdapter().getCount();
                        page = (page + 1) % numPages;
                        viewPager.setCurrentItem(page);
                    }
                });
            }
        };
        Timer time = new Timer();
        time.scheduleAtFixedRate(timer, 0, 1432);

        dotscount = slidingImageadapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.share_nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.share_active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.share_nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.share_active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public class SlidingImageAdapter extends PagerAdapter {

        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private Integer[] mResources = {R.drawable.slide_1,R.drawable.slide_2,
                R.drawable.slide_3,R.drawable.slide_4,
                R.drawable.slide_5,R.drawable.slide_6,
                R.drawable.slide_7,R.drawable.slide_8,
                R.drawable.slider_9,R.drawable.slider_10};

        public SlidingImageAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mLayoutInflater.inflate(R.layout.custom_pager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);


            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
            return view;

            
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager vp = (ViewPager) container;
            View view = (View) object;
            vp.removeView(view);

        }
    }
}
