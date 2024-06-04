/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arbolesdebusqueda;

import com.mycompany.arbolesdebusqueda.excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author salet
 */
public class ArbolMViasBusqueda<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {

    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected static final int POSICIÓN_NO_VALIDA = -1;
    protected static final int ORDEN_MINIMO = 3;

    public ArbolMViasBusqueda() {
        this.orden = 3;
    }

    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
        if (orden < ORDEN_MINIMO) {
            throw new ExcepcionOrdenInvalido();
        }
        this.orden = orden;
        System.out.println("el orden " + this.orden);
    }

    public void insertarI(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("la clave es nula");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("el valor es nulo");
        }

        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
            return;
        }
        NodoMVias<K, V> nodoActual = this.raiz;
        do {
            int posicionDeClaveAInsertar = this.buscarPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClaveAInsertar != POSICIÓN_NO_VALIDA) {
                nodoActual.setValor(posicionDeClaveAInsertar, valorAsociado);
                nodoActual = NodoMVias.nodoVacio();
            } else {
                if (nodoActual.esHoja()) {
                    //el nodo actual es hoja y la clave no esta en el nodo
                    if (nodoActual.estanClavesLlenas()) {
                        int posicionDondeEnlazar = this.buscarPosicionPorDondeBajar(nodoActual, claveAInsertar);
                        NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                        nodoActual.setHijo(posicionDondeEnlazar, nuevoHijo);
                    } else {
                        this.insertarClaveYValorOrdenado(nodoActual, claveAInsertar, valorAsociado);
                    }
                    nodoActual = NodoMVias.nodoVacio();
                } else {
                    //el nodo actual no es una hoja y ya sabemos que la clave no esta en este nodo
                    int posicionPorDondeBajar = this.buscarPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {
                        NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                        nodoActual = NodoMVias.nodoVacio();
                    } else {
                        nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                    }
                }
                //nodoActual = NodoMVias.nodoVacio();
            }
        } while (!NodoMVias.esNodoVacio(nodoActual));
        System.out.println("ya inserto");
    }

    protected int buscarPosicionDeClave(NodoMVias<K, V> nodoActual, K claveABuscar) {//bien
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveActual.compareTo(claveABuscar) == 0) {
                return i;
            }
        }
        return POSICIÓN_NO_VALIDA;
    }

    protected int buscarPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveABuscar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveActual.compareTo(claveABuscar) > 0) {
                return i;
            }
        }
        return nodoActual.cantidadDeClavesNoVacias();
    }

    public NodoMVias<K, V> insertarClaveYValorOrdenado(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAsociado) {
        for (int i = nodoActual.cantidadDeClavesNoVacias(); i > 0; i--) {
            K claveActual = nodoActual.getClave(i - 1);
            if (claveAInsertar.compareTo(claveActual) < 0) {
                nodoActual.setClave(i, nodoActual.getClave(i - 1));
                nodoActual.setValor(i, nodoActual.getValor(i - 1));
            } else {
                nodoActual.setClave(i, claveAInsertar);
                nodoActual.setValor(i, valorAsociado);
                return nodoActual;
            }
        }
        nodoActual.setClave(0, claveAInsertar);
        nodoActual.setValor(0, valorAsociado);
        return nodoActual;
    }

    //recursivo
    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave nula");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor nulo");
        }
        this.raiz = insertar(this.raiz, claveAInsertar, valorAsociado);
    }

    private NodoMVias<K, V> insertar(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAsociado) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
        } else {
            /* if (existeClaveEnNodo(nodoActual, claveAInsertar) != POSICIÓN_NO_VALIDA) {
                for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                    K claveActual = nodoActual.getClave(i);
                    if (claveAInsertar.compareTo(claveActual) == 0) {
                        nodoActual.setValor(i, valorAsociado);
                    }
                }
                return nodoActual;
            }
            if (nodoActual.estanClavesLlenas()) {
                int posicionPorDondeBajar = porDondeBajar(nodoActual, claveAInsertar);
                NodoMVias<K, V> nodoNuevo = new NodoMVias<>(posicionPorDondeBajar, claveAInsertar, valorAsociado);
                nodoActual = insertar(nodoNuevo, claveAInsertar, valorAsociado);
                return nodoActual;
            }
            if (nodoActual.esHoja() && !nodoActual.estanClavesLlenas()) {
                int posicionAInsertar = porDondeBajar(nodoActual, claveAInsertar);
                nodoActual = insertarClaveYValorOrdenado(nodoActual, claveAInsertar, valorAsociado);
                return nodoActual;
            }
            if (nodoActual.estanClavesLlenas()) {
                int posicionPorDondeBajar = porDondeBajar(nodoActual, claveAInsertar);
                NodoMVias<K, V> nodoNuevo = new NodoMVias<>(posicionPorDondeBajar, claveAInsertar, valorAsociado);
                nodoActual = insertar(nodoNuevo, claveAInsertar, valorAsociado);

                return nodoActual;
            }*/
        }
        return null;
    }

    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("Clave Invalida");
        }
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            throw new IllegalArgumentException();
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }

    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveEnTurno = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveEnTurno) == 0) {
                if (nodoActual.esHoja()) {
                    eliminarClaveDePosicion(nodoActual, i);
                    if (nodoActual.cantidadDeClavesNoVacias() == 0) {
                        return NodoMVias.nodoVacio();
                    } else {
                        return nodoActual;
                    }
                } else {
                    K claveDeReemplazo;
                    if (hayHijosNoVaciosMasAdelante(nodoActual, i)) {
                        claveDeReemplazo = obtenerSucesorInOrden(nodoActual, claveAEliminar);
                    } else {
                        claveDeReemplazo = obtenerPredecesorInOrden(nodoActual, claveAEliminar);
                    }

                    V valorDeReemplazo = buscar(claveDeReemplazo);
                    nodoActual = eliminar(nodoActual, claveDeReemplazo);
                    nodoActual.setClave(i, claveDeReemplazo);
                    nodoActual.setValor(i, valorDeReemplazo);
                    return nodoActual;
                }
            }
            if (claveAEliminar.compareTo(claveEnTurno) < 0) {
                NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
        }
        nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacias(), this.eliminar(
                nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), claveAEliminar));
        return nodoActual;
    }
    protected void eliminarClaveDePosicion(NodoMVias<K, V> nodoActual, int posicion) {
        if (nodoActual.cantidadDeClavesNoVacias() == 1) {
            nodoActual.setClave(0, null);
            nodoActual.setValor(0, null);
            return;
        }

        for (int i = posicion; i < nodoActual.cantidadDeClavesNoVacias() - 1; i++) {
            nodoActual.setClave(i, nodoActual.getClave(i + 1));
            nodoActual.setValor(i, nodoActual.getValor(i + 1));
        }
        nodoActual.setClave(nodoActual.cantidadDeClavesNoVacias() - 1, null);
        nodoActual.setValor(nodoActual.cantidadDeClavesNoVacias() - 1, null);
    }
    protected boolean hayHijosNoVaciosMasAdelante(NodoMVias<K, V> nodoActual, int posicion) {
        for (int i = posicion; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            if (!nodoActual.esHijoVacio(i + 1)) {
                return true;
            }
        }
        return false;
    }
    private K obtenerSucesorInOrden(NodoMVias<K, V> nodoActual, K clave) {
        int posicion = this.buscarPosicionPorDondeBajar(nodoActual, clave);
        if (nodoActual.esHijoVacio(posicion)) {
            return nodoActual.getClave(posicion);
        }
        K claveDeRetorno = (K) NodoMVias.datoVacio();
        NodoMVias<K, V> nodoAuxiliar = nodoActual.getHijo(posicion);
        while (!NodoMVias.esNodoVacio(nodoAuxiliar)) {
            claveDeRetorno = nodoAuxiliar.getClave(0);
            nodoAuxiliar = nodoAuxiliar.getHijo(0);
        }
        return claveDeRetorno;
    }

    protected K obtenerPredecesorInOrden(NodoMVias<K, V> nodoActual, K clave) {
        int posicion = this.buscarPosicionDeClave(nodoActual, clave);
        if (nodoActual.esHijoVacio(posicion)) {
            if (posicion > 0) {
                return nodoActual.getClave(posicion - 1);
            }
        }
        K claveDeRetorno = (K) NodoMVias.datoVacio();
        NodoMVias<K, V> nodoAuxiliar = nodoActual.getHijo(posicion);
        while (!NodoMVias.esNodoVacio(nodoAuxiliar)) {
            claveDeRetorno = nodoAuxiliar.getClave(nodoAuxiliar.cantidadDeClavesNoVacias() - 1);
            nodoAuxiliar = nodoAuxiliar.getHijo(nodoAuxiliar.cantidadDeClavesNoVacias());
        }
        return claveDeRetorno;
    }
    
    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean huboCambioDeNodoActual = false;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias() && !huboCambioDeNodoActual; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveActual.compareTo(claveABuscar) == 0) {
                    return nodoActual.getValor(i);
                }
                if (claveABuscar.compareTo(claveActual) < 0) {
                    nodoActual = nodoActual.getHijo(i);
                    huboCambioDeNodoActual = true;
                }
            }
            if (!huboCambioDeNodoActual) {
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
            }
        }
        V valorrr = (V) "bnsf";
        return valorrr;
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }

    @Override
    public int size() {//cantidad de nodos
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        do {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            cantidad++;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }//hasta aqui recorre los hijos izquierdos, falta el ultimo hijo
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        } while (!colaDeNodos.isEmpty());
        return cantidad;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i < orden; i++) {
            int alturaDeHijo = altura(nodoActual.getHijo(i));
            if (alturaDeHijo > alturaMayor) {
                alturaMayor = alturaDeHijo;
            }
        }
        return alturaMayor + 1;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        return this.altura() - 1;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        System.out.println("recorrido en in orden"+ recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i + 1), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        do {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                K claveAtual = nodoActual.getClave(i);
                recorrido.add(claveAtual);
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }//hasta aqui recorre los hios izquierdos, falta el ultimo hijo
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        } while (!colaDeNodos.isEmpty());
        System.out.println("recorrido por niveles"+recorrido);
        return recorrido;
    }

    public K minimo() {
        if (this.esArbolVacio()) {
            return null;
        }
        NodoMVias<K, V> nodoActual = this.raiz;
        NodoMVias<K, V> nodoAnterior = NodoMVias.nodoVacio();
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(0);
        }
        return nodoAnterior.getClave(0);
    }

}
