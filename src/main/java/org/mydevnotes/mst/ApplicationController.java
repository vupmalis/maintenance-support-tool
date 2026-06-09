package org.mydevnotes.mst;

import java.util.ArrayList;
import java.util.List;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.datasource.DataCollector;
import org.mydevnotes.mst.datasource.DataRetrieverProvider;
import org.mydevnotes.mst.datasource.DefaultDataCollector;

/**
 *
 * @author vupma
 */
public class ApplicationController implements BusinessEntitySelectionListener, BusinessEntitySelectionProvider {

    private BusinessEntity selectedBusinessEntity;
    private BusinessEntity selectedChildBusinessEntity;
    private final List<BusinessEntityListener> selectionListeners = new ArrayList();
    private final DataCollector dataCollector = new DefaultDataCollector();
    private DataRetrieverProvider dataRetrieverProvider; // = ApplicationContext.getApplicationContext();

    public ApplicationController(DataRetrieverProvider dataRetrieverProvider) {
        this.dataRetrieverProvider = dataRetrieverProvider;
    }

    @Override
    public void onMainBusinessEntitySelected(BusinessEntity businessEntity) {
        this.selectedBusinessEntity = businessEntity;
        this.selectionListeners.forEach(listener -> listener.onBusinessEntitySelected(businessEntity));
    }

    @Override
    public void onChildBusinessEntitySelected(BusinessEntity businessEntity) {

        if (businessEntity != null) {
            this.selectedChildBusinessEntity = businessEntity;

            var entityConfig = ApplicationContext.getApplicationContext().getBusinessEntityConfig(businessEntity.getType());

            if (entityConfig != null) {
                dataCollector.populateBusinessEntityWithDetails(businessEntity, entityConfig, this.dataRetrieverProvider);
            } else {
                ApplicationContext.getApplicationContext().getEventLogger().addLog("Details for " + businessEntity.getType() + "not configured\n");
            }
        }

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
