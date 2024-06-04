/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arbolesdebusqueda;

/**
 *
 * @author salet
 */
public class FechaConPrioridad implements Comparable<FechaConPrioridad>{
    private String fecha;
    private int prioridad;
    
    FechaConPrioridad(String fecha, int prioridad){
        this.fecha=fecha;
        this.prioridad=prioridad;
    }

    @Override
    public int compareTo(FechaConPrioridad otro) {
        int comparacionFecha = this.fecha.compareTo(otro.fecha);
        if(comparacionFecha!=0) return comparacionFecha;
        return Integer.compare(this.prioridad, otro.prioridad);
    }
    
    public String toString() {
        return "fecha: " + fecha +
                ", prioridad: " + prioridad +"\n";
    }
}
