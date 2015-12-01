package edu.licenta.eniko.sqlite.helper;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import edu.licenta.eniko.appclient.R;
import edu.licenta.eniko.sqlite.model.Module;
import edu.licenta.eniko.sqlite.model.Room;
import edu.licenta.eniko.sqlite.model.Sensor;
import edu.licenta.eniko.sqlite.model.SensorToModule;
import edu.licenta.eniko.sqlite.model.Value;
import edu.licenta.eniko.sqlite.model.ValueOfSensor;


/**
 * Created by Eniko on 8/12/2015.
 */
public class DatabaseHelper2 extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "hms.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 4;

    // the DAO object we use to access the SimpleData table
    private Dao<Module, Integer> moduleDao = null;
    private Dao<Room, Integer> roomDao = null;
    private Dao<Sensor, Integer> sensorDao = null;
    private Dao<SensorToModule, Integer> sensorToModuleDao = null;
    private Dao<ValueOfSensor, Integer> valueOfSensorDao = null;
    private Dao<Value, Integer> valueDao = null;

    public DatabaseHelper2(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);


    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper2.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Module.class);
            TableUtils.createTable(connectionSource, Room.class);
            TableUtils.createTable(connectionSource, Value.class);
            TableUtils.createTable(connectionSource, Sensor.class);
            TableUtils.createTable(connectionSource, ValueOfSensor.class);
            TableUtils.createTable(connectionSource, SensorToModule.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper2.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

//        // here we try inserting data in the on-create as a test
//        RuntimeExceptionDao<BaseEntity, Integer> dao = getSimpleDataDao();
//        long millis = System.currentTimeMillis();
//        // create some entries in the onCreate
//        BaseEntity simple = new BaseEntity();
//        dao.create(simple);
//        simple = new BaseEntity();
//        dao.create(simple);
//        Log.i(DatabaseHelper2.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper2.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Module.class, true);
            TableUtils.dropTable(connectionSource, Room.class, true);
            TableUtils.dropTable(connectionSource, Value.class, true);
            TableUtils.dropTable(connectionSource, Sensor.class, true);
            TableUtils.dropTable(connectionSource, ValueOfSensor.class, true);
            TableUtils.dropTable(connectionSource, SensorToModule.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper2.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our Module class. It will create it or just give the cached
     * value.
     */
    public Dao<Module, Integer> getModuleDao() throws SQLException {
        if (moduleDao == null) {
            moduleDao = getDao(Module.class);
        }
        return moduleDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our Room class. It will create it or just give the cached
     * value.
     */
    public Dao<Room, Integer> getRoomDao() throws SQLException {
        if (roomDao == null) {
            roomDao = getDao(Room.class);
        }
        return roomDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our Sensor class. It will create it or just give the cached
     * value.
     */
    public Dao<Sensor, Integer> getSensorDao() throws SQLException {
        if (sensorDao == null) {
            sensorDao = getDao(Sensor.class);
        }
        return sensorDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our Value class. It will create it or just give the cached
     * value.
     */
    public Dao<Value, Integer> getValueDao() throws SQLException {
        if (valueDao == null) {
            valueDao = getDao(Value.class);
        }
        return valueDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our SensorToModule class. It will create it or just give the cached
     * value.
     */
    public Dao<SensorToModule, Integer> getSensorToModuleDao() throws SQLException {
        if (sensorToModuleDao == null) {
            sensorToModuleDao = getDao(SensorToModule.class);
        }
        return sensorToModuleDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our ValueOfSensor class. It will create it or just give the cached
     * value.
     */
    public Dao<ValueOfSensor, Integer> getValueOfSensorDao() throws SQLException {
        if (valueOfSensorDao == null) {
            valueOfSensorDao = getDao(ValueOfSensor.class);
        }
        return valueOfSensorDao;
    }




    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        moduleDao = null;
        roomDao = null;
        sensorDao = null;
    }
}
