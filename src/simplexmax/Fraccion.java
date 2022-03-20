/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmax;

/**
 *
 * @author Vanessa
 */
public class Fraccion {
    private int numerador;
    private int denominador;
    public Fraccion(int num, int den){
       this.numerador=num;
       this.denominador=den;
    }
    public Fraccion(){
    }
    public int mcd(){
       int num=getNumerador(), den=getDenominador(); // le asignamos a las variables el valor del numerador y denominador
       if(num==0){              // veirificamos si es igual a 0
         return 1;                       // si es igual a 0 retornara valor 1
       }else{                           // sino se realizara el siguiente proceso
         if(num<0){                      // verificamos que la variable num sea negativa  
             num = num * -1;            // si es negativa la convertimos a positivo  
         } 
         if(den<0){ 
           den = den * -1;            // realizamos el mismo proceso con la variable den  
         }
         if(den>num){                  // verificamos si la variable den es mayor a num 
             int aux=num;           // si es mayor hacemos un intercambio de valores
             num=den;               // debido a que la variable den no debe ser mayor a num
             den=aux;
         } 
         int mcd=1;               // incializamos la variable mcd en 1
         while(den!=0){          // verificamos si la variable den es diferente de 0
              mcd=den;       // asignamos a la variable mcd lo que contiene den
              den = num % den;   // a la variable den le asignamos el residuo o modulo entre num y den
              num = mcd;         // a la variable num le asignamos mcd

         }                        // el ciclo se repetira hasta que se cumpla la condicion inicial    
         return mcd;
        }
    }

    public void simplificar(){
      int mcd = mcd();
        setNumerador(getNumerador() / mcd);  
        setDenominador(getDenominador() / mcd);
      if(getNumerador()<0 && getDenominador()<0){
            setNumerador(getNumerador() * -1); 
            setDenominador(getDenominador() * -1); 
       }else if(getNumerador()>=0 && getDenominador()<0){
            setNumerador(getNumerador() * -1); 
            setDenominador(getDenominador() * -1); 
        }
    }

    private Fraccion suma(Fraccion f1){
        Fraccion aux = new Fraccion();
        aux.setNumerador(this.numerador * f1.getDenominador() + this.denominador * f1.getNumerador()); 
        aux.denominador = this.denominador * f1.getDenominador();
        aux.simplificar();  //se simplifica antes de devolverla                                                   
        return aux;
    }
    private Fraccion multiplicar(Fraccion f1){
        Fraccion aux = new Fraccion();
        aux.setNumerador(this.numerador * f1.getNumerador());
        aux.setDenominador(this.denominador * f1.getDenominador());
        aux.simplificar();  //se simplifica antes de devolverla
        return aux;
    }
    public Fraccion dividir(Fraccion f) {
        Fraccion aux = new Fraccion();
        aux.setNumerador(this.numerador * f.getDenominador());
        aux.setDenominador(this.numerador * f.getNumerador());
        aux.simplificar();  //se simplifica antes de devolverla
        return aux;
    }
    @Override
    public String toString(){
      if(getDenominador()!=0){
        simplificar();
        return getNumerador()+"/"+getDenominador(); 
      }else {

         return "El denominador debe ser distinto de 0";

      }
    }

    /**
     * @return the numerador
     */
    public int getNumerador() {
        return numerador;
    }

    /**
     * @param numerador the numerador to set
     */
    public void setNumerador(int numerador) {
        this.numerador = numerador;
    }

    /**
     * @return the denominador
     */
    public int getDenominador() {
        return denominador;
    }

    /**
     * @param denominador the denominador to set
     */
    public void setDenominador(int denominador) {
        this.denominador = denominador;
    }
    
}
