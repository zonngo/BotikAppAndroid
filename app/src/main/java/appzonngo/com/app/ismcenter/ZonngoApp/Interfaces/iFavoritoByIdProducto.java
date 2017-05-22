package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_DetalleFavoritos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by marwuinh@gmail.com on 11/01/2017.
 */

public interface iFavoritoByIdProducto {
    @GET("medicine/product/{id}")
    Call<List<MH_DataModel_DetalleFavoritos>> verMedicamento(@Path("id") int id);
}
