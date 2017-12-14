package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Register;
import appzonngo.com.app.ismcenter.ZonngoApp.Http.HttpZonngo;
import appzonngo.com.app.ismcenter.zonngo2.R;

public class MH_Activity_Register extends AppCompatActivity {
    EditText txtNombre;
    EditText txtApellido;
    EditText txtE_mail;
    EditText txtTelefono;
    EditText txtPwd1;
    EditText txtPwd2;
    private View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh_page_register_user);
        //

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtE_mail = (EditText) findViewById(R.id.txtE_mail);
        txtTelefono = (EditText) findViewById(R.id.txtTelefonos);
        txtPwd1 = (EditText) findViewById(R.id.txtPwd1);
        txtPwd2 = (EditText) findViewById(R.id.txtPwd2);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistrar:
                validateRegister();
                break;
        }
    }

    public void validateRegister() {
        if (txtNombre.getText().toString().isEmpty()) {
            setLoginError("nombre es requerido", txtNombre);
        } else if (txtApellido.getText().toString().isEmpty()) {
            setLoginError("apellido es requerida", txtApellido);
        } else if (txtE_mail.getText().toString().isEmpty()) {
            setLoginError("email es requerido", txtE_mail);
        } else if (!isValidEmail(txtE_mail.getText().toString())) {
            setLoginError("formato no admitido", txtE_mail);
        } else if (txtTelefono.getText().toString().isEmpty()) {
            setLoginError("teléfono es requerido", txtTelefono);
        } else if (txtPwd1.getText().toString().isEmpty()) {
            setLoginError("clave es requerida", txtPwd1);
        } else if (txtPwd1.getText().toString().length() < 6) {
            setLoginError("6 caracteres mínimo", txtPwd1);
        } else if (txtPwd2.getText().toString().isEmpty()) {
            setLoginError("confirmación es requerida", txtPwd2);
        } else if (!txtPwd2.getText().toString().equals(txtPwd1.getText().toString())) {
            setLoginError("claves no coinciden", txtPwd2);
        } else {
            Registrar_usurario();
        }
    }

    private void setLoginError(String error, TextView input) {
        input.setError(null);

        input.setError(error);
        focusView = input;
        focusView.requestFocus();
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    public void Registrar_usurario() {
        MH_DataModel_Register usuario = new MH_DataModel_Register();
        usuario.setName(txtNombre.getText().toString());
        usuario.setApellido(txtApellido.getText().toString());
        usuario.setNumero(txtTelefono.getText().toString());
        usuario.setPassword(txtPwd1.getText().toString());
        usuario.setEmail(txtE_mail.getText().toString());
        new HttpZonngo(MH_Activity_Register.this).RegisterUser(usuario);
    }
}
