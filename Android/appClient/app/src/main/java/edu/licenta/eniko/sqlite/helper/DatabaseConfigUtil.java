package edu.licenta.eniko.sqlite.helper;

/**
 * Created by Eniko on 8/12/2015.
 */

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import edu.licenta.eniko.appclient.Module;
import edu.licenta.eniko.sqlite.model.Room;
import edu.licenta.eniko.sqlite.model.Sensor;
import edu.licenta.eniko.sqlite.model.SensorToModule;
import edu.licenta.eniko.sqlite.model.Value;
import edu.licenta.eniko.sqlite.model.ValueOfSensor;

/**
 * DatabaseConfigUtl writes a configuration file to avoid using annotation processing in runtime which is very slow
 * under Android. This gains a noticeable performance improvement.
 *
 * The configuration file is written to /res/raw/ by default. More info at: http://ormlite.com/docs/table-config
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {Room.class, Module.class, Value.class, Sensor.class, SensorToModule.class, ValueOfSensor.class};

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt",classes);
    }
}