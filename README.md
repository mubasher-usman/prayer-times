[![CI](https://github.com/mubasher-usman/prayer-times/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/mubasher-usman/prayer-times/actions/workflows/maven.yml)
[![Releases](https://img.shields.io/github/v/release/mubasaher-usman/prayer-times)](https://github.com/mubasher-usman/prayer-times/releases)
![](https://img.shields.io/packagist/dt/islamic-network/prayer-times.svg)


## Prayer Times Library (Java)

This is Java edition of the Prayer Times Library (v2.3) originally written in JavaScript by Hamid Zarrabi-Zadeh of PrayTimes.org and available on http://praytimes.org/code/v2/js/PrayTimes.js. 

Hussain Ali Khan wrote its version 1 and available at http://praytimes.org/code/git/?a=tree&p=PrayTimes&hb=HEAD&f=v1/java,

This is an improved, bug fixed and rewritten of version 1 with more calculation methods inspired from PHP edition https://github.com/islamic-network/prayer-times, 
It has divulged much from the original since it was first written, so please don't use it as a 'like for like' substitute.

## How to Use this Library

The library is a maven package, so to use it, includes the maven dependency:

```maven
<dependency>
    <groupId>org.mubasherusman</groupId>
    <artifactId>prayer-times</artifactId>
    <version>1.2.1</version>
</dependency>
```

Using it is rather simple:

```java
PrayerTimes prayerTimes = PrayerTimes.builder()
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
        .build();
Map<DayTimeNames,Object> times = prayerTimes.computeTimes();
```

## Methods

Supported methods can be seen @ https://github.com/mubasher-usman/prayer-times/blob/master/src/main/org/mubasherusman/prayertimes/constants/Method.java#L10.

### Understanding Methods

For a discussion on methods, see https://aladhan.com/calculation-methods.

## Tests

Compare the results produced by this library against the original JS version.

## Contributors

Hamid Zarrabi-Zadeh, Hussain Ali Khan, Meezaan-ud-Din Abdu Dhil-Jalali Wal-Ikram

## License

Same as the original - License: GNU LGPL v3.0, which effectively means:
```
Permission is granted to use this code, with or without modification, in any website or application provided that credit is given to the original work with a link back to PrayTimes.org.
```
