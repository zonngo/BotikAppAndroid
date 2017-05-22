package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Notificaciones;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 7/3/2017.
 */

public interface iNotificacion {
    @GET("medicine/notifications/all")
    Call<List<MH_DataModel_Notificaciones>> logoutUsuario(@Query("session_id") String session_id);
}
