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
public class Simplex {
    private TipoSimplex tp;
    private TipoNumero tn;
    private int variables;
    private int restricciones;
    private float[][] matriz;
    public Simplex(TipoSimplex ts, TipoNumero tn, int v, int r, float[][] m){
        this.tn = tn;
        this.tp = ts;
        this.restricciones = r;
        this.variables = v;
        this.matriz = m;
    }
    
    public static String simplex(TipoSimplex ts, TipoNumero tn, int v, int r, float[][] mD){
        String solucion = "";
        if(ts.equals(TipoSimplex.Maximizacion) && tn.equals(tn.Fraccion)){ //maximizacion con fraccion
            Fraccion[][] mF = Fraccion.convertirMatriz(mD);
            SimplexMax sm = new SimplexMax(v, r, mF);
            solucion = sm.getSolucion();
        }else if(ts.equals(TipoSimplex.Maximizacion)){ //maximizacion con decimal
            SimplexMax sm = new SimplexMax(v, r, mD);
            solucion = sm.getSolucion();
        }else if(ts.equals(TipoSimplex.Minimizacion) && tn.equals(tn.Fraccion)){ //Minimizacion con fraccion
            
        }else{//minimizacion con decimal
            
        }
        return solucion;
    }
}
