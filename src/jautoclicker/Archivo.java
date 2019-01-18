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
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Roger Lovera
 */
public class Archivo implements Serializable{
    private DefaultTreeModel dtm;
    private boolean animar;
    private boolean minimizar;

    public Archivo() {
        this(null);
    }

    public Archivo(DefaultTreeModel dtm) {
        this.dtm = dtm;
    }

    
    
    public void setListaAcciones(DefaultTreeModel dtm) {
        this.dtm = dtm;
    }
    
    public void setAnimar(boolean animar){
        this.animar = animar;
    }
    
    public void setMinimizar(boolean minimizar){
        this. minimizar = minimizar;
    }

    public DefaultTreeModel getListaAcciones() {
        return dtm;
    }
    
    public boolean getAnimar(){
        return animar;
    }
    
    public boolean getMinimizar(){
        return minimizar;
    }
}
