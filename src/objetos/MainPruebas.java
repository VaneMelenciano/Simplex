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
        int variables = 3, restricciones = 2; //5, 6, 7, 8, min
        //int variables = 2, restricciones = 3; //1, 2, 3, 4, 9, 10, min
        
        //int variables = 2, restricciones = 3; //1, 2, 3, 4, 5, 7, 8x
        //int variables = 3, restricciones = 2; //5
        System.out.println(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Decimal, variables, restricciones, Matriz.matriz));
        System.out.println(Simplex.getSolucionOptima());
        //Falta sustituir los valores de las solucion optima en 
        //función objetivo y restricciones
        /*Matriz.matriz = Matriz.leerArchivo();
        Matriz.imprimirMatriz(Matriz.convertirMatrizRecibiendoDenominadorNumerador(Matriz.matriz));
        */
    }
}
