package edu.licenta.eniko.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eniko on 6/12/2015.
 */
public class Converter {


    public static Date stringToDate(String stringDate) {

        Date date = null;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

