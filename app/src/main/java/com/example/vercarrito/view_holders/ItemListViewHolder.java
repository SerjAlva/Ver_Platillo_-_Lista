package com.example.vercarrito.view_holders;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vercarrito.R;

public class ItemListViewHolder extends RecyclerView.ViewHolder {

    public TextView tvContenido;
    public CheckBox chBxEstado;

    public ItemListViewHolder(View itemView){
        super(itemView);
        tvContenido = (TextView) itemView.findViewById(R.id.tvElement);
        chBxEstado = (CheckBox) itemView.findViewById(R.id.chBxListo);
    }

    public void itemReady(){
        tvContenido.setPaintFlags(tvContenido.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void itemUnready(){
        tvContenido.setPaintFlags(0);
    }

}
