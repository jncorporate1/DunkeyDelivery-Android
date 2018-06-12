package app.com.dunkeydelivery.utils.sharedprefs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.modules.filter.pager.Filter;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.modules.cart.fragments.items.CartStore;
import app.com.dunkeydelivery.utils.Keys;

/**
 * Created by Developer on 5/30/2017.
 */

public class ObjectSharedPreference {


    public static void saveObject(Object object, String key) {
        final Gson gson = new Gson();
        if (object == null) {
            SharedPref.save(key, "");
        } else {
            String serializedObject = gson.toJson(object);
            SharedPref.save(key, serializedObject);
        }

    }

    public static void saveFilterProductSizes(String saveFilterProductSizes) {
        SharedPref.saveFilterProductSizes(saveFilterProductSizes, "saveFilterProductSizes");
    }

    public static String getFilterProductSizes()
    {
        return SharedPref.getFilterProductSizes("saveFilterProductSizes");
    }

    public static <GenericClass> GenericClass getObject(Class<GenericClass> classType, String key) {
        String data = SharedPref.read(key, null);
        if (data != null) {
            final Gson gson = new Gson();
            return gson.fromJson(data, classType);
        }
        return null;
    }

    public static void saveCart(ArrayList<CartStore> cartOP) {
        Gson gson = new Gson();
        String serilizedArray = gson.toJson(cartOP);
        SharedPref.save("cart", serilizedArray);
    }

    public static void clearCart() {
        SharedPref.save("cart", "");
    }

    private boolean isCartRead() {
        if (SharedPref.read("cart", null) != null) {
            return true;
        }
        return false;
    }

    public static ArrayList<CartStore> getCart() {
        String serializedArray = SharedPref.read("cart", "");
        if (!serializedArray.equals("")) {
            final Gson gson = new Gson();
            Type tokenType = new TypeToken<List<CartStore>>() {
            }.getType();
            ArrayList<CartStore> cartStore = gson.fromJson(serializedArray, tokenType);
            return cartStore;
        }
        return new ArrayList<CartStore>();
    }

    public static String getRawCart() {
        return SharedPref.read("cart", "");
    }

    public static FilterItem getFilters(String filterType) {
        String response = getRawFilter(filterType);
        if (response.equals("")) {
            return null;
        }
        Gson gson = new Gson();
        FilterItem filterItem = gson.fromJson(response, FilterItem.class);
        return filterItem;
    }

    public static void saveFilter(String filterType, FilterItem filterObject) {
        ObjectSharedPreference.saveObject(filterObject, filterType);
    }

    public static String getRawFilter(String filterType) {
        String filter = SharedPref.read(filterType, "");
        return filter;
    }

    public static void clearFilters() {
        SharedPref.save(Keys.Filter_FOOD, "");
        SharedPref.save(Keys.Filter_ALCOHOL, "");
        SharedPref.save(Keys.Filter_OTHER, "");
    }

    public static void clearFilters(String filterType) {
        SharedPref.save(filterType, "");
    }
}
