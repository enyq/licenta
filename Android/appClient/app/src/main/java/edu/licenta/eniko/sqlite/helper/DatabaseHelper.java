package edu.licenta.eniko.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import edu.licenta.eniko.sqlite.HomeManagementDBContract;
import edu.licenta.eniko.sqlite.model.Module;
import edu.licenta.eniko.sqlite.model.Room;
import edu.licenta.eniko.sqlite.model.Sensor;
import edu.licenta.eniko.sqlite.model.Value;
import edu.licenta.eniko.sqlite.model.ValueOfSensor;
import edu.licenta.eniko.util.Converter;

/**
 * Created by Eniko on 6/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String INTEGER = " integer";
    public static final String PRIMARY_KEY = " PRIMARY KEY";
    public static final String DATETIME = " DATETIME";

    private static final String SQL_CREATE_MODULE =
            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.ModuleEntries.TABLE_NAME + " ( " +
                    HomeManagementDBContract.ModuleEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
                    HomeManagementDBContract.ModuleEntries.COLUMN_NAME_SERIAL_NUMBER + TEXT_TYPE +
                    ");";
    private static final String SQL_CREATE_ROOM =
            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.RoomEntries.TABLE_NAME + " ( " +
                    HomeManagementDBContract.RoomEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
                    HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME + TEXT_TYPE +
                    ");";
    private static final String SQL_CREATE_SENSOR =
            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.SensorEntries.TABLE_NAME + " ( " +
                    HomeManagementDBContract.SensorEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
                    HomeManagementDBContract.SensorEntries.COLUMN_NAME_MODULE_ID+ INTEGER +" REFERENCES "+
                    HomeManagementDBContract.ModuleEntries.TABLE_NAME+" ("+ HomeManagementDBContract.ModuleEntries._ID+") "+ COMMA_SEP +
                    HomeManagementDBContract.SensorEntries.COLUMN_NAME_ROOM_ID + INTEGER +" REFERENCES "+
                    HomeManagementDBContract.RoomEntries.TABLE_NAME+" ("+ HomeManagementDBContract.RoomEntries._ID+") "+ COMMA_SEP +
                    HomeManagementDBContract.SensorEntries.COLUMN_NAME_VALUE_ID + INTEGER +" REFERENCES "+
                    HomeManagementDBContract.ValueEntries.TABLE_NAME+" ("+ HomeManagementDBContract.ValueEntries._ID+") "+
                ");";
    private static final String SQL_CREATE_VALUE =
            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.ValueEntries.TABLE_NAME + " ( " +
                    HomeManagementDBContract.ValueEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
                    HomeManagementDBContract.ValueEntries.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    HomeManagementDBContract.ValueEntries.COLUMN_NAME_UM+ TEXT_TYPE +
                    ");";
    private static final String SQL_CREATE_VALUE_OF_SENSOR =
            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.ValueToSensorEntries.TABLE_NAME + " ( " +
                    HomeManagementDBContract.ValueToSensorEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
                    HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_VALUE + INTEGER  + COMMA_SEP +
                    HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_DATE + DATETIME  + COMMA_SEP +
                    HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_SENSOR_ID+ INTEGER +" REFERENCES "+
                    HomeManagementDBContract.SensorEntries.TABLE_NAME+" ("+ HomeManagementDBContract.SensorEntries._ID+") "+
                    ");";

    private static final String SQL_DELETE_ROOM =
            "DROP TABLE IF EXISTS " + HomeManagementDBContract.RoomEntries.TABLE_NAME;

    private static final String SQL_DELETE_SENSOR =
            "DROP TABLE IF EXISTS " + HomeManagementDBContract.SensorEntries.TABLE_NAME;

    private static final String SQL_DELETE_VALUE =
            "DROP TABLE IF EXISTS " + HomeManagementDBContract.ValueEntries.TABLE_NAME;

    private static final String SQL_DELETE_MODULE =
            "DROP TABLE IF EXISTS " + HomeManagementDBContract.ModuleEntries.TABLE_NAME;

    private static final String SQL_DELETE_SENSOR_TO_MODULE =
            "DROP TABLE IF EXISTS " + HomeManagementDBContract.SensorToModuleEntries.TABLE_NAME;

    private static final String SQL_DELETE_VALUES_OF_SENSOR =
            "DROP TABLE IF EXISTS " + HomeManagementDBContract.ValueToSensorEntries.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db_hms.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ROOM);
        db.execSQL(SQL_CREATE_SENSOR);
        db.execSQL(SQL_CREATE_MODULE);
        db.execSQL(SQL_CREATE_VALUE);
        db.execSQL(SQL_CREATE_VALUE_OF_SENSOR);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        db.execSQL(SQL_DELETE_VALUES_OF_SENSOR);
        db.execSQL(SQL_DELETE_SENSOR);
        db.execSQL(SQL_DELETE_VALUE);
        db.execSQL(SQL_DELETE_ROOM);
        db.execSQL(SQL_DELETE_MODULE);

        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    @Override
    public void onConfigure(SQLiteDatabase database) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            database.setForeignKeyConstraintsEnabled(true);
        } else {
            database.execSQL("PRAGMA foreign_keys=ON");
        }
    }
    public void deleteAll(SQLiteDatabase db) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_VALUES_OF_SENSOR);
        db.execSQL(SQL_DELETE_SENSOR_TO_MODULE);
        db.execSQL(SQL_DELETE_ROOM);
        db.execSQL(SQL_DELETE_SENSOR);
        db.execSQL(SQL_DELETE_MODULE);
        db.execSQL(SQL_DELETE_VALUE);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long createModule(Module module) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_SERIAL_NUMBER, module.getSerialNumber());

        // insert row
        return db.insert(HomeManagementDBContract.ModuleEntries.TABLE_NAME, null, values);
    }

    public Module getModule(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ModuleEntries.TABLE_NAME + " WHERE "
                + HomeManagementDBContract.ModuleEntries._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Module m = new Module();
        if (c != null && c.moveToFirst()) {

            m.setId(c.getInt(c.getColumnIndex(HomeManagementDBContract.ModuleEntries._ID)));
            m.setSerialNumber((c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_SERIAL_NUMBER))));
        }
        return m;
    }


    public List<Module> getAllModules() {
        List<Module> modules = new ArrayList<Module>();
        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ModuleEntries.TABLE_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Module m = new Module();
                m.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.ModuleEntries._ID))));
                m.setSerialNumber((c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_SERIAL_NUMBER))));

                // adding to todo list
                modules.add(m);
            } while (c.moveToNext());
        }

        return modules;
    }

    public void removeModule(Module module) {

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(HomeManagementDBContract.ModuleEntries.TABLE_NAME, HomeManagementDBContract.ModuleEntries._ID + "=" + module.getId(),null);
    }

    public long createRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME, room.getName());

        // insert row
      return  db.insert(HomeManagementDBContract.RoomEntries.TABLE_NAME, null, values);
    }

    public Room getRoom(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.RoomEntries.TABLE_NAME + " WHERE "
                + HomeManagementDBContract.RoomEntries._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Room r = new Room();

        if (c != null && c.moveToFirst()) {
            r.setId(c.getInt(c.getColumnIndex(HomeManagementDBContract.RoomEntries._ID)));
            r.setName(c.getString(c.getColumnIndex(HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME)));
        }
        return r;
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<Room>();
        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.RoomEntries.TABLE_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Room r = new Room();
                r.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.RoomEntries._ID))));
                r.setName((c.getString(c.getColumnIndex(HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME))));

                rooms.add(r);
            } while (c.moveToNext());
        }

        return rooms;
    }

    public void removeRoom(Room room) {

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(HomeManagementDBContract.RoomEntries.TABLE_NAME, HomeManagementDBContract.RoomEntries._ID + "=" + room.getId(),null);
    }

//    public List<Module> getAllModulesByType(String type) {
//        List<Module> modules = new ArrayList<Module>();
//
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ModuleEntries.TABLE_NAME + " m, "
//                + HomeManagementDBContract.FeedType.TABLE_NAME + " t" + " INNER JOIN ON m."+ HomeManagementDBContract.ModuleEntries.COLUMN_NAME_TYPE + "= t."+ HomeManagementDBContract.FeedType._ID +
//                "WHERE t."
//                + HomeManagementDBContract.FeedType.COLUMN_NAME_TYPE + " = '" + type;
//
//        Log.e(LOG, selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Module m = new Module();
//                m.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.ModuleEntries._ID))));
//                m.setValue(c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_VALUE)));
//                m.setReceived_at(Date.valueOf(c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_DATE))));
//                m.setRoom(getRoom(c.getInt(c.getColumnIndex(HomeManagementDBContract.RoomEntries._ID))));
//                m.setType(getModuleType(c.getInt(c.getColumnIndex(HomeManagementDBContract.FeedType._ID))));
//
//                modules.add(m);
//            } while (c.moveToNext());
//        }
//
//        return modules;
//    }

//    public void createDataHistory(Module module) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_MODULE_ID, module.getId());
//        values.put(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_VALUE, module.getValue());
//        values.put(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_DATE, String.valueOf(module.getReceived_at()));
//        values.put(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_ROOM_ID, module.getRoom().getId());
//        values.put(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_TYPE, module.getType().getId());
//
//        // insert row
//        db.insert(HomeManagementDBContract.FeedDataHistory.TABLE_NAME, null, values);
//    }
//    public List<Module> getAllDataHistory() {
//        List<Module> modules = new ArrayList<Module>();
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.FeedDataHistory.TABLE_NAME;
//
//        Log.e(LOG, selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Module m = new Module();
//                m.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_MODULE_ID))));
//                m.setValue(c.getString(c.getColumnIndex(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_VALUE)));
//                m.setReceived_at(Converter.stringToDate(c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_DATE))));
//                m.setRoom(getRoom(c.getInt(c.getColumnIndex(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_ROOM_ID))));
//                m.setType(getModuleType(c.getInt(c.getColumnIndex(HomeManagementDBContract.FeedDataHistory.COLUMN_NAME_TYPE))));
//
//                modules.add(m);
//            } while (c.moveToNext());
//        }
//
//        return modules;
//    }

    public long createSensor(Sensor sensor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Log.i("added sensor value:", "Room:"+sensor.getRoom().getId() + "Value:"+ sensor.getValue().getId() + "Module:" + sensor.getModule().getId());
        values.put(HomeManagementDBContract.SensorEntries.COLUMN_NAME_MODULE_ID, sensor.getModule().getId());
        values.put(HomeManagementDBContract.SensorEntries.COLUMN_NAME_ROOM_ID, sensor.getRoom().getId());
        values.put(HomeManagementDBContract.SensorEntries.COLUMN_NAME_VALUE_ID, sensor.getValue().getId());

        // insert row
        return db.insert(HomeManagementDBContract.SensorEntries.TABLE_NAME, null, values);

    }

    public Sensor getSensor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.SensorEntries.TABLE_NAME + " WHERE "
                + HomeManagementDBContract.SensorEntries._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Sensor s = new Sensor();
        if (c != null && c.moveToFirst()) {

            s.setId(c.getInt(c.getColumnIndex(HomeManagementDBContract.SensorEntries._ID)));
            s.setModule(getModule(c.getInt(c.getColumnIndex(HomeManagementDBContract.SensorEntries.COLUMN_NAME_MODULE_ID))));
            s.setRoom(getRoom(c.getInt(c.getColumnIndex(HomeManagementDBContract.SensorEntries.COLUMN_NAME_ROOM_ID))));
            s.setValue(getValue(c.getInt(c.getColumnIndex(HomeManagementDBContract.SensorEntries.COLUMN_NAME_VALUE_ID))));
        }
        return s;
    }

    public List<Sensor> getAllSensors() {
        List<Sensor> sensors = new ArrayList<Sensor>();
        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.SensorEntries.TABLE_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Sensor s = new Sensor();
                s.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.SensorEntries._ID))));
                s.setModule(getModule(c.getInt(c.getColumnIndex(HomeManagementDBContract.SensorEntries.COLUMN_NAME_MODULE_ID))));
                s.setRoom(getRoom(c.getInt(c.getColumnIndex(HomeManagementDBContract.SensorEntries.COLUMN_NAME_ROOM_ID))));
                s.setValue(getValue(c.getInt(c.getColumnIndex(HomeManagementDBContract.SensorEntries.COLUMN_NAME_VALUE_ID))));
                sensors.add(s);
            } while (c.moveToNext());
        }

        return sensors;
    }

    public void removeSensor(Sensor sensor) {

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(HomeManagementDBContract.SensorEntries.TABLE_NAME, HomeManagementDBContract.SensorEntries._ID + "=" + sensor.getId(), null);
    }


    public long createValue(Value value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HomeManagementDBContract.ValueEntries.COLUMN_NAME_NAME, value.getName());
        values.put(HomeManagementDBContract.ValueEntries.COLUMN_NAME_UM, value.getUm().toString());

        // insert row
       return db.insert(HomeManagementDBContract.ValueEntries.TABLE_NAME, null, values);
    }

    public Value getValue(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ValueEntries.TABLE_NAME + " WHERE "
                + HomeManagementDBContract.ValueEntries._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        Value v = new Value();
        if (c != null && c.moveToFirst()) {

            v.setId(c.getInt(c.getColumnIndex(HomeManagementDBContract.ValueEntries._ID)));
            v.setName(c.getString(c.getColumnIndex(HomeManagementDBContract.ValueEntries.COLUMN_NAME_NAME)));
            v.setUm(c.getString(c.getColumnIndex(HomeManagementDBContract.ValueEntries.COLUMN_NAME_UM)));
            }
        return v;
    }

    public List<Value> getAllValues() {
        List<Value> values = new ArrayList<Value>();
        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ValueEntries.TABLE_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Value v = new Value();
                v.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.ValueEntries._ID))));
                v.setName(c.getString(c.getColumnIndex(HomeManagementDBContract.ValueEntries.COLUMN_NAME_NAME)));
                v.setUm(c.getString(c.getColumnIndex(HomeManagementDBContract.ValueEntries.COLUMN_NAME_UM)));
                values.add(v);
            } while (c.moveToNext());
        }

        return values;
    }

    public void removeValue(Value value) {

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(HomeManagementDBContract.ValueEntries.TABLE_NAME, HomeManagementDBContract.ValueEntries._ID + "=" + value.getId(), null);
    }

    public long createValueOfSensor(ValueOfSensor valueOfSensor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(HomeManagementDBContract.SensorEntries._ID, room.getId());
        values.put(HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_SENSOR_ID, valueOfSensor.getSensor().getId());
        values.put(HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_VALUE, valueOfSensor.getData());
        values.put(HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_DATE, valueOfSensor.getReceiveDate().toString());

        // insert row
       return db.insert(HomeManagementDBContract.ValueToSensorEntries.TABLE_NAME, null, values);
    }

    public List<ValueOfSensor> getAllValuesOfSensorBySensor(int sensorId) {
        List<ValueOfSensor> valueOfSensors = new ArrayList<ValueOfSensor>();
        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ValueToSensorEntries.TABLE_NAME + " vs "
               + " INNER JOIN " + HomeManagementDBContract.SensorEntries.TABLE_NAME + " s ON vs."+ HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_SENSOR_ID + "= s."+ HomeManagementDBContract.SensorEntries._ID +
                " WHERE s."
                + HomeManagementDBContract.SensorEntries._ID + " = '" + sensorId+ "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ValueOfSensor vs = new ValueOfSensor();
                vs.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.ValueToSensorEntries._ID))));
                vs.setSensor(getSensor(c.getInt(c.getColumnIndex(HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_SENSOR_ID))));
                vs.setData(c.getLong(c.getColumnIndex(HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_VALUE)));
                vs.setReceiveDate(Converter.stringToDate(c.getString(c.getColumnIndex(HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_DATE))));

                valueOfSensors.add(vs);
            } while (c.moveToNext());
        }

        return valueOfSensors;
    }

    public Cursor fetchAllSensorsOfRoom(int roomid) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT v._id, v.name, vs._id, vs.date, vs.value, v.um FROM sensor s JOIN value v on s.valueid = v._id JOIN value_of_sensor vs on vs.sensorid = s._id where s.roomid = ?;",new String[] {String.valueOf(roomid)});
        //Cursor cursor = db.rawQuery("SELECT s._id, vs._id, vs.value FROM sensor s JOIN value_of_sensor vs on s._id = vs.sensorid",null);

//        if(cursor != null) {
//            cursor.moveToFirst();
//            Log.i("databaseHelper", "cursor not null");
//        }
        return cursor;
    }

    public Cursor fetchAllRooms() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ HomeManagementDBContract.RoomEntries.TABLE_NAME+";",null);

        return cursor;
    }
}
