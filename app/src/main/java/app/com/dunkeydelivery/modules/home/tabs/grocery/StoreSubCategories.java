package app.com.dunkeydelivery.modules.home.tabs.grocery;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.hedgehog.ratingbar.RatingBar;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.FragmentProductList;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.StoreViewPager;
import app.com.dunkeydelivery.modules.home.tabs.grocery.adapters.CategoriesAdapter;
import app.com.dunkeydelivery.modules.home.items.SubCategoryBO;
import app.com.dunkeydelivery.modules.search.Search;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.StoreUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.MenuItemSearch;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoreSubCategories extends ToolbarFragment implements View.OnClickListener {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden = false;
    private static String ARG_PARAM1 = "storeBO";
    private int totalRecords = 0;
    private int pageIndex = 0;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int startIndex = 0;
    private StoreBO storeBO;
    Unbinder unbinder;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;
    @BindView(R.id.tv_subtitle1)
    TextView tvMinOrder;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.ratingbar)
    RatingBar ratingBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.ll_tv_category_line)
    LinearLayout llTvCategoryLine;

    @BindView(R.id.ib_search)
    ImageButton ibSearch;

    @BindView(R.id.ib_review)
    ImageButton ibReview;

    @BindView(R.id.ib_info)
    ImageButton ibInfo;

    private CategoriesAdapter mAdapter;

    public static StoreSubCategories newInstance(StoreBO storeBO) {
        Bundle args = new Bundle();
        StoreSubCategories fragment = new StoreSubCategories();
        args.putParcelable(ARG_PARAM1, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_detail,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            storeBO = getArguments().getParcelable(ARG_PARAM1);
        }

        // Initialize all views
        ratingBar.setmClickable(false);

        initViews();

        setUpStoreDetail();

        setListeners();

        callGetStoresCategoriesService(false);

    }

    private void setUpStoreDetail() {
        try {
            tvTitle.setText(storeBO.getBusinessName());
            tvRate.setText(storeBO.getAverageRating() + "");
            ratingBar.setStar(storeBO.getAverageRating());
            tvMinOrder.setText(getString(R.string.min_order) + "  " + storeBO.getMinOrderPrice());

            if (!storeBO.getDistance().isEmpty())
                tvDistance.setText(storeBO.getDistance() + " m");
            else
                tvDistance.setVisibility(View.GONE);

            if (!storeBO.getMinDeliveryTime().isEmpty())
                tvTime.setText(storeBO.getMinDeliveryTime());
            else
                tvTime.setVisibility(View.GONE);
            //TODO: Set delivery fee..
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //add tags..
        StoreUtils.addStoreTags(context, flowLayout, storeBO.getStoreTags());
    }


    private void setListeners() {
        ibInfo.setOnClickListener(this);
        ibReview.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
    }


    private void initViews() {
        //Initialize main content Linear layout.
        rvSwipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

        tvSwipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

        //refresh layout of recycler view...
        rvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                totalRecords = 0;
                pageIndex = 0;
                callGetStoresCategoriesService(false);
            }
        });

        //refresh layout of textview noResult...
        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                totalRecords = 0;
                pageIndex = 0;
                callGetStoresCategoriesService(false);
            }
        });
    }


    private void setUpRecyclerView(final List<SubCategoryBO> subCategoryBOs, boolean isFromLoadmore) {

        if (mAdapter == null || recyclerView.getAdapter() == null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new CategoriesAdapter(context, subCategoryBOs, recyclerView);

            mAdapter.setClickListener(new CategoriesAdapter.ClickListeners() {
                @Override
                public void onRowClick(SubCategoryBO subCategoryBO) {
//                    SubCategoryBO subCategoryBO = mAdapter.getItem(position);
                    Activities.gotoNextFragment(context, FragmentProductList.newInstance(subCategoryBO,storeBO));
                }

                @Override
                public void onSubItem(SubCategoryBO subCategoryBO) {
//                    SubCategoryBO subCategoryBO = mAdapter.getItem(position);
                    Activities.gotoNextFragment(context, FragmentProductList.newInstance(subCategoryBO,storeBO));
                }

            });

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (mAdapter.getItemCount() >= maxItems && mAdapter.getItemCount() < totalRecords) {
                        mAdapter.addLoadingItem();

                        pageIndex = pageIndex + 1;
                        callGetStoresCategoriesService(true);
                    }

                }
            });

            recyclerView.setAdapter(mAdapter);
        } else {
            if (isFromLoadmore) {
                mAdapter.setLoaded();
                mAdapter.addItems(subCategoryBOs);
            } else {
                mAdapter.setItems(subCategoryBOs);
            }

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        refreshToolbar();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }


    @Override
    public void refreshToolbar() {
        if (!isHidden) {
            Activities.hideBottomNavigation(context, false);
            MenuItemImgOrStr menuItemImgOrStr = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activities.gotoNextFragment(context, CartMain.newInstance());
                }
            });

            MenuItemSearch menuItemSearch = new MenuItemSearch(getString(R.string.search_your_product), R.drawable.ic_search_grey,
                    null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //On Search click...
                    Activities.gotoNextFragment(context, Search.newInstanceForSearch(true, storeBO));
                }
            }, null);

            ToolbarOp.refresh(getView(), getActivity(), "",
                    null, ToolbarOp.Theme.Dark, 0, null, menuItemSearch, menuItemImgOrStr);
        }

    }


    private void stopSwipeLoader() {
        tvSwipeRefresh.setRefreshing(false);
        rvSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_search:
                Activities.gotoNextFragment(context, Search.newInstanceForSearch(true, storeBO));
                break;
            case R.id.ib_review:
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(false, false, storeBO));
                break;
            case R.id.ib_info:
                Activities.gotoNextFragment(context, StoreViewPager.newInstance(true, false, storeBO));
                break;
        }
    }

    private void showNoResult(boolean isShow) {
        if (isShow) {
            tvSwipeRefresh.setVisibility(View.VISIBLE);
            llTvCategoryLine.setVisibility(View.GONE);
        } else {
            tvSwipeRefresh.setVisibility(View.GONE);
            llTvCategoryLine.setVisibility(View.VISIBLE);
        }
    }

    //this method is used to get stores from service and saved in Prefs...
    public void callGetStoresCategoriesService(final boolean isFromLoadmore) {

        if (!isFromLoadmore)
            startRefreshingLoader();

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;

        serviceParams.put(Keys.Store_id, storeBO.getId());
        serviceParams.put(Keys.Page, pageIndex);
        serviceParams.put(Keys.Items, maxItems);

        new WebServicesVolleyTask(context, false, "",
                EnumUtils.ServiceName.StoreCategories,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.StoreCategories),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {

                    if (mAdapter != null) {
                        //set isLoading boolean to false....
                        mAdapter.setLoaded();
                        if (isFromLoadmore)
                            mAdapter.removeLoadingItem();
                    }

                    stopSwipeLoader();
                    if (taskItem.isError()) {
                        //show alert only on the selected tab...
                        showNoResult(true);
//                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();

                                JSONArray categories = jsonObject.getJSONArray("ParentCategories");
                                totalRecords = jsonObject.getInt("TotalRecords");
                                if (categories.length() > 0) {
                                    showNoResult(false);

                                    Type listType = new TypeToken<List<SubCategoryBO>>() {
                                    }.getType();
                                    List<SubCategoryBO> subCategoryBOs = (List<SubCategoryBO>) gson.fromJson(categories.toString(),
                                            listType);

//                                    JSONArray jsonArray=jsonObject.getJSONArray("DeliveryTypes");
//
//                                    UserSharedPreference.saveDeliveryTypes(jsonArray.toString());

                                    setUpRecyclerView(subCategoryBOs, isFromLoadmore);

                                } else {
                                    if (!isFromLoadmore)
                                        showNoResult(true);
                                }
                            }

                        } catch (Exception ex) {
                            showNoResult(true);
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }//end of GetCategories method

    private void startRefreshingLoader() {
        if (tvSwipeRefresh.getVisibility() == View.VISIBLE) {
            //if noresult is shown then display start its refreshlayout
            tvSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    tvSwipeRefresh.setRefreshing(true);
                }
            });

        } else {
            //if content is shown...
            rvSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    rvSwipeRefresh.setRefreshing(true);
                }
            });
        }
    }
}