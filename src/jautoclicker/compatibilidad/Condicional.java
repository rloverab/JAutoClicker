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
package jautoclicker.compatibilidad;

import jautoclicker.Validador;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase <font color=blue><b>Condicional</b></font>
 * Contiene métodos empleados para evaluar condiciones que permite desencadenar o no la ejecución de una serie de acciones programadas.
 * Las condicionales se rigen mediante la comparaciòn de valores tomados del portapapeles y un valor establecido previamente.
 * <b>Nota:</b> 
 * <ul>
 * <li>Solo se admite comparaciones de cadenas de texto y valores numéricos.
 * <li>La comparación de cadenas de texto admite evaluaciones exactas y parciales.</li>
 * <li>La comparación de valores numéricos solo admite números enteros</li>
 * </ul>
 * @author Roger Lovera
 */
public class Condicional implements Serializable{        
    private String patronMinimo;
    private String patronMaximo;
    private int tipoDato;
    private int tipoComparacion;
    private boolean caseSensitive;
    private boolean textoExacto;      

    /**
     * Tipo de comparacion: <b><i>NO_DEFINIDA = 0</i></b> 
     */
    public final static int NO_DEFINIDA = 0;

    /**
     * Tipo de dato: <b><i>TEXTO = 1</i></b> 
     */
    public final static int TEXTO = 1;
    
    /**
     * Tipo de dato: <b><i>TEXTO_IGUAL = 11</i></b> 
     */
    public final static int TEXTO_IGUAL = 11;
    
    /**
     * Tipo de dato: <b><i>TEXTO_DIFERENTE = 12</i></b> 
     */
    public final static int TEXTO_DIFERENTE = 12;

    /**
     * Tipo de dato: <b><i>NUMERO = 2</i></b> 
     */
    public final static int NUMERO = 2;    

    /**
     * Tipo de comparacion numerica: <b><i>IGUAL = 21</i></b> 
     */
    public final static int IGUAL = 21;    

    /**
     * Tipo de comparacion numerica: <b><i>NO_IGUAL = 22</i></b> 
     */
    public final static int NO_IGUAL = 22;    

    /**
     * Tipo de comparacion numerica: <b><i>MAYOR = 23</i></b> 
     */
    public final static int MAYOR = 23;    

    /**
     * Tipo de comparacion numerica: <b><i>MAYOR_IGUAL = 24</i></b> 
     */
    public final static int MAYOR_IGUAL = 24;    

    /**
     * Tipo de comparacion numerica: <b><i>MENOR = 25</i></b> 
     */
    public final static int MENOR = 25;    

    /**
     * Tipo de comparacion numerica: <b><i>MENOR_IGUAL = 26</i></b> 
     */
    public final static int MENOR_IGUAL = 26;    
    
    /**
     * Tipo de comparacion numerica: <b><i>DENTRO_RANGO = 27</i></b> 
     */
    public final static int DENTRO_RANGO = 27;
    
    /**
     * Tipo de comparacion numerica: <b><i>FUERA_RANGO = 28</i></b> 
     */
    public final static int FUERA_RANGO = 28;
    
    /**
     * Tipo de comparacion numerica: <b><i>ES_PAR = 202</i></b> 
     */
    public final static int ES_PAR = 202;
    
    /**
     * Tipo de comparacion numerica: <b><i>ES_IMPAR = 203</i></b> 
     */
    public final static int ES_IMPAR = 203;
    
//Constructor
    public Condicional() {   
        super();        
        patronMinimo = "";
        patronMaximo = "";
        tipoDato = NO_DEFINIDA;
        tipoComparacion = NO_DEFINIDA;
        caseSensitive = false;
        textoExacto = false;      
    }
    
    //Modificadores    

    /**
     * Establece el valor con el que se va a realizar la comparación.
     * @param comparar
     */
    public void setPatron(String comparar) {
        this.patronMinimo = comparar.trim();
    }
    
    /**
     * Establece el valor con el que se va a realizar la comparación de rango.
     * @param minimo Cota inferior
     * @param maximo Cota superior
     */
    public void setRango(String minimo, String maximo){
        patronMinimo = minimo;
        patronMaximo = maximo;
    }
    
    /**
     * Se define el tipo de valores a patron
     * @param tipoDato
     */
    public void setTipoDato(int tipoDato) {
        this.tipoDato = tipoDato;
    }
    
    /**
     * Se define el tipo de comparación a realizar
     * @param tipoComparacion
     */
    public void setTipoComparacion(int tipoComparacion){
        this.tipoComparacion = tipoComparacion;
    }
    
    /**
     * Se indica si al patron cadenas de caracteres debe considerarse las mayúsculas o las minúsculas
     * @param caseSensitive <font color=blue><b>true</b></font> para considerar mayúsculas y minúsculas y <font color=red><b>false</b></font> para no considerarlas
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Indica si al patron cadenas de caracteres se debe considerar cadenas completas o un fragmento esta contenido en la cadena a patron.
     * @param textoExacto
     */
    public void setTextoExacto(boolean textoExacto) {
        this.textoExacto = textoExacto;
    }
    
    //Consultas
    private String getDato(){
        String resultado;
        Clipboard cb;
        Transferable t;
        Boolean esTransferible;
        
        resultado = "";        
        cb = Toolkit.getDefaultToolkit().getSystemClipboard();     
        
        t = cb.getContents(this);
        esTransferible = (t != null) && t.isDataFlavorSupported(DataFlavor.stringFlavor);
        
        if(esTransferible){
            try{
                resultado = t.getTransferData(DataFlavor.stringFlavor).toString();
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(Condicional.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        return resultado;
    }

    public String getPatronMinimo() {
        return patronMinimo;
    }

    public String getPatronMaximo() {
        return patronMaximo;
    }

    public int getTipoDato() {
        return tipoDato;
    }

    public int getTipoComparacion() {
        return tipoComparacion;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public boolean isTextoExacto() {
        return textoExacto;
    }
        
    //Acciones
    public boolean seCumple(){  
        return seCumple(this.getDato());
    }
    
    public boolean seCumple(String portapapeles){  
        Boolean resultado;
        
        if(!portapapeles.isEmpty()){
            switch (tipoDato){
                case TEXTO:
                    if(textoExacto){                        
                        if(caseSensitive){
                            resultado = patronMinimo.equals(portapapeles);                            
                        }else{
                            resultado = patronMinimo.equalsIgnoreCase(portapapeles);
                        }
                    }else{
                        if(caseSensitive){
                            resultado = patronMinimo.contains(portapapeles) || portapapeles.contains(patronMinimo);
                        }else{
                            resultado = patronMinimo.toLowerCase().contains(portapapeles.toLowerCase()) || portapapeles.toLowerCase().contains(patronMinimo.toLowerCase());
                        }
                    }
                    
                    switch(tipoComparacion){
                        case TEXTO_IGUAL:
                            return resultado;
                        case TEXTO_DIFERENTE:
                            return !resultado;
                    }                       
                case NUMERO:  
                    if(Validador.esNumero(portapapeles.trim())){
                        switch(tipoComparacion){
                            case IGUAL:
                                if(Validador.esNumero(patronMinimo.trim())){
                                    return Integer.parseInt(portapapeles.trim()) == Integer.parseInt(patronMinimo.trim());
                                }else{
                                    return false;
                                }
                            case NO_IGUAL:
                                if(Validador.esNumero(patronMinimo.trim())){
                                    return Integer.parseInt(portapapeles.trim()) != Integer.parseInt(patronMinimo.trim());
                                }else{
                                    return false;
                                }                            
                            case MAYOR:
                                if(Validador.esNumero(patronMinimo.trim())){
                                    return Integer.parseInt(portapapeles.trim()) > Integer.parseInt(patronMinimo.trim());
                                }else{
                                    return false;
                                }
                            case MAYOR_IGUAL:
                                if(Validador.esNumero(patronMinimo.trim())){
                                    return Integer.parseInt(portapapeles.trim()) >= Integer.parseInt(patronMinimo.trim());
                                }else{
                                    return false;
                                }
                            case MENOR:
                                if(Validador.esNumero(patronMinimo.trim())){
                                    return Integer.parseInt(portapapeles.trim()) < Integer.parseInt(patronMinimo.trim());
                                }else{
                                    return false;
                                }                            
                            case MENOR_IGUAL:
                                if(Validador.esNumero(patronMinimo.trim())){
                                    return Integer.parseInt(portapapeles.trim()) <= Integer.parseInt(patronMinimo.trim());
                                }else{
                                    return false;
                                }                            
                            case DENTRO_RANGO:
                                if(Validador.esNumero(patronMinimo.trim()) && Validador.esNumero(patronMaximo.trim())){
                                    return (Integer.parseInt(portapapeles.trim()) >= Integer.parseInt(patronMinimo.trim())) && (Integer.parseInt(portapapeles.trim()) <= Integer.parseInt(patronMaximo.trim()));
                                }else{
                                    return false;
                                }
                            case FUERA_RANGO:
                                if(Validador.esNumero(patronMinimo.trim()) && Validador.esNumero(patronMaximo.trim())){
                                    return !((Integer.parseInt(portapapeles.trim()) >= Integer.parseInt(patronMinimo.trim())) && (Integer.parseInt(portapapeles.trim()) <= Integer.parseInt(patronMaximo.trim())));
                                }else{
                                    return false;
                                }
                            case ES_PAR:
                                return (Integer.parseInt(portapapeles.trim()) % 2) == 0;
                            case ES_IMPAR:
                                return (Integer.parseInt(portapapeles.trim()) % 2) != 0;
                        }
                    }
                        
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        String mensaje;
        mensaje = java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("CONDICIONAL") + ": ";  //Condicional    
        
        switch(tipoDato){
            case TEXTO:
                switch(tipoComparacion){
                    case TEXTO_IGUAL:
                        mensaje += " " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("IGUAL"); // Igual
                        break;
                    case TEXTO_DIFERENTE:
                        mensaje += " " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DIFERENTE"); //Diferente
                }
                if(textoExacto){
                    mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("TEXTO EXACTO"); //Texto exacto
                }else{
                    mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("TEXTO PARCIAL"); // Texto parcial
                }
                if(caseSensitive){
                    mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DISTINGUE MAYÚSCILAS Y MINÚSCULAS"); // Distingue mayúsc. y minúsc.
                }
                
                mensaje += " | \"" + patronMinimo + "\"";
                break;
            case NUMERO:
                switch(tipoComparacion){
                    case IGUAL:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " = " + patronMinimo;
                        break;
                    case NO_IGUAL:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " <> " + patronMinimo;
                        break;
                    case MAYOR:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " > " + patronMinimo;
                        break;
                    case MAYOR_IGUAL:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " >=  " + patronMinimo;
                        break;
                    case MENOR:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " < " + patronMinimo;
                        break;
                    case MENOR_IGUAL:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " <= " + patronMinimo;
                        break;
                    case DENTRO_RANGO:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO DENTRO DEL RANGO") + "[" + patronMinimo + ", " + patronMaximo + "]" ; // Número dentro del rango
                        break;
                    case FUERA_RANGO:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO FUERA DEL RANGO") + "[" + patronMinimo + ", " + patronMaximo + "]" ; // Número fuera del rango
                        break;
                    case ES_PAR:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO PAR"); // Número Par
                        break;
                    case ES_IMPAR:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO IMPAR"); // Número Impar
                }
                break;
            default:
                mensaje = java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("CONDICIONAL NO DEFINIDA"); // Condicional no definida
        }
        return mensaje;
    }
}
