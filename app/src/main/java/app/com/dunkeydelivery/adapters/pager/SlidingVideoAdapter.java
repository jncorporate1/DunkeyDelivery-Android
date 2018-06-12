package app.com.dunkeydelivery.adapters.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import app.com.dunkeydelivery.fragments.WelcomeImageFragment;
import app.com.dunkeydelivery.items.SlidingObject;

/**
 * Created by Developer on 4/28/2017.
 */

public class SlidingVideoAdapter extends FragmentStatePagerAdapter {

    private List<SlidingObject> mSlidingObjects;

    public SlidingVideoAdapter(FragmentManager fm, List<SlidingObject> mSlidingObjects) {
        super(fm);
        this.mSlidingObjects = mSlidingObjects;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
            case 1:
            case 2:
               Fragment fragment = WelcomeImageFragment.getInstance(mSlidingObjects.get(position));
                return fragment;
            default:
                return  new Fragment();

        }

//        return null;
    }

    @Override
    public int getCount() {
        return mSlidingObjects.size();
    }
}
