/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmax;

import objetos.Fraccion;
import objetos.Matriz;
import objetos.Simplex;
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
        //Matriz.imprimirMatriz(Matriz.matriz);
        System.out.println();
        Matriz.matrizF = Fraccion.convertirMatriz(Matriz.matriz);
        //Matriz.imprimirMatriz(Matriz.matrizF);
            ///DECIMALES
        //SimplexMax sm1 = new SimplexMax(2, 3, Matriz.matriz);
        //SimplexMax sm1 = new SimplexMax(2, 3, Matriz.matriz);
        //SimplexMax sm3 = new SimplexMax(4, 3, Matriz.matriz);
        /*SimplexMax sm3 = new SimplexMax(2, 3, Matriz.matriz);
        System.out.println();
        Matriz.imprimirMatriz(sm3.getMatrizDecimal());
        System.out.println();
        sm3.imprimirSolucion();*/
            //FRACCIONES
        SimplexMax sm3 = new SimplexMax(2,2, Matriz.matriz);
        //System.out.println("IMPRIME RES");
        //System.out.println(Matriz.imprimirMatriz(sm3.getMatrizFraccion(), 2, 3));
        System.out.println();
        System.out.println(sm3.getSolucion());
    }
    
}
