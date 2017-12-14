package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

/*
 * Created by Marwuin on 15/12/2016.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class MyNetWork {
    public boolean checkNetworkConnection(Context myContext) {
        ConnectivityManager connMgr = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        Toast.makeText(myContext.getApplicationContext(), "Por favor verifique su conexion", Toast.LENGTH_LONG).show();
        return false;
    }

}
