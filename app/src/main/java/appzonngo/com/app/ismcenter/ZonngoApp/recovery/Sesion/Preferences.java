package appzonngo.com.app.ismcenter.ZonngoApp.recovery.Sesion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_InfoUsuario;

/**
 * Created by Marwuin on 22/3/2017.
 */

public class Preferences {

    private static final String PREF_NAME = "AndroidHivePref";

    public static final String IS_NOT_NEW_APP = "IsnewAppON";
    public static final String IS_DATA = "IsdataON";
    public static final String IS_LOGIN = "IsloginON";
    public static final String KEY_NAME = "name";
    public static final String KEY_SESION = "sesion";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_PASSWORD = "password";
    public static final String Key_ID = "id_session";

    //CONTROL DE LOGIN
    private static final String TYPE_LOGIN = "type_login";
    public static final String LOGIN_FACEBOOK = "login_facebook";
    public static final String LOGIN_GOOGLE = "login_google";
    public static final String LOGIN_EMAIL = "login_email";

    public static void setString(String key, String value, Context context) {
        //context.getSharedPreferences("BASE",0);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setBoolean(String key, Boolean value, Context context) {
        //context.getSharedPreferences("BASE",0);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getBoolean(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }

    public static String getString(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void logOutpPreferences(Context mycontext){
        setBoolean(Preferences.IS_LOGIN,false,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_SESION,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_NAME,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_LAST_NAME,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_EMAIL,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_TELEFONO,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_AVATAR,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.TYPE_LOGIN,null,mycontext);///ESTA ES LA MIA
        setBoolean(Preferences.IS_DATA,false,mycontext);///ESTA ES LA MIA
    }

    public static void setDataUserPreferences(Context mycontext, MH_DataModel_InfoUsuario response){
        setString(Preferences.KEY_NAME,response.getNombre(),mycontext);
        setString(Preferences.KEY_LAST_NAME,response.getApellidos(),mycontext);
        setString(Preferences.KEY_EMAIL,response.getEmail(),mycontext);
        setString(Preferences.KEY_TELEFONO,response.getTelefono(),mycontext);
        setString(Preferences.KEY_AVATAR,response.getAvatar(),mycontext);
        setBoolean(Preferences.IS_DATA,true,mycontext);
    }

    public static void clearDataUser(Context mycontext){
        setBoolean(Preferences.IS_NOT_NEW_APP,true,mycontext);//sepa que ya ha estado en la app previamente
        setString(Preferences.KEY_NAME,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_LAST_NAME,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_EMAIL,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_TELEFONO,null,mycontext);///ESTA ES LA MIA
        setString(Preferences.KEY_AVATAR,null,mycontext);///ESTA ES LA MIA
        setBoolean(Preferences.IS_DATA,false,mycontext);///ESTA ES LA MIA
    }

    public static void setLogin(Context mycontext, Boolean isLogin, String typeLogin, String idSesion, String psw){
        setBoolean(Preferences.IS_LOGIN,true,mycontext);
        setString(Preferences.KEY_SESION,idSesion,mycontext);///ESTA ES LA MIA
        setString(Preferences.TYPE_LOGIN,typeLogin,mycontext);
        setString(Preferences.KEY_PASSWORD,psw,mycontext);///ESTA ES LA MIA
    }

    public static String getTypeLogin(Context mycontext){
        return getString(Preferences.TYPE_LOGIN,mycontext);
    }

    public static Boolean idDataReady(Context mycontext){
        return getBoolean(Preferences.IS_DATA,mycontext);
    }


    public static Boolean isNotNewApp(Context mycontext){
        return getBoolean(Preferences.IS_NOT_NEW_APP,mycontext);
    }

    public static String getIdSesion(Context mycontext){
        return getString(Preferences.KEY_SESION,mycontext);
    }

    public static Boolean isLogin(Context mycontext){
        return getBoolean(Preferences.IS_LOGIN,mycontext);
    }


}
