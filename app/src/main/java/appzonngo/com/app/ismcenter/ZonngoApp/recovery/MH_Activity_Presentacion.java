package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.messages.internal.Update;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.vision.text.Text;

import java.util.Arrays;
import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModelLogin;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_LoginFacebook;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_LoginGoogle;
import appzonngo.com.app.ismcenter.ZonngoApp.Http.HttpZonngo;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iLogin;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iLoginFacebook;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iLoginGoogle;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.MyDialoges;
import appzonngo.com.app.ismcenter.zonngo2.R;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Sesion.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MH_Activity_Presentacion extends FragmentActivity implements  GoogleApiClient.OnConnectionFailedListener {

    private String TAG = "MH_Activity_Present";
    static int PAGE_TOTAL=3;
    static int PAGE_1=0;
    static int PAGE_2=1;
    static int PAGE_3=2;
    static GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 343;
    ViewPager pagerPresentacion;
    View viewFlipper;
    private LinearLayout dotsLayout;
    private TextView[] dots;

    GoogleSignInOptions gso;
    private LoginButton btnWithFacebook;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(Preferences.isLogin(MH_Activity_Presentacion.this)){
            Intent intent = new Intent(getApplicationContext(),MH_Principal.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.mh_activity_presentacion);
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();


            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .addScope(new Scope(Scopes.PROFILE))
                    .addScope(new Scope(Scopes.EMAIL))
                    .build();

            dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
            dots = new TextView[PAGE_TOTAL];

            pagerPresentacion = (ViewPager) findViewById(R.id.pagerPresentacion);
            pagerPresentacion.setAdapter(new MainPageAdapter(PAGE_TOTAL));
            pagerPresentacion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    addBottomDots(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            if (!Preferences.isNotNewApp(MH_Activity_Presentacion.this)) {
                addBottomDots(PAGE_1);
                pagerPresentacion.setCurrentItem(PAGE_1);
            } else {
                addBottomDots(PAGE_3);
                pagerPresentacion.setCurrentItem(PAGE_3);
            }
        }
    }


    Button   btnWithEmail;
    TextView ImgBtnEntrar;
    TextView eti_terminosycondiciones;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    class MainPageAdapter extends PagerAdapter {
        int numView=0;

        public MainPageAdapter(int numView) {
            this.numView = numView;
        }

        @Override
        public int getCount() {
            return numView;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View page = null;

            if(position==PAGE_1){
                viewFlipper = (FrameLayout) LayoutInflater.from(MH_Activity_Presentacion.this).inflate(R.layout.mh_presentacion_1, null);

            } else if (position==PAGE_2){
                viewFlipper = (FrameLayout) LayoutInflater.from(MH_Activity_Presentacion.this).inflate(R.layout.mh_presentacion_2, null);

            } else if (position==PAGE_3){


                viewFlipper = (FrameLayout) LayoutInflater.from(MH_Activity_Presentacion.this).inflate(R.layout.mh_presentacion_3, null);

                TextView txtTitle = (TextView) viewFlipper.findViewById(R.id.txtTitle);
                Typeface face=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
                txtTitle.setTypeface(face);



                btnWithFacebook = (LoginButton)viewFlipper.findViewById(R.id.btnWithFacebook);
                btnWithFacebook.setReadPermissions(Arrays.asList("user_status"));
                //LoginManager.getInstance().logInWithReadPermissions(MH_Activity_Presentacion.this, Arrays.asList("email","public_profile"));
                btnWithFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
                btnWithEmail=(Button) viewFlipper.findViewById(R.id.btnWithEmail);
                ImgBtnEntrar=(TextView) viewFlipper.findViewById(R.id.ImgBtnEntrar);
                ImageView imagenzongo= (ImageView)viewFlipper.findViewById(R.id.imagenzongo);
                SignInButton btnWithGoogle = (SignInButton) viewFlipper.findViewById(R.id.btnWithGoogle);
                eti_terminosycondiciones= (TextView )viewFlipper.findViewById(R.id.eti_terminosycondiciones);

                btnWithFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //AccessToken.getCurrentAccessToken().getPermissions();
                        //String nombreFacebook = Profile.getCurrentProfile().getFirstName();
                        //String apellidoFacebook = Profile.getCurrentProfile().getLastName();

                        String tokenFacebook = loginResult.getAccessToken().getToken();
                        String idFacebook = loginResult.getAccessToken().getUserId();

                        //Log.e("NOMBRE Facebook",nombreFacebook);
                        //Log.e("APELLIDO Facebook",apellidoFacebook);
                        Log.e("ID Facebook",idFacebook);
                        Log.e("TOKEN Facebook",tokenFacebook);

                        Login_Facebook(tokenFacebook);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MH_Activity_Presentacion.this, "Login Canceled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                       Toast.makeText(MH_Activity_Presentacion.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });


                btnWithGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        signIn();

                    }
                });
                imagenzongo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signOut();
                    }
                });
               /* btnWithGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }

                });*/
                /*btnWithFacebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MH_Activity_Presentacion.this,"Opción en construción",Toast.LENGTH_LONG).show();
                    }
                });*/

                btnWithEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialoges.showProgressDialog(MH_Activity_Presentacion.this,"Espere...");
                        Intent intent = new Intent(getApplicationContext(), MH_Activity_Register.class);
                        startActivity(intent);
                        //finish();
                    }
                });

                ImgBtnEntrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialoges.showProgressDialog(MH_Activity_Presentacion.this,"Espere...");
                        Intent intent = new Intent(getApplicationContext(), MH_Activity_Login.class);
                        startActivity(intent);
                        //finish();
                    }
                });

                eti_terminosycondiciones.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri webpage = Uri.parse("https://medium.com/zonngo-news/términos-y-condiciones-de-zonngo-a7e5c00fc213");
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                        PackageManager packageManager = getPackageManager();
                        List activities = packageManager.queryIntentActivities(webIntent, 0);
                        boolean isIntentSafe = activities.size() > 0;

                        if (isIntentSafe) {
                            startActivity(webIntent);
                        }
                    }
                });

            }


            page = viewFlipper;
            collection.addView(page, 0);
            return page;


        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.e("destroyItem",String.valueOf(position));
        }
    }

    private void addBottomDots(int currentPage) {


        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyDialoges.dismissProgressDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyDialoges.dismissProgressDialog();
    }
    private void signIn() {
        Log.e(TAG, "signIn()");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                /*Intent signInToMain = new Intent(getApplicationContext(),MH_Activity_Presentacion.class);
                startActivity(signInToMain);*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("requed  code",String.valueOf(requestCode));
        Log.e("resul  code",String.valueOf(resultCode));
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else if(requestCode==64206){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //LLAMAR EL API PARA SOLICITAR EL SSESION ID


            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
           ;

            String nombre=acct.getDisplayName();
            String correo=acct.getEmail();
            String family_nameG=acct.getFamilyName();
            String given_name=acct.getGivenName();
            String idd_gmail=acct.getId();


            String token_gmail=acct.getIdToken();






            //Iprimiendo en consola
            Log.e("hola","como estas");
            Log.e("NOMBRE",nombre);
            Log.e("CORREO",correo);
            Log.e("FAMILY NAME",family_nameG);
            Log.e("GIVEN NAME",given_name);
            Log.e("ID",idd_gmail);
            Log.e("TOKNE GMAIL",token_gmail);

            //Log.e("SERVER GMAIL",Server_gmail);

            Login_Google(token_gmail);
            //MyDialoges.showProgressDialog(MH_Activity_Presentacion.this,"Espere...");
            /*Intent intent = new Intent(getApplicationContext(), TestActivity.class);
            intent.putExtra(TestActivity.TOKEN_GOOGLE, idd_gmail);
            startActivity(intent);*/
            //finish();
           /* mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);*/
        } else {
            // Signed out, show unauthenticated UI.
            /*updateUI(false);*/
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }

    }


    private ProgressDialog loading;
    public void Login_Facebook(String access_token)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpZonngo.URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iLoginFacebook service=retrofit.create(iLoginFacebook.class);
        final MH_DataModelLogin usuario=new MH_DataModelLogin();

        Call<MH_DataModel_LoginFacebook> call=service.LoginFacebookApp(access_token,access_token);


        loading = ProgressDialog.show(MH_Activity_Presentacion.this, "Cargando", "Por favor espere", false, false);
        call.enqueue(new Callback<MH_DataModel_LoginFacebook>() {
            @Override
            public void onResponse(Call<MH_DataModel_LoginFacebook> call, Response<MH_DataModel_LoginFacebook> response) {
                if(response.isSuccessful())
                {
                    //frgmentinfoUsu.InformacionUsuario(response.body().getSessionId());
                    String Id_session=response.body().getSession_id();
                    // setId_session(response.body().getSessionId().toString());
                    Log.e("ID_SESION", "" + response.body().getSession_id());

                    Intent login = new Intent(getApplicationContext(), MH_Principal.class);
                    startActivity(login);
                    loading.dismiss();

                    //Se indicque que esta logueado
                    Preferences.clearDataUser(MH_Activity_Presentacion.this);
                    //Se limpia data de usuario
                    Preferences.setLogin(MH_Activity_Presentacion.this, true, Preferences.LOGIN_FACEBOOK, response.body().getSession_id(), "");
                    finish();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Ocurrio un error de autenticación",
                            Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<MH_DataModel_LoginFacebook> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error en la base de datos"+t,
                        Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });

    }

    public void Login_Google(String access_token)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpZonngo.URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final iLoginGoogle service=retrofit.create(iLoginGoogle.class);
        final MH_DataModelLogin usuario=new MH_DataModelLogin();

        Call<MH_DataModel_LoginGoogle> call=service.iLoginGoogleApp(access_token,access_token);


        loading = ProgressDialog.show(MH_Activity_Presentacion.this, "Cargando", "Por favor espere", false, false);
        call.enqueue(new Callback<MH_DataModel_LoginGoogle>() {
            @Override
            public void onResponse(Call<MH_DataModel_LoginGoogle> call, Response<MH_DataModel_LoginGoogle> response) {
                if(response.isSuccessful())
                {
                    //frgmentinfoUsu.InformacionUsuario(response.body().getSessionId());
                    String Id_session=response.body().getSession_id();
                    // setId_session(response.body().getSessionId().toString());
                    Log.e("ID_SESION", "" + response.body().getSession_id());

                    Intent login = new Intent(getApplicationContext(), MH_Principal.class);
                    startActivity(login);
                    loading.dismiss();

                    //Se indicque que esta logueado
                    Preferences.clearDataUser(MH_Activity_Presentacion.this);
                    //Se limpia data de usuario
                    Preferences.setLogin(MH_Activity_Presentacion.this, true, Preferences.LOGIN_GOOGLE, response.body().getSession_id(), "");
                    finish();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Ocurrio un error de autenticación",
                            Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<MH_DataModel_LoginGoogle> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error en la base de datos"+t,
                        Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });


        ///Prueba Aesion Google



    }


}
