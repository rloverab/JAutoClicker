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

import java.io.Serializable;

/**
 * Clase <b>AccionEspecial</b>
 * Contiene métodos relacionados con la ejecución de atajos de teclado mas comúnes.
 * @author Roger Lovera
 */
public class AccionEspecial implements Serializable{
    //Atributos
    private int tipoAccionEspecial;
    private String contenidoPortapapeles;
    
    //Constantes
    /**
     * Tipo de acción especial: 
     * <b>NO_DEFINIDA = 0</b>
     */
    public final static int NO_DEFINIDA = 0;

    /**
     * Tipo de acción especial: 
     * <b>COPIAR = 1</b>
     */
    public final static int COPIAR = 1;

    /**
     * Tipo de acción especial: 
     * <b>CORTAR = 2</b>
     */
    public final static int CORTAR = 2;

    /**
     * Tipo de acción especial: 
     * <b>PEGAR = 3</b>
     */
    public final static int PEGAR = 3;

    /**
     * Tipo de acción especial: 
     * <b>DESHACER = 4</b>
     */
    public final static int DESHACER = 4;

    /**
     * Tipo de acción especial: 
     * <b>REHACER = 5</b>
     */
    public final static int REHACER = 5;

    /**
     * Tipo de acción especial: 
     * <b>CERRAR = 6</b>
     */
    public final static int CERRAR = 6;

    /**
     * Tipo de acción especial: 
     * <b>SALIR = 7</b>
     */
    public final static int SALIR = 7;

    /**
     * Tipo de acción especial: 
     * <b>CAMBIAR_VENTANA = 8</b>
     */
    public final static int CAMBIAR_VENTANA = 8;
    
    /**
     * Tipo de acción especial: 
     * <b>REFRESCAR = 9</b>
     */
    public final static int REFRESCAR = 9;
    
    /**
     * Tipo de acción especial: 
     * <b>ENTRAR = 10</b>
     */
    public final static int ENTRAR = 10;
    
    /**
     * Tipo de acción especial: 
     * <b>ESCAPAR = 11</b>
     */
    public final static int ESCAPAR = 11;
    
    /**
     * Tipo de acción especial: 
     * <b>LIMPIAR_PORTAPAPELES = 12</b>
     */
    public final static int LIMPIAR_PORTAPAPELES = 12;
    
    /**
     * Tipo de acción especial: 
     * <b>INTRODUCIR_AL_PORTAPAPELES = 13</b>
     */
    public final static int INTRODUCIR_AL_PORTAPAPELES = 13;
    
    /**
     * Tipo de acción especial: 
     * <b>SELECCIONAR_PESTANA_DERECHA = 14</b>
     */
    public final static int SELECCIONAR_PESTANA_DERECHA = 14;
    
    /**
     * Tipo de acción especial: 
     * <b>SELECCIONAR_PESTANA_IZQUIERDA = 15</b>
     */
    public final static int SELECCIONAR_PESTANA_IZQUIERDA = 15;
    
    //Constructores
    public AccionEspecial() {
        this(NO_DEFINIDA);
    }
    
    public AccionEspecial(int accionEspecial){
        this.tipoAccionEspecial = accionEspecial;
        contenidoPortapapeles = "";
    }
    
    public AccionEspecial(String contenidoPortapapeles){
        tipoAccionEspecial = AccionEspecial.INTRODUCIR_AL_PORTAPAPELES;
        this.contenidoPortapapeles = contenidoPortapapeles;
    }
    
    //Modificadores
    /**
     * Establece el accionEspecial que se desea ejecutar
     * @param tipoAccionEpsecial Su valor se puede tomar de las constantes de la clase <b>AccionEspecial</b>     
     */
    public void setTipoAccionEspecial(int tipoAccionEpsecial){
        this.tipoAccionEspecial = tipoAccionEpsecial;
    }
    
    public void setContenidoPortapapeles(String contenidoPortapapeles){
        this.contenidoPortapapeles = contenidoPortapapeles;
    }
    
    //Consulta
    
    public String getContenidoPortapapeles(){
        return contenidoPortapapeles;
    }

    /**
     * Devuelve el tipo de acción especial establecido
     * @return 
     * <b>NO_DEFINIDA = 0</b><br>
     * <b>COPIAR = 1</b><br>
     * <b>CORTAR = 2</b><br>
     * <b>PEGAR = 3</b><br>
     * <b>DESHACER = 4</b><br>
     * <b>REHACER = 5</b><br>
     * <b>CERRAR = 6</b><br>
     * <b>SALIR = 7</b><br>
     * <b>CAMBIAR_VENTANA = 8</b><br>
     * <b>REFRESCAR = 9</b><br>
     * <b>ENTRAR = 10</b><br>
     * <b>ESCAPAR = 11</b><br>
     * <b>LIMPIAR_PORTAPAPELES = 12</b><br>
     * <b>INTRODUCIR_AL_PORTAPAPELES = 13</b><br>
     * <b>SELECCIONAR_PESTANA_DERECHA = 14</b><br>
     * <b>SELECCIONAR_PESTANA_IZQUIERDA = 15</b><br>
     */
    public int getAccionEspecial(){
        return tipoAccionEspecial;
    }
    //Acciones

    /**
     * Ejecuta el accionEspecial definido en el objeto.
     */
    public void ejecutar(){
        switch (tipoAccionEspecial){
            case COPIAR:
                Hotkeys.copiar();
                break;
            case CORTAR:
                Hotkeys.cortar();
                break;
            case PEGAR:
                Hotkeys.pegar();                
                break;            
            case DESHACER:
                Hotkeys.deshacer();
                break;
            case REHACER:
                Hotkeys.rehacer();
                break;
            case CERRAR:    
                Hotkeys.cerrar();
                break;
            case SALIR:   
                Hotkeys.salir();
                break;
            case CAMBIAR_VENTANA:   
                Hotkeys.cambiarVentana();
                break;
            case REFRESCAR:
                Hotkeys.refrescar();
                break;
            case ENTRAR:
                Hotkeys.entrar();
                break;
            case ESCAPAR:
                Hotkeys.escapar();
                break;
            case INTRODUCIR_AL_PORTAPAPELES:
                Portapapeles.setContenido(contenidoPortapapeles);
                break;
            case LIMPIAR_PORTAPAPELES:
                Portapapeles.limpiarPortapapeles();
                break;
            case SELECCIONAR_PESTANA_DERECHA:
                Hotkeys.seleccionarPestanaDerecha();
                break;
            case SELECCIONAR_PESTANA_IZQUIERDA:
                Hotkeys.seleccionarPestanaIzquierda();
        }
    }
    
    @Override
    public String toString(){
        switch (tipoAccionEspecial){
            case COPIAR:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: COPIAR - CONTROL C"); // Copiar - Control C
            case CORTAR:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: CORTAR - CONTROL X"); // Cortar - Control X
            case PEGAR:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: PEGAR - CONTROL V"); // Pegar - Control V
            case DESHACER:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: DESHACER - CONTROL Z"); // Deshacer - Control Z
            case REHACER:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: REHACER - CONTROL Y"); // Rehacer - Control Y
            case CERRAR:    
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: CERRAR - CONTROL F4"); // Cerrar - Control F4
            case SALIR:   
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: SALIR - ALT F4"); // Salir - Alt F4
            case CAMBIAR_VENTANA:   
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: CAMBIAR VENTANA - ALT TAB"); // Cambiar ventana - Alt Tab
            case REFRESCAR:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: REFRESCAR - F5"); // Refrescar - F5
            case ENTRAR:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: ENTRAR - ENTER"); // Entrar - ENTER
            case ESCAPAR:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: ESCAPAR - ESC"); // Escapar - ESC
            case INTRODUCIR_AL_PORTAPAPELES:
                if(contenidoPortapapeles.trim().length() > 8){
                    return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("INTRODUCIR AL PORTAPAPELES") + ": \"" + contenidoPortapapeles.trim().substring(0, 5) + "...\""; // Introducir al portapapeles
                }else{
                    return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("INTRODUCIR AL PORTAPAPELES") + ": \"" + contenidoPortapapeles.trim() + "\""; // Introducir al portapapeles
                }
            case LIMPIAR_PORTAPAPELES:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("LIMPIAR EL PORTAPAPELES"); // Limpiar el portapapeles
            case SELECCIONAR_PESTANA_DERECHA:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: SELECCIONAR PESTAÑA DERECHA"); // Seleccionar pestaña derecha
            case SELECCIONAR_PESTANA_IZQUIERDA:
                return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("HOTKEY: SELECCIONAR PESTAÑA IZQUIERDA"); // Seleccionar pestaña derecha
            default:
                return "No definido";
        }
    }
}
