
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
    "searchConfig",
    "childEntities",
    "entityAttributes"
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
    @JsonProperty("searchConfig")
    @Valid
    @NotNull
    private SearchConfig searchConfig;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("childEntities")
    @NotNull
    private List<@Valid ChildEntity> childEntities;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("entityAttributes")
    @NotNull
    private List<@Valid EntityAttribute> entityAttributes;
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
    @JsonProperty("childEntities")
    public List<ChildEntity> getChildEntities() {
        return childEntities;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("childEntities")
    public void setChildEntities(List<ChildEntity> childEntities) {
        this.childEntities = childEntities;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("entityAttributes")
    public List<EntityAttribute> getEntityAttributes() {
        return entityAttributes;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("entityAttributes")
    public void setEntityAttributes(List<EntityAttribute> entityAttributes) {
        this.entityAttributes = entityAttributes;
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
