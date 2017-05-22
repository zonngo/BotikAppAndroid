package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

/*
 * Created by Marwuin on 15/12/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class myIntents {
    /**
     * Va a la interfaz de permisos de las aplicaciones
     * @param myContext
     */
    public static void ACTION_APPLICATION_DETAILS_SETTINGS(Context myContext){
        myContext.startActivity(
                new Intent()
                        .setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        );
    }

    /**
     * Interfaz de permiso de GPS
     * @param myContext
     */

    public static void ACTION_LOCATION_SOURCE_SETTINGS(Context myContext){
        myContext.startActivity(
                new Intent()
                .setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        );
    }
}
