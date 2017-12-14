package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

/*
 * Created by Marwuin on 15/12/2016.
 */

import android.util.Log;

public class myDebug {
    public static boolean MODE_DEBUG = true;

    public static void showLog_d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void showLog_e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    public static void showLog_e2(String tag, String msg) {
        Log.e(tag, msg);
    }


}
