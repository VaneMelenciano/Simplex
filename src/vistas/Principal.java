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
    private JButton botonResolver;
    private JButton botonEjemplos;
    private JRadioButton radioBoton1Max,radioBoton2Min,radioBoton3Fraccion,radioBoton4Decimal;
    private ButtonGroup modoSimplex,modoSolucion;
    private JComboBox comboVar, comboRes;
    private JTextField cajaS; //cajaSolucion
    private JTextArea cajaC; //cajaConsiderar
    private int numeroVariables, numeroRestricciones;
    private int contadorEjemplos = 0;
    
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
        radioBoton1Max = new JRadioButton("Maximización",true);
        radioBoton2Min = new JRadioButton("Minimización",false);
        radioBoton3Fraccion = new JRadioButton("Fracción",false);
        radioBoton4Decimal = new JRadioButton("Decimal",true);
        modoSimplex = new ButtonGroup();
        modoSolucion = new ButtonGroup();
        comboVar = new JComboBox();
        comboRes = new JComboBox();
        botonResolver = new JButton();
        this.botonEjemplos = new JButton();
        cajaS = new JTextField();
        cajaC = new JTextArea();
        //Añadir el escuchador de eventos a los elementos
        botonResolver.addActionListener(this);
        this.botonEjemplos.addActionListener(this);
        comboVar.addActionListener(this);
        comboRes.addActionListener(this);
        radioBoton1Max.addActionListener(this);
        radioBoton2Min.addActionListener(this);
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
        radioBoton1Max.setBounds(100,90,120,20);
        radioBoton1Max.setBackground(Color.CYAN);
        radioBoton2Min.setBounds(100,120,120,20);
        radioBoton2Min.setBackground(Color.CYAN);
        radioBoton3Fraccion.setBounds(325,90,120,20);
        radioBoton3Fraccion.setBackground(Color.CYAN);
        radioBoton4Decimal.setBounds(325,120,120,20);
        radioBoton4Decimal.setBackground(Color.CYAN);
        modoSimplex.add(radioBoton1Max);
        modoSimplex.add(radioBoton2Min);
        modoSolucion.add(radioBoton3Fraccion);
        modoSolucion.add(radioBoton4Decimal);
        //Especificaciones para las listas desplegables
        comboVar.setBounds(550, 90, 70, 20);
        comboRes.setBounds(775, 90, 70, 20);
        //comboVar.addItem(0);
        //comboRes.addItem(0);
        for(int i=2;i<10;i++){
            comboVar.addItem(i);
            comboRes.addItem(i);
        }
        //Especificaciones para el boton
        botonResolver.setText("Resolver");
        botonResolver.setBounds(520,600,100,30);
        botonResolver.setHorizontalAlignment(SwingConstants.CENTER);
        botonEjemplos.setText("Ejemplos");
        botonEjemplos.setBounds(370,600,100,30);
        botonEjemplos.setHorizontalAlignment(SwingConstants.CENTER);
        //Especificaciones para la caja donde se muestra la solución y el area de cosas a tomar en cuenta
        cajaS.setBounds(100,660,800,20);
        cajaS.setEditable(false);
        cajaC.setBounds(100,495,800,95);
        cajaC.setText("*Cualquier caja de texto vacia o con caracteres no validos se tomará con valor de 0.\n"
                    + "*El etiquetado de las variables no se puede modificar y se trabaja como se muestran en pantalla.\n"
                    + "*Puede elegir llenar las cajas de texto con números enteros, decimal, fraccion o la combinacion del primero con cualquiera de los dos ultimos.\n"
                    + "*Al presionar resolver se abrirá una ventana con las tablas del proceso y podra visualizar cada una de ellas.\n"
                    + "*Usted puede cerrar la ventana de tablas y continuar ingresando nuevos problemas en la ventana principal.\n"
                    + "*La solucion aparecera en la parte inferior de esta ventana, queda consideracion suya sustituir los resultados en la funcion objetivo :)");
        cajaC.setBackground(Color.CYAN);
        cajaC.setEditable(false);
        //Añadir los elementos al panel
        agregarElementosA_Panel();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(comboVar.getSelectedItem() == null) this.numeroVariables = 2;
        else this.numeroVariables = Integer.parseInt(comboVar.getSelectedItem().toString());
        if(comboRes.getSelectedItem() == null) this.numeroRestricciones = 2;
        else this.numeroRestricciones = Integer.parseInt(comboRes.getSelectedItem().toString());
        
        if(e.getSource()==comboVar){
            actualizarVariables();
        }else if(e.getSource()==comboRes){
            actualizarRestricciones();
        }else if(e.getSource()==radioBoton1Max || e.getSource()==radioBoton2Min){
            //Para establecer el booleano a enviar
            boolean aux;
            if(radioBoton1Max.isSelected()){aux=true;}
            else{aux=false;}
            //Para modificar panel
            panel.remove(panelRes);
            panelRes = new PanelRestricciones(this.numeroVariables,this.numeroRestricciones,aux);
            panelRes.setBounds(100, 235, 800, 230);
            panelRes.setBackground(Color.CYAN);
            panel.add(panelRes);
            panel.updateUI();
        }else if(e.getSource()==botonResolver){
            
            //Resolver por simplex maximización
            if(radioBoton1Max.isSelected()){
                resolverMaximizacion();
                
            }//Resolver por simplex minimización
            else{
                resolverMinimizacion();
            }
        }else if(e.getSource()==this.botonEjemplos){
            agregarEjemplos();
        }
    }

    public static void main(String args[]){
        Principal p = new Principal();
    }

    private void agregarEjemplos() {
        cajaS.setText("");
        switch(this.contadorEjemplos){
            case 0: ejemploCeroMaximizacion(); 
                break;
            case 1: ejemploUnoMaximizacion();
                break;
            case 2: ejemploDosMaximizacion();
                break;
            case 3: ejemploTresMaximizacion();
                break;
            case 4: ejemploCuatroMaximizacion();
                break;
            case 5: ejemploCeroMinimizacion();
                break;
            case 6: ejemploUnoMinimizacion();
                break;
            case 7: ejemploDosMinimizacion();
                break;
            case 8: ejemploTresMinimizacion();
                break;
            default: this.contadorEjemplos=-1; ejemploCuatroMinimizacion();
        }
        this.contadorEjemplos++;
    }

    private void actualizarVariables() {
        boolean aux;
        if(radioBoton1Max.isSelected()){aux=true;}
        else{aux=false;}
        //Para modificar el panel variables
        panel.remove(panelVar);
        panel.remove(panelRes);
        panelRes = new PanelRestricciones(this.numeroVariables,this.numeroRestricciones,aux);
        panelRes.setBounds(100, 235, 800, 230);
        panelRes.setBackground(Color.CYAN);

        panelVar = new PanelVariables(this.numeroVariables);
        panelVar.setBounds(100, 175, 800, 30);
        panelVar.setBackground(Color.CYAN);
        panel.add(panelVar);
        panel.add(panelRes);
        panel.updateUI();
    }

    private void agregarElementosA_Panel() {
        panel.add(titulo);   
        panel.add(etiqueta1);  
        panel.add(etiqueta2);
        panel.add(etiqueta3);
        panel.add(etiqueta4);   
        panel.add(etiqueta5); 
        panel.add(etiqueta6); 
        panel.add(etiqueta7); 
        panel.add(etiqueta8); 
        panel.add(radioBoton1Max);
        panel.add(radioBoton2Min);
        panel.add(radioBoton3Fraccion);
        panel.add(radioBoton4Decimal);
        panel.add(comboVar);
        panel.add(comboRes);
        panel.add(botonResolver);
        panel.add(this.botonEjemplos);
        panel.add(cajaS);
        panel.add(cajaC);
        panel.add(panelVar);
        panel.add(panelRes);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    private void actualizarRestricciones() {
        //Para establecer el booleano a enviar
        boolean aux;
        if(radioBoton1Max.isSelected()){aux=true;}
        else{aux=false;}
        //Para modificar panel
        panel.remove(panelRes);
        panelRes = new PanelRestricciones(this.numeroVariables,this.numeroRestricciones,aux);
        panelRes.setBounds(100, 235, 800, 230);
        panelRes.setBackground(Color.CYAN);
        panel.add(panelRes);
        panel.updateUI();
    }

    private void resolverMaximizacion() {
        Fraccion[][] matriz; //En caso de utilizarse se declara
        MatrizMax m = new MatrizMax(this.numeroVariables,this.numeroRestricciones);
        m.crearMatriz(panelVar, panelRes);
        m.mostrarMatriz();
        if(m.getBandera()==false){ //Utilizando Matriz tipo fraccion
            matriz = Matriz.convertirMatrizRecibiendoDenominadorNumerador(m.getMatriz());
            Resultados r = new Resultados(this.numeroVariables,1);
            if(radioBoton3Fraccion.isSelected()){ //Modo fracción
                r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz));     
                cajaS.setText(Simplex.getSolucionOptima());
            }else{ //Modo decimal
                r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz)); 
                cajaS.setText(Simplex.getSolucionOptima());
            }
        }else{ // Utilizando Matriz tipo float
            Resultados r = new Resultados(this.numeroVariables,1);
            if(radioBoton3Fraccion.isSelected()){ //Modo fracción
                r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz()));     
                cajaS.setText(Simplex.getSolucionOptima());
            }else{ //Modo decimal
                r.getArea().setText(Simplex.simplex(TipoSimplex.Maximizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz())); 
                cajaS.setText(Simplex.getSolucionOptima());
            }
        }
    }

    private void resolverMinimizacion() {
        Fraccion[][] matriz; //En caso de utilizarse se declara
        MatrizMin m = new MatrizMin(this.numeroVariables,this.numeroRestricciones);
        m.crearMatriz(panelVar, panelRes);
        m.mostrarMatriz();
        if(m.getBandera()==false){ // Utilizando Matriz tipo fraccion
            matriz = Matriz.convertirMatrizRecibiendoDenominadorNumerador(m.getMatriz());
            Resultados r = new Resultados(this.numeroVariables,0);
            if(radioBoton3Fraccion.isSelected()){ //Modo fracción
                r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz));     
                cajaS.setText(Simplex.getSolucionOptima());
            }else{ //Modo decimal
                r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), matriz)); 
                cajaS.setText(Simplex.getSolucionOptima());
            }
        }else{ // Utilizando Matriz tipo float
            Resultados r = new Resultados(this.numeroVariables,0);
            if(radioBoton3Fraccion.isSelected()){ //Modo fracción
                r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Fraccion, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz()));     
                cajaS.setText(Simplex.getSolucionOptima());
            }else{ //Modo decimal
                r.getArea().setText(Simplex.simplex(TipoSimplex.Minimizacion, TipoNumero.Decimal, Integer.parseInt(comboVar.getSelectedItem().toString()), Integer.parseInt(comboRes.getSelectedItem().toString()), m.getMatriz())); 
                cajaS.setText(Simplex.getSolucionOptima());
            }
        }
    }

    private void ejemploCeroMinimizacion() {
        radioBoton2Min.setSelected(true);
        radioBoton3Fraccion.setSelected(true);
        comboVar.setSelectedIndex(1); this.numeroVariables=3;
        comboRes.setSelectedIndex(0); this.numeroRestricciones=2;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("20");
        panelVar.getbField().get(variables++).setText("30");
        panelVar.getbField().get(variables++).setText("16");
        String restricciones[][] = { {"5/2", "3", "1", "3"},
                                     {"1", "3", "2", "4"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploUnoMinimizacion() {
        radioBoton2Min.setSelected(true);
        this.radioBoton4Decimal.setSelected(true);
        comboVar.setSelectedIndex(0); this.numeroVariables=2;
        comboRes.setSelectedIndex(1); this.numeroRestricciones=3;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("4");
        panelVar.getbField().get(variables++).setText("2");
        String restricciones[][] = { {"4", "1", "20"},
                                     {"2", "1", "14"},
                                    {"1", "6", "18"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploDosMinimizacion() {
        radioBoton2Min.setSelected(true);
        this.radioBoton4Decimal.setSelected(true);
        comboVar.setSelectedIndex(1); this.numeroVariables=3;
        comboRes.setSelectedIndex(0); this.numeroRestricciones=2;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("36");
        panelVar.getbField().get(variables++).setText("40");
        panelVar.getbField().get(variables++).setText("28");
        String restricciones[][] = { {"6", "5", "2", "5"},
                                     {"2", "5", "4", "3"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploTresMinimizacion() {
        radioBoton2Min.setSelected(true);
        radioBoton3Fraccion.setSelected(true);
        comboVar.setSelectedIndex(0); this.numeroVariables=2;
        comboRes.setSelectedIndex(1); this.numeroRestricciones=3;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("2");
        panelVar.getbField().get(variables++).setText("4");
        String restricciones[][] = { {"2", "1", "14"},
                                     {"1", "1", "12"},
                                    {"1", "3", "18"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploCuatroMinimizacion() {
        radioBoton2Min.setSelected(true);
        radioBoton3Fraccion.setSelected(true);
        comboVar.setSelectedIndex(2); this.numeroVariables=4;
        comboRes.setSelectedIndex(2); this.numeroRestricciones=4;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("20000");
        panelVar.getbField().get(variables++).setText("20000");
        panelVar.getbField().get(variables++).setText("20000");
        panelVar.getbField().get(variables++).setText("20000");
        String restricciones[][] = { {"2", "1", "1", "2", "24"},
                                     {"2", "2", "1", "0", "20"},
                                    {"0", "0", "2", "2", "20"},
                                    {"0", "0", "0", "4", "16"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }

    private void ejemploCeroMaximizacion() {
        radioBoton1Max.setSelected(true);
        radioBoton3Fraccion.setSelected(true);
        comboVar.setSelectedIndex(0); this.numeroVariables=2;
        comboRes.setSelectedIndex(1); this.numeroRestricciones=3;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("5");
        panelVar.getbField().get(variables++).setText("3");
        String restricciones[][] = { {"6", "2", "36"},
                                     {"5", "5", "40"},
                                    {"2", "4", "28"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploUnoMaximizacion() {
        radioBoton1Max.setSelected(true);
        radioBoton3Fraccion.setSelected(true);
        comboVar.setSelectedIndex(2); this.numeroVariables=4;
        comboRes.setSelectedIndex(1); this.numeroRestricciones=3;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("1/2");
        panelVar.getbField().get(variables++).setText("3");
        panelVar.getbField().get(variables++).setText("1");
        panelVar.getbField().get(variables++).setText("4");
        String restricciones[][] = { {"5", "1", "1", "1", "40"},
                                     {"2", "1", "1", "1", "12"},
                                    {"0", "1", "0", "9", "12"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploDosMaximizacion() {
        radioBoton1Max.setSelected(true);
        this.radioBoton4Decimal.setSelected(true);
        comboVar.setSelectedIndex(0); this.numeroVariables=2;
        comboRes.setSelectedIndex(1); this.numeroRestricciones=3;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("3");
        panelVar.getbField().get(variables++).setText("4");
        String restricciones[][] = { {"2.5", "1", "20"},
                                     {"3", "3", "30"},
                                    {"1", "2", "16"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploTresMaximizacion() {
        radioBoton1Max.setSelected(true);
        this.radioBoton4Decimal.setSelected(true);
        comboVar.setSelectedIndex(0); this.numeroVariables=2;
        comboRes.setSelectedIndex(0); this.numeroRestricciones=2;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("50");
        panelVar.getbField().get(variables++).setText("40");
        String restricciones[][] = { {"2", "3", "1500"},
                                     {"2", "1", "1000"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
    private void ejemploCuatroMaximizacion() {
        radioBoton1Max.setSelected(true);
        radioBoton3Fraccion.setSelected(true);
        comboVar.setSelectedIndex(1); this.numeroVariables=3;
        comboRes.setSelectedIndex(0); this.numeroRestricciones=2;
        int variables = 0;
        panelVar.getbField().get(variables++).setText("1");
        panelVar.getbField().get(variables++).setText("1");
        panelVar.getbField().get(variables++).setText("1");
        String restricciones[][] = { {"1", "1", "1", "3/2"},
                                     {"1", "1", "1", "1/2"}};
        for(int i=0;i<this.panelRes.getaField().size();i++){
            for(int j=0;j<this.numeroVariables+1;j++){
                this.panelRes.getaField().get(i).get(j).setText(restricciones[i][j]);
            }
        }
    }
}
