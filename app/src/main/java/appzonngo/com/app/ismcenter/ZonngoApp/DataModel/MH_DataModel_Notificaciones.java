package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

import java.io.Serializable;

/**
 * Created by Epica on 7/3/2017.
 */

public class MH_DataModel_Notificaciones implements Serializable {
    private String id;
    private String fecha;
    private String message;
    private String image_link;
    private String type;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
