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
        float aux = f.numerador / f.denominador;

        return Float.valueOf(String.format("%3.3f", aux));
    }
    public static Fraccion[][] convertirMatriz(float[][] matriz) { //convierte una matriz de flotantes a matriz de Fracciones
        Fraccion[][] aux = new Fraccion[matriz.length][matriz[0].length];
        for(int i=0; i<matriz.length; i++){
            for(int j=0; j<matriz[0].length; j++){
                aux[i][j] = convertir(matriz[i][j]);
            }
        }
        return aux;
    }
    public static float[][] convertirMatriz(Fraccion[][] matriz) { //convierte una matriz de flotantes a matriz de Fracciones
        float[][] aux = new float[matriz.length][matriz[0].length];
        for(int i=0; i<matriz.length; i++){
            for(int j=0; j<matriz[0].length; j++){
                aux[i][j] = convertir(matriz[i][j]);
            }
        }
        return aux;
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
