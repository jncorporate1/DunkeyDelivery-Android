package app.com.dunkeydelivery.modules.home.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments.AlcoholMain;
import app.com.dunkeydelivery.modules.home.tabs.food.FoodMain;
import app.com.dunkeydelivery.modules.home.tabs.ride.RideMain;
import app.com.dunkeydelivery.utils.EnumUtils;


/**
 * Created by Developer on 6/6/14.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    //tab titles...
    private int[] tabTitles;

    //tab icons...
    protected int[] imageResId;

    private Context context;

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    /**
     * Create pager adapter
     *
     * @param fm
     */
    public ViewPagerAdapter(FragmentManager fm,
                            Context context, int[] tabTitles, int[] imageResId) {
        super(fm);
        this.context = context;
        this.tabTitles = tabTitles;
        this.imageResId = imageResId;
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment result;
        EnumUtils.HomeTabs selectedTab = EnumUtils.HomeTabs.getTab(position);
        switch (selectedTab) {
            case Food:
            case Grocery:
            case Laundry:
            case Pharmacy:
            case Retail:
                result = FoodMain.newInstance(position);
                break;
            case Alcohol:
                result = AlcoholMain.newInstance();
                break;
            case Ride:
                result = RideMain.newInstance();
                break;
            default:
                result = new Fragment();
                break;
        }
        if(result==null)
        {
            notifyDataSetChanged();
        }
        return result;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return "";
    }

    /**
     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
     * It will help us to retrieve the Fragment by position
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Remove the saved reference from our Map on the Fragment destroy
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
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
