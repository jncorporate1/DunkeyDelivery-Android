package app.com.dunkeydelivery.modules.home.tabs.food.pager;

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

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.modules.account.adapters.PagerAdapter;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoreViewPager extends ToolbarFragment {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private PagerAdapter pagerAdapter;
    private Unbinder unbinder;

    private StoreBO storeBO;

    public static String ARG_PARAM2 = "isFromAlcohol";
    public static String ARG_PARAM1 = "isShowInfo";
    public static String ARG_PARAM3 = "storeBO";
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    private boolean isShowInfo = false;
    private boolean isForAlcoholStore = false;

    protected int[] icons = {R.drawable.ic_msg,
            R.drawable.ic_info_unselected
    };

    protected int[] iconsSelected = {R.drawable.ic_msg_selected,
            R.drawable.ic_info_selected
    };

    public static StoreViewPager newInstance(boolean isShowInfo, boolean isForAlcoholStore, StoreBO storeBO) {
        Bundle args = new Bundle();
        StoreViewPager fragment = new StoreViewPager();
        args.putBoolean(ARG_PARAM1, isShowInfo);
        args.putBoolean(ARG_PARAM2, isForAlcoholStore);
        args.putParcelable(ARG_PARAM3, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewpager_account,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            isShowInfo = getArguments().getBoolean(ARG_PARAM1);
            isForAlcoholStore = getArguments().getBoolean(ARG_PARAM2);
            storeBO = getArguments().getParcelable(ARG_PARAM3);
        }

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


        for (int i = 0; i < 2; i++) {
            if (isShowInfo) {
                if (i == 1) {
                    tabLayout.getTabAt(i).setIcon(iconsSelected[i]);
                } else {
                    tabLayout.getTabAt(i).setIcon(icons[i]);
                }
            } else {
                if (i == 0) {
                    tabLayout.getTabAt(i).setIcon(iconsSelected[i]);
                } else {
                    tabLayout.getTabAt(i).setIcon(icons[i]);
                }

            }

        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < 2; i++) {
                    if (i == tab.getPosition()) {
                        tabLayout.getTabAt(i).setIcon(iconsSelected[i]);
                    } else {
                        tabLayout.getTabAt(i).setIcon(icons[i]);
                    }
                }
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
        fragmentList.add(StoreReviews.newInstance(isForAlcoholStore, storeBO));
        fragmentList.add(StoreInfo.newInstance(isForAlcoholStore, storeBO));

        pagerAdapter = new PagerAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());

        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isShowInfo) {
                    //show info page...
                    viewPager.setCurrentItem(1);
                } else {
                    //show review page
                    viewPager.setCurrentItem(0);
                }

                tabLayout.setSelected(true);

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

        String title = "Wings World";
        if (storeBO != null)
            title = storeBO.getBusinessName();

        ToolbarOp.refresh(getView(), getActivity(), title,
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    public void setStore(StoreBO storeBO) {

        if (pagerAdapter != null && pagerAdapter.getItem(1) != null && pagerAdapter.getItem(1) instanceof StoreInfo)
            ((StoreInfo) pagerAdapter.getItem(1)).setStore(storeBO);
    }
}//main