package app.com.dunkeydelivery.utils;

/**
 * Created by Developer on 6/9/2017.
 */

public class StringValidations {

    public static boolean isEmailValid(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
