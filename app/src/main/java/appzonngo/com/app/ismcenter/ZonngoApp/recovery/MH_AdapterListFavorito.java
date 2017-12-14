package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_DetalleFavoritos;
import appzonngo.com.app.ismcenter.zonngo2.R;

/**
 * Created by Joseris on 29/12/2016.
 */
public class MH_AdapterListFavorito extends RecyclerView.Adapter<MH_AdapterListFavorito.ViewHolder> {


    static MH_AdapterListFavorito.ItemsClickListener ItemsClickListener;
    private static boolean clickListenerON = false;
    private static boolean favoriteClickListenerON = false;                                  // Indica que el evento fue activado desde fuera
    private List<MH_DataModel_DetalleFavoritos> listProdFavoritos;
    private MH_AdapterListFavorito.ItemsDeleteFavoriteClickListener itemsDeleteFavoriteClickListener;        // Evento click del adaptador


    public MH_AdapterListFavorito(List<MH_DataModel_DetalleFavoritos> listProdFavoritos) {
        this.listProdFavoritos = listProdFavoritos;
    }

    @Override
    public MH_AdapterListFavorito.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view = inflate.inflate(R.layout.mh_adapter_prod_favoritos, parent, false);
        return new MH_AdapterListFavorito.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String nombreP = listProdFavoritos.get(position).getNombre() + " - " + listProdFavoritos.get(position).getConcentracion();
        if (nombreP.isEmpty())
            holder.txtProducto.setText("No disponible");
        else
            holder.txtProducto.setText(nombreP);

        String precio = listProdFavoritos.get(position).getTipo();
        if (precio.isEmpty())
            holder.txtCosto.setText("No disponible");
        else
            holder.txtCosto.setText(precio);

        String vendedor = listProdFavoritos.get(position).getTipoPresentacion().getDescripcion();
        if (vendedor.isEmpty())
            holder.txtVendedor.setText("No disponible");
        else
            holder.txtVendedor.setText(vendedor);

        holder.FavoriteButton.setFavorite(true);

    }

    public void setRVOnItemClickListener(MH_AdapterListFavorito.ItemsClickListener ItemsClickListener) {
        this.ItemsClickListener = ItemsClickListener;
        clickListenerON = true;
    }

    public void setRVOnDeleteFavoriteClickListener(MH_AdapterListFavorito.ItemsDeleteFavoriteClickListener itemsDeleteFavoriteClickListener) {
        this.itemsDeleteFavoriteClickListener = itemsDeleteFavoriteClickListener;
        favoriteClickListenerON = true;
    }

    @Override
    public int getItemCount() {
        return listProdFavoritos.size();
    }

    public interface ItemsClickListener {
        void onClickItem(View v, int position);
    }

    public interface ItemsDeleteFavoriteClickListener {
        void onDeleteFavoriteClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtCosto, txtProducto, txtVendedor;
        MaterialFavoriteButton FavoriteButton;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            txtProducto = (TextView) itemView.findViewById(R.id.txtProducto);
            txtVendedor = (TextView) itemView.findViewById(R.id.txtVendedor);
            FavoriteButton = (MaterialFavoriteButton) v.findViewById(R.id.FavoriteButton);
            FavoriteButton.setOnClickListener(this);
            txtCosto = (TextView) v.findViewById(R.id.txtCosto);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.FavoriteButton) {
                if (favoriteClickListenerON) {
                    itemsDeleteFavoriteClickListener.onDeleteFavoriteClick(view, getAdapterPosition());
                    listProdFavoritos.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            } else {
                if (clickListenerON) {
                    ItemsClickListener.onClickItem(view, getAdapterPosition());
                }
            }
        }
    }


}

