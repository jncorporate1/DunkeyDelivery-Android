package app.com.dunkeydelivery.modules.home.tabs.laundry;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.cart.fragments.CartMain;
import app.com.dunkeydelivery.modules.home.items.ProductBO;
import app.com.dunkeydelivery.modules.home.items.StoreBO;
import app.com.dunkeydelivery.modules.home.tabs.laundry.items.LaundryCategory;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
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

public class WashFold extends ToolbarFragment {

    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    public static String ARG_PARAM1 = "laundryCategory";
    private static final String ARG_PARAM2 = "PickUpDate";
    private static final String ARG_PARAM3 = "StoreBO";
    Unbinder unbinder;

    @BindView(R.id.tv_description)
    CustomTextView tv_description;

    @BindView(R.id.btn_add)
    Button btnAdd;

    @BindView(R.id.et_weight)
    EditText etWeight;

    private LaundryCategory laundryCategory;
    private String pickUpDate;

    public static WashFold newInstance(LaundryCategory laundryCategory, String pickUpDate) {
        Bundle args = new Bundle();
        WashFold fragment = new WashFold();
        args.putParcelable(ARG_PARAM1, laundryCategory);
        args.putString(ARG_PARAM2, pickUpDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_wash_fold, container, false);
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

        // Initialize all views
        initViews();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etWeight.setError(null);
                if (etWeight.getText().toString().trim().isEmpty()) {
                    etWeight.setError(getString(R.string.error_field_required));
                    etWeight.requestFocus();
                } else {
                    callApiForWashAndFold(etWeight.getText().toString());
//                    ProductBO productBO = new ProductBO();
                    //productBO.set

                }
            }
        });

    }

    private void callApiForWashAndFold(String weight) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        serviceParams.put(Keys.Store_id, laundryCategory.getStore_Id());
        serviceParams.put(Keys.User_ID, UserSharedPreference.readUserBO().getId());
        serviceParams.put(Keys.LAUNDRY_WEIGHT, weight);
        serviceParams.put(Keys.ADDITIONAL_NOTE, "");
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
//                            SnackBarUtil.showSnackbar(context,context.getString(R.string.));
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


        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.wash_fold),
                null, ToolbarOp.Theme.Dark, 0, null, menuItemImgOrStr);
    }


    private void initViews() {
        //Initialize main content Linear layout.
        tv_description.setText(laundryCategory.getDescription());
    }

    private void stopSwipeLoader() {
    }


}