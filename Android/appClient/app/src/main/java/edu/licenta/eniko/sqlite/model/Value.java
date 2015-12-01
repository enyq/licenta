package edu.licenta.eniko.sqlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import edu.licenta.eniko.sqlite.HomeManagementDBContract;
import edu.licenta.eniko.util.UnitsOfMeasurementEnum;

@DatabaseTable(tableName = HomeManagementDBContract.ValueEntries.TABLE_NAME)
public class Value {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = HomeManagementDBContract.ValueEntries.COLUMN_NAME_NAME)
    private String name;

    @DatabaseField(columnName = HomeManagementDBContract.ValueEntries.COLUMN_NAME_UM)
    private String um;

    public Value() {
    }

    public Value(String um, String name) {
        this.um = um;
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }
}