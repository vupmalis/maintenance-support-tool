
package org.mydevnotes.mst.config;

import java.util.LinkedHashMap;
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
    "request",
    "parentReference",
    "parentReferenceType"
})
@Generated("jsonschema2pojo")
public class ChildEntity {

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
    @JsonProperty("request")
    @NotNull
    private String request;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("parentReference")
    @NotNull
    private String parentReference;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("parentReferenceType")
    @NotNull
    private String parentReferenceType;
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
    @JsonProperty("parentReference")
    public String getParentReference() {
        return parentReference;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("parentReference")
    public void setParentReference(String parentReference) {
        this.parentReference = parentReference;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("parentReferenceType")
    public String getParentReferenceType() {
        return parentReferenceType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("parentReferenceType")
    public void setParentReferenceType(String parentReferenceType) {
        this.parentReferenceType = parentReferenceType;
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
