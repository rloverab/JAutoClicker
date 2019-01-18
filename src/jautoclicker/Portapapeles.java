/*
 * Copyright (C) 2019 Roger Lovera
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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roger Lovera
 */
public final class Portapapeles {
    public static void setContenido (String cadena){
        StringSelection ss;
        Clipboard pp = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        ss = new StringSelection(cadena);
        
        pp.setContents(ss, ss);
    }
    
    public static String getContenido(){
        String resultado;
        Clipboard pp = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t;
        
        resultado = "";   
        
        t = pp.getContents(null);
        
        if((t != null) && t.isDataFlavorSupported(DataFlavor.stringFlavor)){            
            try{
                resultado = t.getTransferData(DataFlavor.stringFlavor).toString();
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(Condicional.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        return resultado;
    }
    
    public static void limpiarPortapapeles(){  
        setContenido("");
        /*
        Clipboard pp = Toolkit.getDefaultToolkit().getSystemClipboard();
        pp.setContents(new Transferable(){
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[0];
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return false;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                throw new UnsupportedFlavorException(flavor);
            }                    
        }, null);*/
    }
}
