package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

import java.util.HashMap;
import java.util.Map;

public class MH_DataModel_TipoPresentacionSimple {

    private Integer id;
    private String descripcion;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion The descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
