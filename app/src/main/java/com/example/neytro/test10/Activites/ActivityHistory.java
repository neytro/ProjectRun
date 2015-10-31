package com.example.neytro.test10.Activites;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.neytro.test10.AdapterHistory;
import com.example.neytro.test10.AdapterItem;
import com.example.neytro.test10.DbColumns;
import com.example.neytro.test10.DbManagement;
import com.example.neytro.test10.R;
/**
 * Created by Neytro on 2015-04-03.
 */
public class ActivityHistory extends ActionBarActivity {
    private final String COLUMN_SPEED = DbColumns.FeedEntry.COLUMN_NAME_SPEED;
    private final String COLUMN_CALORY = DbColumns.FeedEntry.COLUMN_NAME_CALORY;
    private final String COLUMN_DATE = DbColumns.FeedEntry.COLUMN_NAME_DATE;
    private final String COLUMN_DISTANCE = DbColumns.FeedEntry.COLUMN_NAME_DISTANCE;
    private final String COLUMN_TIME = DbColumns.FeedEntry.COLUMN_NAME_TIME;
    private final String COLUMN_TIME_P = DbColumns.FeedEntry.COLUMN_NAME_TIME_PERIOD;
    private final String COLUMN_SCREENSHOT = DbColumns.FeedEntry.COLUMN_NAME_SCREENSHOOT;
    private final int EMPTY = 1;
    private ActionBar actionBar;
    private ListView listViewAdapter;
    private AdapterItem adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setActionBar();
        readData();
        loadAdapterIfnotEmpty();
    }

    //add action bar
    private void setActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_arrow_back);
        actionBar.setTitle(getString(R.string.history));
        actionBar.show();
    }

    //read value from database
    private void readData() {
        DbManagement myDatabase = new DbManagement(this);
        SQLiteDatabase database = myDatabase.getWritableDatabase();
        Cursor cursor = database.query(DbColumns.FeedEntry.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        fillAdapter(cursor);
    }

    private void fillAdapter(Cursor cursor) {
        adapterItem = new AdapterItem();
        if (cursor.getCount() >= EMPTY) {
            getValueFromDatabase(cursor);
        }
    }

    private void getValueFromDatabase(Cursor cursor) {
        for (int i = cursor.getCount() - 1; i >= 0; i--) {
            cursor.moveToPosition(i);
            adapterItem.setSpeed(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPEED)));
            adapterItem.setCalory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CALORY)));
            adapterItem.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
            adapterItem.setDistance(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DISTANCE)));
            adapterItem.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)));
            adapterItem.setTimePeriod(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME_P)));
            adapterItem.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCREENSHOT)));
        }
    }

    //add adapter to listview
    private void loadAdapterIfnotEmpty() {
        if (adapterItem.getSpeed() == null) {
            setEmptyText();
        } else {
            loadAdapter();
        }
    }

    private void setEmptyText() {
        TextView textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        textViewInfo.setText(getString(R.string.empty));
    }

    private void loadAdapter() {
        AdapterHistory hisotyrActivityClass = new AdapterHistory(this, R.layout.activity_history_array_list, adapterItem);
        listViewAdapter = (ListView) findViewById(R.id.listViewAdapter);
        listViewAdapter.setAdapter(hisotyrActivityClass);
    }

    //create menu to main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //listener for menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return (true);
            case R.id.clear:
                alertDialogClearData();
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }

    //alert dialog for remove database
    private void alertDialogClearData() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(getString(R.string.title_usuniecie_historii));
        alertDialog.setMessage(getString(R.string.messageExit));
        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearData();
            }
        });
        alertDialog.setNegativeButton(getString(R.string.no), null);
        alertDialog.show();
    }

    //clear database
    private void clearData() {
        DbManagement myDatabase = new DbManagement(this);
        SQLiteDatabase database = myDatabase.getWritableDatabase();
        database.delete(DbColumns.FeedEntry.TABLE_NAME, null, null);
        listViewAdapter.setAdapter(null);
    }
}
