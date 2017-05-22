package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Ubigeo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 5/4/2017.
 */

public interface iUbigeo {
    @GET("medicine/ubigeo")
    Call<List<MH_DataModel_Ubigeo>> getUbigeo(@Query("ubigeo") int ubigeo);
}
