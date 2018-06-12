package app.com.dunkeydelivery.Firebase;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.NotificationUtils.GenerateNotification;
import app.com.dunkeydelivery.utils.LogUtils;
import app.com.dunkeydelivery.utils.sharedprefs.SharedPref;
import app.com.dunkeydelivery.utils.sharedprefs.UserSharedPreference;

/**
 * Created by Developer on 2/12/2018.
 */

public class FirebasePushNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(UserSharedPreference.readUserBO()!=null) {
            GenerateNotification.sendNotificationSimpleText(getApplicationContext(), remoteMessage.getData().get("Message"), remoteMessage.getData().get("EntityId"), "", "");
        }
    }
}
