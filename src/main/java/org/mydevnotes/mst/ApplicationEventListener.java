package org.mydevnotes.mst;

import org.mydevnotes.mst.config.SearchOption;
import org.mydevnotes.mst.datasource.SearchParameters;

/**
 *
 * @author vupma
 */
public interface ApplicationEventListener {
    public void searchBusinessEntities(SearchOption searchOption, SearchParameters parameters) throws BusinessEntitySearchException;
}
