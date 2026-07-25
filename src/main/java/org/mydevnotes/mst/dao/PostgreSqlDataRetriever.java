package org.mydevnotes.mst.dao;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mydevnotes.mst.ApplicationContext;
import org.mydevnotes.mst.config.ChildEntity;
import org.mydevnotes.mst.config.Detail;
import org.mydevnotes.mst.config.SearchOption;
import org.mydevnotes.mst.datasource.DataRetriever;
import org.mydevnotes.mst.datasource.SearchParameters;
import org.mydevnotes.mst.ui.JPanelSearchOption;

/**
 *
 * @author vupma
 */
public class PostgreSqlDataRetriever implements DataRetriever {

    private final HikariDataSource dataSource;

    public PostgreSqlDataRetriever(String dataSourceName, HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<BusinessEntity> getChildEntities(String parentId, ChildEntity childEntityConfig) {

        List<BusinessEntity> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();) {

            System.out.println(childEntityConfig.getRequest());

            PreparedStatement ps = connection.prepareStatement(childEntityConfig.getRequest());
            System.out.println("Parent Id = " + parentId);
            if ("long".equals(childEntityConfig.getParentReferenceType())) {
                ps.setLong(1, Long.parseLong(parentId));
            } else {
                ps.setString(1, parentId);
            }
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();

            String[] columns = new String[meta.getColumnCount()];

            for (int i = 1; i <= columns.length; i++) {
                columns[i - 1] = meta.getColumnLabel(i);
            }

            while (rs.next()) {
                var businessEntity = getBusinessEntityFromResultSet(childEntityConfig.getBusinessEntityType(), rs, columns);
                result.add(businessEntity);
            }

        } catch (Exception ex) {
            ApplicationContext.getApplicationContext().getEventLogger().addLog("Error during details query execution " + ex.getMessage());
            Logger.getLogger(JPanelSearchOption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static Map<String, Object> executeRequest(Connection connection, String entityId, String entityIdType, String request) throws SQLException {

        Map<String, Object> result = new HashMap<>();

        System.out.println(request);

        PreparedStatement ps = connection.prepareStatement(request);
        System.out.println("Entity Id = " + entityId);

        if ("long".equals(entityIdType)) {
            ps.setLong(1, Long.parseLong(entityId));
        } else {
            ps.setString(1, entityId);
        }
        ResultSet rs = ps.executeQuery();

        ResultSetMetaData meta = rs.getMetaData();

        String[] columns = new String[meta.getColumnCount()];

        for (int i = 1; i <= columns.length; i++) {
            columns[i - 1] = meta.getColumnLabel(i);
        }

        while (rs.next()) {
            for (int i = 1; i <= columns.length; i++) {
                result.put(columns[i - 1], rs.getObject(i));
            }
        }

        return result;
    }

    @Override
    public Map<String, Object> getBusinessEntityDetails(String entityId, Detail detailsConfig) {

        try (Connection connection = dataSource.getConnection();) {

            return executeRequest(connection, entityId, detailsConfig.getReferenceType(), detailsConfig.getRequest());

        } catch (Exception ex) {
            ApplicationContext.getApplicationContext().getEventLogger().addLog("Error during details query execution " + ex.getMessage());
            Logger.getLogger(JPanelSearchOption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new HashMap<>();

    }

    private static BusinessEntity getBusinessEntityFromResultSet(String businessEntityType, ResultSet rs, String[] columns) throws SQLException {

        BusinessEntity businessEntity = new BusinessEntity();
        Map<String, Object> businessEntityAttributes = new HashMap<>();

        for (int i = 1; i <= columns.length; i++) {
            businessEntityAttributes.put(columns[i - 1], rs.getObject(i));
        }

        businessEntity.setId(businessEntityAttributes.containsKey("id") ? String.valueOf(businessEntityAttributes.get("id")) : null);
        businessEntity.setType(businessEntityAttributes.containsKey("entity_type") ? String.valueOf(businessEntityAttributes.get("entity_type")) : businessEntityType);
        businessEntity.setName(businessEntityAttributes.containsKey("name") ? String.valueOf(businessEntityAttributes.get("name")) : "untitled");
        businessEntity.setIconName(businessEntityAttributes.containsKey("h_icon") ? String.valueOf(businessEntityAttributes.get("h_icon")) : "");
        businessEntity.setExportToTimeLineEnabled(businessEntityAttributes.containsKey("export_to_timeline_enabled") ? "Y".equals(String.valueOf(businessEntityAttributes.get("export_to_timeline_enabled"))) : false);
        businessEntity.setAttributes(businessEntityAttributes);

        return businessEntity;
    }

    public static BusinessEntitySearchResult executeRequest(String businessEntityType, Connection connection, String request, SearchParameters parameters) throws SQLException {

        List<BusinessEntity> businessEntities = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(request);

        int paramIndex = 1;

        for (var entry : parameters.getValues().entrySet()) {

            System.out.println("Set values " + entry.getKey() + "=" + entry.getValue());

            try {
                switch (entry.getValue()) {
                    case Long l ->
                        ps.setLong(paramIndex, l);
                    case String s ->
                        ps.setString(paramIndex, s);
                    default ->
                        ps.setString(paramIndex, String.valueOf(entry.getValue()));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            paramIndex++;
        }

        ResultSet rs = ps.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();

        String[] columns = new String[meta.getColumnCount()];

        for (int i = 1; i <= columns.length; i++) {
            columns[i - 1] = meta.getColumnLabel(i);
        }

        while (rs.next()) {
            businessEntities.add(getBusinessEntityFromResultSet(businessEntityType, rs, columns));
        }

        return new BusinessEntitySearchResult(columns, businessEntities, businessEntityType);
    }

    @Override
    public BusinessEntitySearchResult searchBusinessEntities(SearchOption searchOption, SearchParameters parameters) {

        try (Connection connection = dataSource.getConnection();) {

            return executeRequest(searchOption.getBusinessEntityType(), connection, searchOption.getRequest(), parameters);

        } catch (Exception ex) {
            ApplicationContext.getApplicationContext().getEventLogger().addLog("Error during details query execution " + ex.getMessage());
            Logger.getLogger(JPanelSearchOption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new BusinessEntitySearchResult(new String[0], new ArrayList<>(), searchOption.getBusinessEntityType());

    }

}
