package app.com.dunkeydelivery.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 6/30/2017.
 */

public class TitlePagerAdapter extends FragmentStatePagerAdapter {


    List<Fragment> registeredFragments = new ArrayList<Fragment>();
    List<String> titles = new ArrayList<>();

    public TitlePagerAdapter(FragmentManager fm, List<Fragment> registeredFragments, List<String> titles) {
        super(fm);
        this.registeredFragments = registeredFragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public int getCount() {
        return registeredFragments.size();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return titles.get(position);
    }
}
