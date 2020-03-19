package com.example.vercarrito;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder>{

    public ArrayList<ItemCarrito> informacion;
    public Context context;


    //Esta clase servirá para referenciar las vistas por cada item de la lista
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Creamos las variables de referencia para los elementos del CardView
        public CardView cardView;
        public TextView titulo, precio;
        public ImageView imageView, ivMenos, ivMas;

        public MyViewHolder(View v){
            super(v);
            //Referenciamos las referencias con los elementos del CardView
            cardView = (CardView) v.findViewById(R.id.cardView);
            titulo = (TextView) v.findViewById(R.id.tvTitulo);
            precio = (TextView) v.findViewById(R.id.tvPrecio);
            imageView = (ImageView) v.findViewById(R.id.ivImagen);
            ivMenos = (ImageView) v.findViewById(R.id.ivMenos);
            ivMas = (ImageView) v.findViewById(R.id.ivMas);

            ivMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ivMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }


    }



    //Constructor del adaptador
    public Adaptador(Context context, ArrayList<ItemCarrito> informacion) {
        this.context = context;
        this.informacion = informacion;
    }

    //Este método nos sirve para crear la vista de cada item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prueba_item_carrito, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    //Este método se utiliza para la informacion de cada item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //Para dar formato al Precio
        DecimalFormat df2 = new DecimalFormat("#.00");

        myViewHolder.titulo.setText(informacion.get(i).titulo);
        myViewHolder.precio.setText("$" + df2.format(informacion.get(i).precio));
        myViewHolder.imageView.setImageResource(informacion.get(i).imagen);
    }

    //Regresa el tamaño de la lista de información
    @Override
    public int getItemCount() {
        return informacion.size();
    }



}
