package org.mydevnotes.mst;

import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public interface BusinessEntitySelectionListener {
    
    public void setMainBusinessEntity(BusinessEntity businessEntity);
    
    public void setChildBusinessEntity(BusinessEntity businessEntity);
   
}
