/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexMin;

import objetos.Fraccion;
import objetos.Matriz;
import simplexMin.SimplexMin;
import simplexmax.SimplexMax;

/**
 *
 * @author Vanessa
 */
public class MainMin {

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
        
        //Lee matrzi flotantes, cambia a fraccion y aplica simplex //
        /*Matriz.matriz = Matriz.leerArchivo();
        Matriz.imprimirMatriz(Matriz.matriz);
        System.out.println();
        Matriz.matrizF = Fraccion.convertirMatriz(Matriz.matriz);
        Matriz.imprimirMatriz(Matriz.matrizF);
        SimplexMax sm1 = new SimplexMax(2, 3, Matriz.matrizF);
        System.out.println();
        Matriz.imprimirMatriz(sm1.getMatrizF());
        System.out.println();
        sm1.imprimirSolucion();*/
        
        //Prueba 1
        
        /*Ecuacion e1 = new Ecuacion(new Fraccion(0,1), new Fraccion(-2,1));
        Ecuacion e2 = new Ecuacion(new Fraccion(0,1), new Fraccion(-4,1));
        
        Ecuacion e3 = new Ecuacion(new Fraccion(0,1), new Fraccion(0,1));
        Ecuacion e4 = new Ecuacion(new Fraccion(0,1), new Fraccion(0,1));
        Ecuacion e5 = new Ecuacion(new Fraccion(0,1), new Fraccion(0,1));
        
        Ecuacion e6 = new Ecuacion(new Fraccion(-1,1), new Fraccion(0,1));
        Ecuacion e7 = new Ecuacion(new Fraccion(-1,1), new Fraccion(0,1));
        Ecuacion e8 = new Ecuacion(new Fraccion(-1,1), new Fraccion(0,1));
        
        Ecuacion e9 = new Ecuacion(new Fraccion(0,1), new Fraccion(0,1));
        Ecuacion[] e = new Ecuacion[]{e1, e2, e3, e4, e5, e6, e7, e8, e9};*/
        
        //int variables = 4, restricciones = 3; //Prueba 3 NO SALE
        //int variables = 3, restricciones = 3; //Prueba 4
        //int variables = 3, restricciones = 2; //Prueba 5, 1.3, 1.4
        //int variables = 2, restricciones = 3; //Prueba 6 y 1.1, 1.5
        int variables = 2, restricciones = 2; //Prueba 7 y 9 y 11 y 12
        //int variables = 4, restricciones = 4; //Prueba 8
        //int variables = 6, restricciones = 6; //Prueba 10 NO SALE
        
        Matriz.matriz = Matriz.leerArchivo();
        //System.out.println("\n ORIGINAL");
        Fraccion[][] nueva = Matriz.convertirMatriz(Matriz.matriz);
         System.out.println(Matriz.imprimirMatrizMinimizacion(nueva, variables, restricciones) + "\n");
        
        
        SimplexMin sm = new SimplexMin(variables, restricciones, nueva);
        //SimplexMin sm = new SimplexMin(variables, restricciones, Matriz.matriz);
        //System.out.println("\n ULTIMA");
        //System.out.println(Matriz.imprimirMatrizMinimizacion(sm.getMatrizFraccion(), variables, restricciones));
        //Ecuacion.toString(sm.getUltimaFila());
        //System.out.println("\n SOLUCION");
        System.out.println(sm.getSolucion());
        //sm.imprimirSolucion();
    }
    
}
