package edu.licenta.eniko.appclient;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eniko on 6/10/2015.
 */
public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_MODULE =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedModule.TABLE_NAME + " (" +
                    FeedReaderContract.FeedModule._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedModule.COLUMN_NAME_VALUE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.FeedModule.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP + FeedReaderContract.FeedModule.COLUMN_NAME_TYPE + " integer" + COMMA_SEP +
                    FeedReaderContract.FeedModule.COLUMN_NAME_ROOM_ID + " integer" + COMMA_SEP +
                     " FOREIGN KEY ("+FeedReaderContract.FeedModule.COLUMN_NAME_TYPE+") REFERENCES "+FeedReaderContract.FeedType.TABLE_NAME+" ("+FeedReaderContract.FeedType._ID+") "+ COMMA_SEP +
                    " FOREIGN KEY ("+FeedReaderContract.FeedModule.COLUMN_NAME_ROOM_ID+") REFERENCES "+FeedReaderContract.FeedRoom.TABLE_NAME+" ("+FeedReaderContract.FeedRoom._ID+") "+
                    ");";

    private static final String SQL_CREATE_ROOM =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedRoom.TABLE_NAME + " (" +
                    FeedReaderContract.FeedRoom._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedRoom.COLUMN_NAME_NAME + TEXT_TYPE +  ");";
    private static final String SQL_CREATE_TYPE =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedType.TABLE_NAME + " (" +
                    FeedReaderContract.FeedType._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedType.COLUMN_NAME_TYPE + TEXT_TYPE +  ");";

    private static final String SQL_CREATE_DATA_HISTORY =
            "CREATE TABLE IF NOT EXISTS " + FeedReaderContract.FeedDataHistory.TABLE_NAME + " (" +
                    FeedReaderContract.FeedDataHistory._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedDataHistory.COLUMN_NAME_VALUE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.FeedDataHistory.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.FeedDataHistory.COLUMN_NAME_MODULE_ID + TEXT_TYPE + COMMA_SEP +
                    " FOREIGN KEY ("+FeedReaderContract.FeedDataHistory.COLUMN_NAME_MODULE_ID+") REFERENCES "+FeedReaderContract.FeedModule.TABLE_NAME+" ("+FeedReaderContract.FeedModule._ID+") " +
                     ");";


    private static final String SQL_DELETE_ROOM =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedRoom.TABLE_NAME;

    private static final String SQL_DELETE_TYPE =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedType.TABLE_NAME;

    private static final String SQL_DELETE_DATA_HISTORY =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedDataHistory.TABLE_NAME;

    private static final String SQL_DELETE_MODULE =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedModule.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HomeManagementSystem.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ROOM);
        db.execSQL(SQL_CREATE_TYPE);
        db.execSQL(SQL_CREATE_MODULE);
        db.execSQL(SQL_CREATE_DATA_HISTORY);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ROOM);
        db.execSQL(SQL_DELETE_TYPE);
        db.execSQL(SQL_DELETE_MODULE);
        db.execSQL(SQL_DELETE_DATA_HISTORY);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + FeedReaderContract.FeedModule.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}
