package org.mydevnotes.mst.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vupma
 */
public class BusinessEntity {

    private String id;
    private String name;
    private String type;
    private Map<String, Object> attributes;
    private final Map<String, Map<String, Object>> details = new HashMap<>();

    public Map<String, Map<String, Object>> getDetails() {
        return details;
    }

    private List<BusinessEntity> children;

    private String iconName = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<BusinessEntity> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessEntity> children) {
        this.children = children;
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

}
