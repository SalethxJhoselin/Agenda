/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arbolesdebusqueda;

/**
 *
 * @author salet
 */
public class NodoBinario<K, V> {

    private K clave;
    private V valor;
    private NodoBinario<K, V> hijoIzquierdo;
    private NodoBinario<K, V> hijoDerecho;

    public NodoBinario() {
    }

    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return this.clave;
    }

    public V getValor() {
        return this.valor;
    }

    public NodoBinario<K, V> getHijoIzquierdo() {
        return this.hijoIzquierdo;
    }

    public NodoBinario<K, V> getHijoDerecho() {
        return this.hijoDerecho;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public void setHijoIzquierdo(NodoBinario<K, V> hijo) {
        this.hijoIzquierdo = hijo;
    }

    public void setHijoDerecho(NodoBinario<K, V> hijo) {
        this.hijoDerecho = hijo;
    }

    public boolean esVacioHijoIzquierdo() {
        return hijoIzquierdo == null;
    }

    public boolean esVacioHijoDerecho() {
        return hijoDerecho == null;
    }

    public boolean esHoja() {
        return this.esVacioHijoIzquierdo() && this.esVacioHijoDerecho();
    }

    public static boolean esNodoVacio(NodoBinario nodo) {
        return nodo == NodoBinario.nodoVacio();
    }

    public static NodoBinario<?, ?> nodoVacio() {
        return null;
    }
}
