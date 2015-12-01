package edu.licenta.eniko.sqlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import edu.licenta.eniko.sqlite.HomeManagementDBContract;

/**
 * Created by Eniko on 6/11/2015.
 */

@DatabaseTable(tableName = HomeManagementDBContract.RoomEntries.TABLE_NAME)
public class Room {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME)
    private String name;

    public Room() {
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
