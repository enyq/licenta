package edu.licenta.eniko.appclient;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import edu.licenta.eniko.contentProvider.RoomContentProvider;
import edu.licenta.eniko.sqlite.HomeManagementDBContract;
import edu.licenta.eniko.sqlite.helper.DatabaseHelper;
import edu.licenta.eniko.sqlite.model.*;
import edu.licenta.eniko.sqlite.model.Module;
import edu.licenta.eniko.util.UnitsOfMeasurementEnum;

/**
 * Created by Eniko on 8/26/2015.
 */
public class RoomDetailActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private Spinner mCategory;
    private SimpleCursorAdapter adapter;
    private TextView valueNameText;
    private TextView dateText;

    private TextView dataText;
    private TextView umText;

    private DatabaseHelper dbHelper;

    private Uri roomUri, moduleUri, sensorUri, valueUri, valueSensorUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.room_details);
        this.getListView().setDividerHeight(2);
        dbHelper = new DatabaseHelper(getApplicationContext());
//        dbHelper.onCreate(dbHelper.getWritableDatabase());
//        Log.i("Adding new ", "room");
//        long id;
//        Room r = new Room("LivingRoom");
//        id = dbHelper.createRoom(r);
//        r.setId((int)id);
//        Log.i("added value:", r.toString());
//        Module m = new Module("12525678");
//        id = dbHelper.createModule(m);
//        m.setId((int) id);
//        Module retm = dbHelper.getModule(m.getId());
//        Log.i("Adding new ", "value module:" + retm.getId());
//        Value v = new Value("CELSIUS","Temperature1");
//        id = dbHelper.createValue(v);
//        v.setId((int) id);
//        Log.i("added value:", v.toString());
//        Log.i("Adding new ", "sensor");
//        Sensor s = new Sensor(dbHelper.getRoom(1),dbHelper.getValue(1),dbHelper.getModule(1));
//       long id = dbHelper.createSensor(s);
//        s.setId((int)id);
//        Sensor ret = dbHelper.getSensor(s.getId());
//        Log.i("ret sensor value:", "Room:"+ret.getRoom().getName() + "Value:"+ ret.getValue().getName() + "Module:" + ret.getModule().getSerialNumber());
//        Log.i("Adding new ", "valueofsensor");
//        ValueOfSensor vs = new ValueOfSensor(s,25,new Date());
//        dbHelper.createValueOfSensor(vs);
//        Log.i("added value:", vs.toString());
//        valueNameText = (TextView) findViewById(R.id.valueLabel);
//        dateText = (TextView) findViewById(R.id.dateLabel);
//        dataText = (TextView) findViewById(R.id.dataLabel);
//        umText = (TextView) findViewById(R.id.uMLabel);
//        Button confirmButton = (Button) findViewById(R.id.button);
//
          Bundle extras = getIntent().getExtras();
//
//        // check from the saved Instance
//        roomUri = (bundle == null) ? null : (Uri) bundle
//                .getParcelable(RoomContentProvider.CONTENT_ITEM_TYPE);
//
//        // Or passed from the other activity
//        if (extras != null) {
//            roomUri = extras
//                    .getParcelable(RoomContentProvider.CONTENT_ITEM_TYPE);
//
//            fillData(roomUri);
//        }
//
//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(mTitleText.getText().toString())) {
//                    makeToast();
//                } else {
//                    setResult(RESULT_OK);
//                    finish();
//                }
//            }
//
//        });

        List<Room> rooms = dbHelper.getAllRooms();
        for(Room r1 : rooms) {
            Log.i("RoomDetailActivity", "Room id: " + String.valueOf(r1.getId()) + " Room name: " + r1.getName());
        }
        List<Module> modules = dbHelper.getAllModules();
        for(Module m1 : modules) {
            Log.i("RoomDetailActivity", "Module id: " + String.valueOf(m1.getId()) + " Module SN: " + m1.getSerialNumber());
        }

        List<Value> values = dbHelper.getAllValues();
        for(Value v1 : values) {
            Log.i("RoomDetailActivity", "Value id: " + String.valueOf(v1.getId()) + " Value name: " + v1.getName() + "Value UM:" + v1.getUm());
        }

            List<Sensor> sensors= dbHelper.getAllSensors();
        for(Sensor se : sensors) {
            Log.i("RoomDetailActivity", "Sensor id: " + String.valueOf(se.getId()) + " Room name: " + se.getRoom().getName() + " Module sn: "
                    + se.getModule().getSerialNumber() + " Value name: " + se.getValue().getName());


            List<ValueOfSensor> vss = dbHelper.getAllValuesOfSensorBySensor(se.getId());
            for (ValueOfSensor vs1 : vss) {
                Log.i("RoomDetailActivity", "Value: " + vs1.getData() + " Date: " + vs1.getReceiveDate());
            }
        }
        int roomId;
        if (extras != null) {
            roomId = extras
                    .getInt("RoomID");

            fillData(roomId);
        }
    }
    private void fillData(int roomId) {

        Cursor cursor = dbHelper.fetchAllSensorsOfRoom(roomId);

        String[] projection = {HomeManagementDBContract.ValueEntries.COLUMN_NAME_NAME, HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_DATE,
                HomeManagementDBContract.ValueToSensorEntries.COLUMN_NAME_VALUE, HomeManagementDBContract.ValueEntries.COLUMN_NAME_UM};

        int[] to = new int[] { R.id.valueLabel, R.id.dateLabel, R.id.dataLabel, R.id.uMLabel };
        adapter = new SimpleCursorAdapter(this, R.layout.sensor_row, cursor, projection,
                to, 0);

        setListAdapter(adapter);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(RoomContentProvider.CONTENT_ITEM_TYPE, roomUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
//        String category = (String) mCategory.getSelectedItem();
//        String summary = mTitleText.getText().toString();
//        String description = mBodyText.getText().toString();
//
//        // only save if either summary or description
//        // is available
//
//        if (description.length() == 0 && summary.length() == 0) {
//            return;
//        }
//        ContentValues values = new ContentValues();
//        values.put(TodoTable.COLUMN_CATEGORY, category);
//        values.put(TodoTable.COLUMN_SUMMARY, summary);
//        values.put(TodoTable.COLUMN_DESCRIPTION, description);
//
//        if (todoUri == null) {
//            // New todo
//            todoUri = getContentResolver().insert(MyTodoContentProvider.CONTENT_URI, values);
//        } else {
//            // Update todo
//            getContentResolver().update(todoUri, values, null, null);
//        }
    }

    private void makeToast() {
        Toast.makeText(RoomDetailActivity.this, "Please maintain a summary",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { HomeManagementDBContract.RoomEntries._ID, HomeManagementDBContract.RoomEntries.COLUMN_NAME_NAME };
        CursorLoader cursorLoader = new CursorLoader(this,
                RoomContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
    }
}

