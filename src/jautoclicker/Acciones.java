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
import java.util.ArrayList;

/**
 * Clase Acciones
 * Maneja una coleccion de objetos de clase <font color=gray><b>Accion</b></font>
 * @author Roger Lovera
 */
public class Acciones implements Serializable{
    private final ArrayList<Accion> listaAcciones;
    
    //Constructor
    protected Acciones() {
        listaAcciones = new ArrayList<>();
    }
    
    //Modificadores
    
    //Consultas
    /**
     * Devuelve la cantidad de objetos de clase <font color=gray><b>Accion</b></font> contenidos en la clase <font color=green><b>Bucle</b></font>.
     * @return 
     */
    protected int getCantidadAcciones(){        
        return listaAcciones.size();
    }
    
    /**
     * Devuelve un objeto específico de la colección <font color=gray><b>Accion</b></font> contenidos en el objeto de clase <font color=green><b>Bucle</b></font>.
     * @param indice Índice del objeto de la colección.
     * @return
     * <ul>
     *  <li>Objeto <font color=gray><b>Accion</b></font> - Si el índice pertenece al rango de la colección</li>
     *  <li>null - Si el índice está fuera del rango.</li>
     * </ul>
     */
    protected Accion getAccion(int indice){
        if(indice < getCantidadAcciones() && indice >= 0){
            return (Accion)listaAcciones.get(indice);
        }
        return null;
    }
    
    /**
     * Devuelve un objeto <b>ArrayList</b> con un colección de objetos <font color=gray><b>Accion</b></font>
     * @return
     */
    protected ArrayList getListaAcciones(){
        return listaAcciones;
    }
    
    //Acciones
    /**
     * Agrega un objeto de clase <font color=gray><b>Accion</b></font> a la lista contenida en la clase <font color=green><b>Bucle</b></font>.
     * @param accion Objeto de clase <font color=gray><b>Accion</b></font>
     */
    protected void addAccion(Accion accion){
        listaAcciones.add(accion);        
    }
}
