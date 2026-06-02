package org.mydevnotes.mst;

import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public interface BusinessEntitySelectionProvider {
    
    public BusinessEntity getChildBusinessEntity();  
        
    public BusinessEntity getMainBusinessEntity();
}
