package org.mydevnotes.mst;

import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public interface BusinessEntitySelectionListener {
    
    public void onMainBusinessEntitySelected(BusinessEntity businessEntity);
    
    public void onChildBusinessEntitySelected(BusinessEntity businessEntity);
   
}
