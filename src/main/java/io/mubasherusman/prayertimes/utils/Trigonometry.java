package io.mubasherusman.prayertimes.utils;

/**
 * Class Trigonometry contains Trigonometric Math functions

 * Original Authors: Hussain Ali Khan
 * @author Mubasher Usman (mian.mubasherusman@gmail.com)
 */
public class Trigonometry {
    /**
     * degree to radian
     * @param d Double
     * @return radian
     */
    public static double dtr(double d) {
        return (d * Math.PI) / 180.0;
    }

    /**
     * radian to degree
     * @param r Double
     * @return degree
     */
    public static double rtd(double r) {
        return (r * 180.0) / Math.PI;
    }

    /**
     * degree sin
     * @param d Double
     * @return degree sin
     */
    public static double sin(double d) {
        return Math.sin(dtr(d));
    }

    /**
     * degree cos
     * @param d Double
     * @return degree cos
     */
    public static double cos(double d) {
        return Math.cos(dtr(d));
    }

    /**
     * degree tan
     * @param d Double
     * @return degree tan
     */
    public static double tan(double d) {
        return Math.tan(dtr(d));
    }

    /**
     * degree arcsin
     * @param d Double
     * @return degree
     */
    public static double arcsin(double d) {
        return rtd(Math.asin(d));
    }

    /**
     * degree arccos
     * @param d Double
     * @return degree
     */
    public static double arccos(double d) {
        return rtd(Math.acos(d));
    }

    /**
     * degree arctan
     * @param d Double
     * @return degree
     */
    public static double arctan(double d) {
        return rtd(Math.atan(d));
    }

    /**
     * degree arccot
     * @param x Double
     * @return degree
     */
    public static double arccot(double x) {
        return rtd(Math.atan(1.0 / x));
    }

    /**
     * degree arctan2
     * @param x Double
     * @param y Double
     * @return degree
     */
    public static double arctan2(double y, double x) {
        return rtd(Math.atan2(y, x));
    }

    /**
     * fixAngle
     * @param a Double
     * @return double value
     */
    public static double fixAngle(double a) {
        return fix(a, 360);
    }

    /**
     * fixHour
     * @param a Double
     * @return double value
     */
    public static double fixHour(double a) {
        return fix(a, 24);
    }

    /**
     * fix
     * @param a Double
     * @param b Double
     * @return double value
     */
    private static double fix(double a, double b) {
        a = a - (b * (Math.floor(a / b)));
        return (a < 0) ? a + b : a;
    }
}

