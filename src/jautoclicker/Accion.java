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

/**
 * Clase <font color=gray><b>Accion</b></font>
 * Contiene el tipo de accion se va a ejecutar. Estos pueden ser:<br>
 * <ul>
 *  <li><font color=green><b>Bucles</b></font></li>
 *  <li><font color=blue><b>Condicionales</b></font></li>
 *  <li><font color=red><b>Acciones de cursor</b></font></li>
 *  <li><font color=fuchsia><b>Acciones especiales</b></font></li>
 * </ul>
 * @author Roger Lovera
 */
public class Accion {
    private Bucle bucle; //Los Bucles contienen objetos de clase Accion
    private Condicional condicional; //Los Condicionales contienen objetos de clase Accion
    private AccionMouse accionMouse; //Objeto que contiene la accion que debe ejecutar el mouse
    private AccionEspecial accionEspecial; //Objeto con métodos con instrucciones especiales
    private String observacion; //Contiene una observacíón sobre la acciòn a ejecutar

    /**
     * <b>Tipo de acción:</b>
     * <b>NO_DEFINIDA = -1</b>
     */
    public final static int NO_DEFINIDA = -1;

    /**
     * <b>Tipo de acción:</b>
     * <font color=green><b>BUCLE = 0</b></font>
     */
    public final static int BUCLE = 0;

    /**
     * <b>Tipo de acción:</b>
     * <font color=blue><b>CONDICIONAL = 1</b></font>
     */
    public final static int CONDICIONAL = 1;

    /**
     * <b>Tipo de acción:</b>
     * <font color=red><b>ACCIONMOUSE = 2</b></font>
     */
    public final static int ACCIONMOUSE = 2;

    /**
     * <b>Tipo de acción:</b>
     * <font color=fuchsia><b>ACCIONESPECIAL = 3</b></font>
     */
    public final static int ACCIONESPECIAL = 3;
    
    //Modificadores
    public Accion(){
        bucle = null;
        condicional = null;
        accionMouse = null;
        accionEspecial = null;
        observacion = "";        
    }            
    
    /**
     * Define un objeto tipo <font color=green><b>Bucle</b></font> en la clase <b>Accion</b>
     * @param bucle Objeto de tipo Bucle
     */
    public void setBucle(Bucle bucle) {
        this.bucle = bucle;
        condicional = null;
        accionMouse = null;
        accionEspecial = null;
    }
    
    /**
     * Define un objeto tipo <font color=blue><b>Condicional</b></font> en la clase <b>Accion</b>
     * @param condicional Objeto de tipo Condicional
     */
    public void setCondicional(Condicional condicional) {
        bucle = null;
        this.condicional = condicional;
        accionMouse = null;
        accionEspecial = null;
    }
    
    /**
     * Define un objeto tipo <font color=red><b>MoveMouse</b></font> en la clase <b>Accion</b>
     * @param accionMouse Objeto de tipo MoveMouse
     */
    public void setAccionMouse(AccionMouse accionMouse) {
        bucle = null;
        condicional = null;
        this.accionMouse = accionMouse;
        accionEspecial = null;
    }
    
    /**
     * Define un objeto tipo <font color=fuchsia><b>AccionEspecial</b></font> en la clase <b>Accion</b>
     * @param accionEspecial Objeto de tipo AccionMouse
     */
    public void setAccionEspecial(AccionEspecial accionEspecial) {
        bucle = null;
        condicional = null;
        accionMouse = null;
        this.accionEspecial = accionEspecial;
    }
    
    /**
     * Establece una observación relacionada con el objeto
     * @param observacion La observación (cadena de caracteres)
     */
    public void setObservacion(String observacion){
        this.observacion = observacion;
    }
    
    //Consultas    
    /**
     * Devuelve el tipo de accion vinculada al objeto
     * @return 
     * <font color=black><b>NO_DEFINIDA</b></font> si no se ha definido ninguna accion
     * <font color=green><b>BUCLE</b></font> si se trata de un bucle<br>
     * <font color=blue><b>CONDICIONAL</b></font> si se trata de una condicional<br>
     * <font color=red><b>ACCIONMOUSE</b></font> si se trata de una accion de cursor<br>     
     * <font color=fuchsia><b>ACCIONESPECIAL</b></font> si se trata de una accion de cursor<br>     
     */
    public int getTipoAccion(){
        if(bucle != null){
            return BUCLE;
        }else if(condicional != null){
            return CONDICIONAL;
        }else if(accionMouse != null){
            return ACCIONMOUSE;
        }else if(accionEspecial != null){
            return ACCIONESPECIAL;
        }
        return NO_DEFINIDA;
    }
    
    /**
     * Devuelve el objeto de clase <font color=green><b>Bucle</b></font> contenido en la clase <b>Accion</b>(solo si existe)
     * @return
     * <font color=green><b>Objeto de clase Bucle</b></font> si existe<br>
     * <font color=black><b>null</b></font> si no existe
     */
    public Bucle getBucle(){
        return bucle;
    }
    
    /**
     * Devuelve el objeto de clase <font color=blue><b>Condicional</b></font> contenido en la clase <b>Accion</b>(solo si existe)
     * @return
     * <font color=blue><b>Objeto de clase Condicional</b></font> si existe<br>
     * <font color=black><b>null</b></font> si no existe
     */
    public Condicional getCondicional(){
        return condicional;        
    }
    
    /**
     * Devuelve el objeto de clase <font color=red><b>AccionMouse</b></font> contenido en la clase <b>Accion</b>(solo si existe)
     * @return
     * <font color=red><b>Objeto de clase MoveMouse</b></font> si existe<br>
     * <font color=black><b>null</b></font> si no existe
     */
    public AccionMouse getAccionMouse(){
        return accionMouse;
    }
    
    /**
     * Devuelve el objeto de clase <font color=fuchsia><b>AccionEspecial</b></font> contenido en la clase <b>Accion</b>(solo si existe)
     * @return
     */
    public AccionEspecial getAccionEspecial(){
        return accionEspecial;
    }
    
    /**
     * Devuelve la observación establecida en el objeto
     * @return
     * <font color=black><b>Observación</b></font> si no existe (cadena de caracteres)
     */
    public String getObservacion(){
        return observacion;
    }
    
    //Acciones    
    @Override
    public String toString(){
        switch (getTipoAccion()){
            case BUCLE:
                return bucle.toString();
            case CONDICIONAL:
                return condicional.toString();                
            case ACCIONMOUSE:
                return accionMouse.toString();
            case ACCIONESPECIAL:
                return accionEspecial.toString();
            case NO_DEFINIDA:
                return "No definida";
        }
        return "Default";
    }
}
