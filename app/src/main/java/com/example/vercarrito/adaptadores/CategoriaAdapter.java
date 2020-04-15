package com.example.vercarrito.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vercarrito.R;

import java.util.ArrayList;

import com.example.vercarrito.view_holders.CategoriaViewHolder ;
import com.example.vercarrito.modelos.CategoriaAlimento ;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaViewHolder> {

    private Context context;
    private ArrayList<CategoriaAlimento> items;

    public CategoriaAdapter(ArrayList<CategoriaAlimento> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.categoria_individual, parent, false);
        return new CategoriaViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriaViewHolder holder, final int position) {

        holder.updateUI(items.get(position), context);
        holder.setRecyclerviewVisibility(items.get(position).isDeployed());
        holder.ivDesplegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriaAlimento categoriaAlimento = items.get(holder.getAdapterPosition());
                categoriaAlimento.setDeployment(!categoriaAlimento.isDeployed());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
