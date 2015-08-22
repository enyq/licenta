package edu.licenta.eniko.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Eniko on 6/9/2015.
 */
public final class HomeManagementDBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public HomeManagementDBContract() {}

    /* Inner class that defines the table contents */
    public static abstract class RoomEntries implements BaseColumns {
        public static final String TABLE_NAME = "room";
        public static final String COLUMN_NAME_NAME = "name";

    }

    /* Inner class that defines the table contents */
    public static abstract class ModuleEntries implements BaseColumns {
        public static final String TABLE_NAME = "module";
        public static final String COLUMN_NAME_SERIAL_NUMBER = "serialNumber";
    }

    public static abstract class ValueEntries implements BaseColumns {
        public static final String TABLE_NAME = "value";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_UM = "um";

    }

    /* Inner class that defines the table contents */
    public static abstract class SensorEntries implements BaseColumns {
        public static final String TABLE_NAME = "sensor";
        public static final String COLUMN_NAME_ROOM_ID = "roomid";
        public static final String COLUMN_NAME_VALUE_ID = "valueid";
    }

    /* Inner class that defines the table contents */
    public static abstract class SensorToModuleEntries implements BaseColumns {
        public static final String TABLE_NAME = "sensor_to_module";
        public static final String COLUMN_NAME_MODULE_ID = "moduleid";
        public static final String COLUMN_NAME_SENSOR_ID = "sensorid";
    }


    public static abstract class ValueToSensorEntries implements BaseColumns {
        public static final String TABLE_NAME = "value_of_sensor";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_SENSOR_ID = "sensorid";
    }

}