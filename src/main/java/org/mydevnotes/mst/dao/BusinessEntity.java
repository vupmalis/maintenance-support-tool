package org.mydevnotes.mst.dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author vupma
 */
public class BusinessEntity {    
    
    private Long id;
    private String name;
    private String type;
    private Map<String, Object> attributes;
    private List<BusinessEntity> children;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
