package org.mydevnotes.mst.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mydevnotes.mst.config.DataSource;
import org.mydevnotes.mst.jms.ActiveMQJmsMessageHandler;

/**
 *
 * @author vupma
 */
public class DataSourceFactory {

    public static AbstractDataSource createDataSource(DataSource dataSourceConfg) {

        AbstractDataSource dataSource = null;

        if (dataSourceConfg != null) {

            DataSourceConnectionType type = DataSourceConnectionType.fromValue(dataSourceConfg.getConnectionDetails().getType());

            dataSource = switch (type) {
                case DataSourceConnectionType.POSTGRESQL ->
                    createPosgreSQLConnection(dataSourceConfg);
                case DataSourceConnectionType.ACTIVEMQ ->
                    createActiveMQConnection(dataSourceConfg);                    

                default ->
                    createPosgreSQLConnection(dataSourceConfg);

            };
        }
        return dataSource;
    }

    private static AbstractDataSource createPosgreSQLConnection(DataSource dataSourceConfig) {

        //disconnectPostgresqlConnection(dataSource);
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(dataSourceConfig.getConnectionDetails().getConnectionString());
        config.setUsername(dataSourceConfig.getConnectionDetails().getUserName());
        config.setPassword(dataSourceConfig.getConnectionDetails().getPassword());

        config.setMaximumPoolSize(1);
        config.setMinimumIdle(1);
        config.setIdleTimeout(30000);

        HikariDataSource newDataSource = new HikariDataSource(config);

        //postgreSqlDataSources.put(dataSource.getName(), newDataSource);
        //postgreSqlDataRetrievers.put(dataSource.getName(), new PostgreSqlDataRetriever(dataSource.getName(), newDataSource));
        //eventLogger.addLog("Created connection to " + dataSource.getName());
        
        return new HikariDataSourceWrapper(newDataSource);
    }

    private static AbstractDataSource createActiveMQConnection(DataSource dataSourceConfg) {        
        return new ActiveMQJmsMessageHandler(dataSourceConfg);
    }

}
