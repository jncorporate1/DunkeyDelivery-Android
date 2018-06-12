package app.com.dunkeydelivery.activities.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import app.com.dunkeydelivery.BuildConfig;
import app.com.dunkeydelivery.Constants;
import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.activities.MainActivity;
import app.com.dunkeydelivery.activities.auth.dialogs.PictureBSDialogFragment;
import app.com.dunkeydelivery.interfaces.AsyncResponseCallBack;
import app.com.dunkeydelivery.items.TaskItem;
import app.com.dunkeydelivery.items.UserBO;
import app.com.dunkeydelivery.tasks.WebServicesVolleyTask;
import app.com.dunkeydelivery.utils.AlertOP;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.ImageUtils;
import app.com.dunkeydelivery.utils.KeyboardOp;
import app.com.dunkeydelivery.utils.Keys;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.MarshMallowPermission;
import app.com.dunkeydelivery.utils.RealPathUtil;
import app.com.dunkeydelivery.utils.Validation;
import app.com.dunkeydelivery.utils.WebServiceUtils;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private ProgressDialog progressDialog;

    // UI references.
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.imv_user)
    ImageView imvUser;

    @BindView(R.id.et_last_name)
    EditText etLastName;

    @BindView(R.id.et_first_name)
    EditText etFirstName;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirm_password)
    EditText etConfirmPaswd;

    @BindView(R.id.btn_signup)
    Button btnSignup;

    @BindView(R.id.ll_gmail_login)
    LinearLayout llGmailLogin;

    @BindView(R.id.ll_fb_login)
    LinearLayout llFbLogin;


    private Uri outputUri;
    private Uri outputFileUri;
    private String filePath;
    public static final int IMAGE_CAPTURE = 101;
    public static final int IMAGE_LIBRARY = 102;

    public static final int IMAGE_CAPTURE_REQUEST_CODE = 202;
    public static final int CHOOSE_PHOTO_VIDEO_REQUEST_CODE = 204;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = SignUpActivity.this;

        ButterKnife.bind(this);

        setToolbar();

        setListeners();

    }//onCreate

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.sign_up));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable icon = getResources().getDrawable(R.drawable.ic_back_white);
        icon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationIcon(icon);
    }

    private void setListeners() {
        btnSignup.setOnClickListener(this);
        imvUser.setOnClickListener(this);
        llFbLogin.setOnClickListener(this);
        llGmailLogin.setOnClickListener(this);
        etConfirmPaswd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    attemptSignUp();
                }
                return false;
            }
        });
    }

    //when click on back button
    @Override
    public boolean onSupportNavigateUp() {
        KeyboardOp.hide(context, etFirstName);
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_signup:
                KeyboardOp.hide(context, etEmail);
                attemptSignUp();
                break;
            case R.id.imv_user:
                PictureBSDialogFragment.
                        newInstance().
                        show(((AppCompatActivity) context).getSupportFragmentManager(), "Settings Bottom Sheet");
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


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignUp() {

        // Reset errors.
        etFirstName.setError(null);
        etLastName.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etConfirmPaswd.setError(null);

        // Store values at the time of the SIGNUP attempt.
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPaswd.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check if confirm password is empty.
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPaswd.setError(getString(R.string.error_field_required));
            focusView = etConfirmPaswd;
            cancel = true;
        }

        // Check if password is empty.
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView = etPassword;
            cancel = true;
        }

        // Check if password is empty.
        if (!cancel) {
            if (!password.equals(confirmPassword)) {
                etConfirmPaswd.setError(getString(R.string.error_passwords_not_match));
                focusView = etConfirmPaswd;
                cancel = true;
            } else if (!Validation.isPasswordValid(password)) {
                etPassword.setError(getString(R.string.error_invalid_password));
                focusView = etPassword;
                cancel = true;
            }

        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!Validation.isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        // Check if lastname is empty.
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError(getString(R.string.error_field_required));
            focusView = etLastName;
            cancel = true;
        }

        // Check if firstname is empty.
        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError(getString(R.string.error_field_required));
            focusView = etFirstName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (filePath != null) {
                callSignUpServiceWithImage(firstName, lastName, email, password);
            } else {
                callSignupService(firstName, lastName, email, password);
            }
        }
    }


    //this method is used to get states from service and saved in Prefs...
    private void callSignupService(String fName, String lName, String email, String password) {

        HashMap<String, Object> serviceParams = new HashMap<String, Object>();
        HashMap<String, Object> tokenServiceHeaderParams = null;

//        serviceParams.put(Keys.FULLNAME, userName);
        serviceParams.put(Keys.FIRST_NAME, fName);
        serviceParams.put(Keys.LAST_NAME, lName);
        serviceParams.put(Keys.EMAIL, email);
        serviceParams.put(Keys.PASSWORD, password);
        serviceParams.put(Keys.CONFIRM_PASSWORD, password);
        serviceParams.put(Keys.PHONE, "03455083493");
        serviceParams.put(Keys.ROLE, "0");

        new WebServicesVolleyTask(context, true, "",
                EnumUtils.ServiceName.Register, EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.Register),
                EnumUtils.RequestMethod.POST, serviceParams, tokenServiceHeaderParams, new AsyncResponseCallBack() {

            @Override
            public void onTaskComplete(TaskItem taskItem) {

                if (taskItem != null) {
                    KeyboardOp.hide(context, etEmail);
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
    }//end of GetStatesService method


    private void callSignUpServiceWithImage(String fName, String lName, String email, String password) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        String serverUrl = "";
        if (Constants.appDomain == EnumUtils.AppDomain.LIVE) {
            serverUrl = context.getResources().getString(R.string.base_url_live);
        } else if (Constants.appDomain == EnumUtils.AppDomain.QA) {
            serverUrl = context.getResources().getString(R.string.base_url_qa);
        } else if (Constants.appDomain == EnumUtils.AppDomain.DEV) {
            serverUrl = context.getResources().getString(R.string.base_url_dev);
        }

        File file = null;
        if (filePath != null) {
            //if image is attached...
            file = new File(filePath);
            serverUrl = serverUrl + EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.RegisterWithImage);
        } else {
            //if image is not attached...
            serverUrl = serverUrl + EnumUtils.ServiceName.getServicePath(EnumUtils.ServiceName.Register);
        }

        Builders.Any.B builder = Ion.with(context).load("POST", serverUrl);
        builder.setMultipartParameter(Keys.EMAIL, email);
        builder.setMultipartParameter(Keys.FIRST_NAME, fName);
        builder.setMultipartParameter(Keys.LAST_NAME, lName);
        builder.setMultipartParameter(Keys.PASSWORD, password);
        builder.setMultipartParameter(Keys.CONFIRM_PASSWORD, password);
        builder.setMultipartParameter(Keys.PHONE, "12345");
        builder.setMultipartParameter(Keys.ROLE, "0");


        //check if image is attached then send file...
        if (file != null) {
            builder.setMultipartContentType("multipart/form-data");
            builder.setMultipartFile(Keys.FILE, "image/jpeg", file);
        }


        builder.asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            TaskItem taskItem = WebServiceUtils.parseResponse(result.toString(),
                                    EnumUtils.ServiceName.RegisterWithImage, false);
                            if (taskItem.getResponse() != null) {
                                JSONObject jsonObject = null;
                                try {

                                    if (taskItem.isError()) {
                                        AlertOP.showAlert(context, null, WebServiceUtils.getResponseMessage(taskItem));
                                    } else {

                                        jsonObject = new JSONObject(taskItem.getResponse());
                                        Gson gson = new Gson();
                                        UserBO userBO = gson.fromJson(jsonObject.toString(), UserBO.class);

                                        if (userBO != null) {
                                            LogUtils.i("", "onTaskComplete: " + jsonObject.toString());
                                            UserSharedPreference.saveIsUserLoggedIn(true);
                                            UserSharedPreference.saveUserToken(userBO.getToken());
                                            UserSharedPreference.saveUserBO(userBO);
                                            Intent intent = new Intent(context, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }


                        }
                    }
                });
    }

    //******************************* Methods used for take/pick picture ***********************//
    public void capturePhoto() {
        try {
            MarshMallowPermission permission = new MarshMallowPermission(SignUpActivity.this);
            if (permission.checkPermissionForExternalStorage()) {
                final String timeStamp = Calendar.getInstance().getTimeInMillis() + "";
                if (hasCamera()) {
                    String newFileName = "UserImage_" + timeStamp + ".jpg";
                    File newFile = new File(android.os.Environment.getExternalStorageDirectory(), newFileName);
                    if (SDK_INT >= N) {
                        outputUri = FileProvider.getUriForFile(context,
                                BuildConfig.APPLICATION_ID + ".provider",
                                newFile);
                    } else {
                        outputUri = Uri.fromFile(newFile);
                    }
                    outputFileUri = Uri.fromFile(newFile);
                    filePath = outputFileUri.getPath();

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                    startActivityForResult(intent, IMAGE_CAPTURE);
                }
            } else {
                permission.requestPermissionForExternalStorageAndCamera(IMAGE_CAPTURE_REQUEST_CODE);
            }
        }catch (Exception e){
            LogUtils.i("mess",""+e.toString());
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT);
    }

    public void choosePhotoVideoFromLibrary() {
        MarshMallowPermission permission = new MarshMallowPermission(SignUpActivity.this);
        if (permission.checkPermissionForExternalStorage()) {
            if (SDK_INT < 19) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, IMAGE_LIBRARY);
            } else {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                String[] mimetypes = {"image/*"};
                photoPickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                startActivityForResult(photoPickerIntent, IMAGE_LIBRARY);
            }
        } else {
            permission.requestPermissionForExternalStorage(CHOOSE_PHOTO_VIDEO_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case IMAGE_LIBRARY:
                if (resultCode == RESULT_OK) {
                    Intent data1 = new Intent();
                    Uri uri = data.getData();
                    if (Build.VERSION.SDK_INT < 11)
                        filePath = RealPathUtil.getRealPathFromURI_BelowAPI11(context, data.getData());

                        // SDK >= 11 && SDK < 19
                    else if (Build.VERSION.SDK_INT < 19)
                        filePath = RealPathUtil.getRealPathFromURI_API11to18(context, data.getData());

                        // SDK > 19 (Android 4.4)
                    else
                        filePath = RealPathUtil.getRealPathFromURI_API19(context, data.getData());

                    try {
                        final int takeFlags = data.getFlags()
                                & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        context.getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ImageUtils.setImageCentered(context, uri, imvUser, R.drawable.icon_user);
                }
                break;
            case IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    ImageUtils.setImageCentered(context, outputFileUri, imvUser, R.drawable.icon_user);
                }
                break;
            case AuthorizeActivity.AUTHORIZE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    int id = data.getIntExtra(AuthorizeActivity.ARG_PARAM, 0);
                    EnumUtils.Authentications authentications = EnumUtils.Authentications.getAuthentication(id);
                    switch (authentications) {
                        case FACEBOOK:
                            String facebookUserID = data.getStringExtra(AuthorizeActivity.FACEBOOK_USER_ID);
                            String facebookToken = data.getStringExtra(AuthorizeActivity.FACEBOOK_TOKEN_CODE);
                            callSocialSignUpService(facebookUserID, facebookToken);
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
                break;
        }

    }

    private boolean areAllPermissionGranted(@NonNull int[] grantResults) {
        boolean checkPermissionsGranted=false;
        if (grantResults.length <= 0)
            return checkPermissionsGranted;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                checkPermissionsGranted = false;
            } else
            {
                checkPermissionsGranted=true;
            }
        }
        return checkPermissionsGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (areAllPermissionGranted(grantResults)) {
            switch (requestCode) {
                case CHOOSE_PHOTO_VIDEO_REQUEST_CODE:
                    choosePhotoVideoFromLibrary();
                    break;
                case IMAGE_CAPTURE_REQUEST_CODE:
                    capturePhoto();
                    break;
            }
        } else {
            //TODO: Show some message that permission is not granted
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

    private void callSocialSignUpService(String facebookUserID, String facebookToken) {
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
}//main
