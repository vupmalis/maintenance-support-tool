package org.mydevnotes.mst.datasource;

import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.config.BusinessEntityRelation;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public interface DataCollector { 
    public BusinessEntity getBusinessEntityWithDetails(DataRetrieverProvider dataSourceProvider, String entityId, BusinessEntityConfig entityConfig, BusinessEntityRelation relationConfig);    
}
