package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarProdByLatLog;
import appzonngo.com.app.ismcenter.zonngo2.R;

/**
 * Created by Marwuin on 27/12/2016.
 */


public class MH_AdapterProdByLatLog extends RecyclerView.Adapter<MH_AdapterProdByLatLog.ViewHolder> {

    static MH_AdapterProdByLatLog.ItemsClickListener ItemsClickListener;
    private static boolean clickListenerON = false;
    MH_Principal context;
    MaterialFavoriteButton favoriteButton;
    private List<MH_DataModel_ListarProdByLatLog> listProdFramByLatLng;

    public MH_AdapterProdByLatLog(List<MH_DataModel_ListarProdByLatLog> listProdFramByLatLng, MH_Principal context) {
        this.listProdFramByLatLng = listProdFramByLatLng;
        this.context = context;
    }

    @Override
    public MH_AdapterProdByLatLog.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view = inflate.inflate(R.layout.mh_adapter_prod_encontrados, parent, false);
        return new ViewHolder(view);
    }

    public void setRVOnItemClickListener(MH_AdapterProdByLatLog.ItemsClickListener ItemsClickListener) {
        this.ItemsClickListener = ItemsClickListener;
        clickListenerON = true;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.e("Click(prod)", String.valueOf(position));
        String distance = listProdFramByLatLng.get(position).getDistance
                ().toString().concat("km");
        if (distance.isEmpty())
            holder.txtKm.setText("N/A");
        else
            holder.txtKm.setText(listProdFramByLatLng.get(position).getDistance().toString().concat("km"));

        String nombreP = listProdFramByLatLng.get(position).getDetalleFarmaco().getNombreP();
        if (nombreP.isEmpty())
            holder.txtProducto.setText("No disponible");
        else
            holder.txtProducto.setText(nombreP);

        Double precio = listProdFramByLatLng.get(position).getDetalleFarmaco().getPrecio();
        if (precio.isNaN())
            holder.txtCosto.setText("No disponible");
        else
            holder.txtCosto.setText("S/" + String.valueOf(precio) + "c/u");

        String vendedor = listProdFramByLatLng.get(position).getDetalleFarmacias().getNombre();
        if (vendedor.isEmpty())
            holder.txtVendedor.setText("No disponible");
        else
            holder.txtVendedor.setText(vendedor);

            /*
            holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Click(favoriteButton)", holder.);
                    holder.favoriteButton.setImageResource(R.drawable.corazonazul);

                    //Log.e("Favorito", ":"+context.id_session()+ListaPrecios.get(0).getIdP());
                    //    context.aggListaFavoritos(context.id_session(),ListaPrecios.get(0).getIdP());

                }
            });
            */


    }

    @Override
    public int getItemCount() {
        return listProdFramByLatLng.size();
    }


    public interface ItemsClickListener {
        void onClickItem(View v, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtVendedor, txtKm, txtProducto, txtCosto;
        MaterialFavoriteButton favoriteButton;
        MaterialFavoriteButton favorite;

        ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            favorite = new MaterialFavoriteButton.Builder(context).create();
            txtVendedor = (TextView) v.findViewById(R.id.txtVendedor);
            favoriteButton = (MaterialFavoriteButton) v.findViewById(R.id.FavoriteButton);
            txtKm = (TextView) v.findViewById(R.id.txtKm);
            txtProducto = (TextView) v.findViewById(R.id.txtProducto);
            txtCosto = (TextView) v.findViewById(R.id.txtCosto);

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Click(favoriteButton)", "");
                }
            });
        }


        @Override
        public void onClick(View view) {
            if (clickListenerON) {
                ItemsClickListener.onClickItem(view, getAdapterPosition());
            }
        }
    }


}
