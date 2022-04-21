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

    private void multiplicarMFraccion() { //Multiplica por m cada numero de cada columna y lo suma al último
        for(int columna=0; columna<this.matrizFraccion[0].length; columna++){
            Fraccion resultado = this.matrizFraccion[0][columna];//coeficiente de la ecuacion
                for(int fila=1; fila<this.getMatrizFraccion().length-1; fila++){ //dice que fila es
                    resultado = resultado.suma(this.matrizFraccion[fila][columna]);
                } 
               this.matrizFraccion[this.matrizFraccion.length-2][columna] = resultado;//fila de las constantes
               
        }//System.out.println(Matriz.imprimirMatriz(matrizFraccion, this.variables, this.restricciones));System.out.println();
        
    }
    
    private void buscarPivoteFraccion() {
        //Columna: se elige con la ecuación que tenga mayor absoluto coeficiente de la última fila
        int numFilas = this.getMatrizFraccion().length;
        int filaCoeficientes = this.matrizFraccion.length-2;
        int columna=0;
        Fraccion mayor = new Fraccion(this.matrizFraccion[filaCoeficientes][0].getNumerador(), this.matrizFraccion[filaCoeficientes][0].getDenominador());
        //System.out.println("Mayor: " + mayor);
        this.columnaPivote=columna; //posición del valor absoluto, columna pivoteFraccion
        for(int i=1; i<this.variables+this.restricciones; i++){ //recorre las columnas solo en la fila de coeficientes
            Fraccion aux = new Fraccion(this.matrizFraccion[filaCoeficientes][i].getNumerador(), this.matrizFraccion[filaCoeficientes][i].getDenominador());
            //System.out.println("Aux: " + aux);
            if(aux.compararMayor(mayor)){ //Math.abs(this.matrizFraccion[ultima][j])>mayor
                mayor = aux;
                //System.out.println("Mayor nuevo: " + mayor);
                this.columnaPivote=i;
            }
        }
        //System.out.println("\nColumnaP: " + this.columnaPivote);
        
        //Razón de desplazamiento //CHECAR
        int numColumnas = this.matrizFraccion[0].length;
        int fila=0; //recorre las filas de la columna pivoteFraccion
        while(this.matrizFraccion[fila][columnaPivote].getNumerador()<=0 && fila < filaCoeficientes){
            fila++;
        }
        
           Fraccion menor = this.matrizFraccion[fila][numColumnas-1].dividir(this.matrizFraccion[fila][columnaPivote]); //buscar la razón de desplazamineto (menor)
            
            //System.out.println("Menor: " + menor + " Fila: " + fila); 
            this.filapivote=fila;
            for(int i=fila+1; i<filaCoeficientes; i++){//columna de constantes
                Fraccion aux = this.matrizFraccion[i][numColumnas-1].dividir(this.matrizFraccion[i][columnaPivote]);
                //System.out.println("Comparar: " + menor + " con " + aux);
                
                if(aux.getNumerador()>0 && aux.getDenominador()>0 && menor.compararMayor(aux)){ //>0 para que solo eliga positivos//aux < menor -> menor > aux
                    menor = aux;
                    this.filapivote=i;
                    //System.out.println("Nuevo menor: " + menor + "  fila: " + this.filapivote);
                }
            }
            //System.out.println("FilaP: " + this.filapivote);
            actualizarPivoteFraccion();
            //System.out.println("\nPivote: " + this.pivoteFraccion);
            //elemento pivoteFraccion está en la posición [filapivote][columaP] 
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
        //System.out.println("\nFila pivote : " + this.filapivote);
        //System.out.println(Matriz.imprimirMatriz(this.matrizFraccion, this.variables, this.restricciones));
    }
    
    private void columnaPivoteivoteFraccion(){ //hace a toda la columna pivoteFraccion 0, menos al pivoteFraccion
        //System.out.println("\nColumna pivoteFraccion");
        for(int fila=0; fila<this.matrizFraccion.length-2; fila++){ //recorre la columa pivoteFraccion de la matrizFraccion principal (sin contar ultima fila)
          if(fila!=this.filapivote){ //para que no aplique esto a la fila pivoteFraccion
            Fraccion elemento = this.getMatrizFraccion()[fila][this.columnaPivote];
            //System.out.println("Elemento: " + elemento);
            Fraccion x = elemento.dividirNegativo(this.pivoteFraccion); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivoteFraccion); //buscar un numero que multiplicado por el pivoteFraccion 
            //System.out.println("X: " + x);
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
        //System.out.println("\nX: " + x1 + "  " + x2);
         //El número encontrado (x) afecta a toda la fila (ultima fila en este caso)
            for(int columna=0; columna<this.matrizFraccion[0].length; columna++){ 
                /*x*P + E*/
                //coeficientes
                this.matrizFraccion[this.matrizFraccion.length-2][columna] = this.matrizFraccion[this.matrizFraccion.length-2][columna].suma(x1.multiplicar(this.matrizFraccion[this.filapivote][columna]));
                //Independientes
                this.matrizFraccion[this.matrizFraccion.length-1][columna] = this.matrizFraccion[this.matrizFraccion.length-1][columna].suma(x2.multiplicar(this.matrizFraccion[this.filapivote][columna]));
                //por el elemento que esté en la fila pivoteFraccion, el la misma columa (j) del elemento afectado
            }
        //System.out.println("\nColumna pivote: " + this.columnaPivote);
        //System.out.println(Matriz.imprimirMatriz(getMatrizFraccion(), this.variables, this.restricciones));
    }
    
    private void actualizarPivoteFraccion() {
        this.pivoteFraccion = this.matrizFraccion[this.filapivote][this.columnaPivote];
    }
    
     private boolean verificarMFraccion() {
         if(this.matrizFraccion[this.matrizFraccion.length-2][this.matrizFraccion[0].length-1].getNumerador()!=0) return false;
         return true;
     }
     
     private void buscarSolucionFraccion() { //funcion para buscar la solucion con fraccion de las variables
        resultadoFraccion = this.getMatrizFraccion()[this.getMatrizFraccion().length-1][this.getMatrizFraccion()[0].length-1];
        resultadoVariablesFraccion = new Fraccion[this.variables+(this.restricciones*2)];
        int columna = 0; //columna
        while(columna<this.matrizFraccion[0].length-1){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            //System.out.println("\n");
            for(int fila=0; fila<this.getMatrizFraccion().length-2; fila++){ //fila
                //System.out.println("Pos: " + fila + ", " + columna + "    Elemento: " + this.matrizFraccion[fila][columna]);
                if(this.getMatrizFraccion()[fila][columna].getNumerador()==1 && this.getMatrizFraccion()[fila][columna].getDenominador()==1 &&encontrado==false){
                    resultadoVariablesFraccion[columna]=this.getMatrizFraccion()[fila][this.getMatrizFraccion()[fila].length-1];
                    //System.out.println("Primer if");
                }else if(encontrado == true || this.getMatrizFraccion()[fila][columna].getNumerador()!=0){ //ya existe un 1 0 existen numeros dif a 1
                    resultadoVariablesFraccion[columna] = new Fraccion(0, 1);
                    //System.out.println("Segundo if");
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
        solucionOptima += "\nSolución óptima:  ";
        solucionOptima += "Z: " + this.resultadoFraccion + ", "; //Z
        //variables
        int i, j;
        for(i=0; i<this.variables; i++){
            solucion += "X" + (i+1) + ": " + this.resultadoVariablesFraccion[i] + ", ";
            solucionOptima += "X" + (i+1) + ": " + this.resultadoVariablesFraccion[i] + ", ";
        }
        //variables flojas - restricciones
        for(i=0, j=this.variables; i<this.restricciones && j<(this.restricciones+this.variables); i++, j++){
            solucion += "S" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
            solucionOptima += "S" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
            
        }
        //variables artificiales
        for(i=0, j=this.restricciones+this.variables; i<this.restricciones-1 && j<(this.matrizFraccion[0].length-1); i++, j++){
            solucion += "A" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
            solucionOptima += "A" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
        }solucion += "A" + this.restricciones + ": " + this.resultadoVariablesFraccion[this.resultadoVariablesFraccion.length-1] + "\n";
        solucionOptima += "A" + this.restricciones + ": " + this.resultadoVariablesFraccion[this.resultadoVariablesFraccion.length-1] + "\n";
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
