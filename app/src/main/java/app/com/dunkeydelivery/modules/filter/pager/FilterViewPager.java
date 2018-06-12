package app.com.dunkeydelivery.modules.filter.pager;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.adapters.TitlePagerAdapter;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FilterViewPager extends ToolbarFragment {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private TitlePagerAdapter pagerAdapter;
    private Unbinder unbinder;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    boolean isShowDistance = false;

    protected List<String> pageTitle = new ArrayList<>();


    public static FilterViewPager newInstance(boolean isShowDistance) {
        Bundle args = new Bundle();
        FilterViewPager fragment = new FilterViewPager();
        args.putBoolean("isShowDistance", isShowDistance);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter_viewpager_2,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            isShowDistance = getArguments().getBoolean("isShowDistance");
        }


        pageTitle.add(getString(R.string.filter));
        pageTitle.add(getString(R.string.cuisines));

        setUpAdapter();
        createTabIcons();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void createTabIcons() {

        //add divider...
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(context, R.color.grey_light));
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpAdapter() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(Filter.newInstance(isShowDistance));
        fragmentList.add(Cuisines.newInstance());

        pagerAdapter = new TitlePagerAdapter(getChildFragmentManager(), fragmentList, pageTitle);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());

        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                //select first tab...
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
                viewPager.setCurrentItem(0);

            }
        }, 100);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        //TODO: here come the store name in title...
        MenuItemImgOrStr menuItemTick = new MenuItemImgOrStr(R.drawable.tick, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilter();
                Activities.goBackFragment(context, 1);
            }
        });

        MenuItemImgOrStr menuItemReset = new MenuItemImgOrStr(R.drawable.refreshbtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilers();
//                Activities.goBackFragment(context, 1);
            }
        });

        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.filter),
                null, ToolbarOp.Theme.Dark, 0, null, null, menuItemReset, menuItemTick);
    }

    private void saveFilter() {
        FilterItem item = ((Filter) pagerAdapter.getItem(0)).getFilter();
        String cuisines = ((Cuisines) pagerAdapter.getItem(1)).getSelectedCuisines();
        if (item != null)
            item.setCuisines(cuisines);
        FiltersOP.addFilter(Keys.Filter_FOOD, item);
        EventBus.getDefault().post("");
    }

    private void clearFilers() {
        FiltersOP.clearFilter(Keys.Filter_FOOD);
        ((Filter) pagerAdapter.getItem(0)).clearFilter();
        ((Cuisines) pagerAdapter.getItem(1)).clearFilter();
    }

    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

}//main