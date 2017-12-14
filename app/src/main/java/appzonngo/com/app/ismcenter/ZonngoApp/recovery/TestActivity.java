package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import appzonngo.com.app.ismcenter.zonngo2.R;

public class TestActivity extends AppCompatActivity {
    public static String TOKEN_GOOGLE = "token_google";

    String token_gmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        token_gmail = getIntent().getStringExtra(TOKEN_GOOGLE);


        //Instanciando Elementos
        TextView info_1 = (TextView) findViewById(R.id.info_1);
        info_1.setText(token_gmail);

        Button btn_cerrar_secion = (Button) findViewById(R.id.btn_cerrar_secion);
        btn_cerrar_secion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(MH_Activity_Presentacion.mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Intent signInToMain = new Intent(getApplicationContext(), MH_Activity_Presentacion.class);
                startActivity(signInToMain);
                finish();
            }
        });
    }
}
