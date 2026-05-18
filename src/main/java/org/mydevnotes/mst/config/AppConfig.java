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
"sources",
"searchSection"
})
@Generated("jsonschema2pojo")
public class AppConfig {

/**
*
* (Required)
*
*/
@JsonProperty("sources")
@NotNull
private List<@Valid Source> sources;
/**
*
* (Required)
*
*/
@JsonProperty("searchSection")
@Valid
@NotNull
private SearchSection searchSection;
@JsonIgnore
private Map<String, @Valid Object> additionalProperties = new LinkedHashMap<String, Object>();

/**
*
* (Required)
*
*/
@JsonProperty("sources")
public List<Source> getSources() {
return sources;
}

/**
*
* (Required)
*
*/
@JsonProperty("sources")
public void setSources(List<Source> sources) {
this.sources = sources;
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

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
