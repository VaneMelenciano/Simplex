/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexMin;

import java.util.Arrays;
import objetos.Fraccion;
import objetos.Matriz;

/**
 *
 * @author Vanessa
 */
/*
Recibe una matriz donde las últimas dos filas,
son el coeficiente y valor independiente de una ecuación 
(la que tiene M)
la penultima fila es el coeficiente
la última es el valor independiente
 3M+4 -> 3 es coeficiente, 4 es independiente
*/
public class SimplexMin {
    private int restricciones;
    private int variables;
    private Fraccion[][] matrizFraccion;
    private float[][] matrizDecimal;
    private int filapivote;
    private int columnaPivote;
    private Fraccion pivoteFraccion;
    private float pivoteDecimal;
    private Fraccion resultadoFraccion; //solucion z con fraccion
    private float resultadoDecimal; //solucion z con fraccion
    private Fraccion[] resultadoVariablesFraccion; //solucion x con fraccion
    private float[] resultadoVariablesDecimal; //solucion x con fraccion
    private String solucion="";
    private String solucionOptima="";
    //Todas las funciones que tengan una F al final, son las que trabajan con la matrizFraccion de funciones
    public SimplexMin(int variables, int restricciones, Fraccion[][] matriz){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matrizFraccion = matriz;
        procesoFraccion();
    }
    
    public SimplexMin(int variables, int restricciones, float[][] matriz){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matrizDecimal = matriz;
        procesoDecimal();
    }

    private void procesoFraccion() {
        int i=1;
        solucion += "Tabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatrizMinimizacion(this.matrizFraccion, this.variables, this.restricciones);
        
        multiplicarMFraccion(); 
        //Después de multiplicar por M
        solucion += "\nTabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatrizMinimizacion(this.matrizFraccion, this.variables, this.restricciones);
        
        buscarPivoteFraccion();
        filaPivoteFraccion();
        columnaPivoteivoteFraccion();
        //Primera iteración
        solucion += "\nTabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatrizMinimizacion(this.matrizFraccion, this.variables, this.restricciones);
        
        while(!verificarMFraccion()){ //miestras sigan existiendo M última fila, donde están las constantes
            solucion += "\nTabla" + i + ": \n"; i++;
            //Mientras la fila de coeficientes en la última columna, no tenga 0
            buscarPivoteFraccion();
            filaPivoteFraccion();
            columnaPivoteivoteFraccion();
            solucion += Matriz.imprimirMatrizMinimizacion(this.matrizFraccion, this.variables, this.restricciones);
        }
        buscarSolucionFraccion();
    }

    private void procesoDecimal() {
        int i=1;
        solucion += "Tabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatrizMinimizacion(this.matrizDecimal, this.variables, this.restricciones);
        multiplicarMDecimal(); 
        //Después de multiplicar por M
        solucion += "\nTabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatrizMinimizacion(this.matrizDecimal, this.variables, this.restricciones);
        buscarPivoteDecimal();
        filaPivoteDecimal();
        columnaPivoteivoteDecimal();
        //Primera iteración
        solucion += "\nTabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatrizMinimizacion(this.matrizDecimal, this.variables, this.restricciones);
        while(!verificarMDecimal()){ //miestras sigan existiendo M última fila, donde están las constantes
            solucion += "\nTabla" + i + ": \n"; i++;
            //Mientras la fila de coeficientes en la última columna, no tenga 0
            buscarPivoteDecimal();
            filaPivoteDecimal();
            columnaPivoteivoteDecimal();
            solucion += Matriz.imprimirMatrizMinimizacion(this.matrizDecimal, this.variables, this.restricciones);
        }
        buscarSolucionDecimal();
    }
    
    private void multiplicarMFraccion() { //Multiplica por m cada numero de cada columna y lo suma al último
        for(int columna=0; columna<this.matrizFraccion[0].length; columna++){
            Fraccion resultado = this.matrizFraccion[0][columna];//coeficiente de la ecuacion
                for(int fila=1; fila<this.getMatrizFraccion().length-1; fila++){ //dice que fila es
                    resultado = resultado.suma(this.matrizFraccion[fila][columna]);
                } 
               this.matrizFraccion[this.matrizFraccion.length-2][columna] = resultado;//fila de las constantes
               
        }
        
    }
    
    private void multiplicarMDecimal() { //Multiplica por m cada numero de cada columna y lo suma al último
        for(int columna=0; columna<this.matrizDecimal[0].length; columna++){
            float resultado = this.matrizDecimal[0][columna];
                for(int fila=1; fila<this.matrizDecimal.length-1; fila++){ //dice que fila es
                    resultado +=this.matrizDecimal[fila][columna];
                } 
               this.matrizDecimal[this.matrizDecimal.length-2][columna] = resultado;//fila de las constantes
               
        }
    }
    
    private void buscarPivoteFraccion() {
        //Columna: se elige con la ecuación que tenga mayor absoluto coeficiente de la última fila
        int numFilas = this.getMatrizFraccion().length;
        int filaCoeficientes = this.matrizFraccion.length-2;
        int columna=0;
        Fraccion mayor = new Fraccion(this.matrizFraccion[filaCoeficientes][0].getNumerador(), this.matrizFraccion[filaCoeficientes][0].getDenominador());
        this.columnaPivote=columna; //posición del valor absoluto, columna pivoteFraccion
        for(int i=1; i<this.variables+this.restricciones; i++){ //recorre las columnas solo en la fila de coeficientes
            Fraccion aux = new Fraccion(this.matrizFraccion[filaCoeficientes][i].getNumerador(), this.matrizFraccion[filaCoeficientes][i].getDenominador());
            if(aux.compararMayor(mayor)){ //Math.abs(this.matrizFraccion[ultima][j])>mayor
                mayor = aux;
                this.columnaPivote=i;
            }
        }
        
        //Razón de desplazamiento //CHECAR
        int numColumnas = this.matrizFraccion[0].length;
        int fila=0; //recorre las filas de la columna pivoteFraccion
        while(this.matrizFraccion[fila][columnaPivote].getNumerador()<=0 && fila < filaCoeficientes){
            fila++;
        }
           Fraccion menor = this.matrizFraccion[fila][numColumnas-1].dividir(this.matrizFraccion[fila][columnaPivote]); //buscar la razón de desplazamineto (menor)
            this.filapivote=fila;
            for(int i=fila+1; i<filaCoeficientes; i++){//columna de constantes
                if(this.getMatrizFraccion()[i][columnaPivote].getNumerador()>0 && this.getMatrizFraccion()[i][numColumnas-1].getNumerador()>=0){
                    //filaEncontrada=true;
                    Fraccion aux = this.getMatrizFraccion()[i][numColumnas-1].dividir(this.getMatrizFraccion()[i][columnaPivote]);
                     if(menor.compararMayor(aux)){ //>0 para que solo eliga positivos//aux < menor -> menor > aux
                         menor = aux;
                         this.filapivote=i;
                     } 
                 }
            }
            actualizarPivoteFraccion();
    }
    
     private void buscarPivoteDecimal() {
        //Columna: se elige con la ecuación que tenga mayor absoluto coeficiente de la última fila
        int numFilas = this.matrizDecimal.length;
        int filaCoeficientes = this.matrizDecimal.length-2;
        int columna=0;
        float mayor = this.matrizDecimal[filaCoeficientes][0];
        this.columnaPivote=columna; //posición del valor absoluto, columna pivoteFraccion
        for(int i=1; i<this.variables+this.restricciones; i++){ //recorre las columnas solo en la fila de coeficientes
            float aux = this.matrizDecimal[filaCoeficientes][i];
            if(this.matrizDecimal[filaCoeficientes][i]>mayor){
                mayor = aux;
                this.columnaPivote=i;
            }
        }
        
        //Razón de desplazamiento
        int numColumnas = this.matrizDecimal[0].length;
        int fila=0; //recorre las filas de la columna pivoteFraccion
        while(this.matrizDecimal[fila][columnaPivote]<=0 && fila < filaCoeficientes){
            fila++;
        }
           float menor = this.matrizDecimal[fila][numColumnas-1]/(this.matrizDecimal[fila][columnaPivote]); //buscar la razón de desplazamineto (menor)
           this.filapivote=fila;
            for(int i=fila+1; i<filaCoeficientes; i++){//columna de constantes
                if(this.matrizDecimal[i][columnaPivote]>0 && this.matrizDecimal[i][numColumnas-1]>=0){
                    //filaEncontrada=true;
                    float aux = this.matrizDecimal[i][numColumnas-1]/(this.matrizDecimal[i][columnaPivote]);
                    if(menor>aux){ //>0 para que solo eliga positivos//aux < menor -> menor > aux
                         menor = aux;
                         this.filapivote=i;
                     } 
                 }
            }
            actualizarPivoteDecimal();
    }
    
    private void filaPivoteFraccion(){
        //número que multiplicado por el pivoteFraccion de como resultado 1
        //O sea, el reciproco del pivoteFraccion
        //float reciproco = (float) Math.pow(pivoteFraccion, -1.0);
        Fraccion reciproco = new Fraccion(this.pivoteFraccion.getDenominador(), this.pivoteFraccion.getNumerador());
        //el reciproco afecta a toda la fila pivoteFraccion
        for(int columna=0; columna<this.matrizFraccion[0].length; columna++){ //recorre la filapivote
            this.matrizFraccion[this.filapivote][columna]= this.matrizFraccion[this.filapivote][columna].multiplicar(reciproco);
        }
        actualizarPivoteFraccion();
    }
    
    private void filaPivoteDecimal(){
        //número que multiplicado por el pivoteFraccion de como resultado 1
        //O sea, el reciproco del pivoteFraccion
        float reciproco = (float) Math.pow(pivoteDecimal, -1.0);
        //Fraccion reciproco = new Fraccion(this.pivoteFraccion.getDenominador(), this.pivoteFraccion.getNumerador());
        //el reciproco afecta a toda la fila pivoteFraccion
        for(int columna=0; columna<this.matrizDecimal[0].length; columna++){ //recorre la filapivote
            this.matrizDecimal[this.filapivote][columna]*=reciproco;
        }
        actualizarPivoteDecimal();
    }
    
    private void columnaPivoteivoteFraccion(){ //hace a toda la columna pivoteFraccion 0, menos al pivoteFraccion
        for(int fila=0; fila<this.matrizFraccion.length-2; fila++){ //recorre la columa pivoteFraccion de la matrizFraccion principal (sin contar ultima fila)
          if(fila!=this.filapivote){ //para que no aplique esto a la fila pivoteFraccion
            Fraccion elemento = this.getMatrizFraccion()[fila][this.columnaPivote];
            Fraccion x = elemento.dividirNegativo(this.pivoteFraccion); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivoteFraccion); //buscar un numero que multiplicado por el pivoteFraccion 
            // y sumado al elemento de la columna pivoteFraccion de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int columna=0; columna<this.getMatrizFraccion()[0].length; columna++){ //recorre la fila que se ve afectada por x
                //this.matrizFraccion[i][j] += (x*this.matrizFraccion[this.filapivote][j]); //el número (x) se multiplica
                this.matrizFraccion[fila][columna] =  this.getMatrizFraccion()[fila][columna].suma(this.getMatrizFraccion()[this.filapivote][columna].multiplicar(x));//el número (x) se multiplica
                //por el elemento que esté en la fila pivoteFraccion, el la misma columa (j) del elemento afectado
            }
          }
        }
        //elemento de la columa pivoteFraccion de la última fila
        Fraccion elementoCoeficiente = this.matrizFraccion[this.matrizFraccion.length-2][this.columnaPivote];
        Fraccion elementoIndependiente = this.matrizFraccion[this.matrizFraccion.length-1][this.columnaPivote];
        //(-1)*(elemento/this.pivoteFraccion); //buscar un numero que multiplicado por el pivoteFraccion y sumado al elemento de la columna pivoteFraccion de como resultado 0
        Fraccion x1 = elementoCoeficiente.dividirNegativo(this.pivoteFraccion);
        Fraccion x2 = elementoIndependiente.dividirNegativo(this.pivoteFraccion);
        //Ecuacion x = new Ecuacion(x1, x2);
        //El número encontrado (x) afecta a toda la fila (ultima fila en este caso)
            for(int columna=0; columna<this.matrizFraccion[0].length; columna++){ 
                /*x*P + E*/
                //coeficientes
                this.matrizFraccion[this.matrizFraccion.length-2][columna] = this.matrizFraccion[this.matrizFraccion.length-2][columna].suma(x1.multiplicar(this.matrizFraccion[this.filapivote][columna]));
                //Independientes
                this.matrizFraccion[this.matrizFraccion.length-1][columna] = this.matrizFraccion[this.matrizFraccion.length-1][columna].suma(x2.multiplicar(this.matrizFraccion[this.filapivote][columna]));
                //por el elemento que esté en la fila pivoteFraccion, el la misma columa (j) del elemento afectado
            }
    }
    
    private void columnaPivoteivoteDecimal(){ //hace a toda la columna pivoteFraccion 0, menos al pivoteFraccion
        for(int fila=0; fila<this.matrizDecimal.length-2; fila++){ //recorre la columa pivoteFraccion de la matrizFraccion principal (sin contar ultima fila)
          if(fila!=this.filapivote){ //para que no aplique esto a la fila pivoteFraccion
            float elemento = this.matrizDecimal[fila][this.columnaPivote];
            float x = (-1)*(elemento/this.pivoteDecimal); //buscar un numero que multiplicado por el pivoteFraccion 
              // y sumado al elemento de la columna pivoteFraccion de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int columna=0; columna<this.matrizDecimal[0].length; columna++){ //recorre la fila que se ve afectada por x
                this.matrizDecimal[fila][columna] += (x*this.matrizDecimal[this.filapivote][columna]); //el número (x) se multiplica
                //por el elemento que esté en la fila pivoteFraccion, el la misma columa (j) del elemento afectado
            }
          }
        }
        //elemento de la columa pivoteFraccion de la última fila
        float elementoCoeficiente = this.matrizDecimal[this.matrizDecimal.length-2][this.columnaPivote];
        float elementoIndependiente = this.matrizDecimal[this.matrizDecimal.length-1][this.columnaPivote];
        //(-1)*(elemento/this.pivoteFraccion); //buscar un numero que multiplicado por el pivoteFraccion y sumado al elemento de la columna pivoteFraccion de como resultado 0
        //Fraccion x1 = elementoCoeficiente.dividirNegativo(this.pivoteFraccion);
        //Fraccion x2 = elementoIndependiente.dividirNegativo(this.pivoteFraccion);
        float x1 = (-1)*(elementoCoeficiente / this.pivoteDecimal);
        float x2 = (-1)*(elementoIndependiente/this.pivoteDecimal);
         //El número encontrado (x) afecta a toda la fila (ultima fila en este caso)
            for(int columna=0; columna<this.matrizDecimal[0].length; columna++){ 
                /*x*P + E*/
                //coeficientes
                this.matrizDecimal[this.matrizDecimal.length-2][columna] += x1 * (this.matrizDecimal[this.filapivote][columna]);
                //Independientes
                this.matrizDecimal[this.matrizDecimal.length-1][columna] += (x2 * (this.matrizDecimal[this.filapivote][columna]));
                //por el elemento que esté en la fila pivoteFraccion, el la misma columa (j) del elemento afectado
            }
    }
    
    private void actualizarPivoteFraccion() {
        this.pivoteFraccion = this.matrizFraccion[this.filapivote][this.columnaPivote];
    }
    
    private void actualizarPivoteDecimal() {
        this.pivoteDecimal = this.matrizDecimal[this.filapivote][this.columnaPivote];
    }
    
     private boolean verificarMFraccion() {
         if(this.matrizFraccion[this.matrizFraccion.length-2][this.matrizFraccion[0].length-1].getNumerador()!=0) return false;
         return true;
     }
     private boolean verificarMDecimal() {
         //if(this.matrizDecimal[this.matrizDecimal.length-2][this.matrizDecimal[0].length-1]!=0) return false;
         if(this.matrizDecimal[this.matrizDecimal.length-2][this.matrizDecimal[0].length-1]> -0.001D && this.matrizDecimal[this.matrizDecimal.length-2][this.matrizDecimal[0].length-1]<0.001D) return true;
         //if(this.matrizDecimal[this.matrizDecimal.length-2][this.matrizDecimal[0].length-1]>=0 && this.matrizDecimal[this.matrizDecimal.length-2][this.matrizDecimal[0].length-1]<=0.001) return true;
         return false;
     }
     
     private void buscarSolucionFraccion() { //funcion para buscar la solucion con fraccion de las variables
        resultadoFraccion = this.getMatrizFraccion()[this.getMatrizFraccion().length-1][this.getMatrizFraccion()[0].length-1];
        resultadoVariablesFraccion = new Fraccion[this.variables+(this.restricciones*2)];
        boolean solucionFilaYaAsignado[] = new boolean[this.matrizFraccion.length-1];
        int columna = 0; //columna
        while(columna<this.matrizFraccion[0].length-1){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            for(int fila=0; fila<this.getMatrizFraccion().length-2; fila++){ //fila
                if(this.getMatrizFraccion()[fila][columna].getNumerador()==1 && this.getMatrizFraccion()[fila][columna].getDenominador()==1 &&encontrado==false && solucionFilaYaAsignado[fila]==false){
                    solucionFilaYaAsignado[fila] = true;
                    resultadoVariablesFraccion[columna]=this.getMatrizFraccion()[fila][this.getMatrizFraccion()[fila].length-1];
                }else if(encontrado == true || this.getMatrizFraccion()[fila][columna].getNumerador()!=0){ //ya existe un 1 0 existen numeros dif a 1
                    resultadoVariablesFraccion[columna] = new Fraccion(0, 1);
                    
                    break;
                }
            }
            columna++;
        }
        imprimirSolucionFraccion();
    }
     
    private void imprimirSolucionFraccion(){
        solucion += "\nSolución óptima:  ";
        solucion += "Z: " + this.resultadoFraccion + ", "; //Z
        solucionOptima += "Z: " + this.resultadoFraccion + ", "; //Z
        //variables
        int i, j;
        for(i=0; i<this.variables; i++){
            solucion += "x" + (i+1) + ": " + this.resultadoVariablesFraccion[i] + ", ";
            solucionOptima += "x" + (i+1) + ": " + this.resultadoVariablesFraccion[i] + ", ";
        }
        //variables flojas - restricciones
        for(i=0, j=this.variables; i<this.restricciones && j<(this.restricciones+this.variables); i++, j++){
            solucion += "s" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
            solucionOptima += "s" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
            
        }
        //variables artificiales
        for(i=0, j=this.restricciones+this.variables; i<this.restricciones-1 && j<(this.matrizFraccion[0].length-1); i++, j++){
            solucion += "A" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
            solucionOptima += "A" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
        }solucion += "A" + this.restricciones + ": " + this.resultadoVariablesFraccion[this.resultadoVariablesFraccion.length-1] + "\n";
        solucionOptima += "A" + this.restricciones + ": " + this.resultadoVariablesFraccion[this.resultadoVariablesFraccion.length-1] + "\n";
    }
    
    private void buscarSolucionDecimal() { //funcion para buscar la solucion con fraccion de las variables
        resultadoDecimal = this.matrizDecimal[this.matrizDecimal.length-1][this.matrizDecimal[0].length-1];
        resultadoVariablesDecimal = new float[this.variables+(this.restricciones*2)];
        boolean solucionFilaYaAsignado[] = new boolean[this.matrizDecimal.length-1];
        int columna = 0; //columna
        while(columna<this.matrizDecimal[0].length-1){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            for(int fila=0; fila<this.matrizDecimal.length-2; fila++){ //fila
                if(this.matrizDecimal[fila][columna]==1 && this.matrizDecimal[fila][columna]==1 &&encontrado==false && solucionFilaYaAsignado[fila]==false){
                    solucionFilaYaAsignado[fila] = true;
                    resultadoVariablesDecimal[columna]=this.matrizDecimal[fila][this.matrizDecimal[fila].length-1];
                }else if(encontrado == true || this.matrizDecimal[fila][columna]!=0){ //ya existe un 1 0 existen numeros dif a 1
                    resultadoVariablesDecimal[columna] = 0;
                    break;
                }
            }
            columna++;
        }
        imprimirSolucionDecimal();
    }
     
    private void imprimirSolucionDecimal(){
        solucion += "\nSolución óptima:  ";
        solucion += "Z: " + this.resultadoDecimal + ", "; //Z
        solucionOptima += "Z: " + this.resultadoDecimal + ", "; //Z
        String aux;
        //variables
        int i, j;
        for(i=0; i<this.variables; i++){
            aux = String.format("%3.3f", this.resultadoVariablesDecimal[i]);
            solucion += "x" + (i+1) + ": " + aux + ", ";
            solucionOptima += "x" + (i+1) + ": " + aux + ", ";
        }
        //variables flojas - restricciones
        for(i=0, j=this.variables; i<this.restricciones && j<(this.restricciones+this.variables); i++, j++){
            aux = String.format("%3.3f", this.resultadoVariablesDecimal[j]);
            solucion += "s" + (i+1) + ": " + aux + ", ";
            solucionOptima += "s" + (i+1) + ": " + aux + ", ";
            
        }
        //variables artificiales
        for(i=0, j=this.restricciones+this.variables; i<this.restricciones-1 && j<(this.matrizDecimal[0].length-1); i++, j++){
            aux = String.format("%3.3f", this.resultadoVariablesDecimal[j]);
            solucion += "A" + (i+1) + ": " + aux + ", ";
            solucionOptima += "A" + (i+1) + ": " + aux + ", ";
        }
        aux = String.format("%3.3f", this.resultadoVariablesDecimal[this.resultadoVariablesDecimal.length-1]);
        solucion += "A" + this.restricciones + ": " + aux + "\n";
        solucionOptima += "A" + this.restricciones + ": " + aux + "\n";
    }
    
    public void imprimirSolucionF(){
        System.out.println("Z: " + this.resultadoFraccion); //Z
        
        for(int i=0; i<this.resultadoVariablesFraccion.length; i++){
            System.out.println(this.resultadoVariablesFraccion[i]);
        }
    }
    
    /**
     * @return the matrizFraccion
     */
    public Fraccion[][] getMatrizFraccion() {
        return matrizFraccion;
    }
    public String getSolucion(){
        return this.solucion;
    }
    public String getSolucionOptima(){
        return this.solucionOptima;
    }
}
