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
    private float[][] matriz;
    private Fraccion[][] matrizF;
    private int filaP;
    private int columnaP;
    private float pivote;
    private Fraccion pivoteF;
    private Fraccion zF; //solucion z con fraccion
    private Fraccion[] xF; //solucion x con fraccion
    //Todas las funciones que tengan una F al final, son las que trabajan con la matriz de funciones
    public SimplexMax(int variables, int restricciones, float[][] matriz){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matriz = matriz;
        
        proceso();
    }
    public SimplexMax(int variables, int restricciones, Fraccion[][] matriz){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matrizF = matriz;
        
        procesoF();
    }
    private void proceso(){
        //funcion principal para hacer le proceso del simplex max
        System.out.println("Primer proceso");
        buscarPivote();
        filaPivote();
        columnaPivote();
        while(!verificarNegativos()){ //miestras sigan existiendo números negativos en la última fila
            //Matriz.imprimirMatriz(matriz);
            System.out.println("Siguiente proceso");
           buscarPivote();
            filaPivote();
            columnaPivote(); 
        } 
    }
    private void procesoF(){
        //funcion principal para hacer le proceso del simplex max
        while(!verificarNegativosF()){ //miestras sigan existiendo números negativos en la última fila
            //Matriz.imprimirMatriz(matriz);
            System.out.println("Siguiente proceso");
            buscarPivoteF();
            filaPivoteF();
            columnaPivoteF(); 
        } 
        buscarSolZF(); //Z
        buscarSolXF();
    }
    
    private void buscarPivote(){ //buca la posición del elemento Pivote (fila y columna)
        System.out.println("buscar pivote");
        int filas = this.getMatriz().length;
        System.out.println(filas);
        int ultima= filas -1; //última fila, donde esta la función objetivo
        System.out.println(ultima);
        float mayor = Math.abs(this.getMatriz()[ultima][0]); //valor absoluto mayor de la última fila
        System.out.println(mayor);
        this.setColumnaP(0); //posición del valor absoluto, columna pivote
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            System.out.println(this.getMatriz()[ultima][j]);
            if(Math.abs(this.getMatriz()[ultima][j])>mayor){
                System.out.println("Entro");
                mayor = this.getMatriz()[ultima][j];
                setColumnaP(j);
            }
        }
        System.out.println("ColumnaP: " + this.columnaP);
        //Razón de desplazamiento
        int columnas = this.getMatriz()[0].length;
        System.out.println(columnas);
        this.setFilaP(0); //fila pivote
        float menor = (float)this.getMatriz()[0][columnas-1] / this.getMatriz()[0][columnaP]; //buscar la razón de desplazamineto (menor)
        System.out.println(this.getMatriz()[0][columnas-1] + "/" + this.getMatriz()[0][columnaP] + " = " + menor);
        for(int i=1; i<filas-1; i++){//columna de constantes
            float aux = this.getMatriz()[i][columnas-1] / this.getMatriz()[i][columnaP];
            System.out.println(this.getMatriz()[i][columnas-1] + "/" + this.getMatriz()[i][columnaP] + " = " + aux);
            if(aux<menor){
                System.out.println("entro");
                menor = aux;
                setFilaP(i);
            }
        }
        System.out.println("FilaP: " + this.filaP);
        actualizarPivote();
        System.out.println("Pivote: " + this.getPivote());
        //elemento pivote está en la posición [filaP][columaP]
    }
    
    private void buscarPivoteF(){ //buca la posición del elemento Pivote (fila y columna)
        System.out.println("buscar pivote");
        int filas = this.getMatrizF().length;
        System.out.println(filas);
        int ultima= filas -1; //última fila, donde esta la función objetivo
        System.out.println(ultima);
        Fraccion mayor = new Fraccion(Math.abs(getMatrizF()[ultima][0].getNumerador()), getMatrizF()[ultima][0].getDenominador());
        //double mayor = Math.abs(Fraccion.convertirFD(this.matrizF[ultima][0])); //valor absoluto mayor de la última fila
        System.out.println(mayor);
        this.setColumnaP(0); //posición del valor absoluto, columna pivote
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            System.out.println(this.getMatrizF()[ultima][j]);
            //double auxF = Math.abs(this.matriz[ultima][j]);
            Fraccion auxF = new Fraccion(Math.abs(getMatrizF()[ultima][j].getNumerador()), getMatrizF()[ultima][j].getDenominador());
            if(auxF.compararMayor(mayor)){ //Math.abs(this.matriz[ultima][j])>mayor
                System.out.println("Entro");
                mayor = this.getMatrizF()[ultima][j];
                setColumnaP(j);
            }
        }
        System.out.println("ColumnaP: " + this.columnaP);
        //Razón de desplazamiento
        int columnas = this.getMatrizF()[0].length;
        System.out.println(columnas);
        this.setFilaP(0); //fila pivote
        //float menor = (float)this.matriz[0][columnas-1] / this.matriz[0][columnaP]; //buscar la razón de desplazamineto (menor)
        Fraccion menor = this.getMatrizF()[0][columnas-1].dividir(this.getMatrizF()[0][columnaP]); //buscar la razón de desplazamineto (menor)
        System.out.println(this.getMatrizF()[0][columnas-1] + "  /   " + this.getMatrizF()[0][columnaP] + " = " + menor);
        for(int i=1; i<filas-1; i++){//columna de constantes
            //float aux = this.matriz[i][columnas-1] / this.matriz[i][columnaP];
            Fraccion aux = this.getMatrizF()[i][columnas-1].dividir(this.getMatrizF()[i][columnaP]);
            System.out.println(aux);
            if(menor.compararMayor(aux)){ //aux < menor -> menor > aux
                System.out.println("entro");
                menor = aux;
                setFilaP(i);
            }
        }
        System.out.println("FilaP: " + this.filaP);
        actualizarPivoteF();
        System.out.println("Pivote: " + this.getPivoteF());
        //elemento pivote está en la posición [filaP][columaP]
    }
    
    private void filaPivote(){//Hacer a fila pivote 0, menos al elemento pivote
        //número que multiplicado por el pivote de como resultado 1
        //O sea, el reciproco del pivote
        float reciproco = (float) Math.pow(getPivote(), -1.0);
        //el reciproco afecta a toda la fila pivote
        for(int i=0; i<this.getMatriz()[0].length; i++){ //recorre la filaP
            this.matriz[this.filaP][i] *=reciproco;
        }
        actualizarPivote();
        System.out.println("Fila pivote");
        Matriz.imprimirMatriz(getMatriz());
    }
    
    private void filaPivoteF(){//Hacer a fila pivote 0, menos al elemento pivote
        //número que multiplicado por el pivote de como resultado 1
        //O sea, el reciproco del pivote
        //float reciproco = (float) Math.pow(pivote, -1.0);
        Fraccion reciproco = new Fraccion(this.getPivoteF().getDenominador(), this.getPivoteF().getNumerador());
        //el reciproco afecta a toda la fila pivote
        for(int i=0; i<this.getMatrizF()[0].length; i++){ //recorre la filaP
            this.matrizF[this.filaP][i]= this.getMatrizF()[this.filaP][i].multiplicar(reciproco);
        }
        actualizarPivoteF();
        System.out.println("Fila pivote");
        Matriz.imprimirMatriz(getMatrizF());
    }
    
    private void columnaPivote(){
        System.out.println("Columna pivote");
        for(int i=0; i<this.getMatriz().length; i++){ //recorre la columa pivote
          if(i!=this.filaP){ //para que no aplique esto a la fila pivote
            float elemento = this.getMatriz()[i][this.columnaP];
            System.out.println("Elemento: " + elemento);
            float x = (-1)*(elemento/this.getPivote()); //buscar un numero que multiplicado por el pivote 
            System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.getMatriz()[0].length; j++){ //recorre la fila que se ve afectada por x
                this.matriz[i][j] += (x*this.getMatriz()[this.filaP][j]); //el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        
        Matriz.imprimirMatriz(getMatriz());
    }
    
    private void columnaPivoteF(){
        System.out.println("Columna pivote");
        for(int i=0; i<this.getMatrizF().length; i++){ //recorre la columa pivote
          if(i!=this.filaP){ //para que no aplique esto a la fila pivote
            Fraccion elemento = this.getMatrizF()[i][this.columnaP];
            System.out.println("Elemento: " + elemento);
            Fraccion x = elemento.dividirNegativo(getPivoteF()); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote 
            System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.getMatrizF()[0].length; j++){ //recorre la fila que se ve afectada por x
                //this.matriz[i][j] += (x*this.matriz[this.filaP][j]); //el número (x) se multiplica
                this.matrizF[i][j] =  this.getMatrizF()[i][j].suma(this.getMatrizF()[this.filaP][j].multiplicar(x));//el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        Matriz.imprimirMatriz(getMatrizF());
    }
    
    private boolean verificarNegativos(){
        //verifica que en la última fila no haya números negativos, en las variables
        int filas = this.getMatriz().length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        for(int j=0; j<this.variables; j++){
            if(this.getMatriz()[ultima][j]<0)return false;
        }
        return true;
    }
    
    private boolean verificarNegativosF(){
        //verifica que en la última fila no haya números negativos, en las variables
        int filas = this.getMatrizF().length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        for(int j=0; j<this.variables; j++){
            if(this.getMatrizF()[ultima][j].getNumerador()<0)return false;
        }
        return true;
    }

    private void actualizarPivote() {
       this.pivote = this.getMatriz()[this.filaP][this.columnaP];
    }
    
    private void actualizarPivoteF() {
       this.pivoteF = this.getMatrizF()[this.filaP][this.columnaP];
    }
    private void buscarSolZF() { //funcion para buscar la solucion con fraccion de Z
        zF = this.getMatrizF()[this.getMatrizF().length-1][this.getMatrizF()[0].length-1];
    }
    private void buscarSolXF() { //funcion para buscar la solucion con fraccion de las variables
        xF = new Fraccion[this.variables];
        int t = 0; //columna
        while(t<this.variables){
            for(int i=0; i<this.getMatrizF().length-1; i++){ //fila
                if(this.getMatrizF()[i][t].getNumerador()==1){
                    xF[t]=this.getMatrizF()[i][this.getMatrizF()[i].length-1];
                    ///ERROR
                }
            }
            t++;
        }
    }
    public void imprimirSolucion(){
        System.out.println("Z: " + this.zF); //Z
        
        for(int i=0; i<this.xF.length; i++){
            System.out.println(this.xF[i]);
        }
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
    public void setMatriz(float[][] matriz) {
        this.matriz = matriz;
    }

    /**
     * @param matrizF the matrizF to set
     */
    public void setMatrizF(Fraccion[][] matrizF) {
        this.matrizF = matrizF;
    }

    /**
     * @param filaP the filaP to set
     */
    public void setFilaP(int filaP) {
        this.filaP = filaP;
    }

    /**
     * @param columnaP the columnaP to set
     */
    public void setColumnaP(int columnaP) {
        this.columnaP = columnaP;
    }

    /**
     * @return the pivote
     */
    public float getPivote() {
        return pivote;
    }

    /**
     * @return the pivoteF
     */
    public Fraccion getPivoteF() {
        return pivoteF;
    }

    /**
     * @return the zF
     */
    public Fraccion getzF() {
        return zF;
    }

    /**
     * @return the xF
     */
    public Fraccion[] getxF() {
        return xF;
    }

    /**
     * @return the matriz
     */
    public float[][] getMatriz() {
        return matriz;
    }

    /**
     * @return the matrizF
     */
    public Fraccion[][] getMatrizF() {
        return matrizF;
    }
    
}
