package app.com.dunkeydelivery.activities.auth;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Arrays;
import java.util.HashMap;

import app.com.dunkeydelivery.R;
import app.com.dunkeydelivery.utils.EnumUtils;
import app.com.dunkeydelivery.utils.LogUtils;
import butterknife.ButterKnife;

//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.twitter.sdk.android.core.Callback;
//import com.twitter.sdk.android.core.Result;
//import com.twitter.sdk.android.core.Twitter;
//import com.twitter.sdk.android.core.TwitterAuthConfig;
//import com.twitter.sdk.android.core.TwitterAuthToken;
//import com.twitter.sdk.android.core.TwitterCore;
//import com.twitter.sdk.android.core.TwitterException;
//import com.twitter.sdk.android.core.TwitterSession;
//import com.twitter.sdk.android.core.identity.TwitterLoginButton;
//import app.com.streamix.R;
//import app.com.streamix.utilities.EnumUtils.Authentications;

public class AuthorizeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final int AUTHORIZE_REQUEST_CODE = 10;
    public static final String FACEBOOK_USER_ID = "facebookUserID";
    public static final String FACEBOOK_TOKEN_CODE = "facebookTokenCode";
    public static final String GMAIL_FIRST_NAME = "GMAIL_FIRST_NAME";
    public static final String GMAIL_SECOND_NAME = "GMAIL_SECOND_NAME";
    public static final String GMAIL_FULLNAME = "GMAIL_FULLNAME";
    public static final String GMAIL_IMAGE_URL = "GMAIL_IMAGE_URL";
    public static final String GMAIL_EMAIL = "GMAIL_EMAIL";
    private static final String TAG = "SocialLogin";

    //fb callback
    private CallbackManager callbackManager;
    //google
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;

    EnumUtils.Authentications authentications;
    public static final String ARG_PARAM = "authenticationFrom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.activity_authorize);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            int id = getIntent().getIntExtra(ARG_PARAM, 0);
            authentications = EnumUtils.Authentications.getAuthentication(id);
        }
        checkAuthenticationType();
    }//onCreate


    private void checkAuthenticationType() {
        switch (authentications) {
            case FACEBOOK:
                setFacebookLoginCallBack();
                //fbLoginButton.performClick();
                break;
            case GMAIL:
                setGoogleLoginCallBack();
                callGoogleSignIn();
                //googleLoginButton.performClick();
                break;
        }
    }

    //************************************************************Google Login***********************************************//

    //set google login  config
    private void setGoogleLoginCallBack() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_server_client_key))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    //google signin intent request
    void callGoogleSignIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //receive google signin result
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String token = acct.getIdToken();
            Intent intent = new Intent();
            intent.putExtra(GMAIL_FIRST_NAME, acct.getGivenName().trim());
            intent.putExtra(GMAIL_SECOND_NAME, acct.getFamilyName().trim());
            intent.putExtra(GMAIL_FULLNAME, acct.getDisplayName().trim());
            intent.putExtra(GMAIL_IMAGE_URL, acct.getPhotoUrl().toString());
            intent.putExtra(GMAIL_EMAIL, acct.getEmail().trim());
            intent.putExtra(AuthorizeActivity.ARG_PARAM, EnumUtils.Authentications.GMAIL.ordinal());
            setResult(Activity.RESULT_OK, intent);
            //showToast(acct.getDisplayName()+"\n"+acct.getFamilyName()+"\n"+acct.getGivenName()+"\n"+acct.getPhotoUrl());
            Log.i("", "Google success: " + token);
            signOut();
            finish();
        } else if (!result.isSuccess()) {
            LogUtils.i("mess", "error  " + result.getStatus().getStatusCode());
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    //call google logout
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.i("status", "" + status);
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LogUtils.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    //************************************************************Facebook***********************************************//

    //setup facebook login
    private void setFacebookLoginCallBack() {
        try {
            FacebookSdk.sdkInitialize(AuthorizeActivity.this);
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().logInWithReadPermissions(AuthorizeActivity.this, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(AuthorizeActivity.ARG_PARAM, EnumUtils.Authentications.FACEBOOK.ordinal());
                    resultIntent.putExtra(FACEBOOK_USER_ID, loginResult.getAccessToken().getUserId());
                    resultIntent.putExtra(FACEBOOK_TOKEN_CODE, loginResult.getAccessToken().getToken());
                    setResult(Activity.RESULT_OK, resultIntent);
                    LogOutFromFB();
                    finish();
                }

                @Override
                public void onCancel() {
                    finish();
                }

                @Override
                public void onError(FacebookException error) {
                    if (error instanceof FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                    }
                    finish();
                }
            });
        } catch (Exception error) {
            error.printStackTrace();
            finish();
        }
    }

    //facebook logout
    public void LogOutFromFB() {
        LoginManager.getInstance().logOut();
    }

    //************************************************************Twitter***********************************************//


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}//main
