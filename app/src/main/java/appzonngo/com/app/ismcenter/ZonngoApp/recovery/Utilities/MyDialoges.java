package appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities;

/*
 * Created by Marwuin on 15/12/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;

public class MyDialoges {
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

}
