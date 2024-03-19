/**
 * PrayTime.java: Prayer Times Calculator (ver 1.0)
 * PrayTimes.js: Prayer Times Calculator (ver 2.3)
 * https://github.com/islamic-network/prayer-times (ver 1.23)
 * Copyright (C) 2007-2011 PrayTimes.org
 * Copyright (C) 2015-2017 AlAdhan.com

 * Original Java Code By: Hussain Ali Khan
 * Original JS Code By: Hamid Zarrabi-Zadeh
 * Original Php Code By: Meezaan-ud-Din Abdu Dhil-Jalali Wal-Ikram (https://github.com/islamic-network/prayer-times)

 * Ported to Java with the help of Original PrayTime.java and PHP codebase by Mubasher Usman (mian.mubasherusman@gmail.com)

 * License: GNU LGPL v3.0

 * TERMS OF USE:
 * 	Permission is granted to use this code, with or
 * 	without modification, in any website or application
 * 	provided that credit is given to the original work
 * 	with a link back to PrayTimes.org.

 * This program is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY.

 * PLEASE DO NOT REMOVE THIS COPYRIGHT BLOCK.

 */
package org.mubasherusman.prayertimes;

import org.mubasherusman.prayertimes.moonsighting.Fajr;
import org.mubasherusman.prayertimes.moonsighting.Isha;
import org.mubasherusman.prayertimes.moonsighting.ShafaqMethod;
import org.mubasherusman.prayertimes.constants.TimeName;
import org.mubasherusman.prayertimes.constants.Fiqh;
import org.mubasherusman.prayertimes.constants.LatAdjMethod;
import org.mubasherusman.prayertimes.constants.Method;
import org.mubasherusman.prayertimes.constants.MidNightMode;
import org.mubasherusman.prayertimes.constants.TimeFormat;
import org.mubasherusman.prayertimes.utils.CommonUtils;
import org.mubasherusman.prayertimes.utils.DateUtils;
import org.mubasherusman.prayertimes.utils.Trigonometry;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static org.mubasherusman.prayertimes.constants.TimeName.*;
import static org.mubasherusman.prayertimes.constants.LatAdjMethod.ANGLE_BASED;
import static org.mubasherusman.prayertimes.constants.LatAdjMethod.NONE;
import static org.mubasherusman.prayertimes.constants.LatAdjMethod.ONE_SEVENTH;
import static org.mubasherusman.prayertimes.constants.Method.CUSTOM;
import static org.mubasherusman.prayertimes.constants.Method.MOONSIGHTING;
import static org.mubasherusman.prayertimes.constants.MidNightMode.JAFARI;
import static org.mubasherusman.prayertimes.constants.MidNightMode.STANDARD;
import static org.mubasherusman.prayertimes.constants.SunProperty.DECLINATION;
import static org.mubasherusman.prayertimes.constants.SunProperty.EQUATION_OF_TIME;
import static org.mubasherusman.prayertimes.constants.TimeFormat.*;
import static org.mubasherusman.prayertimes.utils.CommonUtils.evaluate;
import static org.mubasherusman.prayertimes.utils.CommonUtils.twoDigitsFormat;
import static org.mubasherusman.prayertimes.utils.DateUtils.timeDiff;
import static org.mubasherusman.prayertimes.utils.SunPosHelper.sunPosition;
import static org.mubasherusman.prayertimes.utils.SunPosHelper.sunRiseAngle;

/**
 * PrayerTimes Class will calculate prayer times of current or any given date.
 * The mandatory settings for prayer times calculation is
 * Calculation-Method {@link Method} ,
 * School of Thought (Fiqh) {@link Fiqh} ,

 * Optional Params
 * Asar Shadow Factor. It Will override the default Shadow factors instructed by Fiqh.
 * Normally, You do not need to provide Asar Shadow Factor.

 * Original Authors: Hamid Zarrabi-Zadeh
 * Credit: Php Port (Meezaan-ud-Din Abdu Dhil-Jalali Wal-Ikram). Java Port v1 (Hussain Ali Khan)

 * @author Mubasher Usman (mian.mubasherusman@gmail.com)
 */
public class PrayerTimes {
    /**
     * If we're unable to calculate a time, we'll return this
     */
    private static final String INVALID_TIME = "-----";
    private final Double asrShadowFactor;
    private final ZonedDateTime date;
    private final Double latitude;
    private final Double longitude;

    private Method method = Method.MWL;
    private Fiqh fiqh = Fiqh.STANDARD;
    private LatAdjMethod latitudeAdjustmentMethod = ANGLE_BASED;
    private TimeFormat timeFormat = H24;
    private ShafaqMethod shafaqMethod = ShafaqMethod.GENERAL;
    private MidNightMode midnightMode = STANDARD;
    private Double elevation;
    private Map<TimeName, Integer> offset;
    private Map<TimeName, Object> customMethodParams;
    private Map<TimeName, Object> settings;
    private final Double julianDate;

    /**
     * @param builder Builder
     */
    private PrayerTimes(Builder builder) {
        Objects.requireNonNull(builder,"Builder Required");
        this.date = Objects.requireNonNullElseGet(builder.date,
                () -> ZonedDateTime.now(ZoneId.of(
                        Objects.requireNonNull(builder.timeZone, "Time Zone required"))));
        setMethod(builder);
        if(builder.fiqh!=null) this.fiqh = builder.fiqh;
        this.asrShadowFactor = Objects.requireNonNullElseGet(builder.asarShadowFactor, this.fiqh::getId);
        loadSettings();
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.elevation = builder.elevation == null ? 0 : 1 * elevation;
        this.julianDate = DateUtils.getJulianDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()) - longitude / (15.0 * 24.0);
        if(builder.midnightMode!=null) this.midnightMode = builder.midnightMode;
        if(builder.shafaqMethod!=null) this.shafaqMethod = builder.shafaqMethod;
        if(builder.timeFormat!=null) this.timeFormat = builder.timeFormat;
        if(builder.latitudeAdjustmentMethod!=null) this.latitudeAdjustmentMethod = builder.latitudeAdjustmentMethod;
        if(builder.offset!=null) this.offset = builder.offset;
    }

    private void setMethod(Builder builder) {
        if(builder.method!=null) {
            this.method = builder.method;
            if(this.method==CUSTOM) {
                customMethodParams = method.getParams();
                customMethodParams.put(FAJR, builder.fajarAngle);
                customMethodParams.put(MAGHRIB, builder.maghribAngleOrMin);
                customMethodParams.put(ISHA, builder.eshaAngleOrMin);
            }
        }
    }

    private void loadSettings() {
        this.settings = new HashMap<>();
        Map<TimeName,Object> params = method.equals(Method.CUSTOM)? customMethodParams : method.getParams();
        this.settings.put(IMSAK, params.get(IMSAK) != null ? params.get(IMSAK) : "10 min");
        this.settings.put(FAJR, params.get(FAJR) != null ? params.get(FAJR) : 0.0);
        this.settings.put(ZHUHR, params.get(ZHUHR) != null ? params.get(ZHUHR) : "0 min");
        this.settings.put(ISHA, params.get(ISHA) != null ? params.get(ISHA) : 0.0);
        this.settings.put(MAGHRIB, params.get(MAGHRIB) != null ? params.get(MAGHRIB) : "0 min");
        
        // Pick up methods midnightMode
        if (params.get(MIDNIGHT) != null) {
            this.midnightMode = (MidNightMode) params.get(MIDNIGHT);
        }
    }

    //--------------------------------------------------------------------------------------
    /**
     * @return Map
     */
    public Map<TimeName, Object> computeTimes() {
        // default times
        Map<TimeName, Double> times = new LinkedHashMap<>(){{
            put(IMSAK, 5.0);
            put(FAJR, 5.0);
            put(SUNRISE, 6.0);
            put(ZHUHR, 12.0);
            put(ASR, 13.0);
            put(SUNSET, 18.0);
            put(MAGHRIB, 18.0);
            put(ISHA, 18.0);
        }};
        computePrayerTimes(times);
        adjustTimes(times);

        // add nighttime's
        double diff = (Objects.equals(midnightMode, JAFARI)) ?
                timeDiff(times.get(SUNSET), times.get(FAJR)) :
                timeDiff(times.get(SUNSET), times.get(SUNRISE));
        times.put(MIDNIGHT, times.get(SUNSET) + diff / 2);
        times.put(FIRST_THIRD, times.get(SUNSET) + diff / 3);
        times.put(LAST_THIRD, times.get(SUNSET) + 2 * (diff / 3));

        // If our method is Moon sighting, reset the Fajr and Isha times
        if (Objects.equals(method, MOONSIGHTING)) {
            moonSightingRecalculation(times);
        }

        tuneTimes(times);
        return modifyFormats(times);
    }

    /**
     * Calculate prayer times
     * @param times Map of default times
     */
    private void computePrayerTimes(Map<TimeName, Double> times) {
        dayPortion(times);
        times.put(IMSAK, sunAngleTime(evaluate(settings.get(IMSAK)), times.get(IMSAK), "ccw"));
        times.put(SUNRISE, sunAngleTime(sunRiseAngle(elevation), times.get(SUNRISE), "ccw"));
        times.put(FAJR, sunAngleTime(evaluate(settings.get(FAJR)), times.get(FAJR), "ccw"));
        times.put(ZHUHR, midDay(times.get(ZHUHR)));
        times.put(ASR, asrTime(asrShadowFactor, times.get(ASR)));
        times.put(SUNSET, sunAngleTime(sunRiseAngle(elevation), times.get(SUNSET)));
        times.put(MAGHRIB, sunAngleTime(evaluate(settings.get(MAGHRIB)), times.get(MAGHRIB)));
        times.put(ISHA, sunAngleTime(evaluate(settings.get(ISHA)), times.get(ISHA)));
    }

    /**
     * Time Adjustments based on timezone
     * @param times Map of Prayer times
     */
    private void adjustTimes(Map<TimeName, Double> times) {
        ZoneId dateTimeZone = date.getZone();
        for (Map.Entry<TimeName, Double> entry : times.entrySet()) {
            TimeName key = entry.getKey();
            Double value = entry.getValue();
            times.put(key, value + (dateTimeZone.getRules().getOffset(date.toInstant()).getTotalSeconds() / 3600) - longitude / 15);
        }
        if (!latitudeAdjustmentMethod.equals(NONE)) {
            adjustHighLatitudes(times);
        }
        if (CommonUtils.containsMin(settings.get(IMSAK))) {
            times.put(IMSAK, times.get(FAJR) - evaluate(settings.get(IMSAK)) / 60);
        }
        if (CommonUtils.containsMin(settings.get(MAGHRIB))) {
            times.put(MAGHRIB, times.get(SUNSET) + evaluate(settings.get(MAGHRIB)) / 60);
        }
        if (CommonUtils.containsMin(settings.get(ISHA))) {
            times.put(ISHA, times.get(MAGHRIB) + evaluate(settings.get(ISHA)) / 60);
        }
        times.put(ZHUHR, times.get(ZHUHR) + evaluate(settings.get(ZHUHR)) / 60);
    }

    /**
     * If the calculation method is moon sighting, then adjust Fajar and Esha times
     * @param times Map of calculated prayer times
     */
    private void moonSightingRecalculation(Map<TimeName, Double> times) {
        // Reset Fajr
        Fajr fajrMS = new Fajr(date, latitude);
        times.put(FAJR, times.get(SUNRISE) - (fajrMS.getMinutesBeforeSunrise() / 60));

        // Fajr has changed, also reset Imsak
        if (CommonUtils.containsMin(settings.get(IMSAK))) {
            times.put(IMSAK, times.get(FAJR) - evaluate(settings.get(IMSAK)) / 60);
        }

        // Reset Isha
        Isha ishaMS = new Isha(date, latitude, shafaqMethod);
        times.put(ISHA, times.get(SUNSET) + (ishaMS.getMinutesAfterSunset() / 60));
    }

    /**
     * adjust times based on provided offset
     * @param times map
     */
    private void tuneTimes(Map<TimeName, Double> times) {
        if (offset != null && !offset.isEmpty()) {
            for (Map.Entry<TimeName, Integer> entry : offset.entrySet()) {
                Integer value = entry.getValue();
                times.computeIfPresent(entry.getKey(), (k,v)-> v + value / 60);
            }
        }
    }

    /**
     * Modify the Prayer time as per given format.
     * If the required format is float then time in double or format to relevant 12/24 hrs string
     * @param times map
     * @return new Map of Formatted Prayer times.
     */
    private Map<TimeName, Object> modifyFormats(Map<TimeName, Double> times) {
        Map<TimeName, Object> modifiedTimes = new LinkedHashMap<>();
        for (Map.Entry<TimeName, Double> entry : times.entrySet()) {
            TimeName key = entry.getKey();
            Double value = entry.getValue();
            modifiedTimes.put(key, getFormattedTime(value, key));
        }
        return modifiedTimes;
    }

    /**
     * @param time Double
     * @param prayer String
     * @return either double or String time based on format
     */
    private Object getFormattedTime(double time, TimeName prayer) {
        if (Double.isNaN(time)) {
            return INVALID_TIME;
        }
        if (timeFormat.equals(DECIMAL)) {
            return time;
        }
        String[] suffixes = {"AM", "PM"};
        time = Trigonometry.fixHour(time + 0.5 / 60);  // add 0.5 minutes to round
        int hours = (int) Math.floor(time);
        int minutes = (int) Math.floor((time - hours) * 60);
        String suffix = timeFormat.equals(H12) ? suffixes[(hours < 12 ? 0 : 1)] : "";
        int hour = timeFormat.equals(H24) ? hours : ((hours + 12 - 1) % 12 + 1);
        String twoDigitHour = twoDigitsFormat(hour);
        String twoDigitMinutes = twoDigitsFormat(minutes);
        if (timeFormat.equals(ISO8601)) {
            // Create temporary date object
            ZonedDateTime tempDate = date.withHour(hours).withMinute(minutes);
            if (prayer.equals(MIDNIGHT)) {
                if (hours >= 1 && hours < 12) {
                    tempDate = tempDate.plusDays(1);
                }
            }
            return tempDate.toString();
        }
        return twoDigitHour + ":" + twoDigitMinutes + (suffix.isEmpty() ? "" : " " + suffix);
    }

    /**
     * Adjust Times for High Latitude areas
     * @param times prayer times
     */
    private void adjustHighLatitudes(Map<TimeName, Double> times) {
        double nightTime = timeDiff(times.get(SUNSET), times.get(SUNRISE));
        times.put(IMSAK, adjustHLTime(times.get(IMSAK), times.get(SUNRISE), evaluate(settings.get(IMSAK)), nightTime, "ccw"));
        times.put(FAJR, adjustHLTime(times.get(FAJR), times.get(SUNRISE), evaluate(settings.get(FAJR)), nightTime, "ccw"));
        times.put(ISHA, adjustHLTime(times.get(ISHA), times.get(SUNSET), evaluate(settings.get(ISHA)), nightTime));
        times.put(MAGHRIB, adjustHLTime(times.get(MAGHRIB), times.get(SUNSET), evaluate(settings.get(MAGHRIB)), nightTime));
    }

    /**
     * Adjust High Latitude time without a direction
     * @param time prayer Time
     * @param base prayer time reference e.g. SUNRISE | SUNSET
     * @param angle sun angle based on calculation method
     * @param night SUNSET and SUNRISE diff
     * @return adjusted time
     */
    private double adjustHLTime(double time, double base, double angle, double night) {
        return adjustHLTime(time,base,angle,night, null);
    }
    /**
     * Adjust High Latitude time with a direction
     * @param time prayer Time
     * @param base prayer time reference e.g. SUNRISE | SUNSET
     * @param angle sun angle based on calculation method
     * @param night SUNSET and SUNRISE diff
     * @param direction The ccw direction
     * @return adjusted time
     */
    private double adjustHLTime(double time, double base, double angle, double night, String direction) {
        double portion = nightPortion(angle, night);
        double timeDiff = ("ccw".equals(direction)) ? timeDiff(time, base) : timeDiff(base, time);
        if (Double.isNaN(time) || timeDiff > portion) {
            time = base + ("ccw".equals(direction) ? -portion : portion);
        }
        return time;
    }

    /**
     * Calculate Night portion
     * @param angle sun angle
     * @param night night value
     * @return time
     */
    private double nightPortion(double angle, double night) {
        double portion;
        if (latitudeAdjustmentMethod.equals(ANGLE_BASED)) {
            portion = 1.0 / 60.0 * angle;
        } else if (latitudeAdjustmentMethod.equals(ONE_SEVENTH)) {
            portion = 1.0 / 7.0;
        } else {
            // MIDDLE_OF_THE_NIGHT
            portion = 1.0 / 2.0;
        }
        return portion * night;
    }

    /**
     * @param angle angle
     * @param time time
     * @return time integer
     */
    private double sunAngleTime(double angle, double time) {
        return sunAngleTime(angle,time,null);
    }

    /**
     * @param angle angle
     * @param time time
     * @param direction direction
     * @return time integer
     */
    private double sunAngleTime(double angle, double time, String direction) {
        double decl = sunPosition(julianDate + time).get(DECLINATION);
        double noon = midDay(time);
        double p1 = -Trigonometry.sin(angle) - Trigonometry.sin(decl) * Trigonometry.sin(latitude);
        double p2 = Trigonometry.cos(decl) * Trigonometry.cos(latitude);
        double cosRange = (p1 / p2);
        if (cosRange > 1) {
            cosRange = 1;
        }
        if (cosRange < -1) {
            cosRange = -1;
        }
        double t = 1 / 15.0 * Trigonometry.arccos(cosRange);
        return noon + ((direction!=null && direction.equals("ccw")) ? -t : t);
    }

    /**
     * Calculate Asar time
     * @param factor Juristic method e.g., When Shcool of thought is SHAAFI, Malakai then its 1 otherwise 2
     * @param time time
     * @return Asar Prayer time
     */
    private double asrTime(double factor, double time) {
        //double julianDate = gregorianToJulianDate();
        double decl = sunPosition(julianDate + time).get(DECLINATION);
        double angle = -Trigonometry.arccot(factor + Trigonometry.tan(Math.abs(latitude - decl)));
        return sunAngleTime(angle, time);
    }

    /**
     * compute mid-day (Noon, Dhuhr, Zawal) time
     * @param time given time
     * @return Noon as double value
     */
    private double midDay(double time) {
        double eqt = sunPosition(julianDate + time).get(EQUATION_OF_TIME);
        return Trigonometry.fixHour(12 - eqt); // noon
    }

    /**
     * convert hours-to-day portions
     * @param times default times
     */
    private void dayPortion(Map<TimeName, Double> times) {
        times.replaceAll((k, v) -> v / 24);
    }

    //------------------------- Helper Meta data to used for calculation -------------------------
    public Map<String, Object> getMeta() {
        return new LinkedHashMap<>() {
            {
                put("latitude",latitude);
                put("longitude",longitude);
                put("timezone",date.format(DateTimeFormatter.ofPattern("zzz", Locale.forLanguageTag("en"))));
                if (Objects.equals(method, MOONSIGHTING)) {
                    put("latitudeAdjustmentMethod", NONE);
                    method.getParams().put(SHAFAQ, shafaqMethod);
                }else {
                    put("latitudeAdjustmentMethod", latitudeAdjustmentMethod);
                }
                put("method", method);
                put("midnightMode",midnightMode);
                put("school", fiqh);
                put("offset", offset);
            }
        };
    }

    //-----------------------------------------
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double latitude;
        private double longitude;
        private ZonedDateTime date;
        private String timeZone;
        private Method method;
        private Fiqh fiqh;
        private Double asarShadowFactor;
        private TimeFormat timeFormat;
        private ShafaqMethod shafaqMethod;
        private LatAdjMethod latitudeAdjustmentMethod;
        private MidNightMode midnightMode;
        private Map<TimeName, Integer> offset;
        private Double elevation;
        //---------custom method properties ----------
        private Float fajarAngle;
        private Object maghribAngleOrMin;
        private Object eshaAngleOrMin;

        private Builder() {}

        /**
         * Sets the latitude
         *
         * @param latitude A {@link double} instance.
         * @return This builder.
         */
        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        /**
         * Sets the longitude
         *
         * @param longitude A {@link double} instance.
         * @return This builder.
         */
        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        /**
         * Sets the date
         *
         * @param date A {@link ZonedDateTime} instance.
         * @return This builder.
         */
        public Builder date(ZonedDateTime date) {
            this.date = date;
            return this;
        }

        /**
         * Sets the timeZone in case you want to calculate current prayer times
         * Example: Asia/Karachi
         *
         * @param timeZone A {@link String} instance.
         * @return This builder.
         */
        public Builder timezone(String timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        /**
         * Sets the calculation method
         *
         * @param method A {@link String} instance.
         * @return This builder.
         */
        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        /**
         * Sets the Fiqh
         *
         * @param fiqh A {@link Fiqh} instance.
         * @return This builder.
         */
        public Builder fiqh(Fiqh fiqh) {
            this.fiqh = fiqh;
            return this;
        }

        /**
         * Sets the asarShadowFactor
         *
         * @param asarShadowFactor A {@link Integer} instance.
         * @return This builder.
         */
        public Builder asarShadowFactor(Double asarShadowFactor) {
            this.asarShadowFactor = asarShadowFactor;
            return this;
        }

        /**
         * Sets the time format
         *
         * @param timeFormat A {@link TimeFormat} instance.
         * @return This builder.
         */
        public Builder timeFormat(TimeFormat timeFormat) {
            this.timeFormat = timeFormat;
            return this;
        }

        /**
         * Sets the shafaq Method
         *
         * @param shafaqMethod A {@link ShafaqMethod} instance.
         * @return This builder.
         */
        public Builder shafaqMethod(ShafaqMethod shafaqMethod) {
            this.shafaqMethod = shafaqMethod;
            return this;
        }

        /**
         * Sets the latitudeAdjustmentMethod
         *
         * @param latitudeAdjustmentMethod A {@link LatAdjMethod} instance.
         * @return This builder.
         */
        public Builder latitudeAdjustmentMethod(LatAdjMethod latitudeAdjustmentMethod) {
            this.latitudeAdjustmentMethod = latitudeAdjustmentMethod;
            return this;
        }

        /**
         * Sets the midnightMode
         *
         * @param midnightMode A {@link MidNightMode} instance.
         * @return This builder.
         */
        public Builder midnightMode(MidNightMode midnightMode) {
            this.midnightMode = midnightMode;
            return this;
        }

        /**
         * Sets the elevation
         *
         * @param elevation A {@link Double} instance.
         * @return This builder.
         */
        public Builder elevation(Double elevation) {
            this.elevation = elevation;
            return this;
        }

        /**
         * Set custom value and in custom method name
         * @param fajarAngle angle
         * @param maghribAngleOrMin angle or minute
         * @param eshaAngleOrMin angle or minute
         */
        public void setCustomMethod(Float fajarAngle, Object maghribAngleOrMin, Object eshaAngleOrMin) {
            this.method = Method.CUSTOM;
            this.fajarAngle=fajarAngle;
            this.maghribAngleOrMin=maghribAngleOrMin;
            this.eshaAngleOrMin=eshaAngleOrMin;
        }

        /**
         * Provide custom time adjustments for prayer times
         * @param imsak int
         * @param fajr int
         * @param sunrise int
         * @param dhuhr int
         * @param asr int
         * @param maghrib int
         * @param sunset int
         * @param isha int
         * @param midnight int
         */
        public void setOffset(int imsak, int fajr, int sunrise, int dhuhr, int asr, int maghrib, int sunset, int isha, int midnight) {
            offset = new HashMap<>(){
                {
                    put(IMSAK,imsak);
                    put(FAJR, fajr);
                    put(SUNRISE,sunrise);
                    put(ZHUHR,dhuhr);
                    put(ASR,asr);
                    put(MAGHRIB,maghrib);
                    put(SUNSET,sunset);
                    put(ISHA,isha);
                    put(MIDNIGHT,midnight);
                }
            };
        }

        /**
         * Creates a new {@link PrayerTimes} instance from the parameters set on this builder.
         *
         * @return A new {@link PrayerTimes} instance.
         * @throws IllegalArgumentException If any of the parameters set on the builder are invalid.
         */
        public PrayerTimes build() {
            return new PrayerTimes(this);
        }
    }
}


