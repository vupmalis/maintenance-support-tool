package org.mydevnotes.mst.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.config.BusinessEntityRelation;
import org.mydevnotes.mst.config.ChildEntity;
import org.mydevnotes.mst.config.Detail;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public class DefaultDataCollector implements DataCollector {

    // safe guard against infinite loops
    private static final int MAX_TREE_HIGHT = 15;

    @Override
    public void enrichBusinessEntityWithDetails(BusinessEntity businessEntity, BusinessEntityConfig entityConfig, DataRetrieverProvider dataSourceProvider) {

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

    private Map<String, Object> getBusinessEntityDetail(BusinessEntity businessEntity, Detail detailConfig, DataRetrieverProvider dataSourceProvider) {

        Map<String, Object> result = new HashMap<>();
        DataRetriever dataRetriever = dataSourceProvider.getDataRetriever(detailConfig.getDataSource());

        if (dataRetriever != null) {

            result = dataRetriever.getBusinessEntityDetails(businessEntity.getId(), detailConfig);
        }

        return result;
    }

    @Override
    public void enrichBusinessEntityWithChildrens(int treeHight, BusinessEntity businessEntity, List<BusinessEntityRelation> entityRelations, DataRetrieverProvider dataSourceProvider) {

        if (treeHight > MAX_TREE_HIGHT) {
            return;
        }

        var entityRelationConfig = entityRelations.stream().filter(conf -> conf.getBusinessEntityType().equals(businessEntity.getType())).findFirst().orElse(null);

        if (entityRelationConfig != null) {

            entityRelationConfig.getChildEntities().forEach(relation -> {

                List<BusinessEntity> childEntities = getChildEntities(businessEntity, relation, dataSourceProvider);
                businessEntity.getChildrens().put(relation.getBusinessEntityType(), childEntities);

                childEntities.forEach(childEntity -> {
                    enrichBusinessEntityWithChildrens(treeHight + 1, childEntity, entityRelations, dataSourceProvider);
                });
            }
            );
        }
    }

    private List<BusinessEntity> getChildEntities(BusinessEntity businessEntity, ChildEntity childEntitiesConfig, DataRetrieverProvider dataSourceProvider) {
        DataRetriever dataRetriever = dataSourceProvider.getDataRetriever(childEntitiesConfig.getDataSource());
        return dataRetriever.getChildEntities(businessEntity.getId(), childEntitiesConfig);
    }
}
