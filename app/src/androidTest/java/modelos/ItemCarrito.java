package modelos;

public class ItemCarrito {

    /*
     *
     * Esta clase sólo es para lectura, en realidad no es utilizada por las actividades. Si quieres Modificar
     * la clase ItemCarrito está ubicada en:
     *           C:\Users\sergi\AndroidStudioProjects\Ver-Platillo-V1\app\src\main\java\com\example\vercarrito\ItemCarrito.java
     *           com.example.vercarrito.modelos.ItemCarrito
     *
     */

    public String titulo;
    public double precio, precioMostrador;
    public int imagen, porciones;
    public String categoria;

    public ItemCarrito(String titulo, double precio, int imagen, int porciones, String categoria) {
        this.titulo = titulo;
        this.precio = precio;
        this.imagen = imagen;
        this.porciones = porciones;
        this.precioMostrador = precio*porciones;
        this.categoria=categoria;
    }
}
