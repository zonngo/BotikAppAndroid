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

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_LoginFacebook;
import appzonngo.com.app.ismcenter.ZonngoApp.Http.HttpZonngo;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iLogin;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iLoginFacebook;
import appzonngo.com.app.ismcenter.zonngo2.R;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModelLogin;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Sesion.Preferences;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS.MyNetWork;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MH_Activity_Login extends AppCompatActivity {
    EditText txtEmail;
    EditText txtPassword;
    private View focusView = null;
    //InformacionUsuario frgmentinfoUsu=new InformacionUsuario();

    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh_login);
        txtEmail =(EditText) findViewById(R.id.txtEmail);
        txtPassword =(EditText) findViewById(R.id.txtPass);

        //txtEmail.setText("enorelisp@gmail.com");
        //txtPassword.setText("123456");
        /*if(revisarGps()){
            DialogSiNoPersonal2 ubi = new DialogSiNoPersonal2();
            ubi.show(getSupportFragmentManager(),"DialogSiNoPersonal2");
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       else*/{
            if(Preferences.isLogin(MH_Activity_Login.this))
            {
                Log.e("SESION ID",Preferences.getIdSesion(MH_Activity_Login.this));
                loading = ProgressDialog.show(MH_Activity_Login.this, "Cargando", "Por favor espere", false, false);
                Intent login = new Intent(getApplicationContext(), MH_Principal.class);
                //login.putExtra("idsession",session.getUserDetails().get(session.Key_ID));

                startActivity(login);
                loading.dismiss();
                finish();
            }
        }
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.);
    }


    public void onClick(View view) {
        if(new MyNetWork().checkNetworkConnection(this)) {
            switch (view.getId()) {

                case R.id.btnLogin:
                    validateLogin();
                    break;
                case R.id.btnRecoveryPassword:
                    Intent ListSong = new Intent(getApplicationContext(), MH_Activity_RecuperarContrasena.class);
                    startActivity(ListSong);
                    break;
                case R.id.btnRegister:
                    Intent ListSong2 = new Intent(getApplicationContext(), MH_Activity_Register.class);
                    startActivity(ListSong2);
                    break;
            }
        }
    }

    public void validateLogin() {
        if (txtEmail.getText().toString().isEmpty()) {
            setLoginError("correo es requerido", txtEmail);
        } else if (!isValidEmail(txtEmail.getText().toString())) {
            setLoginError("formato no admitido", txtEmail);
        } else if (txtPassword.getText().toString().isEmpty()) {
            setLoginError("clave es requerida", txtPassword);
        } else if (txtPassword.getText().toString().length()<6) {
            setLoginError("6 caracteres mínimo", txtPassword);
        } else {
            Login_usurario();
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void setLoginError(String error, TextView input) {
        txtEmail.setError(null);
        txtPassword.setError(null);

        input.setError(error);
        focusView = input;
        focusView.requestFocus();
    }


    public void Login_usurario()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpZonngo.URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iLogin service=retrofit.create(iLogin.class);
        final MH_DataModelLogin usuario=new MH_DataModelLogin();
        Call<MH_DataModelLogin> call=service.LoginUsiarioApp(txtEmail.getText().toString(), txtPassword.getText().toString());
        loading = ProgressDialog.show(MH_Activity_Login.this, "Cargando", "Por favor espere", false, false);
        call.enqueue(new Callback<MH_DataModelLogin>() {
            @Override
            public void onResponse(Call<MH_DataModelLogin> call, Response<MH_DataModelLogin> response) {
                if(response.isSuccessful())
                {
                    //frgmentinfoUsu.InformacionUsuario(response.body().getSessionId());
                    String Id_session=response.body().getSessionId();
                   // setId_session(response.body().getSessionId().toString());
                    Log.e("ID_SESION", "" + response.body().getSessionId());

                    Intent login = new Intent(getApplicationContext(), MH_Principal.class);
                    startActivity(login);
                    loading.dismiss();

                    //Se indicque que esta logueado
                    Preferences.clearDataUser(MH_Activity_Login.this);
                    //Se limpia data de usuario
                    Preferences.setLogin(MH_Activity_Login.this, true, Preferences.LOGIN_EMAIL, response.body().getSessionId(), txtPassword.getText().toString());
                    finish();
                }else
                {
                    Toast.makeText(getBaseContext(),"Usuario o contraseña invalido o correo no validado, verifique si confirmo el correo.",
                            Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<MH_DataModelLogin> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error en la base de datos"+t,
                        Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });

    }


/*
    @Override
    public void onPossitiveButtonClick() {
        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        finish();
    }
*/
    /*
    public boolean revisarGps(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        return (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER )); // si lo tiene prendido entonces retorna falso

    }

*/

}
