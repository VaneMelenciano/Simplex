/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Hugo Geovani Arroyo Castorena
 */
public class PanelRestricciones extends JPanel{
    private ArrayList<ArrayList<JLabel>> aLabel;
    private ArrayList<ArrayList<JTextField>> aField;

    public PanelRestricciones(int v, int r, boolean b){
        aLabel = new ArrayList();
        aField = new ArrayList();
        setSize(800,230);
        setLayout(null);
        initializeComponents(v, r, b);
    }

    private void initializeComponents(int v, int r, boolean b){
        agregarComponentes(v, r);
        agregarDesigualdades(v, r, b);
    }

    private void agregarComponentes(int v, int r){
        //INICIALIZAR LOS COMPONENTES NECESARIOS
        //Agregar los arraylist necesarios según la cantidad de restricciones y a su vez agregar etiquetas y cajas de texto necesarias.
        int cont=0,contF=0;
        for(int i=0;i<r;i++){
            getaLabel().add(new ArrayList());
            getaField().add(new ArrayList());
            int auxF=0;
            int auxL=40;
            for(int j=1;j<=v;j++){
                getaField().get(i).add(new JTextField());
                getaField().get(i).get(j-1).setBounds(auxF, 5+cont, 40, 20);
                this.add(getaField().get(i).get(j-1));
                getaLabel().get(i).add(new JLabel("x"+j+" + "));
                getaLabel().get(i).get(j-1).setBounds(auxL, 5+cont, 30, 20);
                this.add(getaLabel().get(i).get(j-1));
            auxF+=70;
            auxL+=70;
            contF=auxF;
            }
            getaField().get(i).add(new JTextField());
            getaField().get(i).get(v).setBounds(auxF, 5+cont, 40, 20);
            this.add(getaField().get(i).get(v));
        cont+=25;
        }
        this.setVisible(true);
    }

    private void agregarDesigualdades(int v, int r,boolean b){
        if(b==true){//Cuando es maximización <=
            for(int i=0;i<r;i++)
                getaLabel().get(i).get(v-1).setText("x"+v+" ≤");
        }else{//Cuando es minimización >=
            for(int i=0;i<r;i++)
                getaLabel().get(i).get(v-1).setText("x"+v+" ≥");
        }
    }

    public ArrayList<ArrayList<JLabel>> getaLabel() {
        return aLabel;
    }

    public ArrayList<ArrayList<JTextField>> getaField() {
        return aField;
    }

}
