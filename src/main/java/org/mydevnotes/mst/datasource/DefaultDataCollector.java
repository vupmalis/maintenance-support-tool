package org.mydevnotes.mst.datasource;

import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.config.BusinessEntityRelation;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public class DefaultDataCollector implements DataCollector{

    @Override
    public BusinessEntity getBusinessEntityWithDetails(DataRetrieverProvider dataSourceProvider, String entityId, BusinessEntityConfig entityConfig, BusinessEntityRelation relationConfig) {
        
        
        BusinessEntity businessEntity = getBusinessEntity( dataSourceProvider, entityId, entityConfig);
        
        
        return businessEntity;
        
    }

    private BusinessEntity getBusinessEntity(DataRetrieverProvider dataSourceProvider, String entityId, BusinessEntityConfig entityConfig) {
        
        BusinessEntity businessEntity = null;
        
        DataRetriever dataRetriever = dataSourceProvider.getDataRetriever(entityConfig.getDataSource());
        
        
        
        return businessEntity;
    }
    
}
