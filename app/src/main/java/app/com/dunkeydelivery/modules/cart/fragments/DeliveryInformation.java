package app.com.dunkeydelivery.modules.cart.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.Items.Address;
import app.com.dunkeydelivery.modules.account.fragments.AddNewAddress;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.ColorOp;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.customviews.widgets.CustomRadioButton;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DeliveryInformation extends ToolbarFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param_1";
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    Unbinder unbinder;

    @BindView(R.id.tv_add_address)
    TextView tvAddNewAddress;

    @BindView(R.id.rg_address)
    RadioGroup radioGroup;

    @BindView(R.id.rb1)
    RadioButton rb1;

    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.rv_swipe_refresh)
    SwipeRefreshLayout rvSwipeRefresh;

    private ArrayList<Address> addressList;
    private Address userAddress;


    public static DeliveryInformation newInstance(Address address) {
        Bundle args = new Bundle();
        DeliveryInformation fragment = new DeliveryInformation();
        args.putSerializable(ARG_PARAM1, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_delivery_info,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        if (getArguments() != null) {
            userAddress = (Address) getArguments().getSerializable(ARG_PARAM1);
        }

        rvSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callGetAddressListApi(UserSharedPreference.readUserBO().getId());
            }
        });
//        setUpRecycler(new ArrayList<ItemBO>());

        rvSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                updateSwipeLoader(true);
                callGetAddressListApi(UserSharedPreference.readUserBO().getId());
            }
        });

        rvSwipeRefresh.setColorSchemeColors(
                ColorOp.getColor(context, R.color.colorPrimary),
                ColorOp.getColor(context, R.color.colorAccent),
                ColorOp.getColor(context, R.color.colorPrimaryDark));
        setListeners();

        //TODO in this screen we will add address views in radio group...

    }

    private void updateSwipeLoader(boolean showRefreshing) {
        if (rvSwipeRefresh != null) {
            rvSwipeRefresh.setRefreshing(showRefreshing);
        }
    }

    private void setListeners() {
        tvAddNewAddress.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        rb1.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

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
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.delivery_information),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }


    private void stopSwipeLoader() {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_add_address:
                Activities.gotoNextFragment(context, AddNewAddress.newInstance(null));
                break;
            case R.id.btn_update:
//                Activities.goBackFragment(context, 1);
                if(radioGroup.getChildCount()>0) {
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
//                EventBus.getDefault().post(addressList.get(radioGroup.indexOfChild(radioButton)));
                    callUpdateAddressApi(addressList.get(radioGroup.indexOfChild(radioButton)));
                }
                else
                {
                    SnackBarUtil.showSnackbar(context,getResources().getString(R.string.select_address_to_proceed),true);
                }
                break;
        }
    }

    private void callUpdateAddressApi(final Address address) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();


        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().getAccessToken());

        serviceParams.put("Address_Id", address.getId());
        serviceParams.put(Keys.User_ID, UserSharedPreference.readUserBO().getId());

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetUserAddressesById;

        new WebServicesVolleyTask(context, true, "Updating Address",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                goBackToFragment(address);
                            }
                        });
                    } else {
                        goBackToFragment(address);
                    }

                } else {
                    goBackToFragment(address);
                }

            }
        });
    }

    private void goBackToFragment(Address address) {
        EventBus.getDefault().post(address);
        Activities.goBackFragment(context, 1);
    }

    private void callGetAddressListApi(String userId) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);
        serviceParams.put(Keys.USER_ID, userId);


        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.GetUserAddresses;

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
                                JSONArray jsonArray = jsonObject.getJSONArray("addresses");
                                Gson gson = new Gson();
                                Type typeToken = new TypeToken<List<Address>>() {
                                }.getType();
                                addressList = gson.fromJson(jsonArray.toString(), typeToken);
                                setUpData(addressList);
                            }
                            //updateSwipeLoader(false);
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


    private void setUpData(List<Address> addressList) {
        radioGroup.removeAllViews();
        int id = 5;     //using this variable to set Id of radiobutton when radiobutton is added in layout.
        //id can be any number
        for (Address address : addressList) {
            CustomRadioButton customRadioButton = new CustomRadioButton(context);
            customRadioButton.setId(id++);
            customRadioButton.setText(address.getAddress() + " " + address.getAddress2());
            if (userAddress != null) {
                if (address.getId() == userAddress.getId()) {
                    customRadioButton.setChecked(true);
                } else {
                    customRadioButton.setChecked(false);
                }
            }
            radioGroup.addView(customRadioButton);
        }
    }

    @Subscribe
    public void getMessage(Object object) {
        updateSwipeLoader(true);
        callGetAddressListApi(UserSharedPreference.readUserBO().getId());
    }
}