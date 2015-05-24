package edu.licenta.eniko.appclient;

import java.io.*;
import java.net.*;

/**
 * Created by Eniko on 5/24/2015.
 */
public class ConnectionUtil {

    public void connect() throws MalformedURLException {
        URL url = new URL("http://182.158.6.5");
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
                urlConnection.disconnect();
            }
        }
     public void readStream(InputStream in) {


     }
}
