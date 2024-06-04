/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arbolesdebusqueda;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author salet
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements
        IArbolBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {

    }

    public NodoBinario<K, V> sucesorInOrden(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior;
        do {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return nodoAnterior;
    }

    //buscar Recursivo
    public V buscarRec(K claveABuscar) {
        if (claveABuscar == null) {
            throw new IllegalArgumentException("clave nula");
        }
        return buscarRec(this.raiz, claveABuscar);
    }

    private V buscarRec(NodoBinario<K, V> nodoActual, K claveABuscar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return null;
        }
        K claveActual = nodoActual.getClave();
        if (claveABuscar.compareTo(claveActual) < 0) {
            V posibleValorPorIzquierda = buscarRec(nodoActual.getHijoIzquierdo(), claveABuscar);
            return posibleValorPorIzquierda;
        }
        if (claveABuscar.compareTo(claveActual) > 0) {
            V posibleValorPorDerecha = buscarRec(nodoActual.getHijoDerecho(), claveABuscar);
            return posibleValorPorDerecha;
        }
        return nodoActual.getValor();
    }
    //eliminar recursivo
    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("clave nula");
        }
        V valorAEliminar = buscarRec(claveAEliminar);
        if (valorAEliminar == null) {
            throw new IllegalArgumentException("la clave no se encuentra en el arbo");
        }
        NodoBinario<K, V> nodoActual = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> posibleHijoIzquierdo = eliminar(
                    nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(posibleHijoIzquierdo);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> posibleHijoDerecho = eliminar(
                    nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(posibleHijoDerecho);
            return nodoActual;
        }
        //caso 1
        if (nodoActual.esHoja()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        //caso 2
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        //caso 3
        if (!nodoActual.esHoja()) {
            NodoBinario<K, V> nodoSucesor=sucesorInOrden(nodoActual.getHijoDerecho());
            NodoBinario<K, V> posibleHijoDerecho = eliminar(
                    nodoActual.getHijoDerecho(), nodoSucesor.getClave());
            nodoActual.setHijoDerecho(posibleHijoDerecho);
            nodoActual.setClave(nodoSucesor.getClave());
            nodoActual.setValor(nodoSucesor.getValor());
        }
        return nodoActual;
    }
    
    public int sizeRec(){
        if(this.esArbolVacio())
            return 0;
        return sizeRec(this.raiz);
    }
    private int sizeRec(NodoBinario <K,V> nodoActual){
        if(NodoBinario.esNodoVacio(nodoActual))
            return 0;
        int cantNodosPorRamaIzquierda = sizeRec(nodoActual.getHijoIzquierdo());
        int cantNodosPorRamaDerecha = sizeRec(nodoActual.getHijoDerecho());
        int cantNodosHijos = cantNodosPorRamaIzquierda+cantNodosPorRamaDerecha;
        return cantNodosHijos+1;
    }
    
   //devuelve el nivel del nodo recursivo
    public int nivelDelNodo(K clave){
        if(clave ==null)
            throw new IllegalArgumentException("clave nula");
        V valorAsociado=buscarRec(clave);
        if(valorAsociado ==null)
            throw new IllegalArgumentException("el nodo no se encuentra en el arbol");
        return nivelDelNodo(this.raiz, clave)-1;
    }
    private int nivelDelNodo(NodoBinario<K,V> nodoActual, K clave){
        K claveActual=nodoActual.getClave();
        int nivelPorIzquierda=0;
        int nivelPorDerecha=0;
        if(NodoBinario.esNodoVacio(nodoActual))
            return 0;
        if(clave.compareTo(claveActual)<0){
            nivelPorIzquierda = nivelDelNodo(nodoActual.getHijoIzquierdo(), clave);
            return nivelPorIzquierda+1;
        }
        if(clave.compareTo(claveActual)>0){
            nivelPorDerecha=nivelDelNodo(nodoActual.getHijoDerecho(), clave);
            return nivelPorDerecha+1;
        }
        return nivelPorIzquierda+nivelPorDerecha+1;
    }
    
    /*reconstruccion de arboles en base a sus recorridos recursivo*/
    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden, boolean usandoPreOrden) {
        if (clavesInOrden.isEmpty() || clavesNoInOrden.isEmpty()
                || valoresInOrden.isEmpty() || valoresNoInOrden.isEmpty()) {
            throw new IllegalArgumentException("existen parametros vacios");
        }
        if (clavesInOrden == null || clavesNoInOrden == null
                || valoresInOrden == null || valoresNoInOrden == null) {
            throw new IllegalArgumentException("existen parametros nulos");
        }
        if (clavesInOrden.size() != clavesNoInOrden.size()
                || valoresInOrden.size() != valoresNoInOrden.size()) {
            throw new IllegalArgumentException("los tamanos de las listas no pueden ser distintos");
        }
        if (usandoPreOrden) {
            this.raiz = reconstruirConPreOrden(clavesInOrden, valoresInOrden, clavesNoInOrden,
                    valoresNoInOrden);
        } else {
            this.raiz = reconstruirConPostOrden(clavesInOrden, valoresInOrden, clavesNoInOrden,
                    valoresNoInOrden);
        }
    }

    public NodoBinario<K, V> reconstruirConPreOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesEnPreOrden, List<V> valoresEnPreOrden) {
        if (clavesInOrden.isEmpty()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }

        int posicionDeClavePadreEnPreOrden = 0;
        K clavePadre = clavesEnPreOrden.get(posicionDeClavePadreEnPreOrden);
        V valorPadre = valoresEnPreOrden.get(posicionDeClavePadreEnPreOrden);
        int posicionDeClavePadreEnInOrden = this.posicionDeClave(clavePadre,clavesInOrden);
        //para armar rama izquierda
        List<K> clavesInOrdenPorIzquierda = clavesInOrden.subList(posicionDeClavePadreEnPreOrden, posicionDeClavePadreEnInOrden);
        List<V> valoresInOrdenPorIzquierda = valoresInOrden.subList(posicionDeClavePadreEnPreOrden, posicionDeClavePadreEnInOrden);
        List<K> clavesEnPreOrdenPorIzquierda = clavesEnPreOrden.subList(posicionDeClavePadreEnPreOrden+1, posicionDeClavePadreEnInOrden+1);
        List<V> valoresEnPreOrdenPorIzquierda = valoresEnPreOrden.subList(posicionDeClavePadreEnPreOrden+1, posicionDeClavePadreEnInOrden+1);
        NodoBinario<K, V> hijoIzquierdo = reconstruirConPreOrden(clavesInOrdenPorIzquierda, valoresInOrdenPorIzquierda,
                clavesEnPreOrdenPorIzquierda, valoresEnPreOrdenPorIzquierda);

        //para armar rama derecha
        List<K> clavesInOrdenPorDerecha = clavesInOrden.subList(posicionDeClavePadreEnInOrden+1, clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClavePadreEnInOrden+1, clavesInOrden.size());
        List<K> clavesEnPreOrdenPorDerecha = clavesEnPreOrden.subList(posicionDeClavePadreEnInOrden+1, clavesInOrden.size());
        List<V> valoresEnPreOrdenPorDerecha = valoresEnPreOrden.subList(posicionDeClavePadreEnInOrden+1, clavesInOrden.size());
        NodoBinario<K,V> hijoDerecho=reconstruirConPreOrden(clavesInOrdenPorDerecha,valoresInOrdenPorDerecha,
                clavesEnPreOrdenPorDerecha,valoresEnPreOrdenPorDerecha);
        //armando el nodo actual
        NodoBinario<K,V> nodoPadre =new NodoBinario<>(clavePadre,valorPadre);
        nodoPadre.setHijoIzquierdo(hijoIzquierdo);
        nodoPadre.setHijoDerecho(hijoDerecho);
        return nodoPadre;
    }
    private int posicionDeClave(K claveABuscar, List<K> listaDeClaves) {
        for (int i = 0; i < listaDeClaves.size(); i++) {
            K claveActual = listaDeClaves.get(i);
            if (claveActual.compareTo(claveABuscar) == 0) {
                return i;
            }
        }
        return -1; //posicion invalida
    }
//HASTA AQUI
    public NodoBinario<K, V> reconstruirConPostOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesEnPostOrden, List<V> valoresEnPostOrden) {
        return null;
    }

    public ArbolBinarioBusqueda(NodoBinario nodoRaiz) {
        this.raiz = nodoRaiz;
    }

    /*• Contar nodos
    • Contar hojas.
    • Reconstruir árbol a partir de sus recorridos*/
    //insertarIterativo
    public void insertar(K clave, V valor) {
        if (clave == null) {
            throw new IllegalArgumentException("clave nula");
        }
        if (valor == null) {
            throw new IllegalArgumentException("valor nulo");
        }
        NodoBinario<K, V> nuevoNodo = new NodoBinario<>(clave, valor);
        /* <> se utiliza para inferir automáticamente los tipos*/
        if (this.esArbolVacio()) {
            this.raiz = nuevoNodo;
            return;
        }
        //NodoBinario<K, V> nodoAnterior = null;
        NodoBinario<K, V> nodoAnterior = (NodoBinario<K, V>) NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        do {
            K claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (clave.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (clave.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else if (clave.compareTo(claveActual) == 0) {
                nodoActual.setValor(valor);
                return;
            }
        } while (!NodoBinario.esNodoVacio(nodoActual));

        if (clave.compareTo(nodoAnterior.getClave()) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
            return;
        } else if (clave.compareTo(nodoAnterior.getClave()) > 0) {
            nodoAnterior.setHijoDerecho(nuevoNodo);
            return;
        }
    }
    //insertarRecursivo
    public void insertarRecursivo(K clave, V valor){
        if(clave==null)
            throw new IllegalArgumentException("CLAVE NULA");
        if(valor==null)
            throw new IllegalArgumentException("VALOR NULO");
        this.raiz=insertarRecursivo(this.raiz, clave, valor);
    }
    private NodoBinario<K,V> insertarRecursivo(NodoBinario<K,V>nodoActual, K claveAInsertar, V valorAsociado){
        if(nodoActual.esNodoVacio(nodoActual)){
            return new NodoBinario<>(claveAInsertar, valorAsociado);
        }else{
            K claveActual=nodoActual.getClave();
            if(claveAInsertar.compareTo(claveActual)<0){
                NodoBinario<K,V> HIAInsertar=insertarRecursivo(nodoActual.getHijoIzquierdo(), claveAInsertar, valorAsociado);
                nodoActual.setHijoIzquierdo(HIAInsertar);
                return nodoActual;
            }
            if(claveAInsertar.compareTo(claveActual)>0){
                NodoBinario<K,V> HDAInsertar=insertarRecursivo(nodoActual.getHijoDerecho(), claveAInsertar, valorAsociado);
                nodoActual.setHijoDerecho(HDAInsertar);
                return nodoActual;
            }
            nodoActual.setValor(valorAsociado);
            return nodoActual;
        }
    }


    protected NodoBinario<K, V> buscarNodoSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior;
        do {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return nodoAnterior;
    }
    //buscar iterativo
    @Override
    public V buscar(K clave) {
        if (clave == null) {
            throw new IllegalArgumentException("clave nula");
        }
        if (this.esArbolVacio()) {
            throw new IllegalArgumentException("Arbol Vacio");
        }
        NodoBinario<K, V> nodoActual = this.raiz;
        do {
            K claveActual = nodoActual.getClave();
            if (clave.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (clave.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else if (clave.compareTo(claveActual) == 0) {
                return nodoActual.getValor();
            }
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return null;
    }

    @Override
    public boolean contiene(K clave) {
        if (this.buscar(clave) != null) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        if (!this.esArbolVacio()) {
            int cantidadDeNodos = 0;
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                cantidadDeNodos++;
                if (!nodoActual.esVacioHijoDerecho()) {
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
            }
            return cantidadDeNodos;
        } else {
            return 0;
        }
    }

    public int alturaIt() {
        if (!this.esArbolVacio()) {
            int alturaDelArbol = 0;
            Queue<NodoBinario<K, V>> colaDeNodos;
            colaDeNodos = new LinkedList<>();
            NodoBinario<K, V> nodoActual = this.raiz;
            colaDeNodos.offer(nodoActual);
            while (!colaDeNodos.isEmpty()) {
                int cantidadDeNodosLaCola = colaDeNodos.size();
                int i = 0;
                while (i < cantidadDeNodosLaCola) {
                    nodoActual = colaDeNodos.poll();
                    if (nodoActual.getHijoIzquierdo() != null) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (nodoActual.getHijoDerecho() != null) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    i++;
                }
                alturaDelArbol++;
            }
            return alturaDelArbol;
        }
        return 0;
    }
//altura recursivo
    @Override
    public int altura() {//nivel + 1 
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) { //como decir n=0
            return 0;
        }
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        if (alturaPorIzquierda > alturaPorDerecha) {
            return alturaPorIzquierda + 1;
        } else {
            return alturaPorDerecha + 1;
        }
    }

    @Override
    public void vaciar() {
        this.raiz = (NodoBinario<K, V>) NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        return nivel(this.raiz);
    }

    private int nivel(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) { //como decir n=0
            return -1;
        }
        int nivelPorIzquierda = nivel(nodoActual.getHijoIzquierdo());
        int nivelPorDerecha = nivel(nodoActual.getHijoDerecho());
        if (nivelPorIzquierda > nivelPorDerecha) {
            return nivelPorIzquierda + 1;
        } else {
            return nivelPorDerecha + 1;
        }
    }

    public K minimo() { //el nodo con clave mas minima del arbol  en este caso el HI()
        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = null;
        if (!this.esArbolVacio()) {
            while (!NodoBinario.esNodoVacio(nodoActual)) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            }
        }
        return nodoAnterior.getClave();
    }

    public K maximo() { //el nodo con clave mas alto del arbol  en este caso el HD()
        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = null;
        if (!this.esArbolVacio()) {
            while (!NodoBinario.esNodoVacio(nodoActual)) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
        return nodoAnterior.getClave();
    }

    
    public List<K> recorridoEnInOrdenIterativo() {
        List<K> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            NodoBinario<K, V> nodoActual = this.raiz;
            meterALaPilaParaInOrden(pilaDeNodos, nodoActual);
            while (!pilaDeNodos.isEmpty()) {
                nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
                if (!NodoBinario.esNodoVacio(nodoActual.getHijoDerecho())) {
                    meterALaPilaParaInOrden(pilaDeNodos,
                            nodoActual.getHijoDerecho());
                }
            }
        }
        return recorrido;
    }

    public Stack<NodoBinario<K, V>> meterALaPilaParaInOrden(
            Stack<NodoBinario<K, V>> pila, NodoBinario<K, V> nodoActual) {
        do {
            pila.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        } while (!NodoBinario.esNodoVacio(nodoActual));
        return pila;
    }

    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new LinkedList();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {//si n=0
            return;
        }
        recorridoEnInOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrden(nodoActual.getHijoDerecho(), recorrido);
    }

   
    public List<K> recorridoEnPreOrdenIterativo() {
        List<K> recorrido = new LinkedList();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
                if (!nodoActual.esVacioHijoDerecho()) {
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
            }
        }
        return recorrido;
    }

    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {//si n=0
            return;
        }
        recorrido.add(nodoActual.getClave());
        recorridoEnPreOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPreOrden(nodoActual.getHijoDerecho(), recorrido);
    }

    
    public List<K> recorridoEnPostOrdenIterativo() {
        List<K> recorrido = new ArrayList();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            NodoBinario<K, V> nodoActual = this.raiz;
            apilarParaPostOrden(pilaDeNodos, nodoActual);
            while (!pilaDeNodos.isEmpty()) {
                nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
                if (!pilaDeNodos.isEmpty()) {
                    NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                    if (!NodoBinario.esNodoVacio(nodoDelTope.getHijoDerecho())
                            && nodoDelTope.getHijoDerecho() != nodoActual) {
                        apilarParaPostOrden(pilaDeNodos,
                                nodoDelTope.getHijoDerecho());
                    }
                }
            }
        }
        return recorrido;
    }

    public Stack<NodoBinario<K, V>> apilarParaPostOrden(
            Stack<NodoBinario<K, V>> pila, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pila.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
        return pila;
    }

    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        //simulamos el n para caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {//si n=0
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrden(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos;
            colaDeNodos = new LinkedList<>();
            NodoBinario<K, V> nodoActual = this.raiz;
            colaDeNodos.offer(nodoActual);
            while (!colaDeNodos.isEmpty()) {
                nodoActual = colaDeNodos.poll();
                recorrido.add(nodoActual.getClave());
                if (nodoActual.getHijoIzquierdo() != null) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (nodoActual.getHijoDerecho() != null) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
        return recorrido;
    }

    public int cantidadDeHijosDerechosEnelArbolRec() {
        return cantidadDeHijosDerechosEnelArbolRec(this.raiz);
    }

    private int cantidadDeHijosDerechosEnelArbolRec(NodoBinario<K, V> nodoActual) {//perfecto
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int hijosDerechosDelHI = cantidadDeHijosDerechosEnelArbolRec(nodoActual.getHijoIzquierdo());
        int hijosDerechosDelHD = cantidadDeHijosDerechosEnelArbolRec(nodoActual.getHijoDerecho());
        if (!NodoBinario.esNodoVacio(nodoActual.getHijoDerecho())) {
            return hijosDerechosDelHI + hijosDerechosDelHD + 1;
        } else {
            return hijosDerechosDelHI + hijosDerechosDelHD;
        }
    }

    /*metodo que retorne si un arbol binario tiene solo nodos completos, es decir,
    nodos que tengan 2 hijos diferentes de vacio en el nivel n*/
    public boolean tieneNodosCompletosEnNivel(int nivelObjetivo) {
        return tieneNodosCompletosEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private boolean tieneNodosCompletosEnNivel(NodoBinario<K, V> nodoActual,
            int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return true;//si es vacio no se puede saber si tiene hijos completos
        }
        if (nivelActual == nivelObjetivo) {
            return !nodoActual.esNodoVacio(nodoActual.getHijoIzquierdo())
                    && !nodoActual.esNodoVacio(nodoActual.getHijoDerecho());
        }
        boolean completoPorIzquierda = this.tieneNodosCompletosEnNivel(nodoActual.getHijoIzquierdo(),
                nivelObjetivo, nivelActual + 1);
        boolean completoPorDerecha = this.tieneNodosCompletosEnNivel(nodoActual.getHijoDerecho(),
                nivelObjetivo, nivelActual + 1);

        return completoPorIzquierda && completoPorDerecha;
    }

}
