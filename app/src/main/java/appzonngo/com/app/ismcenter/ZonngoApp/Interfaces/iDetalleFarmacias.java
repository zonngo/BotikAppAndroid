package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_DetalleFarmacia;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by marwuinh@gmail.com on 28/11/2016.
 */

public interface iDetalleFarmacias {
    @GET("medicine/pharmacy/{idF}")
    Call<List<MH_DataModel_DetalleFarmacia>> getFarmacias(@Path("idF") int farmacia);
}
