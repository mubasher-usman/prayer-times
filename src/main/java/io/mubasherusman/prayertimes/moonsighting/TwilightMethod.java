package io.mubasherusman.prayertimes.moonsighting;

/**
 * Twilight (Arabic Shafaq) <br>
 * An indicator to select a function of latitude and seasons for Fajr &amp; Isha.
 *
 * <p>For Isha, Imam Shafi'i, Imam Maalik, Imam Ahmad bin Hanbal, and two prominent pupils of Imam Abu-Hanifa (Imam Abu-Yusuf and Imam Muhammad) all preferred Shafaq Ahmer. Only Imam Abu-Hanifa preferred Shafaq Abyad.</p>
 *
 * <p>Moonsighting.com uses Shafaq Ahmer in summer when nights are short and Shafaq Abyad in winter, when days are short. However, Shafaq General is chosen to avoid hardship at higher latitudes, when Shafaq Abyad becomes too late in summer. Shafaq General uses Shafaq Abyad in Summer and Shafaq Ahmer in Winter. Transition from Abyad to Ahmer is used in Spring and Ahmer to Abyad in Fall. These formulas are good up to the 55degrees latitude.</p>
 */
public enum TwilightMethod {
    AHMER,ABYAD,GENERAL
}
