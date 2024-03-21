package io.mubasherusman.prayertimes.constants;

/**
 * This is the school of thought or Islamic jurisprudence to determine
 * ASAR prayer shadow size.
 */
public enum Fiqh {

    /**
     * STANDARD
     */
    STANDARD(1.0,"Malaki, Shafi, Humbali"),
    /**
     * HANAFI
     */
    HANAFI(2.0,"Hanafi");

    private final double id;
    private final String name;

    Fiqh(double id,String name){
        this.id = id;
        this.name=name;
    }

    /**
     * Get Id
     * @return double
     */
    public double getId() {
        return id;
    }

    /**
     * Get Name
     * @return String
     */
    public String getName() {
        return name;
    }
}
