package edu.licenta.eniko.sqlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import edu.licenta.eniko.sqlite.HomeManagementDBContract;

/**
 * Created by Eniko on 6/14/2015.
 */

@DatabaseTable(tableName = HomeManagementDBContract.SensorEntries.TABLE_NAME)
public class Sensor {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = HomeManagementDBContract.SensorEntries.COLUMN_NAME_ROOM_ID)
    private Room room;

    @DatabaseField(foreign = true, columnName = HomeManagementDBContract.SensorEntries.COLUMN_NAME_VALUE_ID)
    private Value value;

    @DatabaseField(foreign = true, columnName = HomeManagementDBContract.SensorEntries.COLUMN_NAME_MODULE_ID)
    private Module module;



    public Sensor() {
    }

    public Sensor(Room room, Value value, Module module) {
        this.room = room;
        this.value = value;
        this.module = module;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Sensor( Room room) { this.room = room; }

    public Room getRoom() { return room; }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
