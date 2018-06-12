package app.com.dunkeydelivery.modules.account.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 6/30/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    List<Fragment> registeredFragments = new ArrayList<Fragment>();

    public PagerAdapter(FragmentManager fm, List<Fragment> registeredFragments) {
        super(fm);
        this.registeredFragments = registeredFragments;
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
        return "";
    }
}
