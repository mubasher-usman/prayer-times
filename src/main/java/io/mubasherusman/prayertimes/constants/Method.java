package io.mubasherusman.prayertimes.constants;

import io.mubasherusman.prayertimes.moonsighting.ShafaqMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculation Methods
 */
public enum Method {
    JAFARI(0,"Shia Ithna-Ashari, Leva Institute, Qum",new HashMap<>(){{
        put(TimeName.FAJR, 16.0);
        put(TimeName.ISHA, 14.0);
        put(TimeName.MAGHRIB, 4.0);
        put(TimeName.MIDNIGHT, MidNightMode.JAFARI);
    }},new HashMap<>(){{
        put("latitude", 34.6415764);
        put("longitude", 50.8746035);
    }}),
    KARACHI(1,"University of Islamic Sciences, Karachi",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.ISHA, 18.0);
    }},new HashMap<>(){{
        put("latitude", 24.8614622);
        put("longitude", 67.0099388);
    }}),
    ISNA(2,"Islamic Society of North America (ISNA)",new HashMap<>(){{
        put(TimeName.FAJR, 15.0);
        put(TimeName.ISHA, 15.0);
    }},new HashMap<>(){{
        put("latitude", 39.70421229999999);
        put("longitude", -86.39943869999999);
    }}),
    MWL(3,"Muslim World League", new HashMap<>() {{
        put(TimeName.FAJR, 18.0);
        put(TimeName.ISHA, 17.0);
    }}, new HashMap<>() {{
        put("latitude", 51.5194682);
        put("longitude", -0.1360365);
    }}),
    MAKKAH(4,"Umm Al-Qura University, Makkah",new HashMap<>(){{
        put(TimeName.FAJR, 18.5);
        put(TimeName.ISHA, "90 min");
    }},new HashMap<>(){{
        put("latitude", 21.3890824);
        put("longitude", 39.8579118);
    }}),
    EGYPT(5,"Egyptian General Authority of Survey",new HashMap<>(){{
        put(TimeName.FAJR, 19.5);
        put(TimeName.ISHA, 17.5);
    }},new HashMap<>(){{
        put("latitude", 30.0444196);
        put("longitude", 31.2357116);
    }}),
    TEHRAN(7,"Institute of Geophysics, University of Tehran",new HashMap<>(){{
        put(TimeName.FAJR, 17.7);
        put(TimeName.ISHA, 14.0);
        put(TimeName.MAGHRIB, 4.5);
        put(TimeName.MIDNIGHT, MidNightMode.JAFARI);
    }},new HashMap<>(){{
        put("latitude", 35.6891975);
        put("longitude", 51.3889736);
    }}),
    GULF(8,"Gulf Region",new HashMap<>(){{
        put(TimeName.FAJR, 19.5);
        put(TimeName.ISHA, "90 min");
    }},new HashMap<>(){{
        put("latitude", 24.1323638);
        put("longitude", 53.3199527);
    }}),
    KUWAIT(9,"Kuwait",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.ISHA, 17.5);
    }},new HashMap<>(){{
        put("latitude", 29.375859);
        put("longitude", 47.9774052);
    }}),
    QATAR(10,"Qatar",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.ISHA, "90 min");
    }},new HashMap<>(){{
        put("latitude", 25.2854473);
        put("longitude", 51.5310398);
    }}),
    SINGAPORE(11,"Majlis Ugama Islam Singapura, Singapore",new HashMap<>(){{
        put(TimeName.FAJR, 20.0);
        put(TimeName.ISHA, 18.0);
    }},new HashMap<>(){{
        put("latitude", 1.352083);
        put("longitude", 103.819836);
    }}),
    FRANCE(12,"Union Organization Islamic de France",new HashMap<>(){{
        put(TimeName.FAJR, 12.0);
        put(TimeName.ISHA, 12.0);
    }},new HashMap<>(){{
        put("latitude", 48.856614);
        put("longitude", 2.3522219);
    }}),
    TURKEY(13,"Diyanet İşleri Başkanlığı, Turkey (experimental)",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.ISHA, 17.0);
    }},new HashMap<>(){{
        put("latitude", 39.9333635);
        put("longitude", 32.8597419);
    }}),
    RUSSIA(14,"Spiritual Administration of Muslims of Russia",new HashMap<>(){{
        put(TimeName.FAJR, 16.0);
        put(TimeName.ISHA, 15.0);
    }},new HashMap<>(){{
        put("latitude", 54.73479099999999);
        put("longitude", 55.9578555);
    }}),
    MOONSIGHTING(15,"Moonsighting Committee Worldwide (Moonsighting.com)",new HashMap<>(){{
        put(TimeName.SHAFAQ, ShafaqMethod.GENERAL);
    }},null),
    DUBAI(16,"Dubai (experimental)",new HashMap<>(){{
        put(TimeName.FAJR, 18.2);
        put(TimeName.ISHA, 18.2);
    }},new HashMap<>(){{
        put("latitude", 25.0762677);
        put("longitude", 55.087404);
    }}),
    JAKIM(17,"Jabatan Kemajuan Islam Malaysia (JAKIM)",new HashMap<>(){{
        put(TimeName.FAJR, 20.0);
        put(TimeName.ISHA, 18.0);
    }},new HashMap<>(){{
        put("latitude", 3.139003);
        put("longitude", 101.686855);
    }}),
    TUNISIA(18,"Tunisia",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.ISHA, 18.0);
    }},new HashMap<>(){{
        put("latitude", 36.8064948);
        put("longitude", 10.1815316);
    }}),
    ALGERIA(19,"Algeria",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.ISHA, 17.0);
    }},new HashMap<>(){{
        put("latitude", 36.753768);
        put("longitude", 3.0587561);
    }}),
    KEMENAG(20,"Kementerian Agama Republik Indonesia",new HashMap<>(){{
        put(TimeName.FAJR, 20.0);
        put(TimeName.ISHA, 18.0);
    }},new HashMap<>(){{
        put("latitude", -6.2087634);
        put("longitude", 106.845599);
    }}),
    MOROCCO(21,"Morocco",new HashMap<>(){{
        put(TimeName.FAJR, 19.0);
        put(TimeName.ISHA, 17.0);
    }},new HashMap<>(){{
        put("latitude", 33.9715904);
        put("longitude", -6.8498129);
    }}),
    PORTUGAL(22,"Comunidade Islamica de Lisboa",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.MAGHRIB, "3 min");
        put(TimeName.ISHA, "77 min");
    }},new HashMap<>(){{
        put("latitude", 38.7222524);
        put("longitude", -9.1393366);
    }}),
    JORDAN(23,"Ministry of Awqaf, Islamic Affairs and Holy Places, Jordan",new HashMap<>(){{
        put(TimeName.FAJR, 18.0);
        put(TimeName.MAGHRIB, "5 min");
        put(TimeName.ISHA, 18.0);
    }},new HashMap<>(){{ // Amman, Jordan
        put("latitude", 31.9461222);
        put("longitude", 35.923844);
    }}),
    CUSTOM(99,"Custom",new HashMap<>(){{
        put(TimeName.FAJR, 15.0);
        put(TimeName.ISHA, 15.0);
    }},null);


    private Integer id;
    private String name;
    private Map<TimeName, Object> params;
    private Map<String, Double> location;

    Method(Integer id,
           String name,
           Map<TimeName, Object> params,
           Map<String, Double> location) {
        this.id = id;
        this.name = name;
        this.params=params;
        this.location=location;
    }

    /**
     * Set the Fajr Angle
     *
     * @param angle 18.0 or 18.5 for degrees
     */
    public void setFajrAngle(Object angle) {
        this.params.put(TimeName.FAJR, angle);
    }

    /**
     * Set Maghrib angle or minutes after sunset. Example 18.0 or 18.5 or '20 min'
     *
     * @param angleOrMinsAfterSunset angleOrMinsAfterSunset
     */
    public void setMaghribAngleOrMins(Object angleOrMinsAfterSunset) {
        this.params.put(TimeName.MAGHRIB, angleOrMinsAfterSunset);
    }

    /**
     * Set Isha angle or mins after Maghrib. Example 18.0 or 18.5 or '90 min'
     *
     * @param angleOrMinsAfterMaghrib angleOrMinsAfterMaghrib
     */
    public void setIshaAngleOrMins(Object angleOrMinsAfterMaghrib) {
        this.params.put(TimeName.ISHA, angleOrMinsAfterMaghrib);
    }

    public Map<TimeName, Object> getParams() {
        return this.equals(CUSTOM) || this.equals(MOONSIGHTING)? new HashMap<>(params): Map.copyOf(params);
    }


}


