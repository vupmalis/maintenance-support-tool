package org.mydevnotes.mst.datasource;

/**
 *
 * @author vupma
 */
public interface DataRetrieverProvider {    
    
    public DataRetriever getDataRetriever(String name);
    
}
