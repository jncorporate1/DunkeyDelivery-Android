package app.com.dunkeydelivery.modules.account.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Objects;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.Items.Address;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.Validation;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddNewAddress extends ToolbarFragment implements View.OnClickListener {
    private Context context;
    Unbinder unbinder;
    public static String ARG_PARAM1 = "addressItem";
    private Address addressItem;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.et_suit)
    EditText etSuit;

    @BindView(R.id.et_city)
    EditText etCity;

    @BindView(R.id.et_state_code)
    EditText etSateCode;

    @BindView(R.id.et_zipcode)
    EditText etZip;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.rg)
    RadioGroup radioGroup;

    @BindView(R.id.rb_home)
    RadioButton rbHome;

    @BindView(R.id.rb_work)
    RadioButton rbWork;

    @BindView(R.id.rb_custom)
    RadioButton rbCustom;

    @BindView(R.id.btn_add_new_address)
    Button btnContinueAddress;


    public static AddNewAddress newInstance(Address addressItem) {
        Bundle args = new Bundle();
        AddNewAddress fragment = new AddNewAddress();
        args.putSerializable(ARG_PARAM1, addressItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_address,
                container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        if (getArguments() != null) {
            addressItem = (Address) getArguments().getSerializable(ARG_PARAM1);
        }

        if (addressItem != null) {
            setUpAddressDetail();
        }

        setListeners();

    }

    //setup user address details
    private void setUpAddressDetail() {
        etAddress.setText(addressItem.getAddress());
        etSuit.setText(addressItem.getAddress2());
        etCity.setText(addressItem.getCity());
        etSateCode.setText(addressItem.getStateCode());
        etZip.setText(addressItem.getZipCode());
        etPhone.setText(addressItem.getPhone());
        if (addressItem.getFrequency() == null || addressItem.getFrequency().equals("Home")) {
            rbHome.setChecked(true);
        } else if (addressItem.getFrequency().equals("Work")) {
            rbWork.setChecked(true);
        } else if (addressItem.getFrequency().equals("Custom")) {
            rbCustom.setChecked(true);
        }
        btnContinueAddress.setText(getString(R.string.update));
    }

    //set Listeners on widgets
    private void setListeners() {
        btnContinueAddress.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add_new_address:
                KeyboardOp.hide(context, etAddress);
                addAdress();
                break;
        }
    }

    private void addAdress() {
        etAddress.setError(null);
        etSuit.setError(null);
        etCity.setError(null);
        etSateCode.setError(null);
        etZip.setError(null);
        etPhone.setError(null);


        // Store values at the time of the SIGNUP attempt.
        String address = etAddress.getText().toString().trim();
        String suit = etSuit.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String stateCode = etSateCode.getText().toString().trim();
        String zip = etZip.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String frequency = null;
        if (rbHome.isChecked()) {
            frequency = "Home";
        } else if (rbWork.isChecked()) {
            frequency = "Work";
        } else if (rbCustom.isChecked()) {
            frequency = "Custom";
        }

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError(getString(R.string.error_field_required));
            focusView = etPhone;
            cancel = true;
        } else if (!Validation.isPhoneValid(phone)) {
            etPhone.setError(getString(R.string.error_invalid_phone));
            focusView = etPhone;
            cancel = true;
        }

        if (TextUtils.isEmpty(zip)) {
            etZip.setError(getString(R.string.error_field_required));
            focusView = etZip;
            cancel = true;
        }

        if (TextUtils.isEmpty(stateCode)) {
            etSateCode.setError(getString(R.string.error_field_required));
            focusView = etSateCode;
            cancel = true;
        }

        if (TextUtils.isEmpty(city)) {
            etCity.setError(getString(R.string.error_field_required));
            focusView = etCity;
            cancel = true;
        }

        if (TextUtils.isEmpty(suit)) {
            etSuit.setError(getString(R.string.error_field_required));
            focusView = etSuit;
            cancel = true;
        }

        if (TextUtils.isEmpty(address)) {
            etAddress.setError(getString(R.string.error_field_required));
            focusView = etAddress;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            callSetAddressListApi(address, suit, city, stateCode, zip, phone, frequency);
        }
    }

    private void callSetAddressListApi(final String fullAddress, final String suit, final String city, final String stateCode, final String Zip, final String phone, final String frequency) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.FullAddress, fullAddress);
        serviceParams.put(Keys.Address2, suit);
        serviceParams.put(Keys.City, city);
        serviceParams.put(Keys.State, stateCode);
        serviceParams.put(Keys.Telephone, phone);
        serviceParams.put(Keys.PostalCode, Zip);
        serviceParams.put(Keys.Frequency, frequency);
        serviceParams.put(Keys.USER_ID, UserSharedPreference.readUserBO().getId());
        EnumUtils.ServiceName serviceName;
        if (addressItem == null) {
            serviceName = EnumUtils.ServiceName.AddAddress;
        } else {
            serviceParams.put(Keys.id, addressItem.getId());
            serviceName = EnumUtils.ServiceName.EditUserAddress;
        }

        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context, etPhone);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {
                            String message = (addressItem == null) ? getString(R.string.address_added_successfully) : getString(R.string.address_updated_successfully);
                            SnackBarUtil.showSnackbar(context, message, false);
                            if(addressItem==null) {
                                addressItem=new Address(fullAddress,suit,city,stateCode,Zip,phone,frequency);
                                EventBus.getDefault().post(addressItem);
                            }
                            Activities.goBackFragment(context, 1);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}