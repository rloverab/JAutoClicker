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

import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase <font color=red><b>AccionMouse</b></font>
 * Contiene los datos relacionados a las acciones del cursor.
 * @author Roger Lovera
 */
public class AccionMouse implements Serializable{    
    //Atributos
    private int x;
    private int y;
    private int retardo;
    private int pulsacion;
    private int boton;    
    public static final int CLICK_NINGUNO     = 0;
    public static final int CLICK_SIMPLE      = 1;
    public static final int CLICK_DOBLE       = 2;
    public static final int CLICK_MANTENER    = 3;
    public static final int CLICK_SOLTAR      = 4;
    
    //Constructores
    public AccionMouse(){
        x = 0;
        y = 0;
        pulsacion = 0;
        boton = 0;
        retardo = 1000;
    }
    
    //Modificadores    
    /**
     * Establece la coordenada donde el cursor debe ubicarse.<br>
     * <b>Notas:</b>
     * <ul>
     * <li>El origen del sistema de coordenadas esta ubicado en la esquina superior izquierda del monitor.</li>
     * <li>La unidad de las coordenadas está expresada en pixeles.</li>
     * </ul>
     * @param x Coordenada del cursor en el eje X
     * @param y Coordenada del cursor en el ehe Y
     */
    public void setCoordenada(int x, int y){
        this.x = x;
        this.y = y;        
    }
    
    public void setRetardo(int retardo){           
        this.retardo = retardo;
    }
    
    public void setPulsacion(int pulsacion){
        this.pulsacion = pulsacion;
    }
        
    public void setBoton(int boton){ //Asigna botón a pulsar. NOTA: S.O Base: Windows
        this.boton = boton;        
    } 
        
    //Consultas
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getPulsacion(){
        return pulsacion;
    }
    
    public String getPulsacionTipo(){
        switch(pulsacion){
            case 0: //Ninguno
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("NINGUNO");
            case 1: //Simple
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("SIMPLE");
            case 2: //Doble
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("DOBLE");
            case 3: //Mantener
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("MANTENER");
            case 4: //Soltar
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("SOLTAR");
        }
        return null;        
    }
       
    public String getBotonNombre(){
        switch (boton){            
            case MouseEvent.BUTTON1: //Izquierdo
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("IZQUIERDO");
            case MouseEvent.BUTTON2: //Centro o derecho               
                if(Validador.esWindows()){ //Centro
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("CENTRO");
                }else{ //Derecho
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("DERECHO");
                }                
            case MouseEvent.BUTTON3: //Derecho o centro                
                if(Validador.esWindows()){ //Derecho
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("DERECHO");
                }else{ //Centro
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("CENTRO");
                }                 
            default: //Ninguno
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("NINGUNO");
        }
    }
    
    public int getBoton(){
        return boton;        
    }   
    
    public int getRetardo(){
        return retardo;
    }
        
    //Acciones    
    @Override
    public String toString(){        
        return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("CLIC") + ": (" + x + "," + y + ") - " + retardo + "ms. - " + getBotonNombre() + " - " + getPulsacionTipo();
    }    
}
