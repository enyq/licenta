package edu.licenta.eniko.appclient;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import edu.licenta.eniko.sqlite.helper.DatabaseHelper2;
import edu.licenta.eniko.sqlite.model.Room;
import edu.licenta.eniko.sqlite.model.Module;
import edu.licenta.eniko.sqlite.model.Sensor;
import edu.licenta.eniko.sqlite.model.SensorToModule;
import edu.licenta.eniko.sqlite.model.Value;
import edu.licenta.eniko.sqlite.model.ValueOfSensor;
import edu.licenta.eniko.util.UnitsOfMeasurementEnum;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper2> {


   // DatabaseHelper dbh;
    EditText etResponse;
    TextView tvIsConnected;
    TextView temp, moduleid;
    TableRow tr1;

    private final String LOG_TAG = getClass().getSimpleName();
    private final static int MAX_NUM_TO_CREATE = 8;

    EditText dataValue;
    static ArrayList<Module> modules = null;
    TableLayout heatTable, airTable, lightingTable, securityTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  dbh = new DatabaseHelper(getApplicationContext());

//        dbh.createRoom(new Room(2,"Kitchen"));
//        dbh.createRoom(new Room(3,"Bedroom"));
//        dbh.createRoom(new Room(4,"Bathroom"));
//        dbh.createRoom(new Room(5,"Garage"));
//        dbh.createRoom(new Room(7,"Living Room"));
//
//        dbh.createModuleType(new ModuleType(1, "Comfort"));
//        dbh.createModuleType(new ModuleType(2, "Security"));


//        SQLiteDatabase db = dbh.getReadableDatabase();
//      //  dbh.deleteAll(db);
////
//        dbh.onCreate(db);
//       List<Module> modules = dbh.getAllDataHistory();
//
//        for(Module m : modules){
//            System.out.println("Module:" + m.getId()+" "+m.getValue()+" "+m.getReceived_at()+" "+m.getRoom()+ " "+ m.getType());
//        }
//        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
//
//      //  new HttpAsyncTask().execute("http://192.168.0.180:80/");
//
//        if (isConnected()) {
//            tvIsConnected.setBackgroundColor(0xFF00CC00);
//            tvIsConnected.setText("You are connected");
//        } else {
//            tvIsConnected.setText("You are NOT connected");
//        }

        temp = (TextView) findViewById(R.id.temp);
        temp.setMovementMethod(new ScrollingMovementMethod());
        doSampleDatabaseStuff("onCreate", temp);

//        TableLayout tl = (TableLayout) findViewById(R.id.heat_table);
//
//        TableRow tr_head = new TableRow(this);
//      //  tr_head.setId(10);
//        tr_head.setBackgroundColor(Color.GRAY);
//        tr_head.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//
//        TextView label_date = new TextView(this);
//        //label_date.setId();
//        label_date.setText("Camera");
//        label_date.setTextColor(Color.WHITE);
//        label_date.setPadding(5, 5, 5, 5);
//        tr_head.addView(label_date);// add the column to the table row here
//
//        TextView label_weight_kg = new TextView(this);
//       // label_weight_kg.setId(21);// define id that must be unique
//        label_weight_kg.setText("Valoare"); // set the text for the header
//        label_weight_kg.setTextColor(Color.WHITE); // set the color
//        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
//        tr_head.addView(label_weight_kg); // add the column to the table row here
//
//        tl.addView(tr_head, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//        Integer count=0;
//
//        List<Module> vmodules = dbh.getAllModules();
//
//       for(Module m : vmodules){
//           TableRow tr = new TableRow(this);
//
//           tr.setId(100 + count);
//           tr.setLayoutParams(new TableLayout.LayoutParams(
//                   TableLayout.LayoutParams.MATCH_PARENT,
//                   TableLayout.LayoutParams.WRAP_CONTENT));
//
////Create two columns to add as table data
//           // Create a TextView to add date
//           TextView labelDATE = new TextView(this);
//           labelDATE.setId(200+count);
//           labelDATE.setText(m.getRoom().getName());
//           labelDATE.setPadding(2, 0, 5, 0);
//           labelDATE.setTextColor(Color.BLACK);
//           tr.addView(labelDATE);
//           TextView labelWEIGHT = new TextView(this);
//           labelWEIGHT.setId(200+count);
//           labelWEIGHT.setText(m.getValue());
//           labelWEIGHT.setTextColor(Color.BLACK);
//           tr.addView(labelWEIGHT);
//
//// finally add this to the table row
//           tl.addView(tr, new TableLayout.LayoutParams(
//                   TableLayout.LayoutParams.MATCH_PARENT,
//                   TableLayout.LayoutParams.WRAP_CONTENT));
//           count++;
//       }
    }

    private void doSampleDatabaseStuff(String action, TextView tv) {
        // get our dao


        try {
            Dao<Module, Integer> moduleDao = getHelper().getModuleDao();
               // query for all of the data objects in the database
            Dao<Room, Integer> roomDao = getHelper().getRoomDao();
            Dao<Sensor, Integer> sensorDao = getHelper().getSensorDao();
            Dao<Value, Integer> valueDao = getHelper().getValueDao();
            Dao<SensorToModule, Integer> sensorToModuleDao = getHelper().getSensorToModuleDao();
            Dao<ValueOfSensor, Integer> valueOfSensorDao = getHelper().getValueOfSensorDao();


            //  List<BaseEntity> list = simpleDao.queryForAll();
            // our string builder for building the content-view
          StringBuilder sb = new StringBuilder();
//            sb.append("------------------------------------------\n");
//            sb.append("Deleted ids:");
//            for (BaseEntity simple : list) {
//                sb.append(' ').append(simple.getId());
//                Log.i(LOG_TAG, "writing simple(" + simple.getId() + ")");
//            }
//            sb.append('\n');
//            sb.append("------------------------------------------\n");

        Module module = new Module("254416");
        // store it in the database
        moduleDao.create(module);
        Log.i(LOG_TAG, "created module(" + module.getId() + ")");
        // output it
        sb.append('#').append(module.getId()).append(": ");
        sb.append(module).append('\n');

            Module module2 = new Module("254416");
            // store it in the database
            moduleDao.create(module2);
            Log.i(LOG_TAG, "created module(" + module2.getId() + ")");
            // output it
            sb.append('#').append(module2.getId()).append(": ");
            sb.append(module2).append('\n');

            Module module3 = new Module("254416");
            // store it in the database
            moduleDao.create(module3);
            Log.i(LOG_TAG, "created module(" + module3.getId() + ")");
            // output it
            sb.append('#').append(module3.getId()).append(": ");
            sb.append(module3).append('\n');

        Room room = new Room("Dining Room");
        // store it in the database
        roomDao.create(room);
        Log.i(LOG_TAG, "created room(" + room.getId() + ")");
        // output it
        sb.append('#').append(room.getId()).append(": ");
        sb.append(room).append('\n');

            Room room2 = new Room("Bathroom");
            // store it in the database
            roomDao.create(room2);
            Log.i(LOG_TAG, "created room(" + room2.getId() + ")");
            // output it
            sb.append('#').append(room2.getId()).append(": ");
            sb.append(room2).append('\n');

            Room room3 = new Room("Kitchen");
            // store it in the database
            roomDao.create(room3);
            Log.i(LOG_TAG, "created room(" + room3.getId() + ")");
            // output it
            sb.append('#').append(room3.getId()).append(": ");
            sb.append(room3).append('\n');

            Sensor sensor = new Sensor(room);
            // store it in the database
            sensorDao.create(sensor);
            Log.i(LOG_TAG, "created sensor(" + sensor.getId() + ")");
            // output it
            sb.append('#').append(sensor.getId()).append(": ");
            sb.append(sensor).append('\n');

            Sensor sensor2 = new Sensor(room2);
            // store it in the database
            sensorDao.create(sensor2);
            Log.i(LOG_TAG, "created sensor(" + sensor2.getId() + ")");
            // output it
            sb.append('#').append(sensor2.getId()).append(": ");
            sb.append(sensor2).append('\n');

            Sensor sensor3 = new Sensor(room3);
            // store it in the database
            sensorDao.create(sensor3);
            Log.i(LOG_TAG, "created sensor(" + sensor3.getId() + ")");
            // output it
            sb.append('#').append(sensor3.getId()).append(": ");
            sb.append(sensor3).append('\n');

            SensorToModule sensorToModule = new SensorToModule(module, sensor);
            // store it in the database
            sensorToModuleDao.create(sensorToModule);
            Log.i(LOG_TAG, "created sensorToModule(" + sensorToModule.getId() + ")");
            // output it
            sb.append('#').append(sensorToModule.getId()).append(": ");
            sb.append(sensorToModule).append('\n');

            SensorToModule sensorToModule2 = new SensorToModule(module2, sensor2);
            // store it in the database
            sensorToModuleDao.create(sensorToModule2);
            Log.i(LOG_TAG, "created sensorToModule(" + sensorToModule2.getId() + ")");
            // output it
            sb.append('#').append(sensorToModule2.getId()).append(": ");
            sb.append(sensorToModule2).append('\n');

            SensorToModule sensorToModule3 = new SensorToModule(module3, sensor3);
            // store it in the database
            sensorToModuleDao.create(sensorToModule3);
            Log.i(LOG_TAG, "created sensorToModule(" + sensorToModule3.getId() + ")");
            // output it
            sb.append('#').append(sensorToModule3.getId()).append(": ");
            sb.append(sensorToModule3).append('\n');


            Value value = new Value("CELSIUS", "temperature");
            valueDao.create(value);
            Log.i(LOG_TAG, "created value(" + value.getId() + ")");
            // output it
            sb.append('#').append(value.getId()).append(": ");
            sb.append(value).append('\n');

            Value value2 = new Value("CANDELA", "light");
            valueDao.create(value2);
            Log.i(LOG_TAG, "created value(" + value2.getId() + ")");
            // output it
            sb.append('#').append(value2.getId()).append(": ");
            sb.append(value2).append('\n');

            Value value3 = new Value("PERCENT", "movement");
            valueDao.create(value3);
            Log.i(LOG_TAG, "created value(" + value3.getId() + ")");
            // output it
            sb.append('#').append(value3.getId()).append(": ");
            sb.append(value3).append('\n');


            ValueOfSensor valueOfSensor = new ValueOfSensor(sensor, 35, new Date());
            valueOfSensorDao.create(valueOfSensor);
            ValueOfSensor valueOfSensor2 = new ValueOfSensor(sensor, 28, new Date());
            valueOfSensorDao.create(valueOfSensor2);
            ValueOfSensor valueOfSensor3 = new ValueOfSensor(sensor2, 59, new Date());
            valueOfSensorDao.create(valueOfSensor3);
            ValueOfSensor valueOfSensor4 = new ValueOfSensor(sensor3, 0, new Date());
            valueOfSensorDao.create(valueOfSensor4);
            ValueOfSensor valueOfSensor5 = new ValueOfSensor(sensor, 29, new Date());
            valueOfSensorDao.create(valueOfSensor5);
            ValueOfSensor valueOfSensor6 = new ValueOfSensor(sensor2, 66, new Date());
            valueOfSensorDao.create(valueOfSensor6);

            temp.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
      //  Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
    }
//    public static String GET(String urlString) {
//        InputStream inputStream = null;
//        String result = "";
//        try {
//
//            // create a url object
//            URL url = new URL(urlString);
//
//            // create a url connection object
//            URLConnection urlConnection = url.openConnection();
//
//
//            // receive response as inputStream
//           inputStream = urlConnection.getInputStream();
//
//
//            // convert inputstream to string
//            if (inputStream != null) {
//               modules = convertInputStreamToString(inputStream);
//              //  result = convertInputStreamToString(inputStream);
//            } else
//                result = "Did not work!";
//
//        } catch (Exception e) {
//            Log.d("InputStream", e.getLocalizedMessage());
//        }
//
//        return result;
//    }
//    public static String GET(String urlString, DatabaseHelper dbh){
//        InputStream inputStream = null;
//        String result = "";
//        try {
//
//            // create a url object
//            URL url = new URL(urlString);
//
//            // create a url connection object
//            URLConnection urlConnection = url.openConnection();
//
//            // receive response as inputStream
//           inputStream = urlConnection.getInputStream();
//
//            // convert inputstream to string
//            if(inputStream != null)
//            {   result = convertInputStreamToString(inputStream);
//
//            ModuleFactory factory = new ModuleFactory();
//
//            JSONObject json = new JSONObject(result);
//
//            JSONArray jArray = json.getJSONArray("ModuleData");
//
//                for (int i=0; i < jArray.length(); i++)
//                {
//                    try {
//                        JSONObject oneObject = jArray.getJSONObject(i);
//                        // Pulling items from the array
//                        //Module module = factory.getModule("Comfort");//new Module(25, "temp", 15);
//                        //System.out.println(module.getClass());
//                        Module module = new Module();
//                        String idInt = oneObject.getString("ID");
//                        module.setId(Integer.valueOf(idInt));
//
//                        String typeString = oneObject.getString("Type");
//                        ModuleType mt = dbh.getModuleType(Integer.valueOf(typeString));
//                        module.setType(mt);
//
//                        String roomString = oneObject.getString("Room");
//                        Room r = dbh.getRoom(Integer.valueOf(roomString));
//                        module.setRoom(r);
//
//                        JSONArray dataArray = oneObject.getJSONArray("Data");
//                        for (int j=0; j < dataArray.length(); j++)
//                        {
//                            JSONObject oneObject2 = dataArray.getJSONObject(j);
//
//                            String timeString = oneObject2.getString("Time");
//                            module.setReceived_at(Converter.stringToDate(timeString));
//
//                            String value = oneObject2.getString("Values");
//                            module.setValue(value);
//                        }
//                        Module history = dbh.getModule(module.getId());
//                        if(history!=null) {
//                            dbh.createDataHistory(history);
//                            dbh.removeModule(history);
//                        }
//                        dbh.createModule(module);
//                       System.out.println("!!!!!!!!!!!"+module.getId()+" "+module.getValue()+" "+module.getReceived_at()+" "+module.getType().getType()+" "+module.getRoom().getName());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }}
//            else
//                result = "Did not work!";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("InputStream", e.getLocalizedMessage());
//        }
//
//        return result;
//    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
//    private void createUI(ArrayList<Module> modules) {
//        String dataValue = modules.get(0).getValue();
//        temp.setText(dataValue);
//        System.out.println(String.valueOf(dataValue));
//    }

    // convert inputstream to String
//    private static ArrayList<Module> convertInputStreamToString(InputStream inputStream) throws IOException {
////
//
//
//
//
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String id = "";
//        String name = "";
//        String value;
//        ModuleFactory factory = new ModuleFactory();
//        ArrayList<Module> modules = new ArrayList<>();
//        while ((id = bufferedReader.readLine()) != null) {
//            System.out.println(id);
//
////            name = bufferedReader.readLine();
////            value = bufferedReader.readLine();
////            //  Module module = new Module(Integer.getInteger(id), name, Float.valueOf(value), ModuleEnum type);
////            Module module = factory.getModule("Comfort");//new Module(25, "temp", 15);
////            module.setId(Integer.getInteger(id));
////            module.setName(name);
////            module.setValue(Float.valueOf(value));
////            modules.add(module);
//        }
//
//
//        inputStream.close();
//        return modules;
//
//    }
//

// check network connection
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

//    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
//
//            return GET(urls[0],dbh);
//        }
//
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
//         //   etResponse.setText(result);
////           if (modules != null && modules.size()!=0) {
////               // createUI(modules);
////               for (Module m : modules){
////                   DatabaseHelper db = new DatabaseHelper(
////                           getApplicationContext());
////
////                   // inserting new label into database
////             //      db.();
////               }
////
////            }
//        }
//    }
}