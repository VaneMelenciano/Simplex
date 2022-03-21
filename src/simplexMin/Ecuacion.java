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
    private Fraccion coe; //coeficiente
    private Fraccion ind; //independiente
    // 3M+4 -> 3 es coeficiente, 4 es independiente
    public Ecuacion(Fraccion c, Fraccion i){
        this.coe = c;
        this.ind = i;
    }
    public Ecuacion(){
    }

    @Override
    public String toString(){
        String res = "";
        boolean coeficiente=false;
      if(this.coe.getNumerador()!=0){
        res = this.coe.toString() + "M";
        if(this.ind.getNumerador()!=0){
            if(this.ind.getNumerador()>0)
                res += "+" + this.ind.toString();
            else
                res += this.ind.toString();
        }
        return res;
      }else{ //no hay M
          if(this.ind.getNumerador()!=0){
              return this.ind.toString();
          }else{
              return "0";
          }
      }
    }
    public static void toString(Ecuacion[] ecuacion){
        for(int i=0; i<ecuacion.length; i++){
            System.out.print(ecuacion[i].toString() + " ");
        }
    }
    /**
     * @return the coe
     */
    public Fraccion getCoe() {
        return coe;
    }

    /**
     * @param coe the coe to set
     */
    public void setCoe(Fraccion coe) {
        this.coe = coe;
    }

    /**
     * @return the ind
     */
    public Fraccion getInd() {
        return ind;
    }

    /**
     * @param ind the ind to set
     */
    public void setInd(Fraccion ind) {
        this.ind = ind;
    }
    
}
