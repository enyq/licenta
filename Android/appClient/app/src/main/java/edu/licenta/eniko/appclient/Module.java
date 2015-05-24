package edu.licenta.eniko.appclient;

/**
 * Created by Eniko on 5/24/2015.
 */
public class Module {
    int id;
    String name;
    float value;

    public Module() {
    }

    public Module(int id, String name, float value) {
        this.id = id;
        this.name = name;
        this.value = value;
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
