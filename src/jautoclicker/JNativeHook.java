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
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseInputListener;

/**
 *
 * @author Roger Lovera
 */
public class JNativeHook {

    public JNativeHook() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        
    //Desactivar salidas por consola
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);            
        logger.setUseParentHandlers(false);
    }
    
    
    public void addMouseListener(NativeMouseInputListener nativeMouseInputLister){
        GlobalScreen.addNativeMouseListener(nativeMouseInputLister);    
        GlobalScreen.addNativeMouseMotionListener(nativeMouseInputLister);
    }
    
    public void addKeyListener(NativeKeyListener nativeKeyListener){
        GlobalScreen.addNativeKeyListener(nativeKeyListener);                
    }
    
    public static void cerrar(){
        try {
            GlobalScreen.unregisterNativeHook();            
        } catch (NativeHookException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
