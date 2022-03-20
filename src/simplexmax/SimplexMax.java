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
    int filaP;
    int columnaP;
    float pivote;
    
    public SimplexMax(int restricciones, int variables, float[][] matriz){
        this.restricciones=restricciones;
        this.variables=variables;
        this.matriz = matriz;
        proceso();
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
    private boolean verificarNegativos(){
        //verifica que en la última fila no haya números negativos, en las variables
        int filas = this.matriz.length;
        int ultima= filas -1; //última fila, donde esta la función objetivo
        for(int j=0; j<this.variables; j++){
            if(this.matriz[ultima][j]<0)return false;
        }
        return true;
    }

    private void actualizarPivote() {
       this.pivote = this.matriz[this.filaP][this.columnaP];
    }
}
