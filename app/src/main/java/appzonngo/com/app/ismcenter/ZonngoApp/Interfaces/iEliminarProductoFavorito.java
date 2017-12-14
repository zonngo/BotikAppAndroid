package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_AddFavorito;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 14/01/2017.
 */

public interface iEliminarProductoFavorito {
    @FormUrlEncoded
    @POST("medicine/favorite/delete")
    Call<MH_DataModel_AddFavorito> EliminarFavorito(@Query("session_id") String session_id, @Field("idProducto") int idproducto);
}
