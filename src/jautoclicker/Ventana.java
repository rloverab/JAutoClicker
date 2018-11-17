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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

/**
 *
 * @author Roger Lovera
 */
public class Ventana extends javax.swing.JFrame implements NativeKeyListener, NativeMouseInputListener{
    //Atributos 
    private String nombreArchivo;
    private Runnable robotMouse;
    private Acciones acciones;
    private final DefaultComboBoxModel botones;
    private final DefaultComboBoxModel pulsacion;    
    private int hashCode;
    private ArrayList<Integer> teclasPulsadas;
    private boolean capturarCoordenada;
    private boolean capturarAccion;
    private JNativeHook jNativeHook;    
    private DefaultTreeModel dtm;
    private final int BAJAR = 1;
    private final int SUBIR = -1;
    
    //SubClases
    class KeySpinner extends KeyAdapter{
        @Override
        public void keyTyped(KeyEvent e){
            if(e.getKeyChar() < '0' || e.getKeyChar() > '9'){
                e.consume();
            }
        }
    }
    
    /**
     * Creates new form Ventana
     */
     
    public Ventana() {     
        teclasPulsadas = new ArrayList<>();
        nombreArchivo = "";
        pulsacion = new DefaultComboBoxModel(new String[] { java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SIMPLE"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DOBLE"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("MANTENER"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SOLTAR") });
        botones = new DefaultComboBoxModel(new String[] { java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NINGUNO"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("IZQUIERDO"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("CENTRO"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DERECHO") });
        
        initComponents(); 
        
        lblCoordenadas.setText("("+
                MouseInfo.getPointerInfo().getLocation().x+","+
                MouseInfo.getPointerInfo().getLocation().y+")");
        
        spinX.setEditor(new JSpinner.NumberEditor(spinX, "#"));
        spinY.setEditor(new JSpinner.NumberEditor(spinY, "#"));       
        
        //Spinner KeyTyped
        ((JSpinner.DefaultEditor)spinIteraciones.getEditor()).getTextField().addKeyListener(new KeySpinner());
        ((JSpinner.DefaultEditor)spinX.getEditor()).getTextField().addKeyListener(new KeySpinner());
        ((JSpinner.DefaultEditor)spinY.getEditor()).getTextField().addKeyListener(new KeySpinner());
        ((JSpinner.DefaultEditor)spinDelay.getEditor()).getTextField().addKeyListener(new KeySpinner());
        
        acciones = new Acciones();        
        acciones.addAccion(new Accion());
        acciones.getAccion(0).setBucle(new Bucle());
        
        DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode();
        dtm = new DefaultTreeModel(dmtn);
        dmtn.setUserObject(acciones.getAccion(0));
        jtAcciones.setModel(dtm);
        jtAcciones.setSelectionInterval(0, 0);        
        
        
        dtm.addTreeModelListener(new TreeModelListener(){
            @Override
            public void treeNodesChanged(TreeModelEvent e) {                
                
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {
            }

            @Override
            public void treeStructureChanged(TreeModelEvent e) {
                boolean arbolNoVacio = jtAcciones.getRowCount() > 1;
                btnPlay.setEnabled(arbolNoVacio); 
            }
        });
        
        jtAcciones.addTreeSelectionListener(new TreeSelectionListener(){
            @Override
            public void valueChanged(TreeSelectionEvent e) {                  
                boolean nodoSeleccionado = jtAcciones.getRowForPath(e.getNewLeadSelectionPath()) > 0;
                btnSubir.setEnabled(nodoSeleccionado);
                btnBajar.setEnabled(nodoSeleccionado);
                btnBorrar.setEnabled(nodoSeleccionado); 
            }        
        });
        
        actualizarBotones();  
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
        spinDelay = new javax.swing.JSpinner();
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
        jPanel1 = new javax.swing.JPanel();
        cbxAccionEspecial = new javax.swing.JComboBox<>();
        btnAgregar1 = new javax.swing.JButton();
        panelOrdenEjecucion = new javax.swing.JPanel();
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

        spinDelay.setModel(new javax.swing.SpinnerNumberModel(1000, 1, 60000, 1));

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
                        .addComponent(spinDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(spinDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(lblCoordenadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelInformacionLayout.createSequentialGroup()
                        .addGroup(panelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUbicacionActial)
                            .addComponent(lblF8, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lvlF4)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInformacionLayout.setVerticalGroup(
            panelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUbicacionActial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCoordenadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lvlF4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblF8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Acción especial"));

        cbxAccionEspecial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bucle", "Condicional", "Hotkey: Copiar - Control C", "Hotkey: Cortar - Control X", "Hotkey: Pegar - Control V", "Hotkey: Cerrar - Control F4", "Hotkey: Salir - Alt F4", "Hotkey: Deshacer - Control Z", "Hotkey: Rehacer - Control Y", "Hotkey: Cambiar ventana - Alt Tab" }));

        btnAgregar1.setText(bundle.getString("AGREGAR")); // NOI18N
        btnAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cbxAccionEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxAccionEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar1))
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
                        .addComponent(panelInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panPrincipalLayout.setVerticalGroup(
            panPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelOrdenEjecucion.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ÓRDEN DE EJECUCIÓN"))); // NOI18N

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
        spinIteraciones.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                spinIteracionesFocusLost(evt);
            }
        });
        spinIteraciones.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                spinIteracionesCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
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

        javax.swing.GroupLayout panelOrdenEjecucionLayout = new javax.swing.GroupLayout(panelOrdenEjecucion);
        panelOrdenEjecucion.setLayout(panelOrdenEjecucionLayout);
        panelOrdenEjecucionLayout.setHorizontalGroup(
            panelOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrdenEjecucionLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrdenEjecucionLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(panelOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBajar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSubir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblIterations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spinIteraciones)))
                    .addGroup(panelOrdenEjecucionLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelOrdenEjecucionLayout.createSequentialGroup()
                                .addComponent(chkMinimizar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(chkAnimar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelOrdenEjecucionLayout.setVerticalGroup(
            panelOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrdenEjecucionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrdenEjecucionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelOrdenEjecucionLayout.createSequentialGroup()
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
                    .addComponent(panPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelOrdenEjecucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelOrdenEjecucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Modificadores     
    public void setJNativeHook(JNativeHook jNativeHook){
        this.jNativeHook = jNativeHook;
    }
                
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
    
    //Acciones   
    private void minimizarMaximixar(){
        if(this.getExtendedState() == JFrame.NORMAL){
            this.setExtendedState(JFrame.ICONIFIED);
        }else{
            this.setExtendedState(JFrame.NORMAL);
        }
    }
    
    private boolean guardarArchivo(boolean guardarComo){
        /*
        boolean guardar;
        String archivo;
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
                oos.writeObject(listaMoveMouse);
                oos.close();                
                JOptionPane.showMessageDialog(
                        rootPane, 
                        java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ARCHIVO GUARDADO EXITOSAMENTE"), 
                        java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDADO"), 
                        JOptionPane.INFORMATION_MESSAGE);                
                nombreArchivo = archivo;
                hashCode = listaMoveMouse.hashCodeAlterno();
                this.setTitle(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("JAUTOCLICKER") + " - " + this.soloNombreArchivo());
            } catch (IOException ex) {
                System.out.println(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NO SE PUDE GUARDAR EL ARCHIVO:")+ex);
            }
        }   
        return guardar;
        */
        return false;
        
    }
    
    private void abrirArchivo(){
        /*
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

                    if((aux != null) && (aux instanceof ListaMoveMouse)){
                        listaMoveMouse = (ListaMoveMouse)aux;
                        //llenarTabla();
                        nombreArchivo = archivo;
                        hashCode = listaMoveMouse.hashCodeAlterno();                 
                        this.setTitle(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("JAUTOCLICKER") + " - " + this.soloNombreArchivo());
                    }
                } catch (IOException | ClassNotFoundException ex) { 
                    System.out.println(ex);
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("EL ARCHIVO NO EXISTE"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("¡ERROR!"), JOptionPane.ERROR_MESSAGE);
            }
        }
        */
    }
    
    private void agregarNodo(Accion accion){
        DefaultMutableTreeNode nodoSeleccionado, nodoNuevo;
        
        nodoSeleccionado = (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent();
        nodoNuevo = null;
        if(nodoSeleccionado == null){
            nodoSeleccionado = (DefaultMutableTreeNode)dtm.getRoot();
        }
        
        switch(((Accion)nodoSeleccionado.getUserObject()).getTipoAccion()){
            case Accion.BUCLE:
            case Accion.CONDICIONAL:
                nodoNuevo = new DefaultMutableTreeNode(accion);
                dtm.insertNodeInto(
                        nodoNuevo, 
                        nodoSeleccionado,
                        dtm.getChildCount(nodoSeleccionado));
                break;
            case Accion.ACCIONMOUSE:
            case Accion.ACCIONESPECIAL:
                nodoNuevo = new DefaultMutableTreeNode(accion);
                if(((Accion)nodoSeleccionado.getUserObject()).getTipoAccion() == Accion.BUCLE || 
                        ((Accion)nodoSeleccionado.getUserObject()).getTipoAccion() == Accion.CONDICIONAL){
                    dtm.insertNodeInto(
                            nodoNuevo, 
                            nodoSeleccionado,
                            dtm.getChildCount(nodoSeleccionado));
                }else{
                    dtm.insertNodeInto(
                            nodoNuevo, 
                            (DefaultMutableTreeNode)nodoSeleccionado.getParent(),
                            dtm.getChildCount((DefaultMutableTreeNode)nodoSeleccionado.getParent()));
                }
        }
        //dtm.reload();
        jtAcciones.expandPath(jtAcciones.getSelectionPath());
        if(nodoNuevo != null){            
            for(int i = 0; i < jtAcciones.getRowCount(); i++){
                jtAcciones.setSelectionRow(i);
                if(((DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent()).getUserObject().equals(accion)){                    
                    break;
                }
            }
        }
        
        
       
    }
    
    private void agregarAccionMouse(){        
        Accion accion;
        AccionMouse accionMouse;
        accionMouse = new AccionMouse();
        accion = new Accion();
        
        accionMouse.setCoordenada((int)spinX.getValue(), (int)spinY.getValue());

        accionMouse.setRetardo((int)spinDelay.getValue());
        
        if(cbxBoton.getSelectedIndex() != 0){
            switch(cbxBoton.getSelectedIndex()){ //Asigna el botón
                case 1:
                    accionMouse.setBoton(MouseEvent.BUTTON1);
                    break;
                case 2:
                    if(Validador.esWindows()){
                        accionMouse.setBoton(MouseEvent.BUTTON2);                
                    }else{
                        accionMouse.setBoton(MouseEvent.BUTTON3);                
                    }
                    break;                
                case 3:
                    if(Validador.esWindows()){
                        accionMouse.setBoton(MouseEvent.BUTTON3);                
                    }else{
                        accionMouse.setBoton(MouseEvent.BUTTON2);                
                    }
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

        acciones.addAccion(accion); //Agrega el objeto a la lista
        
        agregarNodo(accion);        
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
                accion = new Accion();
                accion.setCondicional(new Condicional());
                
                break;
            case 2: //Copiar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.COPIAR));                
                
                break;
            case 3: //Cortar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.CORTAR));                
                
                break;
            case 4: //Pegar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.PEGAR));
                
                break;
            case 5: //Cerrar
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.CERRAR));
                
                break;
            case 6: //Salir
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.SALIR));
                
                break;
            case 7: //Deshacer
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.DESHACER));
                
                break;
            case 8: //Rehacer
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.REHACER));
                
                break;
            case 9: //Cambiar ventana
                accion = new Accion();
                accion.setAccionEspecial(new AccionEspecial(AccionEspecial.CAMBIAR_VENTANA));
        }
        
        if(accion != null){
            acciones.addAccion(accion);
            agregarNodo(accion);
        }
    }
        
    private void actualizarBotones(){
        System.out.println("nodoSeleccionado: " + jtAcciones.getLeadSelectionRow());
        System.out.println("arbolNoVacio: " + jtAcciones.getRowCount());
        
        
        boolean nodoSeleccionado = jtAcciones.getLeadSelectionRow() > 0;
        boolean arbolNoVacio = jtAcciones.getRowCount() > 1;
        if(!(robotMouse != null && ((RobotMouse)robotMouse).estaDetenido())){
/*            btnSubir.setEnabled(tblClicks.getSelectedRow() >= 0 && modelo.getRowCount() > 1);
            btnBajar.setEnabled(tblClicks.getSelectedRow() >= 0 && modelo.getRowCount() > 1);        
            btnBorrar.setEnabled(tblClicks.getSelectedRow() >= 0 && modelo.getRowCount() > 0);
            btnPlay.setEnabled(modelo.getRowCount() > 0); 
            spinIteraciones.setEnabled(tblClicks.getRowCount() > 0);
            chkMinimizar.setEnabled(tblClicks.getRowCount() > 0);
            chkAnimar.setEnabled(tblClicks.getRowCount() > 0);*/
            btnSubir.setEnabled(nodoSeleccionado && arbolNoVacio);
            btnBajar.setEnabled(nodoSeleccionado && arbolNoVacio);
            btnBorrar.setEnabled(nodoSeleccionado && arbolNoVacio);
            btnPlay.setEnabled(arbolNoVacio); 
            spinIteraciones.setEnabled(arbolNoVacio);
            chkMinimizar.setEnabled(arbolNoVacio);
            chkAnimar.setEnabled(arbolNoVacio);
        }
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
        spinDelay.setEnabled(activar);
        spinIteraciones.setEnabled(activar);
        cbxBoton.setEnabled(activar);        
        cbxPulsacion.setEnabled(activar && cbxBoton.getSelectedIndex() > 0);
        btnCapturar.setEnabled(activar);
        btnAgregar.setEnabled(activar);
        btnSubir.setEnabled(activar);
        btnBajar.setEnabled(activar);
        btnBorrar.setEnabled(activar);
//        tblClicks.setEnabled(activar);
        chkAnimar.setEnabled(activar);
        chkMinimizar.setEnabled(activar);
    }
    
    private void actualizarIteraciones(int indiceBucle){
        /*
        if(listaMoveMouse.getCantidadObjetos() > 0){
            for(int i = listaMoveMouse.getPosicionInicialBucle(indiceBucle); i <= listaMoveMouse.getPosicionFinalBucle(indiceBucle); i++){
                listaMoveMouse.getObjeto(i).setIteraciones((int)spinIteraciones.getValue());
            }
        }
        */
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
    
    private void borrarClicks(){ 
        DefaultMutableTreeNode dmtn; //aux;
        TreeNode aux;
        int indice;
        
        dmtn = (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent();
        indice = dmtn.getParent().getIndex(dmtn);
        if(indice > 0){
            //indice--;
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
        //dtm.reload();
        for(int i = 0; i < jtAcciones.getRowCount(); i++){
            jtAcciones.setSelectionRow(i);
            if(aux == (DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent()){
                break;
            }
        }
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
        /*
        if(tblClicks.getRowCount() > 0){
            btnPlay.requestFocus();
            if(robotMouse != null && ((RobotMouse)robotMouse).estaDetenido()){
                robotMouse = null;
            }
            
            if(robotMouse == null){            
                robotMouse = new RobotMouse(listaMoveMouse, chkAnimar.isSelected(), this);
                new Thread(robotMouse).start();
            }else{                
                ((RobotMouse)robotMouse).detener();
                robotMouse = null; 
            }
        }*/
    }
    
    private void nuevo(){
        nombreArchivo = "";
        //listaMoveMouse.vaciar();
        spinX.setValue(0);
        spinY.setValue(0);
        spinDelay.setValue(1000);
        spinIteraciones.setValue(1);
        cbxBoton.setSelectedIndex(0);
        cbxPulsacion.setSelectedIndex(0);
        chkMinimizar.setSelected(false);
        chkAnimar.setSelected(false);
        /*
        hashCode = listaMoveMouse.hashCodeAlterno();
        while(modelo.getRowCount() > 0){
            modelo.removeRow(modelo.getRowCount()-1);
        }
        */
        this.setTitle(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("JAUTOCLICKER"));
    }
    
    private boolean permitirSalida(){
        /*
        if (listaMoveMouse.hashCodeAlterno() != hashCode){
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
        }else{
            return true;
        }*/
        return false;
    }
    
    private void salir(){
        /*
        boolean salir;
        salir = false;
        
        if (listaMoveMouse.hashCodeAlterno() != hashCode){
            if(JOptionPane.showConfirmDialog(
                    rootPane, 
                    "¿Desea guardar los cambios primero?", 
                    "Guardar cambios", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
                if(guardarArchivo(nombreArchivo.isEmpty())){
                    salir = true;
                }               
            }else{
                salir = true;
            }
        }else{
            salir = true;
        }
        */
        if(permitirSalida()){
            /*
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            jNativeHook.cerrar();
            System.runFinalization();
            System.exit(0);
            
        }
        
            
    }
    
    private void vaciarTreeAcciones(){
        jtAcciones.setSelectionRow(0);
        dtm.removeNodeFromParent((DefaultMutableTreeNode)jtAcciones.getLastSelectedPathComponent());
            
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
        
        lblCoordenadas.setText("("+x+","+y+")");
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
        //agregarMoveMouse();
        this.agregarAccionMouse();
        //actualizarIteraciones(0);
        //actualizarBotones();        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrarClicks();  
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        //subirClicks();        
        moverClicks(this.SUBIR);
    }//GEN-LAST:event_btnSubirActionPerformed

    private void btnBajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajarActionPerformed
        //bajarClicks();
        moverClicks(this.BAJAR);
    }//GEN-LAST:event_btnBajarActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        reproducirDetener();        
    }//GEN-LAST:event_btnPlayActionPerformed

    private void spinIteracionesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinIteracionesStateChanged
        actualizarIteraciones(0);
    }//GEN-LAST:event_spinIteracionesStateChanged

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        salir();
    }//GEN-LAST:event_itemSalirActionPerformed

    private void itemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNuevoActionPerformed
        this.vaciarTreeAcciones();
        /*
        if (listaMoveMouse.hashCodeAlterno() != hashCode){
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
        }else{
            nuevo();
        }
        */
    }//GEN-LAST:event_itemNuevoActionPerformed

    private void spinIteracionesCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_spinIteracionesCaretPositionChanged
        try {
            spinIteraciones.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.actualizarIteraciones(0);
    }//GEN-LAST:event_spinIteracionesCaretPositionChanged

    private void spinIteracionesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_spinIteracionesFocusLost
        this.actualizarIteraciones(0);
    }//GEN-LAST:event_spinIteracionesFocusLost

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
                if(!this.getLocale().getLanguage().equals("es")){                 
                    Desktop.getDesktop().open(new File("Manual_EN.pdf"));                    
                }else{                    
                    Desktop.getDesktop().open(new File("Manual_ES.pdf"));
                }
            } catch (IOException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);                
            }
        }
    }//GEN-LAST:event_itemManualActionPerformed

    private void btnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar1ActionPerformed
        // TODO add your handling code here:
        this.agregarAccionEspecial();
    }//GEN-LAST:event_btnAgregar1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar1;
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
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JPanel panDatos;
    private javax.swing.JPanel panPrincipal;
    private javax.swing.JPanel panelInformacion;
    private javax.swing.JPanel panelOrdenEjecucion;
    private javax.swing.JSpinner spinDelay;
    private javax.swing.JSpinner spinIteraciones;
    private javax.swing.JSpinner spinX;
    private javax.swing.JSpinner spinY;
    // End of variables declaration//GEN-END:variables
}
