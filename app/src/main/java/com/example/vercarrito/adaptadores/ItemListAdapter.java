package com.example.vercarrito.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vercarrito.R;

import java.util.ArrayList;

import com.example.vercarrito.view_holders.ItemListViewHolder;
import com.example.vercarrito.modelos.ElementosLista ;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListViewHolder > {

    private Context context;
    private ArrayList<ElementosLista> items;

    public ItemListAdapter(ArrayList<ElementosLista> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.elemento_lista, parent, false);

        return new ItemListViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListViewHolder holder, final int position) {
        holder.tvContenido.setText(items.get(position).toString());
        if(items.get(position).isReady()){
            holder.itemReady();
        }
        holder.chBxEstado.setChecked(items.get(position).isReady());

        if(items.get(position).isReady()){
            holder.itemReady();
        }else{
            holder.itemUnready();
        }

        holder.chBxEstado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                items.get(position).setStatus(!items.get(position).isReady());
                holder.chBxEstado.setChecked(items.get(position).isReady());
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
