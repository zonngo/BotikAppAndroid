package appzonngo.com.app.ismcenter.ZonngoApp.recovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_Ubigeo;
import appzonngo.com.app.ismcenter.zonngo2.R;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class MH_AdapterUbigeo extends RecyclerView.Adapter<MH_AdapterUbigeo.ViewHolder> {
    private static boolean clickListenerON=false;
    static MH_AdapterUbigeo.ItemsClickListener ItemsClickListener;
    private Context mContext;
    List<MH_DataModel_Ubigeo> listUbigeo;

    int rowSelected=-1;
    public MH_AdapterUbigeo(List<MH_DataModel_Ubigeo> listUbigeo, Context mContext){
        this.mContext=mContext;
        this.listUbigeo=listUbigeo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view= inflate.inflate(R.layout.mh_adapter_ubigeo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvUbigeo.setText(listUbigeo.get(position).getName());
        Log.e("onBindViewHolder: A ",String.valueOf(position));
        holder.rbUbigeo.setClickable(false);
        if(rowSelected==position){
            holder.rbUbigeo.setChecked(true);
        }else{
            holder.rbUbigeo.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return (null != listUbigeo ? listUbigeo.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView tvUbigeo;
        RadioButton rbUbigeo;
        View row;
        public ViewHolder(View v) {
            super(v);
            row=v;
            v.setOnClickListener(this);
            tvUbigeo = (TextView)v.findViewById(R.id.tvUbigeo);
            rbUbigeo = (RadioButton)v.findViewById(R.id.rbUbigeo);
        }

        @Override
        public void onClick(View view) {
            //rbUbigeo.setSelected(true);
            rowSelected=getAdapterPosition();
            if (clickListenerON) {
                ItemsClickListener.onClickItem(view, rowSelected);
            }
        }
    }

    public void setRVOnItemClickListener(MH_AdapterUbigeo.ItemsClickListener ItemsClickListener) {
        this.ItemsClickListener = ItemsClickListener;
        clickListenerON=true;
    }

    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }

    public interface ItemsClickListener {
        void onClickItem(View v, int position);
    }
}
