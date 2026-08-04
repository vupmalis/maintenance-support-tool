package org.mydevnotes.mst;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mydevnotes.mst.config.AppConfig;
import org.mydevnotes.mst.config.DataSource;
import org.mydevnotes.mst.action.scripts.ScriptResourcesProvider;
import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.config.EnvironmentConfig;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.dao.PostgreSqlDataRetriever;
import org.mydevnotes.mst.datasource.DataRetriever;
import org.mydevnotes.mst.datasource.DataRetrieverProvider;
import org.mydevnotes.mst.web.StaticFileHttpServer;

/**
 *
 * @author vupma
 */
public class ApplicationContext implements DataRetrieverProvider, ScriptResourcesProvider {

    String configValidationErrors = "";

    private AppConfig appConfig;
    private EnvironmentConfig envConfig;
    private StaticFileHttpServer staticFileHttpServer;

    public EnvironmentConfig getEnvConfig() {
        return envConfig;
    }
    private final ApplicationController applicationController;
    public final static ApplicationContext applicationContext = new ApplicationContext();

    private Path configPath;

    public void setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
        this.applicationController.setEventLogger(eventLogger);
    }
    private final Map<String, HikariDataSource> postgreSqlDataSources = new HashMap<>();
    private final Map<String, PostgreSqlDataRetriever> postgreSqlDataRetrievers = new HashMap<>();

    private EventLogger eventLogger;

    @Override
    public EventLogger getEventLogger() {
        return this.eventLogger;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setConfig(EnvironmentConfig environmentConfig) {
        this.envConfig = environmentConfig;
    }

    public void setConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String getConfigValidationErrors() {
        return configValidationErrors;
    }

    public void setConfigValidationErrors(String configValidationErrors) {
        this.configValidationErrors = configValidationErrors;
    }

    public ApplicationContext() {
        this.applicationController = new ApplicationController(this, this.getEventLogger());
    }

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

        eventLogger.addLog("Created connection to " + dataSource.getName());
    }

    public void disconnectPostgresqlConnection(DataSource dataSource) {
        if (postgreSqlDataSources.containsKey(dataSource.getName())) {
            HikariDataSource oldDataSource = postgreSqlDataSources.get(dataSource.getName());

            if (oldDataSource != null && !oldDataSource.isClosed()) {
                oldDataSource.close();
            }

            postgreSqlDataSources.remove(dataSource.getName());
            postgreSqlDataRetrievers.remove(dataSource.getName());
            eventLogger.addLog("Disconnected from " + dataSource.getName());
        }
    }

    @Override
    public HikariDataSource getPosgreSQLDataSource(String dataSourceName) throws DataSourceNotFoundException {

        if (postgreSqlDataSources.containsKey(dataSourceName)) {
            return postgreSqlDataSources.get(dataSourceName);
        } else {
            throw new DataSourceNotFoundException("Config does not contains PostgreSQL Datasource " + dataSourceName);
        }
    }

    public synchronized void closeAllDataSources() {

        eventLogger.addLog("Disconnected from all datasources...");
        this.postgreSqlDataSources.forEach((key, value) -> {
            try {
                if (value != null) {

                    if (!value.isClosed()) {
                        value.close();
                    }

                    System.out.println("Closed datasource " + key);
                    eventLogger.addLog("Disconnected from " + key);
                }
            } catch (Exception e) {
                System.err.println("Failed to close datasource " + key);
                eventLogger.addLog("Failed to disconnect from " + key + ":" + e.getMessage());
                e.printStackTrace();
            }
        });
        postgreSqlDataSources.clear();
        postgreSqlDataRetrievers.clear();
    }

    public boolean isConfigLoaded() {
        return getAppConfig() != null;
    }

    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
    }

    public Path getConfigPath() {
        return this.configPath;
    }

    @Override
    public DataRetriever getDataRetriever(String name) {

        DataRetriever dataRetriever = null;

        DataSource dataSourceConfig = envConfig.getDataSources().stream().filter(ds -> ds.getName().equals(name)).findFirst().orElse(null);

        if (dataSourceConfig != null) {

            //TODO introduce enum
            dataRetriever = switch (dataSourceConfig.getConnectionDetails().getType()) {
                case "PostgreSQL" ->
                    postgreSqlDataRetrievers.get(name);
                default ->
                    throw new IllegalStateException("Unexpected value: " + (dataSourceConfig.getType()));
            };
        }

        return dataRetriever;
    }

    @Override
    public String getBusinessEntityType() {
        return this.applicationController.getChildBusinessEntity() == null ? this.applicationController.getChildBusinessEntity().getType() : this.applicationController.getMainBusinessEntity().getType();
    }

    @Override
    public String getBusinessEntityId() {
        return this.applicationController.getChildBusinessEntity() == null ? this.applicationController.getChildBusinessEntity().getId() : this.applicationController.getMainBusinessEntity().getId();
    }

    public ApplicationController getApplicationController() {
        return this.applicationController;
    }

    public BusinessEntityConfig getBusinessEntityConfig(String businessEntityType) {
        return this.appConfig.getBusinessEntityConfig().stream().filter(bec -> businessEntityType.equals(bec.getBusinessEntityType())).findFirst().orElse(null);
    }

    @Override
    public BusinessEntity getBusinessEntity() {
        return this.applicationController.getChildBusinessEntity() == null ? this.applicationController.getChildBusinessEntity() : this.applicationController.getMainBusinessEntity();
    }

    @Override
    public StaticFileHttpServer getStaticFileHttpServer() {

        if (this.staticFileHttpServer == null) {
            try {
                String currentPath = System.getProperty("user.dir");
                Path currentDirectory = Path.of(currentPath).resolve("htmlview");
                Files.createDirectories(currentDirectory);
                this.staticFileHttpServer = new StaticFileHttpServer(currentDirectory, 0);
                
                this.staticFileHttpServer.start();
            } catch (IOException ex) {
                Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return this.staticFileHttpServer;
    }
}
