package org.mydevnotes.mst.action.scripts;

import javax.sql.DataSource;
import org.mydevnotes.mst.DataSourceNotFoundException;

/**
 *
 * @author vupma
 */
public interface ScriptResourcesProvider {
    
    public DataSource getPosgreSQLDataSource(String dataSourceName) throws DataSourceNotFoundException;
    
    public Long getBusinessEntityId();
    
    public String getBusinessEntityType();
}
