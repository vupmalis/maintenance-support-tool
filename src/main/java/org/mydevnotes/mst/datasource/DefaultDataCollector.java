package org.mydevnotes.mst.datasource;

import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public class DefaultDataCollector implements DataCollector {

    @Override
    public void populateBusinessEntityWithDetails(BusinessEntity businessEntity, BusinessEntityConfig entityConfig, DataRetrieverProvider dataSourceProvider) {

        //BusinessEntity businessEntity = getBusinessEntity( dataSourceProvider, entityId, entityConfig);
        
        entityConfig.getDetails().forEach(
                detailConfig -> {
                    System.out.println("Get details for " + detailConfig.getDisplayName());
                    
                }
        );
        
        
        
        
    }

    private BusinessEntity getBusinessEntity(DataRetrieverProvider dataSourceProvider, String entityId, BusinessEntityConfig entityConfig) {

        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setId(entityId);

        entityConfig.getDetails().forEach(
                detailsConfig -> {
                    DataRetriever dataRetriever = dataSourceProvider.getDataRetriever(detailsConfig.getDataSource());
                    var details = dataRetriever.getBusinessEntityDetails(entityId, detailsConfig);
                    // businessEntity.get

                }
        );

        return businessEntity;
    }

}
