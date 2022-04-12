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
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import simplexMin.Ecuacion;

/**
 *
 * @author Vanessa
 */
public class Matriz {
    public static float[][] matriz; //matriz de decimales
    public static Fraccion[][] matrizF; //matriz de fracciones
    public static int restricciones;
    public static int variables;
    
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
            if (abre != null) { //verifica que esté abierto
                
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);

                while ((aux = lee.readLine()) != null) {
                    texto = aux; //guarda la linea del archivo leido en el String
                    lista.add(texto); //añade el String anterior a la lista
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
            if (abre != null) { //verifica que esté abierto
                
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);

                while ((aux = lee.readLine()) != null) {
                    texto = aux; //guarda la linea del archivo leido en el String
                    lista.add(texto); //añade el String anterior a la lista
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

    public static void imprimirMatriz(Fraccion[][] nueva, Ecuacion[] e) {
        imprimirMatriz(nueva);
        Ecuacion.toString(e);
    }

    public static String imprimirMatriz(Fraccion[][] mat, int var, int res) {
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
                matriz += mat[i][j] + "\t";
            }
            matriz += "| " + mat[i][mat[0].length-1] + "\n";
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
    
}
