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
 * Clase <b>AccionEspecial</b>
 * Contiene métodos relacionados con la ejecución de atajos de teclado mas comúnes.
 * @author Roger Lovera
 */
public class AccionEspecial {
    //Atributos
    private int accionEspecial;
    public final static int NO_DEFINIDA = 0;
    public final static int COPIAR = 1;
    public final static int CORTAR = 2;
    public final static int PEGAR = 3;
    public final static int MOVER = 4;
    public final static int DESHACER = 5;
    public final static int REHACER = 6;
    public final static int CERRAR = 7;
    public final static int SALIR = 8;
    public final static int CAMBIAR_VENTANA = 9;
    
    //Constructores
    public AccionEspecial() {
        this(NO_DEFINIDA);
    }
    
    public AccionEspecial(int accionEspecial){
        this.accionEspecial = accionEspecial;
    }
    
    //Modificadores
    /**
     * Establece el accionEspecial que se desea ejecutar
     * @param accionEspecial Su valor se puede tomar de las constantes de la clase <b>AccionEspecial</b>
     */
    public void setAccionEspecial(int accionEspecial){
        this.accionEspecial = accionEspecial;
    }
    
    //Consulta
    //Acciones

    /**
     * Ejecuta el accionEspecial definido en el objeto.
     */
    public void ejecutar(){
        switch (accionEspecial){
            case COPIAR:
                Hotkeys.copiar();
                break;
            case CORTAR:
                Hotkeys.cortar();
                break;
            case PEGAR:
                Hotkeys.pegar();                
                break;
            case MOVER:
                Hotkeys.mover();
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
        }
    }
    
    @Override
    public String toString(){
        switch (accionEspecial){
            case COPIAR:
                return "Copiar - Control C";
            case CORTAR:
                return "Cortar - Control X";
            case PEGAR:
                return "Pegar - Control V";           
            case DESHACER:
                return "Deshacer - Control Z";
            case REHACER:
                return "Rehacer - Control Y";
            case CERRAR:    
                return "Cerrar - Control F4";
            case SALIR:   
                return "Salir - Alt F4";
            case CAMBIAR_VENTANA:   
                return "Cambiar ventana - Alt Tab";
            default:
                return "No definido";
        }
    }
}
