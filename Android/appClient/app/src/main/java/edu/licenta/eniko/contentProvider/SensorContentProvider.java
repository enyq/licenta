package edu.licenta.eniko.contentProvider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

import edu.licenta.eniko.sqlite.HomeManagementDBContract;
import edu.licenta.eniko.sqlite.helper.DatabaseHelper;

/**
 * Created by Eniko on 8/26/2015.
 */
public class SensorContentProvider extends ContentProvider {

    // database
    private DatabaseHelper database;

    // used for the UriMacher
    private static final int ROOMS = 10;
    private static final int ROOM_ID = 20;

    private static final String AUTHORITY = "edu.licenta.eniko.contentProvider";

    private static final String BASE_PATH = "rooms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/rooms";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/room";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, ROOMS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ROOM_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(HomeManagementDBContract.RoomEntries.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ROOMS:
                break;
            case ROOM_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(HomeManagementDBContract.RoomEntries._ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case ROOMS:
                id = sqlDB.insert(HomeManagementDBContract.RoomEntries.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case ROOMS:
                rowsDeleted = sqlDB.delete(HomeManagementDBContract.RoomEntries.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case ROOM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(HomeManagementDBContract.RoomEntries.TABLE_NAME,
                            HomeManagementDBContract.RoomEntries._ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(HomeManagementDBContract.RoomEntries.TABLE_NAME,
                            HomeManagementDBContract.RoomEntries._ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case ROOMS:
                rowsUpdated = sqlDB.update(HomeManagementDBContract.RoomEntries.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case ROOM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(HomeManagementDBContract.RoomEntries.TABLE_NAME,
                            values,
                            HomeManagementDBContract.RoomEntries._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(HomeManagementDBContract.RoomEntries.TABLE_NAME,
                            values,
                            HomeManagementDBContract.RoomEntries._ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { HomeManagementDBContract.RoomEntries._ID, HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}

