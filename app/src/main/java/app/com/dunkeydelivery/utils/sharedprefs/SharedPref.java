package app.com.dunkeydelivery.utils.sharedprefs;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import app.com.dunkeydelivery.App;
import app.com.dunkeydelivery.modules.filter.pager.items.FilterProductSizes;
import app.com.dunkeydelivery.utils.Keys;


public class SharedPref {

	public static void clearCache() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.clear();
		edit.commit();
	}

	public static void setCheckForFilterSizes(int value)
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt("stateValue",value);
		edit.commit();
	}

	public static int getCheckForFilterSizes()
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getInt("stateValue",0);
	}

	public static void saveProductSize(String productSize)
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString("size",productSize);
		edit.commit();
	}

	public static String getProductSize()
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getString("size",null);
	}

	public static void saveFilterProductSizes(String saveFilterProductSizes,String key)
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(key,saveFilterProductSizes);
		edit.commit();
	}

	public static String getFilterProductSizes(String getFilterProductSizes)
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getString(getFilterProductSizes,null);
	}

	public static String read(String valueKey, String valueDefault) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getString(valueKey, valueDefault);
	}

	public static void save(String valueKey, String value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(valueKey, value);
		edit.commit();
	}

	public static void saveDateAndTime(String dateKey, String dateValue,String timeKey, String timeValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(dateKey, dateValue);
		edit.putString(timeKey, timeValue);
		edit.commit();
	}

	public static void saveDeliveryTypes(String deliveryTypesKey,String deliveryTypes) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(deliveryTypesKey, deliveryTypes);
		edit.commit();
	}

	public static String readDate(String dateKey,String defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getString(dateKey,defaultValue);
	}

	public static String readDeliveryTypes(String deliveryTypesKey,String defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getString(deliveryTypesKey,defaultValue);
	}

	public static String readTime(String timeKey,String defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getString(timeKey,defaultValue);
	}

	public static int read(String valueKey, int valueDefault) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getInt(valueKey, valueDefault);
	}

	public static void save(String valueKey, int value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt(valueKey, value);
		edit.commit();
	}

	public static boolean read(String valueKey, boolean valueDefault) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getBoolean(valueKey, valueDefault);
	}

	public static void save(String valueKey, boolean value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(valueKey, value);
		edit.commit();
	}

	public static long read(String valueKey, long valueDefault) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getLong(valueKey, valueDefault);
	}

	public static void save(String valueKey, long value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putLong(valueKey, value);
		edit.commit();
	}

	public static float read(String valueKey, float valueDefault) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		return prefs.getFloat(valueKey, valueDefault);
	}

	public static void save(String valueKey, float value) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(App.context);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putFloat(valueKey, value);
		edit.commit();
	}

}
