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
package jautoclicker.compatibilidad;

//import java.util.ArrayList;

import java.io.Serializable;


/**
 * Clase <font color=green><b>Bucle</b></font>.
 * Contiene el conjunto de acciones que se va a ejecutar un número determinado de veces.
 * @author Roger Lovera
 */
public class Bucle implements Serializable{
    private int iteraciones;

    //Constructores
    
    public Bucle(int iteraciones) {
        super();
        this.iteraciones = iteraciones;
    }
    
    public Bucle() {
        this(1);        
    }
 
    //Modificadores  
    /**
     * Define la cantidad de veces que se repetira el bucle.
     * @param iteraciones Cantidad de iteraciones
     */
    public void setIteraciones(int iteraciones){
        this.iteraciones = iteraciones;        
    }
    
    //Consultas
    /**
     * Devuelve la cantidad de veces que se repite el bucle
     * @return
     */
    public int getIteraciones(){          
        return iteraciones;
    }
    
    //Acciones
    @Override
    public String toString(){
        if(iteraciones > 1){
            return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("BUCLE") + ": " + iteraciones + " " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ITERACIONES"); // Bucle: # iteraciones
        }else{
            return java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("BUCLE") + ": " + iteraciones + " " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("ITERACIÓN"); // Bucle: # iteración
        }
    }
}