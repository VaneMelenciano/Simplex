/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author Vanessa
 */
public class Fraccion {
    //si la fraccion es negativa, el numerador tiene el signo
    private int numerador;
    private int denominador;
    public Fraccion(int num, int den){
       this.numerador=num;
       this.denominador=den;
    }
    public Fraccion(){
    }
    public int mcd(){
       int num=getNumerador(), den=getDenominador(); 
       if(num==0){              
         return 1;                       
       }else{                           
         if(num<0){                     
             num = num * -1;            
         } 
         if(den<0){ 
           den = den * -1;           
         }
         if(den>num){                  
             int aux=num;           
             num=den;               
             den=aux;
         } 
         int mcd=1;              
         while(den!=0){          
              mcd=den;      
              den = num % den;   
              num = mcd;         

         }                          
         return mcd;
        }
    }

    public void simplificar(){
        if(getNumerador()==0) this.denominador=1;
      if(getDenominador()!=1 && getDenominador()!=0){
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
      
    }

    public Fraccion suma(Fraccion f1){
        Fraccion aux = new Fraccion();
        aux.setNumerador((this.numerador * f1.getDenominador()) + (this.denominador * f1.getNumerador())); 
        aux.denominador = this.denominador * f1.getDenominador();
        aux.simplificar();  //se simplifica antes de devolverla                                                   
        return aux;
    }
    public Fraccion multiplicar(Fraccion f1){
        Fraccion aux = new Fraccion();
        aux.setNumerador(this.numerador * f1.getNumerador());
        aux.setDenominador(this.denominador * f1.getDenominador());
        aux.simplificar();  //se simplifica antes de devolverla
        return aux;
    }
    public Fraccion dividir(Fraccion f) {
        Fraccion aux = new Fraccion();
        aux.setNumerador(this.numerador * f.getDenominador());
        aux.setDenominador(this.denominador * f.getNumerador());
        aux.simplificar();  //se simplifica antes de devolverla
        return aux;
    }
    public Fraccion dividirNegativo(Fraccion f) {
       Fraccion aux = new Fraccion();
        aux.setNumerador(this.numerador * f.getDenominador());
        aux.setDenominador(this.denominador * f.getNumerador());
        aux.simplificar();  //se simplifica antes de devolverla
        aux.setNumerador(aux.getNumerador()*(-1));
        return aux; 
    }
    public Fraccion dividirAbs(Fraccion f) { //Retortna el valor absoluto
        Fraccion aux = new Fraccion();
        aux.setNumerador(Math.abs(this.numerador * f.getDenominador()));
        aux.setDenominador(Math.abs(this.denominador * f.getNumerador()));
        aux.simplificar();  //se simplifica antes de devolverla
        return aux;
    }
    public static Fraccion convertir(float numero){ //pasa un decimal a fraccion
        //2.5 -> (2.5*10)/10  -> 5/2
        //0.1 -> 1/10
        String str = String.valueOf(numero);
        //int intNumero = Integer.parseInt(str.substring(0, str.indexOf('.'))); //parte entera del numero
        //int decInt = Integer.parseInt(str.substring(str.indexOf('.') + 1)); //parte decimal del numero
        String decimal = str.substring(str.indexOf('.') + 1); //parte decimal del numero
        int t = decimal.length()-1;
        String aux = "1";
        while(t>=0){
            aux+="0";
            t--;
        }
        Fraccion auxF = new Fraccion();
        auxF.setNumerador((int) (numero*Integer.parseInt(aux)));
        auxF.setDenominador(Integer.parseInt(aux));
        auxF.simplificar();
        return auxF;
    }
    public static float convertir(Fraccion f){ //pasa un fraccion a decimal
        float aux = (float)((float)f.numerador / f.denominador);

        return aux;
        //return Float.valueOf(String.format("%3.3f", aux));
    }
    public boolean compararMayor(Fraccion f) { //comparar si la fraccion actual es mayor que la recibida
        /*if(this.numerador<0 && f.numerador<0) return ((numerador*f.getDenominador())<(f.getNumerador()*this.denominador));
        else if(this.numerador<0 && f.numerador>0) return true;
        else if(this.numerador>0 && f.numerador<0) return false;*/
        //System.out.println("Comparar: " + numerador*f.getDenominador() + " con " + f.getNumerador()*this.denominador);
        //return ((numerador*f.getDenominador())>(f.getNumerador()*this.denominador));
        //System.out.println("Comparar: " + ((float)this.numerador/this.denominador ) + " " + ((float)f.getNumerador()*f.getDenominador()));
        return (((float)this.numerador/this.denominador )>((float)f.getNumerador()/f.getDenominador()));
    }
    
    @Override
    public String toString(){
        simplificar();
        if(getDenominador()!=1 && getNumerador()!=0 && getDenominador()!=0)
            return String.valueOf(getNumerador()) + "/" + String.valueOf(getDenominador());
        else if(getDenominador()==0)
            return "0"; 
        else //numero enteros
             return String.valueOf(getNumerador()); 
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
