package app.com.dunkeydelivery.activities.auth;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.Validation;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPassword extends AppCompatActivity {

    private Context context;

    // UI references.
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.btn_submit)
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        context = ForgetPassword.this;
        ButterKnife.bind(this);

        setToolbar();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setError(null);
                if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
                    etEmail.setError(getString(R.string.error_field_required));
                } else if (!Validation.isEmailValid(etEmail.getText().toString().trim())) {
                    etEmail.setError(getString(R.string.error_invalid_email));
                } else {
                    callForgetPasswordService(etEmail.getText().toString());
                }
            }
        });


    }//onCreate

    private void callForgetPasswordService(String email) {
        HashMap<String, Object> serviceParams = new HashMap<>();
        HashMap<String, Object> headerTokenParams = null;

        serviceParams.put(Keys.EMAIL, email);

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.PasswordResetThroughEmail;

        new WebServicesVolleyTask(context, true, "Sending Email", serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, headerTokenParams, new AsyncResponseCallBack() {
            @Override
            public void onTaskComplete(TaskItem taskItem) {
                if (taskItem.isError()) {
                    gotoPreviousActivity(WebServiceUtils.getResponseMessage(taskItem), false);
                } else {
                    gotoPreviousActivity(getString(R.string.an_email_send), true);
                }
            }
        });
    }

    private void gotoPreviousActivity(String message, final boolean isSuccess) {
        AlertOP.showAlert(context, "", message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (isSuccess)
                    finish();

            }
        });
    }

    private void showConfirmationAlert() {
        KeyboardOp.hide(context, etEmail);
        AlertOP.showAlert(context, getString(R.string.reset),
                getString(R.string.an_email_send),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }

    //when click on back button
    @Override
    public boolean onSupportNavigateUp() {
        KeyboardOp.hide(context, etEmail);
        onBackPressed();
        return true;
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.forgot_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable icon = getResources().getDrawable(R.drawable.ic_back_white);
        icon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationIcon(icon);
    }

}//main
