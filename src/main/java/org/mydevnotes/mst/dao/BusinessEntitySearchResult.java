package org.mydevnotes.mst.dao;

import java.util.List;

/**
 *
 * @author vupma
 */
public class BusinessEntitySearchResult {
    
    private final String[] entityAttributes; 
    private final List<BusinessEntity> searchResult;
    private final String entityType;

    public BusinessEntitySearchResult(String[] entityAttributes, List<BusinessEntity> searchResult, String objectType) {
        this.entityAttributes = entityAttributes;
        this.searchResult = searchResult;
        this.entityType = objectType;
    }

    public String[] getEntityAttributes() {
        return this.entityAttributes;
    }

    public List<BusinessEntity> getSearchResult() {
        return this.searchResult;
    }

    public String getEntityType() {
        return this.entityType; 
    }
    
    
}
