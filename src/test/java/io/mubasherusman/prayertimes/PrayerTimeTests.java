package io.mubasherusman.prayertimes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.mubasherusman.prayertimes.constants.Fiqh;
import io.mubasherusman.prayertimes.constants.MidNightMode;
import io.mubasherusman.prayertimes.moonsighting.ShafaqMethod;
import io.mubasherusman.prayertimes.constants.TimeName;
import io.mubasherusman.prayertimes.constants.LatAdjMethod;
import io.mubasherusman.prayertimes.constants.Method;
import io.mubasherusman.prayertimes.constants.TimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

public class PrayerTimeTests {

    @Test
    void testDefaultPrayerTimes(){
        Map<TimeName,Object> times = PrayerTimes.builder()
                //Mandatory Fields
                .latitude(31.33599139999999)
                .longitude(72.94605659999999)
                .timezone("Asia/Karachi")
                //Optional Settings, IF not provided, then the following defaults will be used
                .method(Method.MWL)
                .fiqh(Fiqh.STANDARD)
                .latitudeAdjustmentMethod(LatAdjMethod.ANGLE_BASED)
                .midnightMode(MidNightMode.STANDARD)
                .timeFormat(TimeFormat.H24)
                .shafaqMethod(ShafaqMethod.GENERAL)
                .build().computeTimes();
        Assertions.assertNotNull(times);
        Assertions.assertEquals(times.size(),11);
        times.forEach((k,v)-> System.out.println(k+ " = " + v));
    }

    @Test
    void testIso8601Format() {
        Map<TimeName,Object> times = PrayerTimes.builder()
                .latitude(51.508515)
                .longitude(-0.1254872)
                .date(ZonedDateTime.of(
                        LocalDateTime.of(2014,4,24,0,0,0),
                        ZoneId.of("Europe/London")))
                .method(Method.ISNA)
                .latitudeAdjustmentMethod(LatAdjMethod.ANGLE_BASED)
                .timeFormat(TimeFormat.ISO8601)
                .build().computeTimes();
        Assertions.assertEquals("2014-04-24T03:57+01:00[Europe/London]",times.get(TimeName.FAJR));
        Assertions.assertEquals("2014-04-24T05:46+01:00[Europe/London]",times.get(TimeName.SUNRISE));
        Assertions.assertEquals( "2014-04-24T12:59+01:00[Europe/London]",times.get(TimeName.ZHUHR));
        Assertions.assertEquals("2014-04-24T16:55+01:00[Europe/London]",times.get(TimeName.ASR));
    }

    @Test
    void testISNATimes() {
        PrayerTimes prayerTimes = PrayerTimes.builder()
                .latitude(51.508515)
                .longitude(-0.1254872)
                .date(ZonedDateTime.of(
                        LocalDateTime.of(2014,4,24,0,0,0),
                        ZoneId.of("Europe/London")))
                .method(Method.ISNA)
                .build();
        Map<TimeName,Object> times = prayerTimes.computeTimes();
        Assertions.assertEquals("03:47", times.get(TimeName.IMSAK));
        Assertions.assertEquals("03:57",times.get(TimeName.FAJR));
        Assertions.assertEquals("05:46",times.get(TimeName.SUNRISE));
        Assertions.assertEquals("12:59", times.get(TimeName.ZHUHR));
        Assertions.assertEquals("16:55",times.get(TimeName.ASR));
        Assertions.assertEquals("20:12",times.get(TimeName.SUNSET));
        Assertions.assertEquals("20:12",times.get(TimeName.MAGHRIB));
        Assertions.assertEquals("22:02",times.get(TimeName.ISHA));
        Assertions.assertEquals("00:59",times.get(TimeName.MIDNIGHT));
    }

    @Test
    void testNoTimes(){
        PrayerTimes prayerTimes = PrayerTimes.builder()
                .latitude(67.104732)
                .longitude(67.104732)
                .date(ZonedDateTime.of(
                        LocalDateTime.of(2018,1,19,0,0,0),
                        ZoneId.of("Asia/Yekaterinburg")))
                .method(Method.KARACHI)
                .build();
        Map<TimeName,Object> times = prayerTimes.computeTimes();
        times.forEach((k,v)-> Assertions.assertNotEquals("-----",v));
    }

    @Test
    void testMoonSightingTimes() {
        PrayerTimes prayerTimes = PrayerTimes.builder()
                .latitude(51.508515)
                .longitude(-0.1254872)
                .date(ZonedDateTime.of(
                        LocalDateTime.of(2014,4,24,0,0,0),
                        ZoneId.of("Europe/London")))
                .method(Method.MOONSIGHTING)
                .shafaqMethod(ShafaqMethod.GENERAL)
                .build();
        Map<TimeName,Object> times = prayerTimes.computeTimes();
        Assertions.assertEquals("03:54", times.get(TimeName.IMSAK));
        Assertions.assertEquals("04:04",times.get(TimeName.FAJR));
        Assertions.assertEquals("05:46",times.get(TimeName.SUNRISE));
        Assertions.assertEquals("12:59", times.get(TimeName.ZHUHR));
        Assertions.assertEquals("16:55",times.get(TimeName.ASR));
        Assertions.assertEquals("20:12",times.get(TimeName.SUNSET));
        Assertions.assertEquals("20:12",times.get(TimeName.MAGHRIB));
        Assertions.assertEquals("21:21",times.get(TimeName.ISHA));
        Assertions.assertEquals("00:59",times.get(TimeName.MIDNIGHT));
    }
}
