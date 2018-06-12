package app.com.dunkeydelivery.modules.account.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.abstracts.ToolbarFragment;
import app.com.dunkeydelivery.activities.Activities;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.Validation;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import app.com.dunkeydelivery.utils.toolbar.ToolbarOp;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePassword extends ToolbarFragment {
    private Context context;
    private String TAG = this.getClass().getSimpleName();

    @BindView(R.id.btn_update)
    Button btnUpdate;

    @BindView(R.id.et_new_password)
    EditText etNewPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;


    public static ChangePassword newInstance() {
        Bundle args = new Bundle();
        ChangePassword fragment = new ChangePassword();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_password,
                container, false);
        context = inflater.getContext();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize all views

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        etNewPassword.setError(null);
        etConfirmPassword.setError(null);

        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check if confirm password is empty.
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.error_field_required));
            focusView = etConfirmPassword;
            cancel = true;
        }

        // Check if password is empty.
        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError(getString(R.string.error_field_required));
            focusView = etNewPassword;
            cancel = true;
        }

        // Check if password is empty.
        if (!cancel) {
            if (!newPassword.equals(confirmPassword)) {
                etConfirmPassword.setError(getString(R.string.error_passwords_not_match));
                focusView = etConfirmPassword;
                cancel = true;
            } else if (!Validation.isPasswordValid(newPassword)) {
                etNewPassword.setError(getString(R.string.error_invalid_password));
                focusView = etNewPassword;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            callChangePasswordApi(etNewPassword.getText().toString(), etConfirmPassword.getText().toString());
        }
    }

    private void callChangePasswordApi(String password, String confirmPassword) {
        HashMap<String, Object> serviceParams = new HashMap<>();
        HashMap<String, Object> headerTokenParams = new HashMap<>();

        headerTokenParams.put(Keys.TOKEN, UserSharedPreference.readUserToken().accessToken);

        serviceParams.put(Keys.USER_ID, UserSharedPreference.readUserBO().id);
        serviceParams.put(Keys.PASSWORD, password);
        serviceParams.put(Keys.CONFIRM_PASSWORD, confirmPassword);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.ChangePassword;

        new WebServicesVolleyTask(context, true, getString(R.string.password_changing),
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName), EnumUtils.RequestMethod.POST,
                serviceParams, headerTokenParams, new AsyncResponseCallBack() {
            @Override
            public void onTaskComplete(TaskItem taskItem) {
                if (taskItem.isError()) {
                    AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                } else {
                    AlertOP.showAlert(context, null, getString(R.string.password_updated_sucessfully), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Activities.goBackFragment(context, 1);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void refreshToolbar() {
        ToolbarOp.refresh(getView(), getActivity(), getString(R.string.change_password),
                null, ToolbarOp.Theme.Dark, 0, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        Activities.hideBottomNavigation(context, true);
    }


    private void initViews(View view) {
        //Initialize main content Linear layout.

    }

    private void stopSwipeLoader() {
    }


}