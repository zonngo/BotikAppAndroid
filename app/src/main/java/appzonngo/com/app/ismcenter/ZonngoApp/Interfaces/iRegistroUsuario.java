package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Register;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by marwuinh@gmail.com on 03/12/2016.
 */

public interface iRegistroUsuario {

    @FormUrlEncoded
    @POST("new")
    Call<MH_DataModel_Register> Insertar_datos_usuario(@Field("name") String name, @Field("email") String email, @Field("lastName") String lastName, @Field("password") String password, @Field("telephone") String telephone);

}
