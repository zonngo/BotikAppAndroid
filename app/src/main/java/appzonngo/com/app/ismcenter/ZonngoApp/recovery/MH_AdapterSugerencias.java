package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModelSugerencias;
import appzonngo.com.app.ismcenter.zonngo2.R;

/**
 * Created by marwuinh@gmail.com on 24/11/2016.
 */

public class MH_AdapterSugerencias extends RecyclerView.Adapter<MH_AdapterSugerencias.ViewHolder> {
    static MH_AdapterSugerencias.ItemsClickListener ItemsClickListener;
    //RECUPERANDO ARCHIVO
    private static boolean clickListenerON = false;
    MH_Principal context;
    String medicamento;
    private List<MH_DataModelSugerencias> listaSugerencias;

    public MH_AdapterSugerencias(List<MH_DataModelSugerencias> listaSugerencias, Context context) {
        this.listaSugerencias = listaSugerencias;
        this.context = (MH_Principal) context;
    }

    @Override
    public MH_AdapterSugerencias.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view = inflate.inflate(R.layout.mh_adapter_sugerencias, parent, false);
        return new ViewHolder(view);
    }

    public void setRVOnItemClickListener(MH_AdapterSugerencias.ItemsClickListener ItemsClickListener) {
        this.ItemsClickListener = ItemsClickListener;
        clickListenerON = true;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("Click(Suge)", String.valueOf(position));

        holder.texto1.setText(listaSugerencias.get(position).getNombre());
        holder.texto2.setText(listaSugerencias.get(position).getConcent());

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorClientGray));
        else
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
    }

    @Override
    public int getItemCount() {
        return listaSugerencias.size();
    }

    public interface ItemsClickListener {
        void onClickItem(View v, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView texto1, texto2;

        ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            texto1 = (TextView) v.findViewById(R.id.textItem);
            texto2 = (TextView) v.findViewById(R.id.textItem2);
        }

        @Override
        public void onClick(View view) {
            if (clickListenerON) {
                ItemsClickListener.onClickItem(view, getAdapterPosition());
            }
        }
    }

}




