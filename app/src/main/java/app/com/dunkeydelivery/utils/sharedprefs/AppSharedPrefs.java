package app.com.dunkeydelivery.utils.sharedprefs;

/**
 * Created by Developer on 5/30/2017.
 */

public class AppSharedPrefs {

    public static boolean readIsRefresh() {
        return (SharedPref.read("isRefresh", false));
    }

    public static void saveIsRefresh(boolean value) {
        SharedPref.save("isRefresh", value);
    }

    public static boolean readIsFriendListRefresh() {
        return (SharedPref.read("isRefreshFriend", false));
    }

    public static void saveIsFriendListRefresh(boolean value) {
        SharedPref.save("isRefreshFriend", value);
    }


    public static boolean readIsFromBroadCast() {
        return (SharedPref.read("isFromBroadcast", false));
    }

    public static void saveIsFromBroadCast(boolean value) {
        SharedPref.save("isFromBroadcast", value);
    }

    public static String readRegToken() {
        return (SharedPref.read("RegToken", ""));
    }

    public static void saveRegToken(String value) {
        SharedPref.save("RegToken", value);
    }


    public static String readDeviceID() {
        return (SharedPref.read("DeviceID", ""));
    }

    public static void saveDeviceID(String value) {
        SharedPref.save("DeviceID", value);
    }

}
