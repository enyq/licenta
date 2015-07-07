package edu.licenta.eniko.appclient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnItemSelectedListener {

    EditText etResponse;
    TextView tvIsConnected;
    TextView temp, moduleid;
    TableRow tr1;


    EditText dataValue;
    static ArrayList<Module> modules = null;
    // Spinner element
    TableLayout heatTable, airTable, lightingTable, securityTable;

    // Add button
    Button btnAdd;

    // Input text
    EditText inputLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout t1;
        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());

// Gets the data repository in write mode
        SQLiteDatabase db = dbh.getWritableDatabase();
        dbh.onUpgrade(db,1,2);
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedRoom._ID, 1);
        values.put(FeedReaderContract.FeedRoom.COLUMN_NAME_NAME, "Living Room");


// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.FeedRoom.TABLE_NAME,
                FeedReaderContract.FeedRoom._ID,
                values);
// Create a new map of values, where column names are the keys
        ContentValues values2 = new ContentValues();
        values2.put(FeedReaderContract.FeedType._ID, 1);
        values2.put(FeedReaderContract.FeedType.COLUMN_NAME_TYPE, "Comfort");


// Insert the new row, returning the primary key value of the new row
        long newRowId2;
        newRowId2 = db.insert(
                FeedReaderContract.FeedType.TABLE_NAME,
                FeedReaderContract.FeedType._ID,
                values2);

// Create a new map of values, where column names are the keys
        ContentValues values3 = new ContentValues();
        values3.put(FeedReaderContract.FeedType._ID, 1);
        values3.put(FeedReaderContract.FeedModule.COLUMN_NAME_VALUE,15);
        values3.put(FeedReaderContract.FeedModule.COLUMN_NAME_DATE,15-16-2015);
        values3.put(FeedReaderContract.FeedModule.COLUMN_NAME_TYPE,1);
        values3.put(FeedReaderContract.FeedModule.COLUMN_NAME_ROOM_ID,1);



// Insert the new row, returning the primary key value of the new row
        long newRowId3;
        newRowId3 = db.insert(
                FeedReaderContract.FeedModule.TABLE_NAME,
                FeedReaderContract.FeedModule._ID,
                values3);

        TableLayout tl = (TableLayout) findViewById(R.id.main_table);

        TableRow tr_head = new TableRow(this);
      //  tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


        TextView label_date = new TextView(this);
        //label_date.setId();
        label_date.setText("Room");
        label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        tr_head.addView(label_date);// add the column to the table row here

        TextView label_weight_kg = new TextView(this);
       // label_weight_kg.setId(21);// define id that must be unique
        label_weight_kg.setText("Value"); // set the text for the header
        label_weight_kg.setTextColor(Color.WHITE); // set the color
        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight_kg); // add the column to the table row here

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        Integer count=0;

        //List<String> all = db.getAllLabels();
        String selectQuery = "SELECT room.name, module.value FROM " + FeedReaderContract.FeedModule.TABLE_NAME + " inner join "+
                FeedReaderContract.FeedRoom.TABLE_NAME +" on room._id=module.roomid;";


        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            String roomName = cursor.getString(0);// get the first variable
            String valueTemp = cursor.getString(1);// get the second variable
// Create the table row
            TableRow tr = new TableRow(this);
            if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
            tr.setId(100 + count);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
            // Create a TextView to add date
            TextView labelDATE = new TextView(this);
            labelDATE.setId(200+count);
            labelDATE.setText(roomName);
            labelDATE.setPadding(2, 0, 5, 0);
            labelDATE.setTextColor(Color.BLACK);
            tr.addView(labelDATE);
            TextView labelWEIGHT = new TextView(this);
            labelWEIGHT.setId(200+count);
            labelWEIGHT.setText(valueTemp);
            labelWEIGHT.setTextColor(Color.BLACK);
            tr.addView(labelWEIGHT);

// finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            count++;
        }

    //    heatTable = (TableLayout)findViewById(R.id.tableLayout1);
      //  tr1 = (TableRow)findViewById(R.id.tableRow3);
       // tr1.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
     //  TextView textview = new TextView(this);
     //   moduleid =(TextView)findViewById(R.id.roomName);
    //    moduleid.setText("Room name");
//textview.getTextColors(R.color.)
     //   textview.setTextColor(Color.YELLOW);
      //  tr1.addView(moduleid);
     //   heatTable.addView(tr1);//, new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        // Spinner element
     //   airTable = (TableLayout)findViewById(R.id.moduleid);

        // Spinner click listener
//        airSpinner.setOnItemSelectedListener(this);
//
//        // Spinner element
//        lightingSpinner = (Spinner) findViewById(R.id.lightingSpinner);
//
//        // Spinner click listener
//        lightingSpinner.setOnItemSelectedListener(this);
//
//        // Spinner element
//        securitySpinner = (Spinner) findViewById(R.id.securitySpinner);
//
//        // Spinner click listener
//        securitySpinner.setOnItemSelectedListener(this);

//        etResponse = (EditText) findViewById(R.id.etResponse);
       // tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

        // check if you are connected or not
//        if (isConnected()) {
//            tvIsConnected.setBackgroundColor(0xFF00CC00);
//            tvIsConnected.setText("You are connected");
//        } else {
//            tvIsConnected.setText("You are NOT connected");
//        }

    //    new HttpAsyncTask().execute("http://79.119.109.209:1000/");
        // Loading spinner data from database
       // loadData(heatSpinner, "Comfort");
       // loadData(airSpinner, "Comfort");
       // loadData(lightingSpinner,"Comfort");
       // loadData(securitySpinner,"Comfort");

        /**
         * Add new label button click listener
         * */
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                String label = inputLabel.getText().toString();
//
//                if (label.trim().length() > 0) {
//                    // database handler
//                    DatabaseHandler db = new DatabaseHandler(
//                            getApplicationContext());
//
//                    // inserting new label into database
//                    db.insertLabel(label);
//
//                    // making input filed text to blank
//                    inputLabel.setText("");
//
//                    // Hiding the keyboard
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);
//
//                    // loading spinner with newly added data
//                    loadSpinnerData();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please enter label name",
//                            Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadData( String type) {
        // database handler
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

      //  getSelectedLabelValue(label);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // get reference to the views
//        etResponse = (EditText) findViewById(R.id.etResponse);
//        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
//        temp = (TextView) findViewById(R.id.temp);
//        // check if you are connected or not
//        if (isConnected()) {
//            tvIsConnected.setBackgroundColor(0xFF00CC00);
//            tvIsConnected.setText("You are connected");
//        } else {
//            tvIsConnected.setText("You are NOT connected");
//        }
//
//        // show response on the EditText etResponse
//        //  etResponse.setText(GET("http://hmkcode.com/examples/index.php"));
//        temp.setText("");
//        new HttpAsyncTask().execute("http://79.119.109.209:1000/");
//
//    }

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
    public static String GET(String urlString){
        InputStream inputStream = null;
        String result = "";
        try {

            // create a url object
            URL url = new URL(urlString);

            // create a url connection object
            URLConnection urlConnection = url.openConnection();


            // receive response as inputStream
           inputStream = urlConnection.getInputStream();
            modules = new ArrayList<>();
            // convert inputstream to string
            if(inputStream != null)
            {   result = convertInputStreamToString(inputStream);
            // Convert String to json object
                ModuleFactory factory = new ModuleFactory();

            JSONObject json = new JSONObject(result);

// get LL json object
            JSONArray jArray = json.getJSONArray("ModuleData");

                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        Module module = factory.getModule("Comfort");//new Module(25, "temp", 15);
                        System.out.println(module.getClass());

                        String UUIDint = oneObject.getString("UUID");
                       String typeString = oneObject.getString("Type");
                        String nameString = oneObject.getString("Name");
                        module.setUUId(UUIDint);
                        module.setName(nameString);
                        System.out.println("!!!!!! " + UUIDint+" "+typeString+" "+nameString);

                        JSONArray dataArray = oneObject.getJSONArray("Data");
                        for (int j=0; j < dataArray.length(); j++)
                        {
                            JSONObject oneObject2 = dataArray.getJSONObject(j);
                           // String timeString = oneObject2.getString("Time");
                            String value = oneObject2.getString("Values");
                            module.setValue(value);
                            System.out.println("!!!!!!" + value);

                            // int valueInt = oneObject.getInt("Values");
                        }
                        modules.add(module);
                       System.out.println("!!!!!!!!!!!"+module.getName()+" "+module.getUUId()+" "+module.getValue());
                    } catch (JSONException e) {
                        // Oops
                    }
                }}
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    private void createUI(ArrayList<Module> modules) {
        String dataValue = modules.get(0).getValue();
        temp.setText(dataValue);
        System.out.println(String.valueOf(dataValue));
    }

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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
//            etResponse.setText(result);
           if (modules != null && modules.size()!=0) {
               // createUI(modules);
               for (Module m : modules){
                   DatabaseHelper db = new DatabaseHelper(
                           getApplicationContext());

                   // inserting new label into database
             //      db.();
               }

            }
        }
    }
}