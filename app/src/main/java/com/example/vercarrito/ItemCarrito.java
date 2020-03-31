package com.example.vercarrito;

public class ItemCarrito {
    String titulo;
    double precio, precioMostrador;
    int imagen, porciones;

    public ItemCarrito(String titulo, double precio, int imagen, int porciones) {
        this.titulo = titulo;
        this.precio = precio;
        this.imagen = imagen;
        this.porciones = porciones;
        this.precioMostrador = precio*porciones;
    }
}
