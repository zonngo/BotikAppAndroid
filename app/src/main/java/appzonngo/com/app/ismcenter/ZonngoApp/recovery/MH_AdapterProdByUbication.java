package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.Calculate;
import appzonngo.com.app.ismcenter.zonngo2.R;
import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarProdByLatLog;

/**
 * Created by marwuinh@gmail.com on 24/11/2016.
 */

public class MH_AdapterProdByUbication extends RecyclerView.Adapter<MH_AdapterProdByUbication.ViewHolder> {
    //RECUPERANDO ARCHIVO
   // private boolean favorite=false;
    private static boolean clickListenerON=false;
    static MH_AdapterProdByUbication.ItemsClickListener ItemsClickListener;

    private MH_AdapterProdByUbication.ItemsFavoriteClickListener itemsFavoriteClickListener;        // Evento click del adaptador
    private static boolean favoriteClickListenerON=false;                                  // Indica que el evento fue activado desde fuera

    private List<MH_DataModel_ListarProdByLatLog> listProdFramByLatLng;
    MH_Principal context;
    String medicamento;

    public MH_AdapterProdByUbication(List<MH_DataModel_ListarProdByLatLog> listProdFramByLatLng, Context context) {
        this.listProdFramByLatLng =listProdFramByLatLng;
        this.context = (MH_Principal) context;
    }

    @Override
    public MH_AdapterProdByUbication.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view= inflate.inflate(R.layout.mh_adapter_prod_encontrados, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtVendedor, txtKm, txtProducto, txtCosto;
        MaterialFavoriteButton favoriteButton;
        Boolean selected=false;

        ImageView image;
        //MaterialFavoriteButton favorite;
        ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            //v.setOnLongClickListener(this);
            //favorite = new MaterialFavoriteButton.Builder(context).create();
            txtVendedor =(TextView) v.findViewById(R.id.txtVendedor);
            favoriteButton=(MaterialFavoriteButton)v.findViewById(R.id.FavoriteButton);
            favoriteButton.setOnClickListener(this);

            txtKm =(TextView) v.findViewById(R.id.txtKm);
            txtProducto =(TextView) v.findViewById(R.id.txtProducto);
            txtCosto =(TextView) v.findViewById(R.id.txtCosto);

            //favoriteButton.setFocusable(false);
            //favoriteButton.setClickable(true);
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.FavoriteButton) {
                Log.e("gfdgfdddfd",String.valueOf(getAdapterPosition()));
                //favorite=!favorite;
                boolean myFavorite=listProdFramByLatLng.get(getAdapterPosition()).changeFavorite();

                //Log.e("Favorite change to","pos: "+getAdapterPosition()+", Valor: "+favorite);

                //listProdFramByLatLng.get(getAdapterPosition()).setFavorite(favorite);
                favoriteButton.setFavorite(myFavorite,true);
                if(favoriteClickListenerON){
                    itemsFavoriteClickListener.onFavoriteClick(view, getAdapterPosition(), myFavorite);
                }
                notifyDataSetChanged();
            }else{
                if (clickListenerON) {
                    ItemsClickListener.onClickItem(view, getAdapterPosition());
                }
            }

        }
/*
        @Override
        public boolean onLongClick(View view) {

            //if(longClickListenerON) {
                //listProdFramByLatLng.get(getAdapterPosition()).changeFavorite();
                boolean favorite=listProdFramByLatLng.get(getAdapterPosition()).changeFavorite();
                //favoriteButton.setFavorite(favorite,true);
                //ItemsLongClickListener.onLongClick(view, getAdapterPosition());
            //}
            return false;
        }*/
    }

    public void setRVOnItemClickListener(MH_AdapterProdByUbication.ItemsClickListener ItemsClickListener) {
        this.ItemsClickListener = ItemsClickListener;
        clickListenerON=true;
    }

    public interface ItemsClickListener {
        void onClickItem(View v, int position);
    }

    public void setRVOnItemsFavoriteClickListener(ItemsFavoriteClickListener itemsFavoriteClickListener) {
        this.itemsFavoriteClickListener = itemsFavoriteClickListener;
        favoriteClickListenerON=true;
    }

    public interface ItemsFavoriteClickListener {
        void onFavoriteClick(View v, int position, Boolean favorite);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        //modificaron la API y no retorna esta valor
        try {
            Double distance = new Calculate().Redondear(listProdFramByLatLng.get(position).getDistance());
            holder.txtKm.setText(distance.isNaN() ? "No disponible" : distance.toString().concat("km"));
        }
        catch (NullPointerException e){
            holder.txtKm.setText("No disponible");
        }


        Double precio =listProdFramByLatLng.get(position).getDetalleFarmaco().getPrecio();
        holder.txtCosto.setText(precio.isNaN() ? "No disponible" : "S/"+String.valueOf(precio)+"c/u");

        String nombreP=listProdFramByLatLng.get(position).getDetalleFarmaco().getNombreP();
        holder.txtProducto.setText(nombreP==null ? "No disponible" : nombreP);

        String vendedor=listProdFramByLatLng.get(position).getDetalleFarmacias().getNombre();
        holder.txtVendedor.setText(vendedor==null ? "No disponible" : vendedor);

        ///boolean favorite=listProdFramByLatLng.get(position).getFavorite();
        holder.favoriteButton.setFavorite(listProdFramByLatLng.get(position).getFavorite());
        //Log.e("Favorite","pos: "+position+", Valor: "+favorite);
    }

    @Override
    public int getItemCount() {
        return listProdFramByLatLng.size();
    }

   }




