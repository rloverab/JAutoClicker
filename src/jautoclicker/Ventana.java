/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jautoclicker;

import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
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
    private MoveMouse moveMouse;
    private ListaMoveMouse listaMoveMouse;
    private final DefaultTableModel modelo;    
    private final String[] columnas;
    private final DefaultComboBoxModel botones;
    private final DefaultComboBoxModel pulsacion;    
    private int hashCode;
    private ArrayList<Integer> teclasPulsadas;
    private boolean capturarCoordenada;
    private boolean capturarAccion;
    
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
        teclasPulsadas = new ArrayList();
        nombreArchivo = "";
        pulsacion = new DefaultComboBoxModel(new String[] { java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SIMPLE"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DOBLE"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("MANTENER"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SOLTAR") });
        botones = new DefaultComboBoxModel(new String[] { java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NINGUNO"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("IZQUIERDO"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("CENTRO"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DERECHO") });
        
        initComponents(); 
      
        listaMoveMouse = new ListaMoveMouse();
        hashCode = listaMoveMouse.hashCodeAlterno();
        
        columnas = new String[]{"#", "X", "Y", java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("RETARDO (MS)"), java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("BOTON"),java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("PULSACION")};
        
        modelo = new DefaultTableModel(){
            private final boolean [] tableColums = {false, false, false, false, false, false};
            
            @Override
            public final boolean isCellEditable(int row, int column) {
                return this.tableColums[column];
            }
        };
        
        modelo.setColumnIdentifiers(columnas);
        
        prepararTabla();   
        
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

        jPanel4 = new javax.swing.JPanel();
        cbxBoton = new javax.swing.JComboBox<>();
        lblBoton = new javax.swing.JLabel();
        lblPulsacion = new javax.swing.JLabel();
        cbxPulsacion = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        spinDelay = new javax.swing.JSpinner();
        btnAgregar = new javax.swing.JButton();
        Coordenada = new javax.swing.JLabel();
        btnCapturar = new javax.swing.JButton();
        spinX = new javax.swing.JSpinner();
        spinY = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnBorrar = new javax.swing.JButton();
        btnBajar = new javax.swing.JButton();
        btnSubir = new javax.swing.JButton();
        spinIteraciones = new javax.swing.JSpinner();
        btnPlay = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        chkAnimar = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClicks = new javax.swing.JTable();
        chkMinimizar = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        lblF8 = new javax.swing.JLabel();
        lvlF4 = new javax.swing.JLabel();
        lblUbicacionActial = new javax.swing.JLabel();
        lblCoordenadas = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemNuevo = new javax.swing.JMenuItem();
        itemAbrir = new javax.swing.JMenuItem();
        itemGuardar = new javax.swing.JMenuItem();
        itemGuardarComo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemSalir = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("jautoclicker/Bundle"); // NOI18N
        setTitle(bundle.getString("JCLICKER")); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(565, 590));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("DATOS"))); // NOI18N

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

        jLabel3.setText(bundle.getString("RETARDO (MS)")); // NOI18N

        spinDelay.setModel(new javax.swing.SpinnerNumberModel(1000, 1, 60000, 1));

        btnAgregar.setText(bundle.getString("AGREGAR")); // NOI18N
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        Coordenada.setText(bundle.getString("COORDENADAS")); // NOI18N

        btnCapturar.setText(bundle.getString("CAPTURAR")); // NOI18N
        btnCapturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturarActionPerformed(evt);
            }
        });

        spinX.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2000000000, 1));

        spinY.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2000000000, 1));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("X");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Y");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnCapturar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spinDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPulsacion, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxBoton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxPulsacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(Coordenada, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(spinX, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinY, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Coordenada)
                    .addComponent(spinY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxBoton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBoton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPulsacion)
                    .addComponent(cbxPulsacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spinDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnCapturar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ÓRDEN DE EJECUCIÓN"))); // NOI18N

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

        jLabel4.setText(bundle.getString("ITERACIONES")); // NOI18N

        chkAnimar.setText(bundle.getString("ANIMAR")); // NOI18N

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setFocusTraversalPolicyProvider(true);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(360, 244));

        tblClicks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClicks.setFillsViewportHeight(true);
        tblClicks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tblClicks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClicksMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblClicksMousePressed(evt);
            }
        });
        tblClicks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblClicksKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblClicks);

        chkMinimizar.setText("Minimizar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBajar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSubir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPlay, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(spinIteraciones)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(chkMinimizar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(chkAnimar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSubir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBajar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
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

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("INFORMACIÓN"))); // NOI18N

        lblF8.setText(bundle.getString("F8: COORDENADA INSTANTÁNEA")); // NOI18N

        lvlF4.setText(bundle.getString("F4: REPRODUCIR/DETENER")); // NOI18N

        lblUbicacionActial.setText(bundle.getString("UBICACION ACTUAL:")); // NOI18N

        lblCoordenadas.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCoordenadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCoordenadas.setMaximumSize(new java.awt.Dimension(162, 29));
        lblCoordenadas.setMinimumSize(new java.awt.Dimension(192, 29));
        lblCoordenadas.setPreferredSize(new java.awt.Dimension(162, 29));

        jLabel1.setText("F9: Minimizar/Maximizar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCoordenadas, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUbicacionActial)
                    .addComponent(lblF8)
                    .addComponent(lvlF4)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
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

        menuArchivo.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ARCHIVO").charAt(0));
        menuArchivo.setText(bundle.getString("ARCHIVO")); // NOI18N

        itemNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        itemNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-new.png"))); // NOI18N
        itemNuevo.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NUEVO").charAt(0));
        itemNuevo.setText(bundle.getString("NUEVO")); // NOI18N
        itemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(itemNuevo);

        itemAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        itemAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-open.png"))); // NOI18N
        itemAbrir.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ABRIR...").charAt(0));
        itemAbrir.setText(bundle.getString("ABRIR...")); // NOI18N
        itemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemAbrir);

        itemGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        itemGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-save.png"))); // NOI18N
        itemGuardar.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDAR").charAt(0));
        itemGuardar.setText(bundle.getString("GUARDAR")); // NOI18N
        itemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(itemGuardar);

        itemGuardarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/document-save-as.png"))); // NOI18N
        itemGuardarComo.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("GUARDAR COMO...").charAt(0));
        itemGuardarComo.setText(bundle.getString("GUARDAR COMO...")); // NOI18N
        itemGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarComoActionPerformed(evt);
            }
        });
        menuArchivo.add(itemGuardarComo);
        menuArchivo.add(jSeparator1);

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jautoclicker/recursos/stock_exit.png"))); // NOI18N
        itemSalir.setMnemonic(java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("SALIR").charAt(0));
        itemSalir.setText(bundle.getString("SALIR")); // NOI18N
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemSalir);

        jMenuBar1.add(menuArchivo);

        menuAyuda.setText(bundle.getString("ACERCA DE...")); // NOI18N

        jMenuItem5.setText(bundle.getString("ACERCA DE JCLICKER")); // NOI18N
        jMenuItem5.setEnabled(false);
        menuAyuda.add(jMenuItem5);

        jMenuBar1.add(menuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //Modificadores 
    /**
     * Seleciona una fila de la tabla mediante su indice
     * @param indice Indice de la fila
     */
    public void setFilaSeleccionada(int indice){
        if(indice >= 0){
            tblClicks.changeSelection(indice, 0, false, false);
        }        
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
        ObjectOutputStream oos;
        JFileChooser selectorArchivo;
        
        selectorArchivo = new JFileChooser();
        
        selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos JClicker","jac"));
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
                                "¿Desea reemplazar el archivo?", 
                                "Archivo existente", 
                                JOptionPane.YES_NO_OPTION, 
                                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
                            guardar = true;
                        }
                    }else{
                        guardar = true;
                    }
                    break;
                    case JFileChooser.ERROR_OPTION:                    
                        System.out.println("Error al seleccionar el archivo");
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
                        "Archivo guardado exitosamente", 
                        "Guardado", 
                        JOptionPane.INFORMATION_MESSAGE);                
                nombreArchivo = archivo;
                hashCode = listaMoveMouse.hashCodeAlterno();
            } catch (IOException ex) {
                System.out.println("No se pude guardar el archivo:\n"+ex);
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
        
        selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos JClicker","jac"));
        if(selectorArchivo.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            archivo = selectorArchivo.getSelectedFile().toString();
            if((new File(archivo).exists())){
                try {  
                    ois = new ObjectInputStream(new FileInputStream(archivo));                    
                    aux = ois.readObject();

                    if((aux != null) && (aux instanceof ListaMoveMouse)){
                        listaMoveMouse = (ListaMoveMouse)aux;
                        llenarTabla();
                        nombreArchivo = archivo;
                        hashCode = listaMoveMouse.hashCodeAlterno();                 
                    }
                } catch (IOException | ClassNotFoundException ex) { 
                    System.out.println(ex);
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "El archivo no existe", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void prepararTabla(){
        //Modelado de la tabla
        tblClicks.setModel(modelo);        
        
        tblClicks.getColumnModel().getColumn(0).setPreferredWidth(30); // #
        tblClicks.getColumnModel().getColumn(0).setMaxWidth(30);
        tblClicks.getColumnModel().getColumn(0).setMinWidth(30);        
        tblClicks.getColumnModel().getColumn(1).setPreferredWidth(50); // X
        tblClicks.getColumnModel().getColumn(1).setMaxWidth(50);
        tblClicks.getColumnModel().getColumn(1).setMinWidth(50);
        tblClicks.getColumnModel().getColumn(2).setPreferredWidth(50); // Y
        tblClicks.getColumnModel().getColumn(2).setMaxWidth(50);
        tblClicks.getColumnModel().getColumn(2).setMinWidth(50);
        tblClicks.getColumnModel().getColumn(3).setPreferredWidth(80); // Retardo
        tblClicks.getColumnModel().getColumn(3).setMaxWidth(80);
        tblClicks.getColumnModel().getColumn(3).setMinWidth(80);
        tblClicks.getColumnModel().getColumn(4).setPreferredWidth(65); // Boton
        tblClicks.getColumnModel().getColumn(4).setMaxWidth(65);
        tblClicks.getColumnModel().getColumn(4).setMinWidth(65);     
        tblClicks.getColumnModel().getColumn(5).setPreferredWidth(85); // Pulsación
        tblClicks.getColumnModel().getColumn(5).setMaxWidth(85);
        tblClicks.getColumnModel().getColumn(5).setMinWidth(85);  
        
        tblClicks.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                int ultimoIndice = tblClicks.getRowCount()-1;
                tblClicks.changeSelection(ultimoIndice, 0, false, false);
            }
        });
        
        tblClicks.getModel().addTableModelListener(new TableModelListener(){
            @Override
            public void tableChanged(TableModelEvent e) {
                actualizarBotones();
            }
        });
    }
    
    private void agregarMoveMouse(){
        if (listaMoveMouse == null){ //Verifica que la objeto listaMoveMouse exista
            listaMoveMouse = new ListaMoveMouse(); //Instancia el objeto listaMoveMouse
        }

        if(moveMouse == null){ //Verifica que el objeto moveMouse exista
            moveMouse = new MoveMouse(); //Instancia el objeto moveMouse de ser necesario
        }            

        moveMouse.setIndice(listaMoveMouse.getCantidadObjetos()+1); //Asigna el indice tomando la cantidad e objetos en la lista

        moveMouse.setCoordenada((int)spinX.getValue(), (int)spinY.getValue());

        moveMouse.setRetardo((int)spinDelay.getValue());
        
        if(cbxBoton.getSelectedIndex() != 0){
            switch(cbxBoton.getSelectedIndex()){ //Asigna el botón
                case 1:
                    moveMouse.setBoton(MouseEvent.BUTTON1);
                    break;
                case 2:
                    if(Validador.esWindows()){
                        moveMouse.setBoton(MouseEvent.BUTTON2);                
                    }else{
                        moveMouse.setBoton(MouseEvent.BUTTON3);                
                    }
                    break;                
                case 3:
                    if(Validador.esWindows()){
                        moveMouse.setBoton(MouseEvent.BUTTON3);                
                    }else{
                        moveMouse.setBoton(MouseEvent.BUTTON2);                
                    }
            }
            
            switch(cbxPulsacion.getSelectedIndex()){
                case 0:
                    moveMouse.setPulsacion(MoveMouse.CLICK_SIMPLE);
                    break;
                case 1:
                    moveMouse.setPulsacion(MoveMouse.CLICK_DOBLE);
                    break;
                case 2:
                    moveMouse.setPulsacion(MoveMouse.CLICK_MANTENER);
                    break;
                case 3:
                    moveMouse.setPulsacion(MoveMouse.CLICK_SOLTAR);                
            }
        }else{            
            moveMouse.setBoton(MouseEvent.NOBUTTON);
            moveMouse.setPulsacion(MoveMouse.CLICK_NINGUNO);
        }        

        listaMoveMouse.agregar(moveMouse); //Agrega el objeto a la lista

        modelo.addRow(moveMouse.getVector()); //Agrega el click a la tabla
        actualizarIteraciones(0);
        moveMouse = null; //Elimina la referencia al objeto insertado en la lista
    }
    
    private void corregirIndiceTabla(){ //Corrige la numeración del campo indice en la tabla
        for (int i = 0; i < modelo.getRowCount(); i++){
            modelo.setValueAt(i+1, i, 0);
        }
    }
    
    private void llenarTabla(){ //Rellena la tabla tomando los valores de la lista
        while(modelo.getRowCount()>0){
            modelo.removeRow(modelo.getRowCount()-1);            
        }
        
        if(listaMoveMouse.getCantidadObjetos() > 0){
            for(MoveMouse mm : listaMoveMouse.getLista()){
                modelo.addRow(mm.getVector());
            }
        }        
        spinIteraciones.setValue(listaMoveMouse.getObjeto(0).getIteraciones());
    }
        
    private void actualizarBotones(){
        btnSubir.setEnabled(tblClicks.getSelectedRow() >= 0 && modelo.getRowCount() > 1);
        btnBajar.setEnabled(tblClicks.getSelectedRow() >= 0 && modelo.getRowCount() > 1);        
        btnBorrar.setEnabled(tblClicks.getSelectedRow() >= 0 && modelo.getRowCount() > 0);
        btnPlay.setEnabled(modelo.getRowCount() > 0); 
        spinIteraciones.setEnabled(tblClicks.getRowCount() > 0);
        chkMinimizar.setEnabled(tblClicks.getRowCount() > 0);
        chkAnimar.setEnabled(tblClicks.getRowCount() > 0);
    }
    
    //Trabajo en progreso
    private void bucleClicks(){
        int indiceBucle = listaMoveMouse.getIndiceBucleDisponible();
        if(tblClicks.getSelectedRowCount() > 0){            
            for(
                    int i = tblClicks.getSelectedRows()[0]; 
                    i <= tblClicks.getSelectedRows()[tblClicks.getSelectedRowCount()-1]; 
                    i++){
                modelo.setValueAt(indiceBucle, i, 6);
                listaMoveMouse.getObjeto(i).setIndiceBucle(indiceBucle);
            }
        }
    }
    //Trabajo en progreso
    
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
        chkAnimar.setEnabled(activar);
        chkMinimizar.setEnabled(activar);
    }
    
    private void actualizarIteraciones(int indiceBucle){
        if(listaMoveMouse.getCantidadObjetos() > 0){
            for(int i = listaMoveMouse.getPosicionInicialBucle(indiceBucle); i <= listaMoveMouse.getPosicionFinalBucle(indiceBucle); i++){
                listaMoveMouse.getObjeto(i).setIteraciones((int)spinIteraciones.getValue());
            }
        }
    }
    
    private void subirClicks(){ //Sube un conjunto de click en la tabla y en la lista
        int indicePrimero;
        int indiceUltimo;
        
        if(tblClicks.getSelectedRowCount() > 0){
            indicePrimero = tblClicks.getSelectedRows()[0];
            indiceUltimo = tblClicks.getSelectedRows()[tblClicks.getSelectedRowCount()-1];        
            
            if (indicePrimero > 0) {
                modelo.moveRow( //Mueve la fila entera un espacio hacia arriba
                        indicePrimero, 
                        indiceUltimo, 
                        indicePrimero-1);
                
                listaMoveMouse.subir(indicePrimero, indiceUltimo);
                
                setFilaSeleccionada(indicePrimero-1);

                tblClicks.getSelectionModel().setSelectionInterval( //Mantiene el cursor en la fila afectada
                        indicePrimero-1, 
                        indiceUltimo-1);
                
                //Actualiza los indices                
                corregirIndiceTabla();                 
            }
        }
    }
   
    private void bajarClicks(){ //Baja un conjunto de click en la tabla y en la lista
        int indicePrimero;
        int indiceUltimo;
        
        if(tblClicks.getSelectedRowCount() > 0){
            indicePrimero = tblClicks.getSelectedRows()[0];
            indiceUltimo = tblClicks.getSelectedRows()[tblClicks.getSelectedRowCount()-1];    
            
            if (indiceUltimo < tblClicks.getRowCount()-1) {
                modelo.moveRow( //Mueve la fila entera un espacio hacia abajo
                        indicePrimero,
                        indiceUltimo,
                        indicePrimero+1);
                
                listaMoveMouse.bajar(indicePrimero, indiceUltimo);
                
                setFilaSeleccionada(indiceUltimo+1);
                
                tblClicks.getSelectionModel().setSelectionInterval( //Mantiene el cursor en la fila afectada
                        indicePrimero+1,
                        indiceUltimo+1);     
                
                //Actualiza los indices                
                corregirIndiceTabla(); 
            }
        }
    }
    
    private void borrarClicks(){ //Elimina un conjunto de clicks en la tabla y en la lista
        int primero;
        int ultimo;
        
        primero = tblClicks.getSelectedRows()[0];
        ultimo = tblClicks.getSelectedRows()[tblClicks.getSelectedRowCount()-1];        
        
        if(tblClicks.getSelectedRowCount() > 0){ //Verifica que exista filas seleccionadas
            for(int i = ultimo; i >= primero; i--){ //Elimina las fils selecionadas
                modelo.removeRow(i);
                listaMoveMouse.eliminar(i);                
                if(i - 1 >= 0){
                    tblClicks.getSelectionModel().setSelectionInterval(i - 1, i - 1);
                }
            }   
            
            //Actualiza los indices            
            corregirIndiceTabla();
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
        }
    }
    
    private void nuevo(){
        nombreArchivo = "";
        listaMoveMouse.vaciar();
        spinX.setValue(0);
        spinY.setValue(0);
        spinDelay.setValue(1000);
        spinIteraciones.setValue(1);
        cbxBoton.setSelectedIndex(0);
        cbxPulsacion.setSelectedIndex(0);
        chkMinimizar.setSelected(true);
        chkAnimar.setSelected(false);
        hashCode = listaMoveMouse.hashCodeAlterno();
        while(modelo.getRowCount() > 0){
            modelo.removeRow(modelo.getRowCount()-1);
        }
    }
    
    private boolean permitirSalida(){
        if (listaMoveMouse.hashCodeAlterno() != hashCode){
            if(JOptionPane.showConfirmDialog(
                    rootPane, 
                    "¿Desea guardar los cambios primero?", 
                    "Guardar cambios", 
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
        }
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
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.runFinalization();
            System.exit(0);
        }
        
            
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
    
    private void btnCapturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturarActionPerformed
        capturarCoordenada = true;
        capturarAccion = true;
        this.setExtendedState(JFrame.ICONIFIED); //Minimiza la ventana
        
    }//GEN-LAST:event_btnCapturarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregarMoveMouse();
        actualizarIteraciones(0);
        //actualizarBotones();        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrarClicks();  
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        subirClicks();        
    }//GEN-LAST:event_btnSubirActionPerformed

    private void btnBajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajarActionPerformed
        bajarClicks();
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
        if (listaMoveMouse.hashCodeAlterno() != hashCode){
            if(JOptionPane.showConfirmDialog(
                    rootPane, 
                    "¿Desea guardar los cambios primero?", 
                    "Guardar cambios", 
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

    private void tblClicksMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClicksMousePressed
        actualizarBotones();
    }//GEN-LAST:event_tblClicksMousePressed

    private void tblClicksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClicksKeyPressed
        actualizarBotones();
    }//GEN-LAST:event_tblClicksKeyPressed

    private void itemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarActionPerformed
        guardarArchivo(false);
    }//GEN-LAST:event_itemGuardarActionPerformed

    private void itemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAbrirActionPerformed
        abrirArchivo();
    }//GEN-LAST:event_itemAbrirActionPerformed

    private void itemGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarComoActionPerformed
        guardarArchivo(true);
    }//GEN-LAST:event_itemGuardarComoActionPerformed

    private void tblClicksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClicksMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            System.out.println("Double Click");
            if(tblClicks.getSelectedRow() >= 0){
                spinX.setValue(tblClicks.getValueAt(tblClicks.getSelectedRow(), 1));
                spinY.setValue(tblClicks.getValueAt(tblClicks.getSelectedRow(), 2));
                spinDelay.setValue(tblClicks.getValueAt(tblClicks.getSelectedRow(), 3));
                cbxBoton.setSelectedItem((String)tblClicks.getValueAt(tblClicks.getSelectedRow(), 4));
                cbxPulsacion.setSelectedItem((String)tblClicks.getValueAt(tblClicks.getSelectedRow(), 5));
            }
        }
    }//GEN-LAST:event_tblClicksMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        salir();
    }//GEN-LAST:event_formWindowClosing

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
    private javax.swing.JLabel Coordenada;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBajar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCapturar;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnSubir;
    private javax.swing.JComboBox<String> cbxBoton;
    private javax.swing.JComboBox<String> cbxPulsacion;
    private javax.swing.JCheckBox chkAnimar;
    private javax.swing.JCheckBox chkMinimizar;
    private javax.swing.JMenuItem itemAbrir;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemGuardarComo;
    private javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblBoton;
    private javax.swing.JLabel lblCoordenadas;
    private javax.swing.JLabel lblF8;
    private javax.swing.JLabel lblPulsacion;
    private javax.swing.JLabel lblUbicacionActial;
    private javax.swing.JLabel lvlF4;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JSpinner spinDelay;
    private javax.swing.JSpinner spinIteraciones;
    private javax.swing.JSpinner spinX;
    private javax.swing.JSpinner spinY;
    private javax.swing.JTable tblClicks;
    // End of variables declaration//GEN-END:variables
}
