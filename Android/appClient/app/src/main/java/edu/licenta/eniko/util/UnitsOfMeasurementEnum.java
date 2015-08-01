package edu.licenta.eniko.util;

/**
 * Created by Eniko on 6/14/2015.
 */
public enum UnitsOfMeasurementEnum {

    CELSIUS("Celsius", 0),
    PERCENT("Percent", 1),
    CANDELA("Candela", 2);


    private String stringValue;
    private int intValue;
    private UnitsOfMeasurementEnum(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
