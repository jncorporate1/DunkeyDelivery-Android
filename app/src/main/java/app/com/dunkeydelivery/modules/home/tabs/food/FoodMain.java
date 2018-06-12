package app.com.dunkeydelivery.modules.home.tabs.food;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.filter.pager.Filter;
import app.com.dunkeydelivery.modules.filter.pager.FilterViewPager;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.modules.home.adapters.NearByStoreAdapter;
import app.com.dunkeydelivery.modules.home.adapters.PopularStoreAdapter;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.grocery.StoreSubCategories;
import app.com.dunkeydelivery.modules.home.tabs.laundry.LaundryStoreDetail;
import app.com.dunkeydelivery.modules.home.tabs.pharmacy.PharmacyDetail;
import app.com.dunkeydelivery.modules.search.items.AddressItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FoodMain extends Fragment implements View.OnClickListener {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;

    private int startIndex = 0;
    private int totalRecords = 0;

    public static String ARG_PARAM1 = "selectedTab";
    EnumUtils.HomeTabs selectedTab;

    private PopularStoreAdapter mAdapter;
    private NearByStoreAdapter nearByStoreAdapter;
    private AddressItem selectedAddressItem;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefreshLayout;
    @BindView(R.id.content_swipe_refresh)
    SwipeRefreshLayout contentSwipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_near_you)
    RecyclerView rvNearYou;

    @BindView(R.id.ll_popular)
    LinearLayout llPopular;

    @BindView(R.id.tv_noresult_nearby)
    TextView tvNoresultNearby;

    @BindView(R.id.tv_nearby)
    TextView tvNearby;

    @BindView(R.id.tv_popular)
    TextView tvPopular;

    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_add_item)
    TextView tv_add_item;

    public static FoodMain newInstance(int selectedTab) {
        Bundle args = new Bundle();
        FoodMain fragment = new FoodMain();
        args.putInt(ARG_PARAM1, selectedTab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_food_main, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            selectedTab = EnumUtils.HomeTabs.getTab(getArguments().getInt(ARG_PARAM1, 0));
        }

        initViews();

        setUpDetail();

        setListener();

    }

    //set swipe refresh loader visibility
    private void showNoResult(boolean isShow) {
        if (isShow) {
            if(tvSwipeRefreshLayout!=null && contentSwipeRefreshLayout!=null) {
                tvSwipeRefreshLayout.setVisibility(View.VISIBLE);
                contentSwipeRefreshLayout.setVisibility(View.GONE);
            }
        } else {
            if(tvSwipeRefreshLayout!=null && contentSwipeRefreshLayout!=null) {
                tvSwipeRefreshLayout.setVisibility(View.GONE);
                contentSwipeRefreshLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    //setup swipe refresh loader color and set swipe refreshing listener
    private void initViews() {

        tvSwipeRefreshLayout.setColorSchemeResources(
                EnumUtils.HomeTabs.getTabColor(selectedTab.ordinal()),
                R.color.colorPrimary,
                EnumUtils.HomeTabs.getTabColor(selectedTab.ordinal()));

        contentSwipeRefreshLayout.setColorSchemeResources(
                EnumUtils.HomeTabs.getTabColor(selectedTab.ordinal()),
                R.color.colorPrimary,
                EnumUtils.HomeTabs.getTabColor(selectedTab.ordinal()));

        //refresh layout of recycler view...
        tvSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                totalRecords = 0;
                callGetStoresService(selectedTab.ordinal());
            }
        });

        //refresh layout of textview noResult...
        contentSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                totalRecords = 0;
                callGetStoresService(selectedTab.ordinal());
            }
        });
    }

    //check layout visibility and start swipe refresh loader
    private void startRefreshingLoader() {

        if (tvSwipeRefreshLayout.getVisibility() == View.VISIBLE) {
            //if noresult is shown then display start its refreshlayout
            tvSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if(tvSwipeRefreshLayout!=null) {
                        tvSwipeRefreshLayout.setRefreshing(true);
                    }
                }
            });

        } else {
            //if content is shown...
            contentSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if(contentSwipeRefreshLayout!=null) {
                        contentSwipeRefreshLayout.setRefreshing(true);
                    }
                }
            });
        }
    }

    //stop swipe refresh loader
    private void stopRefreshing() {
        if(tvSwipeRefreshLayout!=null && contentSwipeRefreshLayout!=null) {
            tvSwipeRefreshLayout.setRefreshing(false);
            contentSwipeRefreshLayout.setRefreshing(false);
        }
    }

    //set distance listener
    private void setListener() {
        tvDistance.setOnClickListener(this);
    }

    //setup labels
    private void setUpDetail() {
        switch (selectedTab) {
            case Food:
                tvPopular.setText(getString(R.string.popular_restaurant));
                break;
            case Grocery:
                tvPopular.setText(getString(R.string.popular_grocery));
                break;
            case Laundry:
                tvPopular.setText(getString(R.string.popular_laundry));
                break;
            case Pharmacy:
                tvPopular.setText(getString(R.string.popular_pharmacy));
                break;
            case Retail:
                tvPopular.setText(getString(R.string.popular_retail));
                break;
        }
    }

    //setup popular stores recycler
    private void setUpPopularRecyclerView(List<StoreBO> storeBOs) {


        if (mAdapter == null || recyclerView.getAdapter() == null) {

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new PopularStoreAdapter(storeBOs, context, recyclerView);

            mAdapter.setClickListener(new PopularStoreAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {
                    StoreBO storeBO = mAdapter.getItem(position);
                    gotoCategories(storeBO);
                }
            });

            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(storeBOs);
        }

    }

    //goto store sub categories
    private void gotoCategories(StoreBO storeBO) {

        switch (selectedTab) {
            case Food:
            case Retail:
            case Grocery:
                Activities.gotoNextFragment(context, StoreSubCategories.newInstance(storeBO));
                break;
            case Pharmacy:
                Activities.gotoNextFragment(context, PharmacyDetail.newInstance(storeBO));
                break;
            case Laundry:
                Activities.gotoNextFragment(context, LaundryStoreDetail.newInstance(storeBO));
                break;
        }

    }

    //setup nearBy stores recycler
    private void setUpNearByRecyclerView(List<StoreBO> storeBOs) {

        if (storeBOs != null && storeBOs.size() > 0) {
            tvNearby.setText(getString(R.string.near_you) + " (" + storeBOs.size() + ")");
            rvNearYou.setVisibility(View.VISIBLE);
            tvNoresultNearby.setVisibility(View.GONE);
            if (nearByStoreAdapter == null || rvNearYou.getAdapter() == null) {
                rvNearYou.setLayoutManager(new LinearLayoutManager(context));
                rvNearYou.setItemAnimator(new DefaultItemAnimator());

                nearByStoreAdapter = new NearByStoreAdapter(storeBOs, context, rvNearYou);

                nearByStoreAdapter.setClickListener(new NearByStoreAdapter.ClickListeners() {
                    @Override
                    public void onRowClick(StoreBO storeBO) {
                        gotoCategories(storeBO);
                    }
                });

                rvNearYou.setAdapter(nearByStoreAdapter);
            } else {
                nearByStoreAdapter.setItems(storeBOs);
            }
        } else {
            rvNearYou.setVisibility(View.INVISIBLE);
            tvNoresultNearby.setVisibility(View.VISIBLE);
            tvNearby.setText(getString(R.string.near_you) + " (0)");
        }
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_distance:
                Activities.gotoNextFragment(context, FilterViewPager.newInstance(true));
                break;
        }
    }

    @OnClick(R.id.tv_add_item)
    public void tvAddItemClicked() {
        callGetStoresService(selectedTab.ordinal());
    }

    //get addressItem
    public AddressItem getSelectedAddressItem() {
        return selectedAddressItem;
    }

    //initialize addressItem
    public void setSelectedAddressItem(AddressItem selectedAddressItem) {
        this.selectedAddressItem = selectedAddressItem;
    }

    //this method is used to get stores from service and saved in Prefs...
    public void callGetStoresService(final int currentItem) {

        startRefreshingLoader();
        FilterItem filterItem = null;
        if (currentItem == 0) {
            filterItem = FiltersOP.getFilters(Keys.Filter_FOOD);
        } else {
            filterItem = FiltersOP.getFilters(Keys.Filter_OTHER);
        }

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;


        double latitude = 0;
        double longitude = 0;
        if (selectedAddressItem != null) {
            latitude = selectedAddressItem.getLatitude();
            longitude = selectedAddressItem.getLongitude();
        } else {
            if (!UserSharedPreference.readUserLat().isEmpty() && !UserSharedPreference.readUserLng().isEmpty()) {
                latitude = Double.parseDouble(UserSharedPreference.readUserLat());
                longitude = Double.parseDouble(UserSharedPreference.readUserLng());
            }
        }
        if (filterItem != null) {
            serviceParams.put(Keys.SortBy, filterItem.getSortBy());
            serviceParams.put(Keys.Rating, filterItem.getRating());
            serviceParams.put(Keys.MinDeliveryTime, filterItem.getMinDeliveryTime());
            serviceParams.put(Keys.PriceRanges, filterItem.getPriceRanges());
            serviceParams.put(Keys.MinDeliveryCharges, filterItem.getMinDeliveryCharges());
            serviceParams.put(Keys.IsSpecial, filterItem.isSpecial());
            serviceParams.put(Keys.IsFreeDelivery, filterItem.isFreeDelivery());
            serviceParams.put(Keys.IsNewRestaurants, filterItem.isNewRestaurants());
            serviceParams.put(Keys.CategoryName, context.getResources().getString(EnumUtils.HomeTabs.getName(currentItem)));
            serviceParams.put(Keys.latitude, latitude);
            serviceParams.put(Keys.longitude, longitude);
            serviceParams.put(Keys.Cuisines, filterItem.getSortBy());
        } else {
            serviceParams.put(Keys.Category_id, selectedTab.ordinal());
            serviceParams.put(Keys.Lat, latitude);
            serviceParams.put(Keys.Lng, longitude);
        }

        EnumUtils.ServiceName serviceName;
        if (filterItem == null) {
            serviceName = EnumUtils.ServiceName.GetStoresByCategories;
        } else {
            serviceName = EnumUtils.ServiceName.FilterStore;
        }
        new WebServicesVolleyTask(context, false, "",
                serviceName,
                EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    stopRefreshing();
                    if (taskItem.isError()) {
                        //show alert only on the selected tab...
                        showNoResult(true);

                        Log.i(TAG, "onTaskComplete: currentItem " + currentItem);
                        Log.i(TAG, "onTaskComplete: selectedTab " + selectedTab.ordinal());

                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                LogUtils.e(TAG, taskItem.getResponse());
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                JSONArray popularStores = jsonObject.getJSONArray("PopularStores");

                                JSONArray nearByStores = jsonObject.getJSONArray("NearByStores");
                                Gson gson = new Gson();

                                if (popularStores.length() > 0 || nearByStores.length() > 0) {
                                    showNoResult(false);

                                    Type listType = new TypeToken<List<StoreBO>>() {
                                    }.getType();
                                    List<StoreBO> popularStoresBO = (List<StoreBO>) gson.fromJson(popularStores.toString(),
                                            listType);
                                    if (popularStoresBO.size() == 0) {
                                        llPopular.setVisibility(View.GONE);
                                    } else {
                                        llPopular.setVisibility(View.VISIBLE);
                                        setUpPopularRecyclerView(popularStoresBO);
                                    }

                                    List<StoreBO> nearbyStoresBO = (List<StoreBO>) gson.fromJson(nearByStores.toString(),
                                            listType);
                                    setUpNearByRecyclerView(nearbyStoresBO);
                                } else {
                                    showNoResult(true);
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }//end of GetStores method
}//main