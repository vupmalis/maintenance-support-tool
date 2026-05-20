
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
    "objectType",
    "detailsObjects"
})
@Generated("jsonschema2pojo")
public class DetailsSection {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objectType")
    @NotNull
    private String objectType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detailsObjects")
    @NotNull
    private List<@Valid DetailsObject> detailsObjects;
    @JsonIgnore
    private Map<String, @Valid Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objectType")
    public String getObjectType() {
        return objectType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objectType")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detailsObjects")
    public List<DetailsObject> getDetailsObjects() {
        return detailsObjects;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detailsObjects")
    public void setDetailsObjects(List<DetailsObject> detailsObjects) {
        this.detailsObjects = detailsObjects;
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
