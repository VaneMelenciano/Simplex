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
public class Ecuacion{// extends Fraccion{
    Fraccion coe; //coeficiente
    Fraccion ind; //independiente
    // 3M+4 -> 3 es coeficiente, 4 es independiente
    public Ecuacion(Fraccion c, Fraccion i){
        this.coe = c;
        this.ind = i;
    }
}
