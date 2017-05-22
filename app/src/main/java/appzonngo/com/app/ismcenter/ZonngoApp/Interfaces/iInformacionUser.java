package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;


import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_InfoUsuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 23/12/2016.
 */

public interface iInformacionUser {
    @GET("info")
    Call<MH_DataModel_InfoUsuario> getInfoUsuario(@Query("session_id") String session_id);
}
