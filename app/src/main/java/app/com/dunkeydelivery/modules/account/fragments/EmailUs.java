package app.com.dunkeydelivery.modules.account.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.modules.account.dialogs.SelectReasonDialog;
import app.com.dunkeydelivery.modules.account.interfaces.ReasonTypeClickListener;
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

public class EmailUs extends ToolbarFragment implements View.OnClickListener {
    private Context context;
    private String TAG = this.getClass().getSimpleName();
    Unbinder unbinder;

    @BindView(R.id.tv_reason)
    TextView tvReason;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etMail;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_instructions)
    EditText etInstructions;



    @BindView(R.id.btn_send)
    Button btnSend;

    EnumUtils.ReasonType selectedReasonType = EnumUtils.ReasonType.None;

    public static EmailUs newInstance() {
        Bundle args = new Bundle();
        EmailUs fragment = new EmailUs();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emailus,
                container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = inflater.getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        Activities.lockDrawer(context);
        setListeners();
        setUserInfo();
    }

    private void setListeners() {
        tvReason.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    private void setUserInfo()
    {
        etName.setText(UserSharedPreference.readUserBO().getFullName());
        etMail.setText(UserSharedPreference.readUserBO().getEmail());
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
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.email_us),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    private void stopSwipeLoader() {
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_reason:
                SelectReasonDialog dFragment = SelectReasonDialog.newInstance(selectedReasonType.ordinal(),
                        new ReasonTypeClickListener() {
                            @Override
                            public void onReasonClick(EnumUtils.ReasonType reasonType) {
                                //TODO her we get th selected reason...
                                selectedReasonType = reasonType;
                                tvReason.setText(EnumUtils.ReasonType.getLabel(selectedReasonType.ordinal(), context));
                            }
                        });
                dFragment.show(getActivity().getSupportFragmentManager(), "");
                break;
            case R.id.btn_send:
                sendEmail();
                break;
        }
    }

    private void sendEmail() {

        etName.setError(null);
        etMail.setError(null);
        etPhone.setError(null);

        // Store values at the time of the SIGNUP attempt.
        String name = etName.getText().toString().trim();
        String email = etMail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String instructions = etInstructions.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(instructions)) {
            etInstructions.setError(getString(R.string.error_field_required));
            focusView = etInstructions;
            cancel = true;
        }

        if(!TextUtils.isEmpty(phone) && !Validation.isPhoneValid(phone)) {
            etPhone.setError(getString(R.string.error_invalid_phone));
            focusView = etPhone;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            etMail.setError(getString(R.string.error_field_required));
            focusView = etMail;
            cancel = true;
        } else if (!Validation.isEmailValid(email)) {
            etMail.setError(getString(R.string.error_invalid_email));
            focusView = etMail;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            etName.setError(getString(R.string.error_field_required));
            focusView = etName;
            cancel = true;
        }

        //check whether the user select any reason or not...
        if(!cancel && selectedReasonType == EnumUtils.ReasonType.None){
            //show Alert...
            KeyboardOp.hide(context, etMail);
            cancel = true;
            showReasonRequiredAlert();
        }

        if (cancel) {
            if(focusView != null)
                focusView.requestFocus();
        } else {

            callEmailUsApi(name,email,phone,instructions,EnumUtils.ReasonType.getLabel(selectedReasonType.ordinal(),context));
        }

    }

    private void showReasonRequiredAlert() {
        AlertOP.showAlert(context, null, getString(R.string.please_select_reason));
    }

    private void clearFields() {
        etMail.setText("");
        etName.setText("");
        etPhone.setText("");
        Activities.goBackFragment(context, 1);
    }

    private void callEmailUsApi(String name,String email,String phone,String instructions,String selectedReasonType) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = new HashMap<>();

        tokenServiceHeaderParams.put(Keys.Authorization, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.Name,name );
        serviceParams.put(Keys.Email,email );
        serviceParams.put(Keys.Phone,phone );
        serviceParams.put(Keys.Message,instructions);
        serviceParams.put(Keys.ContactReason,selectedReasonType);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.SubmitContactUs;

        new WebServicesVolleyTask(context, true, "Sending Email",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context);

                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {
                            KeyboardOp.hide(context, etMail);
                            SnackBarUtil.showSnackbar(context, "Email Sent.", false);
                            clearFields();

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