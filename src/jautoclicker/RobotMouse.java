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
import java.sql.Time;
import java.util.Date;
import javax.swing.JFrame;
import org.jnativehook.GlobalScreen;

/**
 *
 * @author Roger Lovera
 */
public class RobotMouse implements Runnable{  
    //Atributos
    private Robot robot;
    private ListaMoveMouse listaMoveMouse;
    private Ventana ventana;
    private boolean animar;
    private boolean detener;
    
    //Constructores    
    public RobotMouse(){        
        listaMoveMouse = null;
        ventana = null;
        animar = false;
        detener = false;
    }
    
    public RobotMouse(ListaMoveMouse listaMoveMouse, boolean animar, Ventana ventana){
        try {
            robot = new Robot();
            this.listaMoveMouse = listaMoveMouse;
            this.animar = animar;
            this.ventana = ventana;
            detener = false;
        } catch (AWTException ex) {
            System.out.println(ex);
        }
    }
    
    //Modificadores

    /**
     * Define una referencia a la lista de acciones programadas del cursor que 
     * se va a reproducir
     * @param listaMoveMouse Objeto ListaMoveMouse que contiene las acciones 
     * programadas del cursor
     */
    public void setListaMoveMouse(ListaMoveMouse listaMoveMouse){
        this.listaMoveMouse = listaMoveMouse;
    }
    
    /**
     * Define si se va a animar las transiciones entre coordenadas
     * @param animar Se para por parámetro uno de los siguientes valores:<br><br>
     * <font color=blue><b> true</b></font> para activar la animacion <br>
     * <font color=red><b>  false</b></font> para desactivar la animación
     */
    public void setAnimar(boolean animar){
        this.animar = animar;
    }
    
    /**
     * Define una referencia al objeto ventana. Requerido para que algunos 
     * métodos de la clase RobotMouse pueden manipular ciertos componentes del
     * objeto Ventana.
     * @param ventana Objeto de tipo Ventana
     */
    public void setVentana(Ventana ventana){        
        this.ventana = ventana;
    }
    //Consultas    

    /**
     * Consulta si un proceso de reproducción de acciones programadas se 
     * encuentra en ejecución o no.
     * @return <font color=blue><b>true</b></font> si se encuentra detenido <br>
     * <font color=red><b>false</b></font> si se está ejecutando
     */
    public boolean estaDetenido(){            
        return detener;
    }

    
    //Acciones
    private void accionMouse(MoveMouse moveMouse){                
        robot.mouseMove(moveMouse.getX(), moveMouse.getY());     
            
        if(moveMouse.getBoton() != MouseEvent.NOBUTTON){
            switch(moveMouse.getPulsacion()){
                case MoveMouse.CLICK_SIMPLE:
                    robot.mousePress(InputEvent.getMaskForButton(moveMouse.getBoton()));
                    robot.mouseRelease(InputEvent.getMaskForButton(moveMouse.getBoton()));
                    break;
                case MoveMouse.CLICK_DOBLE:
                    for (int i = 1; i <= 2; i++){
                        robot.mousePress(InputEvent.getMaskForButton(moveMouse.getBoton()));
                        robot.mouseRelease(InputEvent.getMaskForButton(moveMouse.getBoton()));
                        robot.delay(GlobalScreen.getMultiClickIterval());
                    }
                    break;
                case MoveMouse.CLICK_MANTENER:
                    robot.mousePress(InputEvent.getMaskForButton(moveMouse.getBoton()));                    
                    break;
                case MoveMouse.CLICK_SOLTAR:                    
                    robot.mouseRelease(InputEvent.getMaskForButton(moveMouse.getBoton()));                    
            }
        }
    }
    
    private void retardo(int retardo){  
        for(int i = 0; i < retardo; i++){
            if(detener){
                break;
            }
            robot.delay(1);                            
        }
    }
    
    private void moverMouse(int xInicio, int yInicio, int xFinal, int yFinal, int retardo){
        double xPasos;
        double yPasos;
        double x = (double)xInicio;
        double y = (double)yInicio;
        int divisor = (int)((this.distanciaRecorrido(xInicio, yInicio, xFinal, yFinal)/retardo) + 2);
                
        xPasos = ((double)(xFinal - xInicio)/(double)(retardo));
        yPasos = ((double)(yFinal - yInicio)/(double)(retardo));            
        
        for(int i = 0; i < retardo; i++){
            if(detener){ //Para interrumpir el hilo          
                break;
            }            
            
            if(i % 2 == 0){
                robot.mouseMove((int)x, (int)y);
                robot.delay(1);
            }
                
            x += xPasos;
            y += yPasos;            
        }
        System.out.println(
                new Date() + "\n" +
                "Diferencia X: " + (xFinal - xInicio) + "\n" +
                "Diferencia Y: " + (yFinal - yInicio) + "\n" +
                "Hipotenusa: " + this.distanciaRecorrido(xInicio, yInicio, xFinal, yFinal) + "\n" +
                "Divisor: " + divisor);
    }   
    
    private void reproducirAccion(
            ListaMoveMouse listaMoveMouse,
            int indiceBucle,
            boolean animar){
        int posicionInicial;
        int posicionFinal;
        int iteraciones;
        
        if(listaMoveMouse.getCantidadObjetos() > 0){
            ventana.actualizarControles(true);
            
            posicionInicial = listaMoveMouse.getPosicionInicialBucle(indiceBucle);
            posicionFinal = listaMoveMouse.getPosicionFinalBucle(indiceBucle);
            iteraciones = listaMoveMouse.getObjeto(posicionInicial).getIteraciones();
            
            principal:
            for(int i = 0; i < iteraciones; i++){
                if(detener){ //Para interrumpir el hilo                    
                    break;                    
                }
                
                for(int j = posicionInicial; j <= posicionFinal; j++){
                    if(detener){ //Para interrumpir el hilo
                        break principal;
                    }
                    
                    if(indiceBucle != listaMoveMouse.getObjeto(j).getIndiceBucle()){                        
                        reproducirAccion( //Llamado recursivo
                                listaMoveMouse,
                                listaMoveMouse.getObjeto(j).getIndice(),
                                animar);
                        j = listaMoveMouse.getPosicionObjeto(j);
                    }else{
                        if(animar){ //Animar las transiciones
                            moverMouse(
                                    MouseInfo.getPointerInfo().getLocation().x, 
                                    MouseInfo.getPointerInfo().getLocation().y, 
                                    listaMoveMouse.getObjeto(j).getX(), 
                                    listaMoveMouse.getObjeto(j).getY(), 
                                    listaMoveMouse.getObjeto(j).getRetardo());
                        }else{ // Sin animaciones
                            retardo(listaMoveMouse.getObjeto(j).getRetardo());
                        }
                        if(!detener){ //Para interrumpir el hilo
                            accionMouse(listaMoveMouse.getObjeto(j));
                        }
                    }
                    ventana.setFilaSeleccionada(j);
                }            
            }            
            ventana.actualizarControles(false);
        }
    }
    
    private double distanciaRecorrido(int xInicio, int yInicio, int xFinal, int yFinal){
        return Math.sqrt(Math.pow((double)(xFinal - xInicio), 2) + Math.pow((double)(yFinal - yInicio), 2));        
    }

    /**
     * Detiene la ejecución de reproduccion de acciones programadas del cursor.
     */
    public void detener(){
        detener = true;
    }

    
    @Override
    public void run() {        
        if(ventana.getVentanaMinimizada()){
            ventana.setExtendedState(JFrame.ICONIFIED);        
        }
        
        reproducirAccion(listaMoveMouse, 0, animar);           
        this.detener();
        ventana.setExtendedState(JFrame.NORMAL);        
        
        Thread.currentThread().interrupt();               
    }
}
