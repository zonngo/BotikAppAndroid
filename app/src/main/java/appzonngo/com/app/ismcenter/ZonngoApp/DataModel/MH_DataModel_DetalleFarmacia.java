
package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

import java.util.HashMap;
import java.util.Map;

public class MH_DataModel_DetalleFarmacia {

    private Integer id;
    private String estado;
    private String tipoEst;
    private String nombre;
    private Object ubigeo;
    private String direccion;
    private String provincia;
    private String telefono;
    private Object horario;
    private String dtecnico;
    private Float lat;
    private Float lng;
    private String distrito;
    private String departamento;
    private String ruc;
    private String situacion;
    private String categoria;
    private String razonSocial;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * 
     * @param estado
     *     The estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * 
     * @return
     *     The tipoEst
     */
    public String getTipoEst() {
        return tipoEst;
    }

    /**
     * 
     * @param tipoEst
     *     The tipo_est
     */
    public void setTipoEst(String tipoEst) {
        this.tipoEst = tipoEst;
    }

    /**
     * 
     * @return
     *     The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre
     *     The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @return
     *     The ubigeo
     */
    public Object getUbigeo() {
        return ubigeo;
    }

    /**
     * 
     * @param ubigeo
     *     The ubigeo
     */
    public void setUbigeo(Object ubigeo) {
        this.ubigeo = ubigeo;
    }

    /**
     * 
     * @return
     *     The direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * 
     * @param direccion
     *     The direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * 
     * @return
     *     The provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * 
     * @param provincia
     *     The provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * 
     * @return
     *     The telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * 
     * @param telefono
     *     The telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * 
     * @return
     *     The horario
     */
    public Object getHorario() {
        return horario;
    }

    /**
     * 
     * @param horario
     *     The horario
     */
    public void setHorario(Object horario) {
        this.horario = horario;
    }

    /**
     * 
     * @return
     *     The dtecnico
     */
    public String getDtecnico() {
        return dtecnico;
    }

    /**
     * 
     * @param dtecnico
     *     The dtecnico
     */
    public void setDtecnico(String dtecnico) {
        this.dtecnico = dtecnico;
    }

    /**
     * 
     * @return
     *     The lat
     */
    public Float getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     *     The lat
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }

    /**
     * 
     * @return
     *     The lng
     */
    public Float getLng() {
        return lng;
    }

    /**
     * 
     * @param lng
     *     The lng
     */
    public void setLng(Float lng) {
        this.lng = lng;
    }

    /**
     * 
     * @return
     *     The distrito
     */
    public String getDistrito() {
        return distrito;
    }

    /**
     * 
     * @param distrito
     *     The distrito
     */
    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    /**
     * 
     * @return
     *     The departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * 
     * @param departamento
     *     The departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * 
     * @return
     *     The ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * 
     * @param ruc
     *     The ruc
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    /**
     * 
     * @return
     *     The situacion
     */
    public String getSituacion() {
        return situacion;
    }

    /**
     * 
     * @param situacion
     *     The situacion
     */
    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    /**
     * 
     * @return
     *     The categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * 
     * @param categoria
     *     The categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * 
     * @return
     *     The razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * 
     * @param razonSocial
     *     The razonSocial
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
