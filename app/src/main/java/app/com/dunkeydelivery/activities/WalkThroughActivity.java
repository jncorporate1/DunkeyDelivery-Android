package app.com.dunkeydelivery.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.auth.LoginActivity;
import app.com.dunkeydelivery.adapters.pager.SlidingVideoAdapter;
import app.com.dunkeydelivery.databinding.ActivityViewPagerBinding;
import app.com.dunkeydelivery.items.SlidingObject;

public class WalkThroughActivity extends AppCompatActivity {

    ActivityViewPagerBinding binding;

    private List<SlidingObject> mSlidingObjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_view_pager);
        setUpData();
        setUpPager();

    }//onCreate

    private void setUpPager() {
        binding.viewPager.setAdapter(new SlidingVideoAdapter(getSupportFragmentManager(), mSlidingObjects));
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.addOnPageChangeListener(new android.support.v4.view.ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(WalkThroughActivity.this, LoginActivity.class));
                            finish();
                        }
                    }, 500);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.viewIndicator.setViewPager(binding.viewPager);
    }

    private void setUpData() {
        mSlidingObjects = new ArrayList<>();
        mSlidingObjects.add(new SlidingObject("", "", R.drawable.welcome_one_bg) );
        mSlidingObjects.add(new SlidingObject("", "", R.drawable.welcome_one_bg));
        mSlidingObjects.add(new SlidingObject("Short Description 2", "Long Description 2", R.drawable.welcome_one_bg));
    }

}//main
