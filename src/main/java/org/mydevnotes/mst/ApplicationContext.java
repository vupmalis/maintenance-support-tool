package org.mydevnotes.mst;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mydevnotes.mst.config.AppConfig;
import org.mydevnotes.mst.config.DataSource;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.action.scripts.ScriptResourcesProvider;
import org.mydevnotes.mst.dao.PostgreSqlDataRetriever;
import org.mydevnotes.mst.datasource.DataRetriever;
import org.mydevnotes.mst.datasource.DataRetrieverProvider;

/**
 *
 * @author vupma
 */
public class ApplicationContext  implements BusinessEntitySelectionListener, BusinessEntitySelectionProvider, ScriptResourcesProvider, DataRetrieverProvider{

    String configValidationErrors = "";    

    private AppConfig appConfig;
    
    private BusinessEntity selectedBusinessEntity;
    private BusinessEntity selectedChildBusinessEntity;
    private List<BusinessEntityListener> selectionListeners = new ArrayList();
    private Path configPath;

    public void setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }
    private Map<String, HikariDataSource> postgreSqlDataSources = new HashMap<>();
    private Map<String, PostgreSqlDataRetriever> postgreSqlDataRetrievers = new HashMap<>();
    
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

    public void createPosgreSQLConnection(DataSource dataSource) {

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
        postgreSqlDataRetrievers.put(dataSource.getName(), new PostgreSqlDataRetriever(dataSource.getName(), newDataSource));
        
        eventLogger.addLog("Created connection to " + dataSource.getName() + "\n");
    }

    public void disconnectPostgresqlConnection(DataSource dataSource) {
        if (postgreSqlDataSources.containsKey(dataSource.getName())) {
            HikariDataSource oldDataSource = postgreSqlDataSources.get(dataSource.getName());
            
            if (oldDataSource != null && !oldDataSource.isClosed()) {
                oldDataSource.close();
            }
            
            postgreSqlDataSources.remove(dataSource.getName());
            postgreSqlDataRetrievers.remove(dataSource.getName());
            eventLogger.addLog("Disconnected from " + dataSource.getName() + "\n");
        }
    }

    @Override
    public HikariDataSource getPosgreSQLDataSource(String dataSourceName) throws DataSourceNotFoundException{
        
        if (postgreSqlDataSources.containsKey(dataSourceName)) {        
            return postgreSqlDataSources.get(dataSourceName);
        } else {
            throw new DataSourceNotFoundException("Config does not contains PostgreSQL Datasource " + dataSourceName);
        }
    }

    public void closeAllDataSources() {

        eventLogger.addLog("Disconnected from all datasources...");
        this.postgreSqlDataSources.forEach((key, value) -> {
            try {
                if (value != null) {
                    
                    if (!value.isClosed()){
                        value.close(); 
                    }
                    
                    postgreSqlDataRetrievers.remove(key);
                    postgreSqlDataSources.remove(key);
                    
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

    @Override
    public void onMainBusinessEntitySelected(BusinessEntity businessEntity) {
        this.selectedBusinessEntity = businessEntity;
        this.selectionListeners.forEach(listener -> listener.onBusinessEntitySelected(businessEntity));
    }

    @Override
    public BusinessEntity getMainBusinessEntity() {
        return this.selectedBusinessEntity;
    }

    @Override
    public void onChildBusinessEntitySelected(BusinessEntity businessEntity) {
        this.selectedChildBusinessEntity = businessEntity;
        this.selectionListeners.forEach(listener -> listener.onBusinessEntitySelected(businessEntity));
    }

    @Override
    public BusinessEntity getChildBusinessEntity() {
        return this.selectedChildBusinessEntity;
    }
    
    public void addSelectionListener(BusinessEntityListener selectionListener){
        this.selectionListeners.add(selectionListener);
    }

    @Override
    public Long getBusinessEntityId() {
        return this.getChildBusinessEntity() == null ? this.getChildBusinessEntity().getId() : this.getMainBusinessEntity().getId();
    }

    @Override
    public String getBusinessEntityType() {
        return this.getChildBusinessEntity() == null ? this.getChildBusinessEntity().getType() : this.getMainBusinessEntity().getType();        
    }

    public boolean isConfigLoaded() {
        return getAppConfig() != null;
    }

    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
    }
    
    public Path getConfigPath(){
        return this.configPath;
    }

    @Override
    public DataRetriever getDataRetriever(String name) {
        
        DataRetriever dataRetriever = null;
        
        DataSource dataSourceConfig = appConfig.getDataSources().stream().filter(ds -> ds.getName().equals(name)).findFirst().orElse(null);
        
        if (dataSourceConfig != null){
            
            //TODO introduce enum
            dataRetriever = switch (dataSourceConfig.getType()) {
                case "PostgreSQL" -> postgreSqlDataRetrievers.get(dataSourceConfig.getType());               
                default -> throw new IllegalStateException("Unexpected value: " + (dataSourceConfig.getType()));
            };
        }
        
        return dataRetriever;
    }

}
