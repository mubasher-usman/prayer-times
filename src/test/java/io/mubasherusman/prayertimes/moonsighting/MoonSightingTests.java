package io.mubasherusman.prayertimes.moonsighting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class MoonSightingTests {

    @Test
    void testMinutesBeforeSunrise(){
        Fajr fajr = new Fajr(
                ZonedDateTime.of(
                LocalDateTime.of(2014,4,24,0,0,0),
                ZoneId.of("Europe/London")),51.508515);
        double value = fajr.getMinutesBeforeSunrise();
        Assertions.assertEquals(102.0,value);
    }

    @Test
    void testMinutesAfterSunSet(){
        Isha esha = new Isha(
                ZonedDateTime.of(
                        LocalDateTime.of(2014,4,24,0,0,0),
                        ZoneId.of("Europe/London")),51.508515, TwilightMethod.GENERAL);
        double value = esha.getMinutesAfterSunset();
        Assertions.assertEquals(69.0,value);
    }
}
