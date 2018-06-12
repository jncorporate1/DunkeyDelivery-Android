package app.com.dunkeydelivery.utils;

import java.text.DecimalFormat;

/**
 * Created by Developer on 9/12/2017.
 */

public class NumberUtils {

    /**
     * Formats a number to 2 decimal places.
     */
    public static String formatNumberTo2DecimalPlaces(Object number) {
        try {
            if (number == null) return "0.00";
            // #,###,###
            DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

            if (number instanceof String) {
                if (!((String) number).isEmpty())
                    return formatter.format(Double.parseDouble((String) number));
                return "0.00";
            }
            return formatter.format(number);
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    public static String formatNumberTo1DecimalPlaces(Object number) {
        try {
            if (number == null) return "0.0";
            DecimalFormat formatter = new DecimalFormat("#0.0");

            if (number instanceof String) {
                if (!((String) number).isEmpty())
                    return formatter.format(Double.parseDouble((String) number));
                return "0.0";
            }
            return formatter.format(number);
        } catch (NumberFormatException e) {
            return "0.0";
        }
    }
}
