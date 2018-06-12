package app.com.dunkeydelivery.utils;

import app.com.dunkeydelivery.modules.filter.pager.items.FilterItem;
import app.com.dunkeydelivery.utils.sharedprefs.ObjectSharedPreference;

/**
 * Created by Developer on 1/31/2018.
 */

public class FiltersOP {

    public static FilterItem getFilters(String filterType) {
        FilterItem filterObject = ObjectSharedPreference.getFilters(filterType);
        if (filterObject != null) {
            return filterObject;
        }
        return null;
    }

    public static void addFilter(String filterType, FilterItem filterObject) {
        savedFilter(filterType, filterObject);
    }

    private static void savedFilter(String filterType, FilterItem filterObject) {
        ObjectSharedPreference.saveFilter(filterType, filterObject);
    }

    public static String getRawFilter(String filterType) {
        return ObjectSharedPreference.getRawFilter(filterType);
    }

    public static void clearFilter() {
        ObjectSharedPreference.clearFilters();
    }

    public static void clearFilter(String filterType) {
        ObjectSharedPreference.clearFilters(filterType);
    }

}