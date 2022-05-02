/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Hugo Geovani Arroyo Castorena
 */
public class Resultados extends JFrame{
    private JPanel panel;
    private JTextArea area;
    private Font fuente;
    private int agregar = 0;
    public Resultados(int variables, int tipoSimplex){ //0-> min, 1-> max 
        int ancho = 1200;
        if(variables>=4 && tipoSimplex==0){
            agregar=150;
            ancho+=agregar;
        }
        setSize(ancho,710);
        setTitle("RESULTADOS");
        setLocationRelativeTo(null);
        setResizable(false);
        fuente=new Font("Monospaced", Font.PLAIN, 13);
        initializeComponents();
        
        
    }
    

    private void initializeComponents(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        //Creacion y especificaciones para el area de texto donde se mostraran las tablas del proceso de solución
        area = new JTextArea();
        area.setBounds(15,15,1160+agregar,660);
        area.setEditable(false);
        area.setFont(this.fuente);
        
        //JScrollPane scroll = new JScrollPane(area);
        panel.add(area);
            //panel.add(scroll, BorderLayout.CENTER);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }
    
    public JTextArea getArea(){
        return area;
    }
    public void setTexto(String texto){
        this.area.setText(texto);
        //this.areaConsola.setCaretPosition(this.areaConsola.getDocument().getLength());
    }
}
