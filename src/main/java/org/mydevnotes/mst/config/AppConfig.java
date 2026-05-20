
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
    "dataSources",
    "searchSection",
    "detailsSection",
    "objectAttributes"
})
@Generated("jsonschema2pojo")
public class AppConfig {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dataSources")
    @NotNull
    private List<@Valid DataSource> dataSources;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchSection")
    @Valid
    @NotNull
    private SearchSection searchSection;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detailsSection")
    @NotNull
    private List<@Valid DetailsSection> detailsSection;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objectAttributes")
    @NotNull
    private List<@Valid ObjectAttribute> objectAttributes;
    @JsonIgnore
    private Map<String, @Valid Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dataSources")
    public List<DataSource> getDataSources() {
        return dataSources;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("dataSources")
    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchSection")
    public SearchSection getSearchSection() {
        return searchSection;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchSection")
    public void setSearchSection(SearchSection searchSection) {
        this.searchSection = searchSection;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detailsSection")
    public List<DetailsSection> getDetailsSection() {
        return detailsSection;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detailsSection")
    public void setDetailsSection(List<DetailsSection> detailsSection) {
        this.detailsSection = detailsSection;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objectAttributes")
    public List<ObjectAttribute> getObjectAttributes() {
        return objectAttributes;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("objectAttributes")
    public void setObjectAttributes(List<ObjectAttribute> objectAttributes) {
        this.objectAttributes = objectAttributes;
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
