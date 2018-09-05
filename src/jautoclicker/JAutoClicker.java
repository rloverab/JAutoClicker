/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jautoclicker;

import java.awt.Dimension;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 *
 * @author Roger Lovera
 */
public class JAutoClicker {
    //Atributos
    
    
    //Acciones

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        // TODO code application logic here  
        
        
        //Variables
        Dimension resolucion; //Almacena las resolucion de pantalla actual
        Ventana ventana; //Objeto que contiene la Interfaz de usuario
        
        //Inicializacion
        resolucion = Toolkit.getDefaultToolkit().getScreenSize(); //Obtiene la resolucion actual de la pantalla
        ventana = new Ventana(); //Instancia un objeto de clase Ventana 
        ventana.setLocation( //Ubica la ventana en el centro de la pantalla
                (resolucion.width-ventana.getSize().width)/2, 
                (resolucion.height-ventana.getSize().height)/2);
        ventana.setAlwaysOnTop(true); //Mantiene la ventana siempre al frente
        
        //Inicialización de JNativeHook
        try{
            GlobalScreen.registerNativeHook();
            
            //Desactivar salidas por consola
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);            
            logger.setUseParentHandlers(false);
            
        } catch (NativeHookException e){
            System.out.println(e);
            System.exit(1);
        }
        
        GlobalScreen.addNativeMouseListener(ventana);    
        GlobalScreen.addNativeMouseMotionListener(ventana);
        GlobalScreen.addNativeKeyListener(ventana);                
        //Fin inicialización de JNativeHook
        
        //Tiempo para el Splash
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            System.out.println(e);
        }
            
        ventana.setVisible(true); //Muesta la ventana               
    }    

    
}
