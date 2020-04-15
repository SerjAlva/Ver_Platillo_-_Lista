package com.example.vercarrito.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.vercarrito.R;
import com.example.vercarrito.adaptadores.CategoriaAdapter;
import com.example.vercarrito.modelos.CategoriaAlimento;
import com.example.vercarrito.modelos.ElementosLista;

public class Activity_ver_lista_de_compras extends AppCompatActivity{
    RecyclerView rvContenedor;
    ImageView ivComprar, ivAñadir;
    ArrayList<CategoriaAlimento> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lista_de_compras);


        //categorias = generarCategorias();
        //categorias = getInfo("{Zahanhoria:1.5:kg:Frutas y Verduras;Calabacines:3.5:kg:Frutas y Verduras;Calabacitas:1.0:kg:Frutas y Verduras;pechuga de pavo:350.0:g:Salchichonería;frijoles bayos:850.0:g:Despensa;arroz integral:900.0:g:Despensa;frijoles negros:600.0:g:Despensa;papel higiénico:20.0:rollos:Otros;aceite:200.0:ml:Básicos;sal:1.0:pizca:Básicos;pimienta:1.0:pizca:Básicos;}");
        categorias = getInfo(getIntent().getExtras().getString("lista"));
        rvContenedor = (RecyclerView) findViewById(R.id.rvContenedor);
        ivAñadir = findViewById(R.id.ivAñadir);
        ivComprar = findViewById(R.id.ivComprar);

        CategoriaAdapter adapter = new CategoriaAdapter(categorias);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_ver_lista_de_compras.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContenedor.setLayoutManager(linearLayoutManager);
        rvContenedor.setAdapter(new CategoriaAdapter(categorias));
        rvContenedor.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    ivAñadir.setVisibility(View.GONE);
                    ivComprar.setVisibility(View.GONE);

                }else{
                    ivAñadir.setVisibility(View.VISIBLE);
                    ivComprar.setVisibility(View.VISIBLE);
                }
            }
        });

        ivAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_ver_lista_de_compras.this, "añadiendo platillo :p", Toast.LENGTH_SHORT).show();
            }
        });

        ivComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("http://192.168.0.14:80/stärv_mobile/insertar_producto.php");
            }
        });
    }

    public void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Activity_ver_lista_de_compras.this, getResources().getString(R.string.onSuccessToast), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity_ver_lista_de_compras.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("elementos", generarLista());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public ArrayList<ElementosLista> generarItemsFyV(){
        ArrayList<ElementosLista> lista = new ArrayList<>();
        lista.add(new ElementosLista(1.5, "kg", "Zahanhoria"));
        lista.add(new ElementosLista(3.5, "kg", "Calabacines"));
        lista.add(new ElementosLista(1, "kg", "Calabacitas"));
        return lista;
    }

    public ArrayList<ElementosLista> generarItemsSalch() {
        ArrayList<ElementosLista> lista = new ArrayList<>();
        lista.add(new ElementosLista(350, "g", "pechuga de pavo"));
        return lista;
    }

    public ArrayList<ElementosLista> generarItemsDespensa() {
        ArrayList<ElementosLista> lista = new ArrayList<>();
        lista.add(new ElementosLista(850, "g" , "frijoles bayos"));
        lista.add(new ElementosLista(900, "g", "arroz integral"));
        lista.add(new ElementosLista(600, "g", "frijoles negros"));
        return lista;
    }

    public ArrayList<ElementosLista> generarItemsOtros() {
        ArrayList<ElementosLista> lista = new ArrayList<>();
        lista.add(new ElementosLista(20, "rollos", "papel higiénico"));
        return lista;
    }

    public ArrayList<ElementosLista> generarItemsBasicos(){
        ArrayList<ElementosLista> lista = new ArrayList<>();
        lista.add(new ElementosLista(200, "ml", "aceite"));
        lista.add(new ElementosLista(1, "pizca", "sal"));
        lista.add(new ElementosLista(1, "pizca", "pimienta"));
        return lista;
    }


    public ArrayList<CategoriaAlimento> generarCategorias(){
        ArrayList<CategoriaAlimento> lista = new ArrayList<>();
        lista.add(new CategoriaAlimento("Frutas y Verduras", R.color.FyV, generarItemsFyV()));
        lista.add(new CategoriaAlimento("Salchichonería", R.color.salch, generarItemsSalch()));
        lista.add(new CategoriaAlimento("Despensa", R.color.desp, generarItemsDespensa()));
        lista.add(new CategoriaAlimento("Otros", R.color.otros, generarItemsOtros()));
        lista.add(new CategoriaAlimento("Básicos", R.color.basicos, generarItemsBasicos()));
        return lista;
    }


    public String generarLista(){
        String lista = "";
        String separador = ":";
        for(CategoriaAlimento categoria : categorias){
            for(ElementosLista elemento : categoria.getElementos()){
                lista += elemento.getNombreIngrediente() + separador + elemento.getCantidad() + separador + elemento.getUnidad() + separador + categoria.getTitulo() + ";";
            }
        }
        //lista += "}";
        Log.e("aqui", lista);
        return lista;
    }



    public ArrayList<CategoriaAlimento> getInfo(String rawInfo){

        ArrayList<ElementosLista> informacionObtenida = new ArrayList<>();
        ArrayList<CategoriaAlimento> lista = new ArrayList<>();
        String nombre, unidad, categoria;
        Double cantidad;

        String[] elementos = rawInfo.split(";");
        for(String elemento : elementos){
            String[] infoElemento = elemento.split(":");
            /*for(String info : infoElemento){
                Log.e("infoElemento", info);
            }*/
            if(infoElemento.length == 4){
                Log.e("longitud", Integer.toString(infoElemento.length));
                nombre = infoElemento[0];
                cantidad = Double.parseDouble(infoElemento[1]);
                unidad = infoElemento[2];
                categoria = infoElemento[3];
                informacionObtenida.add(new ElementosLista(cantidad, unidad, nombre));
            }
        }
        lista.add(new CategoriaAlimento("Lista de compras", R.color.desp, informacionObtenida));
        return  lista;
    }

}
