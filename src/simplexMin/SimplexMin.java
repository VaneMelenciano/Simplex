/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplexMin;

import objetos.Fraccion;
import objetos.Matriz;

/**
 *
 * @author Vanessa
 */
public class SimplexMin {
    int restricciones;
    int variables;
    private Fraccion[][] matriz;
    private Ecuacion[] ultimaFila;
    int filaP;
    int columnaP;
    Fraccion pivote;
    private Fraccion zF; //solucion z con fraccion
    private Fraccion[] xF; //solucion x con fraccion
    //Todas las funciones que tengan una F al final, son las que trabajan con la matriz de funciones
    public SimplexMin(int variables, int restricciones, Fraccion[][] matriz, Ecuacion[] ultimaFila){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matriz = matriz;
        this.ultimaFila = ultimaFila; //se recibe ya con las M (tipo ecuacion)
        proceso();
    }

    private void proceso() {
        multiplicarM(); 
        buscarPivote();
        filaPivote();
        columnaPivote();
        while(!verificarM()){ //miestras sigan existiendo M última fila, donde están las constantes
         //   multiplicarM(); 
            buscarPivote();
            filaPivote();
            columnaPivote();
        }
        buscarSolZ();
        buscarSolX();
    }

    private void multiplicarM() { //Multiplica por m cada numero de cada columna y lo suma al último
        for(int i=0; i<this.matriz[0].length; i++){ //dice que columna es
            Fraccion c = this.matriz[0][i];//coeficiente de la ecuacion
                //System.out.println(c);
                for(int j=1; j<this.getMatriz().length; j++){ //dice que fila es
                    //System.out.println("j: " + j);
                    //System.out.print(c + " + " + this.matriz[j][i] + " = ");
                    c = c.suma(this.matriz[j][i]);
                    //System.out.println("\t" + c);
                } 
                //c ya tiene la suma, la ultima fila el coeficiente
                c = c.suma(this.ultimaFila[i].getCoe());
               Ecuacion e = new Ecuacion(c, this.getUltimaFila()[i].getInd());
               this.ultimaFila[i] = e; //se actualiza la ultima fila con la suma
        }
    }
    private void buscarPivote() {
        int filas = this.getMatriz().length;
        Fraccion mayor = new Fraccion(Math.abs(this.ultimaFila[0].getCoe().getNumerador()), this.ultimaFila[0].getCoe().getDenominador());
         //valor absoluto mayor de la última fila
        this.columnaP=0; //posición del valor absoluto, columna pivote
        for(int j=1; j<this.matriz[0].length-1; j++){ //recorre el valor de las variables en la última fila
            Fraccion auxF = new Fraccion(this.ultimaFila[j].getCoe().getNumerador(), this.ultimaFila[j].getCoe().getDenominador());
            if(auxF.compararMayor(mayor)){ //Math.abs(this.matriz[ultima][j])>mayor
                mayor = this.ultimaFila[j].getCoe();
                this.columnaP=j;
            }
        }
        System.out.println("\nColumnaP: " + this.columnaP);
        //Razón de desplazamiento
        int columnas = this.matriz[0].length;
        this.filaP=0; //fila pivote
        int i=0;
        while(this.matriz[i][columnaP].getNumerador()<0){
            i++;
        }
        if(i==filas){ //si todos son negativos (la columna)
           int m=0;
           Fraccion menor = this.matriz[m][columnas-1].dividir(this.matriz[m][columnaP]);
           for(; m<filas; m++){//columna de constantes
                Fraccion aux = this.matriz[i][columnas-1].dividir(this.matriz[m][columnaP]);
                System.out.println("Comparar: " + menor + " con " + aux);
                if(aux.getNumerador()>=0 && menor.compararMayor(aux)){ //aux < menor -> menor > aux
                    menor = aux;
                    this.filaP=m;
                    System.out.println("Menor: " + menor);
                }
            } 
        }else{
           Fraccion menor = this.matriz[i][columnas-1].dividir(this.matriz[i][columnaP]); //buscar la razón de desplazamineto (menor)
            System.out.println("Menor: " + menor); 
            this.filaP=i;
            i++;
            for(; i<filas; i++){//columna de constantes
                Fraccion aux = this.matriz[i][columnas-1].dividir(this.matriz[i][columnaP]);
                System.out.println("Comparar: " + menor + " con " + aux);
                if(aux.getNumerador()>=0 && menor.compararMayor(aux)){ //aux < menor -> menor > aux
                    menor = aux;
                    this.filaP=i;
                    System.out.println("Menor: " + menor);
                }
            }
        }
            System.out.println("FilaP: " + this.filaP);
            actualizarPivote();
            System.out.println("\nPivote: " + this.pivote);
            //elemento pivote está en la posición [filaP][columaP] 
    }
    
    private void filaPivote(){//Hacer a fila pivote 0, menos al elemento pivote
        //número que multiplicado por el pivote de como resultado 1
        //O sea, el reciproco del pivote
        //float reciproco = (float) Math.pow(pivote, -1.0);
        Fraccion reciproco = new Fraccion(this.pivote.getDenominador(), this.pivote.getNumerador());
        //el reciproco afecta a toda la fila pivote
        for(int i=0; i<this.matriz[0].length; i++){ //recorre la filaP
            this.matriz[this.filaP][i]= this.matriz[this.filaP][i].multiplicar(reciproco);
        }
        actualizarPivote();
        System.out.println("\nFila pivote : " + this.filaP);
        Matriz.imprimirMatriz(this.matriz, this.ultimaFila);
    }
    private void columnaPivote(){
        //System.out.println("\nColumna pivote");
        for(int i=0; i<this.matriz.length; i++){ //recorre la columa pivote de la matriz principal (sin contar ultima fila)
          if(i!=this.filaP){ //para que no aplique esto a la fila pivote
            Fraccion elemento = this.getMatriz()[i][this.columnaP];
            //System.out.println("Elemento: " + elemento);
            Fraccion x = elemento.dividirNegativo(this.pivote); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote 
            //System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.getMatriz()[0].length; j++){ //recorre la fila que se ve afectada por x
                //this.matriz[i][j] += (x*this.matriz[this.filaP][j]); //el número (x) se multiplica
                this.matriz[i][j] =  this.getMatriz()[i][j].suma(this.getMatriz()[this.filaP][j].multiplicar(x));//el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        //elemento de la columa pivote de la última fila
        Ecuacion elemento = this.ultimaFila[this.columnaP];
        //(-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote y sumado al elemento de la columna pivote de como resultado 0
        Fraccion x1 = elemento.getCoe().dividirNegativo(this.pivote);
        Fraccion x2 = elemento.getInd().dividirNegativo(this.pivote);
        Ecuacion x = new Ecuacion(x1, x2);
        System.out.println("\nX: " + x);
         //El número encontrado (x) afecta a toda la fila (ultima fila en este caso)
            for(int j=0; j<this.ultimaFila.length; j++){ //recorre la fila que se ve afectada por x
                //this.matriz[i][j] += (x*this.matriz[this.filaP][j]); //el número (x) se multiplica
                //this.ultimaFila[j] =  this.ultimaFila[j].suma(this.getMatriz()[this.filaP][j].multiplicar(x));//el número (x) se multiplica
                Ecuacion mul = new Ecuacion(x.getCoe().multiplicar(this.getMatriz()[this.filaP][j]), x.getInd().multiplicar(this.getMatriz()[this.filaP][j]));
                //Ecuacion suma = new Ecuacion(mul.getCoe().suma(this.ultimaFila[j].getCoe()), mul.getInd().suma(this.ultimaFila[j].getInd()));
                this.ultimaFila[j] = new Ecuacion(mul.getCoe().suma(this.ultimaFila[j].getCoe()), mul.getInd().suma(this.ultimaFila[j].getInd()));
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
        System.out.println("\nColumna pivote: " + this.columnaP);
        Matriz.imprimirMatriz(getMatriz(), this.ultimaFila);
    }
    
    private void actualizarPivote() {
        this.pivote = this.matriz[this.filaP][this.columnaP];
    }
    
     private boolean verificarM() {
         if(this.ultimaFila[this.ultimaFila.length-1].getCoe().getNumerador()!=0) return false;
         else return true;
     }
     
     private void buscarSolZ(){ //funcion para buscar la solucion con fraccion de Z
        this.zF = this.ultimaFila[this.ultimaFila.length-1].getInd();//this.getMatriz()[this.getMatriz().length-1][this.getMatriz()[0].length-1];
        //zF = this.getMatriz()[this.getMatriz().length-1][this.getMatriz()[0].length-1];
    }
    private void buscarSolX() { //funcion para buscar la solucion con fraccion de las variables
        System.out.println();
        this.xF = new Fraccion[this.variables];
        int t = 0; //columna
        while(t<this.variables){
            System.out.println("t: " + t);
            for(int i=0; i<this.getMatriz().length; i++){ //fila
                if(this.getMatriz()[i][t].getNumerador()==1){
                    xF[t]=this.getMatriz()[i][this.getMatriz()[i].length-1];
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
     * @return the matriz
     */
    public Fraccion[][] getMatriz() {
        return matriz;
    }

    /**
     * @return the ultimaFila
     */
    public Ecuacion[] getUltimaFila() {
        return ultimaFila;
    }

   
        

    
    
}
