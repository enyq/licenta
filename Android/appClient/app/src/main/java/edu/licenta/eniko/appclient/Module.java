package edu.licenta.eniko.appclient;

/**
 * Created by Eniko on 5/24/2015.
 */
public class Module {
    String uuid;
    String name;
    String value;

    public Module() {
    }

    public Module(String uuid, String name, String value) {
        this.uuid = uuid;
        this.name = name;
        this.value = value;
    }

    public String getUUId() {
        return uuid;
    }

    public void setUUId(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
