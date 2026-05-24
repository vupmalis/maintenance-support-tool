package org.mydevnotes.mst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.mydevnotes.mst.config.SearchDetail;
import org.mydevnotes.mst.config.SearchOption;

/**
 *
 * @author vupma
 */
public class DBQueryExecutor {

    // TODO refactor to return BusinessEntity 
    public static DefaultTableModel execute(SearchOption searchOption, Connection connection, Map<String, String> params) throws SQLException, Exception {

        PreparedStatement ps = connection.prepareStatement(searchOption.getRequest());

        // Set parameters
        for (int i = 0; i < searchOption.getSearchParameters().size(); i++) {

            var parameter = searchOption.getSearchParameters().get(i);

            switch (parameter.getType()) {
                case "String" ->  {
                    ps.setString(i + 1, params.get(parameter.getName()));
                }
                case "long" ->  {
                    ps.setLong(i + 1, Long.parseLong(params.get(parameter.getName())));
                }
            }
        }

        System.out.println("Get result");

        // Execute query
        ResultSet rs = ps.executeQuery();

        System.out.println("Process result");

        return buildTableModel(rs);
    }

    public static DefaultTableModel buildTableModel(
            ResultSet rs
    ) throws Exception {

        ResultSetMetaData meta = rs.getMetaData();

        // Column names
        int columnCount = meta.getColumnCount();

        String[] columns = new String[columnCount];

        for (int i = 1; i <= columnCount; i++) {
            columns[i - 1] = meta.getColumnLabel(i);
        }

        // Table model
        DefaultTableModel model
                = new DefaultTableModel(columns, 0);

        // Rows
        while (rs.next()) {

            Object[] row = new Object[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }

            model.addRow(row);
        }

        return model;
    }

    public static List<BusinessEntity> execute(SearchDetail detailsObjectConfig, Connection connection, Object parentEntityId) throws SQLException {

        List<BusinessEntity> result = new ArrayList<>();

        System.out.println(detailsObjectConfig.getRequest());

        PreparedStatement ps = connection.prepareStatement(detailsObjectConfig.getRequest());
        System.out.println("Parent Id = " + parentEntityId);
        ps.setLong(1, (long) parentEntityId);
        ResultSet rs = ps.executeQuery();

        ResultSetMetaData meta = rs.getMetaData();

        String[] columns = new String[meta.getColumnCount()];

        for (int i = 1; i <= columns.length; i++) {
            columns[i - 1] = meta.getColumnLabel(i);
        }

        while (rs.next()) {

            BusinessEntity businessEntity = new BusinessEntity();
            Map<String, Object> businessEntityAttributes = new HashMap<>();

            for (int i = 1; i <= columns.length; i++) {
                businessEntityAttributes.put(columns[i - 1], rs.getObject(i));
            }

            businessEntity.setId(businessEntityAttributes.containsKey("id") ? (Long) businessEntityAttributes.get("id") : null);
            businessEntity.setType(detailsObjectConfig.getBusinessEntityType());
            businessEntity.setName(businessEntityAttributes.containsKey("name") ? (String) businessEntityAttributes.get("name") : "untitled");
            businessEntity.setAttributes(businessEntityAttributes);

            result.add(businessEntity);
        }

        return result;
    }

}
