//package edu.licenta.eniko.sqlite.helper;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.licenta.eniko.sqlite.HomeManagementDBContract;
//import edu.licenta.eniko.sqlite.model.Module;
//import edu.licenta.eniko.sqlite.model.ModuleType;
//import edu.licenta.eniko.sqlite.model.Room;
//import edu.licenta.eniko.util.Converter;
//
///**
// * Created by Eniko on 6/10/2015.
// */
//public class DatabaseHelperOld extends SQLiteOpenHelper {
//
//    private static final String LOG = "DatabaseHelperOld";
//    private static final String TEXT_TYPE = " TEXT";
//    private static final String COMMA_SEP = ",";
//    public static final String INTEGER = " integer";
//    public static final String PRIMARY_KEY = " PRIMARY KEY";
//    public static final String DATETIME = " DATETIME";
//
//    private static final String SQL_CREATE_MODULE =
//            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.ModuleEntries.TABLE_NAME + " (" +
//                    HomeManagementDBContract.ModuleEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
//                   ");";
//
//    private static final String SQL_CREATE_ROOM =
//            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.RoomEntries.TABLE_NAME + " (" +
//                    HomeManagementDBContract.RoomEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
//                    HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME + TEXT_TYPE +  ");";
//    private static final String SQL_CREATE_SENSOR =
//            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.SensorEntries.TABLE_NAME + " (" +
//                    HomeManagementDBContract.SensorEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
//                    " FOREIGN KEY ("+ HomeManagementDBContract.SensorEntries.COLUMN_NAME_ROOM_ID+") REFERENCES "+ HomeManagementDBContract.RoomEntries.TABLE_NAME+" ("+ HomeManagementDBContract.RoomEntries._ID+") "+
//                ");";
//    private static final String SQL_CREATE_VALUE =
//            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.ValueEntries.TABLE_NAME + " (" +
//                    HomeManagementDBContract.ValueEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
//                    HomeManagementDBContract.ValueEntries.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
//                    HomeManagementDBContract.ValueEntries.COLUMN_NAME_UM+ TEXT_TYPE +
//                    ");";
//
//    private static final String SQL_CREATE_SENSOR_TO_MODULE =
//            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.SensorToModuleEntries.TABLE_NAME + " (" +
//                    HomeManagementDBContract.SensorToModuleEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
//                    " FOREIGN KEY ("+ HomeManagementDBContract.SensorToModuleEntries.COLUMN_NAME_MODULE_ID+") REFERENCES "+ HomeManagementDBContract.ModuleEntries.TABLE_NAME+" ("+ HomeManagementDBContract.ModuleEntries._ID+") "+ COMMA_SEP +
//                    " FOREIGN KEY ("+ HomeManagementDBContract.SensorToModuleEntries.COLUMN_NAME_SENSOR_ID+") REFERENCES "+ HomeManagementDBContract.SensorEntries.TABLE_NAME+" ("+ HomeManagementDBContract.SensorEntries._ID+") "+
//                    ");";
//
//
//    private static final String SQL_CREATE_VALUE_TO_SENSOR =
//            "CREATE TABLE IF NOT EXISTS " + HomeManagementDBContract.ValueToSensorEntries.TABLE_NAME + " (" +
//                    HomeManagementDBContract.ValueToSensorEntries._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
//                    HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_VALUE + INTEGER + PRIMARY_KEY + COMMA_SEP +
//                    HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_DATE + DATETIME + PRIMARY_KEY + COMMA_SEP +
//                    " FOREIGN KEY ("+ HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_VALUE_ID+") REFERENCES "+ HomeManagementDBContract.ModuleEntries.TABLE_NAME+" ("+ HomeManagementDBContract.ModuleEntries._ID+") "+ COMMA_SEP +
//                    " FOREIGN KEY ("+ HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_SENSOR_ID+") REFERENCES "+ HomeManagementDBContract.SensorEntries.TABLE_NAME+" ("+ HomeManagementDBContract.SensorEntries._ID+") "+
//                    ");";
//
//    private static final String SQL_DELETE_ROOM =
//            "DROP TABLE IF EXISTS " + HomeManagementDBContract.RoomEntries.TABLE_NAME;
//
//    private static final String SQL_DELETE_SENSOR =
//            "DROP TABLE IF EXISTS " + HomeManagementDBContract.SensorEntries.TABLE_NAME;
//
//    private static final String SQL_DELETE_VALUE =
//            "DROP TABLE IF EXISTS " + HomeManagementDBContract.ValueEntries.TABLE_NAME;
//
//    private static final String SQL_DELETE_MODULE =
//            "DROP TABLE IF EXISTS " + HomeManagementDBContract.ModuleEntries.TABLE_NAME;
//
//    private static final String SQL_DELETE_SENSOR_TO_MODULE =
//            "DROP TABLE IF EXISTS " + HomeManagementDBContract.SensorToModuleEntries.TABLE_NAME;
//
//    private static final String SQL_DELETE_VALUES_TO_MODULE =
//            "DROP TABLE IF EXISTS " + HomeManagementDBContract.ValueToSensorEntries.TABLE_NAME;
//
//    // If you change the database schema, you must increment the database version.
//    public static final int DATABASE_VERSION = 2;
//    public static final String DATABASE_NAME = "HomeManagementSystem.db";
//
//    public DatabaseHelperOld(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ROOM);
//        db.execSQL(SQL_DELETE_SENSOR);
//        db.execSQL(SQL_CREATE_MODULE);
//        db.execSQL(SQL_CREATE_VALUE);
//        db.execSQL(SQL_CREATE_SENSOR_TO_MODULE);
//        db.execSQL(SQL_CREATE_VALUE_TO_SENSOR);
//
//
//    }
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // This database is only a cache for online data, so its upgrade policy is
//        // to simply to discard the data and start over
//
//        db.execSQL(SQL_DELETE_VALUES_TO_MODULE);
//        db.execSQL(SQL_DELETE_SENSOR_TO_MODULE);
//        db.execSQL(SQL_DELETE_ROOM);
//        db.execSQL( SQL_DELETE_SENSOR);
//        db.execSQL(SQL_DELETE_MODULE);
//        db.execSQL(SQL_DELETE_VALUE);
//
//        onCreate(db);
//    }
//
//    public void deleteAll(SQLiteDatabase db) {
//        // This database is only a cache for online data, so its upgrade policy is
//        // to simply to discard the data and start over
//        db.execSQL(SQL_DELETE_VALUES_TO_MODULE);
//        db.execSQL(SQL_DELETE_SENSOR_TO_MODULE);
//        db.execSQL(SQL_DELETE_ROOM);
//        db.execSQL( SQL_DELETE_SENSOR);
//        db.execSQL(SQL_DELETE_MODULE);
//        db.execSQL(SQL_DELETE_VALUE);
//    }
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpgrade(db, oldVersion, newVersion);
//    }
//
//    public void createModuleType(ModuleType moduleType) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(HomeManagementDBContract.._ID, moduleType.getId());
//        values.put(HomeManagementDBContract.FeedType.COLUMN_NAME_TYPE, moduleType.getType());
//
//        // insert row
//        db.insert(HomeManagementDBContract.FeedType.TABLE_NAME, null, values);
//    }
//
//    public ModuleType getModuleType(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.FeedType.TABLE_NAME + " WHERE "
//                + HomeManagementDBContract.FeedType._ID + " = " + id;
//
//        Log.e(LOG, selectQuery);
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
//        ModuleType mt= new ModuleType();
//        mt.setId(c.getInt(c.getColumnIndex(HomeManagementDBContract.FeedType._ID)));
//        mt.setType((c.getString(c.getColumnIndex(HomeManagementDBContract.FeedType.COLUMN_NAME_TYPE))));
//
//        return mt;
//    }
//
//
//    public List<ModuleType> getAllModuleTypes() {
//        List<ModuleType> moduleTypes = new ArrayList<ModuleType>();
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.FeedType.TABLE_NAME;
//
//        Log.e(LOG, selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                ModuleType mt = new ModuleType();
//                mt.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.FeedType._ID))));
//                mt.setType((c.getString(c.getColumnIndex(HomeManagementDBContract.FeedType.COLUMN_NAME_TYPE))));
//
//                // adding to todo list
//                moduleTypes.add(mt);
//            } while (c.moveToNext());
//        }
//
//        return moduleTypes;
//    }
//
//    public void createRoom(Room room) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(HomeManagementDBContract.RoomEntries._ID, room.getId());
//        values.put(HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME, room.getName());
//
//        // insert row
//        db.insert(HomeManagementDBContract.RoomEntries.TABLE_NAME, null, values);
//    }
//
//    public Room getRoom(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.RoomEntries.TABLE_NAME + " WHERE "
//                + HomeManagementDBContract.RoomEntries._ID + " = " + id;
//
//        Log.e(LOG, selectQuery);
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
//        Room r = new Room();
//        r.setId(c.getInt(c.getColumnIndex(HomeManagementDBContract.RoomEntries._ID)));
//        r.setName(c.getString(c.getColumnIndex(HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME)));
//
//        return r;
//    }
//
//    public List<Room> getAllRooms() {
//        List<Room> rooms = new ArrayList<Room>();
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.RoomEntries.TABLE_NAME;
//
//        Log.e(LOG, selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Room r = new Room();
//                r.setId(c.getInt((c.getColumnIndex(HomeManagementDBContract.RoomEntries._ID))));
//                r.setName((c.getString(c.getColumnIndex(HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME))));
//
//                rooms.add(r);
//            } while (c.moveToNext());
//        }
//
//        return rooms;
//    }
//
//
//    public void createModule(Module module) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(HomeManagementDBContract.ModuleEntries._ID, module.getId());
//        values.put(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_VALUE, module.getValue());
//        values.put(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_DATE, String.valueOf(module.getReceived_at()));
//        values.put(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_ROOM_ID, module.getRoom().getId());
//        values.put(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_TYPE, module.getType().getId());
//
//        // insert row
//        if(db.insertOrThrow(HomeManagementDBContract.ModuleEntries.TABLE_NAME, null, values)==-1){
//            createDataHistory(getModule(module.getId()));
//            createModule(module);
//        }
//    }
//
//    public Module getModule(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ModuleEntries.TABLE_NAME + " WHERE "
//                + HomeManagementDBContract.ModuleEntries._ID + " = " + id;
//
//        Log.e(LOG, selectQuery);
//
//        Cursor c = db.rawQuery(selectQuery, null);
//        Module m = null;
//        if (c != null && c.getCount()>0) {
//            c.moveToFirst();
//
//            m = new Module();
//            m.setId(c.getInt(c.getColumnIndex(HomeManagementDBContract.ModuleEntries._ID)));
//            m.setValue(c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_VALUE)));
//            m.setReceived_at(Converter.stringToDate(c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_DATE))));
//            m.setRoom(getRoom(c.getInt(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_ROOM_ID))));
//            m.setType(getModuleType(c.getInt(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_TYPE))));
//        }
//        return m;
//    }
//
//    public List<Module> getAllModules() {
//        List<Module> modules = new ArrayList<Module>();
//        String selectQuery = "SELECT  * FROM " + HomeManagementDBContract.ModuleEntries.TABLE_NAME;
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
//                m.setReceived_at(Converter.stringToDate(c.getString(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_DATE))));
//                m.setRoom(getRoom(c.getInt(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_ROOM_ID))));
//               m.setType(getModuleType(c.getInt(c.getColumnIndex(HomeManagementDBContract.ModuleEntries.COLUMN_NAME_TYPE))));
//
//                modules.add(m);
//            } while (c.moveToNext());
//        }
//
//        return modules;
//    }
//
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
//
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
//
//    public void removeModule(Module history) {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        db.delete(HomeManagementDBContract.ModuleEntries.TABLE_NAME, HomeManagementDBContract.ModuleEntries._ID + "=" + history.getId(),null);
//    }
//}
