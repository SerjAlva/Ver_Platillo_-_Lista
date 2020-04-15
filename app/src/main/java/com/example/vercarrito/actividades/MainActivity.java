package com.example.vercarrito.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vercarrito.R;
import com.example.vercarrito.adaptadores.AdaptadorPlatillo;
import com.example.vercarrito.modelos.CategoriaAlimento;
import com.example.vercarrito.modelos.ItemCarrito;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Creamos variables de referencia
    RecyclerView recyclerView;
    List<String> itemsCarrito;
    //Creamos un adaptador y un LinearLayoutManager que son necesarios para usar nuestro recyclerView
    LinearLayoutManager linearLayoutManager;
    AdaptadorPlatillo adaptadorPlatillo;
    Button btnComprar;
    double total = 0;
    ArrayList<ItemCarrito> lista = cargarDatos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Realizamos la carga de datos al iniciar la actividad



        //Referenciamos a las variables con los elementos de nuestro xml
        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        btnComprar = findViewById(R.id.btnComprar);

        //Inicializamos a linearLayoutManager y lo asignamos a nuestro recyclerView

        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Asignamos el adaptador a nuestro recyclerView
        adaptadorPlatillo = new AdaptadorPlatillo(this, lista);
        recyclerView.setAdapter(adaptadorPlatillo);

        //Configuramos lo que sea que querramos que haga la aplicación al tocar un elemento del RecyclerView
        adaptadorPlatillo.setOnItemClickListener(new AdaptadorPlatillo.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            //Este método se manda a llamar cuando el usuario añade porciones
            @Override
            public void onAddPortionClick(int position) {
                lista.get(position).porciones++;
                lista.get(position).precioMostrador = lista.get(position).precio*lista.get(position).porciones;
                actualizarPrecio(lista.get(position).precio);
                adaptadorPlatillo.notifyItemChanged(position);
            }
            //Este método se manda a llamar cuando el usuario quita porciones
            @Override
            public void onRetirePortionClick(int position) {

                if(lista.get(position).porciones == 1){
                    Toast.makeText(MainActivity.this, "No se pueden retirar porciones", Toast.LENGTH_SHORT).show();
                }else{
                    lista.get(position).porciones--;
                    lista.get(position).precioMostrador = lista.get(position).precio*lista.get(position).porciones;
                    actualizarPrecio(-(lista.get(position).precio));
                    adaptadorPlatillo.notifyItemChanged(position);
                }
            }

            //Este método se manda a llamar cuando se elimina un item
            @Override
            public void onRemoveClick(final int position) {
                final int posicion = position;
                String pregunta = getString(R.string.onRemove1);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(pregunta + lista.get(position).titulo + "?")
                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                actualizarPrecio(-(lista.get(posicion).precioMostrador));
                                lista.remove(posicion);
                                adaptadorPlatillo.notifyItemRemoved(posicion);
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                //AlertDialog dialog = builder.create();
                try {
                    builder.show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Este listener nos ayuda a controlar cuando vemos o no el botón de compra
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

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Reordenando Datos", Toast.LENGTH_SHORT).show();
                /*lista = cargarDatos();
                adaptador.notifyDataSetChanged();*/
                String listaCompras = "";
                /*for(ItemCarrito item : lista){
                    if(item.listaIngredientes.size() != 0){
                        for(String ingrediente : item.listaIngredientes){
                            listaCompras += ingrediente;
                        }
                    }
                }*/
                Intent intent = new Intent(MainActivity.this, Activity_ver_lista_de_compras.class)
                        .putExtra("lista", "{Zahanhoria:1.5:kg:Frutas y Verduras;Calabacines:3.5:kg:Frutas y Verduras;Calabacitas:1.0:kg:Frutas y Verduras;pechuga de pavo:350.0:g:Salchichonería;frijoles bayos:850.0:g:Despensa;arroz integral:900.0:g:Despensa;frijoles negros:600.0:g:Despensa;papel higiénico:20.0:rollos:Otros;aceite:200.0:ml:Básicos;sal:1.0:pizca:Básicos;pimienta:1.0:pizca:Básicos;}");
                startActivity(intent);
            }
        });

        //Calculamos el total a pagar por el cliente
        for(ItemCarrito item : lista){
            total+=item.precioMostrador;
        }
        btnComprar.setText("Total: $" + total);

    }


    public void actualizarPrecio(double monto){
        total+=monto;
        btnComprar.setText("Total: $" + total);
    }

    //Método para hacer la carga de la información que se va a desplegar en el recyclerView
    public ArrayList<ItemCarrito> cargarDatos(){
        ArrayList<ItemCarrito> lista = new ArrayList<ItemCarrito>();

        lista.add(new ItemCarrito("Sandwich el Cochinito", 200.00, R.drawable.cochinito, 1, "Carnes"));
        lista.add(new ItemCarrito("Hamburguesa", 100.00, R.drawable.hamburguesa, 1, "Parrilla"));
        lista.add(new ItemCarrito("Pizza Artesanal", 120.50, R.drawable.pizza, 1, "Horneado"));
        lista.add(new ItemCarrito("Parrillada", 225.00, R.drawable.carne_asada, 1, "Parrilla"));
        lista.add(new ItemCarrito("Mojarra al mojo de Ajo", 160.00, R.drawable.mojarra, 1, "Mar"));
        lista.add(new ItemCarrito("Pollo a la bologneza", 150.50, R.drawable.pollo_bolognesa, 1, "Aves"));
        return lista;
    }

    public ArrayList<ItemCarrito> reordenarDatos(ArrayList<ItemCarrito> lista){
        ArrayList<ItemCarrito> nuevaLista = new ArrayList<>();
        String[] categorias = {"Aves", "Carnes", "Horneado", "Mar", "Parrilla" };

        for(String categoria : categorias) {
            for (ItemCarrito item : lista) {
                if (item.categoria.equals(categoria)) {
                    nuevaLista.add(item);
                }
            }
        }
        return nuevaLista;
    }
}
