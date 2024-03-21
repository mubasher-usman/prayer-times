package io.mubasherusman.prayertimes.moonsighting;

import java.time.ZonedDateTime;

/**
 * Calculate Fajar (Mandatory First prayer) time's adjustments according to
 * <a href="https://www.moonsighting.com/how-we.html"><b>Moonsighting Committee Worldwide (MCW)</b></a>
 */
public class Fajr extends Prayer {
    public Fajr(ZonedDateTime date, double latitude) {
        super(date, latitude);
        this.a = 75 + 28.65 / 55 * Math.abs(this.latitude);
        this.b = 75 + 19.44 / 55 * Math.abs(this.latitude);
        this.c = 75 + 32.74 / 55 * Math.abs(this.latitude);
        this.d = 75 + 48.1 / 55 * Math.abs(this.latitude);
    }

    public double getMinutesBeforeSunrise() {
        return Math.round(this.getMinutes());
    }
}



