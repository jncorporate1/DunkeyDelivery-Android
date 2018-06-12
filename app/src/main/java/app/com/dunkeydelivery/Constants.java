package app.com.dunkeydelivery;

import app.com.dunkeydelivery.utils.EnumUtils;

public class Constants {

    public static final EnumUtils.AppDomain appDomain = EnumUtils.AppDomain.LIVE;
    //
    public static final String DEFAULT_FONT_NAME = "Roboto-Regular_1B.ttf";
    public static final String Montserrat_FONT_NAME = "Montserrat-Regular_0.otf";
    public static final String DEFAULT_FONT_NAME_FOR_ET = "Roboto-Regular_1B.ttf"; // font majorly used in edittext

    public static final String dateFormat0 = "MM/dd/yyyy";
    public static final String dateFormat1 = "EEE, MMM dd";
    public static final String dateFormat2 = "EEE, MMM dd - hh:mm a";
    public static final String dateFormat3 = "MMM dd, yyyy";
    public static final String dateFormat4 = "hh:mm a";
    public static final String dateFormat5 = "M-d-yyyy";
    public static final String dateFormat6 = "dd/MM/yyyy";
    public static final String dateFormat7 = "MM/dd/yyyy hh:mm:ss a";
    public static final String dateFormat8 = "yyyyMMdd-HHmmssSSS";
    public static final String dateFormat9 = "h:mm a";
    public static final String dateFormat10 = "HH:mm:ss";
    public static final String dateFormat30 = "HH:mm";
    public static final String dateFormat11 = "ddMMyyyyHHmmss";
    public static final String dateFormat12 = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormat13 = "MM/dd/yyyy hh:mm a";
    public static final String dateFormat14 = "yyyy";
    public static final String dateFormat15 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String dateFormat16 = "yyyy-MM-dd";
    public static final String dateFormat17 = "EE, MMM dd";
    public static final String dateFormat18 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String dateFormat19 = "EEEE, MMMM dd, yyyy";
    public static final String dateFormat20 = "MM/dd";
    public static final String dateFormat21 = "MMMM dd, yyyy";
    public static final String dateFormat22 = "MM/yy";
    public static final String dateFormat23 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String dateFormat24 = "MMMM dd";
    public static final String dateFormat25 = "yyyy:MM:dd HH:mm:ss";
    public static final String ServerTimeZone = "EST";
    public static final String dateFormat26 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String dateFormat27 = "dd MMM yyyy";
    public static final String dateFormat28 = "dd MMM";
    public static final String dateFormat29 = "yyyy/MM/dd";


    public static final String GoogleApiURl = "https://maps.googleapis.com/maps/api/geocode/json";
    public static final String Country = "country:US";
    public static final String APIKey = "AIzaSyBJN4cRF8HnjEwJ3Z99CcivKberdeOvgKU";


    //server Error codes for GenerateToken service...
    //server Error codes for GenerateToken service...
    public static final int VALID_RESPONSE_CODE = 200;
    public static final int CREATED_RESPONSE_CODE = 201;
    public static final int UPDATED_RESPONSE_CODE = 202;
    public static final String EC_INVALID_TOKEN = "500";
    public static final String EC_TOKEN_EXPIRED = "502";

    // Encryption related constants
    public static final String Encrypt = ":Encrypt";

    //key and vector for debug build..
    public static final String EncryptionKeyDebug = "";
    public static final String EncryptionVectorDebug = "";

    //key and vector for live build..
    public static final String EncryptionKeyLive = "";
    public static final String EncryptionVectorLive = "";


    // Time related constants
    public static final int WEBSERVICE_WAITTIME = 120000;
    public static final int WEBSERVICE_SOCKETTIME = 120000;


    public static String CURRENCY_SYMBOL = "$";
    public static String NULL = "N/A";


    public static int MAX_ITEMS_TO_LOAD = 20;

    public static final String STRIPE_TESTING_API_KEY = "pk_test_u58ujQ6huY9lQA5GzNhWiVQR";
    public static final String STRIPE_LIVE_API_KEY = "pk_live_MsAqhQ7Y6vi9tnQbJJBfNVEe";
}
