package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;


/**
 * Created by Epica on 7/3/2017.
 */

public class MH_ManagerNotificacion {


    public void showNotificationCompat(Context myContext, int id_session, Intent myIntent, int iconId, String title, String contentText) {
        //NOTIFICACION SIMPLE
        NotificationCompat.Builder notificationBuilder
                = (NotificationCompat.Builder) new NotificationCompat.Builder(myContext)
                .setContentTitle(title)
                .setContentText(contentText)
                //.setAutoCancel(true)//se cierra al ver notificacion
                //.setPriority(Notification.PRIORITY_MAX)
                //.setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(iconId)//icono a la izquierda
                //.setLargeIcon((((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap()))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));//sonido seleccionado
        //.setContentInfo("4")
        //.setTicker("Alerta!");//texto que aparece por unos segundos en la barra de estado al generarse una nueva notificación
        //AGREGAR ACCIONES A LA NOTIFICACION
        //Intent moreInfoIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//borra actividades intermedias
        // Crear pila
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(myContext);
        // Añadir actividad padre
        //taskStackBuilder.addParentStack(Eslabon.class); si se quiere regresar a la actividad al cerrar
        // Referenciar Intent para la notificación
        taskStackBuilder.addNextIntent(myIntent);
        // Obtener PendingIntent resultante de la pila
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Asignación del pending intent
        notificationBuilder.setContentIntent(pendingIntent);
        //se construye la notificacion
        NotificationManager notificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //se emite la notificacion
        notificationManager.notify(id_session, notificationBuilder.build());
    }
}