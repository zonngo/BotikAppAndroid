package appzonngo.com.app.ismcenter.ZonngoApp.Interfaces;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_DetalleFarmaco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by marwuinh@gmail.com on 28/12/2016.
 */

public interface iDetalleFarmaco {
    @GET("medicine/pharmacy/{idF}/product/{idP}")
    Call<List<MH_DataModel_DetalleFarmaco>> getPreciosFarmaciasProductos(@Path("idF") int farmacia, @Path("idP") int product);
}
