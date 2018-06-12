package app.com.dunkeydelivery.modules.home.tabs.ride;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.home.tabs.FragmentHome;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.SnackBarUtil;
import app.com.dunkeydelivery.utils.Validation;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RideMain extends Fragment implements View.OnClickListener {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    private boolean isHidden;
    Unbinder unbinder;

    @BindView(R.id.et_fullname)
    EditText et_fullname;
    @BindView(R.id.et_bussiness_name)
    EditText et_bussiness_name;
    @BindView(R.id.et_bussiness_type)
    EditText et_bussiness_type;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_zipcode)
    EditText et_zipcode;


    @BindView(R.id.btn_submit)
    Button btnSubmit;

    public static RideMain newInstance() {
        Bundle args = new Bundle();
        RideMain fragment = new RideMain();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ride_main,
                container, false);
        context = inflater.getContext();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
    }

    private void setListeners() {
        btnSubmit.setOnClickListener(this);
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


    private void attemptSubmitForm() {

        // Reset errors.
        et_fullname.setError(null);
        et_email.setError(null);
        et_bussiness_name.setError(null);
        et_phone.setError(null);
        et_zipcode.setError(null);
        et_bussiness_type.setError(null);

        // Store values at the time of the login attempt.
        String email = et_email.getText().toString().trim();
        String fullName = et_fullname.getText().toString().trim();
        String bussinessName = et_bussiness_name.getText().toString().trim();
        String bussinessType = et_bussiness_type.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String zip = et_zipcode.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(zip)) {
            et_zipcode.setError(getString(R.string.error_field_required));
            focusView = et_zipcode;
            cancel = true;
        } else if (!Validation.isZipValid(email)) {
            et_zipcode.setError(getString(R.string.error_invalid_zip));
            focusView = et_zipcode;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            et_phone.setError(getString(R.string.error_field_required));
            focusView = et_phone;
            cancel = true;
        } else if (!Validation.isPhoneValid(phone)) {
            et_phone.setError(getString(R.string.error_invalid_phone));
            focusView = et_phone;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_email.setError(getString(R.string.error_field_required));
            focusView = et_email;
            cancel = true;
        } else if (!Validation.isEmailValid(email)) {
            et_email.setError(getString(R.string.error_invalid_email));
            focusView = et_email;
            cancel = true;
        }

        if (TextUtils.isEmpty(bussinessType)) {
            et_bussiness_type.setError(getString(R.string.error_field_required));
            focusView = et_bussiness_type;
            cancel = true;
        }


        if (TextUtils.isEmpty(bussinessName)) {
            et_bussiness_name.setError(getString(R.string.error_field_required));
            focusView = et_bussiness_name;
            cancel = true;
        }

        if (TextUtils.isEmpty(bussinessType)) {
            et_bussiness_type.setError(getString(R.string.error_field_required));
            focusView = et_bussiness_type;
            cancel = true;
        }

        if (TextUtils.isEmpty(fullName)) {
            et_fullname.setError(getString(R.string.error_field_required));
            focusView = et_fullname;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            callRegisterRideApi(email,fullName,bussinessName,bussinessType,phone,zip);
            //clearFields();
//            SnackBarUtil.showSnackbar(context, "Form submitted", false);
        }
    }

    private void clearFields() {
        KeyboardOp.hide(context, et_fullname);
        et_fullname.setText("");
        et_email.setText("");
        et_bussiness_name.setText("");
        et_phone.setText("");
        et_zipcode.setText("");
        et_bussiness_type.setText("");
        SnackBarUtil.showSnackbar(context, getString(R.string.form_submitted_successfully), false);
        //take user to main food page...
        Fragment fragment = Activities.getCurrentFragment(context);
        if (fragment instanceof FragmentHome) {
            ((FragmentHome) fragment).setSelectedTab(0);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_submit:
                attemptSubmitForm();
                break;
        }
    }

    private void callRegisterRideApi(String email, String fullName, String businessName, String businessType, String phone, String zip) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.FULLNAME, fullName);
        serviceParams.put(Keys.BusinessName, businessName);
        serviceParams.put(Keys.BusinessType, businessType);
        serviceParams.put(Keys.EMAIL, email);
        serviceParams.put(Keys.PHONE, phone);
        serviceParams.put(Keys.ZipCode, zip);
        serviceParams.put(Keys.SignInType, EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Rider));


        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.RegisterRide;

        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {

                    KeyboardOp.hide(context, et_fullname);
                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {
                            clearFields();
//                            if (taskItem.getResponse() != null) {
//                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
//                                JSONArray jsonArray = jsonObject.getJSONArray("CreditCards");
//                                Gson gson = new Gson();
//                                Type typeToken = new TypeToken<List<CardItem>>() {
//                                }.getType();
//                                List<CardItem> cardItems = gson.fromJson(jsonArray.toString(), typeToken);
                            //setUpRecycler(cardItems);
                            //}

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        //EventBus.getDefault().post("");
                        // if response is successful then do something
                    }
                }
            }
        });
    }
}