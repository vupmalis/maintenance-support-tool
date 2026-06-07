package org.mydevnotes.mst.datasource;

import java.util.List;
import java.util.Map;
import org.mydevnotes.mst.config.ChildEntity;
import org.mydevnotes.mst.config.Detail;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public interface DataRetriever {

    public List<BusinessEntity> getChildEntities(String parentId, ChildEntity childEntityConfig);    
    public Map<String, Object> getBusinessEntityDetails(String parentId, Detail detailsConfig);    
    
}
