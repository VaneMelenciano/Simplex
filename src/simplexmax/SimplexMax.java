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
public class SimplexMax {
    int restricciones;
    int variables;
    public float[][] matriz;
    public Fraccion[][] matrizF;
    int filaP;
    int columnaP;
    float pivote;
    Fraccion pivoteF;
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
    }
    
    private void buscarPivote(){ //buca la posición del elemento Pivote (fila y columna)
        System.out.println("buscar pivote");
        int filas = this.matriz.length;
        System.out.println(filas);
        int ultima= filas -1; //última fila, donde esta la función objetivo
        System.out.println(ultima);
        float mayor = Math.abs(this.matriz[ultima][0]); //valor absoluto mayor de la última fila
        System.out.println(mayor);
        this.columnaP = 0; //posición del valor absoluto, columna pivote
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            System.out.println(this.matriz[ultima][j]);
            if(Math.abs(this.matriz[ultima][j])>mayor){
                System.out.println("Entro");
                mayor = this.matriz[ultima][j];
                columnaP = j;
            }
        }
        System.out.println("ColumnaP: " + this.columnaP);
        //Razón de desplazamiento
        int columnas = this.matriz[0].length;
        System.out.println(columnas);
        this.filaP = 0; //fila pivote
        float menor = (float)this.matriz[0][columnas-1] / this.matriz[0][columnaP]; //buscar la razón de desplazamineto (menor)
        System.out.println(this.matriz[0][columnas-1] + "/" + this.matriz[0][columnaP] + " = " + menor);
        for(int i=1; i<filas-1; i++){//columna de constantes
            float aux = this.matriz[i][columnas-1] / this.matriz[i][columnaP];
            System.out.println(this.matriz[i][columnas-1] + "/" + this.matriz[i][columnaP] + " = " + aux);
            if(aux<menor){
                System.out.println("entro");
                menor = aux;
                filaP = i;
            }
        }
        System.out.println("FilaP: " + this.filaP);
        actualizarPivote();
        System.out.println("Pivote: " + this.pivote);
        //elemento pivote está en la posición [filaP][columaP]
    }
    
    private void buscarPivoteF(){ //buca la posición del elemento Pivote (fila y columna)
        System.out.println("buscar pivote");
        int filas = this.matrizF.length;
        System.out.println(filas);
        int ultima= filas -1; //última fila, donde esta la función objetivo
        System.out.println(ultima);
        Fraccion mayor = new Fraccion(Math.abs(matrizF[ultima][0].getNumerador()), matrizF[ultima][0].getDenominador());
        //double mayor = Math.abs(Fraccion.convertirFD(this.matrizF[ultima][0])); //valor absoluto mayor de la última fila
        System.out.println(mayor);
        this.columnaP = 0; //posición del valor absoluto, columna pivote
        for(int j=1; j<this.variables; j++){ //recorre el valor de las variables en la última fila
            System.out.println(this.matrizF[ultima][j]);
            //double auxF = Math.abs(this.matriz[ultima][j]);
            Fraccion auxF = new Fraccion(Math.abs(matrizF[ultima][j].getNumerador()), matrizF[ultima][j].getDenominador());
            if(auxF.compararMayor(mayor)){ //Math.abs(this.matriz[ultima][j])>mayor
                System.out.println("Entro");
                mayor = this.matrizF[ultima][j];
                columnaP = j;
            }
        }
        System.out.println("ColumnaP: " + this.columnaP);
        //Razón de desplazamiento
        int columnas = this.matrizF[0].length;
        System.out.println(columnas);
        this.filaP = 0; //fila pivote
        //float menor = (float)this.matriz[0][columnas-1] / this.matriz[0][columnaP]; //buscar la razón de desplazamineto (menor)
        Fraccion menor = this.matrizF[0][columnas-1].dividir(this.matrizF[0][columnaP]); //buscar la razón de desplazamineto (menor)
        System.out.println(this.matrizF[0][columnas-1] + "  /   " + this.matrizF[0][columnaP] + " = " + menor);
        for(int i=1; i<filas-1; i++){//columna de constantes
            //float aux = this.matriz[i][columnas-1] / this.matriz[i][columnaP];
            Fraccion aux = this.matrizF[i][columnas-1].dividir(this.matrizF[i][columnaP]);
            System.out.println(aux);
            if(menor.compararMayor(aux)){ //aux < menor -> menor > aux
                System.out.println("entro");
                menor = aux;
                filaP = i;
            }
        }
        System.out.println("FilaP: " + this.filaP);
        actualizarPivoteF();
        System.out.println("Pivote: " + this.pivoteF);
        //elemento pivote está en la posición [filaP][columaP]
    }
    
    private void filaPivote(){//Hacer a fila pivote 0, menos al elemento pivote
        //número que multiplicado por el pivote de como resultado 1
        //O sea, el reciproco del pivote
        float reciproco = (float) Math.pow(pivote, -1.0);
        //el reciproco afecta a toda la fila pivote
        for(int i=0; i<this.matriz[0].length; i++){ //recorre la filaP
            this.matriz[this.filaP][i] *=reciproco;
        }
        actualizarPivote();
        System.out.println("Fila pivote");
        Matriz.imprimirMatriz(matriz);
    }
    
    private void filaPivoteF(){//Hacer a fila pivote 0, menos al elemento pivote
        //número que multiplicado por el pivote de como resultado 1
        //O sea, el reciproco del pivote
        //float reciproco = (float) Math.pow(pivote, -1.0);
        Fraccion reciproco = new Fraccion(this.pivoteF.getDenominador(), this.pivoteF.getNumerador());
        //el reciproco afecta a toda la fila pivote
        for(int i=0; i<this.matrizF[0].length; i++){ //recorre la filaP
            this.matrizF[this.filaP][i]= this.matrizF[this.filaP][i].multiplicar(reciproco);
        }
        actualizarPivoteF();
        System.out.println("Fila pivote");
        Matriz.imprimirMatriz(matrizF);
    }
    
    private void columnaPivote(){
        System.out.println("Columna pivote");
        for(int i=0; i<this.matriz.length; i++){ //recorre la columa pivote
          if(i!=this.filaP){ //para que no aplique esto a la fila pivote
            float elemento = this.matriz[i][this.columnaP];
            System.out.println("Elemento: " + elemento);
            float x = (-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote 
            System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.matriz[0].length; j++){ //recorre la fila que se ve afectada por x
                this.matriz[i][j] += (x*this.matriz[this.filaP][j]); //el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        
        Matriz.imprimirMatriz(matriz);
    }
    
    private void columnaPivoteF(){
        System.out.println("Columna pivote");
        for(int i=0; i<this.matrizF.length; i++){ //recorre la columa pivote
          if(i!=this.filaP){ //para que no aplique esto a la fila pivote
            Fraccion elemento = this.matrizF[i][this.columnaP];
            System.out.println("Elemento: " + elemento);
            Fraccion x = elemento.dividirNegativo(pivoteF); //al dividir, multiplica por -1 el nominador
            //float x = (-1)*(elemento/this.pivote); //buscar un numero que multiplicado por el pivote 
            System.out.println("X: " + x);
              // y sumado al elemento de la columna pivote de como resultado 0

            //El número encontrado (x) afecta a toda la fila
            for(int j=0; j<this.matrizF[0].length; j++){ //recorre la fila que se ve afectada por x
                //this.matriz[i][j] += (x*this.matriz[this.filaP][j]); //el número (x) se multiplica
                this.matrizF[i][j] =  this.matrizF[i][j].suma(this.matrizF[this.filaP][j].multiplicar(x));//el número (x) se multiplica
                //por el elemento que esté en la fila pivote, el la misma columa (j) del elemento afectado
            }
          }
        }
        Matriz.imprimirMatriz(matrizF);
    }
    
    private boolean verificarNegativos(){
        //verifica que en la última fila no haya números negativos, en las variables
        int filas = this.matriz.length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        for(int j=0; j<this.variables; j++){
            if(this.matriz[ultima][j]<0)return false;
        }
        return true;
    }
    
    private boolean verificarNegativosF(){
        //verifica que en la última fila no haya números negativos, en las variables
        int filas = this.matrizF.length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        for(int j=0; j<this.variables; j++){
            if(this.matrizF[ultima][j].getNumerador()<0)return false;
        }
        return true;
    }

    private void actualizarPivote() {
       this.pivote = this.matriz[this.filaP][this.columnaP];
    }
    
    private void actualizarPivoteF() {
       this.pivoteF = this.matrizF[this.filaP][this.columnaP];
    }
}
