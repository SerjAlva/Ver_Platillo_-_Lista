package com.example.vercarrito;

public class ItemCarrito {
    String titulo;
    double precio, precioMostrador;
    int imagen, porciones;
    String categoria;

    public ItemCarrito(String titulo, double precio, int imagen, int porciones, String categoria) {
        this.titulo = titulo;
        this.precio = precio;
        this.imagen = imagen;
        this.porciones = porciones;
        this.precioMostrador = precio*porciones;
        this.categoria=categoria;
    }
}
