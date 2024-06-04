/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arbolesdebusqueda;

import com.mycompany.arbolesdebusqueda.excepciones.ExcepcionOrdenInvalido;

/**
 *
 * @author salet
 */
public class ArbolB<K extends Comparable<K>, V> extends ArbolMViasBusqueda<K, V> {
    private final int nroMaxDeDatos;
    private final int nroMinDeDatos;
    private final int nroMinDeHijos;

    public ArbolB() {
        super(); //orden = 3
        this.nroMaxDeDatos = 2;
        this.nroMinDeDatos = 1;
        this.nroMinDeHijos = 2;
    }

    public ArbolB(int n) throws ExcepcionOrdenInvalido{
        super(n);
        this.nroMaxDeDatos = n - 1;
        this.nroMinDeDatos = n / 2;
        this.nroMinDeHijos = this.nroMinDeDatos + 1;
    }

    @Override
    public void insertar(K clave, V valor) {
        if (clave == null) {
            throw new IllegalArgumentException("La clave a insertar no puede ser nula.");
        }
        if (valor == null) {
            throw new IllegalArgumentException("El valor a insertar no puede ser nulo.");
        }
        if (super.esArbolVacio()) {
            super.raiz = new NodoMVias<>(super.orden + 1, clave, valor);
            return;
        }
        super.raiz = insertarEnNodo(super.raiz, clave, valor);
        if (super.raiz.cantidadDeClavesNoVacias() > super.orden - 1) {
            NodoMVias<K, V> nuevaRaiz = new NodoMVias<>(super.orden);
            nuevaRaiz.setHijo(0, super.raiz);
            dividirNodo(nuevaRaiz);
            super.raiz = nuevaRaiz;
        }
    }

    private NodoMVias<K, V> insertarEnNodo(NodoMVias<K, V> nodoActual, K clave, V valor) {
        int posicion = buscarClave(nodoActual, clave);

        if (posicion != -1) {
            nodoActual.setClave(posicion, clave);
            nodoActual.setValor(posicion, valor);
            return nodoActual;
        }

        if (nodoActual.esHoja()) {
            super.insertarClaveYValorOrdenado(nodoActual, clave, valor);
        } else {
            int indiceDelHijo = buscarHijo(nodoActual, clave);
            NodoMVias<K, V> nodoHijo = nodoActual.getHijo(indiceDelHijo);
            NodoMVias<K, V> nodoHijoActualizado = insertarEnNodo(nodoHijo, clave, valor);
            nodoActual.setHijo(indiceDelHijo, nodoHijoActualizado);
        }

        if (nodoActual.estanClavesLlenas()) {
            return dividirNodo(nodoActual);
        }

        return nodoActual;
    }

    private NodoMVias<K, V> dividirNodo(NodoMVias<K, V> nodoActual) {
        K claveCentral = nodoActual.getClave(this.nroMinDeDatos);
        V valorCentral = nodoActual.getValor(this.nroMinDeDatos);

        NodoMVias<K, V> nuevoNodoDerecho = new NodoMVias<>(super.orden + 1);
        for (int i = this.nroMinDeDatos + 1; i < super.orden; i++) {
            nuevoNodoDerecho.setClave(i - this.nroMinDeDatos - 1, nodoActual.getClave(i));
            nuevoNodoDerecho.setValor(i - this.nroMinDeDatos - 1, nodoActual.getValor(i));
            nodoActual.setClave(i, null);
            nodoActual.setValor(i, null);
        }

        if (!nodoActual.esHoja()) {
            for (int i = this.nroMinDeHijos; i <= super.orden; i++) {
                nuevoNodoDerecho.setHijo(i - this.nroMinDeHijos, nodoActual.getHijo(i));
                nodoActual.setHijo(i, NodoMVias.nodoVacio());
            }
        }

        NodoMVias<K, V> nuevoNodoIzquierdo = new NodoMVias<>(super.orden + 1);
        for (int i = 0; i < this.nroMinDeDatos; i++) {
            nuevoNodoIzquierdo.setClave(i, nodoActual.getClave(i));
            nuevoNodoIzquierdo.setValor(i, nodoActual.getValor(i));
            nodoActual.setClave(i, null);
            nodoActual.setValor(i, null);
        }

        if (!nodoActual.esHoja()) {
            for (int i = 0; i <= this.nroMinDeHijos - 1; i++) {
                nuevoNodoIzquierdo.setHijo(i, nodoActual.getHijo(i));
                nodoActual.setHijo(i, NodoMVias.nodoVacio());
            }
        }

        NodoMVias<K, V> nuevoPadre = new NodoMVias<>(super.orden + 1, claveCentral, valorCentral);
        nuevoPadre.setHijo(0, nuevoNodoIzquierdo);
        nuevoPadre.setHijo(1, nuevoNodoDerecho);

        return nuevoPadre;
    }

    private int buscarHijo(NodoMVias<K, V> nodoActual, K clave) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            if (clave.compareTo(nodoActual.getClave(i)) < 0 || nodoActual.esClaveVacia(i)) {
                return i;
            }
        }
        return nodoActual.cantidadDeClavesNoVacias();
    }

    private int buscarClave(NodoMVias<K, V> nodoActual, K clave) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            if (clave.compareTo(nodoActual.getClave(i)) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public V eliminar(K clave) {
        if (clave == null) {
            throw new IllegalArgumentException("La clave a eliminar no puede ser nula.");
        }

        V valorEliminado = buscar(clave);
        if (valorEliminado == null) {
            return null;
        }

        raiz = eliminarRecursivo(raiz, clave);
        return valorEliminado;
    }

    private NodoMVias<K, V> eliminarRecursivo(NodoMVias<K, V> nodoActual, K clave) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return null;
        }

        int posicionDeClave = buscarPosicionDeClave(nodoActual, clave);

        if (posicionDeClave < nodoActual.cantidadDeClavesNoVacias() && clave.compareTo(nodoActual.getClave(posicionDeClave)) == 0) {
            if (nodoActual.esHoja()) {
                super.eliminarClaveDePosicion(nodoActual, posicionDeClave);
                return nodoActual;
            }

            K claveReemplazo = buscarClaveReemplazo(nodoActual.getHijo(posicionDeClave + 1));
            V valorReemplazo = buscar(claveReemplazo);

            nodoActual.setClave(posicionDeClave, claveReemplazo);
            nodoActual.setValor(posicionDeClave, valorReemplazo);

            nodoActual.setHijo(posicionDeClave + 1, eliminarRecursivo(nodoActual.getHijo(posicionDeClave + 1), claveReemplazo));
        } else {
            NodoMVias<K, V> nuevoHijo = eliminarRecursivo(nodoActual.getHijo(posicionDeClave), clave);
            nodoActual.setHijo(posicionDeClave, nuevoHijo);
        }

        if (nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
            if (nodoActual == raiz) {
                if (nodoActual.cantidadDeClavesNoVacias() == 0) {
                    raiz = NodoMVias.nodoVacio();
                }
            } else if (nodoActual.cantidadDeClavesNoVacias() < nroMinDeDatos) {
                return eliminarHijoVacio(nodoActual);
            }
        }

        return nodoActual;
    }

    private K buscarClaveReemplazo(NodoMVias<K, V> nodo) {
        while (!nodo.esHijoVacio(0)) {
            nodo = nodo.getHijo(0);
        }
        return nodo.getClave(0);
    }

    private NodoMVias<K, V> eliminarHijoVacio(NodoMVias<K, V> nodoPadre) {
        int posicionDeHijo = buscarPosicionDeHijo(nodoPadre, NodoMVias.nodoVacio());
        int hermanoIzquierdo = posicionDeHijo - 1;
        int hermanoDerecho = posicionDeHijo + 1;

        if (hermanoIzquierdo >= 0 && !nodoPadre.esHijoVacio(hermanoIzquierdo)) {
            NodoMVias<K, V> nodoHermano = nodoPadre.getHijo(hermanoIzquierdo);
            if (nodoHermano.cantidadDeClavesNoVacias() > nroMinDeDatos) {
                K clavePadre = nodoPadre.getClave(hermanoIzquierdo);
                V valorPadre = nodoPadre.getValor(hermanoIzquierdo);
                K claveHermano = nodoHermano.getClave(nodoHermano.cantidadDeClavesNoVacias() - 1);
                V valorHermano = nodoHermano.getValor(nodoHermano.cantidadDeClavesNoVacias() - 1);

                nodoPadre.setClave(hermanoIzquierdo, claveHermano);
                nodoPadre.setValor(hermanoIzquierdo, valorHermano);

                nodoHermano.setClave(nodoHermano.cantidadDeClavesNoVacias() - 1, clavePadre);
                nodoHermano.setValor(nodoHermano.cantidadDeClavesNoVacias() - 1, valorPadre);

                nodoHermano.setHijo(nodoHermano.cantidadDeClavesNoVacias(), nodoPadre.getHijo(hermanoIzquierdo + 1));
                nodoPadre.setHijo(hermanoIzquierdo + 1, NodoMVias.nodoVacio());

                return nodoPadre;
            }
        }

        if (hermanoDerecho <= nodoPadre.cantidadDeClavesNoVacias() && !nodoPadre.esHijoVacio(hermanoDerecho)) {
            NodoMVias<K, V> nodoHermano = nodoPadre.getHijo(hermanoDerecho);
            if (nodoHermano.cantidadDeClavesNoVacias() > nroMinDeDatos) {
                K clavePadre = nodoPadre.getClave(posicionDeHijo);
                V valorPadre = nodoPadre.getValor(posicionDeHijo);
                K claveHermano = nodoHermano.getClave(0);
                V valorHermano = nodoHermano.getValor(0);

                nodoPadre.setClave(posicionDeHijo, claveHermano);
                nodoPadre.setValor(posicionDeHijo, valorHermano);

                super.eliminarClaveDePosicion(nodoHermano, 0);

                nodoPadre.setHijo(posicionDeHijo + 1, nodoPadre.getHijo(posicionDeHijo));
                nodoPadre.setHijo(posicionDeHijo, NodoMVias.nodoVacio());

                return nodoPadre;
            }
        }

        if (hermanoIzquierdo >= 0 && !nodoPadre.esHijoVacio(hermanoIzquierdo)) {
            NodoMVias<K, V> nodoHermano = nodoPadre.getHijo(hermanoIzquierdo);
            K clavePadre = nodoPadre.getClave(hermanoIzquierdo);
            V valorPadre = nodoPadre.getValor(hermanoIzquierdo);

            nodoHermano.setClave(nodoHermano.cantidadDeClavesNoVacias(), clavePadre);
            nodoHermano.setValor(nodoHermano.cantidadDeClavesNoVacias(), valorPadre);

            for (int i = posicionDeHijo; i < nodoPadre.cantidadDeClavesNoVacias() - 1; i++) {
                nodoPadre.setClave(i, nodoPadre.getClave(i + 1));
                nodoPadre.setValor(i, nodoPadre.getValor(i + 1));
                nodoPadre.setHijo(i + 1, nodoPadre.getHijo(i + 2));
            }

            super.eliminarClaveDePosicion(nodoPadre, nodoPadre.cantidadDeClavesNoVacias() - 1);
            nodoPadre.setHijo(nodoPadre.cantidadDeClavesNoVacias(), NodoMVias.nodoVacio());

            return nodoPadre;
        }

        return null;
    }

    protected int buscarPosicionDeClave(NodoMVias<K, V> nodo, K clave) {
        int posicion = 0;
        while (posicion < nodo.cantidadDeClavesNoVacias() && clave.compareTo(nodo.getClave(posicion)) > 0) {
            posicion++;
        }
        return posicion;
    }

    private int buscarPosicionDeHijo(NodoMVias<K, V> nodo, NodoMVias<K, V> hijo) {
        for (int i = 0; i <= nodo.cantidadDeClavesNoVacias(); i++) {
            if (nodo.getHijo(i) == hijo) {
                return i;
            }
        }
        return -1;
    }
}
