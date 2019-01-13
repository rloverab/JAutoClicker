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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Roger Lovera
 */
public class DialogoCondicional extends javax.swing.JDialog {
    //ATRIBUTOS
    private BucleCondicionado bucleCondicionado;
    private Condicional condicional;
    private final ActionListener jrbListener;
    private int tipoFuncion;
    public final static int CONDICIONAL = 1;
    public final static int BUCLE_CONDICIONADO = 2;

    /**
     * Creates new form dlgCondicional
     * @param parent
     * @param modal
     */
    public DialogoCondicional(java.awt.Frame parent, boolean modal) {
        super(parent, modal);        
        initComponents();
        jrbListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((JRadioButton)e.getSource() == jrbPar) || ((JRadioButton)e.getSource() == jrbImpar)){
                    txtPatronNumericoMinimo.setText("");
                    txtPatronNumericoMaximo.setText("");
                    txtPatronNumericoMinimo.setEnabled(false);
                    txtPatronNumericoMaximo.setEnabled(false);                    
                }else{
                    if(((JRadioButton)e.getSource() == jrbDentroRango) || ((JRadioButton)e.getSource() == jrbFueraRango)){
                        if(!txtPatronNumericoMinimo.isEnabled()){
                            txtPatronNumericoMinimo.setText("");
                            txtPatronNumericoMinimo.setEnabled(true);        
                        }
                        if(!txtPatronNumericoMaximo.isEnabled()){
                            txtPatronNumericoMaximo.setText("");
                            txtPatronNumericoMaximo.setEnabled(true);        
                        }
                    }else{
                        if(!txtPatronNumericoMinimo.isEnabled()){
                            txtPatronNumericoMinimo.setText("");
                            txtPatronNumericoMinimo.setEnabled(true);
                        }
                        txtPatronNumericoMaximo.setText("");
                        txtPatronNumericoMaximo.setEnabled(false);
                    }
                }
            }
        };
        jrbDentroRango.addActionListener(jrbListener);
        jrbFueraRango.addActionListener(jrbListener);
        jrbIgualQue.addActionListener(jrbListener);        
        jrbNoIgualQue.addActionListener(jrbListener);
        jrbImpar.addActionListener(jrbListener);
        jrbMayorIgualQue.addActionListener(jrbListener);
        jrbMayorQue.addActionListener(jrbListener);
        jrbMenorIgualQue.addActionListener(jrbListener);
        jrbMenorQue.addActionListener(jrbListener);
        jrbPar.addActionListener(jrbListener);    
        
        txtPatronNumericoMinimo.setDocument(new LimiteCaracteres(txtPatronNumericoMinimo,9));
        txtPatronNumericoMaximo.setDocument(new LimiteCaracteres(txtPatronNumericoMaximo,9));
    }
    
    /**
     * Creates new form dlgCondicional
     * @param parent
     * @param modal
     * @param tipoFuncion
     */
    public DialogoCondicional(java.awt.Frame parent, boolean modal, int tipoFuncion){
        this(parent,modal);
        this.tipoFuncion = tipoFuncion;
        
        switch(tipoFuncion){
            case CONDICIONAL:
                activarFuncionBucle(false);
                break;
            case BUCLE_CONDICIONADO:
                activarFuncionBucle(true);
        }
    }
        
    //MODIFICADORES
    public void setCondicional(Condicional condicional){
        this.condicional = condicional;
        if(condicional != null){
            switch(condicional.getTipoDato()){
                case Condicional.TEXTO:
                    //panAlfanumerico.setVisible(true);
                    jtpComparacion.setSelectedIndex(1);
                    txtPatronAlfanumerico.setText(condicional.getPatronMinimo());
                    chkTextoParcial.setSelected(!condicional.isTextoExacto());
                    chkSensible.setSelected(condicional.isCaseSensitive());
                    
                    switch(condicional.getTipoComparacion()){
                        case Condicional.TEXTO_IGUAL:
                            jrbTextoIgual.setSelected(true);
                            break;
                        case Condicional.TEXTO_DIFERENTE:
                            jrbTextoDiferente.setSelected(true);
                    }
                    break;
                case Condicional.NUMERO:
                    //panNumerico.setVisible(true);
                    jtpComparacion.setSelectedIndex(0);
                    switch(condicional.getTipoComparacion()){
                        case Condicional.IGUAL:
                            jrbIgualQue.setSelected(true);
                            break;
                        case Condicional.NO_IGUAL:
                            jrbNoIgualQue.setSelected(true);
                            break;
                        case Condicional.MAYOR:
                            jrbMayorQue.setSelected(true);
                            break;
                        case Condicional.MAYOR_IGUAL:
                            jrbMayorIgualQue.setSelected(true);
                            break;
                        case Condicional.MENOR:
                            jrbMenorQue.setSelected(true);
                            break;
                        case Condicional.MENOR_IGUAL:
                            jrbMenorIgualQue.setSelected(true);
                            break;
                        case Condicional.DENTRO_RANGO:
                            jrbDentroRango.setSelected(true);
                            break;
                        case Condicional.FUERA_RANGO:
                            jrbFueraRango.setSelected(true);
                            break;
                        case Condicional.ES_PAR:
                            jrbPar.setSelected(true);
                            break;
                        case Condicional.ES_IMPAR:
                            jrbImpar.setSelected(true);
                    }
                    
                    switch(condicional.getTipoComparacion()){
                        case Condicional.IGUAL:                            
                        case Condicional.NO_IGUAL:
                        case Condicional.MAYOR:
                        case Condicional.MAYOR_IGUAL:
                        case Condicional.MENOR:
                        case Condicional.MENOR_IGUAL:
                            txtPatronNumericoMinimo.setText(condicional.getPatronMinimo());
                            txtPatronNumericoMaximo.setText("");
                            break;
                        case Condicional.DENTRO_RANGO: 
                        case Condicional.FUERA_RANGO:
                            txtPatronNumericoMinimo.setText(condicional.getPatronMinimo());
                            txtPatronNumericoMaximo.setText(condicional.getPatronMaximo());
                            break;
                        case Condicional.ES_PAR:                     
                        case Condicional.ES_IMPAR:
                            txtPatronNumericoMinimo.setText("");
                            txtPatronNumericoMaximo.setText("");
                            jrbImpar.setSelected(true);
                    }
            }
        }
    }
    
    public void setBucleCondicionado(BucleCondicionado bucleCondicionado){
        this.bucleCondicionado = bucleCondicionado;
        if(bucleCondicionado != null){
            switch(bucleCondicionado.getTipoDato()){
                case Condicional.TEXTO:
                    jtpComparacion.setSelectedIndex(1);
                    txtPatronAlfanumerico.setText(bucleCondicionado.getPatronMinimo());
                    chkTextoParcial.setSelected(!bucleCondicionado.isTextoExacto());
                    chkSensible.setSelected(bucleCondicionado.isCaseSensitive());
                    
                    switch(bucleCondicionado.getTipoComparacion()){
                        case BucleCondicionado.TEXTO_IGUAL:
                            jrbTextoIgual.setSelected(true);
                            break;
                        case BucleCondicionado.TEXTO_DIFERENTE:
                            jrbTextoDiferente.setSelected(true);
                    }
                    
                    switch(bucleCondicionado.getTipoEvaluacion()){
                        case BucleCondicionado.EVALUAR_ANTES:
                            jrbEvaluarAntesTexto.setSelected(true);
                            break;
                        case BucleCondicionado.EVALUAR_DESPUES:
                            jrbEvaluarDespuesTexto.setSelected(true);
                    }
                    
                    break;
                case Condicional.NUMERO:
                    jtpComparacion.setSelectedIndex(0);
                    switch(bucleCondicionado.getTipoComparacion()){
                        case BucleCondicionado.IGUAL:
                            jrbIgualQue.setSelected(true);
                            break;
                        case BucleCondicionado.NO_IGUAL:
                            jrbNoIgualQue.setSelected(true);
                            break;
                        case BucleCondicionado.MAYOR:
                            jrbMayorQue.setSelected(true);
                            break;
                        case BucleCondicionado.MAYOR_IGUAL:
                            jrbMayorIgualQue.setSelected(true);
                            break;
                        case BucleCondicionado.MENOR:
                            jrbMenorQue.setSelected(true);
                            break;
                        case BucleCondicionado.MENOR_IGUAL:
                            jrbMenorIgualQue.setSelected(true);
                            break;
                        case BucleCondicionado.DENTRO_RANGO:
                            jrbDentroRango.setSelected(true);
                            break;
                        case BucleCondicionado.FUERA_RANGO:
                            jrbFueraRango.setSelected(true);
                            break;
                        case BucleCondicionado.ES_PAR:
                            jrbPar.setSelected(true);
                            break;
                        case BucleCondicionado.ES_IMPAR:
                            jrbImpar.setSelected(true);
                    }
                    
                    switch(bucleCondicionado.getTipoComparacion()){
                        case BucleCondicionado.IGUAL:                            
                        case BucleCondicionado.NO_IGUAL:
                        case BucleCondicionado.MAYOR:
                        case BucleCondicionado.MAYOR_IGUAL:
                        case BucleCondicionado.MENOR:
                        case BucleCondicionado.MENOR_IGUAL:
                            txtPatronNumericoMinimo.setText(bucleCondicionado.getPatronMinimo());
                            txtPatronNumericoMaximo.setText("");
                            break;
                        case BucleCondicionado.DENTRO_RANGO: 
                        case BucleCondicionado.FUERA_RANGO:
                            txtPatronNumericoMinimo.setText(bucleCondicionado.getPatronMinimo());
                            txtPatronNumericoMaximo.setText(bucleCondicionado.getPatronMaximo());
                            break;
                        case BucleCondicionado.ES_PAR:                     
                        case BucleCondicionado.ES_IMPAR:
                            txtPatronNumericoMinimo.setText("");
                            txtPatronNumericoMaximo.setText("");
                            jrbImpar.setSelected(true);
                    }
                    
                    switch(bucleCondicionado.getTipoEvaluacion()){
                        case BucleCondicionado.EVALUAR_ANTES:
                            jrbEvaluarAntesNumero.setSelected(true);
                            break;
                        case BucleCondicionado.EVALUAR_DESPUES:
                            jrbEvaluarDespuesNumero.setSelected(true);
                    }
            }
        }
    }
    
    //CONSULTAS
    public Condicional getCondicional(){
        return condicional;
    }
    
    public BucleCondicionado getBucleCondicionado(){
        return bucleCondicionado;
    }
    
    //ACCIONES
    private void activarFuncionBucle(boolean activar){
        lblEvaluarCondicionNumero.setEnabled(activar);
        lblEvaluarCondicionTexto.setEnabled(activar);
        jrbEvaluarDespuesNumero.setEnabled(activar);
        jrbEvaluarDespuesTexto.setEnabled(activar);
        jrbEvaluarAntesNumero.setEnabled(activar);
        jrbEvaluarAntesTexto.setEnabled(activar);
        
        lblEvaluarCondicionNumero.setVisible(activar);
        lblEvaluarCondicionTexto.setVisible(activar);
        jrbEvaluarDespuesNumero.setVisible(activar);
        jrbEvaluarDespuesTexto.setVisible(activar);
        jrbEvaluarAntesNumero.setVisible(activar);
        jrbEvaluarAntesTexto.setVisible(activar);
        
        if(activar){            
            this.setTitle("Bucle condicional");
            jrbEvaluarAntesNumero.setSelected(true);
            jrbEvaluarAntesTexto.setSelected(true);
        }else{
            this.setTitle("Condicional");
        }            
    }
    
    private boolean esValido(){
        if(panNumerico.isVisible()){            
            if(jrbPar.isSelected() || jrbImpar.isSelected()){
                return true;
            }
            if(!txtPatronNumericoMinimo.toString().trim().isEmpty() && Validador.esNumero(txtPatronNumericoMinimo.getText())){
                if(jrbDentroRango.isSelected() || jrbFueraRango.isSelected()){
                    if(!txtPatronNumericoMaximo.toString().trim().isEmpty() && 
                            Validador.esNumero(txtPatronNumericoMaximo.getText()) &&
                            (Integer.valueOf(txtPatronNumericoMinimo.getText().trim()) <= Integer.valueOf(txtPatronNumericoMaximo.getText().trim()))){
                        return true;
                    }
                }else{
                    if(grpTipoComparacion.getSelection() != null){
                        return true;
                    }
                }               
            }
        }else{            
            return !txtPatronAlfanumerico.toString().trim().isEmpty() && grpComparacionTexto.getSelection() != null;
        }
        return false;
    }
    
    private void soloNumeros(JTextField campoTexto, KeyEvent e){
        char c;
        c = e.getKeyChar();
        
        if(c < '0' || c > '9'){            
            if(campoTexto.getText().trim().isEmpty()){
                if(c != '-'){
                    e.consume();
                }
            }else{
                e.consume();
            }
        }
    }
    
    private boolean guardar(){
        if(esValido()){
            switch(tipoFuncion){
                case CONDICIONAL:
                    if(condicional == null){
                        condicional = new Condicional();
                    }

                    if(panNumerico.isVisible()){
                        //condicional.setPatron(txtPatronNumericoMinimo.getText().trim());
                        condicional.setRango(txtPatronNumericoMinimo.getText().trim(), "");
                        condicional.setTipoDato(Condicional.NUMERO);
                        if(jrbIgualQue.isSelected()){                    
                            condicional.setTipoComparacion(Condicional.IGUAL);
                        }
                        if(jrbNoIgualQue.isSelected()){                    
                            condicional.setTipoComparacion(Condicional.NO_IGUAL);
                        }
                        if(jrbMenorQue.isSelected()){                    
                            condicional.setTipoComparacion(Condicional.MENOR);
                        }
                        if(jrbMenorIgualQue.isSelected()){                    
                            condicional.setTipoComparacion(Condicional.MENOR_IGUAL);
                        }
                        if(jrbMayorQue.isSelected()){                    
                            condicional.setTipoComparacion(Condicional.MAYOR);
                        }
                        if(jrbMayorIgualQue.isSelected()){                    
                            condicional.setTipoComparacion(Condicional.MAYOR_IGUAL);
                        }
                        if(jrbDentroRango.isSelected()){
                            condicional.setTipoComparacion(Condicional.DENTRO_RANGO);
                            condicional.setRango(
                                    txtPatronNumericoMinimo.getText().trim(), 
                                    txtPatronNumericoMaximo.getText().trim());
                        }
                        if(jrbFueraRango.isSelected()){
                            condicional.setTipoComparacion(Condicional.FUERA_RANGO);
                            condicional.setRango(
                                    txtPatronNumericoMinimo.getText().trim(), 
                                    txtPatronNumericoMaximo.getText().trim());
                        }
                        if(jrbPar.isSelected()){
                            condicional.setTipoComparacion(Condicional.ES_PAR);
                            condicional.setRango("","");
                        }
                        if(jrbImpar.isSelected()){
                            condicional.setTipoComparacion(Condicional.ES_IMPAR);
                            condicional.setRango("","");
                        }                                        
                    }else{
                        condicional.setPatron(txtPatronAlfanumerico.getText().trim());
                        condicional.setTipoDato(Condicional.TEXTO);
                        if(jrbTextoIgual.isSelected()){
                            condicional.setTipoComparacion(Condicional.TEXTO_IGUAL);
                        }
                        if(jrbTextoDiferente.isSelected()){
                            condicional.setTipoComparacion(Condicional.TEXTO_DIFERENTE);
                        }
                        condicional.setTextoExacto(!chkTextoParcial.isSelected());
                        condicional.setCaseSensitive(chkSensible.isSelected());
                    }
                    return true;
                case BUCLE_CONDICIONADO:
                    if(bucleCondicionado == null){
                        bucleCondicionado = new BucleCondicionado();
                    }

                    if(panNumerico.isVisible()){
                        bucleCondicionado.setPatron(txtPatronNumericoMinimo.getText().trim());
                        bucleCondicionado.setTipoDato(BucleCondicionado.NUMERO);
                        if(jrbIgualQue.isSelected()){                    
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.IGUAL);
                        }
                        if(jrbNoIgualQue.isSelected()){                    
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.NO_IGUAL);
                        }
                        if(jrbMenorQue.isSelected()){                    
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.MENOR);
                        }
                        if(jrbMenorIgualQue.isSelected()){                    
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.MENOR_IGUAL);
                        }
                        if(jrbMayorQue.isSelected()){                    
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.MAYOR);
                        }
                        if(jrbMayorIgualQue.isSelected()){                    
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.MAYOR_IGUAL);
                        }
                        if(jrbDentroRango.isSelected()){
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.DENTRO_RANGO);
                            bucleCondicionado.setRango(
                                    txtPatronNumericoMinimo.getText().trim(), 
                                    txtPatronNumericoMaximo.getText().trim());
                        }
                        if(jrbFueraRango.isSelected()){
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.FUERA_RANGO);
                            bucleCondicionado.setRango(
                                    txtPatronNumericoMinimo.getText().trim(), 
                                    txtPatronNumericoMaximo.getText().trim());
                        }
                        if(jrbPar.isSelected()){
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.ES_PAR);
                        }
                        if(jrbImpar.isSelected()){
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.ES_IMPAR);
                        }
                        if(tipoFuncion == DialogoCondicional.BUCLE_CONDICIONADO){
                            if(jrbEvaluarAntesNumero.isSelected()){
                                bucleCondicionado.setTipoEvaluacion(BucleCondicionado.EVALUAR_ANTES);
                            }

                            if(jrbEvaluarDespuesNumero.isSelected()){
                                bucleCondicionado.setTipoEvaluacion(BucleCondicionado.EVALUAR_DESPUES);
                            }
                        }                    
                    }else{
                        bucleCondicionado.setPatron(txtPatronAlfanumerico.getText().trim());
                        bucleCondicionado.setTipoDato(Condicional.TEXTO);
                        if(jrbTextoIgual.isSelected()){
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.TEXTO_IGUAL);
                        }
                        if(jrbTextoDiferente.isSelected()){
                            bucleCondicionado.setTipoComparacion(BucleCondicionado.TEXTO_DIFERENTE);
                        }
                        bucleCondicionado.setTextoExacto(!chkTextoParcial.isSelected());
                        bucleCondicionado.setCaseSensitive(chkSensible.isSelected());

                        if(tipoFuncion == DialogoCondicional.BUCLE_CONDICIONADO){
                            if(jrbEvaluarAntesTexto.isSelected()){
                                bucleCondicionado.setTipoEvaluacion(BucleCondicionado.EVALUAR_ANTES);
                            }

                            if(jrbEvaluarDespuesTexto.isSelected()){
                                bucleCondicionado.setTipoEvaluacion(BucleCondicionado.EVALUAR_DESPUES);
                            }
                        }
                    }
                    return true;        
            }
        }   
        return false;        
                
    }

    private void cerrar(){
        condicional = null;
        bucleCondicionado = null;
        this.dispose();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpTipoComparacion = new javax.swing.ButtonGroup();
        grpComparacionTexto = new javax.swing.ButtonGroup();
        grpEvaluacionNumero = new javax.swing.ButtonGroup();
        grpEvaluacionTexto = new javax.swing.ButtonGroup();
        jtpComparacion = new javax.swing.JTabbedPane();
        panNumerico = new javax.swing.JPanel();
        txtPatronNumericoMinimo = new javax.swing.JTextField();
        lblPatronNumerico = new javax.swing.JLabel();
        lblTipoComparacion = new javax.swing.JLabel();
        jrbIgualQue = new javax.swing.JRadioButton();
        jrbNoIgualQue = new javax.swing.JRadioButton();
        jrbMenorQue = new javax.swing.JRadioButton();
        jrbMayorQue = new javax.swing.JRadioButton();
        jrbMenorIgualQue = new javax.swing.JRadioButton();
        jrbMayorIgualQue = new javax.swing.JRadioButton();
        btnAgregarNumerico = new javax.swing.JButton();
        btnCerrarNumerico = new javax.swing.JButton();
        txtPatronNumericoMaximo = new javax.swing.JTextField();
        jrbDentroRango = new javax.swing.JRadioButton();
        jrbPar = new javax.swing.JRadioButton();
        jrbImpar = new javax.swing.JRadioButton();
        jrbFueraRango = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblEvaluarCondicionNumero = new javax.swing.JLabel();
        jrbEvaluarAntesNumero = new javax.swing.JRadioButton();
        jrbEvaluarDespuesNumero = new javax.swing.JRadioButton();
        panAlfanumerico = new javax.swing.JPanel();
        txtPatronAlfanumerico = new javax.swing.JTextField();
        lblPatronAlfanumerico = new javax.swing.JLabel();
        lblParametros = new javax.swing.JLabel();
        chkTextoParcial = new javax.swing.JCheckBox();
        chkSensible = new javax.swing.JCheckBox();
        btnAgregarAlfanumerico = new javax.swing.JButton();
        btnCerrarAlfanumerico = new javax.swing.JButton();
        jrbTextoIgual = new javax.swing.JRadioButton();
        jrbTextoDiferente = new javax.swing.JRadioButton();
        lblTipoComparacion1 = new javax.swing.JLabel();
        lblEvaluarCondicionTexto = new javax.swing.JLabel();
        jrbEvaluarAntesTexto = new javax.swing.JRadioButton();
        jrbEvaluarDespuesTexto = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        txtPatronNumericoMinimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPatronNumericoMinimoKeyTyped(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("jautoclicker/Bundle"); // NOI18N
        lblPatronNumerico.setText(bundle.getString("PATRÓN DE COMPARACIÓN ")); // NOI18N

        lblTipoComparacion.setText(bundle.getString("TIPO DE COMPARACIÓN")); // NOI18N

        grpTipoComparacion.add(jrbIgualQue);
        jrbIgualQue.setText(bundle.getString("IGUAL QUE")); // NOI18N

        grpTipoComparacion.add(jrbNoIgualQue);
        jrbNoIgualQue.setText(bundle.getString("NO ES IGUAL QUE")); // NOI18N

        grpTipoComparacion.add(jrbMenorQue);
        jrbMenorQue.setText(bundle.getString("MENOR QUE")); // NOI18N

        grpTipoComparacion.add(jrbMayorQue);
        jrbMayorQue.setText(bundle.getString("MAYOR QUE")); // NOI18N

        grpTipoComparacion.add(jrbMenorIgualQue);
        jrbMenorIgualQue.setText(bundle.getString("MENOR O IGUAL QUE")); // NOI18N

        grpTipoComparacion.add(jrbMayorIgualQue);
        jrbMayorIgualQue.setText(bundle.getString("MAYOR O IGUAL QUE")); // NOI18N

        btnAgregarNumerico.setText(bundle.getString("AGREGAR")); // NOI18N
        btnAgregarNumerico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarNumericoActionPerformed(evt);
            }
        });

        btnCerrarNumerico.setText(bundle.getString("CERRAR")); // NOI18N
        btnCerrarNumerico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarNumericoActionPerformed(evt);
            }
        });

        txtPatronNumericoMaximo.setEnabled(false);
        txtPatronNumericoMaximo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPatronNumericoMaximoKeyTyped(evt);
            }
        });

        grpTipoComparacion.add(jrbDentroRango);
        jrbDentroRango.setText(bundle.getString("DENTRO DEL RANGO")); // NOI18N

        grpTipoComparacion.add(jrbPar);
        jrbPar.setText(bundle.getString("PAR")); // NOI18N

        grpTipoComparacion.add(jrbImpar);
        jrbImpar.setText(bundle.getString("IMPAR")); // NOI18N

        grpTipoComparacion.add(jrbFueraRango);
        jrbFueraRango.setText(bundle.getString("FUERA DEL RANGO")); // NOI18N

        jLabel1.setText(bundle.getString("MÍNIMO")); // NOI18N

        jLabel2.setText(bundle.getString("MÁXIMO")); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblEvaluarCondicionNumero.setText(bundle.getString("EVALUAR CONDICIÓN")); // NOI18N

        grpEvaluacionNumero.add(jrbEvaluarAntesNumero);
        jrbEvaluarAntesNumero.setText(bundle.getString("EVALUAR CONDICIÓN ANTES")); // NOI18N

        grpEvaluacionNumero.add(jrbEvaluarDespuesNumero);
        jrbEvaluarDespuesNumero.setText(bundle.getString("EVALUAR CONDICIÓN DESPUÉS")); // NOI18N

        javax.swing.GroupLayout panNumericoLayout = new javax.swing.GroupLayout(panNumerico);
        panNumerico.setLayout(panNumericoLayout);
        panNumericoLayout.setHorizontalGroup(
            panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panNumericoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbIgualQue)
                    .addComponent(jrbMenorQue)
                    .addComponent(jrbMayorQue)
                    .addComponent(jrbDentroRango))
                .addGap(20, 20, 20)
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panNumericoLayout.createSequentialGroup()
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrbMenorIgualQue)
                            .addComponent(jrbNoIgualQue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrbPar)
                            .addComponent(jrbImpar))
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(panNumericoLayout.createSequentialGroup()
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrbFueraRango)
                            .addComponent(jrbMayorIgualQue))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panNumericoLayout.createSequentialGroup()
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panNumericoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCerrarNumerico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregarNumerico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panNumericoLayout.createSequentialGroup()
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panNumericoLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lblPatronNumerico))
                            .addGroup(panNumericoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTipoComparacion)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPatronNumericoMinimo, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPatronNumericoMaximo, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(panNumericoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jrbEvaluarDespuesNumero)
                    .addComponent(jrbEvaluarAntesNumero)
                    .addComponent(lblEvaluarCondicionNumero))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panNumericoLayout.setVerticalGroup(
            panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panNumericoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPatronNumericoMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPatronNumerico)
                    .addComponent(txtPatronNumericoMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTipoComparacion)
                    .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)))
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panNumericoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jrbIgualQue)
                            .addComponent(jrbNoIgualQue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jrbMenorQue)
                            .addComponent(jrbMenorIgualQue, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jrbMayorQue)
                            .addComponent(jrbMayorIgualQue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jrbDentroRango)
                            .addComponent(jrbFueraRango))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE))
                    .addGroup(panNumericoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jrbPar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrbImpar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(12, 12, 12)
                .addComponent(lblEvaluarCondicionNumero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbEvaluarAntesNumero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbEvaluarDespuesNumero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panNumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrarNumerico)
                    .addComponent(btnAgregarNumerico))
                .addContainerGap())
        );

        jtpComparacion.addTab(bundle.getString("COMPARACIÓN NUMÉRICA "), panNumerico); // NOI18N

        lblPatronAlfanumerico.setText(bundle.getString("PATRÓN DE COMPARACIÓN ")); // NOI18N

        lblParametros.setText(bundle.getString("PARÁMETROS")); // NOI18N

        chkTextoParcial.setText(bundle.getString("TEXTO PARCIAL")); // NOI18N

        chkSensible.setText(bundle.getString("DISTINGUE MAYÚSCILAS Y MINÚSCULAS")); // NOI18N

        btnAgregarAlfanumerico.setText(bundle.getString("AGREGAR")); // NOI18N
        btnAgregarAlfanumerico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAlfanumericoActionPerformed(evt);
            }
        });

        btnCerrarAlfanumerico.setText(bundle.getString("CERRAR")); // NOI18N
        btnCerrarAlfanumerico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarAlfanumericoActionPerformed(evt);
            }
        });

        grpComparacionTexto.add(jrbTextoIgual);
        jrbTextoIgual.setText(bundle.getString("IGUAL")); // NOI18N

        grpComparacionTexto.add(jrbTextoDiferente);
        jrbTextoDiferente.setText(bundle.getString("DIFERENTE")); // NOI18N

        lblTipoComparacion1.setText(bundle.getString("TIPO DE COMPARACIÓN")); // NOI18N

        lblEvaluarCondicionTexto.setText(bundle.getString("EVALUAR CONDICIÓN")); // NOI18N

        grpEvaluacionTexto.add(jrbEvaluarAntesTexto);
        jrbEvaluarAntesTexto.setText(bundle.getString("EVALUAR CONDICIÓN ANTES")); // NOI18N

        grpEvaluacionTexto.add(jrbEvaluarDespuesTexto);
        jrbEvaluarDespuesTexto.setText(bundle.getString("EVALUAR CONDICIÓN DESPUÉS")); // NOI18N

        javax.swing.GroupLayout panAlfanumericoLayout = new javax.swing.GroupLayout(panAlfanumerico);
        panAlfanumerico.setLayout(panAlfanumericoLayout);
        panAlfanumericoLayout.setHorizontalGroup(
            panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAlfanumericoLayout.createSequentialGroup()
                .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAlfanumericoLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(lblPatronAlfanumerico)
                        .addGap(18, 18, 18)
                        .addComponent(txtPatronAlfanumerico))
                    .addGroup(panAlfanumericoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblParametros)
                            .addComponent(chkSensible)
                            .addComponent(chkTextoParcial))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panAlfanumericoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panAlfanumericoLayout.createSequentialGroup()
                                .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jrbEvaluarDespuesTexto)
                                    .addComponent(jrbEvaluarAntesTexto)
                                    .addComponent(lblEvaluarCondicionTexto))
                                .addGap(0, 108, Short.MAX_VALUE))
                            .addGroup(panAlfanumericoLayout.createSequentialGroup()
                                .addComponent(btnCerrarAlfanumerico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgregarAlfanumerico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(panAlfanumericoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panAlfanumericoLayout.createSequentialGroup()
                        .addComponent(jrbTextoIgual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbTextoDiferente))
                    .addComponent(lblTipoComparacion1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panAlfanumericoLayout.setVerticalGroup(
            panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panAlfanumericoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPatronAlfanumerico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPatronAlfanumerico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblParametros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkTextoParcial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkSensible)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTipoComparacion1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrbTextoIgual)
                    .addComponent(jrbTextoDiferente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblEvaluarCondicionTexto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbEvaluarAntesTexto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbEvaluarDespuesTexto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panAlfanumericoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarAlfanumerico)
                    .addComponent(btnCerrarAlfanumerico))
                .addContainerGap())
        );

        jtpComparacion.addTab(bundle.getString("COMPARACIÓN ALFANUMÉRICA"), panAlfanumerico); // NOI18N

        jLabel5.setText(bundle.getString("NOTA: LOS VALORES A COMPARAR SON TOMADOS DEL PORTAPAPELES")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtpComparacion)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtpComparacion, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarNumericoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarNumericoActionPerformed
        // TODO add your handling code here:
        if(guardar()){
            this.dispose();
        }
    }//GEN-LAST:event_btnAgregarNumericoActionPerformed

    private void btnCerrarNumericoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarNumericoActionPerformed
        // TODO add your handling code here:
        cerrar();
    }//GEN-LAST:event_btnCerrarNumericoActionPerformed

    private void btnCerrarAlfanumericoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarAlfanumericoActionPerformed
        // TODO add your handling code here:
        cerrar();
    }//GEN-LAST:event_btnCerrarAlfanumericoActionPerformed

    private void btnAgregarAlfanumericoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAlfanumericoActionPerformed
        // TODO add your handling code here:
        if(guardar()){
            this.dispose();
        }
    }//GEN-LAST:event_btnAgregarAlfanumericoActionPerformed

    private void txtPatronNumericoMinimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPatronNumericoMinimoKeyTyped
        // TODO add your handling code here:
        soloNumeros(txtPatronNumericoMinimo, evt);
    }//GEN-LAST:event_txtPatronNumericoMinimoKeyTyped

    private void txtPatronNumericoMaximoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPatronNumericoMaximoKeyTyped
        // TODO add your handling code here:
        soloNumeros(txtPatronNumericoMaximo, evt);
    }//GEN-LAST:event_txtPatronNumericoMaximoKeyTyped

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
            java.util.logging.Logger.getLogger(DialogoCondicional.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogoCondicional dialog = new DialogoCondicional(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarAlfanumerico;
    private javax.swing.JButton btnAgregarNumerico;
    private javax.swing.JButton btnCerrarAlfanumerico;
    private javax.swing.JButton btnCerrarNumerico;
    private javax.swing.JCheckBox chkSensible;
    private javax.swing.JCheckBox chkTextoParcial;
    private javax.swing.ButtonGroup grpComparacionTexto;
    private javax.swing.ButtonGroup grpEvaluacionNumero;
    private javax.swing.ButtonGroup grpEvaluacionTexto;
    private javax.swing.ButtonGroup grpTipoComparacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JRadioButton jrbDentroRango;
    private javax.swing.JRadioButton jrbEvaluarAntesNumero;
    private javax.swing.JRadioButton jrbEvaluarAntesTexto;
    private javax.swing.JRadioButton jrbEvaluarDespuesNumero;
    private javax.swing.JRadioButton jrbEvaluarDespuesTexto;
    private javax.swing.JRadioButton jrbFueraRango;
    private javax.swing.JRadioButton jrbIgualQue;
    private javax.swing.JRadioButton jrbImpar;
    private javax.swing.JRadioButton jrbMayorIgualQue;
    private javax.swing.JRadioButton jrbMayorQue;
    private javax.swing.JRadioButton jrbMenorIgualQue;
    private javax.swing.JRadioButton jrbMenorQue;
    private javax.swing.JRadioButton jrbNoIgualQue;
    private javax.swing.JRadioButton jrbPar;
    private javax.swing.JRadioButton jrbTextoDiferente;
    private javax.swing.JRadioButton jrbTextoIgual;
    private javax.swing.JTabbedPane jtpComparacion;
    private javax.swing.JLabel lblEvaluarCondicionNumero;
    private javax.swing.JLabel lblEvaluarCondicionTexto;
    private javax.swing.JLabel lblParametros;
    private javax.swing.JLabel lblPatronAlfanumerico;
    private javax.swing.JLabel lblPatronNumerico;
    private javax.swing.JLabel lblTipoComparacion;
    private javax.swing.JLabel lblTipoComparacion1;
    private javax.swing.JPanel panAlfanumerico;
    private javax.swing.JPanel panNumerico;
    private javax.swing.JTextField txtPatronAlfanumerico;
    private javax.swing.JTextField txtPatronNumericoMaximo;
    private javax.swing.JTextField txtPatronNumericoMinimo;
    // End of variables declaration//GEN-END:variables
}
