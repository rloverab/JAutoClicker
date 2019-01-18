/*
 * Copyright (C) 2018 Roger Lovera
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jautoclicker;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

/**
 *
 * @author Roger Lovera
 */
public class VentanaPrincipal extends javax.swing.JFrame implements NativeKeyListener, NativeMouseInputListener{
    //Atributos 
    private String nombreArchivo;
    private Runnable robotMouse;
    private final DefaultComboBoxModel botones;
    private final DefaultComboBoxModel pulsacion;
    private final DefaultComboBoxModel accionesEspeciales;
    private final ArrayList<Integer> teclasPulsadas;
    private boolean capturarCoordenada;
    private boolean capturarAccion;    
    private DefaultTreeModel dtm;
    private DefaultMutableTreeNode nodoBucleActual;
    private Thread hilo;
    private final int BAJAR = 1;
    private final int SUBIR = -1;
    
    /**
     * Creates new form Ventana
     */
     
    public VentanaPrincipal() {             
        //Atributos        
        Accion accion;
        MouseListener listenerMouse;
        KeyListener keyListenerParaSpinner;
        TreeSelectionListener treeSelectionListener;
        FocusListener focusListener;
        
        //Instancia de objetos;
        teclasPulsadas = new ArrayList<>();
        nombreArchivo = "";
        pulsacion = new DefaultComboBoxModel(new String[] { 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SIMPLE"), 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DOBLE"), 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("MANTENER"), 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SOLTAR") });
        botones = new DefaultComboBoxModel(new String[] { 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NINGUNO"), 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("IZQUIERDO"), 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("CENTRO"), 
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DERECHO") });
        accionesEspeciales = new DefaultComboBoxModel(new String [] {
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("BUCLE"), //0: Bucle
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("CONDICIONAL"), //1: Condicional
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("BUCLE CONDICIONADO"), //2: Bucle condicionado
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: COPIAR - CONTROL C"), //3: Hotkey: Copiar - Control C
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: CORTAR - CONTROL X"), //4: Hotkey: Cortar - Control X
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: PEGAR - CONTROL V"), //5: Hotkey: Pegar - Control V
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: DESHACER - CONTROL Z"), //6: Hotkey: Deshacer - Control Z
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: REHACER - CONTROL Y"), //7: Hotkey: Rehacer - Control Y
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: CAMBIAR VENTANA - ALT TAB"), //8: Hotkey: Cambiar ventana - Alt Tab
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: CERRAR - CONTROL F4"), //9: Hotkey: Cerrar - Control F4
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: SALIR - ALT F4"), //10: Hotkey: Salir - Alt F4
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: REFRESCAR - F5"), //11: Hotkey: Refrescar - F5
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: ENTRAR - ENTER"), //12: Tecla: Entrar - Enter
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: ESCAPAR - ESC"), //13: Tecla: Escapar - Esc
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("INTRODUCIR AL PORTAPAPELES"), //14: Introducir al portapapeles
            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("LIMPIAR EL PORTAPAPELES")}); //15: Hotkey Escapar - Esc
        
        // Generación de ventana
        initComponents(); 
        
        //Listeners
        listenerMouse = new MouseListener() {            
            DefaultMutableTreeNode nodoSeleccionado;
            TreePath path;
            Accion accion;
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    System.out.println("Doble clic");
                    nodoSeleccionado = (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent();
                    accion = ((Accion)nodoSeleccionado.getUserObject());
                    
                    if(nodoSeleccionado != null && accion != null){
                        switch(accion.getTipoAccion()){
                            case Accion.ACCIONMOUSE:
                                spinX.setValue(accion.getAccionMouse().getX());
                                spinY.setValue(accion.getAccionMouse().getY());
                                cbxBoton.setSelectedItem(accion.getAccionMouse().getBotonNombre());
                                cbxPulsacion.setSelectedItem(accion.getAccionMouse().getPulsacionTipo());
                                spinRetardo.setValue(accion.getAccionMouse().getRetardo());
                                Toolkit.getDefaultToolkit().beep();
                                break;
                            case Accion.CONDICIONAL:
                                path = new TreePath(nodoSeleccionado.getPath());   
                                if(jtAcciones.isExpanded(path)){
                                    jtAcciones.collapsePath(path);
                                }else{
                                    jtAcciones.expandPath(path);
                                }
                                DialogoCondicional dlgCondicional = new DialogoCondicional(getVentanaPrincipal(), true, DialogoCondicional.CONDICIONAL);
                                dlgCondicional.setLocationRelativeTo(getVentanaPrincipal());
                                dlgCondicional.setCondicional(accion.getCondicional());
                                dlgCondicional.setVisible(true);                                                                         
                                dtm.nodeChanged(nodoSeleccionado);
                                
                                break;
                            case Accion.BUCLE_CONDICIONADO:
                                path = new TreePath(nodoSeleccionado.getPath());   
                                if(jtAcciones.isExpanded(path)){
                                    jtAcciones.collapsePath(path);
                                }else{
                                    jtAcciones.expandPath(path);
                                }
                                DialogoCondicional dlgBucleCondicionado = new DialogoCondicional(getVentanaPrincipal(), true, DialogoCondicional.BUCLE_CONDICIONADO);
                                dlgBucleCondicionado.setLocationRelativeTo(getVentanaPrincipal());
                                dlgBucleCondicionado.setBucleCondicionado(accion.getBucleCondicionado());
                                dlgBucleCondicionado.setVisible(true);                                                                         
                                dtm.nodeChanged(nodoSeleccionado);
                                break;
                            case Accion.ACCIONESPECIAL:
                                if(accion.getAccionEspecial().getAccionEspecial() == AccionEspecial.INTRODUCIR_AL_PORTAPAPELES){
                                    DialogoContenidoPortapapeles dlgContenidoPortapapeles = new DialogoContenidoPortapapeles(getVentanaPrincipal(), true);
                                    dlgContenidoPortapapeles.setAccionEspecial(accion.getAccionEspecial());
                                    dlgContenidoPortapapeles.setLocationRelativeTo(getVentanaPrincipal());
                                    dlgContenidoPortapapeles.setVisible(true);
                                    dtm.nodeChanged(nodoSeleccionado);
                                }
                                
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //Sin implementar
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Sin implementar
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //Sin implementar
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //Sin implementar
            }
        };
        keyListenerParaSpinner = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar() < '0' || e.getKeyChar() > '9'){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //Sin implementar

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //Sin implementar
            }
        };
        treeSelectionListener = new TreeSelectionListener(){
            DefaultMutableTreeNode nodoBucle;
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                nodoBucle = (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent();
                
                if(nodoBucle != null){                    
                    do{                        
                        if(nodoBucle.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("VERDADERO")) || 
                                nodoBucle.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("FALSO")) || 
                                ((Accion)nodoBucle.getUserObject()).getTipoAccion() != Accion.BUCLE){
                            nodoBucle = (DefaultMutableTreeNode)nodoBucle.getParent();
                        }                                             
                    }while(nodoBucle.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("VERDADERO")) || 
                            nodoBucle.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("FALSO")) ||
                            ((Accion)nodoBucle.getUserObject()).getTipoAccion() != Accion.BUCLE);
                  
                    nodoBucleActual = nodoBucle;
                    spinIteraciones.setValue(((Accion)nodoBucleActual.getUserObject()).getBucle().getIteraciones());                    
                }                
            }
            
        };
        focusListener = new FocusListener(){            
            @Override
            public void focusGained(FocusEvent e) {   
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run() {
                        ((JTextField)e.getSource()).selectAll();
                    }
                });
                    
                
                        
            }

            @Override
            public void focusLost(FocusEvent e) {
                //Sin implementar
            }            
        };
        // Valor iniciar de coordenadas de cursor
        lblCoordenadas.setText("("+
                MouseInfo.getPointerInfo().getLocation().x+","+
                MouseInfo.getPointerInfo().getLocation().y+")");
        
        //Preparando Spinners
        spinX.setEditor(new JSpinner.NumberEditor(spinX, "#"));
        spinY.setEditor(new JSpinner.NumberEditor(spinY, "#"));       
        ((DefaultEditor)spinIteraciones.getEditor()).getTextField().addKeyListener(keyListenerParaSpinner);
        ((DefaultEditor)spinX.getEditor()).getTextField().addKeyListener(keyListenerParaSpinner);
        ((DefaultEditor)spinY.getEditor()).getTextField().addKeyListener(keyListenerParaSpinner);
        ((DefaultEditor)spinRetardo.getEditor()).getTextField().addKeyListener(keyListenerParaSpinner);        
        ((DefaultEditor)spinIteraciones.getEditor()).getTextField().addFocusListener(focusListener);
        ((DefaultEditor)spinX.getEditor()).getTextField().addFocusListener(focusListener);
        ((DefaultEditor)spinY.getEditor()).getTextField().addFocusListener(focusListener);
        ((DefaultEditor)spinRetardo.getEditor()).getTextField().addFocusListener(focusListener);
                
        //Preparación JTree - Lista de acciones
        DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode();
        dtm = new DefaultTreeModel(dmtn);
        accion = new Accion();
        accion.setBucle(new Bucle());
        dmtn.setUserObject(accion);
        jtAcciones.setModel(dtm);
        jtAcciones.setSelectionInterval(0, 0);        
        jtAcciones.addTreeSelectionListener(treeSelectionListener);
        jtAcciones.addMouseListener(listenerMouse);
        
        actualizarBotones();
        
        nodoBucleActual = (DefaultMutableTreeNode)dtm.getRoot();        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panPrincipal = new javax.swing.JPanel();
        panDatos = new javax.swing.JPanel();
        cbxBoton = new javax.swing.JComboBox<>();
        lblBoton = new javax.swing.JLabel();
        lblPulsacion = new javax.swing.JLabel();
        cbxPulsacion = new javax.swing.JComboBox<>();
        lblDelay = new javax.swing.JLabel();
        spinRetardo = new javax.swing.JSpinner();
        btnAgregar = new javax.swing.JButton();
        lblCoordenada = new javax.swing.JLabel();
        btnCapturar = new javax.swing.JButton();
        spinX = new javax.swing.JSpinner();
        spinY = new javax.swing.JSpinner();
        lblX = new javax.swing.JLabel();
        lblY = new javax.swing.JLabel();
        panelInformacion = new javax.swing.JPanel();
        lblF8 = new javax.swing.JLabel();
        lvlF4 = new javax.swing.JLabel();
        lblUbicacionActial = new javax.swing.JLabel();
        lblCoordenadas = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panAccionEspecial = new javax.swing.JPanel();
        cbxAccionEspecial = new javax.swing.JComboBox<>();
        btnAgregarAccionEspecial = new javax.swing.JButton();
        panOrdenEjecucion = new javax.swing.JPanel();
        btnBorrar = new javax.swing.JButton();
        btnBajar = new javax.swing.JButton();
        btnSubir = new javax.swing.JButton();
        spinIteraciones = new javax.swing.JSpinner();
        btnPlay = new javax.swing.JButton();
        lblIterations = new javax.swing.JLabel();
        chkAnimar = new javax.swing.JCheckBox();
        chkMinimizar = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtAcciones = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        itemNuevo = new javax.swing.JMenuItem();
        itemAbrir = new javax.swing.JMenuItem();
        itemGuardar = new javax.swing.JMenuItem();
        itemGuardarComo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemSalir = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        itemManual = new javax.swing.JMenuItem();
        itemAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("jautoclicker/Bundle"); // NOI18N
        setTitle(bundle.getString("JAUTOCLICKER")); // NOI18N
        setIconImage(getIconImage());
        setResizable(false);
        setSize(new java.awt.Dimension(565, 590));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("DATOS"))); // NOI18N

        cbxBoton.setModel(botones);
        cbxBoton.setSelectedItem(0);
        cbxBoton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxBotonItemStateChanged(evt);
            }
        });

        lblBoton.setText(bundle.getString("BOTÓN")); // NOI18N

        lblPulsacion.setText(bundle.getString("PULSACIÓN")); // NOI18N

        cbxPulsacion.setModel(pulsacion);
        cbxPulsacion.setSelectedItem(0);
        cbxPulsacion.setEnabled(false);

        lblDelay.setText(bundle.getString("RETARDO (MS)")); // NOI18N

        spinRetardo.setModel(new javax.swing.SpinnerNumberModel(1000, 1, 60000, 1));

        btnAgregar.setText(bundle.getString("AGREGAR")); // NOI18N
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        lblCoordenada.setText(bundle.getString("COORDENADAS")); // NOI18N

        btnCapturar.setText(bundle.getString("CAPTURAR")); // NOI18N
        btnCapturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturarActionPerformed(evt);
            }
        });

        spinX.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2000000000, 1));

        spinY.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2000000000, 1));

        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText("X"); // NOI18N

        lblY.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblY.setText("Y"); // NOI18N

        javax.swing.GroupLayout panDatosLayout = new javax.swing.GroupLayout(panDatos);
        panDatos.setLayout(panDatosLayout);
        panDatosLayout.setHorizontalGroup(
            panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panDatosLayout.createSequentialGroup()
                        .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPulsacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(14, 14, 14)
                        .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxBoton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxPulsacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panDatosLayout.createSequentialGroup()
                        .addComponent(lblCoordenada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinX, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinY, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panDatosLayout.createSequentialGroup()
                        .addComponent(lblDelay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(64, 64, 64)
                        .addComponent(spinRetardo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panDatosLayout.createSequentialGroup()
                        .addComponent(btnCapturar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panDatosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblX, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblY, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panDatosLayout.setVerticalGroup(
            panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDatosLayout.createSequentialGroup()
                .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblX)
                    .addComponent(lblY))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCoordenada)
                    .addComponent(spinY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxBoton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBoton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPulsacion)
                    .addComponent(cbxPulsacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDelay)
                    .addComponent(spinRetardo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCapturar)
                    .addComponent(btnAgregar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInformacion.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("INFORMACIÓN"))); // NOI18N

        lblF8.setText(bundle.getString("F8: COORDENADA INSTANTÁNEA")); // NOI18N

        lvlF4.setText(bundle.getString("F4: REPRODUCIR/DETENER")); // NOI18N

        lblUbicacionActial.setText(bundle.getString("UBICACION ACTUAL:")); // NOI18N

        lblCoordenadas.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCoordenadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCoordenadas.setMaximumSize(new java.awt.Dimension(162, 29));
        lblCoordenadas.setMinimumSize(new java.awt.Dimension(192, 29));
        lblCoordenadas.setPreferredSize(new java.awt.Dimension(162, 29));

        jLabel1.setText(bundle.getString("F9: MINIMIZAR/MAXIMIZAR")); // NOI18N

        javax.swing.GroupLayout panelInformacionLayout = new javax.swing.GroupLayout(panelInformacion);
        panelInformacion.setLayout(panelInformacionLayout);
        panelInformacionLayout.setHorizontalGroup(
            panelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUbicacionActial)
                    .addComponent(lvlF4)
                    .addGroup(panelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblF8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblCoordenadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInformacionLayout.setVerticalGroup(
            panelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUbicacionActial)
                .addGap(18, 18, 18)
                .addComponent(lblCoordenadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lvlF4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblF8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        panAccionEspecial.setBorder(javax.swing.BorderFactory.createTitledBorder("Acción especial"));

        cbxAccionEspecial.setModel(accionesEspeciales);

        btnAgregarAccionEspecial.setText(bundle.getString("AGREGAR")); // NOI18N
        btnAgregarAccionEspecial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAccionEspecialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panAccionEspecialLayout = new javax.swing.GroupLayout(panAccionEspecial);
        panAccionEspecial.setLayout(panAccionEspecialLayout);
        panAccionEspecialLayout.setHorizontalGroup(
            panAccionEspecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAccionEspecialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxAccionEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarAccionEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panAccionEspecialLayout.setVerticalGroup(
            panAccionEspecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAccionEspecialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAccionEspecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxAccionEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarAccionEspecial))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panPrincipalLayout = new javax.swing.GroupLayout(panPrincipal);
        panPrincipal.setLayout(panPrincipalLayout);
        panPrincipalLayout.setHorizontalGroup(
            panPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panPrincipalLayout.createSequentialGroup()
                        .addComponent(panDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panAccionEspecial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panPrincipalLayout.setVerticalGroup(
            panPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panAccionEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panOrdenEjecucion.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ÓRDEN DE EJECUCIÓN"))); // NOI18N

        btnBorrar.setText(bundle.getString("BORRAR")); // NOI18N
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnBajar.setText(bundle.getString("BAJAR")); // NOI18N
        btnBajar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajarActionPerformed(evt);
            }
        });

        btnSubir.setText(bundle.getString("SUBIR")); // NOI18N
        btnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirActionPerformed(evt);
            }
        });

        spinIteraciones.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000000, 1));
        spinIteraciones.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinIteracionesStateChanged(evt);
            }
        });

        btnPlay.setText(bundle.getString("REPRODUCIR (F4)")); // NOI18N
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        lblIterations.setText(bundle.getString("ITERACIONES")); // NOI18N

        chkAnimar.setText(bundle.getString("ANIMAR")); // NOI18N

        chkMinimizar.setText(bundle.getString("MINIMIZAR")); // NOI18N

        jScrollPane2.setViewportView(jtAcciones);

        javax.swing.GroupLayout panOrdenEjecucionLayout = new javax.swing.GroupLayout(panOrdenEjecucion);
        panOrdenEjecucion.setLayout(panOrdenEjecucionLayout);
        panOrdenEjecucionLayout.setHorizontalGroup(
            panOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOrdenEjecucionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panOrdenEjecucionLayout.createSequentialGroup()
                        .addGroup(panOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPlay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBajar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSubir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(panOrdenEjecucionLayout.createSequentialGroup()
                        .addGroup(panOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIterations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panOrdenEjecucionLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panOrdenEjecucionLayout.createSequentialGroup()
                                        .addComponent(chkMinimizar)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(chkAnimar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panOrdenEjecucionLayout.createSequentialGroup()
                        .addComponent(spinIteraciones)
                        .addContainerGap())))
        );
        panOrdenEjecucionLayout.setVerticalGroup(
            panOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOrdenEjecucionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panOrdenEjecucionLayout.createSequentialGroup()
                        .addComponent(btnSubir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBajar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIterations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinIteraciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(chkMinimizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkAnimar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlay)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mnuArchivo.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ARCHIVO").charAt(0));
        mnuArchivo.setText(bundle.getString("ARCHIVO")); // NOI18N

        itemNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        itemNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-new.png"))); // NOI18N
        itemNuevo.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NUEVO").charAt(0));
        itemNuevo.setText(bundle.getString("NUEVO")); // NOI18N
        itemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoActionPerformed(evt);
            }
        });
        mnuArchivo.add(itemNuevo);

        itemAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        itemAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-open.png"))); // NOI18N
        itemAbrir.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ABRIR...").charAt(0));
        itemAbrir.setText(bundle.getString("ABRIR...")); // NOI18N
        itemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAbrirActionPerformed(evt);
            }
        });
        mnuArchivo.add(itemAbrir);

        itemGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        itemGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-save.png"))); // NOI18N
        itemGuardar.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDAR").charAt(0));
        itemGuardar.setText(bundle.getString("GUARDAR")); // NOI18N
        itemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarActionPerformed(evt);
            }
        });
        mnuArchivo.add(itemGuardar);

        itemGuardarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-save-as.png"))); // NOI18N
        itemGuardarComo.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDAR COMO...").charAt(0));
        itemGuardarComo.setText(bundle.getString("GUARDAR COMO...")); // NOI18N
        itemGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarComoActionPerformed(evt);
            }
        });
        mnuArchivo.add(itemGuardarComo);
        mnuArchivo.add(jSeparator1);

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/stock_exit.png"))); // NOI18N
        itemSalir.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SALIR").charAt(0));
        itemSalir.setText(bundle.getString("SALIR")); // NOI18N
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        mnuArchivo.add(itemSalir);

        jMenuBar1.add(mnuArchivo);

        mnuAyuda.setText(bundle.getString("AYUDA")); // NOI18N

        itemManual.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("MANUAL").charAt(0));
        itemManual.setText(bundle.getString("MANUAL")); // NOI18N
        itemManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemManualActionPerformed(evt);
            }
        });
        mnuAyuda.add(itemManual);

        itemAcercaDe.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ACERCA DE JAUTOCLICKER").charAt(0));
        itemAcercaDe.setText(bundle.getString("ACERCA DE JAUTOCLICKER")); // NOI18N
        itemAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAcercaDeActionPerformed(evt);
            }
        });
        mnuAyuda.add(itemAcercaDe);

        jMenuBar1.add(mnuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panOrdenEjecucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panOrdenEjecucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Modificadores     
          
    //Consultas    
    
    /**
     * Verifica si control <b>JCheckBox "Minimizar"</b> este seleccionado.
     * @return <font color=blue><b>true</b></font> si esta selecionado <br>
     * <font color=red><b>false</b></font> no está selecionado
     */
    public boolean getVentanaMinimizada(){        
        return chkMinimizar.isSelected();
    }
    
    public boolean getAnimar(){
        return chkAnimar.isSelected();
    }
    
    private VentanaPrincipal getVentanaPrincipal(){
        return this;
    }
    
    //Acciones   
    private void minimizarMaximixar(){
        if(this.getExtendedState() == JFrame.NORMAL){
            this.setExtendedState(JFrame.ICONIFIED);
        }else{
            this.setExtendedState(JFrame.NORMAL);
        }
    }
    
    private boolean guardarArchivo(boolean guardarComo){
        boolean guardar;
        String archivo;
        Archivo contenidoArchivo;
        ObjectOutputStream oos;
        JFileChooser selectorArchivo;
        
        selectorArchivo = new JFileChooser();
        
        selectorArchivo.setFileFilter(new FileNameExtensionFilter(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ARCHIVOS JAUTOCLICKER"),"jac"));
        archivo = "";
        guardar = false;
        if(guardarComo || nombreArchivo.isEmpty()){
            switch(selectorArchivo.showSaveDialog(this)){
                case JFileChooser.APPROVE_OPTION:                
                    
                    archivo = selectorArchivo.getSelectedFile().toString();
                    
                    if(archivo.length()<=4 || !archivo.substring(archivo.length()-4, archivo.length()).contains(".jac")){
                        archivo = archivo + ".jac";
                    }
                    
                    if((new File(archivo)).exists()){
                        if(JOptionPane.showConfirmDialog(
                                rootPane, 
                                java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("¿DESEA REEMPLAZAR EL ARCHIVO?"), 
                                java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ARCHIVO EXISTENTE"), 
                                JOptionPane.YES_NO_OPTION, 
                                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
                            guardar = true;
                        }
                    }else{
                        guardar = true;
                    }
                    break;
                    case JFileChooser.ERROR_OPTION:                    
                        System.out.println(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ERROR AL SELECCIONAR EL ARCHIVO"));
            }
        }else{
            archivo = nombreArchivo;
            guardar = true;
        }
        
        
        if(guardar && archivo.length() > 0){
            try {             
                oos = new ObjectOutputStream(new FileOutputStream(archivo));                                         
                contenidoArchivo = new Archivo(dtm);
                contenidoArchivo.setAnimar(chkAnimar.isSelected());
                contenidoArchivo.setMinimizar(chkMinimizar.isSelected());
                oos.writeObject(contenidoArchivo);
                oos.close();                
                JOptionPane.showMessageDialog(
                        rootPane, 
                        java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ARCHIVO GUARDADO EXITOSAMENTE"), 
                        java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDADO"), 
                        JOptionPane.INFORMATION_MESSAGE);                
                nombreArchivo = archivo;
                this.setTitle(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("JAUTOCLICKER") + " - " + this.soloNombreArchivo());
            } catch (IOException ex) {
                System.out.println(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NO SE PUDE GUARDAR EL ARCHIVO:")+ex);
            }
        }   
        return guardar;
    }
    
    private void abrirArchivo(){        
        String archivo;
        ObjectInputStream ois;
        Object aux;
        JFileChooser selectorArchivo;
        
        selectorArchivo = new JFileChooser();
        
        selectorArchivo.setFileFilter(new FileNameExtensionFilter(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ARCHIVOS JAUTOCLICKER"),"jac"));
        if(selectorArchivo.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            archivo = selectorArchivo.getSelectedFile().toString();
            if((new File(archivo).exists())){
                try {  
                    ois = new ObjectInputStream(new FileInputStream(archivo));                    
                    aux = ois.readObject();
                    
                    if((aux != null) && (aux instanceof Archivo)){
                        dtm = ((Archivo)aux).getListaAcciones();
                        jtAcciones.setModel(dtm);
                        chkAnimar.setEnabled(true);
                        chkAnimar.setSelected(((Archivo)aux).getAnimar());                        
                        chkMinimizar.setEnabled(true);
                        chkMinimizar.setSelected(((Archivo)aux).getMinimizar());
                        jtAcciones.setSelectionRow(0);
                        this.actualizarBotones();
                        this.actualizarControles(false);
                        nombreArchivo = archivo;
                        this.setTitle(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("JAUTOCLICKER") + " - " + this.soloNombreArchivo());
                    }
                } catch (IOException | ClassNotFoundException ex) { 
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(
                            rootPane,
                            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("EL ARCHIVO SELECCIONADO ES INCOMPATIBLE"),
                            java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("¡ERROR!"),
                            JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(
                        rootPane, 
                        java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("EL ARCHIVO NO EXISTE"), 
                        java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("¡ERROR!"), 
                        JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    
    private void agregarNodo(Accion accion){
        DefaultMutableTreeNode nodoSeleccionado, nodoNuevo;
        
        nodoSeleccionado = (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent();
        
        if(nodoSeleccionado == null){
            nodoSeleccionado = (DefaultMutableTreeNode)dtm.getRoot();
        }
        
        nodoNuevo = new DefaultMutableTreeNode(accion);
        if(accion.getTipoAccion() == Accion.CONDICIONAL){
            nodoNuevo.add(new DefaultMutableTreeNode(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("VERDADERO")));
            nodoNuevo.add(new DefaultMutableTreeNode(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("FALSO")));
        }
        
        if(nodoSeleccionado.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("VERDADERO")) || 
                nodoSeleccionado.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("FALSO"))){
            dtm.insertNodeInto(
                    nodoNuevo,
                    nodoSeleccionado,
                    dtm.getChildCount(nodoSeleccionado));
            jtAcciones.expandPath(new TreePath(nodoSeleccionado.getPath()));
            jtAcciones.setSelectionPath(new TreePath(nodoNuevo.getPath()));
            
        }else{
            switch(((Accion)nodoSeleccionado.getUserObject()).getTipoAccion()){
                case Accion.BUCLE:                
                case Accion.BUCLE_CONDICIONADO:
                    dtm.insertNodeInto(
                            nodoNuevo, 
                            nodoSeleccionado,
                            dtm.getChildCount(nodoSeleccionado));
                    break;
                case Accion.CONDICIONAL:                                        
                    dtm.insertNodeInto(
                            nodoNuevo, 
                            (DefaultMutableTreeNode)nodoSeleccionado.getChildAt(0),
                            dtm.getChildCount((DefaultMutableTreeNode)nodoSeleccionado.getChildAt(0)));
                    jtAcciones.expandPath(new TreePath(((DefaultMutableTreeNode)nodoSeleccionado.getChildAt(0))).getParentPath());
                    jtAcciones.setSelectionPath(new TreePath(nodoNuevo.getPath()));
                    break;
                case Accion.ACCIONMOUSE:
                case Accion.ACCIONESPECIAL:
                    dtm.insertNodeInto(
                            nodoNuevo, 
                            (DefaultMutableTreeNode)nodoSeleccionado.getParent(),
                            dtm.getIndexOfChild(nodoSeleccionado.getParent(), nodoSeleccionado)+1);
            }
            jtAcciones.expandPath(jtAcciones.getSelectionPath());

            //seleccionarNodo(nodoNuevo);                  
        }
        
        if(accion.getTipoAccion() == Accion.CONDICIONAL){
            jtAcciones.expandPath(new TreePath(nodoNuevo.getPath()));
            //jtAcciones.setSelectionPath(new TreePath(((DefaultMutableTreeNode)nodoNuevo.getChildAt(0)).getPath()));
            seleccionarNodo(((DefaultMutableTreeNode)nodoNuevo.getChildAt(0)));
            jtAcciones.scrollPathToVisible(new TreePath(((DefaultMutableTreeNode)nodoNuevo.getChildAt(1)).getPath()));
        }else{
            seleccionarNodo(nodoNuevo);
        }
    }
    
    private void expandirTodo(){
        int filas = jtAcciones.getRowCount();
        for(int i = 0; i < filas; i++){
            jtAcciones.expandRow(i);
            filas = jtAcciones.getRowCount();
        }
    }
    
    public void seleccionarNodo(DefaultMutableTreeNode nodo){
        int fila = jtAcciones.getRowForPath(new TreePath(nodo.getPath()));
        jtAcciones.setSelectionRow(fila);
        jtAcciones.scrollRowToVisible(fila);
        jtAcciones.revalidate();
        jtAcciones.repaint();
    }
    
    private void agregarAccionMouse(){        
        Accion accion;
        AccionMouse accionMouse;
        accionMouse = new AccionMouse();
        accion = new Accion();
        
        accionMouse.setCoordenada((int)spinX.getValue(), (int)spinY.getValue());

        accionMouse.setRetardo((int)spinRetardo.getValue());
        
        if(cbxBoton.getSelectedIndex() != 0){
            switch(cbxBoton.getSelectedIndex()){ //Asigna el botón
                case 1:
                    accionMouse.setBoton(MouseEvent.BUTTON1);
                    break;
                case 2:
                    accionMouse.setBoton(MouseEvent.BUTTON2);                
                    /*if(Validador.esWindows()){
                        accionMouse.setBoton(MouseEvent.BUTTON2);                
                    }else{
                        accionMouse.setBoton(MouseEvent.BUTTON3);                
                    }*/
                    break;                
                case 3:
                    accionMouse.setBoton(MouseEvent.BUTTON3);                
                    /*if(Validador.esWindows()){
                        accionMouse.setBoton(MouseEvent.BUTTON3);                
                    }else{
                        accionMouse.setBoton(MouseEvent.BUTTON2);                
                    }*/
            }
            
            switch(cbxPulsacion.getSelectedIndex()){
                case 0:
                    accionMouse.setPulsacion(AccionMouse.CLICK_SIMPLE);
                    break;
                case 1:
                    accionMouse.setPulsacion(AccionMouse.CLICK_DOBLE);
                    break;
                case 2:
                    accionMouse.setPulsacion(AccionMouse.CLICK_MANTENER);
                    break;
                case 3:
                    accionMouse.setPulsacion(AccionMouse.CLICK_SOLTAR);                
            }
        }else{            
            accionMouse.setBoton(MouseEvent.NOBUTTON);
            accionMouse.setPulsacion(AccionMouse.CLICK_NINGUNO);
        }        
        
        accion.setAccionMouse(accionMouse);
        
        agregarNodo(accion);      
        actualizarBotones();
    }
    
    private void agregarAccionEspecial(){
        Accion accion;
        accion = null;
        
        switch(cbxAccionEspecial.getSelectedIndex()){
            case 0: //Bucle
                accion = new Accion();
                accion.setBucle(new Bucle());

                break;
            case 1: //Condicional
                DialogoCondicional dlgCondicional = new DialogoCondicional(this, true, DialogoCondicional.CONDICIONAL);
                dlgCondicional.setLocationRelativeTo(this);
                dlgCondicional.setVisible(true);
                if(dlgCondicional.getCondicional() != null){
                    accion = new Accion();
                    accion.setCondicional(dlgCondicional.getCondicional());
                }                
                break;
            case 2: //Bucle condicionado
                DialogoCondicional dlgBucleCondicional = new DialogoCondicional(this, true, DialogoCondicional.BUCLE_CONDICIONADO);
                dlgBucleCondicional.setLocationRelativeTo(this);
                dlgBucleCondicional.setVisible(true);
                if(dlgBucleCondicional.getBucleCondicionado() != null){
                    accion = new Accion();
                    accion.setBucleCondicionado(dlgBucleCondicional.getBucleCondicionado());
                }                
                break;
            case 3: //Copiar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.COPIAR));                                
                break;
            case 4: //Cortar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.CORTAR));                
                break;
            case 5: //Pegar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.PEGAR));                
                break;
            case 6: //Deshacer
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.DESHACER));
                break;
            case 7: //Rehacer
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.REHACER));
                break;
            case 8: //Cambiar ventana
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.CAMBIAR_VENTANA));
                break;
            case 9: //Cerrar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.CERRAR));
                break;
            case 10: //Salir
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.SALIR));
                break;
            case 11: //Refrescar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.REFRESCAR));
                break;
            case 12: //Entrar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.ESCAPAR));
                break;
            case 13: //Escapar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.ESCAPAR));
                break;
            case 14: //
                DialogoContenidoPortapapeles dlgContenidoPortapapeles =  new DialogoContenidoPortapapeles(this, true);
                dlgContenidoPortapapeles.setLocationRelativeTo(this);
                dlgContenidoPortapapeles.setVisible(true);
                if(dlgContenidoPortapapeles.getAccionEspecial() != null){
                    accion = new Accion();
                    accion.setAccionEspecial(dlgContenidoPortapapeles.getAccionEspecial());
                }
                break;
            case 15: //Limpiar el portapapeles
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.LIMPIAR_PORTAPAPELES));
        }
        
        if(accion != null){
            agregarNodo(accion);
        }
    }
        
    private void actualizarBotones(){  
        boolean arbolNoVacio;
        
        if((robotMouse != null && !((RobotMouse)robotMouse).estaDetenido())){
            arbolNoVacio = false;
        }else{
            arbolNoVacio = jtAcciones.getRowCount() > 1;
        }     
        
        btnPlay.setEnabled(arbolNoVacio); 
    }
    
    private String soloNombreArchivo(){
        if(!nombreArchivo.isEmpty()){
            for(int i = nombreArchivo.length()-1; i > 0; i--){
                if((nombreArchivo.charAt(i)+"").equals(System.getProperty("file.separator"))){
                    return nombreArchivo.substring(i+1, nombreArchivo.length());
                }
            }
        }
        return "";
    }
    
    private void activarControles(boolean activar){
        spinX.setEnabled(activar);
        spinY.setEnabled(activar);
        spinRetardo.setEnabled(activar);
        spinIteraciones.setEnabled(activar);
        cbxBoton.setEnabled(activar);        
        cbxPulsacion.setEnabled(activar && cbxBoton.getSelectedIndex() > 0);
        btnCapturar.setEnabled(activar);
        btnAgregar.setEnabled(activar);
        btnAgregarAccionEspecial.setEnabled(activar);
        btnSubir.setEnabled(activar);
        btnBajar.setEnabled(activar);
        btnBorrar.setEnabled(activar);
        chkAnimar.setEnabled(activar);
        chkMinimizar.setEnabled(activar);
        itemNuevo.setEnabled(activar);
        itemAbrir.setEnabled(activar);
        itemGuardar.setEnabled(activar);
        itemGuardarComo.setEnabled(activar);
        
    }
    
    private void actualizarIteraciones(){
        ((Accion)nodoBucleActual.getUserObject()).getBucle().setIteraciones((int)spinIteraciones.getValue());                        
            dtm.nodeChanged(nodoBucleActual);        
    }
    
    private void moverClicks(int direccion){        
        DefaultMutableTreeNode nodoSeleccionado, nodoPadre, aux;
        int indiceSeleccionado;
        int filaArbol;
        boolean expandir;
        int factor;
        
        factor = direccion;            
        
        nodoSeleccionado = (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent();        
        filaArbol = jtAcciones.getLeadSelectionRow();  
        
        if(nodoSeleccionado != null){ 
            
            if(!(nodoSeleccionado.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("VERDADERO")) || 
                    nodoSeleccionado.toString().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("FALSO")))){
                if(nodoSeleccionado.getParent() != null){ 
                    indiceSeleccionado = dtm.getIndexOfChild( 
                            nodoSeleccionado.getParent(), 
                            nodoSeleccionado);

                    if((indiceSeleccionado > 0 && direccion == SUBIR) || 
                            (indiceSeleccionado < nodoSeleccionado.getParent().getChildCount()-1 && direccion == BAJAR)){ //Si se obtuvo el indice del nodo seleccionado
                        expandir = jtAcciones.isExpanded(filaArbol);
                        aux = nodoSeleccionado; 
                        nodoPadre = (DefaultMutableTreeNode) nodoSeleccionado.getParent(); 

                        dtm.removeNodeFromParent(nodoSeleccionado); 
                        dtm.insertNodeInto(aux, nodoPadre, indiceSeleccionado + factor); 

                        switch (direccion){
                            case SUBIR:
                                for (int i = filaArbol; i > 0; i--){
                                    jtAcciones.setSelectionRow(i);
                                    this.seleccionarNodo((DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent());
                                    if(aux == (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent()){
                                        if(expandir){
                                            jtAcciones.expandRow(i);
                                        }
                                        break;
                                    }
                                }
                                break;
                            case BAJAR:
                                for (int i = filaArbol; i < jtAcciones.getRowCount(); i++){
                                    jtAcciones.setSelectionRow(i);
                                    this.seleccionarNodo((DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent());
                                    if(aux == (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent()){
                                        if(expandir){
                                            jtAcciones.expandRow(i);
                                        }
                                        break;
                                    }
                                }
                        }
                    }                
                }
            }   
        }
    } 
    
    private void borrarClicks(){ 
        DefaultMutableTreeNode dmtn;
        TreeNode aux;
        int indice;
        
        dmtn = (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent();
        indice = dmtn.getParent().getIndex(dmtn);
        if(indice > 0){
            aux = (DefaultMutableTreeNode)dmtn.getParent().getChildAt(indice - 1);            
        }else{
            if(dmtn.getParent().getChildCount() > 1){
                aux = (DefaultMutableTreeNode)dmtn.getParent().getChildAt(indice + 1);                
            }else{
                aux = (DefaultMutableTreeNode)dmtn.getParent();         
            }
        }  
        
        if(!dmtn.isRoot()){
            dtm.removeNodeFromParent(dmtn);            
        } 
        for(int i = 0; i < jtAcciones.getRowCount(); i++){
            jtAcciones.setSelectionRow(i);
            if(aux == (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent()){
                break;
            }
        }
        actualizarBotones();
    }
    
    /**
     * Ajusta los controles en función de si se va a <u><b>reproducir</b></u> o a 
     * <u><b>detener</b></u> las acciones programadas del cursor
     * @param reproducir Se pasa por parametros el valor
     * <font color=blue><b>true</b></font> si se va a reproducir acciones o el 
     * valor <font color=red><b>false</b></font> si se van a detener.
     */
    public void actualizarControles(boolean reproducir){
        if(reproducir){
            btnPlay.setText(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DETENER (F4)"));
        }else{
            btnPlay.setText(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("REPRODUCIR (F4)"));
        }        
        activarControles(!reproducir);         
    }
    
    /**
     * <b>Reproduce</b> o <b>detiene</b> las acciones programadas del cursor.
     */
    public void reproducirDetener(){   
        this.expandirTodo();
        jtAcciones.setSelectionRow(0);
        if(jtAcciones.getRowCount() > 1){
            btnPlay.requestFocus();
            if(robotMouse != null && ((RobotMouse)robotMouse).estaDetenido()){
                robotMouse = null;
            }
            
            if(robotMouse == null){
                robotMouse = new RobotMouse(
                        (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent(), 
                        chkAnimar.isSelected(), 
                        this);
                //new Thread(robotMouse).start();
                ((Thread)robotMouse).start();
                
                
            }else{           
                
                ((RobotMouse)robotMouse).detener();
                
                robotMouse = null; 
            }
        }      
    }
    
    private void nuevo(){
        nombreArchivo = "";
        spinX.setValue(0);
        spinY.setValue(0);
        spinRetardo.setValue(1000);
        this.vaciarTreeAcciones();        
        
        ((Accion)nodoBucleActual.getUserObject()).getBucle().setIteraciones(1);
        
        spinIteraciones.setValue(1);
        cbxBoton.setSelectedIndex(0);
        cbxPulsacion.setSelectedIndex(0);
        chkMinimizar.setSelected(false);
        chkAnimar.setSelected(false);                
        this.setTitle(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("JAUTOCLICKER"));
    }
    
    private boolean permitirSalida(){
        if(JOptionPane.showConfirmDialog(
                rootPane, 
                java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("¿DESEA GUARDAR LOS CAMBIOS PRIMERO?"), 
                java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDAR CAMBIOS"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
            if(guardarArchivo(nombreArchivo.isEmpty())){
                return true;
            }               
        }else{
            return true;
        }        
        return false;
    }
    
    private void salir(){        
        if(permitirSalida()){            
            JNativeHook.cerrar();
            System.runFinalization();
            System.exit(0);   
        }
    }
    
    private void vaciarTreeAcciones(){     
        while(jtAcciones.getRowCount() > 1){
            jtAcciones.setSelectionRow(1);         
            this.borrarClicks();
        }
        jtAcciones.setSelectionRow(0);
    }
    
    //Métodos abstractos de la clase NativeKeyListener
    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) { 
        /*Sin implementar*/
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {  
        int keyCode;
        keyCode = nke.getKeyCode();
        
        if(!teclasPulsadas.contains(keyCode)){
            teclasPulsadas.add(keyCode);
        }        
        if(teclasPulsadas.size() <= 1){
            switch(keyCode){
                case NativeKeyEvent.VC_F4:    
                    reproducirDetener();
                    break;
                case NativeKeyEvent.VC_F8:                                                    
                    spinX.setValue(MouseInfo.getPointerInfo().getLocation().x);
                    spinY.setValue(MouseInfo.getPointerInfo().getLocation().y);
                    Toolkit.getDefaultToolkit().beep();
                    break;
                case NativeKeyEvent.VC_F9:
                    minimizarMaximixar();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        int keyCode;
        keyCode = nke.getKeyCode();
        if(teclasPulsadas.contains(keyCode)){
            teclasPulsadas.remove(teclasPulsadas.indexOf(keyCode));
        }
    }
    
    //Metodos abstractos de la clase NativeMouseInputListener
    @Override
    public void nativeMouseClicked(NativeMouseEvent nme) {
        /*Sin implementar*/
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nme) {        
        int boton = nme.getButton();     
        System.out.println(nme.getButton());
        if (capturarCoordenada){ //Evalua si se captura de las coordenadas
            spinX.setValue(nme.getX()); //Asigna coordenadas capturadas
            spinY.setValue(nme.getY());
                        
            if (capturarAccion){ //Evalua si se captura la acción de los botones                  
                switch(boton){
                    case 2: //Botón derecho en JNativeHook
                        if(Validador.esWindows()){
                            cbxBoton.setSelectedIndex(3);//Botón derecho en Java
                        }else{
                            cbxBoton.setSelectedIndex(2);//Botón central en Java
                        }                        
                        break;
                    case 3: //Boton central JNativeHook
                        if(Validador.esWindows()){
                            cbxBoton.setSelectedIndex(2);//Botón central en Java
                        }else{
                            cbxBoton.setSelectedIndex(3);//Botón derecho en Java
                        }                        
                        break;
                    default: //Botón izquierdo
                        cbxBoton.setSelectedIndex(1); //Botón izquierdo
                }                
            }else{
                cbxBoton.setSelectedIndex(0);
            }            
        } 
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nme) {        
        if(capturarCoordenada){
            capturarCoordenada = capturarAccion = false; // Desactiva la captura del evento 
            setExtendedState(JFrame.NORMAL); //Restaura la ventana
            requestFocus(); //Enfoca la ventana
        }
    }
    
    @Override
    public void nativeMouseMoved(NativeMouseEvent nme) {
        int x, y;
        
        x = nme.getX();
        y = nme.getY();
        if(x < 0){
            x = 0;
        }

        if (y < 0){
            y = 0;
        }
        
        lblCoordenadas.setText("(" + x + "," + y + ")");
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nme) {
        /*Sin implementar*/
    }
    
    //Métodos abstractos de la clase Image
    @Override
    public Image getIconImage(){
        return Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("recursos/iconJAC-16x18.png"));
    }
    
    private void btnCapturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarActionPerformed
        capturarCoordenada = true;
        capturarAccion = true;
        this.setExtendedState(JFrame.ICONIFIED); //Minimiza la ventana
        
    }//GEN-LAST:event_btnCapturarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        this.agregarAccionMouse();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrarClicks();  
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        moverClicks(this.SUBIR);
    }//GEN-LAST:event_btnSubirActionPerformed

    private void btnBajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajarActionPerformed
        moverClicks(this.BAJAR);
    }//GEN-LAST:event_btnBajarActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        reproducirDetener();        
    }//GEN-LAST:event_btnPlayActionPerformed

    private void spinIteracionesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinIteracionesStateChanged
        actualizarIteraciones();
    }//GEN-LAST:event_spinIteracionesStateChanged

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        salir();
    }//GEN-LAST:event_itemSalirActionPerformed

    private void itemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNuevoActionPerformed
        if(JOptionPane.showConfirmDialog(
                rootPane, 
                java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("¿DESEA GUARDAR LOS CAMBIOS PRIMERO?"), 
                java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDAR CAMBIOS"), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
            if(guardarArchivo(nombreArchivo.isEmpty())){
                nuevo();
            }               
        }else{
            nuevo();
        }        
    }//GEN-LAST:event_itemNuevoActionPerformed

    private void cbxBotonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxBotonItemStateChanged
        cbxPulsacion.setEnabled(cbxBoton.getSelectedIndex() != 0);                
    }//GEN-LAST:event_cbxBotonItemStateChanged

    private void itemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarActionPerformed
        guardarArchivo(false);
    }//GEN-LAST:event_itemGuardarActionPerformed

    private void itemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAbrirActionPerformed
        abrirArchivo();
    }//GEN-LAST:event_itemAbrirActionPerformed

    private void itemGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarComoActionPerformed
        guardarArchivo(true);
    }//GEN-LAST:event_itemGuardarComoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        salir();
    }//GEN-LAST:event_formWindowClosing

    private void itemAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAcercaDeActionPerformed
        // TODO add your handling code here:  
        AcercaDe acercaDe;
        acercaDe = new AcercaDe(this, true);
        acercaDe.setLocationRelativeTo(this);
        acercaDe.setVisible(true);

    }//GEN-LAST:event_itemAcercaDeActionPerformed

    private void itemManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemManualActionPerformed
        if(Desktop.isDesktopSupported()){        
            try {
                if(!this.getLocale().getLanguage().equals(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ES"))){                 
                    Desktop.getDesktop().open(new File(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("MANUAL_EN.PDF")));                    
                }else{                    
                    Desktop.getDesktop().open(new File(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("MANUAL_ES.PDF")));
                }
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);                
            }
        }
    }//GEN-LAST:event_itemManualActionPerformed

    private void btnAgregarAccionEspecialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAccionEspecialActionPerformed
        // TODO add your handling code here:
        this.agregarAccionEspecial();
    }//GEN-LAST:event_btnAgregarAccionEspecialActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JNativeHook jNativeHook;
                    jNativeHook = new JNativeHook();
                    
                    VentanaPrincipal ventanaPrincipal;                    
                    ventanaPrincipal = new VentanaPrincipal();
                    
                    jNativeHook.addKeyListener(ventanaPrincipal);
                    jNativeHook.addMouseListener(ventanaPrincipal);
                    ventanaPrincipal.setLocationRelativeTo(null);
                    ventanaPrincipal.setAlwaysOnTop(true);
                    
                    ventanaPrincipal.setVisible(true);
                } catch (NativeHookException ex) {                    
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);                    
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarAccionEspecial;
    private javax.swing.JButton btnBajar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCapturar;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnSubir;
    private javax.swing.JComboBox<String> cbxAccionEspecial;
    private javax.swing.JComboBox<String> cbxBoton;
    private javax.swing.JComboBox<String> cbxPulsacion;
    private javax.swing.JCheckBox chkAnimar;
    private javax.swing.JCheckBox chkMinimizar;
    private javax.swing.JMenuItem itemAbrir;
    private javax.swing.JMenuItem itemAcercaDe;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemGuardarComo;
    private javax.swing.JMenuItem itemManual;
    private javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTree jtAcciones;
    private javax.swing.JLabel lblBoton;
    private javax.swing.JLabel lblCoordenada;
    private javax.swing.JLabel lblCoordenadas;
    private javax.swing.JLabel lblDelay;
    private javax.swing.JLabel lblF8;
    private javax.swing.JLabel lblIterations;
    private javax.swing.JLabel lblPulsacion;
    private javax.swing.JLabel lblUbicacionActial;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JLabel lvlF4;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JPanel panAccionEspecial;
    private javax.swing.JPanel panDatos;
    private javax.swing.JPanel panOrdenEjecucion;
    private javax.swing.JPanel panPrincipal;
    private javax.swing.JPanel panelInformacion;
    private javax.swing.JSpinner spinIteraciones;
    private javax.swing.JSpinner spinRetardo;
    private javax.swing.JSpinner spinX;
    private javax.swing.JSpinner spinY;
    // End of variables declaration//GEN-END:variables
}
