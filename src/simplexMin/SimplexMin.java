/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexMin;

import objetos.Fraccion;

/**
 *
 * @author Vanessa
 */
public class SimplexMin {
    int restricciones;
    int variables;
    Fraccion[][] matriz;
    Ecuacion[] ultimaFila;
    int filaP;
    int columnaP;
    Fraccion pivote;
    //Todas las funciones que tengan una F al final, son las que trabajan con la matriz de funciones
    public SimplexMin(int variables, int restricciones, Fraccion[][] matriz){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matriz = matriz;
        proceso();
    }

    private void proceso() {
       buscarPivote(); 
    }

    private void buscarPivote() {
        
    }
}
