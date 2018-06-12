package app.com.dunkeydelivery.utils;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;


/**
 * Created by Developer on 7/4/2017.
 */

public class MiscUtils {

    public static void callPhone(Context context, String phoneNumber,Fragment fragment) {

        MarshMallowPermission marshMallowPermission=new MarshMallowPermission((Activity)context);

        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber.trim()));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                marshMallowPermission.requestPermissionForPhone(1,fragment);
                //return;
            }
            else {
                context.startActivity(intent);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
