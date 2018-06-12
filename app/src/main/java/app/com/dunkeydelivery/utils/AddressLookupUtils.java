package app.com.dunkeydelivery.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.dunkeydelivery.modules.search.items.AddressItem;

/**
 * Created by Developer on 7/20/2016.
 */
public class AddressLookupUtils {

    public static List<AddressItem> parseAddressResults(String response, boolean onlyShowValidAddresses) {
        List<AddressItem> addressItems = new ArrayList<>();
        if(response != null){
            try{
                JSONObject obj = new JSONObject(response);
                JSONArray array=obj.getJSONArray("results");
                for(int i=0;i<array.length();i++)
                {
                    AddressItem addressItem = new AddressItem();
                    JSONObject item = array.getJSONObject(i);
                    JSONArray jsonTblPoints = item.getJSONArray("address_components");
                    if(jsonTblPoints.length() > 0){
                        for(int j=0; j < jsonTblPoints.length(); j++)
                        {
                            JSONObject jsonTblPoint = jsonTblPoints.getJSONObject(j);

                            JSONArray typJsonArray = jsonTblPoint.getJSONArray("types");

                            if(typJsonArray.length() > 0){
                                for(int k=0; k < typJsonArray.length(); k++){

                                    String type = typJsonArray.getString(k);
                                    if(type.equals("street_number")){
                                        addressItem.setStreetAddress(jsonTblPoint.getString("long_name"));
                                        break;
                                    }

                                    if(type.equals("route")){
                                        addressItem.setAddressLine(jsonTblPoint.getString("long_name"));
                                        break;
                                    }

                                    if(type.equals("locality")){
                                        // City
                                        addressItem.setCity(jsonTblPoint.getString("long_name"));
                                        break;
                                    }

                                    if(type.equals("administrative_area_level_1")){
                                        // State
                                        addressItem.setState(jsonTblPoint.getString("long_name"));
                                        addressItem.setStateCode(jsonTblPoint.getString("short_name"));
                                        break;
                                    }

                                    if(type.equals("postal_code")){
                                        // Zip Code
                                        addressItem.setZipCode(jsonTblPoint.getString("long_name"));
                                        break;
                                    }

                                    if(type.equals("country")){
                                        // Country
                                        addressItem.setCountry(jsonTblPoint.getString("long_name"));
                                        addressItem.setCountryCode(jsonTblPoint.getString("short_name"));
                                        break;
                                    }
                                }//types loop
                            }
                        }//loop address components
                    }

                    //formatted_address is the complete address which contains city state and zip and country
                    addressItem.setFormattedAddress(item.getString("formatted_address"));
                    JSONObject geoJson = item.getJSONObject("geometry");
                    JSONObject locJson = geoJson.getJSONObject("location");
                    addressItem.setLatitude(Double.parseDouble(locJson.getString("lat")));
                    addressItem.setLongitude(Double.parseDouble(locJson.getString("lng")));



                    //check if add valid address
                    if(onlyShowValidAddresses){

                        //check if countryCode is empty or it is not equivalent to "US" or zip is empty then continue...
                        if(addressItem.getCountryCode() == null || addressItem.getCountryCode().isEmpty()
                                || !addressItem.getCountryCode().equals("US") || addressItem.getZipCode().isEmpty()){
                            continue;
                        }

                        //only add those addresses whose city and state is not empty
                        if((!addressItem.getCity().isEmpty()
                                || !addressItem.getState().isEmpty()) ){
                            addressItems.add(addressItem);
                        }
                    }else{
                        //else add all addresses
                        addressItems.add(addressItem);
                    }


                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return addressItems;
    }


    //******************* Methods used for making search relavant with in 15 mile radius *****************************//

    private static final double EARTHRADIUS = 6366198;
    private static final double DISTANCE_IN_METERS = 24141; //this is equavalent to 15 miles
    /**
     * Create a new LatLng which lies toNorth meters north and toEast meters
     * east of startLL
     */


    public static ArrayList<LatLng> createBoundsWithMinDiagonal(Location userLocation) {

        ArrayList<LatLng> points = new ArrayList<>();
        try{
            /** Add 2 points 1000m northEast and southWest of the center.
             * They increase the bounds only, if they are not already larger
             * than this.
             * 1000m on the diagonal translates into about 709m to each direction. */

            LatLng northEast = move(userLocation, DISTANCE_IN_METERS, DISTANCE_IN_METERS);
            LatLng southWest = move(userLocation, -DISTANCE_IN_METERS, -DISTANCE_IN_METERS);
            points.add(southWest);
            points.add(northEast);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return points;
    }


    private static LatLng move(Location startLL, double toNorth, double toEast) {
        double lonDiff = meterToLongitude(toEast, startLL.getLatitude());
        double latDiff = meterToLatitude(toNorth);
        return new LatLng(startLL.getLatitude() + latDiff, startLL.getLongitude()
                + lonDiff);
    }

    private static double meterToLongitude(double meterToEast, double latitude) {
        double latArc = Math.toRadians(latitude);
        double radius = Math.cos(latArc) * EARTHRADIUS;
        double rad = meterToEast / radius;
        return Math.toDegrees(rad);
    }


    private static double meterToLatitude(double meterToNorth) {
        double rad = meterToNorth / EARTHRADIUS;
        return Math.toDegrees(rad);
    }

    public static boolean isGpsEnable(Activity activity){
        if(activity != null){
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                return true;
            else
                return false;
        }
        return false;
    }

//    //this method is used to return API key that is used for geocoding service in address lookup...
//    public static String getGoogleAPIKey(){
//        String apiKey = Constants.APIKey;
//        SettingBO settingBO = App.getSettingBO();
//
//        //now check if settings come from server and api key also coming then set that api key....
//        //else it will pick the apikey from constants so, it will not stop the working of google api...
//        if(settingBO != null && !settingBO.getGoogleMapsAPIKey_Android().isEmpty()){
//            apiKey = settingBO.getGoogleMapsAPIKey_Android();
//        }
//        return apiKey;
//    }

}//main
