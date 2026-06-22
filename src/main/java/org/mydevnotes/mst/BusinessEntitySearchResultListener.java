package org.mydevnotes.mst;

import java.util.List;
import org.mydevnotes.mst.dao.BusinessEntity;
import org.mydevnotes.mst.dao.BusinessEntitySearchResult;

/**
 *
 * @author vupma
 */
public interface BusinessEntitySearchResultListener {
    public void onBusinessEntitySearchResultReady(BusinessEntitySearchResult searchResult);
}
