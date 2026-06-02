package org.mydevnotes.mst.ui.tree;

import java.awt.Component;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author vupma
 */
public class BusinessEntityTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final Map<String, Icon> icons = new TreeMap();

    private static Icon getBusinessEntityIcon(String name) {

        if (icons.containsKey(name)) {
            return icons.get(name);
        } else {
            Icon icon = new ImageIcon(BusinessEntityTreeCellRenderer.class.getResource("/icons/" + name));
            icons.put(name, icon);
            return icon;
        }
    }

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
                
                setIcon(BusinessEntityTreeCellRenderer.getBusinessEntityIcon(businessEntityNode.getIconName()));

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
