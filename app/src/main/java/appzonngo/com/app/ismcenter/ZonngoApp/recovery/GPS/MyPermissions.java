package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

/*
 * Created by Marwuin on 15/12/2016.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import appzonngo.com.app.ismcenter.zonngo2.R;


public class MyPermissions {

    private AppCompatActivity mContext;
    private View viewSnackBar;
    String[] MANIFEST_PERMISSIONS;
    int[] REQUEST_PERMISSIONS;
    String[] MSG_DENIED_PERMISSIONS;


    public MyPermissions(AppCompatActivity mContext, View viewSnackBar, String[] MANIFEST_PERMISSIONS, int[] REQUEST_PERMISSIONS, String[] MSG_DENIED_PERMISSIONS) {
        this.mContext = mContext;
        this.viewSnackBar = viewSnackBar;
        this.MANIFEST_PERMISSIONS = MANIFEST_PERMISSIONS;
        this.REQUEST_PERMISSIONS = REQUEST_PERMISSIONS;
        this.MSG_DENIED_PERMISSIONS = MSG_DENIED_PERMISSIONS;
    }

    /**
     * SISI no ha permiso de GPS FINE o COARSE,
     * se lleva al usuario a la interfaz de permisos de aplicaciones
     * SINO, se remueve la aptualizaciones
     */
    public static boolean ACCESS_FINE_LOCATION_OR_ACCESS_COARSE_LOCATION(Context myContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    /**
     * Si no tiene permisos, se verifica la razon y se solicita que lo active (la respuesta se obtiene en "onRequestPermissionsResult")
     */
    public void managePermissionDenied(String permission, String msg, int codePermission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission)) {
            //Si lo rechazo anteriormente
            //notificacion para que el usuario active prmisos
            msgDeniedPermission(msg);
            //Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
        } else {//Solicita el permiso
            myDebug.showLog_d("manegePermissionDenied","permission"+permission);
            ActivityCompat.requestPermissions(mContext, new String[]{permission}, codePermission);
        }
    }

    /**
     * verifica si tiene activo permiso de acceso GPS
     * @return
     */
    public boolean checkPermission(String permission){
        return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void msgDeniedPermission(String msgDeniedPermission){
        Snackbar.make(viewSnackBar,msgDeniedPermission, Snackbar.LENGTH_LONG)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override public void onClick(View view){
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        mContext.startActivity(intent);
                    }}).show();
    }

    /**
     * Si tiene permisos, obtiene la ultima ubicacion conocida
     */
    public boolean obtainPermissions() {
        for(int i=0;i<MANIFEST_PERMISSIONS.length;i++){
            String permission=MANIFEST_PERMISSIONS[i];
            int requestPermission=REQUEST_PERMISSIONS[i];
            String msgDeniedPermission= MSG_DENIED_PERMISSIONS[i];
            if(!checkPermission(permission)){
                managePermissionDenied(permission, msgDeniedPermission, requestPermission);
                return false;
            }
        }
        //si todos los permisos estan concedidos
        return true;
        //truecallSIP();
    }

    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for(int i=0;i<REQUEST_PERMISSIONS.length;i++){
            int requestPermission = REQUEST_PERMISSIONS[i];
            if (requestCode == requestPermission) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //obtainCallPermissions();//all permission ready?
                    //acepto permiso
                    return true;
                }else {
                    //rechazo permiso
                    String msgDeniedPermission = MSG_DENIED_PERMISSIONS[i];
                    msgDeniedPermission(msgDeniedPermission);
                    return false;
                }
            }
        }
        return false;
    }


}
