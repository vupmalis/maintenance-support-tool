package org.mydevnotes.mst.datasource;

import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public interface DataCollector { 
    public void populateBusinessEntityWithDetails(BusinessEntity businessEntity, BusinessEntityConfig entityConfig, DataRetrieverProvider dataSourceProvider);    
}
