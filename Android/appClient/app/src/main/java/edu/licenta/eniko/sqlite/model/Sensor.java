package edu.licenta.eniko.sqlite.model;

/**
 * Created by Eniko on 6/14/2015.
 */
public class Sensor {

    private int id;
    private Room room;


    public Sensor() {
    }

    public Sensor(int id) {
        this.id = id;
    }

    public Sensor(Room room) {
        this.room = room;
    }

    public Sensor(int id, Room room) {
        this.id = id;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
