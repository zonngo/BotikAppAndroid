package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Notificaciones;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 07/01/2017.
 */

public interface iNotificationNew {
    @GET("medicine/notifications/news")
    Call<List<MH_DataModel_Notificaciones>> logoutUsuario(@Query("session_id") String session_id);
}
