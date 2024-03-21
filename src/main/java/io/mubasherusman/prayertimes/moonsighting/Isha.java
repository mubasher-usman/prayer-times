package io.mubasherusman.prayertimes.moonsighting;

import java.time.ZonedDateTime;

/**
 * Calculate Esha (Mandatory Night prayer) time5's adjustments according to
 * <a href="https://www.moonsighting.com/how-we.html"><b>Moonsighting Committee Worldwide (MCW)</b></a>
 */
public class Isha extends Prayer {

    public Isha(ZonedDateTime date, double latitude, TwilightMethod twilightMethod) {
        super(date, latitude);
        setShafaq(twilightMethod);
    }

    /**
     * Calculate function of latitude and season based on given Shafaq method.
     * @param twilightMethod the instance of {@link TwilightMethod}
     */
    public void setShafaq(TwilightMethod twilightMethod) {
        if (twilightMethod.equals(TwilightMethod.AHMER)) {
            this.a = 62 + 17.4 / 55.0 * Math.abs(this.latitude);
            this.b = 62 - 7.16 / 55.0 * Math.abs(this.latitude);
            this.c = 62 + 5.12 / 55.0 * Math.abs(this.latitude);
            this.d = 62 + 19.44 / 55.0 * Math.abs(this.latitude);
        } else if (twilightMethod.equals(TwilightMethod.ABYAD)) {
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



