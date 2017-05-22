package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarProdByLatLog;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 07/12/2016.
 */

public interface iListarFarmcaciasUbigeo {
    @GET("medicine/search/pharmacies3")
    Call<List<MH_DataModel_ListarProdByLatLog>> getFarmacias3(@Query("product") int product, @Query("ubigeo") int ubigeo, @Query("order") int order);
}
