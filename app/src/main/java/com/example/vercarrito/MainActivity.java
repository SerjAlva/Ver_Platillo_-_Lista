package com.example.vercarrito;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Creamos variables de referencia
    RecyclerView recyclerView;
    List<String> itemsCarrito;
    //Creamos un adaptador y un LinearLayoutManager que son necesarios para usar nuestro recyclerView
    LinearLayoutManager linearLayoutManager;
    Adaptador adaptador;
    Button btnComprar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Realizamos la carga de datos al iniciar la actividad
        ArrayList<ItemCarrito> lista = cargarDatos();


        //Referenciamos a las variables con los elementos de nuestro xml
        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        btnComprar = (Button) findViewById(R.id.btnComprar);

        //Inicializamos a linearLayoutManager y lo asignamos a nuestro recyclerView
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Asignamos el adaptador a nuestro recyclerView
        adaptador = new Adaptador(this, lista);
        recyclerView.setAdapter(adaptador);

        double total = 0;
        for(ItemCarrito item : lista){
            total+=item.precio;
        }
        btnComprar.setText("Total: $" + total);

        //Este listenner nos ayuda a controlar cuando vemos o no el botón de compra
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Si el desplazamiento es hacia abajo:
                if (dy > 0) {
                    btnComprar.setVisibility(View.INVISIBLE);
                }
                //Si el desplazamiento es hacia arriba:
                else {
                    btnComprar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //Método para hacer la carga de la información que se va a desplegar en el recyclerView
    public ArrayList<ItemCarrito> cargarDatos(){
        ArrayList<ItemCarrito> lista = new ArrayList<ItemCarrito>();
        lista.add(new ItemCarrito("Pollo a la bologneza", 150.50, R.drawable.pollo_bolognesa));
        lista.add(new ItemCarrito("Sandwich el Cochinito", 200.00, R.drawable.cochinito));
        lista.add(new ItemCarrito("Hamburguesa", 100.00, R.drawable.hamburguesa));
        lista.add(new ItemCarrito("Pizza Artesanal", 120.50, R.drawable.pizza));
        lista.add(new ItemCarrito("Parrillada", 225.00, R.drawable.carne_asada));
        lista.add(new ItemCarrito("Mojarra al mojo de Ajo", 160.00, R.drawable.mojarra));
        return lista;
    }
}
