package edu.licenta.eniko.sqlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import edu.licenta.eniko.sqlite.HomeManagementDBContract;

/**
 * Created by Eniko on 6/11/2015.
 */

@DatabaseTable(tableName = HomeManagementDBContract.ModuleEntries.TABLE_NAME)
public class Module {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = HomeManagementDBContract.ModuleEntries.COLUMN_NAME_SERIAL_NUMBER, canBeNull = false)
    private String serialNumber;

    Module() {
    }

    public Module(String serialNumber){
        super();
        this.serialNumber = serialNumber;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
