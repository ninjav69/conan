/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.renderer;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ninjav
 */
public class DateCellRenderer extends DefaultTableCellRenderer {

    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");

    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value instanceof Date) {
            value = f.format(value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
