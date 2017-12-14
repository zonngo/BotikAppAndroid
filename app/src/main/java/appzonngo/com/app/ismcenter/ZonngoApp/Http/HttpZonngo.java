package appzonngo.com.app.ismcenter.ZonngoApp.Http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModelSugerencias;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_AddFavorito;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_AgregarFavoritos;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_CerrarSesion;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_DetalleFarmacia;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_DetalleFarmaco;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_DetalleFavoritos;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_InfoUsuario;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarIdfavoritos;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarProdByLatLog;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Notificaciones;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Register;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Ubigeo;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_UpdateUsuario;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iAgregarFavoritos;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iCerrarSesion;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iDetalleFarmacias;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iDetalleFarmaco;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iEliminarProductoFavorito;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iFavoritoByIdProducto;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iFavoritosIdLista;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iInformacionUser;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iListarFarmcaciasLatLog;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iListarFarmcaciasUbigeo;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iNotificacion;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iNotificationNew;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iRegistroUsuario;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iSugerencias;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iSugerenciasCTM;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iUbigeo;
import appzonngo.com.app.ismcenter.ZonngoApp.Interfaces.iUpdateUsuario;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.MH_Activity_Login;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.MH_Activity_Presentacion;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.MH_Principal;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Sesion.Preferences;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.Calculate;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.MyDialoges;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marwuin on 21/3/2017.
 */

public class HttpZonngo {

    public final static String URL_BASE_ACOUNT = "https://auth.zonngo.com/";
    private final String URL_BASE = "https://api.zonngo.com/v1.0/";
    private final String URL_Vieja_Vieja = "https://accounts.zonngo.com/";
    Context myContext;
    Boolean readyPrice[];
    Boolean readyDetailPharmacy[];
    //5. LISTA DE FAVORITO
    Boolean ready[];
    private List<MH_DataModelSugerencias> listaSUgerencias;
    private List<MH_DataModel_ListarProdByLatLog> listProdFramByLatLng;
    private List<MH_DataModel_DetalleFavoritos> listProdFravoritos;
    private List<MH_DataModel_Notificaciones> listaNotificaciones;
    private List<MH_DataModel_Ubigeo> listUbigeo;
    private Boolean dataDetalleFarmReady = false;
    private Boolean dataDetalleProdReady = false;


    //1. BUSCAR PREFERENCIAS
    private MH_Principal vista;

    public HttpZonngo(Context myContext) {
        this.myContext = myContext;
    }

    public HttpZonngo(MH_Principal vista) {
        this.vista = vista;
        myContext = vista;
        listaSUgerencias = new ArrayList<>();
        listProdFramByLatLng = new ArrayList<>();
        listProdFravoritos = new ArrayList<>();
        listaNotificaciones = new ArrayList<>();
        listUbigeo = new ArrayList<>();
        //detalleFarmacias = new ArrayList<>();
        //ListaPrecios = new ArrayList<>();
    }

    public void BusquedaSugerencias(String buscar) {
        Log.e("Search sugerencys from", buscar);


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iSugerencias service = retrofit.create(iSugerencias.class);


        service.getSugerencias(buscar).enqueue(new Callback<List<MH_DataModelSugerencias>>() {
            @Override
            public void onResponse(Call<List<MH_DataModelSugerencias>> call, Response<List<MH_DataModelSugerencias>> response) {
                listaSUgerencias.clear();
                if (response.isSuccessful()) {
                    Log.e("JSON sugerencias", call.request().url().toString());

                    for (int i = 0; i < response.body().size(); i++) {
                        Log.e("GENERICO", "num:" + i + " -> " + response.body().get(i).getPactivo());
                        listaSUgerencias.add(response.body().get(i));

                        /*if(i==response.body().size()-1)
                            vista.getRecyclerSugerencias().setBackgroundColor(vista.getResources().getColor(R.color.colorWhite));*/
                    }
                    //listaSUgerencias = response.body();

                    Log.e("Numero de Items", String.valueOf(listaSUgerencias.size()));

                }
                vista.getAdapterSugerencias().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MH_DataModelSugerencias>> call, Throwable t) {
                Log.e("error...............", t.toString());
            }
        });
    }

    public void BusquedaSugerenciasCTM(String buscar, int tipo) {
        Log.e("Search sugerencys from", buscar);


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iSugerenciasCTM service = retrofit.create(iSugerenciasCTM.class);


        service.getSugerencias(buscar, tipo).enqueue(new Callback<List<MH_DataModelSugerencias>>() {
            @Override
            public void onResponse(Call<List<MH_DataModelSugerencias>> call, Response<List<MH_DataModelSugerencias>> response) {
                listaSUgerencias.clear();
                if (response.isSuccessful()) {
                    Log.e("JSON sugerencias", call.request().url().toString());

                    for (int i = 0; i < response.body().size(); i++) {
                        Log.e("GENERICO", "num:" + i + " -> " + response.body().get(i).getPactivo());
                        listaSUgerencias.add(response.body().get(i));

                        /*if(i==response.body().size()-1)
                            vista.getRecyclerSugerencias().setBackgroundColor(vista.getResources().getColor(R.color.colorWhite));*/
                    }
                    //listaSUgerencias = response.body();

                    Log.e("Numero de Items", String.valueOf(listaSUgerencias.size()));

                }
                vista.getAdapterSugerencias().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MH_DataModelSugerencias>> call, Throwable t) {
                Log.e("error...............", t.toString());
            }
        });
    }

    //2. DATOS SOBRE EL PRODUCTO FARMACIA,PRECIO
    public void BusquedaFarmaciasPorProductoID(final int id) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //OJO CAPTURAR LA UBICACION DEL USUARIO
        /*
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(lat, lng), 13);
        mMap.moveCamera(camUpd1);

        */
        /*Double lat= MyMaps.latPrueba;
        Double lng= MyMaps.lngPrueba;
        Log.e("buscar famacias: ","ID: "+id+", Lat: "+lat+", Lng: "+lng+", order: "+vista.getOderSearch());
*/
        LatLng latLng = vista.getLatLng();
        Double lat = latLng.latitude;
        Double lng = latLng.longitude;
        //no funciona con coordenadas de venezuela, por eso se dejan estaticas para pruebas

        MyDialoges.showProgressDialog(vista, "Buscando farmacias... Por favor espere");


        final iListarFarmcaciasLatLog service = retrofit.create(iListarFarmcaciasLatLog.class);

        service.getFarmacias(id, lat, lng, (double) 3.0, vista.getOderSearch()).enqueue(new Callback<List<MH_DataModel_ListarProdByLatLog>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_ListarProdByLatLog>> call, Response<List<MH_DataModel_ListarProdByLatLog>> response) {
                Log.e("JSON Farmacias", call.request().url().toString());
                if (response.isSuccessful()) {

                    listProdFramByLatLng.clear();
                    refreshingData();

                    readyPrice = new Boolean[response.body().size()];
                    readyDetailPharmacy = new Boolean[response.body().size()];
                    Log.e("Farmacias en la zona: ", String.valueOf(response.body().size()));
                    for (int i = 0; i < response.body().size(); i++) {// de tdoas las farmacias
                        readyPrice[i] = false;
                        readyDetailPharmacy[i] = false;
                        listProdFramByLatLng.add(i, response.body().get(i));
                        Integer idFarmacia = listProdFramByLatLng.get(i).getId();
                        Double distanceFarm = new Calculate().Redondear(listProdFramByLatLng.get(i).getDistance());
                        Log.e("Farmacias data", "id: " + idFarmacia + ", Distance: " + distanceFarm);
                        BuscarPrecioFarmaciaProducto(i, idFarmacia, id);//se busca el precio de un procucto

                        //BuscarPrecioFarmaciaProductoResp(idFarmacia, id);
                        MostrarDetalleFarmacias(i, idFarmacia);//busca ubicacion completa de la farmacia
                    }

                    Boolean isFavorite = false;
                    if (response.body().size() == 0) {
                        Toast.makeText(vista, "No se recibieron resultados", Toast.LENGTH_LONG).show();
                        //EN ESTE CASO BUSCAR MENOR PRECIO EN LIMA
                        // BUSCAR id EN


                    } else {//se verifica si el producto (id buscado) es favorito
                        for (int j = 0; j < listProdFravoritos.size(); j++) {//recorre lista favorito
                            if (id == listProdFravoritos.get(j).getId()) {
                                Log.e("Favorito", "id: " + id);
                                isFavorite = true;
                                break;//si encontro el favorito, se sale
                            }
                        }
                        listProdFramByLatLng.get(0).setFavorite(isFavorite);//FAVORITO ES ESTATICO, NO IMPORTA CUAL SE MODIFIQUE
                    }

                } else {
                    Toast.makeText(vista, "No se recibieron resultados", Toast.LENGTH_LONG).show();
                }

                MyDialoges.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_ListarProdByLatLog>> call, Throwable t) {
                Log.e("error...............", t.toString());
                MyDialoges.dismissProgressDialog();
                Toast.makeText(vista, "Ocurrió un error inesperado", Toast.LENGTH_LONG).show();
            }

        });
    }

    //2. DATOS SOBRE EL PRODUCTO FARMACIA,PRECIO
    public void BusquedaFarmaciasPorUbigeo(final int id, List<MH_DataModel_Ubigeo> ubigeoSelected) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Log.e("buscar famacias: ",
                "Region: " + (ubigeoSelected.size() > 0 ? ubigeoSelected.get(0).getName() : "Todas") +
                        ", Provincia: " + (ubigeoSelected.size() > 1 ? ubigeoSelected.get(1).getName() : "Todas") +
                        ", Distrito: " + (ubigeoSelected.size() > 2 ? ubigeoSelected.get(2).getName() : "Todas") +
                        ", order: " + vista.getOderSearch());

        ///LatLng latLng=vista.getLatLng();
        ///Double lat= latLng.latitude;
        //Double lng= latLng.longitude;
        //no funciona con coordenadas de venezuela, por eso se dejan estaticas para pruebas

        MyDialoges.showProgressDialog(vista, "Buscando farmacias... Por favor espere");


        final iListarFarmcaciasUbigeo service = retrofit.create(iListarFarmcaciasUbigeo.class);

        int ubigeo = 0;
        if (ubigeoSelected.size() == 1)
            ubigeo = ubigeoSelected.get(0).getId();
        else if (ubigeoSelected.size() == 2)
            ubigeo = ubigeoSelected.get(1).getId();
        else if (ubigeoSelected.size() == 3)
            ubigeo = ubigeoSelected.get(2).getId();

        service.getFarmacias3(id, ubigeo, vista.getOderSearch()).enqueue(new Callback<List<MH_DataModel_ListarProdByLatLog>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_ListarProdByLatLog>> call, Response<List<MH_DataModel_ListarProdByLatLog>> response) {
                Log.e("JSON Farmacias", call.request().url().toString());
                if (response.isSuccessful()) {

                    listProdFramByLatLng.clear();
                    refreshingData();

                    readyPrice = new Boolean[response.body().size()];
                    readyDetailPharmacy = new Boolean[response.body().size()];
                    Log.e("Farmacias en la zona: ", String.valueOf(response.body().size()));
                    for (int i = 0; i < response.body().size(); i++) {// de tdoas las farmacias
                        readyPrice[i] = false;
                        readyDetailPharmacy[i] = false;
                        listProdFramByLatLng.add(i, response.body().get(i));
                        Integer idFarmacia = listProdFramByLatLng.get(i).getId();
                        //Double distanceFarm=new Calculate().Redondear(listProdFramByLatLng.get(i).getDistance());
                        //Log.e("Farmacias data","id: "+idFarmacia+", Distance: "+distanceFarm);
                        BuscarPrecioFarmaciaProducto(i, idFarmacia, id);//se busca el precio de un procucto

                        //BuscarPrecioFarmaciaProductoResp(idFarmacia, id);
                        MostrarDetalleFarmacias(i, idFarmacia);//busca ubicacion completa de la farmacia
                    }

                    Boolean isFavorite = false;
                    if (response.body().size() == 0) {
                        Toast.makeText(vista, "No se recibieron resultados", Toast.LENGTH_LONG).show();
                        //EN ESTE CASO BUSCAR MENOR PRECIO EN LIMA
                        // BUSCAR id EN


                    } else {//se verifica si el producto (id buscado) es favorito
                        for (int j = 0; j < listProdFravoritos.size(); j++) {//recorre lista favorito
                            if (id == listProdFravoritos.get(j).getId()) {
                                Log.e("Favorito", "id: " + id);
                                isFavorite = true;
                                break;//si encontro el favorito, se sale
                            }
                        }
                        listProdFramByLatLng.get(0).setFavorite(isFavorite);//FAVORITO ES ESTATICO, NO IMPORTA CUAL SE MODIFIQUE
                    }

                } else {
                    Toast.makeText(vista, "No se recibieron resultados", Toast.LENGTH_LONG).show();
                }

                MyDialoges.dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_ListarProdByLatLog>> call, Throwable t) {
                Log.e("error...............", t.toString());
                MyDialoges.dismissProgressDialog();
                Toast.makeText(vista, "Ocurrió un error inesperado", Toast.LENGTH_LONG).show();
            }

        });
    }

    //3. BUSCAR PRECIOS PRODUCTOS
    public void BuscarPrecioFarmaciaProducto(final int num, int idFarmacia, final int idProducto) {
        Log.e("BuscarPreFarmProd", "idFarmacia: " + idFarmacia + ", idProducto: " + idProducto);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iDetalleFarmaco service = retrofit.create(iDetalleFarmaco.class);

        service.getPreciosFarmaciasProductos(idFarmacia, idProducto).enqueue(new Callback<List<MH_DataModel_DetalleFarmaco>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_DetalleFarmaco>> call, Response<List<MH_DataModel_DetalleFarmaco>> response) {
                Log.e("JSON Precios", "num: " + num + ", " + call.request().url().toString());

                if (response.isSuccessful()) {
                    //POR ENVIAR ESA INFO SEPARADA VIENEN CON RETRASOS LAS CONSULTAS

                    //new Gson().fromJson()
                    listProdFramByLatLng.get(num).setDetalleFarmaco(response.body().get(0));
                    //OJO NOMBRE NO SE DESERIALIZA.... ROBLEMAS DE LIBRERIA PROBLEMA
                    //por ello se coloca directo
                    //Log.e("getNombreP", ":"+listProdFramByLatLng.get(0).getDetalleFarmaco().getNombreP());
                    listProdFramByLatLng.get(num).getDetalleFarmaco().setNombreP(vista.getTxtSearchSUgerencias().getText().toString());
                    int pro = Integer.valueOf(idProducto);
                    listProdFramByLatLng.get(num).getDetalleFarmaco().setIdP(pro);

                    /*if(num==listProdFramByLatLng.size()-1){//SI YA SE TIENEN TODOS LOS ELEMENTOS

                        Log.e("Finishing", "BuscarPrecioFarmaciaProducto");
                        Log.e("getDistance", ":"+listProdFramByLatLng.get(0).getDistance().toString());
                        Log.e("getIdP", ":"+listProdFramByLatLng.get(0).getDetalleFarmaco().getIdP());
                        Log.e("getNombreP", ":"+listProdFramByLatLng.get(0).getDetalleFarmaco().getNombreP());
                        Log.e("getVencimiento", ":"+listProdFramByLatLng.get(0).getDetalleFarmaco().getVencimiento());
                        //listProdFramByLatLng.get(0).getDetalleFarmaco().getNombreP();
                    }*/

                    readyPrice[num] = true; ///garantiza qye llego esta busqueda (DETALLE APIS DEBE ENVIAR TODO EN UNA SOLA CONSLUTA)
                    for (int i = 0; i < readyPrice.length; i++) {///verifica que llegaron todas
                        if (!readyPrice[i])//si no ha llegado
                            break;
                        if (i == readyPrice.length - 1) {
                            setDataDetalleProdReady(true);
                            UpdateFinished();
                            Log.e("Finishing", "BuscarPrecioFarmaciaProducto");
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_DetalleFarmaco>> call, Throwable t) {
                Log.e("error...............", t.toString());
                Toast.makeText(vista, "Ocurrió un error inesperado", Toast.LENGTH_LONG).show();

            }
        });
    }

    //4. BUSCAR FARMACIAS
    public void MostrarDetalleFarmacias(final int num, int idfarmacia) {
        String farmacia = null;
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iDetalleFarmacias service = retrofit.create(iDetalleFarmacias.class);

        service.getFarmacias(idfarmacia).enqueue(new Callback<List<MH_DataModel_DetalleFarmacia>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_DetalleFarmacia>> call, Response<List<MH_DataModel_DetalleFarmacia>> response) {
                Log.e("JSON Datalle Farmacias", "num: " + num + ", " + call.request().url().toString());
                if (response.isSuccessful()) {
                    listProdFramByLatLng.get(num).setDetalleFarmacias(response.body().get(0));

                    Log.e("getNombre", response.body().get(0).getNombre());
                    Log.e("Name Farmacy", listProdFramByLatLng.get(num).getDetalleFarmacias().getNombre());
                    //POR ENVIAR ESA INFO SEPARADA VIENEN CON RETRASOS LAS CONSULTAS
                    /*Log.e("Farmacia",lisfarmacia.get(num).getDetalleFarmacias().getNombre()+
                            ", Producto: "+lisfarmacia.get(num).getListaPrecios().getNombreP()+
                            ", Precio: "+lisfarmacia.get(num).getListaPrecios().getPrecio()
                    );*/
                    /*if(num==listProdFramByLatLng.size()-1){//SI YA SE TIENEN TODOS LOS ELEMENTOS
                        setDataDetalleFarmReady(true);
                        Log.e("Finishing", "MostrarDetalleFarmacias");

                    }
                    UpdateFinished();*/
                    readyDetailPharmacy[num] = true; ///garantiza qye llego esta busqueda (DETALLE APIS DEBE ENVIAR TODO EN UNA SOLA CONSLUTA)
                    for (int i = 0; i < readyDetailPharmacy.length; i++) {///verifica que llegaron todas
                        if (!readyDetailPharmacy[i])//si no ha llegado
                            break;
                        if (i == readyDetailPharmacy.length - 1) {
                            setDataDetalleFarmReady(true);
                            UpdateFinished();
                            Log.e("Finishing", "MostrarDetalleFarmacias");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_DetalleFarmacia>> call, Throwable t) {
                Log.e("error...............", t.toString());
                Toast.makeText(vista, "Ocurrió un error inesperado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ActualizarFavoritos(String idSession) {
        //ListarProductos.clear();
        //ListaProductosNombres.clear();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //MyDialoges.showProgressDialog(vista, "Actualizando productos favoritos... Por favor espere");
        final iFavoritosIdLista service = retrofit.create(iFavoritosIdLista.class);
        Log.e("ID favorito", "------------" + idSession + "--" + idSession);
        service.ListarFavorito(idSession).enqueue(new Callback<List<MH_DataModel_ListarIdfavoritos>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_ListarIdfavoritos>> call, Response<List<MH_DataModel_ListarIdfavoritos>> response) {
                Log.e("JSON ID Favoritos", call.request().url().toString());

                if (response.isSuccessful()) {
                    listProdFravoritos.clear();
                    ready = new Boolean[response.body().size()];//{false,false,false,false};
                    for (int i = 0; i < response.body().size(); i++) {
                        ready[i] = false;//llegan dessincronizadas
                        //listProdFravoritos.
                        //listProdFravoritos.get(i).setId(response.body().get(i).getIdProducto());
                        idProductos(i, response.body().get(i).getIdProducto());
                        Log.e("Ver: Productos  ", "---->" + response.body().get(i).getIdProducto().toString());

                    }
                    //MyDialoges.dismissProgressDialog();
                } else {
                    Log.e("No ListaFavoritos ", "------------" + call.request().url().toString());
                    //MyDialoges.dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_ListarIdfavoritos>> call, Throwable t) {
                Log.e("mmmm", "Nooonnn" + t.toString());
                //MyDialoges.dismissProgressDialog();
            }
        });
    }

    //5. DETALLES FAVORITOS POR ID

    private void idProductos(final int num, int idProducto) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iFavoritoByIdProducto service = retrofit.create(iFavoritoByIdProducto.class);

        service.verMedicamento(idProducto).enqueue(new Callback<List<MH_DataModel_DetalleFavoritos>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_DetalleFavoritos>> call, Response<List<MH_DataModel_DetalleFavoritos>> response) {
                Log.e("JSON ID Favoritos", call.request().url().toString());
                if (response.isSuccessful()) {
                    listProdFravoritos.add(response.body().get(0));

                    //ListaProductosNombres.add(response.body().get(0).getNombre()+" "+response.body().get(0).getConcentracion());
                    //Adapter2.notifyDataSetChanged();
                    Log.e("Mostrar un producto:", "buscando en favoritos: " + response.body().get(0).getNombre() + response.body().get(0).getConcentracion());


                    ready[num] = true; ///garantiza qye llego esta busqueda (DETALLE APIS DEBE ENVIAR TODO EN UNA SOLA CONSLUTA)

                    for (int i = 0; i < ready.length; i++) {///verifica que llegaron todas
                        if (!ready[i])//si no ha llegado
                            break;
                        if (i == ready.length - 1) {
                            vista.getAdapterFavoritos().notifyDataSetChanged();
                            Log.e("Mostrar un producto:", "refrezco lista");
                        }

                    }
                    /*if(ready[0]&&ready[1]&&ready[1]&&ready[]){//si encontro el ultimo
                        //MyDialoges.dismissProgressDialog();


                        vista.adapterFavoritos.notifyDataSetChanged();
                        Log.e("Mostrar un producto:","refrezco lista");
                    }*/
                } else {
                    //MyDialoges.dismissProgressDialog();
                    Log.e("No producto", "Nooo" + call.request().url().toString());
                }
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_DetalleFavoritos>> call, Throwable t) {
                Log.e("mmmm", "Nooonnn" + t.toString());
                //MyDialoges.dismissProgressDialog();

            }
        });
    }

    // 6. ELIMINAR FAVORITOS
    public void EliminarFavoritos(String id, int idProducto) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iEliminarProductoFavorito service = retrofit.create(iEliminarProductoFavorito.class);
        Log.e("ID favorito", ":" + idProducto);

        service.EliminarFavorito(id, idProducto).enqueue(new Callback<MH_DataModel_AddFavorito>() {
            @Override
            public void onResponse(Call<MH_DataModel_AddFavorito> call, Response<MH_DataModel_AddFavorito> response) {
                Log.e("JSON Delete Favoritos", call.request().url().toString());
                if (response.isSuccessful()) {
                    Log.e("Favorito Eliminar Exito", "-------------");
                    ActualizarFavoritos(vista.id_session());
                } else {
                    Log.e("No eliminar favorito", "------------");
                    //MyDialoges.dismissProgressDialog();
                }
                //  Log.e("No entro ","------------" + response.body().toString());
            }

            @Override
            public void onFailure(Call<MH_DataModel_AddFavorito> call, Throwable t) {
                Log.e("error...............", t.toString());
                //MyDialoges.dismissProgressDialog();
            }
        });
    }

    //7. AGREGAR FAVORTIOS
    public void AgregarFavoritos(String id, int idProducto) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iAgregarFavoritos service = retrofit.create(iAgregarFavoritos.class);
        Log.e("ID favorito", ":" + idProducto + ", sesion: " + id);

        service.AggFavorito(id, idProducto).enqueue(new Callback<MH_DataModel_AgregarFavoritos>() {
            @Override
            public void onResponse(Call<MH_DataModel_AgregarFavoritos> call, Response<MH_DataModel_AgregarFavoritos> response) {
                Log.e("JSON Agregar Favoritos", call.request().url().toString());
                if (response.isSuccessful()) {
                    Log.e("Favorito Agg Exito", "-------------");
                    ActualizarFavoritos(vista.id_session());
                } else {

                    Log.e("No entro favorito", "------------");
                }
            }

            @Override
            public void onFailure(Call<MH_DataModel_AgregarFavoritos> call, Throwable t) {
                Log.e("error...............", t.toString());
            }
        });
    }

    //8. CERRAR SESION
    public void Logout(String id) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iCerrarSesion service = retrofit.create(iCerrarSesion.class);

        Log.e("cerraNDO Sesion", id);
        service.logoutUsuario(id).enqueue(new Callback<MH_DataModel_CerrarSesion>() {
            @Override
            public void onResponse(Call<MH_DataModel_CerrarSesion> call, Response<MH_DataModel_CerrarSesion> response) {
                Log.e("JSON cierra Sesion", call.request().url().toString());
                if (response.isSuccessful()) {
                    Log.e("Logout_Ok", ":" + response.body().getMessage());
                } else {
                    Toast.makeText(vista, "Error! NO se cerro sesión en servidor", Toast.LENGTH_LONG).show();
                }
                Log.e("Logout_SEGURO", ":" + "LLLLLLLLLLLLLL");
                Preferences.logOutpPreferences(vista);
                Intent salir = new Intent(vista.getApplicationContext(), MH_Activity_Presentacion.class);
                vista.startActivity(salir);
                vista.finish();

            }

            @Override
            public void onFailure(Call<MH_DataModel_CerrarSesion> call, Throwable t) {
                Log.e("error...............", t.toString());
            }
        });

    }

    //11. CONSULTAR NUEVAS NOTIFICACIONES
    public void VerificarNotificaciones(String session_id) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Log.e("VERIFICAR NOTIFICACION1", session_id);
        final iNotificationNew service = retrofit.create(iNotificationNew.class);

        Log.e("VERIFICAR NOTIFICACION2", session_id);
        service.logoutUsuario(session_id).enqueue(new Callback<List<MH_DataModel_Notificaciones>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_Notificaciones>> call, Response<List<MH_DataModel_Notificaciones>> response) {
                Log.e("JSON NUEVA NOTIFICACION", call.request().url().toString());
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.e("Notificacion_Ok", "n. " + i + " - " + response.body().get(i).getMessage());
                        //Log.e("ESTADO N.",response.body().get(i).getEstado());
                        listaNotificaciones.add(response.body().get(i));

                        if (i == response.body().size() - 1) {//si encontro el ultimo
                            //listaNotificaciones.


                            Collections.sort(listaNotificaciones, new Comparator<MH_DataModel_Notificaciones>() {
                                public int compare(MH_DataModel_Notificaciones obj1, MH_DataModel_Notificaciones obj2) {
                                    // ## Ascending order
                                    return obj2.getFecha().compareToIgnoreCase(obj1.getFecha()); // To compare string values
                                    // return Integer.valueOf(obj1.empId).compareTo(obj2.empId); // To compare integer values

                                    // ## Descending order
                                    // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                                    // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
                                }
                            });

                            vista.infoNewNotifications(listaNotificaciones.get(i));
                            vista.adapterNotificaciones.notifyDataSetChanged();
                        }
                    }

                } else {
                    Log.e("Notificacion_Error", ":");
                    //Toast.makeText(vista,"Error solicitando notificaciones",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_Notificaciones>> call, Throwable t) {
                Log.e("error...............", t.toString());
            }
        });

    }

    //11. CONSULTAR TODAS NOTIFICACIONES
    public void ObtenerNotificacionesAll(String session_id) {
        Log.e("hhhhhh NOTIFICACION1", session_id);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Log.e("fffffff NOTIFICACION1", session_id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Log.e("VERIFICAR NOTIFICACION1", session_id);
        final iNotificacion service = retrofit.create(iNotificacion.class);

        Log.e("VERIFICAR NOTIFICACION2", session_id);
        service.logoutUsuario(session_id).enqueue(new Callback<List<MH_DataModel_Notificaciones>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_Notificaciones>> call, Response<List<MH_DataModel_Notificaciones>> response) {
                Log.e("JSON ALL NOTIFICATIONS", call.request().url().toString());
                if (response.isSuccessful()) {
                    listaNotificaciones.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.e("Notificacion_Ok", "n. " + i + " - " + response.body().get(i).getMessage());
                        //Log.e("ESTADO N.",response.body().get(i).getEstado());
                        listaNotificaciones.add(response.body().get(i));
                        if (i == response.body().size() - 1) {//si encontro el ultimo
                            //listaNotificaciones.
                            Collections.sort(listaNotificaciones, new Comparator<MH_DataModel_Notificaciones>() {
                                public int compare(MH_DataModel_Notificaciones obj1, MH_DataModel_Notificaciones obj2) {
                                    return obj2.getFecha().compareToIgnoreCase(obj1.getFecha()); // To compare string values
                                }
                            });
                            vista.getAdapterNotificaciones().notifyDataSetChanged();
                        }
                    }

                } else {
                    Log.e("Notificacion_Error all", ":");
                    //Toast.makeText(vista,"Error solicitando all notificaciones",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_Notificaciones>> call, Throwable t) {
                Log.e("error...............", t.toString());
            }
        });

    }

    public void checkdataUser() {
        if (!Preferences.idDataReady(vista))
            InformacionUsuario(vista.id_session());
        else
            vista.showDataUser();
    }

    //. DATA USER
    public void InformacionUsuario(String idusuario) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iInformacionUser service = retrofit.create(iInformacionUser.class);

        Log.e("ID", ":" + idusuario);
        service.getInfoUsuario(idusuario).enqueue(new Callback<MH_DataModel_InfoUsuario>() {

            public void onResponse(Call<MH_DataModel_InfoUsuario> call, Response<MH_DataModel_InfoUsuario> response) {
                Log.e("JSON data user", call.request().url().toString());
                if (response.isSuccessful()) {
                    Preferences.setDataUserPreferences(vista, response.body());
                    vista.showDataUser();
                    Log.e("InformacionUsuario", ":" + call.request().url().toString());
                    Log.e("ID", ":");
                    Log.e("InformacionUsuario", "EntroGano");
                    Log.e("Nombre", ":" + response.body().getApellidos());
                    Log.e("Nombre", ":" + response.body().getAvatar());
                    Log.e("ToString:", " " + response.body().toString());
                    Log.e("Telf", ":" + response.body().getTelefono());
                    Log.e("Email", ":" + response.body().getEmail());
                } else {
                    Log.e("InformacionUsuarioX", ":" + call.request().url().toString());
                }
            }

            @Override
            public void onFailure(Call<MH_DataModel_InfoUsuario> call, Throwable t) {
                Log.e("error...............", t.toString());
                Log.e("error...............", ":" + call.request().url().toString());


            }
        });


    }

    //10 ACTUALIZAR USER
    public void UpdateDataUser(String session_id, String name, String lastName, String password, String telephone, String curr_password) {

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iUpdateUsuario service = retrofit.create(iUpdateUsuario.class);


        Log.e("DATA update user", "session_id: " + session_id + ", name: " + name + ", lastName: " + lastName + ", password: " + password + ", telephone: " + telephone + ", curr_password: " + curr_password);
        MyDialoges.showProgressDialog(vista, "Actualizando información del usuario");
        service.ActualizarDatosUsuario(session_id, name, lastName, password, telephone, curr_password).enqueue(new Callback<MH_DataModel_UpdateUsuario>() {
            public void onResponse(Call<MH_DataModel_UpdateUsuario> call, Response<MH_DataModel_UpdateUsuario> response) {
                Log.e("JSON update user", call.request().url().toString());
                if (response.isSuccessful()) {
                    Log.e("ActualizacionUsuarioYes", ": ..............................");
                    Toast.makeText(vista, "Usuario Actualizado... Debe volver a iniciar sesión", Toast.LENGTH_SHORT).show();
                    MyDialoges.dismissProgressDialog();


                    ///se cierra sesion
                    Logout(Preferences.getIdSesion(vista));
                } else {
                    Toast.makeText(vista, "Ocurrio un error actualizando", Toast.LENGTH_SHORT).show();
                    Log.e("Error Actualizando:", ": ..............................");
                    MyDialoges.dismissProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<MH_DataModel_UpdateUsuario> call, Throwable t) {
                Log.e("error...............", t.toString());
                MyDialoges.dismissProgressDialog();
            }
        });
    }

    public void RegisterUser(MH_DataModel_Register usuario) {
        //showpDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE_ACOUNT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iRegistroUsuario service = retrofit.create(iRegistroUsuario.class);
        MyDialoges.showProgressDialog(myContext, "Espere...");
        Log.e("JSON register user", new Gson().toJson(usuario));
        Call<MH_DataModel_Register> call = service.Insertar_datos_usuario(usuario.getName(), usuario.getEmail(), usuario.getApellido(), usuario.getPassword(), usuario.getNumero());
        call.enqueue(new Callback<MH_DataModel_Register>() {
            @Override
            public void onResponse(Call<MH_DataModel_Register> call, Response<MH_DataModel_Register> response) {
                Log.e("JSON register user", call.request().url().toString());
                Toast.makeText(myContext, "Usuario Registrado... Debe confirmar su correo para poder ingresar",
                        Toast.LENGTH_LONG).show();
                MyDialoges.dismissProgressDialog();
                Intent ListSong = new Intent(myContext.getApplicationContext(), MH_Activity_Login.class);
                myContext.startActivity(ListSong);
            }

            @Override
            public void onFailure(Call<MH_DataModel_Register> call, Throwable t) {
                Toast.makeText(myContext, "No se registro" + t,
                        Toast.LENGTH_LONG).show();
                MyDialoges.dismissProgressDialog();
            }
        });

    }

    /**
     * SE EJECUTA AL TERMINAR DE RECIBIR LOS DATOS (SON TRE HILOS INDEPENDIENTE, NO SE SABE CUAL ACABA PRIMERO)
     */
    public void UpdateFinished() {
        //MOSTRAR LISTA DE ELEMENTOS ENCONTRADOS
        if (getDataDetalleFarmReady() && getDataDetalleProdReady()) {
            MyDialoges.dismissProgressDialog();
            vista.getmViewPager().setCurrentItem(MH_Principal.PAGE_ENCONTRADOS);
            vista.getMyMaps().getmMap().clear();
            vista.getMyMaps().makeUserPosition(vista.getLatLng());
            vista.getMyMaps().moveCamera(vista.getLatLng());
            vista.getMyMaps().makePharmaciesPosition(listProdFramByLatLng);
            vista.getAdapterProductos().notifyDataSetChanged();
            //vista.

            //Actualizar favoritos, para marcar
            //ActualizarFavoritos(vista.id_session());
        }
    }

    public List<MH_DataModelSugerencias> getListaSUgerencias() {
        return listaSUgerencias;
    }

    public void setListaSUgerencias(List<MH_DataModelSugerencias> listaSUgerencias) {
        this.listaSUgerencias = listaSUgerencias;
    }

    public List<MH_DataModel_ListarProdByLatLog> getListProdFramByLatLng() {
        return listProdFramByLatLng;
    }

    public void setListProdFramByLatLng(List<MH_DataModel_ListarProdByLatLog> listProdFramByLatLng) {
        this.listProdFramByLatLng = listProdFramByLatLng;
    }

    public List<MH_DataModel_Ubigeo> getListUbigeo() {
        return listUbigeo;
    }

    public void setListUbigeo(List<MH_DataModel_Ubigeo> listUbigeo) {
        this.listUbigeo = listUbigeo;
    }

    public List<MH_DataModel_Notificaciones> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public void refreshingData() {
        dataDetalleFarmReady = false;
        dataDetalleProdReady = false;
    }

    public Boolean getDataDetalleFarmReady() {
        return dataDetalleFarmReady;
    }

    public void setDataDetalleFarmReady(Boolean dataDetalleFarmReady) {
        this.dataDetalleFarmReady = dataDetalleFarmReady;
    }

    public Boolean getDataDetalleProdReady() {
        return dataDetalleProdReady;
    }

    public void setDataDetalleProdReady(Boolean dataDetalleProdReady) {
        this.dataDetalleProdReady = dataDetalleProdReady;
    }

    public List<MH_DataModel_DetalleFavoritos> getListProdFravoritos() {
        return listProdFravoritos;
    }

    public HttpZonngo setListProdFravoritos(List<MH_DataModel_DetalleFavoritos> listProdFravoritos) {
        this.listProdFravoritos = listProdFravoritos;
        return this;
    }


    //
    public void busquedaUbigeo(int idUbigeo, Context myContext) {
        Log.e("Search ubigeo from", String.valueOf(idUbigeo));


        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final iUbigeo service = retrofit.create(iUbigeo.class);


        service.getUbigeo(idUbigeo).enqueue(new Callback<List<MH_DataModel_Ubigeo>>() {
            @Override
            public void onResponse(Call<List<MH_DataModel_Ubigeo>> call, Response<List<MH_DataModel_Ubigeo>> response) {
                listUbigeo.clear();
                if (response.isSuccessful()) {
                    Log.e("JSON Ubigeo", call.request().url().toString());

                    for (int i = 0; i < response.body().size(); i++) {
                        Log.e("City", "num: " + i + " -> " + response.body().get(i).getName());
                        listUbigeo.add(response.body().get(i));
                    }

                    Log.e("Numero de Items", String.valueOf(listUbigeo.size()));

                }
                if (vista.getBsAdapter().getSwipeRefreshUbigeo() != null) {
                    vista.getBsAdapter().getSwipeRefreshUbigeo().setRefreshing(false);
                    vista.getBsAdapter().getSwipeRefreshUbigeo().setEnabled(false);
                }
                vista.getBsAdapter().getAdapterUbigeo().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MH_DataModel_Ubigeo>> call, Throwable t) {
                Log.e("error...............", t.toString());
                vista.getBsAdapter().getSwipeRefreshUbigeo().setRefreshing(false);
            }
        });
    }
}
