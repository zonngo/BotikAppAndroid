package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

/**
 * Created by Joseris on 11/01/2017.
 */

public class MH_DataModel_DetalleFavoritos {

    private Integer id;
    private String nombre;
    private String concentracion;
    private String tipo;
    private MH_DataModel_TipoPresentacion tipoPresentacion;
    private MH_DataModel_TipoPresentacionSimple tipoPresentacionSimple;
    private Integer fracciones;
    private MH_DataModel_RegistroSanitario registroSanitario;
    private MH_DataModel_Laboratorio laboratorio;
    private MH_DataModel_Presentacion presentacion;
    private String estado;
    private String pactivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public MH_DataModel_TipoPresentacion getTipoPresentacion() {
        return tipoPresentacion;
    }

    public void setTipoPresentacion(MH_DataModel_TipoPresentacion tipoPresentacion) {
        this.tipoPresentacion = tipoPresentacion;
    }

    public MH_DataModel_TipoPresentacionSimple getTipoPresentacionSimple() {
        return tipoPresentacionSimple;
    }

    public void setTipoPresentacionSimple(MH_DataModel_TipoPresentacionSimple tipoPresentacionSimple) {
        this.tipoPresentacionSimple = tipoPresentacionSimple;
    }

    public Integer getFracciones() {
        return fracciones;
    }

    public void setFracciones(Integer fracciones) {
        this.fracciones = fracciones;
    }

    public MH_DataModel_RegistroSanitario getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(MH_DataModel_RegistroSanitario registroSanitario) {
        registroSanitario = registroSanitario;
    }

    public MH_DataModel_Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(MH_DataModel_Laboratorio laboratorio) {
        laboratorio = laboratorio;
    }

    public MH_DataModel_Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(MH_DataModel_Presentacion presentacion) {
        presentacion = presentacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPactivo() {
        return pactivo;
    }

    public void setPactivo(String pactivo) {
        this.pactivo = pactivo;
    }
}

