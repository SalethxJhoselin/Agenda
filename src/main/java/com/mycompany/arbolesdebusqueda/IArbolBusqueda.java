/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.arbolesdebusqueda;

import com.mycompany.arbolesdebusqueda.excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author salet
 */
public interface IArbolBusqueda<K extends Comparable<K>, V> {

    void insertar(K clave, V valor);

    V eliminar(K clave);

    V buscar(K clave);

    boolean contiene(K clave);

    int size();

    int altura();

    void vaciar();

    boolean esArbolVacio();

    int nivel();

    List<K> recorridoEnInOrden();

    List<K> recorridoEnPreOrden();

    List<K> recorridoEnPostOrden();

    List<K> recorridoPorNiveles();

    public static void main(String[] args) throws ExcepcionOrdenInvalido {
        System.out.println("Hello World!");
        Scanner entrada = new Scanner(System.in);
        String orden = entrada.next();
        ArbolMViasBusqueda ArbolMVias = new ArbolMViasBusqueda< >(Integer.valueOf(orden));
        ArbolMVias.insertarI(10, "dato1");
        ArbolMVias.insertarI(4, "dato2");
        ArbolMVias.insertarI(6, "dato3");
        ArbolMVias.insertarI(8, "dato4");
        ArbolMVias.insertarI(111, "dato5");
        ArbolMVias.insertarI(5, "dato6");
        ArbolMVias.insertarI(30, "dato7");
        ArbolMVias.insertarI(20, "dato8");

        //String claveABuscar = (String) ArbolMVias.buscar(4);

        List<Integer> recorrido = new ArrayList<>();
        recorrido = ArbolMVias.recorridoPorNiveles();
        System.out.println("recorrido por niveles: "+recorrido );
        /*ArbolBinarioBusqueda ArbolBinario = new ArbolBinarioBusqueda();
        ArbolBinario.insertarRecursivo(69, "A");
        ArbolBinario.insertarRecursivo(60, "B");
        ArbolBinario.insertarRecursivo(80, "C");
        ArbolBinario.insertarRecursivo(400, "D");
        ArbolBinario.insertarRecursivo(68, "E");
        ArbolBinario.insertarRecursivo(72, "F");
        ArbolBinario.insertarRecursivo(100, "G");
        ArbolBinario.insertarRecursivo(30, "H");
        ArbolBinario.insertarRecursivo(50, "I");
        ArbolBinario.insertarRecursivo(64, "J");
        ArbolBinario.insertarRecursivo(70, "K");
        ArbolBinario.insertarRecursivo(78, "L");
        ArbolBinario.insertarRecursivo(90, "H");
        ArbolBinario.insertarRecursivo(200, "I");
        ArbolBinario.insertarRecursivo(39, "J");
        ArbolBinario.insertarRecursivo(44, "K");
        ArbolBinario.insertarRecursivo(54, "L");
        ArbolBinario.insertarRecursivo(76, "K");
        ArbolBinario.insertarRecursivo(79, "L");
        boolean a = ArbolBinario.contiene(143);
        System.out.println(a);
        List recorridoPorNiveles = ArbolBinario.recorridoPorNiveles();
        System.out.println("recorrido por Niveles           :" + recorridoPorNiveles);

        /*List recorridoPreOrden = ArbolBinario.recorridoEnPreOrdenIterativo();
        System.out.println("recorrido en preOrdenIterativo  :" + recorridoPreOrden);

        List recorridoPreOrdenRec = ArbolBinario.recorridoEnPreOrdenRecursivo();
        System.out.println("recorrido en preOrdenRecursivo  :" + recorridoPreOrdenRec);

        List recorridoInOrden = ArbolBinario.recorridoEnInOrdenIterativo();
        System.out.println("recorrido en InOrdenIterativo  :" + recorridoInOrden);

        List recorridoInOrdenRec = ArbolBinario.recorridoEnInOrdenRecursivo();
        System.out.println("recorrido en InOrdeRecursivo  :" + recorridoInOrdenRec);

        List recorridoPostOrden = ArbolBinario.recorridoEnPostOrdenIterativo();
        System.out.println("recorrido en postOrdenIterativo  :" + recorridoPostOrden);

        List recorridoPostOrdenRec = ArbolBinario.recorridoEnPostOrdenRecursivo();
        System.out.println("recorrido en PostOrdenRecursivo  :" + recorridoPostOrdenRec);
        
        /*int alturaRec=ArbolBinario.alturaRec();
        System.out.println("alturaRec del arbol: "+alturaRec);
        int alturaIt=ArbolBinario.alturaIt();
        System.out.println("alturaIt del arbol: "+alturaIt);
        
        int nivel=ArbolBinario.nivel();
        System.out.println("nivel del arbol: "+nivel);
        
        int cantHijosDerechos=ArbolBinario.cantidadDeHijosDerechosEnelArbolRec();
        System.out.println("cantHijosDerechos: "+cantHijosDerechos);
        
        String v=(String) ArbolBinario.eliminar(100);
        System.out.println("claveEliminada"+v);
        
        List recorridoInOrdenRecu = ArbolBinario.recorridoEnInOrdenRecursivo();
        System.out.println("recorrido en InOrdeRecursivo ver si elimino :" + recorridoInOrdenRecu);
        int sizeRec=ArbolBinario.sizeRec();
        System.out.println("cantidad de nodos en el arbol, recursivo "+sizeRec);
        
        int nivelDelNodo=ArbolBinario.nivelDelNodo(20);
        System.out.println("nivel en el que se encuentra un nodo "+nivelDelNodo);*/

 /* AVL ArbolBinarioAVL = new AVL();
        
        ArbolBinarioAVL.insertar(69, "A");
        ArbolBinarioAVL.insertar(60, "B");
        ArbolBinarioAVL.insertar(80, "C");
        ArbolBinarioAVL.insertar(40, "D");
        ArbolBinarioAVL.insertar(68, "E");
        ArbolBinarioAVL.insertar(72, "F");
        ArbolBinarioAVL.insertar(100, "G");
        ArbolBinarioAVL.insertar(30, "H");
        ArbolBinarioAVL.insertar(50, "I");
        ArbolBinarioAVL.insertar(64, "J");
        ArbolBinarioAVL.insertar(70, "K");
        ArbolBinarioAVL.insertar(78, "L");
        ArbolBinarioAVL.insertar(90, "H");
        ArbolBinarioAVL.insertar(200, "I");
        ArbolBinarioAVL.insertar(39, "J");
        ArbolBinarioAVL.insertar(44, "K");
        ArbolBinarioAVL.insertar(54, "L");
        ArbolBinarioAVL.insertar(76, "K");
        ArbolBinarioAVL.insertar(79, "L");*/
 /*
        ArbolBinarioBusqueda ArbolBinarioAVLPrueba = new ArbolBinarioBusqueda();
        
        ArbolBinarioAVLPrueba=ArbolBinarioAVL;
        ArbolBinarioAVLPrueba.eliminar(60);
        List recorridoInOrdenAVL=ArbolBinarioAVLPrueba.recorridoEnInOrdenRecursivo();
        System.out.println("recorridoInOrdenAVL: "+recorridoInOrdenAVL);*/
    }
}
