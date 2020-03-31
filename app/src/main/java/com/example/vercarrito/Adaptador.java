package com.example.vercarrito;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder>{
    //Lista con los elementos que se van a desplegar
    public ArrayList<ItemCarrito> informacion;

    //contexto en el que se crearan las vistas
    public Context context;

    //Listener para detectar cuando se haga click en un determinado item del RecyclerView
    private OnItemClickListener myListenner;

    //Esta clase sirve para controlar los botones de cada card
    public interface OnItemClickListener{

        //Método que se manda a llamar cuando se toca en "cualquier parte" (menos el boton de añadir/quitar porción)
        void onItemClick(int position);

        //Método que se manda a llamar cuando se toca en añadir porción
        void onAddPortionClick(int position);

        //Método que se manda a llamar cuando se toca en quitar porción
        void onRetirePortionClick(int position);

        //Método que se llama cuando se toca el botón eliminar platillo
        void onRemoveClick(int position);

    }
    //Este método se llama para configurar nuestro onItemClickListner
    public void setOnItemClickListener(OnItemClickListener listener){
        myListenner = listener;
    }

    //Esta clase servirá para referenciar las vistas por cada item de la lista
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Creamos las variables de referencia para los elementos del CardView
        public CardView cardView;
        public TextView titulo, precio, tvCantidadPorciones;
        public ImageView imageView, ivMenos, ivMas;
        public ImageButton ibRemove;


        public MyViewHolder(View v, final OnItemClickListener listener){
            super(v);

            //Referenciamos las referencias con los elementos del CardView
            cardView = (CardView) v.findViewById(R.id.cardView);
            titulo = (TextView) v.findViewById(R.id.tvTitulo);
            precio = (TextView) v.findViewById(R.id.tvPrecio);
            imageView = (ImageView) v.findViewById(R.id.ivImagen);
            ivMenos = (ImageView) v.findViewById(R.id.ivMenos);
            ivMas = (ImageView) v.findViewById(R.id.ivMas);
            tvCantidadPorciones = (TextView) v.findViewById(R.id.tvCantidadPorciones);
            ibRemove = (ImageButton) v.findViewById(R.id.ibRemove);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            ivMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onRetirePortionClick(position);
                        }
                    }
                }
            });

            ivMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onAddPortionClick(position);
                        }
                    }
                }
            });

            ibRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onRemoveClick(position);
                        }
                    }
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
        MyViewHolder myViewHolder = new MyViewHolder(itemView, myListenner);
        return myViewHolder;
    }

    //Este método se utiliza para la informacion de cada item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //Para dar formato al Precio
        DecimalFormat df2 = new DecimalFormat("#.00");

        myViewHolder.titulo.setText(informacion.get(i).titulo);
        myViewHolder.precio.setText("$" + df2.format(informacion.get(i).precioMostrador));
        myViewHolder.imageView.setImageResource(informacion.get(i).imagen);
        myViewHolder.tvCantidadPorciones.setText(Integer.toString(informacion.get(i).porciones));
    }

    //Regresa el tamaño de la lista de información
    @Override
    public int getItemCount() {
        return informacion.size();
    }



}
