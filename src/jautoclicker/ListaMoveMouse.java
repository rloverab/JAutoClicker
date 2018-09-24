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
import java.util.Collections;

/**
 *
 * @author Roger Lovera
 */
public class ListaMoveMouse implements Serializable{
    private final ArrayList<MoveMouse> listaMoveMouse;
    
    public ListaMoveMouse(){
        listaMoveMouse = new ArrayList();
    }
    
    //Modificadores
    
    //Consultas
    public int getIndiceBucleDisponible(){
        int indiceBucle = 0;
        
        for(int i = 0; i < listaMoveMouse.size(); i++){
            if(indiceBucle > listaMoveMouse.get(i).getIndiceBucle()){
                indiceBucle = listaMoveMouse.get(i).getIndice();
                if(this.getPosicionInicialBucle(indiceBucle + 1) == -1){
                    return indiceBucle + 1;
                }
            }
        }
        return indiceBucle + 1;
    }
    
    public int getCantidadBucles(){
        int ultimoIdBucle;
        int contador;
        
        ultimoIdBucle = 0;
        contador = 1;
        
        for(MoveMouse mm : listaMoveMouse){
            if(mm.getIndiceBucle() > ultimoIdBucle){
                ultimoIdBucle = mm.getIndiceBucle();
                contador++;
            }
        }
        
        return contador;
    }
    
    public int getPosicionObjeto(int indice){
        for(int i = 0; i < this.getCantidadObjetos(); i++){
            if(this.getObjeto(i).getIndice() == indice){
                return i;
            }
        }
        return -1;
    }
    
    public int getPosicionInicialBucle(int indiceBucle){
        for (int i = 0; i < listaMoveMouse.size(); i++){
            if(listaMoveMouse.get(i).getIndiceBucle() == indiceBucle){
                return i;
            }
        }
        return -1;
    }
    
    public int getPosicionFinalBucle(int indiceBucle){
        for(int i = listaMoveMouse.size()-1; i >= 0; i--){
            if(listaMoveMouse.get(i).getIndiceBucle() == indiceBucle){
                return i;
            }
        }
        return -1;
    }
    
    public MoveMouse getObjeto(int posicion){
        return listaMoveMouse.get(posicion);
    }
    
    public ArrayList<MoveMouse> getLista(){
        return listaMoveMouse;
    }
    
    public int getCantidadObjetos(){
        return listaMoveMouse.size();
    }
    
    public int hashCodeAlterno(){
        return listaMoveMouse.hashCode();
    }
        
    //Acciones    
    public void agregar(MoveMouse moveMouse){
        listaMoveMouse.add(moveMouse);
    }    
    
    public void ordenar(){
        if(!listaMoveMouse.isEmpty()){            
            Collections.sort(listaMoveMouse);
        }   
    }
    
    public void subir(int indicePrimero, int indiceUltimo){
        MoveMouse aux;        
        if((indicePrimero > 0) && (indicePrimero <= indiceUltimo)){
            for(int i = indicePrimero; i <= indiceUltimo; i++){
                aux = listaMoveMouse.get(i-1);
                listaMoveMouse.set(i-1, listaMoveMouse.get(i));
                listaMoveMouse.set(i, aux);
            }
        }
        actualizarIndices();
    }
    
    public void bajar(int indicePrimero, int indiceUltimo){
        MoveMouse aux;
        if((indiceUltimo < listaMoveMouse.size()-1) && (indicePrimero <= indiceUltimo)){
            for(int i = indiceUltimo; i >= indicePrimero; i--){
                aux = listaMoveMouse.get(i+1);
                listaMoveMouse.set(i+1, listaMoveMouse.get(i));
                listaMoveMouse.set(i, aux);
            }
        }
        actualizarIndices();
    }
    
    public void borrar(int indicePrimero, int indiceUltimo){
        if(indicePrimero <= indiceUltimo){
            for(int i = indiceUltimo; i >= indicePrimero; i--){
                listaMoveMouse.remove(i);
            }
        }
        actualizarIndices();
    }
    
    public void eliminar(int indice){
        if(!listaMoveMouse.isEmpty()){            
            listaMoveMouse.remove(indice);
        }
        actualizarIndices();
    }
    
    private void actualizarIndices(){
        for(int i = 0; i < listaMoveMouse.size(); i++){
            ((MoveMouse)listaMoveMouse.get(i)).setIndice(i+1);
        }
    }
    
    public void vaciar(){
        if(!listaMoveMouse.isEmpty()){
            listaMoveMouse.clear();
        }
    }
}
