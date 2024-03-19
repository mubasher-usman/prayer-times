package org.mubasherusman.prayertimes.constants;

/**
 * This is the school of thought or Islamic jurisprudence to determine
 * ASAR prayer shadow size.
 */
public enum Fiqh {

    STANDARD(1.0,"Malaki, Shafi, Humbali"),
    HANAFI(2.0,"Hanafi");

    private final double id;
    private final String name;

    Fiqh(double id,String name){
        this.id = id;
        this.name=name;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
