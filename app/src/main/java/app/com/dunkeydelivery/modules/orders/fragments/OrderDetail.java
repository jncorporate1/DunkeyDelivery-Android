package app.com.dunkeydelivery.modules.orders.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.SettingBO;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.orders.adapters.OrderProductsAdapter;
import app.com.dunkeydelivery.modules.orders.items.OrderHistory;
import app.com.dunkeydelivery.modules.orders.items.StoreOrder;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrderDetail extends ToolbarFragment {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    public static String ARG_PARAM1 = "orderHistory";
    public static String ARG_PARAM2 = "storeOrder";
    public static String ARG_PARAM3 = "ids";
    private Integer totalRecords;
    Unbinder unbinder;

    private OrderHistory orderHistory;
    private StoreOrder storeOrder;

    @BindView(R.id.nsvOrderDetail)
    NestedScrollView nsvOrderDetail;

    @BindView(R.id.tv_order_date)
    CustomTextView tv_order_date;

    @BindView(R.id.tv_order_date_value)
    CustomTextView tv_order_date_value;

    @BindView(R.id.tv_subtotal)
    CustomTextView tv_subtotal;

    @BindView(R.id.tvTax)
    CustomTextView tvTax;

    @BindView(R.id.tvTip)
    CustomTextView tvTip;

    @BindView(R.id.tv_delivery_fee)
    CustomTextView tv_delivery_fee;

    @BindView(R.id.tv_total)
    CustomTextView tv_total;

    @BindView(R.id.tvTipLabel)
    CustomTextView tvTipLabel;

    @BindView(R.id.tv_address)
    CustomTextView tv_address;

    @BindView(R.id.recyclerViewOrderProducts)
    RecyclerView recyclerViewOrderProducts;

    public static OrderDetail newInstance(OrderHistory orderHistory, StoreOrder storeOrder) {

        Bundle args = new Bundle();
        OrderDetail fragment = new OrderDetail();
        args.putParcelable(ARG_PARAM1, orderHistory);
        args.putParcelable(ARG_PARAM2, storeOrder);
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderDetail newInstance(String orderIdAndStoreId) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM3, orderIdAndStoreId);
        OrderDetail fragment = new OrderDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order_detail, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            orderHistory = getArguments().getParcelable(ARG_PARAM1);
            storeOrder = getArguments().getParcelable(ARG_PARAM2);

            if (orderHistory != null && storeOrder != null) {
                try {
                    setData();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } else if (getArguments().getString(ARG_PARAM3) != null) {
                String ids = getArguments().getString(ARG_PARAM3);
                callGetOrderDetails(ids);
            }
        }
    }

    private void setData()
    {
        if(nsvOrderDetail!=null) {
            nsvOrderDetail.setNestedScrollingEnabled(false);
        }

        if(storeOrder.getOrderDeliveryTime()!=null) {
            String orderDate = DateTimeOp.oneFormatToAnother(storeOrder.getOrderDeliveryTime().trim().split(" ")[0],
                    Constants.dateFormat16, Constants.dateFormat3);
            tv_order_date.setText(orderDate);

            String orderFromTime = storeOrder.getOrderDeliveryTime().trim().split(" ")[1] + " " +
                    storeOrder.getOrderDeliveryTime().trim().split(" ")[2];

            String orderTime = orderFromTime;
            tv_order_date_value.setText(orderTime);
        }

        if(storeOrder.getSubtotal()!=null) {
            String subTotal = Constants.CURRENCY_SYMBOL + storeOrder.getSubtotal();
            tv_subtotal.setText(subTotal);
        }

        SettingBO settingBO = ObjectSharedPreference.getObject(SettingBO.class, Keys.SETTINGS);
        if (settingBO == null) {
            settingBO = new SettingBO();

            tvTipLabel.setText("Order Tip "+ settingBO.getTip() + "%");
        }

        if(orderHistory.getTipAmount()!=null) {
            String tipAmount = Constants.CURRENCY_SYMBOL + new DecimalFormat("##.##").format(orderHistory.getTipAmount());
            tvTip.setText(" : " + tipAmount);
        }

        if(orderHistory.getTotalTaxDeducted()!=null) {
            String taxAmount = Constants.CURRENCY_SYMBOL + orderHistory.getTotalTaxDeducted();
            tvTax.setText(" " + taxAmount);
        }

        if(storeOrder.getDeliveryFee()!=null) {
            String deliveryFee = Constants.CURRENCY_SYMBOL + storeOrder.getDeliveryFee();
            tv_delivery_fee.setText(deliveryFee);
        }

        if(orderHistory.getTotal()!=null) {
            String total = Constants.CURRENCY_SYMBOL + orderHistory.getTotal();
            tv_total.setText(total);
        }

        if(orderHistory.getDeliveryDetails_Address()!=null) {
            tv_address.setText(orderHistory.getDeliveryDetails_Address());
        }

        if(recyclerViewOrderProducts!=null && storeOrder.getOrderItems()!=null && storeOrder.getOrderItems().size()>0) {
            recyclerViewOrderProducts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            OrderProductsAdapter orderProductsAdapter = new OrderProductsAdapter(context, storeOrder.getOrderItems(), recyclerViewOrderProducts);
            recyclerViewOrderProducts.setAdapter(orderProductsAdapter);
        }
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
        Log.i("", "refreshToolbar: ");
        Activities.hideBottomNavigation(context, false);
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.order_detail),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    private void stopSwipeLoader() {
    }

    private void callGetOrderDetails(String ids) {

        HashMap<String, Object> serviceParams = new HashMap<>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        serviceParams.put(Keys.USER_ID_2, UserSharedPreference.readUserBO().getId());
        serviceParams.put(Keys.OrderId, ids.split(",")[0]);
        serviceParams.put(Keys.StoreOrder_Id, ids.split(",")[1]);
        serviceParams.put(Keys.SignInType, EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Other));
        serviceParams.put(Keys.IS_CURRENT_ORDER, true);
        serviceParams.put(Keys.PageSize, 1);
        serviceParams.put(Keys.PAGE_NO, 0);

        new WebServicesVolleyTask(context, false, "",
                EnumUtils.ServiceName.GetOrdersHistoryMobile, EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.GetOrdersHistoryByIdMobile),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                stopSwipeLoader();
                if (taskItem != null) {
                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
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
                                    orderHistory = orderHistoryList.get(0);
                                    storeOrder = orderHistory.getStoreOrders().get(0);
                                    setData();
                                }
                            }

                        } catch (Exception ex) {
//                            showNoResult(true);
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });

    }

}