package org.mydevnotes.mst;

import java.util.ArrayList;
import java.util.List;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.datasource.DataCollector;
import org.mydevnotes.mst.datasource.DataRetrieverProvider;

/**
 *
 * @author vupma
 */
public class ApplicationController implements BusinessEntitySelectionListener, BusinessEntitySelectionProvider {

    private BusinessEntity selectedBusinessEntity;
    private BusinessEntity selectedChildBusinessEntity;
    private final List<BusinessEntityListener> selectionListeners = new ArrayList();

    @Override
    public void onMainBusinessEntitySelected(BusinessEntity businessEntity) {
        this.selectedBusinessEntity = businessEntity;
        this.selectionListeners.forEach(listener -> listener.onBusinessEntitySelected(businessEntity));
    }

    @Override
    public void onChildBusinessEntitySelected(BusinessEntity businessEntity) {
        this.selectedChildBusinessEntity = businessEntity;
        
        
        
        this.selectionListeners.forEach(listener -> listener.onBusinessEntitySelected(businessEntity));
    }

    @Override
    public BusinessEntity getMainBusinessEntity() {
        return this.selectedBusinessEntity;
    }

    @Override
    public BusinessEntity getChildBusinessEntity() {
        return this.selectedChildBusinessEntity;
    }

    public void addSelectionListener(BusinessEntityListener selectionListener) {
        this.selectionListeners.add(selectionListener);
    }
}
