package org.mydevnotes.mst.ui.design;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author vupma
 */
public class AlternateRowRenderer
        extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {

        Component c =
            super.getTableCellRendererComponent(
                table,
                value,
                isSelected,
                hasFocus,
                row,
                column
            );

        // Preserve selection colors
        if (isSelected) {
            return c;
        }

        // Alternate row colors
        if (row % 2 == 0) {

            c.setBackground(Color.WHITE);

        } else {

            c.setBackground(
                new Color(200, 245, 174)
            );
        }

        return c;
    }
}
