package app.com.dunkeydelivery.modules.home.tabs.alcohol.fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.adapters.AlcoholMainAdapter;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.AlcoholStoreBO;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.items.Categories;
import app.com.dunkeydelivery.modules.home.tabs.alcohol.pager.AllProductsViewPager;
import app.com.dunkeydelivery.modules.search.items.AddressItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.FiltersOP;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AlcoholMain extends ToolbarFragment {

    private Context context;
    public String storeIds;
    private Unbinder unbinder;
    private ArrayList<AlcoholStoreBO> alcoholStoreBOs;

    @BindView(R.id.tv_add_item)
    CustomTextView tv_add_item;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private AddressItem selectedAddressItem;

    public static AlcoholMain newInstance() {
        Bundle args = new Bundle();
        AlcoholMain fragment = new AlcoholMain();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alcohol_main,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                callGetAlcoholDetailsApi();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                callGetAlcoholDetailsApi();
            }
        });

        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetAlcoholDetailsApi();
            }
        });
    }

    //get stores on 'Refresh store' click
    @OnClick(R.id.tv_add_item)
    public void tvAddItemClicked() {
        tvSwipeRefresh.setRefreshing(true);
        callGetAlcoholDetailsApi();
    }

    //hand swipe refresh layout
    private void stopRefreshing(final boolean showLoader) {
        if(tvSwipeRefresh!=null && swipeRefreshLayout!=null) {
            tvSwipeRefresh.setRefreshing(showLoader);
            swipeRefreshLayout.setRefreshing(showLoader);
        }
    }

    //show or hide swipeRefreshLayouts
    private void showNoResult(boolean check) {
        if (!check) {
            if(tvSwipeRefresh!=null && swipeRefreshLayout!=null) {
                tvSwipeRefresh.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if(tvSwipeRefresh!=null && swipeRefreshLayout!=null) {
                tvSwipeRefresh.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }
        }
    }

    //setup stores from api response
    private void setUpStores() {
            if (alcoholStoreBOs != null && alcoholStoreBOs.size() > 0) {
                showNoResult(false);
                for (final AlcoholStoreBO alcoholStoreBO : alcoholStoreBOs) {
                    try {
                        if (alcoholStoreBO != null) {
                            View storeView = LayoutInflater.from(context).inflate(R.layout.layout_alcohol_header, null, false);

                            final TextView tvTitle = (TextView) storeView.findViewById(R.id.tv_title);
                            TextView tvMinOrderPrice = (TextView) storeView.findViewById(R.id.tv_min_delivery);
                            TextView tvDeliveryTime = (TextView) storeView.findViewById(R.id.tv_delivery_time);
                            TextView tvDeliveryCharges = (TextView) storeView.findViewById(R.id.tv_delivery_charges);
                            tvTitle.setText(alcoholStoreBO.getBusinessName());
                            tvMinOrderPrice.setText(Html.fromHtml("<font color='#000000' size='14'>Min</font>" + " $" + Double.parseDouble(alcoholStoreBO.getMinOrderPrice())));
                            if (Integer.parseInt(alcoholStoreBO.getMinDeliveryCharges()) != 0) {
                                tvDeliveryCharges.setText(Html.fromHtml("<font color='#000000' size='14'>Delivery Fee</font>" + " $" + Double.parseDouble(alcoholStoreBO.getMinDeliveryCharges())));
                                tvDeliveryCharges.setTextColor(getResources().getColor(R.color.grey4));
                            }
                            tvDeliveryTime.setText("" + alcoholStoreBO.getMinDeliveryTime() + " min");
                            tvTitle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //goto change store...
                                    Activities.gotoNextFragment(context, StoreInfo.newInstance(alcoholStoreBOs));
                                }
                            });

                            if (alcoholStoreBO.getCategories() != null && alcoholStoreBO.getCategories().size() > 0) {
                                llContent.addView(storeView);
                                //get categories and add..

                                setUpCategories(alcoholStoreBO);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            } else {
                showNoResult(true);
            }
    }

    //setup categories in stores
    private void setUpCategories(final AlcoholStoreBO alcoholStoreBO) {

        for (final Categories categoryBO : alcoholStoreBO.getCategories()) {
            try {
                final View contentView = LayoutInflater.from(context).inflate(R.layout.layout_alcohol_content, null, false);

                TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
                TextView tvSeeAll = (TextView) contentView.findViewById(R.id.tv_see_all);
                tvTitle.setText(categoryBO.getName());

                if(categoryBO.getName().trim().equals("Wine"))
                {
                    tvSeeAll.setTag("0");
                }
                else if(categoryBO.getName().trim().equals("Liquor"))
                {
                    tvSeeAll.setTag("1");
                }
                else if(categoryBO.getName().trim().equals("Beer"))
                {
                    tvSeeAll.setTag("2");
                }

                tvSeeAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activities.gotoNextFragment(context, AllProductsViewPager.newInstance(alcoholStoreBO, categoryBO.getParentCategoryId(), categoryBO.getId(), alcoholStoreBO.getLatitude(), alcoholStoreBO.getLongitude(),(String) v.getTag(),categoryBO.getName()));
                    }
                });
                //setup products...
                List<ProductBO> products = categoryBO.getProducts();
                if (products != null && products.size() > 0) {
                    setUpProducts(products, contentView);
                } else {
                    TextView tvNoResult = (TextView) contentView.findViewById(R.id.tv_noresult);
                    tvSeeAll.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.VISIBLE);
                }

                llContent.addView(contentView);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //setup products in store categories
    private void setUpProducts(List<ProductBO> products, View contentView) {

        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final AlcoholMainAdapter mAdapter = new AlcoholMainAdapter(products, context, false);

        mAdapter.setClickListener(new AlcoholMainAdapter.ClickListeners() {
            @Override
            public void onRowClick(int position) {
                Activities.gotoNextFragment(context, ProductDetail.newInstance(mAdapter.getItem(position), false));
            }
        });
        recyclerView.setAdapter(mAdapter);
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
    public void refreshToolbar() {
    }



    @Override
    public void onResume() {
        super.onResume();
        SharedPref.setCheckForFilterSizes(2);
    }

    //get alcohol stores with filters and without filters
    public void callGetAlcoholDetailsApi()
    {
        stopRefreshing(true);
        final HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);

        FilterItem filterItem = FiltersOP.getFilters(Keys.Filter_ALCOHOL);
        if (filterItem == null) {
            serviceParams.put(Keys.Page, 0);
            serviceParams.put(Keys.Items, 2);

            if (this.storeIds != null) {
                serviceParams.put(Keys.Store_Ids, this.storeIds);
            }
        } else {
            if (this.storeIds != null) {
                serviceParams.put(Keys.Store_Ids, this.storeIds);
            }
            serviceParams.put(Keys.SortBy, filterItem.getSortBy());
            serviceParams.put(Keys.Country, filterItem.getCountry());
            serviceParams.put(Keys.Price, filterItem.getPrice());
            serviceParams.put(Keys.Size, filterItem.getSize());
            serviceParams.put(Keys.Page, 0);
            serviceParams.put(Keys.Items, 2);
        }

        if (selectedAddressItem != null) {
            serviceParams.put(Keys.latitude, selectedAddressItem.getLatitude());
            serviceParams.put(Keys.longitude, selectedAddressItem.getLongitude());
        } else {
            if (!UserSharedPreference.readUserLat().isEmpty() && !UserSharedPreference.readUserLng().isEmpty()) {
                serviceParams.put(Keys.latitude, Double.parseDouble(UserSharedPreference.readUserLat()));
                serviceParams.put(Keys.longitude, Double.parseDouble(UserSharedPreference.readUserLng()));
            } else {
                serviceParams.put(Keys.latitude, -33.8688197);
                serviceParams.put(Keys.longitude, 151.2092955);
            }
        }

        final EnumUtils.ServiceName serviceName;

        if (filterItem != null) {
            swipeRefreshLayout.setRefreshing(true);
            serviceName = EnumUtils.ServiceName.AlcoholFilterStore;
        } else {
            serviceName = EnumUtils.ServiceName.AlcoholHomeScreen;
        }

        new WebServicesVolleyTask(context, false, "Loading Alcohol",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        stopRefreshing(false);
                        showNoResult(true);
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
                                if(serviceName == EnumUtils.ServiceName.AlcoholHomeScreen) {
                                    UserSharedPreference.saveFilterProductSizes(jsonObject.getJSONArray("FilterProductSizes").toString());
                                }
                                Type typeToken = new TypeToken<List<AlcoholStoreBO>>() {
                                }.getType();
                                alcoholStoreBOs = gson.fromJson(jsonObject.getJSONArray("Stores").toString(), typeToken);
                                removeViews();
                                LogUtils.i("mess",serviceParams.toString());
                                setUpStores();
                            } else {
                                showNoResult(true);
                            }

                        } catch (Exception ex) {
                            LogUtils.i("mess ", "" + ex.toString());
                        }
                        // if response is successful then do something
                    }
                } else {
                    showNoResult(false);
                }
                stopRefreshing(false);

            }
        });
    }

    //remove all previous stores view before showing new stores
    private void removeViews() {
        llContent.removeAllViews();
    }

    //set addressItem from fragment home for location
    public void setSelectedAddressItem(AddressItem addressItem) {
        selectedAddressItem = addressItem;
    }
}//main