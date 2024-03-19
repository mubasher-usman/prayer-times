package org.mubasherusman.prayertimes.utils;

/**
 * Helper class for Date manipulation
 *
 * @author Mubasher Usman (mian.mubasherusman@gmail.com)
 */
public final class DateUtils {
    /**
     * @param year int
     * @param month int
     * @param day int
     * @return Julian Date as double
     */
    public static double getJulianDate(int year, int month, int day) {
        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        double A = Math.floor(year / 100.0);
        double B = 2 - A + Math.floor(A / 4);
        return Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + B - 1524.5;
    }

     /*private double gregorianToJulianDate() {
        double julianDate = julianDate(date.getMonthValue(), date.getDayOfMonth(), date.getYear()) ;
        //correct for half-day offset
        double dayfrac = date.getHour() / 24.0 - 0.5;
        if (dayfrac < 0) dayfrac += 1;
        //now set the fraction of a day
        double frac = dayfrac + (date.getMinute() + date.getSecond() / 60.0) / 60.0 / 24.0;
        return (julianDate + frac);
    }*/

    /**
     * difference between two times
     * @param t1 time 1
     * @param t2 time 2
     * @return the difference
     */
    public static double timeDiff(double t1, double t2) {
        return Trigonometry.fixHour(t2 - t1);
    }

}
