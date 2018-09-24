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

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Aun son implementar
 * @author Roger Lovera
 */
public class ColorFilas extends DefaultTableCellRenderer{
    private final int columna;
    
    public ColorFilas(int columna){
        this.columna = columna;
    }    
    
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
        //Valores por defecto
        this.setBackground(Color.white);
        this.setForeground(Color.black);
        
        int valor;
        
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        
        valor = (int) table.getValueAt(row, columna);
        while (valor > 9){
            valor = valor % 10;
        }
        switch(valor){
            case 0:
            case 5:
                this.setBackground(Color.white);
                this.setForeground(Color.black);
                break;
            case 1:
            case 6:
                this.setBackground(Color.yellow);
                this.setForeground(Color.black);
                break;
            case 2:
            case 7:
                this.setBackground(Color.blue);
                this.setForeground(Color.white);
                break;
            case 3:
            case 8:
                this.setBackground(Color.green);
                this.setForeground(Color.black);
                break;
            case 4:
            case 9:
                this.setBackground(Color.gray);
                this.setForeground(Color.black);
                break;
            default:
                this.setBackground(Color.white);
                this.setForeground(Color.black);
        }
        return this;
    }
}
