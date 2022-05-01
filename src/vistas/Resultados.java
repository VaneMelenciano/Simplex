/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Hugo Geovani Arroyo Castorena
 */
public class Resultados extends JFrame{
    private JPanel panel;
    private JTextArea area;

    public Resultados(){
        setSize(1000,700);
        setTitle("RESULTADOS");
        setLocationRelativeTo(null);
        setResizable(false);
        initializeComponents();
    }

    private void initializeComponents(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        //Creacion y especificaciones para el area de texto donde se mostraran las tablas del proceso de solución
        area = new JTextArea();
        area.setBounds(15,15,960,660);
        area.setEditable(false);
        panel.add(area);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }
    
    public JTextArea getArea(){
        return area;
    }
}
