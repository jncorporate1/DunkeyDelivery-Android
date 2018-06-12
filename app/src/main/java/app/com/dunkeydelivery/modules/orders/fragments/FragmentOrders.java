package app.com.dunkeydelivery.modules.orders.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.items.UserBO;
import app.com.dunkeydelivery.modules.orders.adapters.OrdersAdapter;
import app.com.dunkeydelivery.modules.orders.items.OrderHistory;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentOrders extends ToolbarFragment {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden = false;
    private int totalRecords = 0;
    private int maxItems = Constants.MAX_ITEMS_TO_LOAD;
    private int startIndex = 0;
    Unbinder unbinder;

    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefreshLayout;

    @BindView(R.id.tv_swipe_refresh)
    SwipeRefreshLayout tvSwipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_noresult)
    TextView tvNoResult;

    private OrdersAdapter mAdapter;

    private UserBO userBO;

    private String EntityId;

    public static FragmentOrders newInstance() {

        Bundle args = new Bundle();
        FragmentOrders fragment = new FragmentOrders();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_recycleview, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Initialize all views

        if (getArguments() != null) {
            if (getArguments().getString("EntityId") != null) {
                this.EntityId = getArguments().getString("EntityId");
            }
        }

        userBO = UserSharedPreference.readUserBO();

        rvSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                callGetOrderHistory(false);
            }
        });

        tvSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex = 0;
                callGetOrderHistory(false);
            }
        });
    }


    private void callGetOrderHistory(final boolean isFromLoadMore) {

        HashMap<String, Object> serviceParams = new HashMap<>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        serviceParams.put(Keys.USER_ID_2, userBO.getId());
        serviceParams.put(Keys.SignInType, 0);//userBO.getRole());

        serviceParams.put(Keys.IS_CURRENT_ORDER, true);
        serviceParams.put(Keys.PAGE_SIZE, maxItems);
        serviceParams.put(Keys.PAGE_NO, startIndex);

        new WebServicesVolleyTask(context, false, "",
                EnumUtils.ServiceName.GetOrdersHistoryMobile, EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.GetOrdersHistoryMobile),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                stopSwipeLoader();
                if (taskItem != null) {

                    if (mAdapter != null) {
                        //set isLoading boolean to false....
                        mAdapter.setLoaded();
                        if (isFromLoadMore)
                            mAdapter.removeLoadingItem();
                    }

                    if (taskItem.isError()) {

                        if (!isFromLoadMore) {
                            AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                            showNoResult(true);
                        }
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());

                                totalRecords = jsonObject.optInt("Count");
                                JSONArray ordersJsonArray = jsonObject.getJSONArray("orders");
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<OrderHistory>>() {
                                }.getType();
                                List<OrderHistory> orderHistoryList = (List<OrderHistory>) gson.fromJson(ordersJsonArray.toString(),
                                        listType);
                                if (orderHistoryList.size() > 0) {
                                    showNoResult(false);
                                    setOrderHistoryRecycler(orderHistoryList, isFromLoadMore);
                                } else {
                                    if (startIndex == 0) {
                                        showNoResult(true);
                                    }
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

    }

    private void showNoResult(boolean isShow) {
        if (isShow)
            tvSwipeRefreshLayout.setVisibility(View.VISIBLE);
        else
            tvSwipeRefreshLayout.setVisibility(View.GONE);
    }


//    private List<OrderHistory> reverseOrders(List<OrderHistory> orderHistories)
//    {
//        Collections.reverse(orderHistories);
//       return orderHistories;
//    }


    private void setOrderHistoryRecycler(List<OrderHistory> orderHistories, boolean isFromLoadmore) {

//        List<OrderHistory> orderHistoryList=reverseOrders(orderHistories);

        if (mAdapter == null || recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            mAdapter = new OrdersAdapter(context, orderHistories, recyclerView, EntityId);
            recyclerView.setAdapter(mAdapter);

            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (mAdapter != null && mAdapter.getItems().size() >= maxItems && mAdapter.getItems().size() < totalRecords) {
                        mAdapter.addLoadingItem();
                        startIndex = startIndex + 1;
                        callGetOrderHistory(true);
                    }
                }
            });

            mAdapter.setClickListener(new OrdersAdapter.ClickListeners() {
                @Override
                public void onRowClick(int position) {

                }

                @Override
                public void onViewClick(int position) {
                    /*OrdersBO ordersBO = mAdapter.getItem(position);
                    Activities.gotoNextFragment(context, OrderDetail.newInstanceForSearch(ordersBO));*/
                }

                @Override
                public void onDeleteClick(int position) {
                    //showDeleteAlert(position);
                }
            });

        } else {
            if (isFromLoadmore) {
                mAdapter.addItems(orderHistories);
                mAdapter.setLoaded();
            } else {
                mAdapter.setItems(orderHistories);
            }
        }
    }

    private void showDeleteAlert(final int position) {

        AlertOP.showAlert(context, getString(R.string.delete_order), getString(R.string.content_delete),
                -1, -1, getString(R.string.yes), getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAdapter != null) {
                            mAdapter.removeItem(position);
                        }
                    }
                });
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
        rvSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                rvSwipeRefreshLayout.setRefreshing(true);
                callGetOrderHistory(false);
            }
        });
        if (!isHidden) {
            Activities.hideBottomNavigation(context, false);
            Log.i("", "refreshToolbar: ");
            ToolbarOp.refresh(getView(), getActivity(), getString(R.string.title_orders),
                    null, ToolbarOp.Theme.Dark, 0, null, null);
        }

    }


    private void stopSwipeLoader() {
        rvSwipeRefreshLayout.setRefreshing(false);
        tvSwipeRefreshLayout.setRefreshing(false);
    }


    public static FragmentOrders newInstance(String entityId) {

        Bundle args = new Bundle();
        args.putString("EntityId", entityId);
        FragmentOrders fragment = new FragmentOrders();
        fragment.setArguments(args);
        return fragment;
    }
}