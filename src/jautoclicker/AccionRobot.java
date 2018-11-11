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

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;

/**
 * Clase AccionRobot
 * Contiene métodos que permiten ejecutar las acciones programadas por el usuario.
 * @author Roger Lovera
 */
public class AccionRobot implements Runnable{
    //Atributos
    private Ventana ventana; 
    private boolean detener;
    
    //Constructor    
    public AccionRobot(Ventana ventana) {
        this.ventana = ventana;
        detener = false;
    }
    
    public AccionRobot(){
        this(null);
    }
    
    //Modificadores
    /**
     * Establece la referencia a la interfaz de usuario.
     * @param ventana
     */
    public void setVentana(Ventana ventana){
        this.ventana = ventana;
    }    
            
    //Consultas
    
    //Acciones
    public void detener(){
        detener = true;
    }
    
    private void retardo(int retardo){  
        long fin = System.currentTimeMillis() + (long)retardo;
        while (System.currentTimeMillis() <= fin){
            if(detener){
                break;
            }
        }        
    }
    
    private void moverMouse(int xInicio, int yInicio, int xFinal, int yFinal, int retardo){
        try {
            Robot robot;
            robot = new Robot();            
            
            long inicio = System.currentTimeMillis();
            long fin = inicio + (long)retardo;
            
            double xPasos;
            double yPasos;
            double x;
            double y;
            
            xPasos = ((double)(xFinal - xInicio)/(double)(retardo));
            yPasos = ((double)(yFinal - yInicio)/(double)(retardo));
            
            while (System.currentTimeMillis() < fin){
                if(detener){ //Para interrumpir el hilo
                    break;
                }
                
                x = xInicio + ((double)(System.currentTimeMillis()-inicio))*xPasos;
                y = yInicio +((double)(System.currentTimeMillis()-inicio))*yPasos;
                robot.mouseMove((int)x, (int)y);
            }
        } catch (AWTException ex) {
            Logger.getLogger(AccionRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    private void accionMouse(AccionMouse accionMouse){                
        try {
            Robot robot;
            robot = new Robot();     
            
            robot.mouseMove(accionMouse.getX(), accionMouse.getY());
            
            if(accionMouse.getBoton() != MouseEvent.NOBUTTON){
                switch(accionMouse.getPulsacion()){
                    case AccionMouse.CLICK_SIMPLE:
                        robot.mousePress(InputEvent.getMaskForButton(accionMouse.getBoton()));
                        robot.mouseRelease(InputEvent.getMaskForButton(accionMouse.getBoton()));
                        break;
                    case AccionMouse.CLICK_DOBLE:
                        for (int i = 1; i <= 2; i++){
                            robot.mousePress(InputEvent.getMaskForButton(accionMouse.getBoton()));
                            robot.mouseRelease(InputEvent.getMaskForButton(accionMouse.getBoton()));
                            robot.delay(GlobalScreen.getMultiClickIterval());
                        }
                        break;                    
                    case AccionMouse.CLICK_MANTENER:
                        robot.mousePress(InputEvent.getMaskForButton(accionMouse.getBoton()));
                        break;
                    case AccionMouse.CLICK_SOLTAR:
                        robot.mouseRelease(InputEvent.getMaskForButton(accionMouse.getBoton()));
                }
            }
        } catch (AWTException ex) {
            Logger.getLogger(AccionRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reproducirAcciones(Acciones acciones){        
        for(int i = 0; i < acciones.getCantidadAcciones(); i++){
            if(detener){
                return;
            }else{
                switch(acciones.getAccion(i).getTipoAccion()){
                    case Accion.BUCLE:
                        for(int j = 0; j < acciones.getAccion(i).getBucle().getIteraciones(); j++){
                            reproducirAcciones(acciones.getAccion(i).getBucle());
                        }
                        break;
                    case Accion.CONDICIONAL:
                        if(acciones.getAccion(i).getCondicional().seCumple()){
                            reproducirAcciones(acciones.getAccion(i).getCondicional());
                        }
                        break;
                    case Accion.ACCIONMOUSE:
                        if(ventana.getAnimar()){
                            moverMouse(
                                    MouseInfo.getPointerInfo().getLocation().x, 
                                    MouseInfo.getPointerInfo().getLocation().y, 
                                    acciones.getAccion(i).getAccionMouse().getX(), 
                                    acciones.getAccion(i).getAccionMouse().getY(), 
                                    acciones.getAccion(i).getAccionMouse().getRetardo());
                        }else{
                            retardo(acciones.getAccion(i).getAccionMouse().getRetardo());
                        }
                        if(detener){
                            return;
                        }else{
                            accionMouse(acciones.getAccion(i).getAccionMouse());
                        }
                        break;        
                    case Accion.ACCIONESPECIAL:
                        acciones.getAccion(i).getAccionEspecial().ejecutar();
                        break;
                    default:
                }
            }
        }                    
    }
    
    //Métodos Runnable
    @Override
    public void run() {
    }
    
}
