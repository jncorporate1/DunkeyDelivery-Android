package app.com.dunkeydelivery.modules.cart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.items.SettingBO;
import app.com.dunkeydelivery.modules.cart.fragments.items.CheckOutSummary;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.orders.items.OrderHistory;
import app.com.dunkeydelivery.modules.orders.items.StoreOrder;
import app.com.dunkeydelivery.utils.CartOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ConfirmOrder extends ToolbarFragment implements View.OnClickListener {
    private static final String ARGS_PARAM_1 = "args_params_1";
    private static final String ARGS_PARAM_2 = "args_params_2";
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    Unbinder unbinder;

    @BindView(R.id.btn_order)
    Button btnOrder;

    @BindView(R.id.btn_continue)
    Button btnContinue;


    @BindView(R.id.tv_order_id)
    TextView tv_order_id;

    @BindView(R.id.tv_order_date)
    TextView tv_order_date;

    @BindView(R.id.tv_delivery_time)
    TextView tv_delivery_time;

    @BindView(R.id.tv_payment_type)
    TextView tv_payment_type;

    @BindView(R.id.tv_tip)
    TextView tv_tip;

    @BindView(R.id.tv_tax)
    TextView tv_tax;
    @BindView(R.id.tv_tip_label)
    TextView tv_tipLabel;

//    @BindView(R.id.tv_frequency)
//    TextView tv_frequency;

    @BindView(R.id.customTextView)
    TextView customTextView;

    @BindView(R.id.tv_additional_notes)
    TextView tv_additional_notes;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_subtotal)
    TextView tv_subtotal;

//    @BindView(R.id.tv_service_fee)
//    TextView tv_service_fee;

    @BindView(R.id.tv_delivery_fee)
    TextView tv_delivery_fee;

    @BindView(R.id.tv_total)
    TextView tv_total;

    private OrderHistory orderHistory;
    private CheckOutSummary checkOutSummary;

    public static ConfirmOrder newInstance(OrderHistory orderHistory, CheckOutSummary checkOutSummary) {
        Bundle args = new Bundle();
        ConfirmOrder fragment = new ConfirmOrder();
        args.putParcelable(ARGS_PARAM_1, orderHistory);
        args.putSerializable(ARGS_PARAM_2, checkOutSummary);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirm_order,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            orderHistory = getArguments().getParcelable(ARGS_PARAM_1);
            checkOutSummary = (CheckOutSummary) getArguments().getSerializable(ARGS_PARAM_2);
        }
        // Initialize all views
        setUpData();
        setListeners();

    }

    private void setUpData() {
        if (orderHistory != null) {
            tv_order_id.setText(orderHistory.getId() + "");

            tv_order_date.setText(DateTimeOp.oneFormatToAnother(orderHistory.getOrderDateTime().trim().split(" ")[0],
                    Constants.dateFormat16, Constants.dateFormat3));
//            tv_delivery_time.setText(DateTimeOp.oneFormatToAnother(
//                    orderHistory.getOrderDateTime().trim().split(" ")[1],
//                    Constants.dateFormat10,
//                    Constants.dateFormat4));
            tv_delivery_time.setText(orderHistory.getOrderDateTime().trim().split(" ")[1] +  " "
                    + orderHistory.getOrderDateTime().trim().split(" ")[2]);

            //todo place proper info after cordination
            tv_payment_type.setText(orderHistory.getPaymentMethodString());
            //todo add frequency information in the Oderhistory
//            tv_frequency.setText(orderHistory.getFrequencyString());   //getFrequencey
            tv_additional_notes.setText(orderHistory.getDeliveryDetails_AddtionalNote() + "");
            tv_address.setText(orderHistory.getDeliveryDetails_Address() + "");
            int value=0;
            for (StoreOrder storeOrder:orderHistory.getStoreOrders())
            {
                value=value+storeOrder.getSubtotal();
            }
            tv_subtotal.setText(Constants.CURRENCY_SYMBOL + value);
//            tv_service_fee.setText("$"+orderHistory.getServiceFee() + "");
            tv_delivery_fee.setText(Constants.CURRENCY_SYMBOL + checkOutSummary.getDeliveryFee() + "");
            tv_total.setText(Constants.CURRENCY_SYMBOL + new DecimalFormat("##.##").format(orderHistory.getTotal()));

        } else {
            tv_order_id.setText("");
            tv_order_date.setText("");
            tv_delivery_time.setText("");
            tv_payment_type.setText("");
//            tv_frequency.setText("");
            customTextView.setText("");
            tv_additional_notes.setText("");
            tv_address.setText("");
            tv_subtotal.setText("");
//            tv_service_fee.setText("");
            tv_delivery_fee.setText("");
            tv_total.setText("");
        }
        if (checkOutSummary != null) {
            tv_tip.setText(Constants.CURRENCY_SYMBOL + checkOutSummary.getTip() + "");
            tv_tax.setText(Constants.CURRENCY_SYMBOL+checkOutSummary.getTax() + "");
        } else {
            tv_tip.setText("0");
            tv_tax.setText("0");
        }
        SettingBO settingBO = ObjectSharedPreference.getObject(SettingBO.class, Keys.SETTINGS);
        if (settingBO == null) {
            settingBO = new SettingBO();
        }
        tv_tipLabel.setText("Tip " + settingBO.getTip() + "%");
        CartOP.clearCart();
    }

    private void setListeners() {
        btnContinue.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.thank_you),
                null, ToolbarOp.Theme.Dark, 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gotoMainFragment();
                    }
                }, null);
    }

    private void gotoMainFragment() {
        Activities.removeAllFragments(context);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_continue:
                Activities.setSelectedTab(context, 0);
                break;
            case R.id.btn_order:
                Activities.setSelectedTab(context, 1);
                break;
        }
    }
}