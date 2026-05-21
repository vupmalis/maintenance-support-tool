package org.mydevnotes.mst.ui.tree;

import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public class BusinessEntityNode {
    
    private final BusinessEntity businessEntity;

    public BusinessEntityNode(BusinessEntity businessEntity) {
        this.businessEntity = businessEntity;
    }

    public BusinessEntity getBusinessEntity() {
        return this.businessEntity;
    }
    
    public String getDisplayName() {
        return this.businessEntity.getName() + "[" + this.businessEntity.getId() + "]";
    }
    
}
