package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_LoginFacebook;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_LoginGoogle;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Marwuin on 24-Sep-17.
 */

public interface iLoginGoogle {
    @FormUrlEncoded
    @POST("login/google-idtoken")
    Call<MH_DataModel_LoginGoogle> iLoginGoogleApp(@Field("id_token") String access_token2, @Query("id_token") String access_token);//url
}
