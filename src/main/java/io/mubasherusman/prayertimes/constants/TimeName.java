package io.mubasherusman.prayertimes.constants;

/**
 * Names to identify different portions of 24 hrs. <br>
 * Some portions are used for mandatory prayers, and some are used to identify sunnah prayers.
 */
public enum TimeName {
    /**
     * IMSAK
     */
    IMSAK("Imsak"),
    /**
     * FAJAR prayer
     */
    FAJR("Fajr"),
    /**
     * SUNRISE
     */
    SUNRISE("Sunrise"),
    /**
     * ZUHAR prayer
     */
    ZHUHR("Dhuhr"),
    /**
     * ASAR prayer
     */
    ASR("Asr"),
    /**
     * SUNSET
     */
    SUNSET("Sunset"),
    /**
     * MAGHRIB prayer
     */
    MAGHRIB("Maghrib"),
    /**
     * ESHA prayer
     */
    ISHA("Isha"),
    /**
     * Twilight
     */
    SHAFAQ("shafaq"),
    /**
     * Mid of the night
     */
    MIDNIGHT("Midnight"),
    /**
     * FIRST THIRD of Night
     */
    FIRST_THIRD("Firstthird"),
    /**
     * Last part of night
     */
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
