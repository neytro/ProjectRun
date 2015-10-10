package com.example.neytro.test10;
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
/**
 * Created by Neytro on 2015-04-03.
 */
public class ActivityHistory extends ActionBarActivity {
    private ActionBar actionBar;
    private ListView listViewAdapter;
    private AdapterItem adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setActionBar();
        readData();
        setActivityAddapter();
    }

    //read value from database
    private void readData() {
        adapterItem = new AdapterItem();
        ClassFeedReaderDbHelper myDatabase = new ClassFeedReaderDbHelper(this);
        SQLiteDatabase database = myDatabase.getWritableDatabase();
        Cursor cursor = database.query(ClassFeedReaderContract.FeedEntry.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() >= 1) {
            for (int i = cursor.getCount() - 1; i >= 0; i--) {
                cursor.moveToPosition(i);
                adapterItem.setSpeed(cursor.getString(cursor.getColumnIndexOrThrow(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_SPEED)));
                adapterItem.setCalory(cursor.getString(cursor.getColumnIndexOrThrow(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_CALORY)));
                adapterItem.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_DATE)));
                adapterItem.setDistance(cursor.getString(cursor.getColumnIndexOrThrow(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_DISTANCE)));
                adapterItem.setTime(cursor.getString(cursor.getColumnIndexOrThrow(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_TIME)));
                adapterItem.setTimePeriod(cursor.getString(cursor.getColumnIndexOrThrow(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_TIME_PERIOD)));
                adapterItem.setImage(cursor.getString(cursor.getColumnIndexOrThrow(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_SCREENSHOOT)));
            }
        }
    }

    //add adapter to listview
    private void setActivityAddapter() {
        if (adapterItem.getSpeed() == null) {
            TextView textViewInfo = (TextView) findViewById(R.id.textViewInfo);
            textViewInfo.setText(getString(R.string.empty));
        } else {
            AdapterHistory hisotyrActivityClass = new AdapterHistory(this, R.layout.activity_history_array_list, adapterItem);
            listViewAdapter = (ListView) findViewById(R.id.listViewAdapter);
            listViewAdapter.setAdapter(hisotyrActivityClass);
        }
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

    //add action bar
    private void setActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_arrow_back);
        actionBar.setTitle(getString(R.string.history));
        actionBar.show();
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
        ClassFeedReaderDbHelper myDatabase = new ClassFeedReaderDbHelper(this);
        SQLiteDatabase database = myDatabase.getWritableDatabase();
        database.delete(ClassFeedReaderContract.FeedEntry.TABLE_NAME, null, null);
        listViewAdapter.setAdapter(null);
    }
}
