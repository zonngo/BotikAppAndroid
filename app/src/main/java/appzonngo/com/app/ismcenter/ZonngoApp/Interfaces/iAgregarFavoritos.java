package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_AgregarFavoritos;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 29/12/2016.
 */

public interface iAgregarFavoritos {
    @FormUrlEncoded
    @POST("medicine/favorite")
    Call<MH_DataModel_AgregarFavoritos> AggFavorito(@Query("session_id") String session_id, @Field("idProducto") int idproducto);
}
