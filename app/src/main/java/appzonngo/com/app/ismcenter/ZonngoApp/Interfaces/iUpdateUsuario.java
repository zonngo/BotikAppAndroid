package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_UpdateUsuario;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by marwuinh@gmail.com on 07/01/2017.
 */

public interface iUpdateUsuario {
    @FormUrlEncoded
    @POST("update")
    Call<MH_DataModel_UpdateUsuario> ActualizarDatosUsuario(@Field("session_id") String session_id, @Field("name") String name, @Field("lastName") String lastName, @Field("password") String password, @Field("telephone") String telephone, @Field("curr_password") String curr_password);
}
