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
    private int atajo;
    public final int NO_DEFINIDA = 0;
    public final int COPIAR = 1;
    public final int PEGAR = 2;
    public final int MOVER = 3;
    public final int DESHACER = 4;
    public final int REHACER = 5;
    public final int CERRAR = 6;
    public final int SALIR = 7;
    public final int CAMBIAR_ENTRE_APLICACIONES = 8;
    
    //Constructores
    public AccionEspecial() {
        atajo = 0;
    }
    
    //Modificadores
    /**
     * Establece el atajo que se desea ejecutar
     * @param atajo Su valor se puede tomar de las constantes de la clase <b>AccionEspecial</b>
     */
    public void setAtajo(int atajo){
        this.atajo = atajo;
    }
    
    //Consulta
    //Acciones

    /**
     * Ejecuta el atajo definido en el objeto.
     */
    public void ejecutar(){
        switch (atajo){
            case COPIAR:
                Hotkeys.copiar();
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
            case CAMBIAR_ENTRE_APLICACIONES:   
                Hotkeys.cambiarEntreAplicaciones();                
        }
    }
    
    @Override
    public String toString(){
        switch (atajo){
            case COPIAR:
                return "Copiar";
            case PEGAR:
                return "Pegar";
            case MOVER:
                return "Mover";
            case DESHACER:
                return "Deshacer";
            case REHACER:
                return "Rehacer";
            case CERRAR:    
                return "Cerrar";
            case SALIR:   
                return "Salir";
            case CAMBIAR_ENTRE_APLICACIONES:   
                return "Cambiar entre aplicaciones";
            default:
                return "No definido";
        }
    }
}
