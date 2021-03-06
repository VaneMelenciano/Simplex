/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static objetos.Fraccion.convertir;

/**
 *
 * @author Vanessa
 */
public class Matriz {
    public static float[][] matriz; //matriz de decimales
    public static Fraccion[][] matrizF; //matriz de fracciones
    public static int restricciones;
    public static int variables;
    
    public static Fraccion[][] convertirMatriz(int[][] matrizOriginalEntera){
        Fraccion[][] matriz = new Fraccion[matrizOriginalEntera.length/2][matrizOriginalEntera[0].length];
        for(int fila=0, filaOriginal=0; fila<matrizOriginalEntera.length/2 && filaOriginal<matrizOriginalEntera.length; fila++, filaOriginal+=2){
            for(int columna =0; columna<matrizOriginalEntera[0].length; columna++){
                matriz[fila][columna] = new Fraccion(matrizOriginalEntera[filaOriginal][columna], matrizOriginalEntera[filaOriginal+1][columna]);
            }
        }
        return matriz;
    }
    public static float[][] convertirMatriz(Fraccion[][] matriz) { //convierte una matriz de flotantes a matriz de Fracciones
        float[][] aux = new float[matriz.length][matriz[0].length];
        for(int i=0; i<matriz.length; i++){
            for(int j=0; j<matriz[0].length; j++){
                aux[i][j] = convertir(matriz[i][j]);
            }
        }
        return aux;
    }
    
    public static Fraccion[][] convertirMatriz(float[][] matriz) { //convierte una matriz de flotantes a matriz de Fracciones
        Fraccion[][] aux = new Fraccion[matriz.length][matriz[0].length];
        for(int i=0; i<matriz.length; i++){
            for(int j=0; j<matriz[0].length; j++){
                aux[i][j] = convertir(matriz[i][j]);
            }
        }
        return aux;
    }
    public static Fraccion[][] convertirMatrizRecibiendoDenominadorNumerador(float[][] matrizOriginal) { //convierte una matriz de flotantes a matriz de Fracciones
        Fraccion[][] matriz = new Fraccion[matrizOriginal.length/2][matrizOriginal[0].length];
        for(int fila=0, filaOriginal=0; fila<matrizOriginal.length/2 && filaOriginal<matrizOriginal.length; fila++, filaOriginal+=2){
            for(int columna =0; columna<matrizOriginal[0].length; columna++){
                matriz[fila][columna] = new Fraccion((int)matrizOriginal[filaOriginal][columna], (int)matrizOriginal[filaOriginal+1][columna]);
            }
        }
        return matriz;
    }
    
    public static float[][] leerArchivo(){
        String aux, texto;
        float matriz[][] = null;
        LinkedList<String> lista = new LinkedList(); //para guardar los datos que se vayan leyendo
        
        
        try {
            JFileChooser file = new JFileChooser(); //llamamos el metodo que permite cargar la ventana
            file.setCurrentDirectory(new File("./Pruebas"));
            file.showOpenDialog(file);
            //Abre el archivo
            File abre = file.getSelectedFile();

            //recorremos el archivo y lo leemos
            if (abre != null) { //verifica que est?? abierto
                
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);

                while ((aux = lee.readLine()) != null) {
                    texto = aux; //guarda la linea del archivo leido en el String
                    lista.add(texto); //a??ade el String anterior a la lista
                }
                lee.close();
                
                
                //TOKENIZAR DATOS
                ArrayList<String> lista2 = new ArrayList<>(); //un renglon
                int columnas =0;
                String aux1 = lista.get(0);
                for (char c: aux1.toCharArray ()) { 
                    if(c == ' '){
                        columnas++;
                    } 
                }
                columnas++;
                matriz = new float[lista.size()][columnas];
                for (int i = 0; i < lista.size(); i++) { 

                    StringTokenizer tokens = new StringTokenizer(lista.get(i), " "); //va separando los renglones guardado en la lista, por los espacios

                    while (tokens.hasMoreTokens()) { //mientras existan tokens (renglones)
                        lista2.add(tokens.nextToken()); //guarda cada dato del renglo en la lista2
                    }
                    
                    for (int x = 0; x < columnas; x++) { 
                        matriz[i][x] = Float.valueOf(lista2.get(x));
                    }
                    lista2.clear();
                }
                
          
            }
            
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nArchivo no encontrado",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
            
        }
        return matriz;
    }
    
    public static Fraccion[][] leerArchivoFraccion(){
        String aux, texto;
        Fraccion matriz[][] = null;
        LinkedList<String> lista = new LinkedList(); //para guardar los datos que se vayan leyendo
        
        
        try {
            JFileChooser file = new JFileChooser(); //llamamos el metodo que permite cargar la ventana
            file.setCurrentDirectory(new File("./Pruebas"));
            file.showOpenDialog(file);
            //Abre el archivo
            File abre = file.getSelectedFile();

            //recorremos el archivo y lo leemos
            if (abre != null) { //verifica que est?? abierto
                
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);

                while ((aux = lee.readLine()) != null) {
                    texto = aux; //guarda la linea del archivo leido en el String
                    lista.add(texto); //a??ade el String anterior a la lista
                }
                lee.close();
                
                
                //TOKENIZAR DATOS
                ArrayList<String> lista2 = new ArrayList<>(); //un renglon
                int columnas =0;
                String aux1 = lista.get(0);
                for (char c: aux1.toCharArray ()) { 
                    if(c == ' '){
                        columnas++;
                    } 
                }
                columnas++;
                matriz = new Fraccion[lista.size()][columnas];
                
                for (int i = 0; i < lista.size(); i++) { 

                    StringTokenizer tokens = new StringTokenizer(lista.get(i), " "); //va separando los renglones guardado en la lista, por los espacios

                    while (tokens.hasMoreTokens()) { //mientras existan tokens (renglones)
                        lista2.add(tokens.nextToken()); //guarda cada dato del renglo en la lista2
                    }
                    
                    /*for (int x = 0; x < columnas; x++) { //s manda directamente como fraccion
                        Fraccion auxF = new Fraccion();
                        int num = Character.getNumericValue(lista2.get(x).charAt(0)); 
                        auxF.setNumerador(num);
                        if(lista.get(x).length()>1){
                            int den = Character.getNumericValue(lista2.get(x).charAt(2));
                            auxF.setDenominador(den);
                        }else{
                            auxF.setDenominador(1);
                        }
                        //matriz[i][x] = Float.valueOf(lista2.get(x));
                    }*/
                    for (int x = 0; x < columnas; x++) { 
                        matriz[i][x] = Fraccion.convertir(Float.valueOf(lista2.get(x)));
                    }
                    
                    lista2.clear();
                }
                
          
            }
            
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nArchivo no encontrado",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
            
        }
        return matriz;
    }
    
    public static void imprimirMatriz(float[][] matriz){
       for(int i=0; i<matriz.length; i++){
            for(float j : matriz[i]){
               System.out.print(j + " "); 
            }
            System.out.println();
        } 
    }
    public static void imprimirMatriz(Fraccion[][] matriz){
       for(int i=0; i<matriz.length; i++){
            for(Fraccion j : matriz[i]){
               //j.toString();
               if(j.getDenominador()!=1 && j.getNumerador()!=0)
                System.out.print(j.getNumerador()+"/"+j.getDenominador());
               else //numero enteros
                 System.out.print(j.getNumerador()); 
               System.out.print(" ");
            }
            System.out.println();
        } 
    }

    public static String imprimirMatriz(Fraccion[][] mat, int var, int res) {
        String matriz  = "";
        //PRIMERA FILA
        int i;
        for(i=1; i<=var; i++){
            matriz += "x" + i + "\t";
        }
        for(i=1; i<=res; i++){
            matriz += "s" + i + "\t";
        }
        matriz += "| Constantes\n";
        //MATRIZ PRINCIPAL
        for(i=0; i<mat.length; i++){//recorre la matriz recibida
            for(int j=0; j<mat[0].length-1; j++){
                matriz += mat[i][j].toString() + "\t";
            }
            matriz += "| " + mat[i][mat[0].length-1].toString() + "\n";
        }
        return matriz;
    }
    public static String imprimirMatriz(float[][] mat, int var, int res) {
        String matriz  = "";
        //PRIMERA FILA
        int i;
        for(i=1; i<=var; i++){
            matriz += "x" + i + "\t";
        }
        for(i=1; i<=res; i++){
            matriz += "S" + i + "\t";
        }
        matriz += "| Constantes\n";
        //MATRIZ PRINCIPAL
        for(i=0; i<mat.length; i++){//recorre la matriz recibida
            for(int j=0; j<mat[0].length-1; j++){
                //Para definir numero de decimales: String.format(5.56733D, "%3.3f")
                matriz += String.format("%3.3f", mat[i][j]) + "\t";
            }
            matriz += "| " + String.format("%3.3f", mat[i][mat[0].length-1]) + "\n";
        }
        return matriz;
    }
    
    public static String imprimirMatrizMinimizacion1(float[][] mat, int var, int res) {
        String matrizCompleta  = "";
        boolean auxExiteEcuacionLarga[] = new boolean[mat[0].length-1];
        //PRIMERA FILA
        int i, columnaAux = 0;
        for(i=1; i<=var; i++){
            matrizCompleta += "x" + i + "\t";
            //matrizCompleta += " " + mat[mat.length-1][columnaAux] +" "  +mat[mat.length-2][columnaAux] + " ";
            if(comprobarEcuacionLarga(mat[mat.length-1][columnaAux], mat[mat.length-2][columnaAux])==true){
                matrizCompleta += "\t";
                auxExiteEcuacionLarga[columnaAux] = true;
            }
            columnaAux++;
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "s" + i + "\t";
            if(comprobarEcuacionLarga(mat[mat.length-1][columnaAux], mat[mat.length-2][columnaAux])==true){
                matrizCompleta += "\t";
                auxExiteEcuacionLarga[columnaAux] = true;
            }
            columnaAux++;
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "A" + i + "\t";
            if(comprobarEcuacionLarga(mat[mat.length-1][columnaAux], mat[mat.length-2][columnaAux])==true){
                matrizCompleta += "\t";
                auxExiteEcuacionLarga[columnaAux] = true;
            }
            columnaAux++;
        }
        matrizCompleta += "| Constantes\n";
        //MATRIZ PRINCIPAL
        for(i=0; i<mat.length-2; i++){//recorre la matriz recibida
            for(int j=0; j<mat[0].length-1; j++){
                
                //Para definir numero de decimales: String.format(5.56733D, "%3.3f")
                matrizCompleta += String.format("%3.3f", mat[i][j]) + "\t";
                if(auxExiteEcuacionLarga[j]==true){ //si hay ecuacion larga en esa columna
                    matrizCompleta += "\t";
                }
            }
            matrizCompleta += "| " + String.format("%3.3f", mat[i][mat[0].length-1]) + "\n";
        }
            //??ltima fila (de ecuaci??n)
        for(int columna=0; columna < mat[0].length; columna++){
            boolean banderaHayM = false;
            
            //if(columna>0 && columna < mat[0].length-1 && auxExiteEcuacionLarga[columna-1]==true && auxExiteEcuacionLarga[columna]==false) matrizCompleta += "\t";
            //Coeficiente
            if(columna == mat[0].length-1){
                 //if(auxExiteEcuacionLarga[columna-1]==true) matrizCompleta += "\t";
                 matrizCompleta += "| ";
               
            }
            
            if(mat[mat.length-2][columna]!=0){
            //if(mat[mat.length-2][columna]<-0.001D && mat[mat.length-2][columna]>0.001D){
                //matriz += mat[mat.length-2][columna] + "M";
                  matrizCompleta += String.format("%3.3f", mat[mat.length-2][columna]) + "M";  
                
                banderaHayM = true;
            }
                if(banderaHayM == true && mat[mat.length-1][columna]>0) matrizCompleta += "+";
                if(mat[mat.length-1][columna]!=0 || banderaHayM == false)matrizCompleta+= String.format("%3.3f", mat[mat.length-1][columna]); 
            matrizCompleta+= "\t";
        }
        matrizCompleta+="\n";
         //matrizCompleta+=Arrays.toString(auxExiteEcuacionLarga);
        return matrizCompleta;
    } 
    
    public static String imprimirMatrizMinimizacion(float[][] mat, int var, int res) {
        String matrizCompleta  = "";
        //PRIMERA FILA
        int i, columnaAux = 0;
        for(i=1; i<=var; i++){
            matrizCompleta += "x" + i + "\t\t";
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "s" + i + "\t\t";
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "A" + i + "\t\t";
        }
        matrizCompleta += "| Constantes\n";
        //MATRIZ PRINCIPAL
        for(i=0; i<mat.length-2; i++){//recorre la matriz recibida
            for(int j=0; j<mat[0].length-1; j++){
                
                //Para definir numero de decimales: String.format(5.56733D, "%3.3f")
                matrizCompleta += String.format("%3.3f", mat[i][j]) + "\t\t";
            }
            matrizCompleta += "| " + String.format("%3.3f", mat[i][mat[0].length-1]) + "\n";
        }
            //??ltima fila (de ecuaci??n)
        for(int columna=0; columna < mat[0].length; columna++){
            boolean banderaHayM = false;
            
            
            //if(columna>0 && columna < mat[0].length-1 && auxExiteEcuacionLarga[columna-1]==true && auxExiteEcuacionLarga[columna]==false) matrizCompleta += "\t";
            //Coeficiente
            if(columna == mat[0].length-1){
                 //if(auxExiteEcuacionLarga[columna-1]==true) matrizCompleta += "\t";
                 matrizCompleta += "| ";
               
            }
            
            if(mat[mat.length-2][columna]!=0){
            //if(mat[mat.length-2][columna]<-0.001D && mat[mat.length-2][columna]>0.001D){
                //matriz += mat[mat.length-2][columna] + "M";
                  matrizCompleta += String.format("%3.3f", mat[mat.length-2][columna]) + "M";  
                
                banderaHayM = true;
            }
                if(banderaHayM == true && mat[mat.length-1][columna]>0) matrizCompleta += "+";
                if(mat[mat.length-1][columna]!=0 || banderaHayM == false)matrizCompleta+= String.format("%3.3f", mat[mat.length-1][columna]); 
            matrizCompleta+= "\t";
            
            if(comprobarEcuacionLarga(mat[mat.length-1][columna], mat[mat.length-2][columna])==false){
                matrizCompleta += "\t";
            }
        }
        matrizCompleta+="\n";
         //matrizCompleta+=Arrays.toString(auxExiteEcuacionLarga);
        return matrizCompleta;
    } 

    public static String imprimirMatrizMinimizacion1(Fraccion[][] mat, int var, int res) {
        String matrizCompleta  = "";
        boolean auxExiteEcuacionLarga[] = new boolean[mat[0].length-1];
        //PRIMERA FILA
        int i, columnaAux = 0;
        for(i=1; i<=var; i++){
            matrizCompleta += "x" + i + "\t";
                if(comprobarEcuacionLarga(mat[mat.length-1][columnaAux], mat[mat.length-2][columnaAux])==true){
                    matrizCompleta += "\t";
                    auxExiteEcuacionLarga[columnaAux] = true;
                }
                columnaAux++;
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "s" + i + "\t";
                if(comprobarEcuacionLarga(mat[mat.length-1][columnaAux], mat[mat.length-2][columnaAux])==true){
                    matrizCompleta += "\t";
                    auxExiteEcuacionLarga[columnaAux] = true;
                }
                columnaAux++; 
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "A" + i + "\t";
                if(comprobarEcuacionLarga(mat[mat.length-1][columnaAux], mat[mat.length-2][columnaAux])==true){
                    matrizCompleta += "\t";
                    auxExiteEcuacionLarga[columnaAux] = true;
                }
                columnaAux++;
        }
        matrizCompleta += "| Constantes\n";
        
        //MATRIZ PRINCIPAL
        for(i=0; i<mat.length-2; i++){//recorre la matriz recibida
            for(int j=0; j<mat[0].length-1; j++){ //columna
                matrizCompleta += mat[i][j].toString() + "\t";
                if(auxExiteEcuacionLarga[j]==true){ //si hay ecuacion larga en esa columna
                    matrizCompleta += "\t";
                }
            }
            matrizCompleta += "| " + mat[i][mat[0].length-1].toString() + "\n";
        }
        //1M-5000
        //??ltima fila (de ecuaci??n)
        String auxS = "";
        for(int columna=0; columna < mat[0].length; columna++){
            auxS = "";
            boolean banderaHayM = false;
            //Agregar tabulador
            if(columna>0 && auxExiteEcuacionLarga[columna-1]==true && agregarTabulador(mat[mat.length-1][columna-1], mat[mat.length-2][columna-1])==true){
                auxS += "\t";
            }
            //Coeficiente
            if(columna == mat[0].length-1){
                auxS += "| ";
            }
            if(mat[mat.length-2][columna].getNumerador()!=0){
                auxS += mat[mat.length-2][columna].toString() + "M";
                banderaHayM = true;
            }
            //Independiente
                if(banderaHayM == true && mat[mat.length-1][columna].getNumerador()>0){
                    auxS += "+";
                }
                if(mat[mat.length-1][columna].getNumerador()!=0 || banderaHayM == false){
                    auxS+= mat[mat.length-1][columna].toString();
                } 
             
             matrizCompleta+= auxS; 
             
            matrizCompleta+= "\t";
            //matrizCompleta+= "\n";
            
        }
        matrizCompleta+="\n";
        //matrizCompleta+=Arrays.toString(auxExiteEcuacionLarga);
        return matrizCompleta;
    }

    public static String imprimirMatrizMinimizacion(Fraccion[][] mat, int var, int res) {
        String matrizCompleta  = "";
        //PRIMERA FILA
        int i, columnaAux = 0;
        for(i=1; i<=var; i++){
            matrizCompleta += "x" + i + "\t\t";
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "s" + i + "\t\t";
        }
        for(i=1; i<=res; i++){
            matrizCompleta += "A" + i + "\t\t";
        }
        matrizCompleta += "| Constantes\n";
        
        //MATRIZ PRINCIPAL
        for(i=0; i<mat.length-2; i++){//recorre la matriz recibida
            for(int j=0; j<mat[0].length-1; j++){ //columna
                matrizCompleta += mat[i][j].toString() + "\t\t";
            }
            matrizCompleta += "| " + mat[i][mat[0].length-1].toString() + "\n";
        }
        //1M-5000
        //??ltima fila (de ecuaci??n)
        String auxS = "";
        for(int columna=0; columna < mat[0].length; columna++){
            auxS = "";
            boolean banderaHayM = false;
            //Agregar tabulador
            if(columna>0){
                auxS += "\t";
            }
            //Coeficiente
            if(columna == mat[0].length-1){
                auxS += "| ";
            }
            if(mat[mat.length-2][columna].getNumerador()!=0){
                auxS += mat[mat.length-2][columna].toString() + "M";
                banderaHayM = true;
            }
            //Independiente
                if(banderaHayM == true && mat[mat.length-1][columna].getNumerador()>0){
                    auxS += "+";
                }
                if(mat[mat.length-1][columna].getNumerador()!=0 || banderaHayM == false){
                    auxS+= mat[mat.length-1][columna].toString();
                } 
             
             matrizCompleta+= auxS; 
             
            //matrizCompleta+= "\n";
            if(comprobarEcuacionLarga(mat[mat.length-1][columna], mat[mat.length-2][columna])==false){
                matrizCompleta += "\t";
            }
            //matrizCompleta += comprobarEcuacionLarga(mat[mat.length-1][columna], mat[mat.length-2][columna]);
        }
        matrizCompleta+="\n";
        //matrizCompleta+=Arrays.toString(auxExiteEcuacionLarga);
        return matrizCompleta;
    }
    
    private static boolean comprobarEcuacionLarga(Fraccion independiente, Fraccion coeficiente) {
        // mat[mat.length-1][10].toString().length() + mat[mat.length-2][10].toString().length() + 2 -> >0 >0
        // mat[mat.length-1][10].toString().length() + 1 -> >0 <=0
        // mat[mat.length-2][10].toString().length() -> <=0 >0
        
        //Coeficiente: -1   Independiente: 5000
        //matrizCompleta += "\nCoeficiente: " + coeficiente.toString() + " " + coeficiente.toString().length()  + "   Independiente: " + independiente.toString() + "  " + " " + independiente.toString().length() + " ";
        if(coeficiente.getNumerador()!=0 && independiente.getNumerador()!=0){
            //matrizCompleta+= "  Long: " +(coeficiente.toString().length() + independiente.toString().length() + 2);
            if((coeficiente.toString().length() + independiente.toString().length())>=7){
                //matrizCompleta+= "  Entro ";
                return true;
            }
        }
        if(coeficiente.getNumerador()!=0){
            if((coeficiente.toString().length() +1)>=8) return true;
        }
        if(independiente.getNumerador()!=0){
            if((independiente.toString().length())>=8) return true;
        }
        return false;
    }
    private static boolean comprobarEcuacionLarga(Float independiente, Float coeficiente) {
        if(independiente!=0 && coeficiente!=0) return true;
        /*if(independiente>=200 || independiente<=-30) return true;
        if(coeficiente>=200 || coeficiente<=-30) return true;*/
        return false;
    }

    private static boolean agregarTabulador(Fraccion independiente, Fraccion coeficiente) {
        if(coeficiente.getNumerador()!=0 && independiente.getNumerador()!=0){
            if(coeficiente.getNumerador()>0 && (coeficiente.toString().length() + independiente.toString().length())==6){
                //matrizCompleta+= "  Entro ";
                return true;
            }
        }return false;
    }
    
    private static boolean agregarTabulador(float independiente, float coeficiente) {
        if(coeficiente!=0 && independiente!=0){
            if(coeficiente>0 && (Float.valueOf(coeficiente).toString().length() + Float.valueOf(independiente).toString().length()) ==7){
                //matrizCompleta+= "  Entro ";
                return true;
            }
        }return false;
    }
    
}
