/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import objetos.Fraccion;
import objetos.Matriz;
import objetos.Simplex;
import objetos.TipoNumero;
import objetos.TipoSimplex;

/**
 *
 * @author Hugo Geovani Arroyo Castorena
 */
public class Principal extends JFrame implements ActionListener{
    private JPanel panel;
    private PanelVariables panelVar;
    private PanelRestricciones panelRes;
    private JLabel titulo,etiqueta1,etiqueta2,etiqueta3,etiqueta4,etiqueta5,etiqueta6,etiqueta7,etiqueta8;
    private JButton boton;
    private JRadioButton radioBoton1,radioBoton2,radioBoton3,radioBoton4;
    private ButtonGroup modoSimplex,modoSolucion;
    private JComboBox comboVar, comboRes;
    private JTextField cajaS; //cajaSolucion
    private JTextArea cajaC; //cajaConsiderar

    public Principal(){
        setSize(1000,800);
        setTitle("METODO SIMPLEX");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeComponents();
    }

    private void initializeComponents(){
        //Crear los objetos
        panel = new JPanel();
        panelVar = new PanelVariables(2);
        panelRes = new PanelRestricciones(2,2,true);
        titulo = new JLabel();
        etiqueta1 = new JLabel();
        etiqueta2 = new JLabel();
        etiqueta3 = new JLabel();
        etiqueta4 = new JLabel();
        etiqueta5 = new JLabel();
        etiqueta6 = new JLabel();
        etiqueta7 = new JLabel();
        etiqueta8 = new JLabel();
        radioBoton1 = new JRadioButton("Maximización",true);
        radioBoton2 = new JRadioButton("Minimización",false);
        radioBoton3 = new JRadioButton("Fracción",false);
        radioBoton4 = new JRadioButton("Decimal",true);
        modoSimplex = new ButtonGroup();
        modoSolucion = new ButtonGroup();
        comboVar = new JComboBox();
        comboRes = new JComboBox();
        boton = new JButton();
        cajaS = new JTextField();
        cajaC = new JTextArea();
        //Añadir el escuchador de eventos a los elementos
        boton.addActionListener(this);
        comboVar.addActionListener(this);
        comboRes.addActionListener(this);
        radioBoton1.addActionListener(this);
        radioBoton2.addActionListener(this);
        //Especificaciones de los paneles
        panel.setLayout(null);
        panel.setBackground(Color.CYAN);
        panelVar.setBounds(100, 175, 800, 30);
        panelRes.setBounds(100, 235, 800, 230);
        //Especificaciones para el titulo
        titulo.setText("SIMPLEXITO");
        titulo.setBounds(100,20,800,30);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(Color.BLACK);
        titulo.setFont(new Font("arial",Font.BOLD,20));
        //Especificaciones para las etiquetas
        etiqueta1.setText("Modo simplex:");
        etiqueta1.setBounds(100,60,120,20);
        etiqueta2.setText("Modo solución:");
        etiqueta2.setBounds(325,60,120,20);
        etiqueta3.setText("No. variables:");
        etiqueta3.setBounds(550,60,120,20);
        etiqueta4.setText("No. restricciones:");
        etiqueta4.setBounds(775,60,120,20);
        etiqueta5.setText("Función objetivo:");
        etiqueta5.setBounds(100,150,120,20);
        etiqueta6.setText("Restricciones:");
        etiqueta6.setBounds(100,210,120,20);
        etiqueta7.setText("Solución óptima:");
        etiqueta7.setBounds(100,630,120,20);
        etiqueta8.setText("Tomar en cuenta que:");
        etiqueta8.setBounds(100,475,150,20);
        //Especificaciones para los radio botones
        radioBoton1.setBounds(100,90,120,20);
        radioBoton1.setBackground(Color.CYAN);
        radioBoton2.setBounds(100,120,120,20);
        radioBoton2.setBackground(Color.CYAN);
        radioBoton3.setBounds(325,90,120,20);
        radioBoton3.setBackground(Color.CYAN);
        radioBoton4.setBounds(325,120,120,20);
        radioBoton4.setBackground(Color.CYAN);
        modoSimplex.add(radioBoton1);
        modoSimplex.add(radioBoton2);
        modoSolucion.add(radioBoton3);
        modoSolucion.add(radioBoton4);
        //Especificaciones para las listas desplegables
        comboVar.setBounds(550, 90, 70, 20);
        comboRes.setBounds(775, 90, 70, 20);
        for(int i=2;i<10;i++){
            comboVar.addItem(i);
            comboRes.addItem(i);
        }
        //Especificaciones para el boton
        boton.setText("Resolver");
        boton.setBounds(450,600,100,30);
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        //Especificaciones para la caja donde se muestra la solución y el area de cosas a tomar en cuenta
        cajaS.setBounds(100,660,800,20);
        cajaS.setEditable(false);
        cajaC.setBounds(100,500,800,90);
        cajaC.setText("*Cualquier caja de texto vacia o con caracteres no validos se tomará con valor de 0.\n"
                    + "*El etiquetado de las variables no se puede modificar y se trabaja como se muestran en pantalla.\n"
                    + "*Puede elegir llenar las cajas de texto con números enteros, decimal, fraccion o la combinacion del primero con cualquiera de los dos ultimos.\n"
                    + "*Al presionar resolver se abrirá una ventana con las tablas del proceso y podra visualizar cada una de ellas.\n"
                    + "*Usted puede cerrar la ventana de tablas y continuar ingresando nuevos problemas en la ventana principal.");
        cajaC.setBackground(Color.CYAN);
        cajaC.setEditable(false);
        //Añadir los elementos al panel
        panel.add(titulo);   
        panel.add(etiqueta1);  
        panel.add(etiqueta2);
        panel.add(etiqueta3);
        panel.add(etiqueta4);   
        panel.add(etiqueta5); 
        panel.add(etiqueta6); 
        panel.add(etiqueta7); 
        panel.add(etiqueta8); 
        panel.add(radioBoton1);
        panel.add(radioBoton2);
        panel.add(radioBoton3);
        panel.add(radioBoton4);
        panel.add(comboVar);
        panel.add(comboRes);
        panel.add(boton);
        panel.add(cajaS);
        panel.add(cajaC);
        panel.add(panelVar);
        panel.add(panelRes);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==comboVar){
            panel.remove(panelVar);
            panelVar = new PanelVariables(Integer.parseInt(comboVar.getSelectedItem().toString()));
            panelVar.setBounds(100, 175, 800, 30);
            panelVar.setBackground(Color.CYAN);
            panel.add(panelVar);
            panel.updateUI();
        }else if(e.getSource()==comboRes){
            //Para establecer el booleano a enviar
            boolean aux;
            if(radioBoton1.isSelected()){aux=true;}
            else{aux=false;}
            //Para modificar panel
            panel.remove(panelRes);
            panelRes = new PanelRestricciones(Integer.parseInt(comboVar.getSelectedItem().toString()),Integer.parseInt(comboRes.getSelectedItem().toString()),aux);
            panelRes.setBounds(100, 235, 800, 230);
            panelRes.setBackground(Color.CYAN);
            panel.add(panelRes);
            panel.updateUI();
        }else if(e.getSource()==radioBoton1 || e.getSource()==radioBoton2){
            //Para establecer el booleano a enviar
            boolean aux;
            if(radioBoton1.isSelected()){aux=true;}
            else{aux=false;}
            //Para modificar panel
            panel.remove(panelRes);
            panelRes = new PanelRestricciones(Integer.parseInt(comboVar.getSelectedItem().toString()),Integer.parseInt(comboRes.getSelectedItem().toString()),aux);
            panelRes.setBounds(100, 235, 800, 230);
            panelRes.setBackground(Color.CYAN);
            panel.add(panelRes);
            panel.updateUI();
        }else if(e.getSource()==boton){
            //Resolver por simplex maximización
            if(radioBoton1.isSelected()){
                Fraccion[][] matriz; //En caso de utilizarse se declara
                MatrizMax m = new MatrizMax(Integer.parseInt(comboVar.getSelectedItem().toString()),Integer.parseInt(comboRes.getSelectedItem().toString()));
                m.crearMatriz(panelVar, panelRes);
                m.mostrarMatriz();
                if(m.getBandera()==false){
                    matriz = Matriz.convertirMatrizRecibiendoDenominadorNumerador(m.getMatriz());
                    Resultados r = new Resultados();
                    if(radioBoton3.isSelected()){ //Modo fracción
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz));     
                        cajaS.setText(Simplex.getSolucionOptima());
                    }else{ //Modo decimal
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz)); 
                        cajaS.setText(Simplex.getSolucionOptima());
                    }
                }else{
                    Resultados r = new Resultados();
                    if(radioBoton3.isSelected()){ //Modo fracción
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz()));     
                        cajaS.setText(Simplex.getSolucionOptima());
                    }else{ //Modo decimal
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz())); 
                        cajaS.setText(Simplex.getSolucionOptima());
                    }
                }
            }//Resolver por simplex minimización
            else{
                Fraccion[][] matriz; //En caso de utilizarse se declara
                MatrizMin m = new MatrizMin(Integer.parseInt(comboVar.getSelectedItem().toString()),Integer.parseInt(comboRes.getSelectedItem().toString()));
                m.crearMatriz(panelVar, panelRes);
                m.mostrarMatriz();
                if(m.getBandera()==false){
                    matriz = Matriz.convertirMatrizRecibiendoDenominadorNumerador(m.getMatriz());
                    Resultados r = new Resultados();
                    if(radioBoton3.isSelected()){ //Modo fracción
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz));     
                        cajaS.setText(Simplex.getSolucionOptima());
                    }else{ //Modo decimal
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz)); 
                        cajaS.setText(Simplex.getSolucionOptima());
                    }
                }else{
                    Resultados r = new Resultados();
                    if(radioBoton3.isSelected()){ //Modo fracción
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz()));     
                        cajaS.setText(Simplex.getSolucionOptima());
                    }else{ //Modo decimal
                        r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz())); 
                        cajaS.setText(Simplex.getSolucionOptima());
                    }
                }
            }
        }
    }

    public static void main(String args[]){
        Principal p = new Principal();
    }

}
