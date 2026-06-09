package org.mydevnotes.mst.datasource;

import java.util.HashMap;
import java.util.Map;
import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.config.Detail;
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

                    Map<String, Object> detail = getBusinessEntityDetail(businessEntity, detailConfig, dataSourceProvider);
                    System.out.println("Found " + detail.size() + " rows");
                    businessEntity.getDetails().put(detailConfig.getDisplayName(), detail);
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

    private Map<String, Object> getBusinessEntityDetail(BusinessEntity businessEntity, Detail detailConfig, DataRetrieverProvider dataSourceProvider) {

        Map<String, Object> result = new HashMap<>();
        DataRetriever dataRetriever = dataSourceProvider.getDataRetriever(detailConfig.getDataSource());

        if (dataRetriever != null) {

            result = dataRetriever.getBusinessEntityDetails(businessEntity.getId(), detailConfig);
        }

        return result;
    }

}
