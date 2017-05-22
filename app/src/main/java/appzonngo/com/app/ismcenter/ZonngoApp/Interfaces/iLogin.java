package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModelLogin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by marwuinh@gmail.com on 04/12/2016.
 */

public interface iLogin {
    @FormUrlEncoded
    @POST("login")
    Call<MH_DataModelLogin> LoginUsiarioApp(@Field("email") String email, @Field("password") String password);
}
