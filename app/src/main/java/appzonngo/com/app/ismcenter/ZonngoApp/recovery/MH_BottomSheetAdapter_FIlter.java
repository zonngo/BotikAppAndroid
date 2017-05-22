package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.Keyboard;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Ubigeo;
import appzonngo.com.app.ismcenter.zonngo2.R;

public class MH_BottomSheetAdapter_FIlter {
    private MH_Principal mContext;
    private BottomSheetBehavior bsb;
    private View backgroundSheets;
    private Status mStatus;
    private ImageButton ImgBtnClose;
    private LinearLayout btnClear;
    private ToggleButton toggleMayor;
    private ToggleButton toggleMenor;
    private ToggleButton toggleGenerico;
    private ToggleButton toggleMarca;

    private TextView tvAplicar;
    private DiscreteSeekBar seekBar;
    private Button btnElegir;
    private Button btnElegir2;

    private MH_AdapterUbigeo adapterUbigeo;
    private RecyclerView recyclerUbigeo;
    private TextView tvUbicacion;

    public MH_BottomSheetAdapter_FIlter(final MH_Principal mContext) {
        this.mContext = mContext;
        backgroundSheets = mContext.findViewById(R.id.BackgroundSheets);//para opacar el fondo al salir ventanas emergentes
        CoordinatorLayout myBottomSheet = (CoordinatorLayout) this.mContext.findViewById(R.id.MyBottomSheets);

        toggleMayor = (ToggleButton) myBottomSheet.findViewById(R.id.toggleMayor);
        toggleMenor = (ToggleButton) myBottomSheet.findViewById(R.id.toggleMenor);
        toggleGenerico = (ToggleButton) myBottomSheet.findViewById(R.id.toggleGenerico);
        toggleMarca = (ToggleButton) myBottomSheet.findViewById(R.id.toggleMarca);
        tvUbicacion = (TextView) myBottomSheet.findViewById(R.id.tvUbicacion);

        seekBar = (DiscreteSeekBar) myBottomSheet.findViewById(R.id.seekBar);

        btnElegir = (Button) myBottomSheet.findViewById(R.id.btnElegir);
        btnElegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click","showListUbigeo()");
                showListUbigeo();
            }
        });
        toggleMayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleMayor.isChecked()){
                    toggleMenor.setChecked(false);
                }
            }
        });

        toggleMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleMenor.isChecked()){
                    toggleMayor.setChecked(false);
                }
            }
        });


        btnClear = (LinearLayout) myBottomSheet.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("btnClear_onClick","onClick");
                toggleMayor.setChecked(false);
                toggleMenor.setChecked(false);
                toggleGenerico.setChecked(false);
                toggleMarca.setChecked(false);
                seekBar.setProgress(1);

                mContext.getUbigeoSelected().clear();
                tvUbicacion.setText("UBICACIÓN:");
            }
        });

        tvAplicar = (TextView) myBottomSheet.findViewById(R.id.tvAplicar);
        tvAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("APLICAR","APLICAR");
                if(!toggleGenerico.isChecked() && !toggleMarca.isChecked()) {
                    mContext.setTipoSearch(MH_Principal.TIPO_DEFAULT);
                    Log.e("APLICAR","TIPO_DEFAULT");
                } else if(!toggleGenerico.isChecked() && toggleMarca.isChecked()) {
                    mContext.setTipoSearch(MH_Principal.TIPO_MARCA);
                    Log.e("APLICAR","TIPO_MARCA");
                } else if(toggleGenerico.isChecked() && !toggleMarca.isChecked()) {
                    mContext.setTipoSearch(MH_Principal.TIPO_GENERICO);
                    Log.e("APLICAR","TIPO_GENERICO");
                } else if(toggleGenerico.isChecked() && toggleMarca.isChecked()) {
                    mContext.setTipoSearch(MH_Principal.TIPO_DEFAULT);
                    Log.e("APLICAR","TIPO_DEFAULT");
                }

                if(toggleMayor.isChecked()) {
                    mContext.setOderSearch(MH_Principal.ORDER_BY_PRICE_DESC);
                    Log.e("APLICAR","ORDER_BY_PRICE_DESC");
                } else if(toggleMenor.isChecked()) {
                    mContext.setOderSearch(MH_Principal.ORDER_BY_PRICE_ASC);
                    Log.e("APLICAR","ORDER_BY_PRICE_ASC");
                } else{
                    mContext.setOderSearch(MH_Principal.ORDER_BY_DISTANCE_ASC);
                    Log.e("APLICAR","ORDER_BY_DISTANCE_ASC");
                }

                setHidden();
            }
        });

        ImgBtnClose = (ImageButton) myBottomSheet.findViewById(R.id.ImgBtnClose);
        ImgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHidden();
            }
        });

        //seekBar.set
        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                seekBar.setProgress(value);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
        bsb = BottomSheetBehavior.from(myBottomSheet);
        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
        mStatus = Status.STATE_HIDDEN;

        backgroundSheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setHidden();
            }
        });

        backgroundSheets.setClickable(false);//para trabajar con la

        bsb.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                String nuevoEstado = "";
                switch(newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        if(bsb.getPeekHeight()==0){
                            MH_BottomSheetControl.opaqueView(backgroundSheets,false);
                            mStatus = Status.STATE_HIDDEN;
                            nuevoEstado = "STATE_HIDDEN2";
                            bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }else{
                            if(!mStatus.equals(Status.STATE_COLLAPSED)) {
                                //*changeMode(Mode.VIEW);
                                MH_BottomSheetControl.opaqueView(backgroundSheets, true);
                                mStatus = Status.STATE_COLLAPSED;
                                nuevoEstado = "STATE_COLLAPSED";
                            }
                        }
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        if(!mStatus.equals(Status.STATE_EXPANDED)) {
                            mStatus = Status.STATE_EXPANDED;
                            nuevoEstado = "STATE_EXPANDED";
                            bsb.setPeekHeight(0);
                        }
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        mStatus = Status.STATE_HIDDEN;
                        MH_BottomSheetControl.opaqueView(backgroundSheets,false);
                        nuevoEstado = "STATE_HIDDEN";
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        nuevoEstado = "STATE_DRAGGING";
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        nuevoEstado = "STATE_SETTLING";
                        break;
                }
                Log.i("BottomSheets", "Nuevo estado: " + nuevoEstado);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //Log.i("BottomSheets", "Offset: " + slideOffset);
            }
        });
        //UBICACIONES PARA FILTOR
        adapterUbigeo=new MH_AdapterUbigeo(mContext.getHttp().getListUbigeo(), mContext);
        mContext.getHttp().busquedaUbigeo(0,mContext);//busca todos las regiones
    }

    public void setCollapsed() {

        if(mContext.getTipoSearch()==MH_Principal.TIPO_DEFAULT) {
            //toggleGenerico.setChecked(true);
            //toggleMarca.setChecked(true);
        } else if(mContext.getTipoSearch()==MH_Principal.TIPO_GENERICO) {
            toggleGenerico.setChecked(true);
            toggleMarca.setChecked(false);
        } else if(mContext.getTipoSearch()==MH_Principal.TIPO_MARCA) {
            toggleGenerico.setChecked(false);
            toggleMarca.setChecked(true);
        }

        if(mContext.getOderSearch()==MH_Principal.ORDER_BY_PRICE_ASC) {
            toggleMayor.setChecked(false);
            toggleMenor.setChecked(true);
        } else if(mContext.getOderSearch()==MH_Principal.ORDER_BY_PRICE_DESC) {
            toggleMayor.setChecked(true);
            toggleMenor.setChecked(false);
        }

        Keyboard.hideKeyboard(mContext);
        MH_BottomSheetControl.opaqueView(backgroundSheets, true);//para evitar retrasos en la animacion
        mStatus= Status.STATE_HIDDEN;
        bsb.setPeekHeight(1000);
        bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void setHidden() {
        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public enum Mode {
        VIEW,
        EDIT,
    }

    public boolean isOpened() {
        return ((mStatus== Status.STATE_COLLAPSED)||(mStatus== Status.STATE_EXPANDED));
    }

    private boolean isCollapsed() {
        return (mStatus== Status.STATE_COLLAPSED);
    }

    private boolean isExpanded() {
        return (mStatus== Status.STATE_EXPANDED);
    }

    private enum Status {
        STATE_COLLAPSED,
        STATE_EXPANDED,
        STATE_HIDDEN
    }

    private int numUbigeo=0;
    private TextView tvTitulo;
    private SwipeRefreshLayout SwipeRefreshUbigeo;
    List<MH_DataModel_Ubigeo> ubigeoSelectedLocal;
    private void showListUbigeo(){
        ubigeoSelectedLocal=new ArrayList<>();
        ubigeoSelectedLocal=mContext.getUbigeoSelected();
        View alertLayout = mContext.getLayoutInflater().inflate(R.layout.mh_recycler_ubigeo_modal, null);
        //LinearLayout btnTranpTop = (LinearLayout) alertLayout.findViewById(R.id.btnTranpTop);
        SwipeRefreshUbigeo = (SwipeRefreshLayout) alertLayout.findViewById(R.id.SwipeRefreshUbigeo);
        SwipeRefreshUbigeo.setEnabled(false);
        recyclerUbigeo = (RecyclerView) SwipeRefreshUbigeo.findViewById(R.id.RecyclerUbigeo);
        tvTitulo=(TextView) alertLayout.findViewById(R.id.tvTitulo);

        TextView tvAplicar=(TextView) alertLayout.findViewById(R.id.tvAplicar);
        LinearLayout btnClearUbigeo = (LinearLayout) alertLayout.findViewById(R.id.btnClearUbigeo);



        btnClearUbigeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.getUbigeoSelected().clear();
                ubigeoSelectedLocal.clear();
                tvTitulo.setText(updateUbigeo());
                tvUbicacion.setText("UBICACIÓN:");
            }
        });
        recyclerUbigeo.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerUbigeo.setHasFixedSize(true);

        ImageButton cancel = (ImageButton) alertLayout.findViewById(R.id.btnImgClose);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        final android.support.v7.app.AlertDialog alert = builder.create();

        alert.setView(alertLayout);

        tvAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
                mContext.setUbigeoSelected(ubigeoSelectedLocal);
                //mContext.getUbigeoSelected().clear();
                //mContext.getUbigeoSelected().addAll(ubigeoSelectedLocal);
                tvUbicacion.setText("UBICACIÓN: ".concat(tvTitulo.getText().toString().isEmpty() ? "" : tvTitulo.getText().toString()));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });

        adapterUbigeo.setRVOnItemClickListener(new MH_AdapterUbigeo.ItemsClickListener() {
            @Override
            public void onClickItem(View v, int position) {

                Log.e("onClickItem: Lsita","Pos: "+position);
                //alert.cancel();
                Log.e("onClickItem: ","mContext.getUbigeoSelected().size(): "+ubigeoSelectedLocal.size());
                if(position!=-1 && ubigeoSelectedLocal.size()<4){
                    Log.e("onClickItem: A","A");
                    if(ubigeoSelectedLocal.size()<3)
                        ubigeoSelectedLocal.add(mContext.getHttp().getListUbigeo().get(position));
                    if(ubigeoSelectedLocal.size()>=3) {
                        ubigeoSelectedLocal.remove(ubigeoSelectedLocal.size()-1);
                        ubigeoSelectedLocal.add(mContext.getHttp().getListUbigeo().get(position));
                    }
                    tvTitulo.setText(updateUbigeo());
                }
                adapterUbigeo.notifyDataSetChanged();
            }
        });

        recyclerUbigeo.setAdapter(adapterUbigeo);

        alert.show();
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        tvTitulo.setText(updateUbigeo());
    }

    public void refreshing(){
        mContext.getHttp().getListUbigeo().clear();
        SwipeRefreshUbigeo.setEnabled(true);
        SwipeRefreshUbigeo.setRefreshing(true);
        adapterUbigeo.setRowSelected(-1);
    }
    private String updateUbigeo(){
        if(ubigeoSelectedLocal.size()<=0) {
            refreshing();
            mContext.getHttp().busquedaUbigeo(0,mContext);
            return "REGIONES > ";
        } else if(ubigeoSelectedLocal.size()==1) {
            refreshing();
            mContext.getHttp().busquedaUbigeo(ubigeoSelectedLocal.get(0).getId(),mContext);

            return ubigeoSelectedLocal.get(0).getName()
                    .concat(" > PROVINCIAS");
        } else if(ubigeoSelectedLocal.size()==2) {
            refreshing();
            mContext.getHttp().busquedaUbigeo(ubigeoSelectedLocal.get(1).getId(),mContext);

            return ubigeoSelectedLocal.get(0).getName()
                    .concat(" > ")
                    .concat(ubigeoSelectedLocal.get(1).getName())
                    .concat(" > DISTRITOS");
        } else if(ubigeoSelectedLocal.size()==3) {
            return ubigeoSelectedLocal.get(0).getName()
                    .concat(" > ")
                    .concat(ubigeoSelectedLocal.get(1).getName())
                    .concat(" > ")
                    .concat(ubigeoSelectedLocal.get(2).getName());
        }
        return "";
    }



    public MH_AdapterUbigeo getAdapterUbigeo() {
        return adapterUbigeo;
    }

    public SwipeRefreshLayout getSwipeRefreshUbigeo() {
        return SwipeRefreshUbigeo;
    }

    public void setSwipeRefreshUbigeo(SwipeRefreshLayout swipeRefreshUbigeo) {
        SwipeRefreshUbigeo = swipeRefreshUbigeo;
    }
}
