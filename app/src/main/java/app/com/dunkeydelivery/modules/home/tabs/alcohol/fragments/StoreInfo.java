package app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.adapters.StoreInfoAdapter;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.AlcoholStoreBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.StoreViewPager;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoreInfo extends ToolbarFragment {

    private Context context;
    private Integer savePosition;
    private StoreInfoAdapter mAdapter;
    private boolean isHidden = false;
    private Unbinder unbinder;
    private ArrayList<AlcoholStoreBO> alcoholStoreBOS;
    private List<StoreBO> storeBOS;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_new_streams)
    TextView tvNewStreams;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    public static StoreInfo newInstance(ArrayList<AlcoholStoreBO> alcoholStoreBO) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("alcoholStoreBo", alcoholStoreBO);
        StoreInfo fragment = new StoreInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_recycleview,
                container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views
        alcoholStoreBOS = getArguments().getParcelableArrayList("alcoholStoreBo");

        setUpRecycler(new ArrayList<StoreBO>());

        rvSwipeRefresh.setEnabled(false);
        tvSwipeRefresh.setEnabled(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        refreshToolbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //setup stores info recycler
    private void setUpRecycler(List<StoreBO> storeBOs) {

        this.storeBOS = storeBOs;
        String categoryName = null;

        for (AlcoholStoreBO alcoholStoreBO : alcoholStoreBOS) {
            try {
                if (alcoholStoreBO != null) {
                    List<Categories> alcoholCategories = alcoholStoreBO.getCategories();
                    if (alcoholCategories != null && alcoholCategories.size() > 0) {
                        for (Categories categories : alcoholStoreBO.getCategories()) {
                            try {
                                if (categories.getName().equals("Wine") || categories.getName().equals("Liquor")) {
                                    categoryName = "Wine & liquor store";
                                } else if (categories.getName().equals("Beer")) {
                                    categoryName = "Beer store";
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            storeBOs.add(new StoreBO(categoryName, true));
            storeBOs.add(new StoreBO(alcoholStoreBO.getMinDeliveryCharges(), alcoholStoreBO.getBusinessName(), alcoholStoreBO.getId(), alcoholStoreBO.getBusinessType(), alcoholStoreBO.getDescription(), alcoholStoreBO.getBusinessName(), alcoholStoreBO.getLatitude(), alcoholStoreBO.getLongitude(), alcoholStoreBO.getOpen_From(), alcoholStoreBO.getOpen_To(), alcoholStoreBO.getAverageRating(), alcoholStoreBO.getImageUrl(), alcoholStoreBO.getAddress(), alcoholStoreBO.getMinOrderPrice(), alcoholStoreBO.getStoreDeliveryHours(), alcoholStoreBO.getStoreTags(), alcoholStoreBO.getDistance(), alcoholStoreBO.getContactNumber(), alcoholStoreBO.getMinDeliveryTime(), null, alcoholStoreBO.getRatingType()));
        }

        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new StoreInfoAdapter(storeBOs, context, recyclerView);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                }
            });

            recyclerView.setAdapter(mAdapter);
            setListeners();
        } else {
            mAdapter.addItems(storeBOs);
            mAdapter.setLoaded();
        }
    }

    //set listeners for store info adapter
    private void setListeners() {

        mAdapter.setClickListener(new StoreInfoAdapter.ClickListeners() {
            @Override
            public void onRowClick(int position) {
            }
            @Override
            public void onChangeStoreClick(int position) {
                String storeIds="";
                if (position == 0) {
                    savePosition = position + 1;
                    if(alcoholStoreBOS.size()==2)
                    {
                        storeIds=mAdapter.getItem(1).getId()+"/"+mAdapter.getItem(3).getId();
                    }
                    else
                    {
                        storeIds=mAdapter.getItem(1).getId()+"";
                    }
                    Activities.gotoNextFragment(context, ChangeStore.newInstance(false, storeIds));
                } else if(position==2){
                    storeIds=mAdapter.getItem(3).getId()+"/"+mAdapter.getItem(1).getId();
                    savePosition = position + 1;
                    Activities.gotoNextFragment(context, ChangeStore.newInstance(true, storeIds));
                }
            }
            @Override
            public void onStoreReviewClick(int position) {
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(false, true, storeBOS.get(position)));
            }
            @Override
            public void onStoreInfoClick(int position) {
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(true, true, storeBOS.get(position)));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.store_info),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }
}