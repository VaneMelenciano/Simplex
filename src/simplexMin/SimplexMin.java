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
    int restricciones;
    int variables;
    private Fraccion[][] matriz;
    //private Ecuacion[] ultimaFila;
    int filaP;
    int columnaP;
    Fraccion pivote;
    private Fraccion zF; //solucion z con fraccion
    private Fraccion[] xF; //solucion x con fraccion
    private String solucion="";
    //Todas las funciones que tengan una F al final, son las que trabajan con la matriz de funciones
    public SimplexMin(int variables, int restricciones, Fraccion[][] matriz){//, Ecuacion[] ultimaFila){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matriz = matriz;
        //this.ultimaFila = ultimaFila; //se recibe ya con las M (tipo ecuacion)
        procesoF();
    }

    private void procesoF() {
        multiplicarMF(); 
        buscarPivoteF();
        filaPivoteF();
        columnaPivoteF();
        while(!verificarM()){ //miestras sigan existiendo M última fila, donde están las constantes
            //Mientras la fila de coeficientes en la última columna, no tenga 0
            buscarPivoteF();
            filaPivoteF();
            columnaPivoteF();
        }
        buscarSolucionF();
    }

    private void multiplicarMF() { //Multiplica por m cada numero de cada columna y lo suma al último
        for(int columna=0; columna<this.matriz[0].length; columna++){
            Fraccion resultado = this.matriz[0][columna];//coeficiente de la ecuacion
                for(int fila=1; fila<this.getMatriz().length-1; fila++){ //dice que fila es
                    resultado = resultado.suma(this.matriz[fila][columna]);
                } 
               this.matriz[this.matriz.length-2][columna] = resultado;//fila de las constantes
               
        }System.out.println();Matriz.imprimirMatriz(matriz);System.out.println();
    }
    
    private void buscarPivoteF() {
        //Columna: se elige con la ecuación que tenga mayor absoluto coeficiente de la última fila
        int numFilas = this.getMatriz().length;
        int filaCoeficientes = this.matriz.length-2;
        int columna=0;
        Fraccion mayor = new Fraccion(this.matriz[filaCoeficientes][0].getNumerador(), this.matriz[filaCoeficientes][0].getDenominador());
        System.out.println("Mayor: " + mayor);
        this.columnaP=columna; //posición del valor absoluto, columna pivote
        columna++;
        for(; columna<this.variables+this.restricciones; columna++){ //recorre las columnas solo en la fila de coeficientes
            Fraccion auxF = new Fraccion(this.matriz[filaCoeficientes][columna].getNumerador(), this.matriz[filaCoeficientes][columna].getDenominador());
            if(auxF.compararMayor(mayor)){ //Math.abs(this.matriz[ultima][j])>mayor
                mayor = auxF;
                System.out.println("Mayor nuevo: " + mayor);
                this.columnaP=columna;
            }
        }
        System.out.println("\nColumnaP: " + this.columnaP);
        
        //Razón de desplazamiento //CHECAR
        int numColumnas = this.matriz[0].length;
        this.filaP=0; //fila pivote
        int fila=0; //recorre las filas de la columna pivote
        while(this.matriz[fila][columnaP].getNumerador()<0 && fila < filaCoeficientes){
            fila++;
        }
        /*if(fila==numFilas-2){ //si todos son negativos (la columna) //MAL
           int m=0;
           Fraccion menor = this.matriz[m][numColumnas-1].dividir(this.matriz[m][columnaP]);
           for(; m<numFilas; m++){//columna de constantes
                Fraccion aux = this.matriz[fila][numColumnas-1].dividir(this.matriz[m][columnaP]);
                System.out.println("Comparar: " + menor + " con " + aux);
                if(aux.getNumerador()>=0 && menor.compararMayor(aux)){ //aux < menor -> menor > aux
                    menor = aux;
                    this.filaP=m;
                    System.out.println("Menor: " + menor);
                }
            } 
        }else{*/
           Fraccion menor = this.matriz[fila][numColumnas-1].dividir(this.matriz[fila][columnaP]); //buscar la razón de desplazamineto (menor)
            //System.out.println("Menor: " + menor); 
            this.filaP=fila;
            fila++;
            for(; fila<filaCoeficientes; fila++){//columna de constantes
                Fraccion aux = this.matriz[fila][numColumnas-1].dividir(this.matriz[fila][columnaP]);
                //System.out.println("Comparar: " + menor + " con " + aux);
                if(aux.getNumerador()>=0 && menor.compararMayor(aux)){ //no tomar negativos //aux < menor -> menor > aux
                    menor = aux;
                    this.filaP=fila;
                    //System.out.println("Menor: " + menor);
                }
            }
        //}
            System.out.println("FilaP: " + this.filaP);
            actualizarPivoteF();
            System.out.println("\nPivote: " + this.pivote);
            //elemento pivote está en la posición [filaP][columaP] 
    }
    
    private void filaPivoteF(){
        //número que multiplicado por el pivote de como resultado 1
        //O sea, el reciproco del pivote
        //float reciproco = (float) Math.pow(pivote, -1.0);
        Fraccion reciproco = new Fraccion(this.pivote.getDenominador(), this.pivote.getNumerador());
        //el reciproco afecta a toda la fila pivote
        for(int columna=0; columna<this.matriz[0].length; columna++){ //recorre la filaP
            this.matriz[this.filaP][columna]= this.matriz[this.filaP][columna].multiplicar(reciproco);
        }
        actualizarPivoteF();
        System.out.println("\nFila pivote : " + this.filaP);
        Matriz.imprimirMatriz(this.matriz);
    }
    
    private void columnaPivoteF(){ //hace a toda la columna pivote 0, menos al pivote
        //System.out.println("\nColumna pivote");
        for(int fila=0; fila<this.matriz.length-2; fila++){ //recorre la columa pivote de la matriz principal (sin contar ultima fila)
          if(fila!=this.filaP){ //para que no aplique esto a la fila pivote
            Fraccion elemento = this.getMatriz()[fila][this.columnaP];
            //System.out.println("Elemento: " + elemento);
            Fraccion x = elemento.dividirNegativo(this.pivote); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote 
            //System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int columna=0; columna<this.getMatriz()[0].length; columna++){ //recorre la fila que se ve afectada por x
                //this.matriz[i][j] += (x*this.matriz[this.filaP][j]); //el número (x) se multiplica
                this.matriz[fila][columna] =  this.getMatriz()[fila][columna].suma(this.getMatriz()[this.filaP][columna].multiplicar(x));//el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        //elemento de la columa pivote de la última fila
        Fraccion elementoCoeficiente = this.matriz[this.matriz.length-2][this.columnaP];
        Fraccion elementoIndependiente = this.matriz[this.matriz.length-1][this.columnaP];
        //(-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote y sumado al elemento de la columna pivote de como resultado 0
        Fraccion x1 = elementoCoeficiente.dividirNegativo(this.pivote);
        Fraccion x2 = elementoIndependiente.dividirNegativo(this.pivote);
        //Ecuacion x = new Ecuacion(x1, x2);
        System.out.println("\nX: " + x1 + "  " + x2);
         //El número encontrado (x) afecta a toda la fila (ultima fila en este caso)
            for(int columna=0; columna<this.matriz[0].length; columna++){ 
                /*x*P + E*/
                //coeficientes
                this.matriz[this.matriz.length-2][columna] = this.matriz[this.matriz.length-2][columna].suma(x1.multiplicar(this.matriz[this.filaP][columna]));
                //Independientes
                this.matriz[this.matriz.length-1][columna] = this.matriz[this.matriz.length-1][columna].suma(x2.multiplicar(this.matriz[this.filaP][columna]));
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
        System.out.println("\nColumna pivote: " + this.columnaP);
        Matriz.imprimirMatriz(getMatriz());
    }
    
    private void actualizarPivoteF() {
        this.pivote = this.matriz[this.filaP][this.columnaP];
    }
    
     private boolean verificarM() {
         if(this.matriz[this.matriz.length-2][this.matriz[0].length-1].getNumerador()!=0) return false;
         return true;
     }
     
     private void buscarSolucionF() { //funcion para buscar la solucion con fraccion de las variables
        zF = this.getMatriz()[this.getMatriz().length-1][this.getMatriz()[0].length-1];
        xF = new Fraccion[this.variables+(this.restricciones*2)];
        int columna = 0; //columna
        while(columna<this.matriz[0].length-1){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            System.out.println("\n");
            for(int fila=0; fila<this.getMatriz().length-2; fila++){ //fila
                System.out.println("Pos: " + fila + ", " + columna + "    Elemento: " + this.matriz[fila][columna]);
                if(this.getMatriz()[fila][columna].getNumerador()==1 && this.getMatriz()[fila][columna].getDenominador()==1 &&encontrado==false){
                    xF[columna]=this.getMatriz()[fila][this.getMatriz()[fila].length-1];
                    System.out.println("Primer if");
                }else if(encontrado == true || this.getMatriz()[fila][columna].getNumerador()!=0){ //ya existe un 1 0 existen numeros dif a 1
                    xF[columna] = new Fraccion(0, 1);
                    System.out.println("Segundo if");
                    break;
                }
            }
            columna++;
        }
        imprimirSolucionF();
    }
     
    private void imprimirSolucionF(){
        solucion += "\nSolución óptima:  ";
        solucion += "Z: " + this.zF + ", "; //Z
        //variables
        int i, j;
        for(i=0; i<this.variables; i++){
            solucion += "X" + (i+1) + ": " + this.xF[i] + ", ";
        }
        //variables flojas - restricciones
        for(i=0, j=this.variables; i<this.restricciones && j<(this.restricciones+this.variables-1); i++, j++){
            solucion += "S" + (i+1) + ": " + this.xF[j] + ", ";
        }
        //variables artificiales
        for(i=0, j=this.restricciones+this.variables; i<this.restricciones-1 && j<(this.matriz[0].length-1); i++, j++){
            solucion += "A" + (i+1) + ": " + this.xF[j] + ", ";
        }solucion += "A" + this.restricciones + ": " + this.xF[this.xF.length-1] + "\n";
    }
    
    public void imprimirSolucion(){
        System.out.println("Z: " + this.zF); //Z
        
        for(int i=0; i<this.xF.length; i++){
            System.out.println(this.xF[i]);
        }
    }
    /**
     * @return the matriz
     */
    public Fraccion[][] getMatriz() {
        return matriz;
    }
    public String getSolucion(){
        return this.solucion;
    }   
}
