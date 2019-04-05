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

import com.sun.glass.events.KeyEvent;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase <b>Hotkeys</b>
 * Contiene métodos que ejecutan diversas acciones especiales.
 * @author Roger Lovera
 */
public final class Hotkeys {
    //Atributos
    //Constructor
    //Modificadores
    //Consultas
    //Acciones

    /**
     * Ejecuta la acción <b>copiar</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Control</u></i> + <i><u>C</u></i>
     */
    public static void copiar(){
        Robot robot;
        try {            
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_C);            
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>cortar</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Control</u></i> + <i><u>C</u></i>
     */
    public static void cortar(){
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_X);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_X);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ejecuta la acción <b>pegar</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Control</u></i> + <i><u>V</u></i>
     */
    public static void pegar(){        
        Robot robot;        
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>deshacer</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Control</u></i> + <i><u>Z</u></i>
     */
    public static void deshacer(){
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_Z);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_Z);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>rehacer</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Control</u></i> + <i><u>Y</u></i>
     */
    public static void rehacer(){
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_Y);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_Y);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>cerrar ventana de un programa</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Control</u></i> + <i><u>F4</u></i>
     */
    public static void cerrar(){
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_F4);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>salir de un programa</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Alt</u></i> + <i><u>F4</u></i>
     */
    public static void salir(){
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F4);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_F4);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>cambiar entre aplicaciones</b> mediante la emulación de un atajo de teclado.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>Alt</u></i> + <i><u>Tabulador</u></i>
     */
    public static void cambiarVentana(){
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_TAB);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>refrescar</b> mediante la emulación de pulsación de la tecla F5.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * Los programas deben aceptar dicha tecla con esa función asignada. (ej. Navegadores de internet).
     * <b>Tecla:</b> <i><u>F5</u></i>
     */
    public static void refrescar(){
        try {
            Robot robot;
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_F5);
            robot.keyRelease(KeyEvent.VK_F5);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>escapar</b> mediante la emulación de pulsación de la tecla ESC.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Combinación:</b> <i><u>ESC</u></i>
     */
    public static void escapar(){
        try {
            Robot robot;
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta la acción <b>entrar</b> mediante la emulación de pulsación de la tecla Enter.
     * Esta acción afecta al programa cuya ventana tenga el foco en ese instante.<br><br>
     * <b>Tecla:</b> <i><u>Enter</u></i>
     */
    public static void entrar(){
        try {
            Robot robot;
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void seleccionarPestanaDerecha(){
        try {
            Robot robot;
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_PAGE_DOWN);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void seleccionarPestanaIzquierda(){
        try {
            Robot robot;
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);            
            robot.keyPress(KeyEvent.VK_PAGE_UP);
            robot.keyRelease(KeyEvent.VK_CONTROL);            
            robot.keyRelease(KeyEvent.VK_PAGE_UP);
        } catch (AWTException ex) {
            Logger.getLogger(Hotkeys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}