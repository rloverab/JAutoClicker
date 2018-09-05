/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jautoclicker;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
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
