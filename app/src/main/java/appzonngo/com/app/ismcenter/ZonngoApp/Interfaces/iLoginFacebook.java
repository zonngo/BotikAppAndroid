package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_LoginFacebook;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 04/12/2016.
 */

public interface iLoginFacebook {
    @FormUrlEncoded
    @POST("login/facebook-token")
    Call<MH_DataModel_LoginFacebook> LoginFacebookApp(@Field("access_token") String access_token2, @Query("access_token") String access_token);//url
    //Call<MH_DataModelLogin> LoginUsiarioApp(@Field("access_token") String access_token);//cuerpo
}
