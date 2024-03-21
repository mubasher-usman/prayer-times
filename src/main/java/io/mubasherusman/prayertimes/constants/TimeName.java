package io.mubasherusman.prayertimes.constants;

/**
 * Names to identify different portions of 24 hrs. <br>
 * Some portions are used for mandatory prayers, and some are used to identify sunnah prayers.
 */
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

    /**
     * Get the name of prayer
     * @return name as String value
     */
    public String getName(){
        return this.name;
    }
}
