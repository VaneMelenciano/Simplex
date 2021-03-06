/*
 * To change this license headerestricciones, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import simplexMin.SimplexMin;
import simplexmax.SimplexMax;

/**
 *
 * @author Vanessa
 */
public class Simplex {
    /*private TipoSimplex tipoSimplex;
    private TipoNumero tipoNumero;
    private int variables;
    private int restricciones;
    private float[][] matrizDecimal;
    private Fraccion[][] matrizFraccion;
    public Simplex(TipoSimplex tipoSimplex, TipoNumero tipoNumero, int variables, int restricciones, float[][] m){
        this.tipoNumero = tipoNumero;
        this.tipoSimplex = tipoSimplex;
        this.restricciones = restricciones;
        this.variables = variables;
        this.matrizDecimal = m;
    }
    public Simplex(TipoSimplex tipoSimplex, TipoNumero tipoNumero, int variables, int restricciones, Fraccion[][] m){
        this.tipoNumero = tipoNumero;
        this.tipoSimplex = tipoSimplex;
        this.restricciones = restricciones;
        this.variables = variables;
        this.matrizFraccion = m;
    }*/
    private static String solucionOptima;
    public static String simplex(TipoSimplex tipoSimplex, TipoNumero tipoNumero, int variables, int restricciones, Fraccion[][] matrizFraccion){
        String solucion = "";
        if(tipoSimplex.equals(TipoSimplex.Maximizacion) && tipoNumero.equals(tipoNumero.Fraccion)){ //maximizacion con fraccion
            SimplexMax sm = new SimplexMax(variables, restricciones, matrizFraccion);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else if(tipoSimplex.equals(TipoSimplex.Maximizacion)){ //maximizacion con decimal
            float[][] matriz = Matriz.convertirMatriz(matrizFraccion);
            SimplexMax sm = new SimplexMax(variables, restricciones, matriz);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else if(tipoSimplex.equals(TipoSimplex.Minimizacion) && tipoNumero.equals(tipoNumero.Fraccion)){ //Minimizacion con fraccion
            SimplexMin sm = new SimplexMin(variables, restricciones, matrizFraccion);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else{//minimizacion con decimal
            float[][] matriz = Matriz.convertirMatriz(matrizFraccion);
            SimplexMin sm = new SimplexMin(variables, restricciones, matriz);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }
        return solucion;
    }
    public static String simplex(TipoSimplex tipoSimplex, TipoNumero tipoNumero, int variables, int restricciones, float[][] matrizDecimal){
        String solucion = "";
        if(tipoSimplex.equals(TipoSimplex.Maximizacion) && tipoNumero.equals(tipoNumero.Fraccion)){ //maximizacion con fraccion
            Fraccion[][] mF = Matriz.convertirMatriz(matrizDecimal);
            SimplexMax sm = new SimplexMax(variables, restricciones, mF);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else if(tipoSimplex.equals(TipoSimplex.Maximizacion)){ //maximizacion con decimal
            SimplexMax sm = new SimplexMax(variables, restricciones, matrizDecimal);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else if(tipoSimplex.equals(TipoSimplex.Minimizacion) && tipoNumero.equals(tipoNumero.Fraccion)){ //Minimizacion con fraccion
            Fraccion[][] mF = Matriz.convertirMatriz(matrizDecimal);
            SimplexMin sm = new SimplexMin(variables, restricciones, mF);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else{//minimizacion con decimal
            SimplexMin sm = new SimplexMin(variables, restricciones, matrizDecimal);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }
        return solucion;
    }
    public static String simplex(TipoSimplex tipoSimplex, TipoNumero tipoNumero, int variables, int restricciones, int[][] matrizEntera){
        String solucion = "";
        Fraccion[][] mF = Matriz.convertirMatriz(matrizEntera);
        if(tipoSimplex.equals(TipoSimplex.Maximizacion) && tipoNumero.equals(tipoNumero.Fraccion)){ //maximizacion con fraccion
            SimplexMax sm = new SimplexMax(variables, restricciones, mF);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else if(tipoSimplex.equals(TipoSimplex.Maximizacion)){ //maximizacion con decimal
            SimplexMax sm = new SimplexMax(variables, restricciones, Matriz.convertirMatriz(mF));
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else if(tipoSimplex.equals(TipoSimplex.Minimizacion) && tipoNumero.equals(tipoNumero.Fraccion)){ //Minimizacion con fraccion
            SimplexMin sm = new SimplexMin(variables, restricciones, mF);
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }else{//minimizacion con decimal
            SimplexMin sm = new SimplexMin(variables, restricciones, Matriz.convertirMatriz(mF));
            solucion = sm.getSolucion();
            solucionOptima = sm.getSolucionOptima();
        }
        return solucion;
    }

    /**
     * @return the solucionOptima
     */
    public static String getSolucionOptima() {
        return solucionOptima;
    }
    
}
