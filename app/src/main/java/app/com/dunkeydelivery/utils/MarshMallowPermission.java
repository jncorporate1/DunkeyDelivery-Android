package app.com.dunkeydelivery.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.R;

public class MarshMallowPermission {
    private Activity activity;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 5;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissionForExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionForExternalStorage(int requestCode, Fragment fragment) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertOP.showAlert(activity, "Alert", "External Storage permission needed. Please allow in App Settings for additional functionality.");
        } else {
            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionForExternalStorage(int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertOP.showAlert(activity, "Alert", "External Storage permission needed. Please allow in App Settings for additional functionality.");
        } else {
            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionForExternalStorageAndCamera(int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertOP.showAlert(activity, "Alert", "External Storage permission needed. Please allow in App Settings for additional functionality.");
        } else {
            activity.requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionForExternalLocation(int requestCode, DialogInterface.OnClickListener onClickListener) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertOP.showAlert(activity, "Alert",
                    "Location permission needed. Please allow in App Settings for additional functionality.",
                    onClickListener);
        } else {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    requestCode);
        }
    }

    //permission for Phone

    public boolean checkPermissionForPhone() {
        int result = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionForPhone(int requestCode, Fragment fragment) {
        //fragment.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},requestCode);
//                    requestCode);
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CALL_PHONE)) {
            AlertOP.showAlert(activity, "Alert", "Phone call permission needed. Please allow in App Settings for additional functionality.");
        } else {
            fragment.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    requestCode);
        }
    }

    //******* Methods for Request Location Permission ***********//
    public static boolean checkPermissionForLocation() {
        if ((ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(App.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPermissionForLocation(Activity activity, Fragment fragment) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            AlertOP.showAlert(activity, "Alert", "Locations permission needed. Please allow in App Settings for additional functionality.");
//            String title = activity.getString(R.string.enable_location);
//            String msg = activity.getResources().getString(R.string.enable_location_msg);
//            String pBtnText = activity.getResources().getString(R.string.goto_setting);
//            String nBtnText = activity.getResources().getString(R.string.cancel);
//            int buttonColor = ContextCompat.getColor(App.context, R.color.green);
//            showSettingsAlert(activity, title, msg, pBtnText, nBtnText, buttonColor);
        } else {
            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

}