package app.com.dunkeydelivery.activities.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.MainActivity;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.items.UserBO;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.Validation;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {


    private Context context;

    // UI references.
    @BindView(R.id.et_email)
    EditText mEmailView;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.tv_reset_paswd)
    TextView tvResetPswd;

    @BindView(R.id.tv_create_account)
    TextView tvCreateAccount;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.ll_gmail_login)
    LinearLayout llGmailLogin;

    @BindView(R.id.ll_fb_login)
    LinearLayout llFbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        ButterKnife.bind(this);

        //generating hash key for facebook
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    attemptLogin();
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
        tvResetPswd.setOnClickListener(this);
        llFbLogin.setOnClickListener(this);
        llGmailLogin.setOnClickListener(this);

    }//onCreate


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

//         Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView = etPassword;
            cancel = true;
        }

//         Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Validation.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            //There was an error; don't attempt login and focus the first
            //form field with an error.
            focusView.requestFocus();
        } else {
//             Show a progress spinner, and kick off a background task to
//             perform the user login attempt.
            callLoginService(email, password);
        }
        ;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                KeyboardOp.hide(context, etPassword);
                attemptLogin();
                break;
            case R.id.tv_create_account:
                startActivity(new Intent(context, SignUpActivity.class));
                break;
            case R.id.tv_reset_paswd:
                startActivity(new Intent(context, ForgetPassword.class));
                break;
            case R.id.ll_fb_login:
                Intent facebookLogin = new Intent(context, AuthorizeActivity.class);
                facebookLogin.putExtra(AuthorizeActivity.ARG_PARAM, EnumUtils.Authentications.FACEBOOK.ordinal());
                startActivityForResult(facebookLogin, AuthorizeActivity.AUTHORIZE_REQUEST_CODE);
                break;
            case R.id.ll_gmail_login:
                Intent gmailLogin = new Intent(context, AuthorizeActivity.class);
                gmailLogin.putExtra(AuthorizeActivity.ARG_PARAM, EnumUtils.Authentications.GMAIL.ordinal());
                startActivityForResult(gmailLogin, AuthorizeActivity.AUTHORIZE_REQUEST_CODE);
                break;
        }
    }


    //this method is used to get user from service and saved in Prefs...
    private void callLoginService(String email, String password) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;

        serviceParams.put(Keys.EMAIL, email);
        serviceParams.put(Keys.PASSWORD, password);

        new WebServicesVolleyTask(context, true, "",
                EnumUtils.ServiceName.login, EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.login),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context, mEmailView);
                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
                                UserBO userBO = gson.fromJson(jsonObject.toString(), UserBO.class);

                                if (userBO != null) {
                                    UserSharedPreference.saveIsUserLoggedIn(true);
                                    UserSharedPreference.saveUserToken(userBO.getToken());
                                    UserSharedPreference.saveUserBO(userBO);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        // if response is successful then do something
                    }
                }
            }
        });
    }//end of login method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == AuthorizeActivity.AUTHORIZE_REQUEST_CODE) {

//
                if (resultCode == RESULT_OK) {
                    int id = data.getIntExtra(AuthorizeActivity.ARG_PARAM, 0);
                    EnumUtils.Authentications authentications = EnumUtils.Authentications.getAuthentication(id);
                    switch (authentications) {
                        case FACEBOOK:
                            String facebookUserID = data.getStringExtra(AuthorizeActivity.FACEBOOK_USER_ID);
                            String facebookToken = data.getStringExtra(AuthorizeActivity.FACEBOOK_TOKEN_CODE);
                            callSocialLoginService(facebookUserID, facebookToken);
                            break;
                        case GMAIL:
                            String gmailFullName = data.getStringExtra(AuthorizeActivity.GMAIL_FULLNAME);
                            String gmailFirstName=data.getStringExtra(AuthorizeActivity.GMAIL_FIRST_NAME);
                            String gmailLastName=data.getStringExtra(AuthorizeActivity.GMAIL_SECOND_NAME);
                            String gmailImageUrl=data.getStringExtra(AuthorizeActivity.GMAIL_IMAGE_URL);
                            String gmailEmail=data.getStringExtra(AuthorizeActivity.GMAIL_EMAIL);
                            callSocialLoginServiceGmail(gmailFullName, gmailFirstName,gmailLastName,gmailImageUrl,gmailEmail);
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    //               cancel or error
                }
            }
        } catch (Exception e) {
        }
    }


    private void callSocialLoginServiceGmail(String fullName, String firstName,String lastName,String imgUrl,String email) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;


        serviceParams.put(Keys.FULLNAME, fullName.trim());
        serviceParams.put(Keys.FIRST_NAME, firstName.trim());
        serviceParams.put(Keys.LAST_NAME, lastName.trim());
        serviceParams.put(Keys.ImageUrl, imgUrl.trim());
        serviceParams.put(Keys.EMAIL, email.trim());
        serviceParams.put(Keys.GMAIL_SocialLoginType, EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Gmail));

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.RegisterWithGmail;

        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context, etPassword);
                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
                                UserBO userBO = gson.fromJson(jsonObject.toString(), UserBO.class);

                                if (userBO != null) {
                                    UserSharedPreference.saveIsUserLoggedIn(true);
                                    UserSharedPreference.saveUserToken(userBO.getToken());
                                    UserSharedPreference.saveUserBO(userBO);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
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

    private void callSocialLoginService(String facebookUserID, String facebookToken) {
        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;


        serviceParams.put(Keys.FB_Id, facebookUserID.trim());
        serviceParams.put(Keys.FB_AccessToken, facebookToken.trim());
        serviceParams.put(Keys.FB_SocialLoginType, EnumUtils.SignInType.getSignInType(EnumUtils.SignInType.Facebook));

        EnumUtils.ServiceName serviceName = EnumUtils.ServiceName.ExternalLogin;

        new WebServicesVolleyTask(context, true, "",
                serviceName, EnumUtils.ServiceName.getServicePath(serviceName),
                EnumUtils.RequestMethod.GET, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context, etPassword);
                    if (taskItem.isError()) {
                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                    } else {
                        try {

                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = new JSONObject(taskItem.getResponse());
                                Gson gson = new Gson();
                                UserBO userBO = gson.fromJson(jsonObject.toString(), UserBO.class);

                                if (userBO != null) {
                                    UserSharedPreference.saveIsUserLoggedIn(true);
                                    UserSharedPreference.saveUserToken(userBO.getToken());
                                    UserSharedPreference.saveUserBO(userBO);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
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

}

