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
        Matriz.matrizF = Matriz.convertirMatriz(Matriz.matriz);
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
        //int variables = 4, restricciones = 3; //Prueba 3
        //int variables = 3, restricciones = 3; //Prueba 4
        //int variables = 3, restricciones = 2; //Prueba 5
        //int variables = 2, restricciones = 3; //Prueba 6
        int variables = 2, restricciones = 2; //Prueba 7 y 9 y 11 y 12
        //int variables = 4, restricciones = 4; //Prueba 8
        //int variables = 6, restricciones = 6; //Prueba 10
        SimplexMax sm3 = new SimplexMax(variables, restricciones, Matriz.matrizF);
        //System.out.println("IMPRIME RES");
        //System.out.println(Matriz.imprimirMatriz(sm3.getMatrizFraccion(), 2, 3));
        System.out.println();
        System.out.println(sm3.getSolucion());
    }
    
}
