package edu.licenta.eniko.sqlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import edu.licenta.eniko.appclient.*;
import edu.licenta.eniko.sqlite.HomeManagementDBContract;

/**
 * Created by Eniko on 8/3/2015.
 */

@DatabaseTable(tableName = HomeManagementDBContract.SensorToModuleEntries.TABLE_NAME)
public class SensorToModule {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = HomeManagementDBContract.SensorToModuleEntries.COLUMN_NAME_SENSOR_ID)
    private Sensor sensor;

    @DatabaseField(foreign = true, columnName = HomeManagementDBContract.SensorToModuleEntries.COLUMN_NAME_MODULE_ID)
    private Module module;

    SensorToModule() {
    }

    public SensorToModule(Module module, Sensor sensor) {
        this.module = module;
        this.sensor = sensor;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
