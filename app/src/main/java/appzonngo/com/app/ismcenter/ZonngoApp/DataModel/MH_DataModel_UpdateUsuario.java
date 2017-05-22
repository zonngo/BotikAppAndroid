package appzonngo.com.app.ismcenter.ZonngoApp.DataModel;

/**
 * Created by Joseris on 07/01/2017.
 */

public class MH_DataModel_UpdateUsuario {
    String session_id;
    String name;
    String lastName;
    String password;
    String telephone;
    String curr_password;

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCurr_password(String curr_password) {
        this.curr_password = curr_password;
    }

    public String getName() {
        return name;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCurr_password() {
        return curr_password;
    }



}
