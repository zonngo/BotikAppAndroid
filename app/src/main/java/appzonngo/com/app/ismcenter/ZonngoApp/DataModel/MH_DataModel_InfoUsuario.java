
package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MH_DataModel_InfoUsuario {

    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Apellidos")
    @Expose
    private String apellidos;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Telefono")
    @Expose
    private String telefono;
    @SerializedName("avatar")
    @Expose
    private String avatar;


    @Override
    public String toString() {
        return "InfoUsuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



}
