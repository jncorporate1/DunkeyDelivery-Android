package app.com.dunkeydelivery.utils.sharedprefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.items.DeliveryTypes;
import app.com.dunkeydelivery.items.TokenBO;
import app.com.dunkeydelivery.items.UserBO;

/**
 * Created by Developer on 5/30/2017.
 */

public class UserSharedPreference {

    public static boolean readIsUserLoggedIn() {
        return (SharedPref.read("isUserLogin", false));
    }

    public static void saveIsUserLoggedIn(boolean value) {
        SharedPref.save("isUserLogin", value);
    }

    //Save and read userToken

//    public static String readUserToken() {
//        return (SharedPref.read("userToken", ""));
//    }

    public static void saveUserToken(TokenBO token) {
        ObjectSharedPreference.saveObject(token, "userToken");
    }

    public static void saveFilterProductSizes(String filterProductSizes) {
        ObjectSharedPreference.saveFilterProductSizes(filterProductSizes);
    }

    public static String getFilterProductSizes() {
        return ObjectSharedPreference.getFilterProductSizes();
    }

    public static TokenBO readUserToken() {
        return ObjectSharedPreference.getObject(TokenBO.class, "userToken");
    }

    public static String readUserLat() {
        return (SharedPref.read(KeysSharedPrefs.USER_LAT, ""));
    }

    public static String readUserLng() {
        return (SharedPref.read(KeysSharedPrefs.USER_LNG, ""));
    }

    public static void saveUserLocation(String lat, String lng) {
        SharedPref.save(KeysSharedPrefs.USER_LAT, lat);
        SharedPref.save(KeysSharedPrefs.USER_LNG, lng);
    }

    public static void saveDateAndTime(String date, String time) {
        SharedPref.saveDateAndTime(KeysSharedPrefs.SELECTED_DATE,date,KeysSharedPrefs.SELECTED_TIME,time);
    }

    public static void saveDeliveryTypes(String deliveryTypes) {
        SharedPref.saveDeliveryTypes(KeysSharedPrefs.DELIVERY_TYPES,deliveryTypes);
    }

    public static String readDate(String dateKey, String defaultValue) {
        return SharedPref.readDate(KeysSharedPrefs.SELECTED_DATE,defaultValue);
    }

    public static ArrayList<DeliveryTypes> readDeliveryTypes() {
        Gson gson=new Gson();
        Type type=new TypeToken<List<DeliveryTypes>>(){}.getType();
        return gson.fromJson(SharedPref.readDeliveryTypes(KeysSharedPrefs.DELIVERY_TYPES,null).toString(),type);
    }

    public static String readTime(String dateKey, String defaultValue) {
        return SharedPref.readDate(KeysSharedPrefs.SELECTED_TIME,defaultValue);
    }

    public static void saveUserBO(UserBO userBO) {
        ObjectSharedPreference.saveObject(userBO, KeysSharedPrefs.USER_INFO_KEY);
    }

    public static UserBO readUserBO() {
        return ObjectSharedPreference.getObject(UserBO.class, KeysSharedPrefs.USER_INFO_KEY);
    }


}
