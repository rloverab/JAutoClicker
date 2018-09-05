/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jautoclicker;

/**
 *
 * @author Roger Lovera
 */
public final class Validador {
    //Acciones
    public static boolean esNumero(String cadena){
        try{
            Integer.parseInt(cadena);
            return true;
        }catch(NumberFormatException e){
            System.out.println("La cadena \"" + cadena + "\" no es un número");
            return false;
        }
    }
    
    public static boolean esWindows(){        
        return System.getProperties().getProperty("os.name").toLowerCase().contains("win");
    }
}
