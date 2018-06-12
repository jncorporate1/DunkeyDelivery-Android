package app.com.dunkeydelivery.Firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;

/**
 * Created by Developer on 2/12/2018.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
       FirebaseInstanceId.getInstance().getToken();
    }
}
