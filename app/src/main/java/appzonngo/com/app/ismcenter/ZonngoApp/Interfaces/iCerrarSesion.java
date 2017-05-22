package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_CerrarSesion;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 07/01/2017.
 */

public interface iCerrarSesion {
    @GET("logout")
    Call<MH_DataModel_CerrarSesion> logoutUsuario(@Query("session_id") String session_id);
        }
