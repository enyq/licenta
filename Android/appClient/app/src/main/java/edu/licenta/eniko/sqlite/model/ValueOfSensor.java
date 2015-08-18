package edu.licenta.eniko.sqlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import edu.licenta.eniko.sqlite.HomeManagementDBContract;
import edu.licenta.eniko.util.UnitsOfMeasurementEnum;

/**
 * Created by Eniko on 8/3/2015.
 */

@DatabaseTable(tableName = HomeManagementDBContract.ValueToSensorEntries.TABLE_NAME)
public class ValueOfSensor {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_SENSOR_ID)
    private Sensor sensor;

    @DatabaseField(columnName = HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_VALUE)
    private long data;

    @DatabaseField(columnName = HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_DATE)
    private Date receiveDate;

    ValueOfSensor() {
    }

    public ValueOfSensor(Sensor sensor, long data, Date receiveDate) {
        this.sensor = sensor;
        this.data = data;
        this.receiveDate = receiveDate;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }
}
