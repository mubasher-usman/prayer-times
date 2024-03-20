package io.mubasherusman.prayertimes.moonsighting;

import java.time.ZonedDateTime;

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



