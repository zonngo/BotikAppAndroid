package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModelSugerencias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marwuinh@gmail.com on 06/12/2016.
 */

public interface iSugerenciasCTM {
    @GET("medicine/search/suggestions")
    Call<List<MH_DataModelSugerencias>> getSugerencias(@Query("keyword") String keyword, @Query("tipo") int tipo);
}
