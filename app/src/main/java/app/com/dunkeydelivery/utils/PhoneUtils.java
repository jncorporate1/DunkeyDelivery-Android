package app.com.dunkeydelivery.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Developer on 2/26/2018.
 */

public class PhoneUtils {


    public static String getDeviceName() {
        try {
            return android.os.Build.MODEL;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getDeviceID(Context context) {
        try {
            String deviceID = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            return deviceID;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
