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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.NativeHookException;

/**
 *
 * @author Roger Lovera
 */
public class JAutoClicker {
    //Acciones
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        //Variables
        Ventana ventana; //Objeto que contiene la Interfaz de usuario
        JNativeHook jNativeHook;
        
        //Inicializacion
        ventana = new Ventana(); //Instancia un objeto de clase Ventana 
        ventana.setLocationRelativeTo(null); //Ubica la ventana en el centro de la pantalla
        ventana.setAlwaysOnTop(true); //Mantiene la ventana siempre al frente
        
        //Inicialización de JNativeHook
        try {
            jNativeHook = new JNativeHook();
            ventana.setJNativeHook(jNativeHook);
            jNativeHook.addMouseListener(ventana);
            jNativeHook.addKeyListener(ventana);
        } catch (NativeHookException ex) {
            Logger.getLogger(JAutoClicker.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        //Fin inicialización de JNativeHook        
        ventana.setVisible(true); //Muesta la ventana   
    }   
}
