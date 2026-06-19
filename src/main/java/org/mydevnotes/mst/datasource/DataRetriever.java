package org.mydevnotes.mst.datasource;

import java.util.List;
import java.util.Map;
import org.mydevnotes.mst.config.ChildEntity;
import org.mydevnotes.mst.config.Detail;
import org.mydevnotes.mst.config.SearchOption;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.dao.BusinessEntitySearchResult;

/**
 *
 * @author vupma
 */
public interface DataRetriever {

    public List<BusinessEntity> getChildEntities(String parentId, ChildEntity childEntityConfig);    
    public Map<String, Object> getBusinessEntityDetails(String parentId, Detail detailsConfig);   
    public BusinessEntitySearchResult searchBusinessEntities(SearchOption searchOption, SearchParameters parameters);
    
}
