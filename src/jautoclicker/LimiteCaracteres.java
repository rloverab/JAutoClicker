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

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Roger Lovera
 */
public class LimiteCaracteres extends PlainDocument{
    public JTextField editor;
    public int maximoCaracteres;

    public LimiteCaracteres(JTextField editor, int maximoCaracteres) {
        this.editor = editor;
        this.maximoCaracteres = maximoCaracteres;
    }
    
    @Override
    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException 
    { 
        if ((editor.getText().length()+arg1.length())>this.maximoCaracteres) 
            return; 
        super.insertString(arg0, arg1, arg2); 
    }
}
