/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

/**
 *
 * @author Hugo Geovani Arroyo Castorena
 */
public class MatrizMax {
    private float[][] matriz;
    private int var,res;
    private boolean bandera;
    private float numerador,denominador;

    public MatrizMax(int v, int r){
        this.var = v;
        this.res = r;
        this.numerador = 0;
        this.denominador = 1;
    }

    public void crearMatriz(PanelVariables jv, PanelRestricciones jr){
        bandera=true;
        //RECORRER CAJAS DE TEXTO Y DEFINIR MODO DE MATRIZ A CREAR SEGÚN EL TIPO DE VALORES RECIBIDOS (FRACCION-FALSE O DECIMAL-TRUE)
        for(int i=0;i<jv.getbField().size();i++){
            if(jv.getbField().get(i).getText().contains("/"))
                bandera = false;
        }
        for(int i=0;i<jr.getaField().size();i++){
            for(int j=0;j<var+1;j++){
                if(jr.getaField().get(i).get(j).getText().contains("/"))
                    bandera = false;
            }
        }
        //CREAR MATRIZ SEGUN EL TIPO DE VALORES RECIBIDOS (FRACCION-FALSE O DECIMAL-TRUE)
        if(bandera==true){ //Se crea matriz ideal para arrojar tablas modo decimal
            //Crear matriz con las dimensiones correspondientes
            this.matriz = new float[res+1][var+res+1];
            //Ingresar en las columnas correspondientes la cantidad necesitada para cada variable (los de las restricciones)
            for(int i=0;i<res;i++){
                for(int j=0;j<var;j++){
                    if(!jr.getaField().get(i).get(j).getText().isEmpty() && esNum(jr.getaField().get(i).get(j).getText()))
                        matriz[i][j]= Float.parseFloat(jr.getaField().get(i).get(j).getText());
                }
            }
            //Ingresar en las columnas correspondientes la cantidad de unos necesarios de las variables de holgura
            for(int i=0;i<res;i++){
                for(int j=var;j<var+res;j++){
                    if(j==(i+var))
                        matriz[i][j]= 1;
                }
            }
            //Ingresar en la ultima columna la cantidad max para cada variable (de las restricciones)
            for(int i=0;i<res;i++){
                if(!jr.getaField().get(i).get(var).getText().isEmpty() && esNum(jr.getaField().get(i).get(var).getText()))
                    matriz[i][var+res]= Float.parseFloat(jr.getaField().get(i).get(var).getText());
            }
            //Ingresar en la ultima fila los valores correspondientes (los de la funcion objetivo
            for(int j=0;j<var;j++){
                if(jv.getbField().get(j).getText().isEmpty())
                    matriz[res][j]=0;
                else{
                    if(esNum(jv.getbField().get(j).getText())){
                        matriz[res][j] = -Float.parseFloat(jv.getbField().get(j).getText());
                    }
                }
            }
        }else{ //Se crea matriz ideal para arrojar tablas modo fracción
            //System.out.println("\nHay fraccion");
            //Crear matriz con las dimensiones correspondientes
            this.matriz = new float[res+res+2][var+res+1];
            //Rellenar matriz con 1 iniciales en las filas correspondientes 
            for(int i=1; i<(2*(res+1)); i+=2){
                for(int j=0; j<(var+res+1); j++){
                    matriz[i][j] = 1;  
                }
            }
            //Ingresar en las columnas correspondientes la cantidad necesitada para cada variable (los de las restricciones)
            for(int i=0;i<res;i++){
                for(int j=0;j<var;j++){
                    if(!jr.getaField().get(i).get(j).getText().isEmpty() && jr.getaField().get(i).get(j).getText().contains("/")){
                        rescatarFraccion(jr.getaField().get(i).get(j).getText());
                        matriz[2*i][j] = numerador;
                        matriz[(2*i)+1][j] = denominador;
                    }else if(!jr.getaField().get(i).get(j).getText().isEmpty() && esNum(jr.getaField().get(i).get(j).getText())){
                        matriz[2*i][j] = Float.parseFloat(jr.getaField().get(i).get(j).getText());
                    }
                }
            }
            //Ingresar en las columnas correspondientes la cantidad de unos necesarios de las variables de holgura
            int k = var;
            for(int i=0;i<(2*res);i+=2){
                matriz[i][k]= 1;    
                k++;
            }
            //Ingresar en la ultima columna la cantidad max para cada variable (de las restricciones)
            for(int i=0;i<res;i++){
                if(!jr.getaField().get(i).get(var).getText().isEmpty() && jr.getaField().get(i).get(var).getText().contains("/")){
                    rescatarFraccion(jr.getaField().get(i).get(var).getText());
                    matriz[2*i][var+res] = numerador;
                    matriz[(2*i)+1][var+res] = denominador;
                }else if(!jr.getaField().get(i).get(var).getText().isEmpty() && esNum(jr.getaField().get(i).get(var).getText())){
                    matriz[2*i][var+res] = Float.parseFloat(jr.getaField().get(i).get(var).getText());
                }
            }
            //Ingresar en la ultima fila los valores correspondientes (los de la funcion objetivo
            for(int j=0;j<var;j++){
                if(!jv.getbField().get(j).getText().isEmpty() && jv.getbField().get(j).getText().contains("/")){
                    rescatarFraccion(jv.getbField().get(j).getText());
                    matriz[res+res][j] = -numerador;
                    matriz[res+res+1][j] = denominador;
                }else if(!jv.getbField().get(j).getText().isEmpty() && esNum(jv.getbField().get(j).getText())){
                    matriz[res+res][j] = -Float.parseFloat(jv.getbField().get(j).getText());
                }
            }
        }
    }

    public void mostrarMatriz(){
        if(bandera==true){
            System.out.print("\n\nMATRIZ\n\n");
            for(int i=0; i<(res+1); i++){
                for(int j=0; j<(var+res+1); j++){
                    System.out.print( matriz[i][j] + " | ");    
                }
                System.out.println();
            }
        }else{
            System.out.print("\n\nMATRIZ\n\n");
            for(int i=0; i<(2*(res+1)); i++){
                for(int j=0; j<(var+res+1); j++){
                    System.out.print( matriz[i][j] + " | ");    
                }
                System.out.println();
            }
        }
    }

    private boolean esNum(String valor){
        try{
            if(valor!=null)
                Float.parseFloat(valor);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }

    private void rescatarFraccion(String valor){
        String[] valores = valor.split("/");
        if(esNum(valores[0]) && esNum(valores[1])){
            numerador = Float.parseFloat(valores[0]);
            denominador = Float.parseFloat(valores[1]);
        }else{
            numerador = 0;
            denominador = 1;
        }     
    }

    public float[][] getMatriz(){
        return matriz;
    }

    public boolean getBandera(){
        return bandera;
    } 
}
