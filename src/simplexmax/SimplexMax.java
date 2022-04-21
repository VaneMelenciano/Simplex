/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexmax;

import objetos.Matriz;
import objetos.Fraccion;

/**
 *
 * @author Vanessa
 */
public class SimplexMax {
    private int restricciones;
    private int variables;
    private float[][] matrizDecimal;
    private Fraccion[][] matrizFraccion;
    private int filaPivote;
    private int columnaPivote;
    private float pivoteDecimal;
    private Fraccion pivoteFraccion;
    private Fraccion resultadoFraccion; //solucion z con fraccion
    private float resultadoDecimal; //solucion z en decimal
    private Fraccion[] resultadoVariablesFraccion; //solucion de variables con fraccion
    //private Fraccion[] resultadoVariablesFraccion; //solucion de variables con fraccion
    private float[] resultadoVariablesDecimal; //solucion de variables en decimal
    //Todas las funciones que tengan una F al final, son las que trabajan con la matriz en fracciones
    private String solucion="";
    private String solucionOptima="";
    
    public SimplexMax(int variables, int restricciones, float[][] matrizDecimal){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matrizDecimal = matrizDecimal;
        
        proceso();
    }
    
    public SimplexMax(int variables, int restricciones, Fraccion[][] matriz){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matrizFraccion = matriz;
    
        procesoFraccion();
    }
    
    private void proceso(){
        //funcion principal para hacer le proceso del simplex max
        int i=1;
        solucion += "Tabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatriz(matrizDecimal, this.variables, this.restricciones);
        
        while(!verificarNegativos()){ //miestras sigan existiendo números negativos en la última fila
           solucion += "\nTabla" + i + ": \n"; i++;
            buscarPivote();
            filaPivoteivote();
            columnaPivoteivote(); 
        } 
        buscarSolucion(); //Z y valores de variables
    }
    
    private void procesoFraccion(){
        //funcion principal para hacer le proceso del simplex max
        int i=1;
        solucion += "Tabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatriz(matrizFraccion, this.variables, this.restricciones);
        while(!verificarNegativosFraccion()){ //miestras sigan existiendo números negativos en la última fila
            solucion += "\nTabla" + i + ": \n"; i++;
            buscarPivoteFraccion();
            filaPivoteivoteFraccion();
            columnaPivoteivoteFraccion(); 
        } 
        buscarSolucionFraccion();
    }
    
    private void buscarPivote(){ //busca la posición del elemento Pivote (fila y columna)
        //Busca columna pivoteDecimal
        int filas = this.getMatrizDecimal().length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        float mayor = Math.abs(this.getMatrizDecimal()[ultima][0]); //valor absoluto mayor de la última fila
        setColumnaPivote(0); //posición del valor absoluto, columna pivoteDecimal
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            if(Math.abs(this.getMatrizDecimal()[ultima][j])>mayor){
                mayor = Math.abs(this.getMatrizDecimal()[ultima][j]);
                setColumnaPivote(j);
            }
        }
        //Razón de desplazamiento (Busca fila pivoteDecimal)
        int columnas = this.getMatrizDecimal()[0].length;
        int fila=0; //fila pivote
        
        while(this.matrizDecimal[fila][columnaPivote]<=0 && fila < filas){
            fila++;
        }
        float menor = (float)(this.getMatrizDecimal()[fila][columnas-1] / this.getMatrizDecimal()[fila][columnaPivote]); //buscar la razón de desplazamineto (menor)
        setFilaPivote(fila);
        for(int i=fila+1; i<filas-1; i++){//columna de constantes
            float aux = (this.getMatrizDecimal()[i][columnas-1] / this.getMatrizDecimal()[i][columnaPivote]);
            if(aux>0 && aux<menor){
                menor = aux;
                setFilaPivote(i);
            }
        }
        actualizarPivote(); //elemento pivoteDecimal está en la posición [filaPivote][columaP]
    }
    
    private void buscarPivoteFraccion(){ //buca la posición del elemento Pivote (fila y columna)
        //Busca columna pivoteDecimal
        int numFilas = this.getMatrizFraccion().length;
        int ultima= numFilas -1; //última fila, donde esta la función objetivo
        Fraccion mayor = new Fraccion(Math.abs(getMatrizFraccion()[ultima][0].getNumerador()), getMatrizFraccion()[ultima][0].getDenominador());
        this.setColumnaPivote(0); //posición del valor absoluto, columna pivoteDecimal
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            Fraccion aux = new Fraccion(Math.abs(getMatrizFraccion()[ultima][j].getNumerador()), getMatrizFraccion()[ultima][j].getDenominador());
            if(aux.compararMayor(mayor)){ //Math.abs(this.matriz[ultima][j])>mayor
                mayor = aux;
                setColumnaPivote(j);
            }
        }
        //Razón de desplazamiento
        int columnas = this.getMatrizFraccion()[0].length;
        int fila=0; //fila pivote
        
        while(this.matrizFraccion[fila][columnaPivote].getNumerador()<=0 && fila < numFilas){
            fila++;
        }
        
        Fraccion menor = this.getMatrizFraccion()[fila][columnas-1].dividir(this.getMatrizFraccion()[fila][columnaPivote]); //buscar la razón de desplazamineto (menor)
        setFilaPivote(fila);
        for(int i=fila+1; i<numFilas-1; i++){//columna de constantes
            Fraccion aux = this.getMatrizFraccion()[i][columnas-1].dividir(this.getMatrizFraccion()[i][columnaPivote]);
            if(aux.getNumerador()>0 && aux.getDenominador()>0 && menor.compararMayor(aux)){ //>0 para que solo eliga positivos//aux < menor -> menor > aux
                menor = aux;
                setFilaPivote(i);
            }
        }
        actualizarPivoteFraccion(); //elemento pivoteDecimal está en la posición [filaPivote][columaP]
    }
    
    private void filaPivoteivote(){//Hacer a fila pivoteDecimal 0, menos al elemento pivoteDecimal
        //número que multiplicado por el pivoteDecimal de como resultado 1
        //O sea, el reciproco del pivoteDecimal
        float reciproco = (float) Math.pow(getPivoteDecimal(), -1.0);
        //el reciproco afecta a toda la fila pivoteDecimal
        for(int i=0; i<this.getMatrizDecimal()[0].length; i++){ //recorre la filaPivote
            this.matrizDecimal[this.filaPivote][i] *=reciproco;
        }
        actualizarPivote();
    }
    
    private void filaPivoteivoteFraccion(){//Hacer a fila pivoteDecimal 0, menos al elemento pivoteDecimal
        //número que multiplicado por el pivoteDecimal de como resultado 1
        //O sea, el reciproco del pivoteDecimal
        Fraccion reciproco = new Fraccion(this.getPivoteFraccion().getDenominador(), this.getPivoteFraccion().getNumerador());
        //el reciproco afecta a toda la fila pivoteDecimal
        for(int i=0; i<this.getMatrizFraccion()[0].length; i++){ //recorre la filaPivote
            this.matrizFraccion[this.filaPivote][i]= this.getMatrizFraccion()[this.filaPivote][i].multiplicar(reciproco);
        }
        actualizarPivoteFraccion();
    }
    
    private void columnaPivoteivote(){
        for(int i=0; i<this.getMatrizDecimal().length; i++){ //recorre la columa pivoteDecimal
          if(i!=this.filaPivote){ //para que no aplique esto a la fila pivoteDecimal
            float elemento = this.getMatrizDecimal()[i][this.columnaPivote];
            float x = (-1)*(elemento/this.getPivoteDecimal()); //buscar un numero que multiplicado por el pivoteDecimal 
              // y sumado al elemento de la columna pivoteDecimal de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.getMatrizDecimal()[0].length; j++){ //recorre la fila que se ve afectada por x
                this.matrizDecimal[i][j] += (x*this.getMatrizDecimal()[this.filaPivote][j]); //el número (x) se multiplica
                //por el elemento que esté en la fila pivoteDecimal, el la misma columa (j) del elemento afectado
            }
          }
        }
        solucion += Matriz.imprimirMatriz(getMatrizDecimal(), this.variables, this.restricciones);
    }
    
    private void columnaPivoteivoteFraccion(){
        for(int i=0; i<this.getMatrizFraccion().length; i++){ //recorre la columa pivoteDecimal
          if(i!=this.filaPivote){ //para que no aplique esto a la fila pivoteDecimal
            Fraccion elemento = this.getMatrizFraccion()[i][this.columnaPivote];
            Fraccion x = elemento.dividirNegativo(getPivoteFraccion()); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivoteDecimal); //buscar un numero que multiplicado por el pivoteDecimal 
              // y sumado al elemento de la columna pivoteDecimal de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.getMatrizFraccion()[0].length; j++){ //recorre la fila que se ve afectada por x
                //this.matriz[i][j] += (x*this.matriz[this.filaPivote][j]); //el número (x) se multiplica
                this.matrizFraccion[i][j] =  this.getMatrizFraccion()[i][j].suma(this.getMatrizFraccion()[this.filaPivote][j].multiplicar(x));//el número (x) se multiplica
                //por el elemento que esté en la fila pivoteDecimal, el la misma columa (j) del elemento afectado
            }
          }
        }
        solucion += Matriz.imprimirMatriz(getMatrizFraccion(), this.variables, this.restricciones);
    
    }
    
    private boolean verificarNegativos(){
        //verifica que en la última fila no haya números negativos, en las variables
        int filas = this.getMatrizDecimal().length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        for(int j=0; j<this.variables; j++){
            if(this.getMatrizDecimal()[ultima][j]<0)return false;
        }
        return true;
    }
    
    private boolean verificarNegativosFraccion(){
        //verifica que en la última fila no haya números negativos, en las variables
        int filas = this.getMatrizFraccion().length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        for(int j=0; j<this.variables; j++){
            if(this.getMatrizFraccion()[ultima][j].getNumerador()<0)return false;
        }
        return true;
    }

    private void actualizarPivote() {
       this.pivoteDecimal = this.getMatrizDecimal()[this.filaPivote][this.columnaPivote];
    }
    
    private void actualizarPivoteFraccion() {
       this.pivoteFraccion = this.getMatrizFraccion()[this.filaPivote][this.columnaPivote];
    }
    
    private void buscarSolucionFraccion() { //funcion para buscar la solucion con fraccion de las variables
        resultadoFraccion = this.getMatrizFraccion()[this.getMatrizFraccion().length-1][this.getMatrizFraccion()[0].length-1];
        resultadoVariablesFraccion = new Fraccion[this.variables+this.restricciones];
            //Para que solo se pueda dar una vez el valor de esa fila a una variable
        boolean solucionFilaYaAsignado[] = new boolean[this.matrizFraccion.length-1];
        int t = 0; //columna
        while(t<(this.variables+this.restricciones)){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            for(int fila=0; fila<this.getMatrizFraccion().length-1; fila++){ //fila
                if(this.getMatrizFraccion()[fila][t].getNumerador()==1 && this.getMatrizFraccion()[fila][t].getDenominador()==1 && encontrado==false && solucionFilaYaAsignado[fila]==false){
                    solucionFilaYaAsignado[fila] = true;
                    resultadoVariablesFraccion[t]=this.getMatrizFraccion()[fila][this.getMatrizFraccion()[fila].length-1];
                }else if(encontrado == true || this.getMatrizFraccion()[fila][t].getNumerador()!=0){ //ya existe un 1 0 existen numeros dif a 1
                    resultadoVariablesFraccion[t] = new Fraccion(0, 1);
                    break;
                }
            }
            t++;
        }
        imprimirSolucionFraccion();
    }
 
    
    private void buscarSolucion() { //funcion para buscar la solucion con fraccion de las variables
        resultadoDecimal = this.getMatrizDecimal()[this.getMatrizDecimal().length-1][this.getMatrizDecimal()[0].length-1];
        resultadoVariablesDecimal = new float[this.variables+this.restricciones];
        //Para que solo se pueda dar una vez el valor de esa fila a una variable
        boolean solucionFilaYaAsignado[] = new boolean[this.matrizDecimal.length-1];
        int t = 0; //columna
        while(t<(this.variables+this.restricciones)){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            for(int fila=0; fila<this.getMatrizDecimal().length-1; fila++){ //fila
                if(this.getMatrizDecimal()[fila][t]==1 && encontrado==false && solucionFilaYaAsignado[fila]==false){
                    solucionFilaYaAsignado[fila] = true;
                    resultadoVariablesDecimal[t]=this.getMatrizDecimal()[fila][this.getMatrizDecimal()[fila].length-1];
                }else if(encontrado == true || this.getMatrizDecimal()[fila][t]!=0){ //ya existe un 1 0 existen numeros dif a 1
                    resultadoVariablesDecimal[t] = 0;
                    break;
                }
            }
            t++;
        }
        imprimirSolucion();
    }
    
    private void imprimirSolucionFraccion(){
        solucion += "\nSolución óptima:  ";
        solucionOptima += "\nSolución óptima:  ";
        solucion += "Z: " + this.resultadoFraccion + ", "; //Z
        solucionOptima += "Z: " + this.resultadoFraccion + ", "; //Z
        //variables
        for(int i=0; i<this.variables-1; i++){
            solucion += "x" + (i+1) + ": " + this.resultadoVariablesFraccion[i] + ", ";
            solucionOptima += "x" + (i+1) + ": " + this.resultadoVariablesFraccion[i] + ", ";
        }solucion += "x" + this.variables + ": " + this.resultadoVariablesFraccion[this.variables-1] + ", ";
        solucionOptima += "x" + this.variables + ": " + this.resultadoVariablesFraccion[this.variables-1] + ", ";
        //variables flojas - restricciones
        for(int i=0, j=this.variables; i<this.restricciones-1 && j<(this.restricciones+this.variables-1); i++, j++){
            solucion += "s" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
            solucionOptima += "s" + (i+1) + ": " + this.resultadoVariablesFraccion[j] + ", ";
        }solucion += "s" + this.restricciones + ": " + this.resultadoVariablesFraccion[this.resultadoVariablesFraccion.length-1] + "\n";
        solucionOptima += "s" + this.restricciones + ": " + this.resultadoVariablesFraccion[this.resultadoVariablesFraccion.length-1] + "\n";
    }
    
    private void imprimirSolucion(){
        solucion += "\nSolución óptima:  ";
        solucionOptima += "\nSolución óptima:  ";
        solucion += "Z: " + String.format("%3.3f", this.resultadoDecimal) + ", "; //Z
        solucionOptima += "Z: " + String.format("%3.3f", this.resultadoDecimal) + ", "; //Z
        //variables
        for(int i=0; i<this.variables-1; i++){
            solucion += "x" + (i+1) + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[i]) + ", ";
            solucionOptima += "x" + (i+1) + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[i]) + ", ";
        }solucion += "x" + this.variables + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[this.variables-1]) + ", ";
        solucionOptima += "x" + this.variables + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[this.variables-1]) + ", ";
        //variables flojas - restricciones
        for(int i=0, j=this.variables; i<this.restricciones-1 && j<(this.restricciones+this.variables-1); i++, j++){
            solucion += "s" + (i+1) + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[j]) + ", ";
            solucionOptima += "s" + (i+1) + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[j]) + ", ";
        }solucion += "s" + this.restricciones + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[this.resultadoVariablesDecimal.length-1]) + "\n";
        solucionOptima += "s" + this.restricciones + ": " + String.format("%3.3f", this.resultadoVariablesDecimal[this.resultadoVariablesDecimal.length-1]) + "\n";
    }
    
    
    /**
     * @param restricciones the restricciones to set
     */
    public void setRestricciones(int restricciones) {
        this.restricciones = restricciones;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(int variables) {
        this.variables = variables;
    }

    /**
     * @param matriz the matriz to set
     */
    public void setMatrizDecimal(float[][] matrizDecimal) {
        this.matrizDecimal = matrizDecimal;
    }

    /**
     * @param matrizFraccion the matrizFraccion to set
     */
    public void setMatrizFraccion(Fraccion[][] matrizFraccion) {
        this.matrizFraccion = matrizFraccion;
    }

    /**
     * @param filaPivote the filaPivote to set
     */
    public void setFilaPivote(int filaPivote) {
        this.filaPivote = filaPivote;
    }

    /**
     * @param columnaPivote the columnaPivote to set
     */
    public void setColumnaPivote(int columnaPivote) {
        this.columnaPivote = columnaPivote;
    }

    /**
     * @return the pivoteDecimal
     */
    public float getPivoteDecimal() {
        return pivoteDecimal;
    }

    /**
     * @return the pivoteFraccion
     */
    public Fraccion getPivoteFraccion() {
        return pivoteFraccion;
    }

    /**
     * @return the resultadoFraccion
     */
    public Fraccion getresultadoFraccion() {
        return resultadoFraccion;
    }

    /**
     * @return the resultadoVariablesFraccion
     */
    public Fraccion[] getresultadoVariablesFraccion() {
        return resultadoVariablesFraccion;
    }

    /**
     * @return the matriz
     */
    public float[][] getMatrizDecimal() {
        return matrizDecimal;
    }

    /**
     * @return the matrizFraccion
     */
    public Fraccion[][] getMatrizFraccion() {
        return matrizFraccion;
    }

    /**
     * @return the solucion
     */
    public String getSolucion() {
        return solucion;
    }

    public String getSolucionOptima() {
            return solucionOptima;
        }    
}
