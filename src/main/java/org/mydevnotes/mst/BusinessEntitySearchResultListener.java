package org.mydevnotes.mst;

import org.mydevnotes.mst.dao.BusinessEntitySearchResult;

/**
 *
 * @author vupma
 */
public interface BusinessEntitySearchResultListener {
    public void onBusinessEntitySearchResultReady(BusinessEntitySearchResult searchResult);
}
