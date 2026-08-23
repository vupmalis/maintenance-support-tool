package org.mydevnotes.mst.datasource;

/**
 *
 * @author vupma
 */
public interface AbstractDataSource {
    public void close();   
    public boolean isClosed();
    public DataSourceConnectionType getType();
}
