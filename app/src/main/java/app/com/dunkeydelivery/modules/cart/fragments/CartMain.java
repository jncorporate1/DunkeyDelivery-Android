package app.com.dunkeydelivery.modules.cart.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.items.SettingBO;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.items.CartStore;
import app.com.dunkeydelivery.modules.cart.fragments.items.CheckoutItem;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.CartOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.DialogFragmentSheduling;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartMain extends ToolbarFragment {
    private Context context;
    private int tag;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    private ArrayList<CartStore> cartStores;
    Unbinder unbinder;

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.btn_go_to)
    Button btnGotoCheckout;

    @BindView(R.id.tv_add_more)
    TextView tvAddMore;

    @BindView(R.id.tv_add_item)
    TextView tv_add_item;

    @BindView(R.id.ll_nocart)
    LinearLayout ll_nocart;
    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;

    @BindView(R.id.tv_points)
    TextView tv_point;

    @BindView(R.id.ll_container)
    LinearLayout ll_container;

    private SwipeLayout openSwipeLayout = null;

    public static CartMain newInstance() {
        Bundle args = new Bundle();
        CartMain fragment = new CartMain();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        btnGotoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (isValidOrder())
                        callCheckOutApi();
                    else {
                        AlertOP.showAlert(context, "CheckOut",
                                context.getResources().getString(R.string.less_min_order),
                                "Ok", "", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                    }
                }
                catch (Exception e)
                {
                    LogUtils.i("mess",""+e.toString());
                }
            }

        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.goBackFragment(context, 1);
            }
        });

        tvAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.setSelectedTab(context, 0);
            }
        });
        tv_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.setSelectedTab(context, 0);
            }
        });

        addStoreView();

    }//onViewCreated

    //validating if any product price is less then store min order price
    private boolean isValidOrder() {
        boolean isValidOrder = true;
        for (CartStore store : CartOP.getCarts()) {
            int num=(int)store.getTotalPriceRaw();
            if (num < Integer.parseInt(store.getMinOrderPrice())) {
                isValidOrder = false;
                break;

            }
        }
        return isValidOrder;
    }

    private void callCheckOutApi() {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        serviceParams.put(Keys.Stores, CartOP.getRawCart());
        serviceParams.put(Keys.User_ID, UserSharedPreference.readUserBO().getId());

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetCartMobile;

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
                            }
                        });
                    } else {
                        try {

                            Gson gson = new Gson();
                            Type type = new TypeToken<CheckoutItem>() {
                            }.getType();
                            CheckoutItem checkoutItem = gson.fromJson(taskItem.getResponse(), type);
                            Activities.gotoNextFragment(context, Checkout.newInstance(checkoutItem));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    //setup stores
    private void addStoreView() {
        int count=0;
        int points = 0;
        try {

            cartStores = CartOP.getCarts();

            if (cartStores != null && cartStores.size() > 0) {

                viewLayout(true);
                for (CartStore store : cartStores) {
                    try {
                        View view = LayoutInflater.from(context).inflate(R.layout.item_product_store, null);
                        initStoreViews(view, store, count);
                        addProductViews(store, cartStores.indexOf(store));
                        SettingBO settingBO = ObjectSharedPreference.getObject(SettingBO.class, Keys.SETTINGS);
                        if (settingBO == null) {
                            settingBO = new SettingBO();
                        }
                        points = (store.getTotalPriceRaw() * settingBO.getPoint()) + points;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            } else {
                viewLayout(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            points = 0;
        }
        tv_point.setText(points + " Points");
    }

    //set layouts visibility if stores added to cart
    private void viewLayout(boolean isCartExists) {
        if (isCartExists) {
            ll_nocart.setVisibility(View.GONE);
            rl_cart.setVisibility(View.VISIBLE);
        } else {
            ll_nocart.setVisibility(View.VISIBLE);
            rl_cart.setVisibility(View.GONE);
        }
    }

    //setup stores data and schedule type
    private void initStoreViews(View view, final CartStore cartStore,int count)
    {
        try {

            LinearLayout ll_store = (LinearLayout) view.findViewById(R.id.ll_store);
            TextView tv_store_name = (TextView) view.findViewById(R.id.tv_store_name);
            TextView tv_delivery_time = (TextView) view.findViewById(R.id.tv_delivery_time);
            TextView tv_min_order_price = (TextView) view.findViewById(R.id.tv_min_order_price);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);

            tv_price.setText(cartStore.getTotalPrice());
            tv_store_name.setText(cartStore.getName());
            if(!cartStore.getOrderDateTime().isEmpty() && !cartStore.getOrderDateTime().equals(null) && !cartStore.getType_Id().equals(0))
            {
                if(cartStore.getOrderDateTime().trim().contains(" "))
                {
                    tv_delivery_time.setText("Delivery Schedule " +
                                    DateTimeOp.oneFormatToAnother(
                                            cartStore.getOrderDateTime().trim().split(" ")[0],
                                            Constants.dateFormat16,
                                            Constants.dateFormat3)+" "+
                                    DateTimeOp.oneFormatToAnother(
                                            cartStore.getOrderDateTime().trim().split(" ")[1]+":00",
                                            Constants.dateFormat10,
                                            Constants.dateFormat4)
                            );
                }
                else {
                    tv_delivery_time.setText("Delivery Schedule " +
                            DateTimeOp.oneFormatToAnother(
                                    cartStore.getOrderDateTime().trim()+":00",
                                    Constants.dateFormat10,
                                    Constants.dateFormat4));
                }
                tv_delivery_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tag = (int) v.getTag();
                        showDialogue(cartStore);
                    }
                });
            }
            else
            {
                tv_delivery_time.setText("Delivery in " + cartStore.getMinDeliveryTime() + " min");
            }
            tv_min_order_price.setText("Minimum Order Price $" + cartStore.getMinOrderPrice() + "");
            tv_delivery_time.setTag(count);
            ++count;
            ll_container.addView(view);

        } catch (Exception e) {
            LogUtils.i("mess",e.toString());
        }
    }

    //show set schedule type dialog
    private void showDialogue(final CartStore storeBO) {
        DialogFragmentSheduling dialogFragmentSheduling = DialogFragmentSheduling.newInstance(
                storeBO.getDeliveryTypes()
                ,
                storeBO.getOrderDateTime()
                ,
                storeBO.getType_Id()
                ,
                storeBO.getOpenFrom().split(":")
                ,
                storeBO.getOpenTo().split(":")
        );
        dialogFragmentSheduling.setLisenter(new DialogFragmentSheduling.SetDialogDismissListener() {
            @Override
            public void setASAPListener(String scheduleTypeId) {
                String sheduleTime=null;
                int scheduleTypeID=0;
                String time = DateTimeOp.oneFormatToAnotherInUTC(Integer.parseInt(storeBO.getMinDeliveryTime()), Constants.dateFormat12, Constants.dateFormat30);
                if(!TextUtils.isEmpty(time)) {
                    String arr[] = time.split(" ");
                    sheduleTime = DateTimeOp.getCurrentDateTime(Constants.dateFormat16) + " " + arr[0];
                    if (!TextUtils.isEmpty(scheduleTypeId)) {
                        scheduleTypeID = Integer.parseInt(scheduleTypeId);
                    }
                }
                storeBO.setOrderDateTime(
                        sheduleTime);
                storeBO.setType_Id(scheduleTypeID);
                cartStores.remove(tag);
                cartStores.add(tag,storeBO);
                ObjectSharedPreference.saveCart(cartStores);
                ll_container.removeAllViews();
                addStoreView();
            }

            @Override
            public void setTodayListener(String selectedTime, String scheduleTypeId) {
                if (checkTimeInBetweenStoreHours(storeBO.getOpenFrom(),storeBO.getOpenTo(),selectedTime) == true) {
                    storeBO.setOrderDateTime(DateTimeOp.getCurrentDateTime(Constants.dateFormat16) + " " + selectedTime);
                    storeBO.setType_Id(Integer.parseInt(scheduleTypeId));
                    cartStores.remove(tag);
                    cartStores.add(tag,storeBO);
                    ObjectSharedPreference.saveCart(cartStores);
                    ll_container.removeAllViews();
                    addStoreView();
                } else {
                    SnackBarUtil.showSnackbar(context, getResources().getString(R.string.store_hours_time), true);
                }
            }

            @Override
            public void setLaterListener(String selectedDate, String selectedTime, String scheduleTypeId) {
                if (checkTimeInBetweenStoreHours(storeBO.getOpenFrom(),storeBO.getOpenTo(),selectedTime) == true) {
                    storeBO.setOrderDateTime(selectedDate + " " + selectedTime);
                    storeBO.setType_Id(Integer.parseInt(scheduleTypeId));
                    cartStores.remove(tag);
                    cartStores.add(tag,storeBO);
                    ObjectSharedPreference.saveCart(cartStores);
                    ll_container.removeAllViews();
                    addStoreView();
                } else {
                    SnackBarUtil.showSnackbar(context, getResources().getString(R.string.store_hours_time), true);
                }
            }
        });
        dialogFragmentSheduling.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragmentSheduling.setCancelable(false);
        dialogFragmentSheduling.show(getActivity().getFragmentManager(), "");
    }

    //check selected time between store hours
    private Boolean checkTimeInBetweenStoreHours(String hoursFrom,String hoursTo,String selectedTime) {
        Boolean check = false;
        try {
            String timeFrom = hoursFrom.trim().split(":")[0] + ":" + hoursFrom.trim().split(":")[1];
            Date formattedTimeFrom = new SimpleDateFormat(Constants.dateFormat30).parse(timeFrom);
            Calendar calendartimeFrom = Calendar.getInstance();
            calendartimeFrom.setTime(formattedTimeFrom);

            String timeTo = hoursTo.trim().split(":")[0] + ":" + hoursTo.trim().split(":")[1];
            Date formattedTimeTo = new SimpleDateFormat(Constants.dateFormat30).parse(timeTo);
            Calendar calendarTimeTo = Calendar.getInstance();
            calendarTimeTo.setTime(formattedTimeTo);
            calendarTimeTo.add(Calendar.DATE, 1);

            String userSelectedTime = selectedTime;
            Date formattedTimeSelected = new SimpleDateFormat(Constants.dateFormat30).parse(userSelectedTime);
            Calendar calendarTimeSelected = Calendar.getInstance();
            calendarTimeSelected.setTime(formattedTimeSelected);
            calendarTimeSelected.add(Calendar.DATE, 1);

            Date x = calendarTimeSelected.getTime();
            if (x.after(calendartimeFrom.getTime()) && x.before(calendarTimeTo.getTime())) {
                check = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }

    //setup products view
    private void addProductViews(CartStore store, int storePosition) {
        ArrayList<ProductBO> storeProducts = store.getProducts();
        for (ProductBO productBO : storeProducts) {
            try {
                View view = LayoutInflater.from(context).inflate(R.layout.item_product_swipe, null);
                initProductViews(view, productBO, storePosition, storeProducts.indexOf(productBO));
                ll_container.addView(view);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //setup products and implement listeners
    private void initProductViews(View view, final ProductBO product, final int storePosition, final int productPosition) {
        try {

            ImageButton ib_decrement = (ImageButton) view.findViewById(R.id.ib_decrement);
            ImageButton ib_increment = (ImageButton) view.findViewById(R.id.ib_increment);
            ImageButton ib_delete = (ImageButton) view.findViewById(R.id.ib_delete);
            ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_popular = (TextView) view.findViewById(R.id.tv_popular);
            TextView tv_sub_title = (TextView) view.findViewById(R.id.tv_sub_title);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tv_quantity = (TextView) view.findViewById(R.id.tv_quantity);
            LinearLayout ll_quantity = (LinearLayout) view.findViewById(R.id.ll_quantity);
            final SwipeLayout swipeLayout = view.findViewById(R.id.swipe);

            ll_quantity.setVisibility(View.VISIBLE);
            ib_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertOP.showAlert(context, "", context.getString(R.string.content_delete), "Yes", "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteProduct(product);
                        }
                    });
                }
            });

            ib_decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (product.getQuantity() > 1) {
                        CartOP.updateProductQuantity(-1, storePosition, productPosition, false);
                        ll_container.removeAllViews();
                        addStoreView();
                    }

                }
            });
            ib_increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartOP.updateProductQuantity(1, storePosition, productPosition, false);
                    ll_container.removeAllViews();
                    addStoreView();
                }
            });
            ImageUtils.setCenterImage(product.getCartImage(), iv_item, context, R.drawable.logo);
            tv_title.setText(product.getName());
            tv_sub_title.setText(product.getDescription());
            tv_price.setText("Price " + product.getPrice());
            tv_quantity.setText(product.getQuantity() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        ToolbarOp.hideToolbarRetainViews(getView(), getContext());
    }

    //delete product from cart and setup stores and products again
    private void deleteProduct(ProductBO product) {
        CartOP.deleteProduct(product);
        ll_container.removeAllViews();
        addStoreView();
    }
}