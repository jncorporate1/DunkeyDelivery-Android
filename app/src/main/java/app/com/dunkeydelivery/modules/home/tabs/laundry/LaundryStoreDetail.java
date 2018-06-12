package app.com.dunkeydelivery.modules.home.tabs.laundry;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hedgehog.ratingbar.RatingBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.food.pager.StoreViewPager;
import app.com.dunkeydelivery.modules.home.tabs.laundry.items.LaundryCategory;
import app.com.dunkeydelivery.modules.search.Search;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.StoreUtils;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.MenuItemSearch;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LaundryStoreDetail extends ToolbarFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    Unbinder unbinder;
    public static String ARG_PARAM1 = "storeBO";
    private StoreBO storeBO;

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

    @BindView(R.id.ib_search)
    ImageButton ibSearch;

    @BindView(R.id.ib_review)
    ImageButton ibReview;

    @BindView(R.id.ib_info)
    ImageButton ibInfo;

    @BindView(R.id.ll_wash)
    LinearLayout llWash;

    @BindView(R.id.ll_cleaning)
    LinearLayout llCleaning;

    @BindView(R.id.ll_tailoring)
    LinearLayout llTailoring;

    @BindView(R.id.iv_wash)
    ImageView iv_wash;

    @BindView(R.id.iv_dry_cleaning)
    ImageView iv_dry_cleaning;

    @BindView(R.id.iv_tailoring)
    ImageView iv_tailoring;

    @BindView(R.id.ll_date)
    LinearLayout llDate;

    @BindView(R.id.ll_time)
    LinearLayout llTime;

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_select_time)
    TextView tvSelectTime;


    private LaundryCategory laundryCategoryWashAndClean;
    private LaundryCategory laundryCategoryDryClean;
    private LaundryCategory laundryCategoryTailoring;

    public static LaundryStoreDetail newInstance(StoreBO storeBO) {
        Bundle args = new Bundle();
        LaundryStoreDetail fragment = new LaundryStoreDetail();
        args.putParcelable(ARG_PARAM1, storeBO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_laundry_detail, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        if (getArguments() != null) {
            storeBO = getArguments().getParcelable(ARG_PARAM1);
        }

        // Initialize all views
        ratingBar.setmClickable(false);

        setUpStoreDetail();

        setListeners();

        //set Current Date..
        tvDate.setText(DateTimeOp.getCurrentDateTime(Constants.dateFormat3));
        tvSelectTime.setText(DateTimeOp.getCurrentDateTime(Constants.dateFormat4));

        callGetLaundryStores();

    }//onViewCreated

    private void callGetLaundryStores() {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        serviceParams.put(Keys.Store_id, storeBO.getId());
        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        new WebServicesVolleyTask(context, true, "",
                EnumUtils.ServiceName.GetParentCategories,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.GetParentCategories),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    if (taskItem.isError()) {
                        //show alert only on the selected tab...
//                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                LogUtils.e(TAG, taskItem.getResponse());

                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());

                                Type tokenType = new TypeToken<List<LaundryCategory>>() {
                                }.getType();
                                ArrayList<LaundryCategory> laundryCategories = new Gson().fromJson(jsonObject.getJSONArray("Categories").toString(), tokenType);
                                setupData(laundryCategories);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

    private void setupData(ArrayList<LaundryCategory> laundryCategories) {

        if (laundryCategories != null) {

            for (int i = 0; i < laundryCategories.size(); i++) {

                LaundryCategory laundryCategory = laundryCategories.get(i);

                if (laundryCategory.getName().trim().equalsIgnoreCase("Wash & Fold") ||
                        laundryCategory.getName().trim().equalsIgnoreCase("Wash and Fold")) {
//                    ImageUtils.setCenterImage(laundryCategory.getImageUrl(), iv_wash, context);
                    llWash.setVisibility(View.VISIBLE);
                    laundryCategoryWashAndClean = laundryCategory;
                } else if (laundryCategory.getName().trim().equalsIgnoreCase("Dry Cleaning")) {
//                    ImageUtils.setCenterImage(laundryCategory.getImageUrl(), iv_dry_cleaning, context);
                    llCleaning.setVisibility(View.VISIBLE);
                    laundryCategoryDryClean = laundryCategory;
                } else if (laundryCategory.getName().trim().equalsIgnoreCase("Tailoring")) {
//                    ImageUtils.setCenterImage(laundryCategory.getImageUrl(), iv_tailoring, context);
                    llTailoring.setVisibility(View.VISIBLE);
                    laundryCategoryTailoring = laundryCategory;
                }
            }
//
        }
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
        llWash.setOnClickListener(this);
        llCleaning.setOnClickListener(this);
        llTailoring.setOnClickListener(this);
        llDate.setOnClickListener(this);
        llTime.setOnClickListener(this);
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
        Log.i("", "refreshToolbar: ");
        Activities.hideBottomNavigation(context, true);
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


    private void initViews(View view) {
        //Initialize main content Linear layout.

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
            case R.id.ll_wash:
                Activities.gotoNextFragment(context, WashFold.newInstance(laundryCategoryWashAndClean, getDateAndTime()));
                break;
            case R.id.ll_cleaning:
                Activities.gotoNextFragment(context, DryCleaning.newInstance(laundryCategoryDryClean, getDateAndTime()));
                break;
            case R.id.ll_tailoring:
                Activities.gotoNextFragment(context, Tailoring.newInstance(laundryCategoryTailoring, getDateAndTime()));
                break;
            case R.id.ll_date:
                showDatePicker();
                break;
            case R.id.ll_time:
                showTimePicker();
                break;
        }
    }

    private String getDateAndTime() {
        return tvDate.getText().toString() + " " + tvTime.getText().toString();

    }

    private void showTimePicker() {

        Calendar now = DateTimeOp.getCalendarFromFormat(tvSelectTime.getText().toString(), Constants.dateFormat4);
        if (now == null) {
            now = Calendar.getInstance();
        }
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                LaundryStoreDetail.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        tpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
        tpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
        tpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
    }

    private void showDatePicker() {

        Calendar now = DateTimeOp.getCalendarFromFormat(tvDate.getText().toString(), Constants.dateFormat3);
        if (now == null) {
            now = Calendar.getInstance();
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                LaundryStoreDetail.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setCancelColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
        dpd.setMinDate(DateTimeOp.getCalendarFromFormat(DateTimeOp.getCurrentDateTime(Constants.dateFormat3), Constants.dateFormat3));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        tvDate.setText(DateTimeOp.oneFormatToAnother(date, Constants.dateFormat6, Constants.dateFormat3));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString + ":" + secondString;
        tvSelectTime.setText(DateTimeOp.oneFormatToAnother(time, Constants.dateFormat10, Constants.dateFormat4));
    }
}