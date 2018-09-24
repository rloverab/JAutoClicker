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
 *
 * @author Roger Lovera
 */
public class MoveMouse implements Serializable,Comparable<MoveMouse>{
    private int indice;
    private int x;
    private int y;
    private int retardo;
    private int pulsacion;
    private int boton;
    private int indiceBucle;
    private int iteraciones;   
    private final char[] GRUPOS;
    public static final int CLICK_NINGUNO     = 0;
    public static final int CLICK_SIMPLE      = 1;
    public static final int CLICK_DOBLE       = 2;
    public static final int CLICK_MANTENER    = 3;
    public static final int CLICK_SOLTAR      = 4;
    //private final ResourceBundle bundle;
    
  
    
    //Constructores
    public MoveMouse(){
        //this.bundle = ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault());
        GRUPOS = new char[]{
            'A','B','C','D','E','F','G',
            'H','I','J','K','L','M','N',
            'O','P','Q','R','S','T','U',
            'V','W','X','Y', 'Z'};
        x = 0;
        y = 0;
        indice = 0;
        pulsacion = 0;
        boton = 0;
        indiceBucle = 0;
        iteraciones = 1;
        retardo = 1000;
    }
    
    //Modificadores
    public void setIndice (int indice){
        this.indice = indice;
    }
    
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
        
    public void setIndiceBucle(int indiceBucle){
        this.indiceBucle = indiceBucle;
    }
    
    public void setIteraciones(int iteraciones){
        this.iteraciones = iteraciones;
    }
    
    //Consultas
    public int getIndice(){
        return indice;
    }
    
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
            case 0: //return "Ninguno";
                //return bundle.getString("NINGUNO");
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("NINGUNO");
            case 1: //return "Simple";
                //return bundle.getString("SIMPLE");
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("SIMPLE");
            case 2: //return "Doble";
                //return bundle.getString("DOBLE");
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("DOBLE");
            case 3: //return "Mantener";
                //return bundle.getString("MANTENER");
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("MANTENER");
            case 4: //return "Soltar";
                //return bundle.getString("SOLTAR");
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("SOLTAR");
        }
        return null;        
    }
       
    public String getBotonNombre(){
        switch (boton){            
            case MouseEvent.BUTTON1: 
                //return "Izquierdo";
                //return bundle.getString("IZQUIERDO");
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("IZQUIERDO");
            case MouseEvent.BUTTON2:                
                if(Validador.esWindows()){
                    //return "Centro";
                    //return bundle.getString("CENTRO");
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("CENTRO");
                }else{
                    //return "Derecho";
                    //return bundle.getString("DERECHO");
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("DERECHO");
                }                
            case MouseEvent.BUTTON3:                 
                if(Validador.esWindows()){
                    //return "Derecho";
                    //return bundle.getString("DERECHO");
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("DERECHO");
                }else{
                    //return "Centro";
                    //return bundle.getString("CENTRO");
                    return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("CENTRO");
                }                 
            default:
                //return "Ninguno";
                //return bundle.getString("NINGUNO");
                return ResourceBundle.getBundle("jautoclicker/Bundle", Locale.getDefault()).getString("NINGUNO");
        }
    }
    
    public int getBoton(){
        return boton;        
    }   
    
    public int getIndiceBucle(){
        return indiceBucle;
    }
    
    public int getIteraciones(){
        return iteraciones;
    }
    
    public int getRetardo(){
        return retardo;
    }
    
    public Object[] getVector(){
        return new Object[]{getIndice(),getX(),getY(),getRetardo(),getBotonNombre(),getPulsacionTipo()};
    }
        
    //Acciones
    
    @Override
    public String toString(){
        return  "Índice:        " + indice + "\n" +
                "Índice bucle: " + indiceBucle + "\n" +
                "Coordenadas:   (" + x + "," + y + ")\n" +
                "Botón:         " + this.getBotonNombre() + "\n" + 
                "Pulsación:     " + this.getPulsacionTipo() + "\n" +
                "Retardo:       " + retardo + " ms\n";
    }    
    
    @Override
    public int compareTo(MoveMouse o) {
        return Integer.compare(indice, o.indice);                    
    }
}
