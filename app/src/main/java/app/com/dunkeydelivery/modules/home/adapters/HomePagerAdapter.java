package app.com.dunkeydelivery.modules.home.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;

/**
 * Created by Developer on 6/30/2017.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    //tab titles...
    private int[] tabTitles = { R.string.food, R.string.alcohol, R.string.grocery,
            R.string.laundry, R.string.pharmacy, R.string.retail, R.string.ride
    };

    //tab icons...
    protected int[] imageResId = {R.drawable.food,
            R.drawable.alcohol,
            R.drawable.grocery,
            R.drawable.laundry,
            R.drawable.pharmacy,
            R.drawable.retail,
            R.drawable.ride,
    };

    private Context context;

    List<Fragment> registeredFragments = new ArrayList<Fragment>();

    public HomePagerAdapter(FragmentManager fm, List<Fragment> registeredFragments, Context context) {
        super(fm);
        this.registeredFragments = registeredFragments;
        this.context = context;
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

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.layout_food, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_title);
        tv.setText(context.getString(tabTitles[position]));
        ImageView img = (ImageView) v.findViewById(R.id.iv_icon);
        img.setImageResource(imageResId[position]);
        return v;
    }
}
