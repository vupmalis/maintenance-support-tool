package org.mydevnotes.mst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.mydevnotes.mst.config.SearchOption;

/**
 *
 * @author vupma
 */
public class DBQueryExecutor {

    public static DefaultTableModel execute(SearchOption searchOption, Connection connection, Map<String, String> params) throws SQLException, Exception {

        PreparedStatement ps = connection.prepareStatement(searchOption.getRequest());

        // Set parameters
        for (int i = 0; i < searchOption.getParameters().size(); i++) {

            var parameter = searchOption.getParameters().get(i);

            switch (parameter.getType()) {
                case "String": {
                    ps.setString(i + 1, params.get(parameter.getName()));
                    break;
                }
                case "int": {
                    ps.setLong(i + 1, Long.parseLong(params.get(parameter.getName())));
                    break;
                }
            }
        }

        System.out.println("Get result");

        // Execute query
        ResultSet rs = ps.executeQuery();

        System.out.println("Process result");
        
        /*
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        // Read result
        while (rs.next()) {

            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i);
                Object value = rs.getObject(i);
                System.out.println(columnName + " = " + value);
            }

            System.out.println("-----");
        }
        */
        
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
        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

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

}
