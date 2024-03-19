package org.mubasherusman.prayertimes.utils;

/**
 * Helper class for common utility methods
 *
 * @author Mubasher Usman (mian.mubasherusman@gmail.com)
 */
public final class CommonUtils {
    /**
     * Check if String contains min e.g., 90 min
     * @param str provided String
     * @return true | false
     */
    public static boolean containsMin(Object str) {
        return str instanceof String && ((String)str).contains("min");
    }

    /**
     * Evaluate given value as String or Number and convert to relevant double
     * @param val Wrapped Value
     * @return converted double value
     */
    public static double evaluate(Object val) {
        if(val instanceof String)
            return Double.parseDouble(((String)val).replaceAll("[^\\d.]", ""));
        else if (val instanceof Integer){
           return Double.valueOf((Integer)val);
        } else {
            return (double) val;
        }
    }

    /**
     * convert single digit to two digits
     * @param num Integer
     * @return two digit String
     */
    public static String twoDigitsFormat(int num) {
        return (num < 10) ? "0" + num : String.valueOf(num);
    }
}
