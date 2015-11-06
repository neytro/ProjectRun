package com.example.neytro.test10;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
/**
 * Created by Neytro on 2015-10-26.
 */
public class AlertDialogs {
    //dialog for gps off.
    Context context;

    public AlertDialogs(Context context) {
        this.context = context;
    }

    public void alertDialogGps() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(context.getString(R.string.GPStitle));
        alertDialog.setMessage(context.getString(R.string.GPSmessage));
        alertDialog.setPositiveButton(context.getString(R.string.Settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setNeutralButton(context.getString(R.string.ignor), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void alertDialogExit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(context.getString(R.string.exit));
        alertDialog.setMessage(context.getString(R.string.messageExit));
        alertDialog.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setNegativeButton(context.getString(R.string.no), null);
        alertDialog.show();
    }
}
