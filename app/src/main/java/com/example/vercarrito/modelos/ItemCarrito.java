package com.example.vercarrito.modelos;

import java.util.ArrayList;

public class ItemCarrito {
    public String titulo;
    public double precio, precioMostrador;
    public int imagen, porciones;
    public String categoria;
    public ArrayList<String> listaIngredientes;

    public ItemCarrito(String titulo, double precio, int imagen, int porciones, String categoria) {
        this.titulo = titulo;
        this.precio = precio;
        this.imagen = imagen;
        this.porciones = porciones;
        this.precioMostrador = precio*porciones;
        this.categoria=categoria;
    }

    public ItemCarrito(String titulo, double precio, double precioMostrador, int imagen, int porciones, String categoria, ArrayList<String> listaIngredientes) {
        this.titulo = titulo;
        this.precio = precio;
        this.precioMostrador = precioMostrador;
        this.imagen = imagen;
        this.porciones = porciones;
        this.categoria = categoria;
        this.listaIngredientes = listaIngredientes;
    }
}
