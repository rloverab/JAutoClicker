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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase <font color=blue><b>Condicional</b></font>
 * Contiene la colecciòn de objetos <font color=grey><b>Accion</b></font> que deben ejecutarse su se cumple una condiciòn.
 * Las condicionales se rigen mediante la comparaciòn de valores tomados del portapapeles y un valor establecido previamente.
 * <b>Nota:</b> 
 * <ul>
 * <li>Solo se admite comparaciones de cadenas de texto y valores numéricos.
 * <li>La comparación de cadenas de texto admite evaluaciones exactas y parciales.</li>
 * <li>La comparación de valores numéricos solo admite números enteros</li>
 * </ul>
 * @author Roger Lovera
 */
public class Condicional extends Acciones{        
    private String comparar;
    private String toString;
    private int tipoValor;
    private int tipoComparacion;
    private boolean caseSensitive;
    private boolean textoExacto;
    private Clipboard cb;

    /**
     * Tipo de comparacion: <b><i>NO_DEFINIDA = 0</i></b> 
     */
    public final static int NO_DEFINIDA = 0;

    /**
     * Tipo de dato: <b><i>TEXTO = 1</i></b> 
     */
    public final static int TEXTO = 1;

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
    
    //Constructor
    public Condicional() {   
        super();        
        comparar = "";
        toString = "Condicional";
        tipoValor = NO_DEFINIDA;
        tipoComparacion = NO_DEFINIDA;
        caseSensitive = false;
        textoExacto = false;
        cb = null;
    }
    
    //Modificadores

    /**
     * Establece el valor con el que se va a realizar la comparación.
     * @param comparar
     */
    public void setComparar(String comparar) {
        this.comparar = comparar.trim();
    }
    
    /**
     * Se define el tipo de valores a comparar
     * @param tipoValor
     */
    public void setTipoValor(int tipoValor) {
        this.tipoValor = tipoValor;
    }
    
    /**
     * Se define el tipo de comparación a realizar
     * @param tipoComparacion
     */
    public void setTipoComparacion(int tipoComparacion){
        this.tipoComparacion = tipoComparacion;
    }
    
    /**
     * Se indica si al comparar cadenas de caracteres debe considerarse las mayúsculas o las minúsculas
     * @param caseSensitive <font color=blue><b>true</b></font> para considerar mayúsculas y minúsculas y <font color=red><b>false</b></font> para no considerarlas
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Indica si al comparar cadenas de caracteres se debe considerar cadenas completas o un fragmento esta contenido en la cadena a comparar.
     * @param textoExacto
     */
    public void setTextoExacto(boolean textoExacto) {
        this.textoExacto = textoExacto;
    }
    
    //Consultas
    
    //Acciones
    public boolean seCumple(){
        cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        String portapapeles;                
        
        try {
            portapapeles = ((String)cb.getData(DataFlavor.stringFlavor)).trim();
        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(Condicional.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        if(cb.isDataFlavorAvailable(DataFlavor.stringFlavor)){
            switch (tipoValor){
                case TEXTO:
                    if(textoExacto){                        
                        if(caseSensitive){
                            toString = "Condicional: Texto exacto y sensible a mayúsculas - " + comparar;
                            return comparar.equals(portapapeles);                            
                        }else{
                            toString = "Condicional: Texto exacto - " + comparar;
                            return comparar.equalsIgnoreCase(portapapeles);
                        }
                    }else{
                        if(caseSensitive){
                            toString = "Condicional: Texto parcial y sensible a mayúsculas - " + comparar;
                            return comparar.contains(portapapeles);
                        }else{
                            toString = "Condicional: Texto parcial - " + comparar;
                            return comparar.toLowerCase().contains(portapapeles.toLowerCase());
                        }
                    }
                case NUMERO:
                    if(Validador.esNumero(portapapeles) && Validador.esNumero(comparar)){
                        switch(tipoComparacion){
                            case IGUAL:                                
                                toString = "Condicional: Número - Igual - " + comparar;
                                return Integer.parseInt(portapapeles) == Integer.parseInt(comparar);
                            case NO_IGUAL:
                                toString = "Condicional: Número - No igual - " + comparar;
                                return Integer.parseInt(portapapeles) != Integer.parseInt(comparar);
                            case MAYOR:
                                toString = "Condicional: Número - Mayor - " + comparar;
                                return Integer.parseInt(portapapeles) > Integer.parseInt(comparar);
                            case MAYOR_IGUAL:
                                toString = "Condicional: Número - Mayor igual - " + comparar;
                                return Integer.parseInt(portapapeles) >= Integer.parseInt(comparar);
                            case MENOR:
                                toString = "Condicional: Número - Menor - " + comparar;
                                return Integer.parseInt(portapapeles) < Integer.parseInt(comparar);
                            case MENOR_IGUAL:
                                toString = "Condicional: Número - Menor igual - " + comparar;
                                return Integer.parseInt(portapapeles) <= Integer.parseInt(comparar);
                        }
                    }
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return toString;
    }
}
