/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arbolesdebusqueda;

/**
 *
 * @author salet
 */
public class AVL<K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V> {

    private static final byte DIFERENCIA_MAXIMA = 1;

    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave no puede ser nula");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("valor no puede ser nulo");
        }
        super.raiz = this.insertar(this.raiz, claveAInsertar, valorAInsertar);
    }

    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho = insertar(nodoActual.getHijoDerecho(),
                    claveAInsertar, valorAInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return this.balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo = insertar(nodoActual.getHijoIzquierdo(),
                    claveAInsertar, valorAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return this.balancear(nodoActual);
        }
        //si llego aqui quiere decir que en el nodo actual esta laa clave a insertar
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }

    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaRamaIzq = this.altura(nodoActual.getHijoIzquierdo());
        int alturaRamaDer = this.altura(nodoActual.getHijoDerecho());
        int diferencia = alturaRamaIzq - alturaRamaDer;
        if (diferencia > DIFERENCIA_MAXIMA) {
            NodoBinario<K, V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            alturaRamaIzq = this.altura(hijoIzquierdo.getHijoIzquierdo());
            alturaRamaDer = this.altura(hijoIzquierdo.getHijoDerecho());
            if (alturaRamaIzq > alturaRamaDer) {
                return rotacionSimpleADerecha(nodoActual);
            } else {
                return rotacionDobleADerecha(nodoActual);
            }
        } else if (diferencia < -DIFERENCIA_MAXIMA) {
            NodoBinario<K, V> hijoDerecho = nodoActual.getHijoDerecho();
            alturaRamaIzq = this.altura(hijoDerecho.getHijoIzquierdo());
            alturaRamaDer = this.altura(hijoDerecho.getHijoDerecho());
            if (alturaRamaDer > alturaRamaIzq) {
                return rotacionSimpleAIzquierda(nodoActual);
            } else {
                return rotacionDobleAIzquierda(nodoActual);
            }
        }
        return nodoActual;
    }

    private NodoBinario<K, V> rotacionSimpleADerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }

    private NodoBinario<K, V> rotacionSimpleAIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }

    private NodoBinario<K, V> rotacionDobleADerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRotaIzquierda = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoQueRotaIzquierda);
        return this.rotacionSimpleADerecha(nodoActual);
    }

    private NodoBinario<K, V> rotacionDobleAIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRotaDerecha = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoQueRotaDerecha);
        return this.rotacionSimpleAIzquierda(nodoActual);
    }

    public V eliminar(K clave) {
        if (clave == null) {
            throw new IllegalArgumentException("clave nula");
        }
        V valorAsociado = buscar(clave);
        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor nulOOo");
        }
        this.raiz = eliminar(this.raiz, clave);
        return valorAsociado;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> posibleHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> posibleHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(posibleHijoDerecho);
            return balancear(nodoActual);
        }
        // aqui ya encontre al nodo
        if (nodoActual.esHoja()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        if (nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
            return nodoActual.getHijoIzquierdo();
        }
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            return nodoActual.getHijoDerecho();
        }
        if (!nodoActual.esHoja()) {
            NodoBinario<K, V> nodoSucesor = sucesorInOrden(nodoActual.getHijoDerecho());
            NodoBinario<K,V> posibleHIjoDerecho = eliminar(nodoActual, nodoSucesor.getClave());
            nodoActual.setHijoDerecho(posibleHIjoDerecho);
            nodoActual.setClave(nodoSucesor.getClave());
            nodoActual.setValor(nodoSucesor.getValor());
        }
        return nodoActual;
    }

    public NodoBinario<K, V> sucesorInOrden(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior;
        do {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (NodoBinario.esNodoVacio(nodoActual));
        return nodoActual;
    }

}
