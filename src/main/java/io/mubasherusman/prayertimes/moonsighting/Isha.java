package io.mubasherusman.prayertimes.moonsighting;

import java.time.ZonedDateTime;

public class Isha extends Prayer {

    public Isha(ZonedDateTime date, double latitude, ShafaqMethod shafaqMethod) {
        super(date, latitude);
        setShafaq(shafaqMethod);
    }

    public void setShafaq(ShafaqMethod shafaqMethod) {
        if (shafaqMethod.equals(ShafaqMethod.AHMER)) {
            this.a = 62 + 17.4 / 55.0 * Math.abs(this.latitude);
            this.b = 62 - 7.16 / 55.0 * Math.abs(this.latitude);
            this.c = 62 + 5.12 / 55.0 * Math.abs(this.latitude);
            this.d = 62 + 19.44 / 55.0 * Math.abs(this.latitude);
        } else if (shafaqMethod.equals(ShafaqMethod.ABYAD)) {
            this.a = 75 + 25.6 / 55.0 * Math.abs(this.latitude);
            this.b = 75 + 7.16 / 55.0 * Math.abs(this.latitude);
            this.c = 75 + 36.84 / 55.0 * Math.abs(this.latitude);
            this.d = 75 + 81.84 / 55.0 * Math.abs(this.latitude);
        } else {
            this.a = 75 + 25.6 / 55.0 * Math.abs(this.latitude);
            this.c = 75 - 9.21 / 55.0 * Math.abs(this.latitude);
            this.b = 75 + 2.05 / 55.0 * Math.abs(this.latitude);
            this.d = 75 + 6.14 / 55.0 * Math.abs(this.latitude);
        }
    }

    public float getMinutesAfterSunset() {
        return (int) Math.round(this.getMinutes());
    }
}



