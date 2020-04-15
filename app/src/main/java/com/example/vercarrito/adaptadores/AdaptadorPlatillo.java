package com.example.vercarrito.adaptadores;

import android.app.DatePickerDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vercarrito.modelos.ItemCarrito;
import com.example.vercarrito.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AdaptadorPlatillo extends RecyclerView.Adapter<AdaptadorPlatillo.MyViewHolder>{
    //Lista con los elementos que se van a desplegar
    public static final int MAX_ITEMS=5;

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
        public ImageView imageView, ivMenos, ivMas, ivRecordatorio;
        public ImageButton ibRemove;
        public ConstraintLayout constraintLayout;
        public ArrayList<ItemCarrito> lista;


        public MyViewHolder(View v, final OnItemClickListener listener, final ArrayList<ItemCarrito> lista){
            super(v);
            this.lista = lista;
            //Referenciamos las referencias con los elementos del CardView
            cardView = (CardView) v.findViewById(R.id.cardView);
            titulo = (TextView) v.findViewById(R.id.tvTitulo);
            precio = (TextView) v.findViewById(R.id.tvPrecio);
            imageView = (ImageView) v.findViewById(R.id.ivImagen);
            ivMenos = (ImageView) v.findViewById(R.id.ivMenos);
            ivMas = (ImageView) v.findViewById(R.id.ivMas);
            ivRecordatorio = (ImageView) v.findViewById(R.id.ivRecordatorio);
            tvCantidadPorciones = (TextView) v.findViewById(R.id.tvCantidadPorciones);
            ibRemove = (ImageButton) v.findViewById(R.id.ibRemove);
            constraintLayout = (ConstraintLayout) v.findViewById(R.id.constraintLayout);

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

            ivRecordatorio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();
                    final Calendar calendario = Calendar.getInstance();
                    int dia = calendario.get(Calendar.DAY_OF_MONTH);
                    int mes = calendario.get(Calendar.MONTH);
                    int anio = calendario.get(Calendar.YEAR);
                    final String titulo = lista.get(getAdapterPosition()).titulo;


                    //Toast.makeText(context, dia+"/"+mes+"/"+anio, Toast.LENGTH_SHORT).show();

                    DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendario.set(year, month, dayOfMonth);
                            //Toast.makeText(context, calendario.get(Calendar.DAY_OF_MONTH) + "/" + calendario.get(Calendar.MONTH) + "/" + calendario.get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_INSERT)
                                    .setData(CalendarContract.Events.CONTENT_URI)
                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendario.getTimeInMillis())
                                    .putExtra(CalendarContract.Events.TITLE, titulo);
                            context.startActivity(intent);
                        }
                    }, anio, mes, dia);

                    datePickerDialog.show();

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
    public AdaptadorPlatillo(Context context, ArrayList<ItemCarrito> informacion) {
        this.context = context;
        this.informacion = informacion;
    }

    //Este método nos sirve para crear la vista de cada item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView;
        //if(i<3)
            itemView = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_carrito, viewGroup, false);
        //else
            itemView = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prueba_item_carrito, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView, myListenner, informacion);
        return myViewHolder;
    }

    //Este método se utiliza para la informacion de cada item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //Para dar formato al Precio
        DecimalFormat df2 = new DecimalFormat("#.00");


        if(getItemCount()>MAX_ITEMS && i < 3){
            ViewGroup.LayoutParams ivParams = myViewHolder.imageView.getLayoutParams();
            ivParams.height -=80;
            myViewHolder.imageView.setLayoutParams(ivParams);
        }

        myViewHolder.titulo.setText(informacion.get(i).titulo);
        myViewHolder.precio.setText("$" + df2.format(informacion.get(i).precioMostrador));
        myViewHolder.tvCantidadPorciones.setText(Integer.toString(informacion.get(i).porciones));
        myViewHolder.imageView.setImageResource(informacion.get(i).imagen);
        switch (informacion.get(i).categoria){
            case "Aves":
                myViewHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.aves));
                break;
            case "Carnes":
                myViewHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.carnes));
                break;
            case "Horneado":
                myViewHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.horneado));
                break;
            case "Mar":
                myViewHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.pescado));
                break;
            case "Parrilla":
                myViewHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.parrilla));
                break;
        }
    }

    //Regresa el tamaño de la lista de información
    @Override
    public int getItemCount() {
        return informacion.size();
    }



}
