package org.mydevnotes.mst.datasource;

/**
 *
 * @author vupma
 */
public enum DataSourceType {

    
    DB("db"), 
    JMS("jms");

    private final String value;

    DataSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DataSourceType fromValue(String value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case "db" ->
                DB;  
            case "jms" ->
                JMS;                 
            default ->
                throw new IllegalArgumentException(
                        "Unknown value: " + value
                );
        };
    }
}
