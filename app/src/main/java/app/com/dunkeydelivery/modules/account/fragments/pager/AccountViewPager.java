package app.com.dunkeydelivery.modules.account.fragments.pager;

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
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountViewPager extends ToolbarFragment {
    private Context context;
    private PagerAdapter pagerAdapter;
    private Unbinder unbinder;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    protected int[] icons = {R.drawable.ic_name_unselected,
            R.drawable.ic_card_unselected,
            R.drawable.ic_address_unselected
    };

    protected int[] iconsSelected = {R.drawable.ic_name_selected,
            R.drawable.ic_card_selected,
            R.drawable.ic_address_selected
    };

    public static AccountViewPager newInstance() {
        Bundle args = new Bundle();
        AccountViewPager fragment = new AccountViewPager();
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

        Activities.lockDrawer(context);
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

    //setup account tab icons
    private void createTabIcons() {

        //add divider line between tabs...
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(context, R.color.grey_light));
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(10);
        linearLayout.setDividerDrawable(drawable);

        for (int i = 0; i < 3; i++) {
            try {
                if (i == 0) {
                    tabLayout.getTabAt(i).setIcon(iconsSelected[i]);
                } else {
                    tabLayout.getTabAt(i).setIcon(icons[i]);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < 3; i++) {
                    try {
                        if (i == tab.getPosition()) {
                            tabLayout.getTabAt(i).setIcon(iconsSelected[i]);
                        } else {
                            tabLayout.getTabAt(i).setIcon(icons[i]);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
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

    //set selected fragment
    public void setPageSelected(int position){
        if(viewPager != null){
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            tab.select();
            viewPager.setCurrentItem(position);
        }
    }

    //setup pager adapter
    private void setUpAdapter() {

        if(pagerAdapter == null){
            List<Fragment> fragmentList = new ArrayList<>();
            fragmentList.add(MyAccount.newInstance());
            fragmentList.add(CreditCard.newInstance());
            fragmentList.add(AddressList.newInstance());

            pagerAdapter = new PagerAdapter(getChildFragmentManager(), fragmentList);
            viewPager.setAdapter(pagerAdapter);
            viewPager.setOffscreenPageLimit(3);
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {

                    viewPager.setCurrentItem(0);
                    tabLayout.setSelected(true);

                }
            }, 50);
        }else{
            viewPager.setAdapter(pagerAdapter);
        }

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.my_account),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }

}//main