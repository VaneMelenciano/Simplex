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
    private float z; //solucion z rn decimal
    private Fraccion[] xF; //solucion de variables con fraccion
    private float[] x; //solucion de variables en decimal
    //Todas las funciones que tengan una F al final, son las que trabajan con la matriz en fracciones
    private String solucion="";
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
        //System.out.println("Primer proceso");
        int i=1;
        solucion += "Tabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatriz(matriz, this.variables, this.restricciones);
        while(!verificarNegativos()){ //miestras sigan existiendo números negativos en la última fila
           solucion += "\nTabla" + i + ": \n"; i++;
            buscarPivote();
            filaPivote();
            columnaPivote(); 
        } 
        buscarSolucion(); //Z
    }
    
    private void procesoF(){
        //funcion principal para hacer le proceso del simplex max
        int i=1;
        solucion += "Tabla" + i + ": \n"; i++;
        solucion += Matriz.imprimirMatriz(matrizF, this.variables, this.restricciones);
        while(!verificarNegativosF()){ //miestras sigan existiendo números negativos en la última fila
            solucion += "\nTabla" + i + ": \n"; i++;
            buscarPivoteF();
            filaPivoteF();
            columnaPivoteF(); 
        } 
        buscarSolucionF();
    }
    
    private void buscarPivote(){ //buca la posición del elemento Pivote (fila y columna)
        //System.out.println("buscar pivote");
        int filas = this.getMatriz().length;
        //System.out.println(filas);
        int ultima= filas -1; //última fila, donde esta la función objetivo
        //System.out.println(ultima);
        float mayor = Math.abs(this.getMatriz()[ultima][0]); //valor absoluto mayor de la última fila
        //System.out.println("\tmayor: " +mayor);
        this.setColumnaP(0); //posición del valor absoluto, columna pivote
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            //System.out.println("\tnuevo: " + this.getMatriz()[ultima][j]);
            if(Math.abs(this.getMatriz()[ultima][j])>mayor){
                //System.out.println("Entro");
                mayor = Math.abs(this.getMatriz()[ultima][j]);
                setColumnaP(j);
            }
        }
        //System.out.println("ColumnaP: " + this.columnaP);
        //Razón de desplazamiento
        int columnas = this.getMatriz()[0].length;
        //System.out.println(columnas);
        this.setFilaP(0); //fila pivote
        float menor = (float)(this.getMatriz()[0][columnas-1] / this.getMatriz()[0][columnaP]); //buscar la razón de desplazamineto (menor)
        //System.out.println(this.getMatriz()[0][columnas-1] + "/" + this.getMatriz()[0][columnaP] + " = " + menor);
        for(int i=1; i<filas-1; i++){//columna de constantes
            float aux = (this.getMatriz()[i][columnas-1] / this.getMatriz()[i][columnaP]);
            //System.out.println(this.getMatriz()[i][columnas-1] + "/" + this.getMatriz()[i][columnaP] + " = " + aux);
            if(aux>0 && aux<menor){
                //System.out.println("entro");
                menor = aux;
                setFilaP(i);
            }
        }
        //System.out.println("FilaP: " + this.filaP);
        actualizarPivote();
        //System.out.println("Pivote: " + this.getPivote());
        //elemento pivote está en la posición [filaP][columaP]
    }
    
    private void buscarPivoteF(){ //buca la posición del elemento Pivote (fila y columna)
        //System.out.println("buscar pivote");
        int filas = this.getMatrizF().length;
        //System.out.println(filas);
        int ultima= filas -1; //última fila, donde esta la función objetivo
        //System.out.println(ultima);
        Fraccion mayor = new Fraccion(Math.abs(getMatrizF()[ultima][0].getNumerador()), getMatrizF()[ultima][0].getDenominador());
        //double mayor = Math.abs(Fraccion.convertirFD(this.matrizF[ultima][0])); //valor absoluto mayor de la última fila
        //System.out.println(mayor);
        this.setColumnaP(0); //posición del valor absoluto, columna pivote
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            //System.out.println(this.getMatrizF()[ultima][j]);
            //double auxF = Math.abs(this.matriz[ultima][j]);
            Fraccion auxF = new Fraccion(Math.abs(getMatrizF()[ultima][j].getNumerador()), getMatrizF()[ultima][j].getDenominador());
            //System.out.println(auxF);
            if(auxF.compararMayor(mayor)){ //Math.abs(this.matriz[ultima][j])>mayor
                //System.out.println("Entro");
                mayor = auxF;
                //mayor = this.getMatrizF()[ultima][j];
                setColumnaP(j);
            }
        }
        //System.out.println("ColumnaP: " + this.columnaP);
        //Razón de desplazamiento
        int columnas = this.getMatrizF()[0].length;
        //System.out.println(columnas);
        this.setFilaP(0); //fila pivote
        //float menor = (float)this.matriz[0][columnas-1] / this.matriz[0][columnaP]; //buscar la razón de desplazamineto (menor)
        Fraccion menor = this.getMatrizF()[0][columnas-1].dividir(this.getMatrizF()[0][columnaP]); //buscar la razón de desplazamineto (menor)
        //System.out.println(this.getMatrizF()[0][columnas-1] + "  /   " + this.getMatrizF()[0][columnaP] + " = " + menor);
        for(int i=1; i<filas-1; i++){//columna de constantes
            //float aux = this.matriz[i][columnas-1] / this.matriz[i][columnaP];
            Fraccion aux = this.getMatrizF()[i][columnas-1].dividir(this.getMatrizF()[i][columnaP]);
            //System.out.println(aux);
            if(aux.getNumerador()>0 && menor.compararMayor(aux)){ //aux < menor -> menor > aux
                //System.out.println("entro");
                menor = aux;
                setFilaP(i);
            }
        }
        //System.out.println("FilaP: " + this.filaP);
        actualizarPivoteF();
        //System.out.println("Pivote: " + this.getPivoteF());
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
        //System.out.println("Fila pivote");
        //Matriz.imprimirMatriz(getMatriz());
    }
    
    private void filaPivoteF(){//Hacer a fila pivote 0, menos al elemento pivote
        //número que multiplicado por el pivote de como resultado 1
        //O sea, el reciproco del pivote
        //float reciproco = (float) Math.pow(pivote, -1.0);
        Fraccion reciproco = new Fraccion(this.getPivoteF().getDenominador(), this.getPivoteF().getNumerador());
        //System.out.println("Reciproco: " + reciproco);
        //el reciproco afecta a toda la fila pivote
        for(int i=0; i<this.getMatrizF()[0].length; i++){ //recorre la filaP
            this.matrizF[this.filaP][i]= this.getMatrizF()[this.filaP][i].multiplicar(reciproco);
        }
        actualizarPivoteF();
        //System.out.println("Fila pivote");
        //Matriz.imprimirMatriz(getMatrizF());
    }
    
    private void columnaPivote(){
        //System.out.println("Columna pivote");
        for(int i=0; i<this.getMatriz().length; i++){ //recorre la columa pivote
          if(i!=this.filaP){ //para que no aplique esto a la fila pivote
            float elemento = this.getMatriz()[i][this.columnaP];
            //System.out.println("Elemento: " + elemento);
            float x = (-1)*(elemento/this.getPivote()); //buscar un numero que multiplicado por el pivote 
            //System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.getMatriz()[0].length; j++){ //recorre la fila que se ve afectada por x
                this.matriz[i][j] += (x*this.getMatriz()[this.filaP][j]); //el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        solucion += Matriz.imprimirMatriz(getMatriz(), this.variables, this.restricciones);
        //System.out.println("Columna pivote");
        //Matriz.imprimirMatriz(getMatriz());
    }
    
    private void columnaPivoteF(){
        //System.out.println("Columna pivote");
        for(int i=0; i<this.getMatrizF().length; i++){ //recorre la columa pivote
          if(i!=this.filaP){ //para que no aplique esto a la fila pivote
            Fraccion elemento = this.getMatrizF()[i][this.columnaP];
            //System.out.println("Elemento: " + elemento);
            Fraccion x = elemento.dividirNegativo(getPivoteF()); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote 
            //System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.getMatrizF()[0].length; j++){ //recorre la fila que se ve afectada por x
                //this.matriz[i][j] += (x*this.matriz[this.filaP][j]); //el número (x) se multiplica
                this.matrizF[i][j] =  this.getMatrizF()[i][j].suma(this.getMatrizF()[this.filaP][j].multiplicar(x));//el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        solucion += Matriz.imprimirMatriz(getMatrizF(), this.variables, this.restricciones);
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
    
    private void buscarSolucionF() { //funcion para buscar la solucion con fraccion de las variables
        zF = this.getMatrizF()[this.getMatrizF().length-1][this.getMatrizF()[0].length-1];
        xF = new Fraccion[this.variables+this.restricciones];
        int t = 0; //columna
        while(t<(this.variables+this.restricciones)){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            for(int i=0; i<this.getMatrizF().length-1; i++){ //fila
                if(this.getMatrizF()[i][t].getNumerador()==1 && this.getMatrizF()[i][t].getDenominador()==1 &&encontrado==false){
                    xF[t]=this.getMatrizF()[i][this.getMatrizF()[i].length-1];
                }else if(encontrado == true || this.getMatrizF()[i][t].getNumerador()!=0){ //ya existe un 1 0 existen numeros dif a 1
                    xF[t] = new Fraccion(0, 1);
                }
            }
            t++;
        }
        imprimirSolucionF();
    }
 
    
    private void buscarSolucion() { //funcion para buscar la solucion con fraccion de las variables
        z = this.getMatriz()[this.getMatriz().length-1][this.getMatriz()[0].length-1];
        x = new float[this.variables+this.restricciones];
        int t = 0; //columna
        while(t<(this.variables+this.restricciones)){
            boolean encontrado = false; //para saber si ya se encontró en valor de la variables, es decir, si existe un uno en la columna de esa variable
            for(int i=0; i<this.getMatriz().length-1; i++){ //fila
                if(this.getMatriz()[i][t]==1 && encontrado==false){
                    x[t]=this.getMatriz()[i][this.getMatriz()[i].length-1];
                }else if(encontrado == true || this.getMatriz()[i][t]!=0){ //ya existe un 1 0 existen numeros dif a 1
                    x[t] = 0;
                    break;
                }
            }
            t++;
        }
        imprimirSolucion();
    }
    
    private void imprimirSolucionF(){
        solucion += "\nSolución óptima:  ";
        solucion += "Z: " + this.zF + ", "; //Z
        //variables
        for(int i=0; i<this.variables-1; i++){
            solucion += "x" + (i+1) + ": " + this.xF[i] + ", ";
        }solucion += "x" + this.variables + ": " + this.xF[this.variables-1] + ", ";
        //variables flojas - restricciones
        for(int i=0, j=this.variables; i<this.restricciones-1 && j<(this.restricciones+this.variables-1); i++, j++){
            solucion += "s" + (i+1) + ": " + this.xF[j] + ", ";
        }solucion += "s" + this.restricciones + ": " + this.xF[this.xF.length-1] + "\n";
    }
    
    private void imprimirSolucion(){
        solucion += "\nSolución óptima:  ";
        solucion += "Z: " + String.format("%3.3f", this.z) + ", "; //Z
        //variables
        for(int i=0; i<this.variables-1; i++){
            solucion += "x" + (i+1) + ": " + String.format("%3.3f", this.x[i]) + ", ";
        }solucion += "x" + this.variables + ": " + String.format("%3.3f", this.x[this.variables-1]) + ", ";
        //variables flojas - restricciones
        for(int i=0, j=this.variables; i<this.restricciones-1 && j<(this.restricciones+this.variables-1); i++, j++){
            solucion += "s" + (i+1) + ": " + String.format("%3.3f", this.x[j]) + ", ";
        }solucion += "s" + this.restricciones + ": " + String.format("%3.3f", this.x[this.x.length-1]) + "\n";
        
        /*for(int i=0; i<this.xF.length; i++){
           solucion += this.xF[i] + ", "; 
        }*/
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

    /**
     * @return the solucion
     */
    public String getSolucion() {
        return solucion;
    }
    
}
