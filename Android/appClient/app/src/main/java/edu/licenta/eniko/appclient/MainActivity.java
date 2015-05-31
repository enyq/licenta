package edu.licenta.eniko.appclient;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Spinner;
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
    TextView temp;
    EditText dataValue;
    static ArrayList<Module> modules = null;
    // Spinner element
    Spinner heatSpinner, airSpinner, lightingSpinner, securitySpinner;

    // Add button
    Button btnAdd;

    // Input text
    EditText inputLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Spinner element
        heatSpinner = (Spinner) findViewById(R.id.heatSpinner);

        // Spinner click listener
        heatSpinner.setOnItemSelectedListener(this);

        // Spinner element
        airSpinner = (Spinner) findViewById(R.id.airSpinner);

        // Spinner click listener
        airSpinner.setOnItemSelectedListener(this);

        // Spinner element
        lightingSpinner = (Spinner) findViewById(R.id.lightingSpinner);

        // Spinner click listener
        lightingSpinner.setOnItemSelectedListener(this);

        // Spinner element
        securitySpinner = (Spinner) findViewById(R.id.securitySpinner);

        // Spinner click listener
        securitySpinner.setOnItemSelectedListener(this);

//        etResponse = (EditText) findViewById(R.id.etResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

        // check if you are connected or not
        if (isConnected()) {
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are connected");
        } else {
            tvIsConnected.setText("You are NOT connected");
        }

        new HttpAsyncTask().execute("http://79.119.109.209:1000/");
        // Loading spinner data from database
        loadSpinnerData(heatSpinner,"Comfort");
        loadSpinnerData(airSpinner,"Comfort");
        loadSpinnerData(lightingSpinner,"Comfort");
        loadSpinnerData(securitySpinner,"Comfort");

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
    private void loadSpinnerData(Spinner spinner, String type) {
        // database handler
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        getSelectedLabelValue(label);
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
                   DatabaseHandler db = new DatabaseHandler(
                           getApplicationContext());

                   // inserting new label into database
                   db.insertLabel(m.getName());
               }

            }
        }
    }
}