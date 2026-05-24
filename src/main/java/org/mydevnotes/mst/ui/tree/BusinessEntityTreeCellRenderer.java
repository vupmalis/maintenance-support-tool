package org.mydevnotes.mst.ui.tree;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author vupma
 */
public class BusinessEntityTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode node) {
            Object obj = node.getUserObject();

            if (obj instanceof BusinessEntityNode businessEntityNode) {
                setText(businessEntityNode.getDisplayName());

                // optional: icon example
                // setIcon(user.isActive() ? ACTIVE_ICON : INACTIVE_ICON);

            } else if (obj != null) {
                setText(obj.toString());
            } else {
                setText("");
            }
        }

        return this;
    }
}