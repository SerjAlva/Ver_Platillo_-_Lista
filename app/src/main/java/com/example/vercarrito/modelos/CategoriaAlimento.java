package com.example.vercarrito.modelos;

import java.util.ArrayList;

/*

    La clase CategoriaAlimento representa las diversas categorías en las que se pueden categorizar a los distintos productos que busquen nuestros clientes
    ejemplo:
        "Frutas y Verduras"
        "Salchichoneria"
        "Despensa"
        etc...

 */

public class CategoriaAlimento {

    private String titulo;
    private ArrayList<ElementosLista> elementos;
    private int numElementos, elementosListos;
    private boolean despliegue;
    public int color;

    public CategoriaAlimento(String titulo, int color, ArrayList<ElementosLista> elementos) {
        this.titulo = titulo;
        this.elementos = elementos;
        numElementos = elementos.size();
        this.despliegue = true;
        this.color = color;
        elementosListos = 0;
    }

    public int getElementosListos() {
        return elementosListos;
    }

    public void setElementosListos(int elementosListos) {
        this.elementosListos = elementosListos;
    }

    public void setDeployment(boolean despliegue){
        this.despliegue=despliegue;
    }

    public boolean isDeployed(){
        return despliegue;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<ElementosLista> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<ElementosLista> elementos) {
        this.elementos = elementos;
    }

    public int getNumElementos() {
        return numElementos;
    }

    public void setNumElementos(int numElementos) {
        this.numElementos = numElementos;
    }

    public void contarListos(){
        int x=0;
        for(ElementosLista item : elementos){
            if(item.isReady())
                x++;
        }
        elementosListos = x;
    }


}
