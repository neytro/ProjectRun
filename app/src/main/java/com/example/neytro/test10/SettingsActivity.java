package com.example.neytro.test10;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by Neytro on 2015-04-03.
 */
public class SettingsActivity extends ActionBarActivity {
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        setContentView(R.layout.activity_settings);
    }

    //add menu to activity history
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }

    //add actionbar to history activity
    private void setActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_arrow_back);
        actionBar.setTitle(getString(R.string.action_settings));
        actionBar.show();
    }
}
