package io.mubasherusman.prayertimes.moonsighting;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Base class to calculate time in minutes according to
 * <a href="https://www.moonsighting.com/how-we.html"><b>Moonsighting Committee Worldwide (MCW)</b></a>
 */
public class Prayer {
    double latitude;
    private final ZonedDateTime date;
    double a;
    double b;
    double c;
    double d;
    private int dyy;
    /**
     * Winter solstice<br>
     * The date when the North Pole reaches to maximum tilt and away from the sun.
     * It is the day with the shortest period of daylight and the longest night of the year.
     */
    private static final String DYY_NORTH_0 = "12-21";
    /**
     * Winter solstice<br>
     * The date when the South Pole reaches to maximum tilt and away from the sun.
     * It is the day with the shortest period of daylight and the longest night of the year.
     */
    private static final String DYY_SOUTH_0 = "06-21";

    /**
     * Constructor
     * @param date the date
     * @param latitude coordinates
     */
    public Prayer(ZonedDateTime date, double latitude) {
        this.date = date;
        this.latitude = latitude;
        getDyy();
    }

    /**
     * Days difference between given date and winter solstice
     */
    public void getDyy() {
        int year = this.date.getYear();
        LocalDate dateDyyZero;
        if (this.latitude > 0) {
            // Northern Hemisphere
            dateDyyZero = LocalDate.parse(year + "-" + DYY_NORTH_0);
        } else { // Southern Hemisphere
            dateDyyZero = LocalDate.parse(year + "-" + DYY_SOUTH_0);
        }
        long diff = ChronoUnit.DAYS.between(dateDyyZero, this.date);
        if (diff > 0) {
            this.dyy = (int) diff;
        } else {
            this.dyy = 365 + (int) diff;
        }
    }

    /**
     * Calculate minutes on given date based on the difference of current date and the winter solstice
     * @return minutes
     */
    protected double getMinutes() {
        if (this.dyy < 91)
            return this.a + (this.b - this.a) / 91 * this.dyy; // '91 DAYS SPAN
        else if (this.dyy < 137)
            return this.b + (this.c - this.b) / 46 * (this.dyy - 91); // '46 DAYS SPAN
        else if (this.dyy < 183)
            return this.c + (this.d - this.c) / 46 * (this.dyy - 137); // '46 DAYS SPAN
        else if (this.dyy < 229)
            return this.d + (this.c - this.d) / 46 * (this.dyy - 183); // '46 DAYS SPAN
        else if (this.dyy < 275)
            return this.c + (this.b - this.c) / 46 * (this.dyy - 229); // '46 DAYS SPAN
        else return this.b + (this.a - this.b) / 91 * (this.dyy - 275); // '91 DAYS SPAN
    }
}
