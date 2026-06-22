package org.mydevnotes.mst;

import java.util.ArrayList;
import java.util.List;
import org.mydevnotes.mst.config.SearchOption;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.datasource.DataCollector;
import org.mydevnotes.mst.datasource.DataRetrieverProvider;
import org.mydevnotes.mst.datasource.DefaultDataCollector;
import org.mydevnotes.mst.datasource.SearchParameters;

/**
 *
 * @author vupma
 */
public class ApplicationController implements BusinessEntitySelectionListener, BusinessEntitySelectionProvider, ApplicationEventListener {

    private BusinessEntity selectedBusinessEntity;
    private BusinessEntity selectedChildBusinessEntity;
    private final List<BusinessEntityListener> selectionListeners = new ArrayList();
    private final List<BusinessEntitySearchResultListener> searchResultListeners = new ArrayList();
    private final DataCollector dataCollector = new DefaultDataCollector();
    private final DataRetrieverProvider dataRetrieverProvider;
    private EventLogger eventLogger;

    public void setEventLogger(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    public ApplicationController(DataRetrieverProvider dataRetrieverProvider, EventLogger eventLogger) {
        this.dataRetrieverProvider = dataRetrieverProvider;
        this.eventLogger = eventLogger;
    }

    @Override
    public void onMainBusinessEntitySelected(BusinessEntity businessEntity) {
        this.selectedBusinessEntity = businessEntity;
        this.selectionListeners.forEach(listener -> listener.onBusinessEntitySelected(businessEntity));
    }

    @Override
    public void onChildBusinessEntitySelected(BusinessEntity businessEntity) {

        this.selectedChildBusinessEntity = businessEntity;

        if (businessEntity != null) {

            var entityConfig = ApplicationContext.getApplicationContext().getBusinessEntityConfig(businessEntity.getType());

            if (entityConfig != null) {
                dataCollector.populateBusinessEntityWithDetails(businessEntity, entityConfig, this.dataRetrieverProvider);
            } else {
                ApplicationContext.getApplicationContext().getEventLogger().addLog("Details for " + businessEntity.getType() + "not configured");
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

    public void addSearchResultListener(BusinessEntitySearchResultListener listener) {
        this.searchResultListeners.add(listener);
    }

    @Override
    public void searchBusinessEntities(SearchOption searchOption, SearchParameters parameters) throws BusinessEntitySearchException {

        var dataRetriever = this.dataRetrieverProvider.getDataRetriever(searchOption.getDataSource());

        if (dataRetriever != null) {

            var businessEntities = dataRetriever.searchBusinessEntities(searchOption, parameters);
            addLog("Found %d entities".formatted(businessEntities.getSearchResult().size()));

            searchResultListeners.forEach(listener -> listener.onBusinessEntitySearchResultReady(businessEntities));

        } else {

            throw new BusinessEntitySearchException("Data source not found: " + searchOption.getDataSource());

        }
    }

    private void addLog(String log) {
        this.eventLogger.addLog(log);
    }
}
