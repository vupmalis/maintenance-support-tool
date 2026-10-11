package org.mydevnotes.mst.action.scripts;

import javax.sql.DataSource;
import org.mydevnotes.mst.DataSourceNotFoundException;
import org.mydevnotes.mst.EventLogger;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.datasource.AbstractDataSource;
import org.mydevnotes.mst.web.StaticFileHttpServer;

/**
 *
 * @author vupma
 */
public interface ScriptResourcesProvider {
    
    public DataSource getPosgreSQLDataSource(String dataSourceName) throws DataSourceNotFoundException;
    
    public String getBusinessEntityId();
    
    public String getBusinessEntityType();
    
    public EventLogger getEventLogger();
    
    public BusinessEntity getBusinessEntity();
    
    public StaticFileHttpServer getStaticFileHttpServer() throws Exception;
    
    public AbstractDataSource getDataSource(String dataSourceName) throws DataSourceNotFoundException;
}
