package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Notificaciones;
import appzonngo.com.app.ismcenter.zonngo2.R;

/**
 * Created by Marwuin on 8/3/2017.
 */

public class MH_AdapterNotificaciones extends RecyclerView.Adapter<MH_AdapterNotificaciones.ViewHolder> {
    static MH_AdapterListFavorito.ItemsClickListener ItemsClickListener;
    static String VISTO = "V";
    static String NO_VISTO = "N";
    private static boolean clickListenerON = false;
    ImageLoader img;
    private List<MH_DataModel_Notificaciones> notificaciones;
    private Context mContext;

    public MH_AdapterNotificaciones(List<MH_DataModel_Notificaciones> not, Context c) {
        notificaciones = not;
        mContext = c;
    }

    private ImageLoaderConfiguration configurarImageLoader() {
        DisplayImageOptions opcionesDefault = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)//@drawable/logocompany
                .showImageOnFail(R.drawable.bandera)
                .showImageForEmptyUri(R.drawable.bandera)
                .showImageOnLoading(R.drawable.loading)
                .resetViewBeforeLoading(true)

                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(opcionesDefault)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .build();
        return config;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view = inflate.inflate(R.layout.mh_adapter_prod_notificaciones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.notificacion_contenido.setText(notificaciones.get(position).getMessage());
        holder.notificacion_fecha.setText(notificaciones.get(position).getFecha());
        img = ImageLoader.getInstance();
        img.init(configurarImageLoader());
        img.displayImage(notificaciones.get(position).getImage_link(), holder.notificacion_image);
        //Log.e("ESTADO N.",notificaciones.get(position).getEstado());
        //NO MANDA ESTATUS DEL MENSAJE REALIMENTADO
        try {
            //Log.e("ESTADO N2.",":" + notificaciones.get(position).getEstado());
            if (notificaciones.get(position).getEstado() == null || notificaciones.get(position).getEstado().equals(NO_VISTO))
                holder.row.setBackgroundColor(mContext.getResources().getColor(R.color.colorNewNotification));
            //holder.notificacion_fecha.setText("123456");

            //holder.itemView.setBackgroundColor(condition.isRight() ? 0xFFAED581 : 0xFFE57373);
        } catch (NullPointerException e) {
            //Log.e("ESTADO N.","NUEVA, NO MANDA STATUS (NOSE VA A CREAR DOS ADAPTADORES)");
        }


    }

    @Override
    public int getItemCount() {
        return (null != notificaciones ? notificaciones.size() : 0);
    }

    public void setRVOnItemClickListener(MH_AdapterListFavorito.ItemsClickListener ItemsClickListener) {
        this.ItemsClickListener = ItemsClickListener;
        clickListenerON = true;
    }

    public interface ItemsClickListener {
        void onClickItem(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView notificacion_image;
        TextView notificacion_fecha, notificacion_contenido;
        View row;

        public ViewHolder(View v) {
            super(v);
            row = v;
            v.setOnClickListener(this);
            notificacion_image = (ImageView) v.findViewById(R.id.notificacion_image);
            notificacion_fecha = (TextView) v.findViewById(R.id.notificacion_fecha);
            notificacion_contenido = (TextView) v.findViewById(R.id.notificacion_contenido);
        }

        @Override
        public void onClick(View view) {
            notificaciones.get(getAdapterPosition()).setEstado(VISTO);
            notifyDataSetChanged();
            if (clickListenerON) {
                ItemsClickListener.onClickItem(view, getAdapterPosition());
            }
        }
    }
}
