package app.com.dunkeydelivery.modules.home.tabs;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kogitune.activity_transition.fragment.ExitFragmentTransition;
import com.kogitune.activity_transition.fragment.FragmentTransition;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.NotificationUtils.GenerateNotification;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.activities.MainActivity;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.Items.Address;
import app.com.dunkeydelivery.modules.account.Items.CardItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.cart.fragments.items.CartStore;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
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
import app.com.dunkeydelivery.utils.sharedprefs.KeysSharedPrefs;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentProductDetail extends ToolbarFragment implements View.OnClickListener {
    private Context context;
    private String sheduleTime;
    private Integer scheduleTypeID;
    private String openFrom;
    private String openTo;
    private boolean checkAnimation;
    Unbinder unbinder;
    private ProductBO itemBO;
    private ArrayList<DeliveryTypes> deliveryTypes;
    private int quantity = 0;
    public static String ARG_PARAM1 = "productBO";
    public static String ARG_PARAM2 = "animationCheck";

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.btn_increment)
    ImageButton btnIncrement;

    @BindView(R.id.btn_decrement)
    ImageButton btnDecrement;

    @BindView(R.id.tv_display_count)
    TextView tvCount;

    @BindView(R.id.btn_add)
    Button btnAdd;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.tv_detail)
    TextView tv_detail;

    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.et_instructions)
    EditText et_instructions;

    int themeColor;
    String hoursFrom[];
    String hoursTo[];

    public static FragmentProductDetail newInstance(ProductBO itemBO,boolean checkAnimation) {
        Bundle args = new Bundle();
        FragmentProductDetail fragment = new FragmentProductDetail();
        args.putParcelable(ARG_PARAM1, itemBO);
        args.putBoolean(ARG_PARAM2, checkAnimation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_detail,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkAnimation=false;

        if (getArguments() != null) {
            itemBO = getArguments().getParcelable(ARG_PARAM1);
            checkAnimation = getArguments().getBoolean(ARG_PARAM2);
        }

        themeColor = (ContextCompat.getColor(context, R.color.colorPrimary));
        setListners();

        if(checkAnimation==true)
        {
            setImageTransitionAnimation(view, savedInstanceState);
        }

        setUpDetail();

    }//onViewCreated

    //setup and show image transition
    private void setImageTransitionAnimation(View rootView, Bundle savedInstanceState) {
        try {
            final ExitFragmentTransition exitFragmentTransition
                    = FragmentTransition
                    .with(this)
                    .to(rootView.findViewById(R.id.imageView))
                    .start(savedInstanceState);
            exitFragmentTransition.startExitListening();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //set listeners
    private void setListners() {
        btnIncrement.setOnClickListener(this);
        btnDecrement.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    //setup product data
    private void setUpDetail() {
        if (itemBO != null) {
            tvTitle.setText(itemBO.getName());
            tv_detail.setText(itemBO.getDescription());
            tv_price.setText(itemBO.getPrice());
            ImageUtils.setCenterImage(itemBO.getImage(), imageView, context, R.drawable.bg3);
            tv_total.setText(getTotalPrice(quantity, itemBO.price));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onResume() {
        super.onResume();
        callGetStoreSchedulingTypesApi(itemBO.getStoreId());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void refreshToolbar() {
        Activities.hideBottomNavigation(context, true);
        MenuItemImgOrStr menuItemImgOrStr = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.gotoNextFragment(context, CartMain.newInstance());
            }
        });

        //TODO; in this we have to add category title
        if (itemBO != null) {
            ToolbarOp.refresh(getView(), getActivity(), itemBO.getName(),
                    null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
        } else {
            ToolbarOp.refresh(getView(), getActivity(), getResources().getString(R.string.default_product_name),
                    null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_increment:
                if (quantity < Constants.MAX_ITEMS_TO_LOAD) {
                quantity = quantity + 1;
                tvCount.setText(quantity + "");
                tvCount.setTextColor(themeColor);
                btnIncrement.setColorFilter(themeColor, PorterDuff.Mode.SRC_IN);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.max_limit_reached), Toast.LENGTH_SHORT).show();
                }
                tv_total.setText(getTotalPrice(quantity, itemBO.price));
                break;
            case R.id.btn_decrement:
                if (quantity > 0) {
                    quantity = quantity - 1;

                    tvCount.setText(quantity + "");
                }
                if (quantity == 0) {
                    tvCount.setTextColor(ContextCompat.getColor(context, R.color.grey));
                    btnIncrement.clearColorFilter();
                }
                tv_total.setText(getTotalPrice(quantity, itemBO.price));
                break;
            case R.id.btn_add:
                try {
                    checkQuantity();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    //check selected time between store hours
    private Boolean checkTimeInBetweenStoreHours(String selectedTime) {
        Boolean check = false;
        try {
            String timeFrom = hoursFrom[0] + ":" + hoursFrom[1];
            Date formattedTimeFrom = new SimpleDateFormat(Constants.dateFormat30).parse(timeFrom);
            Calendar calendarTimeFrom = Calendar.getInstance();
            calendarTimeFrom.setTime(formattedTimeFrom);

            String timeTo = hoursTo[0] + ":" + hoursTo[1];
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
            if (x.after(calendarTimeFrom.getTime()) && x.before(calendarTimeTo.getTime())) {
                check = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return check;
    }

    //show schedule type dialog to get schedule selection
    private void showDialogue() {
        Boolean check = false;
        if (CartOP.getStorePosition(itemBO.getStoreId()) == -1) {
            check = false;
        } else {
            check = true;
        }

        if (!check && deliveryTypes!=null && deliveryTypes.size()>0) {
            try {
                DialogFragmentSheduling dialogFragmentSheduling = DialogFragmentSheduling.newInstance(
                        deliveryTypes,hoursFrom,hoursTo
                );
                dialogFragmentSheduling.setLisenter(new DialogFragmentSheduling.SetDialogDismissListener() {
                    @Override
                    public void setASAPListener(String scheduleTypeId) {

                        sheduleTime = getScheduleForASAP(itemBO);

                        if (!TextUtils.isEmpty(scheduleTypeId)) {
                            scheduleTypeID = Integer.parseInt(scheduleTypeId);
                        } else {
                            scheduleTypeID = null;
                        }
                        goToCartMain();
                    }
                    @Override
                    public void setTodayListener(String selectedTime, String scheduleTypeId) {
                        if (checkTimeInBetweenStoreHours(selectedTime) == true) {
                            if(!TextUtils.isEmpty(scheduleTypeId)) {
                                scheduleTypeID = Integer.parseInt(scheduleTypeId);
                            }
                            else
                            {
                                scheduleTypeID = null;
                            }
                            sheduleTime = DateTimeOp.getCurrentDateTime(Constants.dateFormat16) + " " + selectedTime;
                            goToCartMain();
                        } else {
                            SnackBarUtil.showSnackbar(context, getResources().getString(R.string.store_hours_time), true);
                        }
                    }

                    @Override
                    public void setLaterListener(String selectedDate, String selectedTime, String scheduleTypeId) {
                        if (checkTimeInBetweenStoreHours(selectedTime) == true) {
                            if(!TextUtils.isEmpty(scheduleTypeId)) {
                                scheduleTypeID = Integer.parseInt(scheduleTypeId);
                            }
                            else
                            {
                                scheduleTypeID = null;
                            }
                            sheduleTime = selectedDate + " " + selectedTime;
                            goToCartMain();
                        } else {
                            SnackBarUtil.showSnackbar(context, getResources().getString(R.string.store_hours_time), true);
                        }
                    }
                });
                dialogFragmentSheduling.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                dialogFragmentSheduling.setCancelable(false);
                dialogFragmentSheduling.show(getActivity().getFragmentManager(), "");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } else {
            try {
                if(!check && deliveryTypes.size()==0)
                {
                    sheduleTime = getScheduleForASAP(itemBO);
                    scheduleTypeID=0;
                    goToCartMain();
                }
                else {
                    goToCartMainWithoutSchedule();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //setup shedule for ASAP
    private String getScheduleForASAP(ProductBO productBO) {
        String time = DateTimeOp.oneFormatToAnotherInUTC(Integer.parseInt(productBO.getMinDeliveryTime()), Constants.dateFormat12, Constants.dateFormat30);
        if (!TextUtils.isEmpty(time)) {
            String arr[] = time.split(" ");
            sheduleTime = DateTimeOp.getCurrentDateTime(Constants.dateFormat16) + " " + arr[0];
        }
        return sheduleTime;
    }

    //goto cart main if store already added in cart
    private void goToCartMainWithoutSchedule() {
        Activities.removeAllFragments(context);
        itemBO.setQuantity(quantity);
        itemBO.setAdditionNote(et_instructions.getText().toString().trim());
        CartOP.addITem(itemBO);
        Activities.gotoNextFragment(context, CartMain.newInstance());
    }

    //add new store in cart and go to cart main
    private void goToCartMain() {
        Activities.removeAllFragments(context);
        itemBO.setQuantity(quantity);
        itemBO.setAdditionNote(et_instructions.getText().toString().trim());
        CartOP.addITem(itemBO, scheduleTypeID, sheduleTime, openFrom, openTo, deliveryTypes);
        Activities.gotoNextFragment(context, CartMain.newInstance());
    }

    //check product quantity
    private void checkQuantity() {
        try {
            if (quantity == 0) {
                SnackBarUtil.showSnackbar(context, context.getString(R.string.no_quantity_selected), true);
            } else {
                showDialogue();
            }
        }
        catch (Exception e)
        {
            LogUtils.i("mess",e.toString());
        }
    }

    //calculate product price according to quantity
    private String getTotalPrice(int quantity, String price) {
        return Constants.CURRENCY_SYMBOL + (quantity * Integer.parseInt(price));
    }

    //get store schedule types from api
    private void callGetStoreSchedulingTypesApi(Integer storeId) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.Store_id, storeId);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetStoreSchedule;

        new WebServicesVolleyTask(context, true, "",
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
                                JSONObject store = jsonObject.getJSONObject("Store");
                                openFrom = store.getString("Open_From");
                                openTo = store.getString("Open_To");
                                hoursFrom = openFrom.trim().split(":");
                                hoursTo = openTo.trim().split(":");
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<DeliveryTypes>>() {
                                }.getType();
                                deliveryTypes = gson.fromJson(store.getJSONArray("StoreDeliveryTypes").toString(), type);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}