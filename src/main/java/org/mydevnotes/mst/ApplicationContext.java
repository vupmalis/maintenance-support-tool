package org.mydevnotes.mst;

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
import org.mydevnotes.mst.datasource.AbstractDataSource;
import org.mydevnotes.mst.datasource.DataRetriever;
import org.mydevnotes.mst.datasource.DataRetrieverProvider;
import org.mydevnotes.mst.datasource.DataSourceConnectionType;
import org.mydevnotes.mst.datasource.DataSourceFactory;
import org.mydevnotes.mst.datasource.HikariDataSourceWrapper;
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
    private Path staticFileHttpServerRootLocation;

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

    //TODO use abstract datasource
    private final Map<String, AbstractDataSource> dataSources = new HashMap<>();
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

        /*
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(dataSource.getConnectionDetails().getConnectionString());
        config.setUsername(dataSource.getConnectionDetails().getUserName());
        config.setPassword(dataSource.getConnectionDetails().getPassword());

        config.setMaximumPoolSize(1);
        config.setMinimumIdle(1);
        config.setIdleTimeout(30000);

        HikariDataSource newDataSource = new HikariDataSource(config);
         */
        AbstractDataSource newDataSource = DataSourceFactory.createDataSource(dataSource);
        dataSources.put(dataSource.getName(), newDataSource);

        if (DataSourceConnectionType.POSTGRESQL.equals(newDataSource.getType())) {
            postgreSqlDataRetrievers.put(dataSource.getName(), new PostgreSqlDataRetriever(dataSource.getName(), ((HikariDataSourceWrapper) newDataSource).getDataSource()));
        }

        eventLogger.addLog("Created connection to " + dataSource.getName());
    }

    public void disconnectPostgresqlConnection(DataSource dataSource) {
        if (dataSources.containsKey(dataSource.getName())) {
            var oldDataSource = dataSources.get(dataSource.getName());
            oldDataSource.close();
            dataSources.remove(dataSource.getName());

            if (DataSourceConnectionType.POSTGRESQL.equals(oldDataSource.getType())) {
                postgreSqlDataRetrievers.remove(dataSource.getName());
            }
            eventLogger.addLog("Disconnected from " + dataSource.getName());
        }
    }

    @Override
    public HikariDataSource getPosgreSQLDataSource(String dataSourceName) throws DataSourceNotFoundException {

        if (dataSources.containsKey(dataSourceName)) {

            AbstractDataSource abstractDataSource = dataSources.get(dataSourceName);

            if (DataSourceConnectionType.POSTGRESQL.equals(abstractDataSource.getType())) {
                HikariDataSourceWrapper dataSourceWrapper = (HikariDataSourceWrapper) abstractDataSource;
                return dataSourceWrapper.getDataSource();
            }

            throw new RuntimeException("Data source %s no an postgresql datasource".formatted(dataSourceName));

        } else {
            throw new DataSourceNotFoundException("Config does not contains PostgreSQL Datasource " + dataSourceName);
        }
    }

    public synchronized void closeAllDataSources() {

        eventLogger.addLog("Disconnected from all datasources...");
        this.dataSources.forEach((key, value) -> {
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
        dataSources.clear();
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

            DataSourceConnectionType connectionType = DataSourceConnectionType.fromValue(dataSourceConfig.getConnectionDetails().getType());
            dataRetriever = switch (connectionType) {
                case DataSourceConnectionType.POSTGRESQL ->
                    this.postgreSqlDataRetrievers.get(name);
                default ->
                    throw new IllegalStateException("Unexpected value: " + (dataSourceConfig.getType()));
            };
        }

        return dataRetriever;
    }

    @Override
    public String getBusinessEntityType() {
        return this.applicationController.getChildBusinessEntity() != null ? this.applicationController.getChildBusinessEntity().getType() : this.applicationController.getMainBusinessEntity().getType();
    }

    @Override
    public String getBusinessEntityId() {
        return this.applicationController.getChildBusinessEntity() != null ? this.applicationController.getChildBusinessEntity().getId() : this.applicationController.getMainBusinessEntity().getId();
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
    public StaticFileHttpServer getStaticFileHttpServer() throws Exception {

        if (this.staticFileHttpServer == null) {
            try {
                Path currentDirectory = this.getStaticFileHttpServerRootLocation().resolve(this.getStaticFileHttpServerRootLocation());
                Files.createDirectories(currentDirectory);
                this.staticFileHttpServer = new StaticFileHttpServer(currentDirectory, 0);

                this.staticFileHttpServer.start();
            } catch (IOException ex) {
                Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return this.staticFileHttpServer;
    }

    public Path getStaticFileHttpServerRootLocation() throws Exception {
        if (this.staticFileHttpServerRootLocation == null) {
            throw new Exception("Static web app location root not configured");
        }

        return this.staticFileHttpServerRootLocation;
    }

    public void setStaticFileHttpServerRootLocation(Path staticWebAppRootLocation) {
        this.staticFileHttpServerRootLocation = staticWebAppRootLocation;
    }

    public boolean isProduction() {
        var prodEnv = this.envConfig.getDataSources().stream().filter(env -> env.getIsProd()).findFirst();

        return !prodEnv.isEmpty();
    }

    @Override
    public AbstractDataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException {

        if (this.dataSources.containsKey(dataSourceName)) {

            return this.dataSources.get(dataSourceName);

        } else {
            throw new DataSourceNotFoundException("Config does not contains PostgreSQL Datasource " + dataSourceName);
        }
    }

}
