/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import simplexmax.SimplexMax;

/**
 *
 * @author Vanessa
 */
public class MainPruebas {
    public static void main(String[] args) {
        //MARICES FLOTANTES//
        Matriz.matriz = Matriz.leerArchivo();  //matriz hecha a partir de las ecuaciones, con enteros y decimales
        int variables = 2, restricciones = 3;
        System.out.println(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Fraccion, variables, restricciones, Matriz.matriz));
        //Falta sustituir los valores de las solucion optima en 
        //función objetivo y restricciones
    }
}
