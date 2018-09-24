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
