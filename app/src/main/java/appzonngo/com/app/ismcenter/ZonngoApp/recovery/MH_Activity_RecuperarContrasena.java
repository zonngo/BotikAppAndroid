package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_RecupClave;
import appzonngo.com.app.ismcenter.ZonngoApp.Http.HttpZonngo;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iRecuperarContrasena;
import appzonngo.com.app.ismcenter.zonngo2.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MH_Activity_RecuperarContrasena extends AppCompatActivity {
    EditText txtEmailR;
    MH_DataModel_RecupClave usuario = new MH_DataModel_RecupClave();
    private ProgressDialog loading;
    private View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh_recuperar_contrasena);
        txtEmailR = (EditText) findViewById(R.id.txtEmailR);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistrar:
                validateLogin();
                break;
        }
    }

    public void validateLogin() {

        txtEmailR.setError(null);
        String correo = txtEmailR.getText().toString().trim();
        Log.e("email", correo);
        if (correo.isEmpty()) {
            setLoginError("correo es requerido", txtEmailR);
        } else if (!isValidEmail(correo)) {

            setLoginError("formato no admitido", txtEmailR);
        } else {
            EnviarContrasena();
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void setLoginError(String error, TextView input) {
        input.setError(null);
        input.setError(error);
        focusView = input;
        focusView.requestFocus();
    }

    public void EnviarContrasena() {
        usuario.setEmail(txtEmailR.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpZonngo.URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iRecuperarContrasena service2 = retrofit.create(iRecuperarContrasena.class);
        loading = ProgressDialog.show(MH_Activity_RecuperarContrasena.this, "Cargando", "Enviando correo para recuperar contraseña", false, false);
        Call<MH_DataModel_RecupClave> call = service2.Enviarcontrasena(usuario.getEmail().toString());
        call.enqueue(new Callback<MH_DataModel_RecupClave>() {

            @Override
            public void onResponse(Call<MH_DataModel_RecupClave> call, Response<MH_DataModel_RecupClave> response) {
                Log.e("JSON iRecupContrasena", call.request().url().toString());
                //  Log.e("Enviar correo 2..............", "");
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), " Correo enviado ",
                            Toast.LENGTH_LONG).show();
                    txtEmailR.setText(" ");
                    Intent login = new Intent(getApplicationContext(), MH_Activity_Login.class);
                    startActivity(login);
                    loading.dismiss();
                } else {
                    Toast.makeText(getBaseContext(), "Correo no existe... verifique por favor.",
                            Toast.LENGTH_LONG).show();
                    txtEmailR.setText(" ");
                    loading.dismiss();
                    //    Log.e("Enviar correo 3..............", "");
                }
            }

            @Override
            public void onFailure(Call<MH_DataModel_RecupClave> call, Throwable t) {
                Toast.makeText(getBaseContext(), "No se pudo concretar" + t,
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
