package io.mubasherusman.prayertimes.moonsighting;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Prayer {
    double latitude;
    private final ZonedDateTime date;
    double a;
    double b;
    double c;
    double d;
    private int dyy;
    private static final String DYY_NORTH_0 = "12-21";
    private static final String DYY_SOUTH_0 = "06-21";

    public Prayer(ZonedDateTime date, double latitude) {
        this.date = date;
        this.latitude = latitude;
        getDyy();
    }

    public void getDyy() {
        int year = this.date.getYear();
        LocalDate dateDyyZero;
        if (this.latitude > 0) { // Northern Hemisphere
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
