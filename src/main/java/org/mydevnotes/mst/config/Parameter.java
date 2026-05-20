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
"name",
"title",
"type",
"parameterId"
})
@Generated("jsonschema2pojo")
public class Parameter {

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
@JsonProperty("title")
@NotNull
private String title;
/**
*
* (Required)
*
*/
@JsonProperty("type")
@NotNull
private String type;
/**
*
* (Required)
*
*/
@JsonProperty("parameterId")
@NotNull
private int parameterId;
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
@JsonProperty("title")
public String getTitle() {
return title;
}

/**
*
* (Required)
*
*/
@JsonProperty("title")
public void setTitle(String title) {
this.title = title;
}

/**
*
* (Required)
*
*/
@JsonProperty("type")
public String getType() {
return type;
}

/**
*
* (Required)
*
*/
@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

/**
*
* (Required)
*
*/
@JsonProperty("parameterId")
public int getParameterId() {
return parameterId;
}

/**
*
* (Required)
*
*/
@JsonProperty("parameterId")
public void setParameterId(int parameterId) {
this.parameterId = parameterId;
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