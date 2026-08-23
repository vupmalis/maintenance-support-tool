package org.mydevnotes.mst.datasource;

import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author vupma
 */
public class HikariDataSourceWrapper implements AbstractDataSource {

    HikariDataSource hikariDataSource;

    public HikariDataSourceWrapper(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;        
    }

    @Override
    public void close() {
        if (this.hikariDataSource != null && !this.hikariDataSource.isClosed()) {
            this.hikariDataSource.close();
        }
    }

    @Override
    public boolean isClosed() {
        return this.hikariDataSource == null || this.hikariDataSource.isClosed();
    }

    public HikariDataSource getDataSource() {
        return this.hikariDataSource;
    }

    @Override
    public DataSourceConnectionType getType() {
        return DataSourceConnectionType.POSTGRESQL;
    }

}
