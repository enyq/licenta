package edu.licenta.eniko.sqlite.model;

import edu.licenta.eniko.util.UnitsOfMeasurementEnum;


public class Value {

    private int id;
    private String name;
    private UnitsOfMeasurementEnum um;

    public Value() {
    }

    public Value(int id) {
        this.id = id;
    }

    public Value(int id, String name, UnitsOfMeasurementEnum um) {
        this.id = id;
        this.name = name;
        this.um = um;
    }

    public Value(String name, UnitsOfMeasurementEnum um) {
        this.name = name;
        this.um = um;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitsOfMeasurementEnum getUm() {
        return um;
    }

    public void setUm(UnitsOfMeasurementEnum um) {
        this.um = um;
    }
}