package app.com.dunkeydelivery.utils;

import android.util.Patterns;


public class Validation {
//
//    public static boolean isEmailValid(String keyword) {
//
//        String emailValidate = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        return keyword.matches(emailValidate);
//    }

    public static boolean isEmailValid(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isEmpty(String keyword) {

        return keyword.length() == 0;
    }

    public static boolean isNonEmpty(String value) {
        return !(value == null || value.isEmpty() || value.equalsIgnoreCase("") || value.equalsIgnoreCase("null"));
    }

    /*public static String getNullAsEmptyString(JsonElement jsonElement) {
        if (jsonElement == null)
            return "";
        return jsonElement.isJsonNull() ? "" : jsonElement.getAsString();
    }
*/
    public static boolean isURLValid(String link) {
        return Patterns.WEB_URL.matcher(link).matches();
    }

    public static boolean isDoubleValid(String value) {
        return !(value.equalsIgnoreCase("."));
    }

    public static boolean isPasswordValid(String password) {
        if (password.length() > 5)
            return true;
        else
            return false;
    }

    public static boolean isZipValid(String zip) {
        if (zip.length() > 3)
            return true;
        else
            return false;
    }

    public static boolean isPhoneValid(String phone) {
        if (phone.length() > 9 && android.util.Patterns.PHONE.matcher(phone).matches())
            return true;
        else
            return false;
    }
}
