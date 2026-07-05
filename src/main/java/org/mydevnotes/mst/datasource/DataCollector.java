package org.mydevnotes.mst.datasource;

import java.util.List;
import org.mydevnotes.mst.config.BusinessEntityConfig;
import org.mydevnotes.mst.config.BusinessEntityRelation;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public interface DataCollector { 
    
    public void enrichBusinessEntityWithDetails(BusinessEntity businessEntity, BusinessEntityConfig entityConfig, DataRetrieverProvider dataSourceProvider);
    
    public void enrichBusinessEntityWithChildrens(int treeHight, BusinessEntity businessEntity, List<BusinessEntityRelation> entityRelations, DataRetrieverProvider dataSourceProvider);
}
