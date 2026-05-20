package org.mydevnotes.mst;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import org.mydevnotes.mst.config.AppConfig;
import org.mydevnotes.mst.config.Source;

/**
 *
 * @author vupma
 */
public class ApplicationContext {

    String configValidationErrors = "";    

    private AppConfig appConfig;

    public void setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }
    private Map<String, HikariDataSource> postgreSqlDataSources = new HashMap<>();
    private EventLogger eventLogger;

    public EventLogger getEventLogger() {
        return eventLogger;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String getConfigValidationErrors() {
        return configValidationErrors;
    }

    public void setConfigValidationErrors(String configValidationErrors) {
        this.configValidationErrors = configValidationErrors;
    }

    public final static ApplicationContext applicationContext = new ApplicationContext();

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public boolean configIsValid() {
        return configValidationErrors.isBlank();
    }

    public void createPosgreSQLConnection(Source dataSource) {

        disconnectPostgresqlConnection(dataSource);

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(dataSource.getConnectionDetails().getConnectionString());
        config.setUsername(dataSource.getConnectionDetails().getUserName());
        config.setPassword(dataSource.getConnectionDetails().getPassword());

        config.setMaximumPoolSize(1);
        config.setMinimumIdle(1);
        config.setIdleTimeout(30000);

        HikariDataSource newDataSource = new HikariDataSource(config);

        postgreSqlDataSources.put(dataSource.getName(), newDataSource);
        
        eventLogger.addLog("Created connection to " + dataSource.getName() + "\n");
    }

    public void disconnectPostgresqlConnection(Source dataSource) {
        if (postgreSqlDataSources.containsKey(dataSource.getName())) {
            HikariDataSource oldDataSource = postgreSqlDataSources.get(dataSource.getName());
            
            if (oldDataSource != null && !oldDataSource.isClosed()) {
                oldDataSource.close();
            }
            
            postgreSqlDataSources.remove(dataSource.getName());
            eventLogger.addLog("Disconnected from " + dataSource.getName() + "\n");
        }
    }

    public HikariDataSource getPosgreSQLDataSource(String dataSourceName) {
        return postgreSqlDataSources.get(dataSourceName);
    }

    public void closeAllDataSources() {

        eventLogger.addLog("Disconnected from all datasources...");
        this.postgreSqlDataSources.forEach((key, value) -> {
            try {
                if (value != null) {
                    value.close(); 
                    System.out.println("Closed datasource " + key);
                    eventLogger.addLog("Disconnected from " + key + "\n");
                }
            } catch (Exception e) {
                System.err.println("Failed to close datasource " + key);
                eventLogger.addLog("Failed to disconnect from " + key + ":" + e.getMessage() + "\n");
                e.printStackTrace();
            }
        });
    }

}
