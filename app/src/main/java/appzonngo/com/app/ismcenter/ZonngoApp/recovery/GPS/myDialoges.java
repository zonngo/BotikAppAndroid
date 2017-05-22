package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

/*
 * Created by Marwuin on 15/12/2016.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import appzonngo.com.app.ismcenter.zonngo2.R;

public class myDialoges {
    private static ProgressDialog pDialog;

    public static void showProgressDialog(Context myContext, String msg){
        pDialog = new ProgressDialog(myContext);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    public static void dismissProgressDialog(){
        if(pDialog!=null)
            pDialog.dismiss();
    }

    public static void showSettingsActivateGPS(final Context myContext) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);
        //alertDialog.setTitle(R.string.gps_button);
        alertDialog.setMessage(R.string.gps_off);
        alertDialog.setPositiveButton(R.string.gps_config, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myIntents.ACTION_LOCATION_SOURCE_SETTINGS(myContext);;//pantalla de activacion GPS
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }




}
