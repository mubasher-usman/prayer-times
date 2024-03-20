package io.mubasherusman.prayertimes.utils;

import io.mubasherusman.prayertimes.constants.SunProperty;

import java.util.HashMap;
import java.util.Map;

import static io.mubasherusman.prayertimes.constants.SunProperty.DECLINATION;
import static io.mubasherusman.prayertimes.constants.SunProperty.EQUATION_OF_TIME;

/**
 * This helper class contains methods to find sun position

 * Original Authors: Hussain Ali Khan
 * @author Mubasher Usman (mian.mubasherusman@gmail.com)
 */
public class SunPosHelper {

    /**
     * compute declination angle of sun and equation of time
     * Ref: <a href="http://aa.usno.navy.mil/faq/docs/SunApprox.php">SunApprox.php</a>
     * @param julianDate julian date
     * @return equationOfTime and declination of sun as Map
     */
    public static Map<SunProperty, Double> sunPosition(double julianDate) {
        double D = julianDate - 2451545.0;
        double g = Trigonometry.fixAngle(357.529 + 0.98560028 * D);
        double q = Trigonometry.fixAngle(280.459 + 0.98564736 * D);
        double L = Trigonometry.fixAngle(q + (1.915 * Trigonometry.sin(g)) + (0.020 * Trigonometry.sin(2 * g)));
        //double R = 1.00014 - 0.01671 * Trigonometry.cos(g) - 0.00014 * Trigonometry.cos(2 * g);
        double e = 23.439 - (0.00000036 * D);
        double RA = Trigonometry.arctan2(Trigonometry.cos(e) * Trigonometry.sin(L), Trigonometry.cos(L)) / 15.0;
        double eqt = q / 15.0 - Trigonometry.fixHour(RA);
        double decl = Trigonometry.arcsin(Trigonometry.sin(e) * Trigonometry.sin(L));
        return new HashMap<>() {
            {
                this.put(EQUATION_OF_TIME, eqt);
                this.put(DECLINATION, decl);
            }
        };
    }

    /**
     * Sun Rise Angle from given elevation
     * @param elevation Double
     * @return angle value
     */
    public static double sunRiseAngle(double elevation) {
        //var earthRad = 6371009; // in meters
        //var angle = Trigonometry.arccos(earthRad/(earthRad+ elv));
        double angle = 0.0347 * Math.sqrt(elevation); // an approximation
        return 0.833 + angle;
    }
}
