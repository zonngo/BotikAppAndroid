package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_RecupClave;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by marwuinh@gmail.com on 08/12/2016.
 */

public interface iRecuperarContrasena {
    @FormUrlEncoded
    @POST("recover")
    Call<MH_DataModel_RecupClave> Enviarcontrasena(@Field("email") String email);
}
