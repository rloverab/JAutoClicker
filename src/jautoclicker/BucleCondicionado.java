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

/**
 *
 * @author Roger Lovera
 */
public class BucleCondicionado extends Condicional implements Serializable{
    private int tipoEvaluacion;
    public final static int EVALUAR_ANTES = 0;
    public final static int EVALUAR_DESPUES = 1;

    public BucleCondicionado(){
        this(EVALUAR_ANTES);
    }
    
    public BucleCondicionado(int tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public void setTipoEvaluacion(int tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public int getTipoEvaluacion() {
        return tipoEvaluacion;
    }
    
    @Override
    public String toString(){
        String mensaje;
        mensaje = java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("BUCLE CONDICIONADO") + ": "; // Bucle condicionado
        
        switch(tipoEvaluacion){
            case EVALUAR_ANTES:
                mensaje += java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("EVALUAR -> EJECUTAR"); // Evaluar -> Ejecutar
                break;
            case EVALUAR_DESPUES:
                mensaje += java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("EJECUTAR -> EVALUAR"); // Ejecutar -> Evaluar
        }
        
        switch(this.getTipoDato()){
            case TEXTO:
                switch(this.getTipoComparacion()){
                    case TEXTO_IGUAL:
                        mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("IGUAL"); // Igual
                        break;
                    case TEXTO_DIFERENTE:
                        mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DIFERENTE"); //Diferente
                }
                if(this.isTextoExacto()){
                    mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("TEXTO EXACTO"); //Texto exacto
                }else{
                    mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("TEXTO PARCIAL"); // Texto parcial
                }
                if(this.isCaseSensitive()){
                    mensaje += " | " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("DISTINGUE MAYÚSCILAS Y MINÚSCULAS"); // Distingue mayúsc. y minúsc.
                }
                
                mensaje += " | \"" + this.getPatronMinimo() + "\"";
                break;
            case NUMERO:
                switch(this.getTipoComparacion()){
                    case IGUAL:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " = " + this.getPatronMinimo();
                        break;
                    case NO_IGUAL:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " <> " + this.getPatronMinimo();
                        break;
                    case MAYOR:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " > " + this.getPatronMinimo();
                        break;
                    case MAYOR_IGUAL:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " >=  " + this.getPatronMinimo();
                        break;
                    case MENOR:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " < " + this.getPatronMinimo();
                        break;
                    case MENOR_IGUAL:                            
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO") + " <= " + this.getPatronMinimo();
                        break;
                    case DENTRO_RANGO:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO DENTRO DEL RANGO") + "[" + this.getPatronMinimo() + ", " + this.getPatronMaximo() + "]" ; // Número dentro del rango
                        break;
                    case FUERA_RANGO:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO FUERA DEL RANGO") + "[" + this.getPatronMinimo() + ", " + this.getPatronMaximo() + "]" ; // Número fuera del rango
                        break;
                    case ES_PAR:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO PAR"); // Número Par
                        break;
                    case ES_IMPAR:
                        mensaje += " - " + java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("NÚMERO IMPAR"); // Número Impar
                }
                break;
            default:
                mensaje = java.util.ResourceBundle.getBundle("jautoclicker/Bundle").getString("BUCLE CONDICIONADO NO DEFINIDO"); // Bucle condicionado no definido
        }
        return mensaje;
    }
}