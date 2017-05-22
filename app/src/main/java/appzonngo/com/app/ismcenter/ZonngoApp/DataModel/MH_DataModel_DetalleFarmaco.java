
package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MH_DataModel_DetalleFarmaco {

    private Integer idP;

    @SerializedName("nombreP")
    @Expose
    private String nombreP;
    private String concent;
    private Integer idTP;
    private String desTP;
    private Integer idTP2;
    private String desTP2;
    private Integer fracciones;
    private Integer idREG;
    private String codigo;
    private String vencimiento;
    private Integer idLAB;
    private String nombreLAB;
    private Integer idPRE;
    private String desPRE;
    private String estado;
    private Double precio;


    public Integer getIdP() {
        return idP;
    }

    public void setIdP(Integer idP) {
        this.idP = idP;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getConcent() {
        return concent;
    }

    public void setConcent(String concent) {
        this.concent = concent;
    }

    public Integer getIdTP() {
        return idTP;
    }

    public void setIdTP(Integer idTP) {
        this.idTP = idTP;
    }

    public String getDesTP() {
        return desTP;
    }

    public void setDesTP(String desTP) {
        this.desTP = desTP;
    }

    public Integer getIdTP2() {
        return idTP2;
    }

    public void setIdTP2(Integer idTP2) {
        this.idTP2 = idTP2;
    }

    public String getDesTP2() {
        return desTP2;
    }

    public void setDesTP2(String desTP2) {
        this.desTP2 = desTP2;
    }

    public Integer getFracciones() {
        return fracciones;
    }

    public void setFracciones(Integer fracciones) {
        this.fracciones = fracciones;
    }

    public Integer getIdREG() {
        return idREG;
    }

    public void setIdREG(Integer idREG) {
        this.idREG = idREG;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Integer getIdLAB() {
        return idLAB;
    }

    public void setIdLAB(Integer idLAB) {
        this.idLAB = idLAB;
    }

    public String getNombreLAB() {
        return nombreLAB;
    }

    public void setNombreLAB(String nombreLAB) {
        this.nombreLAB = nombreLAB;
    }

    public Integer getIdPRE() {
        return idPRE;
    }

    public void setIdPRE(Integer idPRE) {
        this.idPRE = idPRE;
    }

    public String getDesPRE() {
        return desPRE;
    }

    public void setDesPRE(String desPRE) {
        this.desPRE = desPRE;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
