
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
    "name",
    "dataSource",
    "businessEntityType",
    "request",
    "searchParameters"
})
@Generated("jsonschema2pojo")
public class SearchOption {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    @NotNull
    private String name;
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
    @JsonProperty("businessEntityType")
    @NotNull
    private String businessEntityType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("request")
    @NotNull
    private String request;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchParameters")
    @NotNull
    private List<@Valid SearchParameter> searchParameters;
    @JsonIgnore
    private Map<String, @Valid Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
    @JsonProperty("request")
    public String getRequest() {
        return request;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("request")
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchParameters")
    public List<SearchParameter> getSearchParameters() {
        return searchParameters;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchParameters")
    public void setSearchParameters(List<SearchParameter> searchParameters) {
        this.searchParameters = searchParameters;
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
