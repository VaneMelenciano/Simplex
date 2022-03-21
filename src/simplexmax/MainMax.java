/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmax;

import objetos.Fraccion;
import objetos.Matriz;
import simplexmax.SimplexMax;

/**
 *
 * @author Vanessa
 */
public class MainMax {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //MARICES FLOTANTES//
        /*Matriz.matriz = Matriz.leerArchivo();
        Matriz.imprimirMatriz(Matriz.matriz);
        System.out.println();
        SimplexMax sm = new SimplexMax(2, 3, Matriz.matriz);
        Matriz.imprimirMatriz(sm.matriz);
        System.out.println();
        Fraccion[][] nueva = Fraccion.convertirMatriz(sm.matriz);
        Matriz.imprimirMatriz(nueva);
        System.out.println();*/
        
        //Matriz de decial a fracciones//
        /*Matriz.matriz = Matriz.leerArchivo();
        Matriz.imprimirMatriz(Matriz.matriz);
        System.out.println();
        Fraccion[][] nueva = Fraccion.convertirMatriz(Matriz.matriz);
        Matriz.imprimirMatriz(nueva);
        System.out.println();*/
        
        //Lee matriz flotantes, cambia a fraccion y aplica simplex //
        Matriz.matriz = Matriz.leerArchivo();
        Matriz.imprimirMatriz(Matriz.matriz);
        System.out.println();
        Matriz.matrizF = Fraccion.convertirMatriz(Matriz.matriz);
        Matriz.imprimirMatriz(Matriz.matrizF);
        SimplexMax sm1 = new SimplexMax(2, 3, Matriz.matrizF);
        System.out.println();
        Matriz.imprimirMatriz(sm1.getMatrizF());
        System.out.println();
        sm1.imprimirSolucion();
    }
    
}
