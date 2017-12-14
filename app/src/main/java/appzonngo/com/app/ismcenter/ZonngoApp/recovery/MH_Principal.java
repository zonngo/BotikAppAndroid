package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Notificaciones;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Ubigeo;
import appzonngo.com.app.ismcenter.ZonngoApp.Http.HttpZonngo;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS.MyGoogleApiActivity;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS.MyMaps;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS.MyNetWork;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Sesion.Preferences;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.Constants;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.Keyboard;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.MyDialoges;
import appzonngo.com.app.ismcenter.zonngo2.R;

import static appzonngo.com.app.ismcenter.ZonngoApp.recovery.MH_Activity_Presentacion.mGoogleApiClient;

public class MH_Principal extends MyGoogleApiActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPagerEx.OnPageChangeListener,
        OnMapReadyCallback {
    private static final int RC_SIGN_IN = 4546;
    public static int PAGE_ENCONTRADOS = 1;
    static int PAGE_TOTAL = 5;
    static int PAGE_MAIN = 0;
    static int PAGE_FAVORITE = 2;
    static int PAGE_NOTIFICACIONES = 3;
    static int PAGE_USER = 4;
    static int TIPO_DEFAULT = 0;
    static int TIPO_MARCA = 1;
    static int TIPO_GENERICO = 2;
    //Menu inferior
    static int ORDER_BY_DISTANCE_ASC = 0;
    static int ORDER_BY_DISTANCE_DESC = 1;
    static int ORDER_BY_PRICE_ASC = 2;
    static int ORDER_BY_PRICE_DESC = 3;
    final String LOG = "MH_Principal";
    public SwipeRefreshLayout swipeRecycler;
    public RecyclerView recyclerSugerencias;
    public RecyclerView recyclerProductos;
    public RecyclerView recyclerFavoritos;
    public RecyclerView recyclerNotificaciones;
    public MH_AdapterSugerencias adapterSugerencias;
    public MH_AdapterProdByUbication adapterProductos;
    public MH_AdapterListFavorito adapterFavoritos;
    public MH_AdapterNotificaciones adapterNotificaciones;
    BottomNavigationView bNavigation;
    //VIEW PAGER
    ViewPager mViewPager;
    View viewFlipper;
    View viewFlipperMaps;
    //conexiones Http
    HttpZonngo http;
    //clase GPS
    MyMaps myMaps;
    TextView txtNavNameUser;
    //FILTRO
    MH_BottomSheetAdapter_FIlter bsAdapter;
    List<MH_DataModel_Ubigeo> ubigeoSelected;
    int tipoSearch = TIPO_DEFAULT;
    int oderSearch = ORDER_BY_DISTANCE_ASC;
    /**
     * se ejecuta cuando se tiene disponible el Token de firebase (ver>MyFirebaseInstanceIDService)
     */
    BroadcastReceiver gpsBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(Constants.UPDATE_LOCATION, false))//si llego mensaje del GPS
            {
                if (mViewPager.getCurrentItem() == PAGE_ENCONTRADOS) {
                    Log.e("GPS Broadcast Receiver", "true");
                    LatLng coord = getLatLng();
                    myMaps.makeUserPosition(coord);
                    myMaps.animateCameraWithOutZoom(coord);

                    //PARA PRUEBAS EN VENEZUELA
                    /*
                    LatLng coordPruebasVzla=new LatLng(MyMaps.latPrueba,MyMaps.lngPrueba);
                    myMaps.makeUserPosition(coordPruebasVzla);
                    myMaps.animateCameraWithOutZoom(coordPruebasVzla);
                    */
                }
            }
        }
    };
    String generico;//para evitar otra consulta, debio llegar en detalle de farmaco
    //contenedro mapa
    LinearLayout contenMaps;
    //String nombre;
    EditText editTNombre = null;
    EditText editApellido = null;
    EditText editEmail = null;
    EditText editTelefono = null;
    EditText editPasswordAct = null;
    EditText editPasswordNext = null;
    Button btnUpdate;
    SliderLayout mDemoSlider;

    ///LatLng broadcastLatLng;
    //Para medicamentos de menor rpecio
    //ImageView imagen_farmacoMenor;
    TextView txtProductoMenor;
    TextView txtFarmaciaMenor;
    TextView txtPrecioMenor;

/*
    @CallSuper
    public void setSelectedItem(int position) {
        if (position >= getMenu().size() || position < 0) return;

        View menuItemView = getMenuItemView(position);
        if (menuItemView == null) return;
        MenuItemImpl itemData = ((MenuView.ItemView) menuItemView).getItemData();


        itemData.setChecked(true);

        boolean previousHapticFeedbackEnabled = menuItemView.isHapticFeedbackEnabled();
        menuItemView.setSoundEffectsEnabled(false);
        menuItemView.setHapticFeedbackEnabled(false); //avoid hearing click sounds, disable haptic and restore settings later of that view
        menuItemView.performClick();
        menuItemView.setHapticFeedbackEnabled(previousHapticFeedbackEnabled);
        menuItemView.setSoundEffectsEnabled(true);


        mLastSelection = position;

    }
*/
    TextView txtGenerico;
    TextView txtPriority;
    FloatingActionButton fab_maps;
    SupportMapFragment mapFragment;
    //TextView txtGenericoMenor;
    Intent moreInfoIntent;
    private String TAG = "MH_Activity_Present";
    private CallbackManager callbackManager;
    //Menu lateral
    private DrawerLayout mDrawerLayout;
    //NotificationsTask nt;
    private Timer myTimer;
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            http.VerificarNotificaciones(id_session());

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here

        }
    };
    private SearchView searchView;
    private EditText txtSearchSUgerencias;
    private View focusView = null;
    private ProgressDialog loading;

    public int getOderSearch() {
        return oderSearch;
    }

    public void setOderSearch(int oderSearch) {
        this.oderSearch = oderSearch;
    }

    public int getTipoSearch() {
        return tipoSearch;
    }

    public void setTipoSearch(int tipoSearch) {
        this.tipoSearch = tipoSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mh_activity_principal);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        super.createAPI_Google(savedInstanceState, mDrawerLayout);

        View headerLayout = ((NavigationView) mDrawerLayout.findViewById(R.id.nav_view)).getHeaderView(0);
        txtNavNameUser = (TextView) headerLayout.findViewById(R.id.txtNavNameUser);

        myMaps = new MyMaps(this);
        http = new HttpZonngo(this);

        ubigeoSelected = new ArrayList<>();

        adapterProductos = new MH_AdapterProdByUbication(http.getListProdFramByLatLng(), MH_Principal.this);
        adapterFavoritos = new MH_AdapterListFavorito(http.getListProdFravoritos());
        adapterNotificaciones = new MH_AdapterNotificaciones(http.getListaNotificaciones(), MH_Principal.this);


        http.ActualizarFavoritos(id_session());
        http.ObtenerNotificacionesAll(id_session());

        //TOOLBAR
        AppBarLayout AppBarL_ActMenu = (AppBarLayout) findViewById(R.id.AppBarL_ActMenu);
        Toolbar toolbar = (Toolbar) AppBarL_ActMenu.findViewById(R.id.Toolbar_Act);
        setSupportActionBar(toolbar);
        //MENU INFERIOR
        RelativeLayout contenBottomMenu = (RelativeLayout) findViewById(R.id.RelativeBotomNavigView);
        bNavigation = (BottomNavigationView) contenBottomMenu.findViewById(R.id.bottom_navigation);
        bNavigation.setOnNavigationItemSelectedListener(this);

        //MENU LATERAL
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //RECYCLER
        //RECUPERANDO EL USO DE ADAPTADOswipeRecycler.RES
        swipeRecycler = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshL_ActMenu);
        swipeRecycler.setEnabled(false);
        swipeRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("Refresh Global", "......................");
                if (mViewPager.getCurrentItem() == PAGE_FAVORITE) {
                    Log.e("Refresh favorite", "......................");
                    http.ActualizarFavoritos(id_session());
                }
                if (mViewPager.getCurrentItem() == PAGE_NOTIFICACIONES) {
                    Log.e("Refresh Notificaciones", "......................");
                    //http.VerificarNotificaciones(id_session());
                    http.ObtenerNotificacionesAll(id_session());
                }

                swipeRecycler.setRefreshing(false);
            }
        });
        //VIEW PAGER

        mViewPager = (ViewPager) swipeRecycler.findViewById(R.id.pager);
        mViewPager.setAdapter(new MainPageAdapter(PAGE_TOTAL));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                swipeRecycler.setEnabled(false);
                //oculta teclado
                if (position != PAGE_MAIN) {//solo cuando no esta en la pagina de busqueda
                    Keyboard.hideKeyboard(MH_Principal.this);
                    searchView.setIconified(true);//oculta barra busqueda
                    //getRecyclerSugerencias().setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    //Toast.makeText(MH_Principal.this, "page " + (position + 1), Toast.LENGTH_SHORT).show();
                }
                if (position == PAGE_MAIN) {
                    Log.e("Buscar", String.valueOf(http.getListProdFramByLatLng().size()));
                    setSelectedItem(R.id.bottomMenuLupa);
                }
                if (position == PAGE_ENCONTRADOS) {
                    Log.e("Encontrados", String.valueOf(http.getListProdFramByLatLng().size()));

                    if (http.getListProdFramByLatLng().size() > 0) {
                        //imagen_farmacoMenor=http.getListProdFramByLatLng().get(0).;
                        txtProductoMenor.setText("Fármaco: ".concat(http.getListProdFramByLatLng().get(0).getDetalleFarmaco().getNombreP())//+
                                //" "+
                                //http.getListProdFramByLatLng().get(0).getDetalleFarmaco().getConcent()
                        );
                        txtFarmaciaMenor.setText("Farmacia: ".concat(http.getListProdFramByLatLng().get(0).getDetalleFarmacias().getNombre()));
                        txtPrecioMenor.setText("Precio: ".concat("S/." + http.getListProdFramByLatLng().get(0).getDetalleFarmaco().getPrecio().toString() + "c/u"));
                        txtGenerico.setText("Genérico: ".concat(generico == null ? "No disponible" : generico));

                        if (oderSearch == ORDER_BY_DISTANCE_ASC)
                            txtPriority.setText("MÁS CERCANA");
                        else if (oderSearch == ORDER_BY_DISTANCE_DESC)
                            txtPriority.setText("MÁS LEJANA");
                        else if (oderSearch == ORDER_BY_PRICE_ASC)
                            txtPriority.setText("MENOR PRECIO");
                        else if (oderSearch == ORDER_BY_PRICE_DESC)
                            txtPriority.setText("MAYOR PRECIO");

                        Log.e("P Actico... ", ":" + generico);
                    } else {
                        txtFarmaciaMenor.setText("");
                        txtFarmaciaMenor.setText("");
                        txtFarmaciaMenor.setText("");
                        txtFarmaciaMenor.setText("");
                    }


                    adapterProductos.notifyDataSetChanged();
                    setSelectedItem(R.id.bottomMenuGPS);
                }
                if (position == PAGE_FAVORITE) {
                    Log.e("Favoritos", String.valueOf(http.getListProdFravoritos().size()));
                    //http.ActualizarFavoritos(id_session());
                    swipeRecycler.setEnabled(true);
                    setSelectedItem(R.id.bottomMenuFavoritos);
                }
                if (position == PAGE_NOTIFICACIONES) {
                    Log.e("Notificaciones", String.valueOf(http.getListaNotificaciones().size()));
                    notificationDefaultIcon(true);
                    //http.ActualizarFavoritos(id_session());
                    swipeRecycler.setEnabled(true);
                    setSelectedItem(R.id.bottomMenuNotificaciones);
                }
                if (position == PAGE_USER) {
                    Log.e("Usuario", String.valueOf(http.getListProdFravoritos().size()));
                    if (!Preferences.getBoolean(Preferences.IS_DATA, MH_Principal.this))//si no hay data
                        http.InformacionUsuario(id_session());
                    else
                        showDataUser();

                    setSelectedItem(R.id.bottomMenuUsuario);
                }

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setSelectedItem(R.id.bottomMenuLupa);


        //FILTOR
        bsAdapter = new MH_BottomSheetAdapter_FIlter(MH_Principal.this);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 10 * 60 * 1000);

        //CRENADO OYENTE BRAODCAST REVIER
        //creando el oyente a la interrupcion (MyFirebaseInstanceIDService)
        IntentFilter iFilter = new IntentFilter(Constants.BROADCAST_RECEIVER_GPS);
        LocalBroadcastManager.getInstance(this).registerReceiver(gpsBroadcastReceiver, iFilter);

        http.checkdataUser();
    }

    private void TimerMethod() {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

    public void showDataUser() {
        if (editTNombre != null)
            editTNombre.setText(Preferences.getString(Preferences.KEY_NAME, MH_Principal.this));
        if (editApellido != null)
            editApellido.setText(Preferences.getString(Preferences.KEY_LAST_NAME, MH_Principal.this));
        if (editEmail != null)
            editEmail.setText(Preferences.getString(Preferences.KEY_EMAIL, MH_Principal.this));
        if (editTelefono != null)
            editTelefono.setText(Preferences.getString(Preferences.KEY_TELEFONO, MH_Principal.this));
        if (txtNavNameUser != null)
            txtNavNameUser.setText(Preferences.getString(Preferences.KEY_NAME, MH_Principal.this) + " " +
                    Preferences.getString(Preferences.KEY_LAST_NAME, MH_Principal.this));
    }

    public void setSelectedItem(int idMenu) {
        View view = bNavigation.findViewById(idMenu);
        view.performClick();
    }

    /**
     * MENU LATERAL e INFERIOR
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Log.e("NvigationItemSelected", String.valueOf(item.getItemId()));
        int id = item.getItemId();

        if (item.getItemId() == R.id.bottomMenuLupa || item.getItemId() == R.id.navMenuLupa) {
            Log.e(LOG, "Buscar");
            if (mViewPager.getCurrentItem() != PAGE_MAIN)
                mViewPager.setCurrentItem(PAGE_MAIN);
        } else if (item.getItemId() == R.id.bottomMenuFavoritos || item.getItemId() == R.id.navMenuFavoritos) {
            Log.e(LOG, "ir a Favoritos");
            if (mViewPager.getCurrentItem() != PAGE_FAVORITE)
                mViewPager.setCurrentItem(PAGE_FAVORITE);
        } else if (item.getItemId() == R.id.bottomMenuNotificaciones || item.getItemId() == R.id.navMenuNotificaciones) {
            if (mViewPager.getCurrentItem() != PAGE_NOTIFICACIONES)
                mViewPager.setCurrentItem(PAGE_NOTIFICACIONES);
            Log.e(LOG, "ir a Notificaciones");
        } else if (item.getItemId() == R.id.bottomMenuGPS) {
            if (mViewPager.getCurrentItem() != PAGE_ENCONTRADOS)
                mViewPager.setCurrentItem(PAGE_ENCONTRADOS);
            Log.e(LOG, "ir a SMS");
        } else if (item.getItemId() == R.id.bottomMenuUsuario || item.getItemId() == R.id.navMenuUsuario) {
            if (mViewPager.getCurrentItem() != PAGE_USER)
                mViewPager.setCurrentItem(PAGE_USER);
            Log.e(LOG, "ir a MenuUsuario");
        } else if (id == R.id.nav_share2) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, " Hola! Encontré un app en la que puedes ahorrar comparando precios de medicamentos de marca vs genéricos, entra a https://app.zonngo.com/");
            startActivity(Intent.createChooser(intent, "Compartir con"));
            Log.e(LOG, "Compartir");
        } else if (id == R.id.salir) {
            //http.Logout(id_session());
            showDialogoLogout(id_session());
            Log.e(LOG, "Cerrar sesion");
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * CREAR MENU DE OPCIONES
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mh_menu_toolbar_search, menu);
        menu.findItem(R.id.menu_toolbar_search).setVisible(true);

        // Configurar barra de busqueda
        MenuItem searchItem = menu.findItem(R.id.menu_toolbar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.busqueda_farmacos).concat("..."));
        //color para el place holder y texto
        EditText txtSerach = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        txtSerach.setHintTextColor(getResources().getColor(R.color.colorVerde));
        txtSerach.setTextColor(Color.BLACK);

        // Configura el boton cancelar
        ImageView searchCloseIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        //searchCloseIcon.setImageResource(R.drawable.ic_close_light);
        searchCloseIcon.setColorFilter(getResources().getColor(R.color.colorVerde), PorterDuff.Mode.MULTIPLY);
        // Configura el boton aceptar
        searchView.setSubmitButtonEnabled(true);
        ImageView searchSubmit = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn);
        searchSubmit.setImageResource(R.drawable.ic_filter_list_white_24dp);
        searchSubmit.setColorFilter(getResources().getColor(R.color.colorVerde), PorterDuff.Mode.MULTIPLY);
        searchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre el menu contextual
                //registerForContextMenu(v);
                //openContextMenu(v);
                Keyboard.hideKeyboard(MH_Principal.this);
                bsAdapter.setCollapsed();
            }
        });
        // Configura el boton de busqueda
        ImageView searchButton = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchButton.setImageResource(R.drawable.lupa_zonngo);
        searchButton.setColorFilter(getResources().getColor(R.color.colorVerde), PorterDuff.Mode.MULTIPLY);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        // Configura el editext de busqueda
        txtSearchSUgerencias = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearchSUgerencias.setInputType(InputType.TYPE_CLASS_TEXT);
        txtSearchSUgerencias.setTextSize(16);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Evento, se solicita realizar la búsqueda pulsando el botón “Enter” del teclado (o lupa)
            @Override
            public boolean onQueryTextSubmit(String query) {
                //se oculta el EditText
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }

            //Evento, cambio texto de búsqueda.
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("onTextChanged to", newText);
                if (mViewPager.getCurrentItem() != PAGE_MAIN)
                    mViewPager.setCurrentItem(PAGE_MAIN);///va a la pagina de busqueda
                if (newText.length() > 3) {

                    if (new MyNetWork().checkNetworkConnection(MH_Principal.this))
                        http.BusquedaSugerenciasCTM(newText, tipoSearch);
                    //http.BusquedaSugerencias(newText);
                    Log.e("hhhhhhhhhhhhhhhhhh", newText);
                } else {
                    http.getListaSUgerencias().clear();
                }
                adapterSugerencias.notifyDataSetChanged();
                return true;
            }
        });

        // editext de busqueda inicialmente oculto
        searchView.setIconified(true);
        return super.onCreateOptionsMenu(menu);
    }

    public void validateUpdate() {
        if (editTNombre.getText().toString().isEmpty()) {
            setLoginError("nombre es requerido", editTNombre);
        } else if (editApellido.getText().toString().isEmpty()) {
            setLoginError("apellido es requerida", editApellido);
        } else if (editTelefono.getText().toString().isEmpty()) {
            setLoginError("teléfono es requerido", editTelefono);
        } else if (editPasswordAct.getText().toString().isEmpty()) {
            setLoginError("clave es requerida", editPasswordAct);
        } else {
            http.UpdateDataUser(
                    Preferences.getString(Preferences.KEY_SESION, MH_Principal.this),
                    editTNombre.getText().toString(),
                    editApellido.getText().toString(),
                    editPasswordNext.getText().toString(),
                    editTelefono.getText().toString(),
                    editPasswordAct.getText().toString());
        }
    }

    private void setLoginError(String error, TextView input) {
        input.setError(null);

        input.setError(error);
        focusView = input;
        focusView.requestFocus();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void SlidePresentacion() {

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.drawable.uno);
        file_maps.put("2", R.drawable.dos);
        file_maps.put("3", R.drawable.tres);
        //file_maps.put("4", R.drawable.cuatro);

        for (String name : file_maps.keySet()) {

            MH_CustomSlider textSliderView = new MH_CustomSlider(this);
            // initialize a SliderLayout
            textSliderView
                    //.description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //.setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public MH_AdapterSugerencias getAdapterSugerencias() {
        return adapterSugerencias;
    }

    public MH_AdapterProdByUbication getAdapterProductos() {
        return adapterProductos;
    }

    public EditText getTxtSearchSUgerencias() {
        return txtSearchSUgerencias;
    }

    public String id_session() {
        return Preferences.getString(Preferences.KEY_SESION, MH_Principal.this);
    }

    public void showDialogoLogout(final String idSesion) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.logout_title);
        alertDialog.setMessage(R.string.confirm_logout);
        alertDialog.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String typeLogin = Preferences.getTypeLogin(MH_Principal.this);
                switch (typeLogin) {
                    case Preferences.LOGIN_EMAIL:
                        http.Logout(idSesion);
                        break;
                    case Preferences.LOGIN_FACEBOOK:
                        if (!FacebookSdk.isInitialized())
                            FacebookSdk.sdkInitialize(getApplicationContext());
                        disconnectFromFacebook(idSesion);
                        break;
                    case Preferences.LOGIN_GOOGLE:
                        signOut();
                        break;
                }


            }
        });
        alertDialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void disconnectFromFacebook(final String idSesion) {
        loading = ProgressDialog.show(MH_Principal.this, "Cargando", "Por favor espere", false, false);
        if (AccessToken.getCurrentAccessToken() == null) {
            Log.e("disconnectFromFacebook", "AccessToken.getCurrentAccessToken() == null");
            //http.Logout(idSesion);
            loading.dismiss();
            gotoPresentacion();
            return; // already logged out

        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                Log.e("disconnectFromFacebook", "onCompleted");
                LoginManager.getInstance().logOut();
                loading.dismiss();

                gotoPresentacion();
            }
        }).executeAsync();
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
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
                gotoPresentacion();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("requed  code", String.valueOf(requestCode));
        Log.e("resul  code", String.valueOf(resultCode));
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == 64206) {

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //LLAMAR EL API PARA SOLICITAR EL SSESION ID


            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


        } else {
            // Signed out, show unauthenticated UI.
            /*updateUI(false);*/
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }

    }

    private void gotoPresentacion() {
        Preferences.logOutpPreferences(MH_Principal.this);
        Intent salir = new Intent(getApplicationContext(), MH_Activity_Presentacion.class);
        startActivity(salir);
        finish();
    }


    @Override
    protected void onDestroy() {
        //notifyON=false;
        super.onDestroy();
        myTimer.cancel();
        MyDialoges.dismissProgressDialog();
    }

    public void infoNewNotifications(MH_DataModel_Notificaciones listaNotificaciones) {
        moreInfoIntent = new Intent(this, MH_Principal.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(listaNotificaciones.getId(), "1");

        new MH_ManagerNotificacion().showNotificationCompat(
                MH_Principal.this,
                0,
                moreInfoIntent,
                R.drawable.ic_flag_white_36dp,
                getResources().getString(R.string.title_notify),
                getResources().getString(R.string.msg_notify)
        );

        if (mViewPager.getCurrentItem() != PAGE_NOTIFICACIONES)
            notificationDefaultIcon(false);


        //ic_flag_white_36dp_on
        ///SNACKBAR
        /*Snackbar.make(mDrawerLayout,"Tiene nueva(s) notificaciones", Snackbar.LENGTH_LONG)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override public void onClick(View view){
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }}).show();*/
    }

    public void notificationDefaultIcon(boolean value) {
        MenuItem item = (MenuItem) bNavigation.getMenu().findItem(R.id.bottomMenuNotificaciones);
        item.setIcon(value ? (R.drawable.ic_flag_white_36dp) : (R.drawable.ic_flag_white_36dp_on));
    }

    private void showListFinds() {
        View alertLayout = getLayoutInflater().inflate(R.layout.mh_recycler_view_modal, null);
        //LinearLayout btnTranpTop = (LinearLayout) alertLayout.findViewById(R.id.btnTranpTop);
        recyclerProductos = (RecyclerView) alertLayout.findViewById(R.id.RecyclerProductosFinds);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(MH_Principal.this));
        recyclerProductos.setHasFixedSize(true);

        ImageButton cancel = (ImageButton) alertLayout.findViewById(R.id.btnImgClose);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        final android.support.v7.app.AlertDialog alert = builder.create();

        alert.setView(alertLayout);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });

        adapterProductos.setRVOnItemClickListener(new MH_AdapterProdByUbication.ItemsClickListener() {
            @Override
            public void onClickItem(View v, int position) {
                Log.e("onClickItem: Lsita", "Pos: " + position);
                //crear la marca de esta farmacia
                //mViewPager.setCurrentItem(5);
                alert.cancel();
                createRoute(position);

                //myMaps.createRouteToPharmacy(getLatLng(),http.getListProdFramByLatLng().get(position));
            }
        });


        adapterProductos.setRVOnItemsFavoriteClickListener(new MH_AdapterProdByUbication.ItemsFavoriteClickListener() {
            @Override
            public void onFavoriteClick(View v, int position, Boolean favorite) {
                Log.e("onFavoriteClick", "Pos: " + position);
                ///IR A BASE DE DATOS
                //int idProducto = http.getListProdFramByLatLng().get(position).getDetalleFarmaco().getIdP();
                if (!favorite)
                    http.EliminarFavoritos(id_session(), http.getListProdFramByLatLng().get(position).getDetalleFarmaco().getIdP());
                else
                    http.AgregarFavoritos(id_session(), http.getListProdFramByLatLng().get(position).getDetalleFarmaco().getIdP());

                //http.ActualizarFavoritos(id_session());
            }
        });
        recyclerProductos.setAdapter(adapterProductos);


        alert.show();
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    public void createRoute(int position) {
        myMaps.getmMap().clear();
        myMaps.makePharmaciesPosition(http.getListProdFramByLatLng());

        //PARA PRUEBAS DESDE VENEZUELA
        /*LatLng vzla = new LatLng(MyMaps.latPrueba,MyMaps.lngPrueba);
        myMaps.createRouteToPharmacy(vzla,http.getListProdFramByLatLng().get(position));
        myMaps.makeUserPosition(vzla);*/
        LatLng coord = getLatLng();
        myMaps.createRouteToPharmacy(coord, http.getListProdFramByLatLng().get(position));
        myMaps.makeUserPosition(coord);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("onKeyDown()", "0");
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.e("onKeyDown()", "1");
            boolean noBack = false;
            noBack = closeBottonSheet();
            if (!noBack) {
                Log.e("onKeyDown()", "2");
                //onBackPressed();
                gotoMain();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean closeBottonSheet() {
        Log.e("closeBottonSheet()", "0");
        if (bsAdapter.isOpened()) {
            Log.e("closeBottonSheet()", "1");
            bsAdapter.setHidden();
            return true;
        }
        return false;
    }

    public void gotoMain() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish();
    }

    public MyMaps getMyMaps() {
        return myMaps;
    }

    public HttpZonngo getHttp() {
        return http;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMaps.setmMap(googleMap);
        //TENER MI UBICACION EN LOGO
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            myMaps.getmMap().setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }*/

    }

    public MH_BottomSheetAdapter_FIlter getBsAdapter() {
        return bsAdapter;
    }

    public RecyclerView getRecyclerSugerencias() {
        return recyclerSugerencias;
    }

    public MH_AdapterListFavorito getAdapterFavoritos() {
        return adapterFavoritos;
    }

    public MH_AdapterNotificaciones getAdapterNotificaciones() {
        return adapterNotificaciones;
    }

    public List<MH_DataModel_Ubigeo> getUbigeoSelected() {
        return ubigeoSelected;
    }

    public void setUbigeoSelected(List<MH_DataModel_Ubigeo> ubigeoSelected) {
        this.ubigeoSelected = ubigeoSelected;
    }

    class MainPageAdapter extends PagerAdapter {
        int numView = 0;

        public MainPageAdapter(int numView) {
            this.numView = numView;
            //viewFlipper = new View();
        }

        @Override
        public int getCount() {
            return numView;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View page = null;

            //if (viewFlipper[position] == null) {
            if (position == PAGE_MAIN) {
                //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                viewFlipper = (RelativeLayout) LayoutInflater.from(MH_Principal.this).inflate(R.layout.mh_page_main, null);
                recyclerSugerencias = (RecyclerView) viewFlipper.findViewById(R.id.RecyclerMain);
                recyclerSugerencias.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                recyclerSugerencias.setLayoutManager(new LinearLayoutManager(MH_Principal.this));
                recyclerSugerencias.setHasFixedSize(true);
                adapterSugerencias = new MH_AdapterSugerencias(http.getListaSUgerencias(), MH_Principal.this);
                adapterSugerencias.setRVOnItemClickListener(new MH_AdapterSugerencias.ItemsClickListener() {
                    @Override
                    public void onClickItem(View v, int position) {
                        txtSearchSUgerencias.setText(http.getListaSUgerencias().get(position).getNombre().concat(" ").concat(http.getListaSUgerencias().get(position).getConcent()));
                        if (getUbigeoSelected().size() == 0)
                            http.BusquedaFarmaciasPorProductoID(http.getListaSUgerencias().get(position).getId());
                        else
                            http.BusquedaFarmaciasPorUbigeo(http.getListaSUgerencias().get(position).getId(), getUbigeoSelected());
                        generico = http.getListaSUgerencias().get(position).getPactivo();
                        //MarcarFarmaciaLatLog(listaSUgerencias.get(position).getId());
                        //http.getListaSUgerencias().clear();
                        adapterSugerencias.notifyDataSetChanged();
                    }
                });
                recyclerSugerencias.setAdapter(adapterSugerencias);

                LinearLayout linear = (LinearLayout) viewFlipper.findViewById(R.id.pageMainBack);
                mDemoSlider = (SliderLayout) linear.findViewById(R.id.mh_slider);
                SlidePresentacion();

            } else if (position == PAGE_ENCONTRADOS) {
                if (viewFlipperMaps == null) {//para no reinflar el mapa
                    //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    viewFlipperMaps = (LinearLayout) LayoutInflater.from(MH_Principal.this).inflate(R.layout.mh_fragment_maps, null);
                    LinearLayout linearMaps = (LinearLayout) viewFlipperMaps.findViewById(R.id.LinearMaps);
                    CoordinatorLayout contenMapa = (CoordinatorLayout) linearMaps.findViewById(R.id.contenMapa);

                    LinearLayout btnCercana = (LinearLayout) viewFlipperMaps.findViewById(R.id.btnCercana);
                    btnCercana.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //http.getListProdFramByLatLng().get(0).
                                    //myMaps.createRouteToPharmacy(getLatLng(),http.getListProdFramByLatLng().get(0));
                                    if (http.getListProdFramByLatLng().size() > 0) {
                                        createRoute(0);
                                    } else {
                                        Toast.makeText(MH_Principal.this, "No hay nuevas busquedas", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                    //imagen_farmacoMenor = (ImageView) viewFlipperMaps.findViewById(R.id.imagen_farmaco2);
                    txtProductoMenor = (TextView) linearMaps.findViewById(R.id.txtProducto);
                    txtFarmaciaMenor = (TextView) linearMaps.findViewById(R.id.txtFarmacia);
                    txtPrecioMenor = (TextView) linearMaps.findViewById(R.id.txtPrecio);
                    txtGenerico = (TextView) linearMaps.findViewById(R.id.txtGenerico);
                    txtPriority = (TextView) linearMaps.findViewById(R.id.txtPriority);

                    fab_maps = (FloatingActionButton) linearMaps.findViewById(R.id.fab_maps);
                    fab_maps.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("setOnClickListener", "hola");
                            if (http.getListProdFramByLatLng().size() > 0) {
                                showListFinds();
                            } else {
                                Toast.makeText(MH_Principal.this, "No hay nuevas busquedas disponibles", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    //CREANDO MAPA
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.myLayoutMap);
                    mapFragment.getMapAsync(MH_Principal.this);
                }
                //////////////////
            } else if (position == PAGE_FAVORITE) {
                viewFlipper = (LinearLayout) LayoutInflater.from(MH_Principal.this).inflate(R.layout.mh_recycler_view, null);
                recyclerFavoritos = (RecyclerView) viewFlipper.findViewById(R.id.RecyclerProductos);
                TextView tvTitulo = (TextView) viewFlipper.findViewById(R.id.tvTitulo);
                tvTitulo.setText("FAVORITOS");
                recyclerFavoritos.setLayoutManager(new LinearLayoutManager(MH_Principal.this));
                recyclerFavoritos.setHasFixedSize(true);
                //
                adapterFavoritos.setRVOnItemClickListener(new MH_AdapterListFavorito.ItemsClickListener() {
                    @Override
                    public void onClickItem(View v, int position) {
                        Log.e("onFavoriteClick List", "Pos: " + position);
                    }
                });
                adapterFavoritos.setRVOnDeleteFavoriteClickListener(new MH_AdapterListFavorito.ItemsDeleteFavoriteClickListener() {
                    @Override
                    public void onDeleteFavoriteClick(View v, int position) {
                        Log.e("onDeleteFavoriteClick", "Pos: " + position);
                        http.EliminarFavoritos(id_session(), http.getListProdFravoritos().get(position).getId());
                        //http.getListProdFramByLatLng().get(position).changeFavorite();
                    }
                });
                recyclerFavoritos.setAdapter(adapterFavoritos);

            } else if (position == PAGE_NOTIFICACIONES) {
                viewFlipper = (LinearLayout) LayoutInflater.from(MH_Principal.this).inflate(R.layout.mh_recycler_view, null);
                recyclerNotificaciones = (RecyclerView) viewFlipper.findViewById(R.id.RecyclerProductos);
                TextView tvTitulo = (TextView) viewFlipper.findViewById(R.id.tvTitulo);
                tvTitulo.setText("NOTIFICACIONES");

                recyclerNotificaciones.setLayoutManager(new LinearLayoutManager(MH_Principal.this));
                recyclerNotificaciones.setHasFixedSize(true);
                //
                adapterNotificaciones.setRVOnItemClickListener(new MH_AdapterListFavorito.ItemsClickListener() {
                    @Override
                    public void onClickItem(View v, int position) {
                        Log.e("onNotif.Click List", "Pos: " + position);
                    }
                });
                recyclerNotificaciones.setAdapter(adapterNotificaciones);


            } else if (position == PAGE_USER) {
                viewFlipper = (ScrollView) LayoutInflater.from(MH_Principal.this).inflate(R.layout.mh_update_data_user, null);
                //TextView tvTitulo= (TextView) viewFlipper[position].findViewById(R.id.tvTitulo);
                //tvTitulo.setText("ACTUALIZAR DATOS");
                editTNombre = (EditText) viewFlipper.findViewById(R.id.txtNombreUpd);
                editApellido = (EditText) viewFlipper.findViewById(R.id.editApellidoUpd);
                editEmail = (EditText) viewFlipper.findViewById(R.id.txtEmailAddressUpd);
                editTelefono = (EditText) viewFlipper.findViewById(R.id.txtTelefonoUpd);
                editPasswordAct = (EditText) viewFlipper.findViewById(R.id.txtPwdAct);
                editPasswordNext = (EditText) viewFlipper.findViewById(R.id.txtPwdNext);
                btnUpdate = (Button) viewFlipper.findViewById(R.id.btnUpd);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validateUpdate();
                    }
                });


            }/* else if (position==5){//DE PRUEBA QUITAR
                    //viewFlipper[position] = (LinearLayout) LayoutInflater.from(MH_Principal.this).inflate(R.layout.mh_fragment_maps, null);
                    viewFlipper[position] = (LinearLayout) LayoutInflater.from(MH_Principal.this).inflate(R.layout.mh_fragment_maps, null);

                    //CREANDO MAPA
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.myLayoutMap);
                    mapFragment.getMapAsync(MH_Principal.this);
                }*/
            //}
            if (position == PAGE_ENCONTRADOS) {
                page = viewFlipperMaps;

            } else {
                page = viewFlipper;

            }


            collection.addView(page, 0);
            return page;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //container.removeView(object.get(position ));
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
            Log.e("destroyItem", String.valueOf(position));

        }
/*
        @Override
        public void destroyItem(View collection, int position, Object view) {
            Log.e("destroyItem",String.valueOf(position));
            ((ViewPager) collection).removeView((View) view);
            //viewFlipper[position]=null;
        }*/
    }


}
