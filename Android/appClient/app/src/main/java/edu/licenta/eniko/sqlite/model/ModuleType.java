package edu.licenta.eniko.sqlite.model;

/**
 * Created by Eniko on 6/11/2015.
 */
public class ModuleType {

    private int id;
    private String type;

    public ModuleType() {
    }

    public ModuleType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public ModuleType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
