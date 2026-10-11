
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
    "searchConfig",
    "businessEntityRelations",
    "businessEntityConfig",
    "staticWebAppConfig"
})
@Generated("jsonschema2pojo")
public class AppConfig {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchConfig")
    @Valid
    @NotNull
    private SearchConfig searchConfig;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityRelations")
    @NotNull
    private List<@Valid BusinessEntityRelation> businessEntityRelations;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityConfig")
    @NotNull
    private List<@Valid BusinessEntityConfig> businessEntityConfig;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("staticWebAppConfig")
    @NotNull
    private List<@Valid StaticWebAppConfig> staticWebAppConfig;
    @JsonIgnore
    private Map<String, @Valid Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchConfig")
    public SearchConfig getSearchConfig() {
        return searchConfig;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchConfig")
    public void setSearchConfig(SearchConfig searchConfig) {
        this.searchConfig = searchConfig;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityRelations")
    public List<BusinessEntityRelation> getBusinessEntityRelations() {
        return businessEntityRelations;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityRelations")
    public void setBusinessEntityRelations(List<BusinessEntityRelation> businessEntityRelations) {
        this.businessEntityRelations = businessEntityRelations;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityConfig")
    public List<BusinessEntityConfig> getBusinessEntityConfig() {
        return businessEntityConfig;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("businessEntityConfig")
    public void setBusinessEntityConfig(List<BusinessEntityConfig> businessEntityConfig) {
        this.businessEntityConfig = businessEntityConfig;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("staticWebAppConfig")
    public List<StaticWebAppConfig> getStaticWebAppConfig() {
        return staticWebAppConfig;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("staticWebAppConfig")
    public void setStaticWebAppConfig(List<StaticWebAppConfig> staticWebAppConfig) {
        this.staticWebAppConfig = staticWebAppConfig;
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
