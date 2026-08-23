package org.mydevnotes.mst.datasource;

/**
 *
 * @author vupma
 */
public enum DataSourceConnectionType {
    
    POSTGRESQL("PostgreSQL"), 
    ACTIVEMQ("ActiveMQ");

    private final String value;

    DataSourceConnectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DataSourceConnectionType fromValue(String value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case "PostgreSQL" ->
                POSTGRESQL; 
            case "ActiveMQ" ->
                ACTIVEMQ;                 
            default ->
                throw new IllegalArgumentException(
                        "Unknown value: " + value
                );
        };
    }
}
