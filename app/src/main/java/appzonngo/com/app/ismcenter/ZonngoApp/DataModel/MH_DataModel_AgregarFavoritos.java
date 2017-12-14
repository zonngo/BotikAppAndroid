package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;


public class MH_DataModel_AgregarFavoritos {

    private String session_id;
    private int idProducto;

    @Override
    public String toString() {
        return "Agregarfavoritos{" +
                "session_id='" + session_id + '\'' +
                ", idProducto=" + idProducto +
                '}';
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

}
