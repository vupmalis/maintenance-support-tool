package org.mydevnotes.mst.datasource;

import java.util.Map;

/**
 *
 * @author vupma
 */
public class SearchParameters {
    
   private Map<String, Object> values; 

    public SearchParameters(Map<String, Object> values) {
        this.values = values;
    }

    public Map<String, Object> getValues() {
        return values;
    }
    
}
