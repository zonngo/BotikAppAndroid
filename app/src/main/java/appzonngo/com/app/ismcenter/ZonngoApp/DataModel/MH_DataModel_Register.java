package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

/**
 * Created by Joseris on 03/12/2016.
 */

public class MH_DataModel_Register {
    String name;
    String apellido;
    String numero;
    String telefono;
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public String getName() {
        return name;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNumero() {
        return numero;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
