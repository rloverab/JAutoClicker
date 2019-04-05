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
import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jnativehook.GlobalScreen;

/**
 *
 * @author Roger Lovera
 */
public class RobotMouse extends Thread{  
    //Atributos
    private Robot robot;
    private DefaultMutableTreeNode listaAcciones;
    private VentanaPrincipal ventana;
    private boolean animar;
    private boolean detener;
    
    //Constructores    
    public RobotMouse(){     
        ventana = null;
        animar = false;
        detener = false;
    }
    
    public RobotMouse(DefaultMutableTreeNode listaAcciones, boolean animar, VentanaPrincipal ventana){
        try {
            robot = new Robot();
            this.listaAcciones = listaAcciones;
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
     * @param ListaAcciones Objeto de tipo DefaultMutableTreeNode
     */    
    public void setListaAcciones(DefaultMutableTreeNode ListaAcciones){
        this.listaAcciones = ListaAcciones;        
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
    public void setVentana(VentanaPrincipal ventana){        
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
    private void accionMouse(AccionMouse accionMouse){                
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
                        robot.delay(GlobalScreen.getMultiClickIterval() - (GlobalScreen.getMultiClickIterval()/10));
                    }
                    break;
                case AccionMouse.CLICK_MANTENER:
                    robot.mousePress(InputEvent.getMaskForButton(accionMouse.getBoton()));                    
                    break;
                case AccionMouse.CLICK_SOLTAR:                    
                    robot.mouseRelease(InputEvent.getMaskForButton(accionMouse.getBoton()));                    
            }
        }
    } 
    
    private void retardo(int retardo){  
        long fin = System.currentTimeMillis() + (long)retardo;
        while (System.currentTimeMillis() <= fin){
            System.out.print(""); /*Necesario para que funcione correctamente el método "retardo".
                                Si no le doy una instrucción que hacer, por algun motivo no se detiene al
                                cumplirse la condición que desencadena la ejecución del método break.*/
            if(detener){ //Para interrumpir el hilo                          
                break;
            }                    
        }        
    }
    
    private void moverMouse(int xInicio, int yInicio, int xFinal, int yFinal, int retardo){
        long inicio = System.currentTimeMillis();
        long fin = inicio + (long)retardo;
        
        double xPasos;
        double yPasos;
        double x;
        double y;
                
        xPasos = ((double)(xFinal - xInicio)/(double)(retardo));
        yPasos = ((double)(yFinal - yInicio)/(double)(retardo));            

        while (System.currentTimeMillis() <= fin){
            if(!detener){
                x = xInicio + ((double)(System.currentTimeMillis()-inicio))*xPasos;
                y = yInicio +((double)(System.currentTimeMillis()-inicio))*yPasos;
                robot.mouseMove((int)x, (int)y);
            }else{ //Para interrumpir el hilo          
                break;
            }   
        }
    }   
    
    private void reproducirAccion(
            DefaultMutableTreeNode nodo,
            boolean animar){  
        switch(((Accion)nodo.getUserObject()).getTipoAccion()){
            case Accion.BUCLE:
                for(int i = 0; i < ((Accion)nodo.getUserObject()).getBucle().getIteraciones(); i++){
                    if(!detener){
                        for(int j = 0; j < nodo.getChildCount(); j++){
                            if(!detener){
                                reproducirAccion((DefaultMutableTreeNode)nodo.getChildAt(j),animar);
                            }else{
                                return;
                            }
                        }
                    }else{
                        return;
                    }
                }
                break;
            case Accion.CONDICIONAL:
                if(((Accion)nodo.getUserObject()).getCondicional().seCumple() && !detener){
                    for(int i = 0; i < ((DefaultMutableTreeNode)nodo.getChildAt(0)).getChildCount(); i++){
                        if(!detener){
                            reproducirAccion((DefaultMutableTreeNode)((DefaultMutableTreeNode)nodo.getChildAt(0)).getChildAt(i),animar);
                        }else{
                            //break;
                            return;
                        }
                    }    
                }else{
                    for(int i = 0; i < ((DefaultMutableTreeNode)nodo.getChildAt(1)).getChildCount(); i++){
                        if(!detener){
                            reproducirAccion((DefaultMutableTreeNode)((DefaultMutableTreeNode)nodo.getChildAt(1)).getChildAt(i),animar);
                        }else{
                            //break;
                            return;
                        }
                    }
                }
                break;
            case Accion.BUCLE_CONDICIONADO:
                switch(((Accion)nodo.getUserObject()).getBucleCondicionado().getTipoEvaluacion()){
                    case BucleCondicionado.EVALUAR_ANTES:
                        while(((Accion)nodo.getUserObject()).getBucleCondicionado().seCumple() && !detener){
                            for(int i = 0; i < nodo.getChildCount(); i++){
                                if(!detener){
                                    reproducirAccion((DefaultMutableTreeNode)nodo.getChildAt(i),animar);
                                }else{
                                    //break;
                                    return;
                                }
                            }
                        }
                        break;
                    case BucleCondicionado.EVALUAR_DESPUES:
                        do{
                            for(int i = 0; i < nodo.getChildCount(); i++){
                                if(!detener){
                                    reproducirAccion((DefaultMutableTreeNode)nodo.getChildAt(i),animar);
                                }else{
                                    return;
                                }
                            }
                        }while(((Accion)nodo.getUserObject()).getBucleCondicionado().seCumple() && !detener);
                }                
                break;
            case Accion.ACCIONMOUSE:
                ventana.seleccionarNodo(nodo);
                if(animar){ //Animar las transiciones
                    moverMouse(
                            MouseInfo.getPointerInfo().getLocation().x, 
                            MouseInfo.getPointerInfo().getLocation().y, 
                            ((Accion)nodo.getUserObject()).getAccionMouse().getX(),
                            ((Accion)nodo.getUserObject()).getAccionMouse().getY(),
                            ((Accion)nodo.getUserObject()).getAccionMouse().getRetardo());
                }else{ // Sin animaciones
                    retardo(((Accion)nodo.getUserObject()).getAccionMouse().getRetardo());
                }
                if(!detener){ //Para interrumpir el hilo
                    accionMouse(((Accion)nodo.getUserObject()).getAccionMouse());
                }
                break;
            case Accion.ACCIONESPECIAL:
                ventana.seleccionarNodo(nodo);
                if(!detener){
                    ((Accion)nodo.getUserObject()).getAccionEspecial().ejecutar();
                    switch(((Accion)nodo.getUserObject()).getAccionEspecial().getAccionEspecial()){
                        case AccionEspecial.COPIAR:
                        case AccionEspecial.CORTAR:
                        case AccionEspecial.INTRODUCIR_AL_PORTAPAPELES:
                        case AccionEspecial.LIMPIAR_PORTAPAPELES:   
                        case AccionEspecial.SELECCIONAR_PESTANA_DERECHA:
                        case AccionEspecial.SELECCIONAR_PESTANA_IZQUIERDA:
                            if(!detener){                            
                                this.retardo(1000); 
                            }                            
                    }
                }
                               
                
        }        
    }
    
    /**
     * Detiene la ejecución de reproduccion de acciones programadas del cursor.
     */
    public void detener(){
        detener = true;
    }
    
    @Override
    public void run() {    
        ventana.actualizarControles(true);
        if(ventana.getVentanaMinimizada()){
            ventana.setExtendedState(JFrame.ICONIFIED);
        }
        reproducirAccion(listaAcciones, animar);
        this.detener();

        ventana.setExtendedState(JFrame.NORMAL);
        ventana.actualizarControles(false);
        Thread.currentThread().interrupt(); 
    }
}
