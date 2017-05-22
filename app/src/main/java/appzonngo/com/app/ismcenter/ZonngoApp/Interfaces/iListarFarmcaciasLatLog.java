package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarProdByLatLog;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 07/12/2016.
 */

public interface iListarFarmcaciasLatLog {
    @GET("medicine/search/pharmacies2")
    Call<List<MH_DataModel_ListarProdByLatLog>> getFarmacias(@Query("product") int product, @Query("lat") double lat, @Query("lng") double lng, @Query("radius") double radius, @Query("order") int order);
}
