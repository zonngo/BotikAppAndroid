
package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MH_DataModel_ListarIdfavoritos {
    @SerializedName("idProducto")
    @Expose
    private Integer idProducto;

    @Override
    public String toString() {
        return "Listarfavoritos{" +
                "idProducto=" + idProducto +
                '}';
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

}
