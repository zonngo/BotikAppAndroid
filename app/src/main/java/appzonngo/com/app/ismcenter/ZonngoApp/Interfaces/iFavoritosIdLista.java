package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarIdfavoritos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 10/01/2017.
 */

public interface iFavoritosIdLista {
    @GET("medicine/favorite")
    Call<List<MH_DataModel_ListarIdfavoritos>>ListarFavorito(@Query("session_id") String session_id);
}
