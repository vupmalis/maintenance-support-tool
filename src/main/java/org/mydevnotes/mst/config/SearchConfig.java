
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
    "searchOptions"
})
@Generated("jsonschema2pojo")
public class SearchConfig {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchOptions")
    @NotNull
    private List<@Valid SearchOption> searchOptions;
    @JsonIgnore
    private Map<String, @Valid Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchOptions")
    public List<SearchOption> getSearchOptions() {
        return searchOptions;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("searchOptions")
    public void setSearchOptions(List<SearchOption> searchOptions) {
        this.searchOptions = searchOptions;
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
