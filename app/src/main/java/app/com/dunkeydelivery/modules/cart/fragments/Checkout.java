package app.com.dunkeydelivery.modules.cart.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.activities.MainActivity;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.SettingBO;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.items.UserBO;
import app.com.dunkeydelivery.modules.account.Items.Address;
import app.com.dunkeydelivery.modules.account.Items.CardItem;
import app.com.dunkeydelivery.modules.account.fragments.AddNewAddress;
import app.com.dunkeydelivery.modules.account.fragments.AddNewCreditCard;
import app.com.dunkeydelivery.modules.cart.fragments.items.CartStore;
import app.com.dunkeydelivery.modules.cart.fragments.items.CheckOutSummary;
import app.com.dunkeydelivery.modules.cart.fragments.items.CheckoutItem;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.orders.items.OrderHistory;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.CartOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomEditText;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Checkout extends ToolbarFragment implements View.OnClickListener {
    private Context context;
    private ProgressDialog verifyCreditMessageDialogue;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    private String params;
    private Address address;
    private CardItem cardItem;
    Unbinder unbinder;

    public static String ARG_PARAM1 = "checkOutItem";

    //payment method group...
    @BindView(R.id.rg_method)
    RadioGroup rgPaymentMethod;
    @BindView(R.id.rb_card)
    RadioButton rbCard;
    @BindView(R.id.rb_paypal)
    RadioButton rbPayPal;

    //frequency group...
    @BindView(R.id.rg_frequency)
    RadioGroup rgFrequency;
    @BindView(R.id.rb_one_time)
    RadioButton rbOneTime;
    @BindView(R.id.rb_weekly)
    RadioButton rbWeekly;
    @BindView(R.id.rb_monthly)
    RadioButton rbMonthly;

    @BindView(R.id.ll_delivery_information)
    LinearLayout llDeliveryInformation;
    @BindView(R.id.ll_delivery_info)
    LinearLayout llDeliveryInfo;
    @BindView(R.id.tv_delivery_info)
    TextView tvDeliveryInfo;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;

    @BindView(R.id.tv_add_creditcard)
    TextView tvAddCard;

    @BindView(R.id.tv_add_delivery_information)
    TextView tvAddDeliveryInformation;

    @BindView(R.id.ll_main_checkout)
    LinearLayout ll_main_checkout;

    @BindView(R.id.btn_place_order)
    Button btnPlaceOrder;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_delivery_fee)
    TextView tv_delivery_fee;
    @BindView(R.id.tv_tip_fee)
    TextView tv_tip_fee;
    @BindView(R.id.tv_tax_fee)
    TextView tv_tax_fee;
    @BindView(R.id.tv_subtotal)
    TextView tv_subtotal;

    @BindView(R.id.et_instructions)
    CustomEditText et_instructions;
    @BindView(R.id.tv_points)
    TextView tv_points;

    @BindView(R.id.tv_tip)
    TextView tv_tip;

    @BindView(R.id.tv_creditcardnumber)
    TextView tvCreditCardNumber;

    @BindView(R.id.tv_creditcardexpiry)
    TextView tvCreditCardExpiry;

    private CheckoutItem checkoutItem;
    private EnumUtils.CheckoutFrequency checkoutFrequency = EnumUtils.CheckoutFrequency.ONE_TIME;
    private SettingBO settingBO;
    private int paymentMethod = 0;

    public static Checkout newInstance(CheckoutItem checkoutItem) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, checkoutItem);
        Checkout fragment = new Checkout();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        initializeProgressDialogue();
        initViews(view);
        setListeners();

        if (getArguments() != null) {
            checkoutItem = getArguments().getParcelable(ARG_PARAM1);
            setUpData();
        } else
            showMainLayout(false);

    }//onViewCreated

    private void initializeProgressDialogue() {
        verifyCreditMessageDialogue = new ProgressDialog(context);
        verifyCreditMessageDialogue.setCanceledOnTouchOutside(false);
    }

    private void callOrderPlaceOutApi(final String id) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        serviceParams.put(Keys.USER_ID_2, UserSharedPreference.readUserBO().getId());
        serviceParams.put(Keys.StripeAccessToken, id);

        String additionalNotes = et_instructions.getText().toString().trim();

        if (!TextUtils.isEmpty(additionalNotes))
            serviceParams.put(Keys.ADDITIONAL_NOTE, additionalNotes);

        serviceParams.put(Keys.PAYMENT_METHOD_TYPE, paymentMethod);

        String address = tvDeliveryInfo.getText().toString().trim();
        serviceParams.put(Keys.DELIVERY_ADDRESS, address);

        List<JSONObject> jsonObjects = new ArrayList<>();
        List<JSONObject> schedulingJsonObject = new ArrayList<>();

        try {
            for (int i = 0; i < checkoutItem.getStoreArray().size(); i++) {

                CartStore cartStore = checkoutItem.getStoreArray().get(i);

                for (int j = 0; j < cartStore.getProducts().size(); j++) {

                    ProductBO productBO = cartStore.getProducts().get(j);

                    if (productBO.getQuantity() != -1) {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ItemId", productBO.getId());
                        jsonObject.put("ItemType", productBO.getItemType());
                        jsonObject.put("Qty", productBO.getQuantity());
                        jsonObject.put("StoreId", cartStore.getStoreId());
                        jsonObject.put("ProductSize", SharedPref.getProductSize());

                        jsonObjects.add(jsonObject);

                    }
                }
            }

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < jsonObjects.size(); i++) {
                jsonArray.put(jsonObjects.get(i));
            }
            jsonObject.put("CartItems", jsonArray);
            serviceParams.put(Keys.CART, jsonObject.toString());

            for (CartStore cartStore : CartOP.getCarts()) {
                JSONObject schedulingObject = new JSONObject();
                schedulingObject.put("Store_Id", cartStore.getStoreId());
                schedulingObject.put("Type_Id", cartStore.getType_Id());
                schedulingObject.put("OrderDateTime", cartStore.getOrderDateTime());
                schedulingObject.put("OrderDate", cartStore.getOrderDateTime());
                schedulingObject.put("MinDeliveryTime", cartStore.getMinDeliveryTime());

                schedulingJsonObject.add(schedulingObject);
            }

            JSONArray schedulingArray = new JSONArray();
            for (JSONObject object : schedulingJsonObject) {
                schedulingArray.put(object);
            }

            serviceParams.put(Keys.StoreDeliverytype, schedulingArray.toString());


            serviceParams.put(Keys.Frequency, EnumUtils.CheckoutFrequency.getPosition(checkoutFrequency));
            params = serviceParams.toString();
            Log.e(TAG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.InsertOrderMobile;

        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    } else {
                        try {
                            Gson gson = new Gson();


                            OrderHistory orderHistory = gson.fromJson(taskItem.getResponse(), OrderHistory.class);
                            Activities.gotoNextFragment(context, ConfirmOrder.newInstance(orderHistory, checkoutItem.getSummary()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    private void showMainLayout(boolean isVisible) {
        ll_main_checkout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setUpData() {
        settingBO = ObjectSharedPreference.getObject(SettingBO.class, Keys.SETTINGS);
        if (settingBO == null) {
            settingBO = new SettingBO();
        }
        tv_tip.setText("Tip " + settingBO.getTip() + "%");
        if (checkoutItem != null) {
            address = checkoutItem.getAddress();
            cardItem = checkoutItem.getCardItem();
            showMainLayout(true);
            setOrderSumamry(checkoutItem.getSummary());
            if (address != null) {
                tvDeliveryInfo.setText(address.getAddress() + ", " + address.getZipCode());
                tvAddDeliveryInformation.setVisibility(View.GONE);
            } else {
                tvAddDeliveryInformation.setVisibility(View.VISIBLE);
            }
            if (cardItem != null) {
                tvCreditCardNumber.setText("Ends With " + cardItem.getCardNumber().substring(cardItem.getCardNumber().length() - 4, cardItem.getCardNumber().length()));
                tvCreditCardExpiry.setText(cardItem.getExpiryDate());
                tvAddCard.setVisibility(View.GONE);
            } else {
                tvAddCard.setVisibility(View.VISIBLE);
            }
            addStoreView(checkoutItem.getStoreArray());
        } else {
            Activities.goBackFragment(context, 1);
        }
    }

    private void setOrderSumamry(CheckOutSummary summary) {
        tv_total.setText(Constants.CURRENCY_SYMBOL + new DecimalFormat("##.##").format(summary.getTotal()));
        tv_subtotal.setText(Constants.CURRENCY_SYMBOL + summary.getSubTotalWDF());
        tv_tax_fee.setText(Constants.CURRENCY_SYMBOL + summary.getTax());
        tv_tip_fee.setText(Constants.CURRENCY_SYMBOL + summary.getTip());
        tv_delivery_fee.setText(Constants.CURRENCY_SYMBOL + summary.getDeliveryFee());
    }

    private void setListeners() {
        setFrequencyListener();
        setPaymentMethodListener();
    }

    //set frequency listner...
    private void setFrequencyListener() {

        tvAddCard.setOnClickListener(this);
        tvAddDeliveryInformation.setOnClickListener(this);
        btnPlaceOrder.setOnClickListener(this);
        llDeliveryInfo.setOnClickListener(this);
        llDeliveryInformation.setOnClickListener(this);

        rgFrequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one_time:
                        //when home is selected...
                        checkoutFrequency = EnumUtils.CheckoutFrequency.ONE_TIME;
                        break;
                    case R.id.rb_weekly:
                        //when work is selected...
                        checkoutFrequency = EnumUtils.CheckoutFrequency.WEEKLY;
                        break;
                    case R.id.rb_monthly:
                        //when custom is selected...
                        checkoutFrequency = EnumUtils.CheckoutFrequency.MONTHLY;
                        break;
                }
            }
        });
    }

    //set frequency listner...
    private void setPaymentMethodListener() {

        rgPaymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_card:
                        paymentMethod = 0;
                        break;
                    case R.id.rb_paypal:
                        paymentMethod = 1;
                        break;
                }
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
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.checkout),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }


    private void initViews(View view) {
//        Initialize main content Linear layout.
        showMainLayout(false);
    }


    private void addStoreView(ArrayList<CartStore> cartStores) {
        int points = 0;
        try {
            if (cartStores != null && cartStores.size() > 0) {
                if (cartStores != null && cartStores.size() > 0) {
                    for (CartStore store : cartStores) {
                        View view = LayoutInflater.from(context).inflate(R.layout.item_product_store, null);
                        TextView tvMinOrderPrice = (TextView) view.findViewById(R.id.tv_min_order_price);
                        tvMinOrderPrice.setVisibility(View.GONE);
                        initStoreViews(view, store);
                        points = (store.getTotalPriceRaw() * settingBO.getPoint()) + points;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_points.setText(points + " Points");

    }

    private void initStoreViews(View view, CartStore cartStore) {
        try {


            LinearLayout ll_store = (LinearLayout) view.findViewById(R.id.ll_store);
            TextView tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
            TextView tv_delivery_time = (TextView) view.findViewById(R.id.tv_delivery_time);
            TextView tv_min_order_price = (TextView) view.findViewById(R.id.tv_min_order_price);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);


            tv_price.setText(cartStore.getTotalPrice());
            tv_store_name.setText(cartStore.getName());
            tv_delivery_time.setText("Delivery in " + cartStore.getMinDeliveryCharges() + " min");
            tv_min_order_price.setText("Minimum Delivery Order $" + cartStore.getMinOrderPrice() + "");


            ll_container.addView(view);

            addProductViews(cartStore.getProducts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addProductViews(ArrayList<ProductBO> allProducts) {
        for (int i = 0; i < allProducts.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_product, null);
            initProductViews(view, allProducts.get(i));
            ll_container.addView(view);
        }
    }

    private void initProductViews(View view, final ProductBO product) {
        ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_popular = (TextView) view.findViewById(R.id.tv_popular);
        TextView tv_sub_title = (TextView) view.findViewById(R.id.tv_sub_title);
        TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        TextView tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
        LinearLayout ll_quantity = (LinearLayout) view.findViewById(R.id.ll_quantity);

        if (!product.getCartImage().contains("null")) {
            ImageUtils.setCenterImage(product.getCartImage(), iv_item, context, R.drawable.logo);
        }
        tv_title.setText(product.getName());
        tv_sub_title.setText(product.getDescription());
        tv_price.setText("Price " + product.getPrice());

        if (product.getQuantity() == -1)
            ll_quantity.setVisibility(View.GONE);
        else {
            tv_quantity.setText(product.getQuantity() + "");
            ll_quantity.setVisibility(View.VISIBLE);
        }

    }

    private void getCreditCardToken() {
        if (checkoutItem.address == null ||
                tvDeliveryInfo.getText().toString().trim().equals("")) {
            SnackBarUtil.showSnackbar(context, "Please add at least one delivery address to proceed", true);
        } else {
            verifyCreditMessageDialogue.setMessage(getResources().getString(R.string.verify_credit_card));
            verifyCreditMessageDialogue.show();
            Card card = null;
            if (checkoutItem.getCardItem() != null) {
                card = new Card(checkoutItem.getCardItem().getCardNumber(), Integer.parseInt(checkoutItem.getCardItem().getExpiryDate().split("/")[0]), Integer.parseInt(checkoutItem.getCardItem().getExpiryDate().split("/")[1]), checkoutItem.getCardItem().getCvv());
            } else if (cardItem != null) {
                card = new Card(cardItem.getCardNumber(), Integer.parseInt(cardItem.getExpiryDate().split("/")[0]), Integer.parseInt(cardItem.getExpiryDate().split("/")[1]), cardItem.getCvv());
            }
            Stripe getToken = new Stripe(context, Constants.STRIPE_LIVE_API_KEY);
            getToken.createToken(card, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    AlertOP.showAlert(context, "", "Your credit card is invalid ");
                    if (verifyCreditMessageDialogue.isShowing()) {
                        verifyCreditMessageDialogue.dismiss();
                    }
                }

                @Override
                public void onSuccess(final Token token) {
                    verifyCreditMessageDialogue.setMessage("Credit Card Verified Successfully");
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            if (verifyCreditMessageDialogue.isShowing()) {
                                verifyCreditMessageDialogue.dismiss();
                            }
                            callOrderPlaceOutApi(token.getId());
                        }
                    }.start();
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_add_creditcard:
                Activities.gotoNextFragment(context, AddNewCreditCard.newInstance(null));
                break;
            case R.id.tv_add_delivery_information:
                Activities.gotoNextFragment(context, AddNewAddress.newInstance(null));
                break;
            case R.id.btn_place_order:
                if (address == null) {
                    SnackBarUtil.showSnackbar(context, "No Address Selected", true);
                } else if (cardItem == null) {
                    SnackBarUtil.showSnackbar(context, "No Credit Card Selected", true);
                } else {
                    getCreditCardToken();
                }
                break;
            case R.id.ll_delivery_information: {
                Activities.gotoNextFragment(context, CreditCardInformation.newInstance());
                break;
            }
            case R.id.ll_delivery_info:
                Activities.gotoNextFragment(context, DeliveryInformation.newInstance(checkoutItem.getAddress()));
                break;
        }
    }

    @Subscribe
    public void getMessage(Object object) {
        if (object instanceof CardItem) {
            cardItem = (CardItem) object;
            tvCreditCardNumber.setText("Ends With " + cardItem.getCardNumber().substring(cardItem.getCardNumber().length() - 4, cardItem.getCardNumber().length()));
            tvCreditCardExpiry.setText(cardItem.getExpiryDate());
            checkoutItem.setCardItem(cardItem);
            tvAddCard.setVisibility(View.GONE);
        } else if (object instanceof Address) {
            address = (Address) object;
            if (checkoutItem != null) {
                checkoutItem.setAddress(address);
            }
            tvDeliveryInfo.setText(address.getAddress() + ", " + address.getZipCode());
            tvAddDeliveryInformation.setVisibility(View.GONE);
        }
    }

}//main