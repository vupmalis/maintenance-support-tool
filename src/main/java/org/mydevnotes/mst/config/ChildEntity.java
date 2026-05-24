
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
    "searchDetails"
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
    @JsonProperty("searchDetails")
    @NotNull
    private List<@Valid SearchDetail> searchDetails;
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
    @JsonProperty("searchDetails")
    public List<SearchDetail> getSearchDetails() {
        return searchDetails;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchDetails")
    public void setSearchDetails(List<SearchDetail> searchDetails) {
        this.searchDetails = searchDetails;
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
