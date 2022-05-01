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
public class PanelVariables extends JPanel{
    private ArrayList<JLabel> bLabel;
    private ArrayList<JTextField> bField;

    public PanelVariables(int v){
        bLabel = new ArrayList();
        bField = new ArrayList();
        setSize(800,30);
        setLayout(null);
        initializeComponents(v);
    }

    private void initializeComponents(int v){
        getbLabel().add(new JLabel("Z = "));
        getbLabel().get(0).setBounds(0,5,25,20);
        this.add(getbLabel().get(0));
        int auxF=25;
        int auxL=65;
        for(int j=1;j<=v;j++){
            getbField().add(new JTextField());
            getbField().get(j-1).setBounds(auxF, 5, 40, 20);
            this.add(getbField().get(j-1));
            getbLabel().add(new JLabel("x"+j+" + "));
            getbLabel().get(j).setBounds(auxL, 5, 30, 20);
            this.add(getbLabel().get(j));
        auxF+=70;
        auxL+=70;
        }
        getbLabel().get(v).setText("x"+v);
        this.setVisible(true);
    }

    public ArrayList<JLabel> getbLabel() {
        return bLabel;
    }

    public ArrayList<JTextField> getbField() {
        return bField;
    }


}
