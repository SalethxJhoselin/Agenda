/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arbolesdebusqueda;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author salet
 */
public class NodoMVias<K, V> {

    private List<K> listaDeClaves;
    private List<V> listaDeValores;
    private List<NodoMVias<K, V>> listaDeHijos;
    
    public static NodoMVias nodoVacio() {
        return null;
    }
//orden=cantidad de hijos de cada nodo
    public NodoMVias(int orden) {
        this.listaDeClaves = new ArrayList<>();
        this.listaDeValores = new ArrayList<>();
        this.listaDeHijos = new ArrayList<>();

        for (int i = 0; i < orden - 1; i++) {
            this.listaDeClaves.add((K) NodoMVias.datoVacio());
            this.listaDeValores.add((V) NodoMVias.datoVacio());
            this.listaDeHijos.add(NodoMVias.nodoVacio());
        }
        this.listaDeHijos.add(NodoMVias.nodoVacio());
    }

    public NodoMVias(int orden, K clave, V valor) {
        this(orden);
        this.listaDeClaves.set(0, clave);
        this.listaDeValores.set(0, valor);
    }


    public static Object datoVacio() {
        return null;
    }

    /**
     * retorna la clave de la posicion indicada por el parametro posicion
     * PRE-Condicion:El parametro posicion indica una posicion valida en el
     * arreglo de la lista de claves
     */
    public K getClave(int posicion) {
        return this.listaDeClaves.get(posicion);
    }

    public void setClave(int posicion, K clave) {
        this.listaDeClaves.set(posicion, clave);
    }

    public V getValor(int posicion) {
        return this.listaDeValores.get(posicion);
    }

    public void setValor(int posicion, V valor) {
        this.listaDeValores.set(posicion, valor);
    }

    public NodoMVias<K, V> getHijo(int posicion) {
        return this.listaDeHijos.get(posicion);
    }

    public void setHijo(int posicion, NodoMVias<K, V> nodo) {
        this.listaDeHijos.set(posicion, nodo);
    }

    public static boolean esNodoVacio(NodoMVias nodo) {
        return nodo == NodoMVias.nodoVacio();
    }

    public boolean esClaveVacia(int posicion) {
        return this.listaDeClaves.get(posicion) == NodoMVias.datoVacio();
    }
    public boolean esHijoVacio(int posicion){
        return this.listaDeHijos.get(posicion)== NodoMVias.nodoVacio();
    }
    public boolean esHoja(){
        for (int i=0; i<this.listaDeHijos.size();i++){
            if(!this.esHijoVacio(i)){
                return false;
            }
        }
        return true;
    }
    
    public boolean estanClavesLlenas(){
        for (int i=0;i<this.listaDeClaves.size();i++){
            if(this.esClaveVacia(i)){
                return false;
            }
        }
        return true;
    }
    public int cantidadDeClavesNoVacias(){
        int cantidad=0;
        for (int i=0;i<this.listaDeClaves.size();i++){
            if(!this.esClaveVacia(i)){
                cantidad++;
            }
        }
        return cantidad;
    }
    public int cantidadDeHijosVacios(){
        int cantidad=0;
        for (int i=0;i<this.listaDeHijos.size();i++){
            if(!this.esHijoVacio(i)){
                cantidad++;
            }
        }
        return cantidad;
    }

}
