
package org.mydevnotes.mst.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "businessEntityType",
    "dataSource",
    "details",
    "actions"
})
@Generated("jsonschema2pojo")
public class BusinessEntityConfig {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityType")
    @NotNull
    private String businessEntityType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dataSource")
    @NotNull
    private String dataSource;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("details")
    @NotNull
    private List<@Valid Detail> details;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("actions")
    @NotNull
    private List<@Valid Action> actions;
    @JsonIgnore
    private Map<String, @Valid Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityType")
    public String getBusinessEntityType() {
        return businessEntityType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityType")
    public void setBusinessEntityType(String businessEntityType) {
        this.businessEntityType = businessEntityType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dataSource")
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dataSource")
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("details")
    public List<Detail> getDetails() {
        return details;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("details")
    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("actions")
    public List<Action> getActions() {
        return actions;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("actions")
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
