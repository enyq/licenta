package edu.licenta.eniko.appclient;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Date;

import edu.licenta.eniko.contentProvider.RoomContentProvider;
import edu.licenta.eniko.sqlite.HomeManagementDBContract;
import edu.licenta.eniko.sqlite.helper.DatabaseHelper;
import edu.licenta.eniko.sqlite.model.Room;
import edu.licenta.eniko.sqlite.model.Sensor;
import edu.licenta.eniko.sqlite.model.Value;
import edu.licenta.eniko.sqlite.model.Module;
import edu.licenta.eniko.sqlite.model.ValueOfSensor;

/**
 * Created by Eniko on 8/24/2015.
 */
public class ListRoomsActivity extends ListActivity {

    DatabaseHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private Uri roomUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_list);
        this.getListView().setDividerHeight(2);
        dbHelper = new DatabaseHelper(getApplicationContext());
       long id;
        Room r = new Room("Kitchen");
        id = dbHelper.createRoom(r);
        r.setId((int)id);
        Log.i("added value:", r.toString());
        Module m = new Module("12575678");
        id = dbHelper.createModule(m);
        m.setId((int) id);
        Module retm = dbHelper.getModule(m.getId());
        Log.i("Adding new ", "value module:" + retm.getId());
        Value v = new Value("CANDELA","Light Intensity");
        id = dbHelper.createValue(v);
        v.setId((int) id);
        Log.i("added value:", v.toString());
        Log.i("Adding new ", "sensor");
        Sensor s = new Sensor(r,v,m);
        id = dbHelper.createSensor(s);
        s.setId((int)id);
        Sensor ret = dbHelper.getSensor(s.getId());
        Log.i("ret sensor value:", "Room:"+ret.getRoom().getName() + "Value:"+ ret.getValue().getName() + "Module:" + ret.getModule().getSerialNumber());
        Log.i("Adding new ", "valueofsensor");
        ValueOfSensor vs = new ValueOfSensor(s,45,new Date());
        dbHelper.createValueOfSensor(vs);

        fillData();
        registerForContextMenu(getListView());
    }

    private void fillData() {

        Cursor cursor = dbHelper.fetchAllRooms();
       // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.label };

        adapter = new SimpleCursorAdapter(this, R.layout.room_row, cursor, from,
                to, 0);

        setListAdapter(adapter);
    }


    // Opens the second activity if an entry is clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor cursor = (Cursor) getListAdapter().getItem(position);
        int roomId = cursor.getInt(cursor.getColumnIndex(HomeManagementDBContract.RoomEntries._ID));
        Intent i = new Intent(this, RoomDetailActivity.class);
        i.putExtra("RoomID", roomId);

        startActivity(i);
    }


}
