package edu.licenta.eniko.appclient;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText etResponse;
    TextView tvIsConnected;
    TextView temp;
    static ArrayList<Module> modules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to the views
        etResponse = (EditText) findViewById(R.id.etResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        temp = (TextView) findViewById(R.id.temp);
        // check if you are connected or not
        if (isConnected()) {
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are connected");
        } else {
            tvIsConnected.setText("You are NOT connected");
        }

        // show response on the EditText etResponse
        //  etResponse.setText(GET("http://hmkcode.com/examples/index.php"));
        temp.setText("");
        new HttpAsyncTask().execute("http://hmkcode.com/examples/index.php");

    }

    public static String GET(String urlString) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create a url object
            URL url = new URL(urlString);

            // create a url connection object
            URLConnection urlConnection = url.openConnection();

            // receive response as inputStream
            inputStream = urlConnection.getInputStream();


            // convert inputstream to string
            if (inputStream != null) {
                modules = convertInputStreamToString(inputStream);
            } else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private void createUI(ArrayList<Module> modules) {
        float tempValue = modules.get(0).getValue();
        temp.setText(String.valueOf(tempValue));
        System.out.println(String.valueOf(tempValue));
    }

    // convert inputstream to String
    private static ArrayList<Module> convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String id = "";
        String name = "";
        String value;
        ModuleFactory factory = new ModuleFactory();
        ArrayList<Module> modules = new ArrayList<>();
        while ((id = bufferedReader.readLine()) != null) {
            name = bufferedReader.readLine();
            value = bufferedReader.readLine();
            //  Module module = new Module(Integer.getInteger(id), name, Float.valueOf(value), ModuleEnum type);
            Module module = factory.getModule("Comfort");//new Module(25, "temp", 15);
            module.setId(Integer.getInteger(id));
            module.setName(name);
            module.setValue(Float.valueOf(value));
            modules.add(module);
        }


        inputStream.close();
        return modules;

    }

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
            etResponse.setText(result);
            if (modules != null) {
                createUI(modules);
            }
        }
    }
}