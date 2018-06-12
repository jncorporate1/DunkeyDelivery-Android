package app.com.dunkeydelivery.modules.home.tabs.food.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hedgehog.ratingbar.RatingBar;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.fragments.ChangePassword;
import app.com.dunkeydelivery.modules.home.items.RatingsItem;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.adapters.ReviewListAdapter;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.items.ReviewBO;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.StoreUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoreReviews extends Fragment implements View.OnClickListener {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    Unbinder unbinder;

    private StoreBO storeBO;
    private int totalRecords = 0;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int startIndex = 0;
    private ReviewListAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

//    @BindView(R.id.rv_swipe_refresh)
//    SwipeRefreshLayout tvSwipeRefresh;

    @BindView(R.id.tv_noresult)
    TextView tvNoResult;
    @BindView(R.id.tv_description)
    TextView tvDescription;

    @BindView(R.id.ll_ratings)
    LinearLayout ll_ratings;

    @BindView(R.id.ratingbar)
    RatingBar ratingbar;

    private boolean isForAlcoholStore = false;

    public static StoreReviews newInstance(boolean isForAlcoholStore, StoreBO storeBO) {
        Bundle args = new Bundle();
        StoreReviews fragment = new StoreReviews();
        args.putBoolean(StoreViewPager.ARG_PARAM2, isForAlcoholStore);
        args.putParcelable(StoreViewPager.ARG_PARAM3, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store_reviews,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        if (getArguments() != null) {
            isForAlcoholStore = getArguments().getBoolean(StoreViewPager.ARG_PARAM2);
            storeBO = getArguments().getParcelable(StoreViewPager.ARG_PARAM3);
        }

        initViews();

//        setUpRecycler(new ArrayList<ReviewBO>());

    }

    private void initViews() {
        //Initialize main content Linear layout.
        rvSwipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimaryDark);

//        tvSwipeRefresh.setColorSchemeResources(
//                R.color.colorPrimary,
//                R.color.colorAccent,
//                R.color.colorPrimaryDark);

        //refresh layout of recycler view...
        rvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateSwipeLoader(true);

                if (storeBO == null) {
                    updateSwipeLoader(false);
                } else {

                    callStoreInformationApi(storeBO.getId());
                }
                startIndex = 0;
                totalRecords = 0;
                //updateSwipeLoader(false);
                //callStoreInformationApi(storeBO.getId());
            }
        });

        //refresh layout of textview noResult...
//        tvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                startIndex = 0;
//                totalRecords = 0;
//                stopSwipeLoader();
////                callStreamsService(startIndex, maxItems, false);
//            }
//        });

        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                updateSwipeLoader(true);

                if (storeBO == null) {
                    updateSwipeLoader(false);
                } else {
                    LogUtils.i("mess", "store reviews    " + storeBO.getId());
                    callStoreInformationApi(storeBO.getId());
                }
            }
        });
    }

    private void callStoreInformationApi(int storeId) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);
        serviceParams.put(Keys.id, storeId);


        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetStoreByIdMobile;

        new WebServicesVolleyTask(context, false, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
//                                Type typeToken = new TypeToken<StoreBO>() {
//                                }.getType();
                                StoreBO storeBO = gson.fromJson(jsonObject.toString(), StoreBO.class);
                                Log.e(TAG, (storeBO == null) ? "false" : "True");
                                setUpData(storeBO);
//                                setUpRecycler(storeBO.storeReviews);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                    updateSwipeLoader(false);

                }
            }
        });
    }

    private void setUpData(StoreBO storeBO) {
        ll_ratings.removeAllViews();
        ratingbar.setmClickable(false);
        ratingbar.setStar(storeBO.getAverageRating());
        tvDescription.setText(Html.fromHtml("Based on <font color='#000000'>"
                + storeBO.getRatingsItem().getTotalStar() +
                " ratings</font> and <font color='#000000'>" +
                storeBO.getStoreReviews().size() + " reviews</font>"));


        setUpUserRatings(storeBO.getRatingsItem());
        setUpRecycler(storeBO.storeReviews);

        if (getParentFragment() != null && getParentFragment() instanceof StoreViewPager) {
            ((StoreViewPager) getParentFragment()).setStore(storeBO);
        }
    }

    private void setUpUserRatings(RatingsItem rating) {
        StoreUtils.addStoreRating(context, rating, ll_ratings);
    }

    private void setUpRecycler(List<ReviewBO> itemBOs) {
/*
        if (isForAlcoholStore) {
//            itemBOs.add(new ReviewBO("John Snow", "22 Orders"));
//            itemBOs.add(new ReviewBO("Micheal", "20 Orders"));
//            itemBOs.add(new ReviewBO("Julia", "3 Orders"));
//            itemBOs.add(new ReviewBO("John Smith", "3 Orders"));
//            itemBOs.add(new ReviewBO("Micheal", "3 Orders"));
//            itemBOs.add(new ReviewBO("Julia", "3 Orders"));
//      } else {
//            itemBOs.add(new ReviewBO("John Snow", null));
//            itemBOs.add(new ReviewBO("Micheal", null));
//            itemBOs.add(new ReviewBO("Julia", null));
//            itemBOs.add(new ReviewBO("John Smith", null));
//            itemBOs.add(new ReviewBO("Micheal", null));
//            itemBOs.add(new ReviewBO("Julia", null));
//      }
        */


        if (mAdapter == null || recyclerView.getAdapter() == null) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setAutoMeasureEnabled(true);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new ReviewListAdapter(itemBOs, isForAlcoholStore, context, recyclerView);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
//                    streamBOs = mAdapter.getItems();
                    if (mAdapter.getItems().size() >= maxItems) {
                        mAdapter.addLoadingItem();
                    }
                }
            });

            mAdapter.setClickListener(new ReviewListAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {
                    ReviewBO itemBO = mAdapter.getItem(position);
                }
            });

            recyclerView.setAdapter(mAdapter);

        } else {
            if (mAdapter.getItemCount() > 0) {
                mAdapter.clearItems();
            }
            mAdapter.addItems(itemBOs);
            mAdapter.setLoaded();
        }

        if (mAdapter.getItemCount() == 0) {
            tvNoResult.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    private void updateSwipeLoader(boolean isRefreshing) {
        if (rvSwipeRefresh != null)
            rvSwipeRefresh.setRefreshing(isRefreshing);
//        tvSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_change_paswd:
                Activities.gotoNextFragment(context, ChangePassword.newInstance());
                break;
        }
    }
}