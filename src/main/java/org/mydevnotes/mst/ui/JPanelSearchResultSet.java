package org.mydevnotes.mst.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
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
                        parentEntity.setId(String.valueOf(this.getValueAt(jTableResults, row, "id")));
                        parentEntity.setType(this.objectType);
                        parentEntity.setName((String) this.getValueAt(jTableResults, row, "name"));
                        parentEntity.setAttributes(getSelectedRowAsBusinessEntity(row));

                        this.eventLogger.addLog(String.format("Extracting details for object id=%s", parentEntity.getId()));

                        this.businessEntitySelectionListener.onChildBusinessEntitySelected(null);
                        this.businessEntitySelectionListener.onMainBusinessEntitySelected(parentEntity);

                        //this.populateDetails(parentEntity);
                    }
                });
    }

    public void cleanup() {
        this.jTableResults.setModel(new DefaultTableModel(new String[]{}, 0));
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
    }

    public static DefaultTableModel buildBusinessEntityTableModel(
            BusinessEntitySearchResult searchResult
    ) {
        DefaultTableModel model = new DefaultTableModel(searchResult.getEntityAttributes(), 0);
        searchResult.getSearchResult().forEach(businessEntity -> {
            Object[] row = Arrays.stream(searchResult.getEntityAttributes()).map(key -> businessEntity.getAttributes().get(key)).toArray();
            model.addRow(row);
        });

        return model;
    }

    void setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

}
