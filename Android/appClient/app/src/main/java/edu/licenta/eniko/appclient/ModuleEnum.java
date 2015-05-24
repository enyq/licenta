package edu.licenta.eniko.appclient;

/**
 * Created by Eniko on 5/24/2015.
 */
public enum ModuleEnum {

    COMFORT("Comfort", 0),
    SECURITY("Security", 1);

    private String stringValue;
    private int intValue;
    private ModuleEnum(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
