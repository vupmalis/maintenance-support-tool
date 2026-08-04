package org.mydevnotes.mst.ui;

import java.awt.Component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.mydevnotes.mst.ApplicationContext;
import org.mydevnotes.mst.BusinessEntitySearchResultListener;
import org.mydevnotes.mst.BusinessEntitySelectionListener;
import org.mydevnotes.mst.EventLogger;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.dao.BusinessEntitySearchResult;
import org.mydevnotes.mst.ui.design.AlternateRowRenderer;

/**
 *
 * @author vupma
 */
public class JPanelSearchResultSet extends javax.swing.JPanel implements BusinessEntitySearchResultListener {

    private EventLogger eventLogger;
    private String objectType;
    private BusinessEntitySelectionListener businessEntitySelectionListener = ApplicationContext.getApplicationContext().getApplicationController();
    private boolean rowHaveDetails = false;
    private List<BusinessEntity> searchResult;

    public void setRowHaveDetails(boolean rowHaveDetails) {
        this.rowHaveDetails = rowHaveDetails;
    }

    /**
     * Creates new form JPanelResultSet
     */
    public JPanelSearchResultSet() {
        initComponents();

        this.jTableResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.cleanup();

        this.jTableResults.setDefaultRenderer(
                Object.class,
                new AlternateRowRenderer()
        );

        this.jTableResults.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    int row = this.jTableResults.getSelectedRow();

                    if (row < 0) {
                        return;
                    }

                    if (this.rowHaveDetails) {

                        row = this.jTableResults.convertRowIndexToModel(row);

                        BusinessEntity parentEntity = new BusinessEntity();
                        parentEntity.setAttributes(getSelectedRowAsBusinessEntity(row));
                        parentEntity.loadFieldValuesFromAttributes(this.objectType);                        
                        
                        parentEntity.setId(String.valueOf(this.getValueAt(jTableResults, row, "id")));
                        parentEntity.setType(this.objectType);
                        parentEntity.setName((String) this.getValueAt(jTableResults, row, "name"));


                        this.eventLogger.addLog(String.format("Extracting details for object id=%s", parentEntity.getId()));

                        this.businessEntitySelectionListener.onChildBusinessEntitySelected(null);
                        this.businessEntitySelectionListener.onMainBusinessEntitySelected(parentEntity);
                    }
                });
    }

    public void cleanup() {
        this.jTableResults.setModel(new DefaultTableModel(new String[]{}, 0));
        this.searchResult = null;
    }

    private Map<String, Object> getSelectedRowAsBusinessEntity(int row) {

        Map<String, Object> rowData = new HashMap<>();

        for (int col = 0; col < this.jTableResults.getColumnCount(); col++) {
            String columnName = this.jTableResults.getColumnName(col);
            Object value = this.jTableResults.getValueAt(row, col);
            rowData.put(columnName, value);
        }

        return rowData;
    }

    public void setTableModel(DefaultTableModel model, String objectType) {
        this.jTableResults.setModel(model);
        this.objectType = objectType;

        /*
        if (hasColumn(this.jTableResults, "export_to_timeline_enabled")) {
            this.jTableResults.getColumn("export_to_timeline_enabled").setCellRenderer(new ButtonRenderer());
            this.jTableResults.getColumn("export_to_timeline_enabled").setCellEditor(new ButtonEditor(new JCheckBox()));
        }
        */

    }

    public static boolean hasColumn(JTable table, Object identifier) {
        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            if (Objects.equals(columnModel.getColumn(i).getIdentifier(), identifier)) {
                return true;
            }
        }

        return false;
    }

    private Object getValueAt(JTable table, int row, String columnName) {

        int viewIndex = table.getColumnModel()
                .getColumnIndex(columnName);

        int modelIndex = table.convertColumnIndexToModel(viewIndex);

        return table.getModel().getValueAt(row, modelIndex);
    }

    public void setBusinessEntity(BusinessEntity businessEntity) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Attribute", "Value"}, 0);

        if (businessEntity.getAttributes() != null) {
            businessEntity.getAttributes().forEach((key, value) -> {
                Object[] row = new Object[]{key, value};
                model.addRow(row);
            });
        }

        this.setTableModel(model, businessEntity.getType());

    }

    public void setBusinessEntityDetail(BusinessEntity businessEntity, String detailName) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Property", "Value"}, 0);

        if (businessEntity.getDetails() != null && businessEntity.getDetails().get(detailName) != null) {
            businessEntity.getDetails().get(detailName).forEach((key, value) -> {
                Object[] row = new Object[]{key, value};
                model.addRow(row);
            });
        }

        this.setTableModel(model, businessEntity.getType());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResults = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jTableResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableResults);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableResults;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onBusinessEntitySearchResultReady(BusinessEntitySearchResult searchResult) {
        this.setTableModel(buildBusinessEntityTableModel(searchResult), searchResult.getEntityType());
        this.searchResult = searchResult.getSearchResult();
    }

    public static DefaultTableModel buildBusinessEntityTableModel(
            BusinessEntitySearchResult searchResult
    ) {
        String[] columns = Stream.concat(
                Arrays.stream(searchResult.getEntityAttributes()),
                Arrays.stream(new String[]{"entityObject"})
        ).toArray(String[]::new);
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        searchResult.getSearchResult().forEach(businessEntity -> {
            Object[] row = Arrays.stream(searchResult.getEntityAttributes()).map(key -> businessEntity.getAttributes().get(key)).toArray();
            model.addRow(row);
        });

        return model;
    }

    void setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    private static class ButtonEditor extends DefaultCellEditor {

        private final JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value,
                boolean isSelected, int row, int column) {

            this.row = row;
            label = value == null ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                JOptionPane.showMessageDialog(
                        button,
                        "Button clicked on row " + row
                );

            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

    }

}
