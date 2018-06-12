package app.com.dunkeydelivery.modules.home.tabs.laundry;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.interfaces.OnLoadMoreListener;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.laundry.adapters.LaundryAdapter;
import app.com.dunkeydelivery.modules.home.tabs.laundry.items.LaundryCategory;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.CartOP;
import app.com.dunkeydelivery.utils.DateTimeOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomTextView;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.MenuItemImgOrStr;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DryCleaning extends ToolbarFragment implements View.OnClickListener {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    Unbinder unbinder;
    public static String ARG_PARAM1 = "laundryCategory";
    private static final String ARG_PARAM2 = "PickUpData";
    private static final String ARG_PARAM3 = "StoreBO";
    private LaundryAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rg)
    RadioGroup rg;

    @BindView(R.id.tv_note)
    TextView tvNote;

    @BindView(R.id.tv_description)
    CustomTextView tv_description;

    @BindView(R.id.btn_add)
    Button btnAdd;

    private LaundryCategory laundryCategory;
    private String pickUpDate;

    //tab titles...
    /*private int[] titles = { R.string.shirts,
            R.string.pants,
            R.string.blouse,
            R.string.sweater,
            R.string.tie,
            R.string.shorts,
            R.string.comforter,
            R.string.blanket,
            R.string.cover,
            R.string.pillow,
            R.string.dress,
            R.string.jacket,
            R.string.suit,
            R.string.skirt,
            R.string.coat,
            R.string.scarf,
    };*/

    //tab icons...
    /*protected int[] imageResId = {R.drawable.shirt,
            R.drawable.pants,
            R.drawable.top,
            R.drawable.sweater,
            R.drawable.tie,
            R.drawable.shorts,
            R.drawable.comforter,
            R.drawable.blanket,
            R.drawable.cover,
            R.drawable.pillow,
            R.drawable.dress,
            R.drawable.jacket,
            R.drawable.suit,
            R.drawable.skirt,
            R.drawable.coat,
            R.drawable.scarf
    };*/

    //tab selected icons...
    /*protected int[] imageResIdSelected = {R.drawable.shirtselected,
            R.drawable.pansselected,
            R.drawable.topselected,
            R.drawable.sweaterselected,
            R.drawable.tiesselected,
            R.drawable.shortsselected,
            R.drawable.comforterselected,
            R.drawable.blanketselected,
            R.drawable.coverselected,
            R.drawable.pillowselected,
            R.drawable.dressselected,
            R.drawable.jacketselected,
            R.drawable.suitselected,
            R.drawable.skirtselected,
            R.drawable.coatselected,
            R.drawable.scarfselected
    };*/

    public static DryCleaning newInstance(LaundryCategory laundryCategory, String pickUpDate) {
        Bundle args = new Bundle();
        DryCleaning fragment = new DryCleaning();
        args.putParcelable(ARG_PARAM1, laundryCategory);
        args.putString(ARG_PARAM2, pickUpDate);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dry_cleaning, container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            laundryCategory = getArguments().getParcelable(ARG_PARAM1);
            pickUpDate = getArguments().getString(ARG_PARAM2);
        }

        callGetDryCleaningProducts();
        initViews(view);
        setListener();
    }

    private void callGetDryCleaningProducts() {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        serviceParams.put(Keys.Store_id, laundryCategory.getStore_Id());
        serviceParams.put(Keys.PARENT_CATEGORY_ID, laundryCategory.getId());

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        new WebServicesVolleyTask(context, true, "",
                EnumUtils.ServiceName.GetCategoryItems,
                EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.GetCategoryItems),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    if (taskItem.isError()) {
                        //show alert only on the selected tab...
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                LogUtils.e(TAG, taskItem.getResponse());

                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());

                                Type listType = new TypeToken<List<ProductBO>>() {
                                }.getType();
                                Gson gson = new Gson();
                                List<ProductBO> productBOList = (List<ProductBO>) gson.fromJson(jsonObject.getJSONArray("Products").toString(),
                                        listType);
                                setUpRecyclerView(productBOList);
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

    private void setListener() {

        btnAdd.setOnClickListener(this);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.tv_entire:
                        //show note...
                        tvNote.setVisibility(View.VISIBLE);
                        btnAdd.setText(context.getString(R.string.submit));
                        recyclerView.setVisibility(View.GONE);
                        break;

                    case R.id.tv_item:
                        //show items...
                        tvNote.setVisibility(View.GONE);
                        btnAdd.setText(context.getString(R.string.add_to_bag));
                        recyclerView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

    }

    private void setUpRecyclerView(List<ProductBO> productBOList) {

        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new LaundryAdapter(productBOList, context, recyclerView);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
            }
        });

        mAdapter.setClickListener(new LaundryAdapter.ClickListeners() {
            @Override
            public void onRowClick(int position) {

            }
        });

        recyclerView.setAdapter(mAdapter);
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
        MenuItemImgOrStr menuItemImgOrStr = new MenuItemImgOrStr(R.drawable.ic_cart, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activities.gotoNextFragment(context, CartMain.newInstance());
            }
        });


        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.dry_cleaning),
                null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.
        tv_description.setText(laundryCategory.getDescription());

    }

    private void stopSwipeLoader() {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add:
                if (rg.getCheckedRadioButtonId() == R.id.tv_entire) {
                    callApiForWashAndFold(tvNote.getText().toString().trim());

                } else {
                    if (mAdapter != null && mAdapter.getItemCount() > 0) {
                        ArrayList<ProductBO> productBOS = mAdapter.getProducts();
                        if (productBOS.size() > 0) {
                            for (ProductBO productBO : productBOS) {
                                productBO.setAdditionNote(tvNote.getText().toString().trim());
                                CartOP.addITem(productBO,0,getScheduleForDealsAndPromotions(productBO)," "," ",null);
                            }
                            Activities.removeAllFragments(context);
                            Activities.gotoNextFragment(context, CartMain.newInstance());
                        } else {
                            SnackBarUtil.showSnackbar(context, context.getString(R.string.no_quantity_selected), false);
                        }

                    } else {
                        Activities.removeAllFragments(context);
                    }
                }

        }
    }

    private String getScheduleForDealsAndPromotions(ProductBO productBO)
    {
        String time = DateTimeOp.oneFormatToAnotherInUTC(Integer.parseInt(productBO.getMinDeliveryTime()), Constants.dateFormat12, Constants.dateFormat30);
        String arr[] = time.split(" ");

        String sheduleTime = DateTimeOp.getCurrentDateTime("yyyy-MM-dd")+" "+arr[0];
        return sheduleTime;
    }

    private void callApiForWashAndFold(String additionalNote) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        serviceParams.put(Keys.Store_id, laundryCategory.getStore_Id());
        serviceParams.put(Keys.User_ID, UserSharedPreference.readUserBO().getId());
        serviceParams.put(Keys.LAUNDRY_WEIGHT, "");
        serviceParams.put(Keys.ADDITIONAL_NOTE, additionalNote);
        serviceParams.put(Keys.LAUNDRY_PICKUPTIME, pickUpDate);

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());
        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.RequestGetClothMobile;

        new WebServicesVolleyTask(context, true, "",
                serviceName,
                EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    if (taskItem.isError()) {
                        //show alert only on the selected tab...
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                LogUtils.e(TAG, taskItem.getResponse());
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                            }

                            Activities.removeAllFragments(context);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }

}