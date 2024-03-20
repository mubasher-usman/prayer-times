package io.mubasherusman.prayertimes.constants;

public enum TimeName {
    IMSAK("Imsak"),
    FAJR("Fajr"),
    SUNRISE("Sunrise"),
    ZHUHR("Dhuhr"),
    ASR("Asr"),
    SUNSET("Sunset"),
    MAGHRIB("Maghrib"),
    ISHA("Isha"),
    SHAFAQ("shafaq"),
    MIDNIGHT("Midnight"),
    FIRST_THIRD("Firstthird"),
    LAST_THIRD("Lastthird");

    private final String name;

    TimeName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}
