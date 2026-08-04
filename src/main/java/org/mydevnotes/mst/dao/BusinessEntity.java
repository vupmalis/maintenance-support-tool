package org.mydevnotes.mst.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
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
    private Timestamp startDate;
    private Timestamp endDate;

    @JsonIgnore
    private boolean exportToTimeLineEnabled;

    @JsonIgnore
    private Map<String, Object> attributes;

    @JsonIgnore
    private final Map<String, Map<String, Object>> details = new HashMap<>();

    private final Map<String, List<BusinessEntity>> childrens = new HashMap<>();

    @JsonIgnore
    private String iconName = "";    

    public Map<String, Map<String, Object>> getDetails() {
        return details;
    }



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

    public void loadFieldValuesFromAttributes(String defaultType) {
        if (this.attributes != null) {
            this.setId(this.attributes.containsKey("id") ? String.valueOf(this.attributes.get("id")) : null);
            this.setType(this.attributes.containsKey("entity_type") ? String.valueOf(this.attributes.get("entity_type")) : defaultType);
            this.setName(this.attributes.containsKey("name") ? String.valueOf(this.attributes.get("name")) : "untitled");
            this.setIconName(this.attributes.containsKey("h_icon") ? String.valueOf(this.attributes.get("h_icon")) : "");
            this.setExportToTimeLineEnabled(this.attributes.containsKey("export_to_timeline_enabled") ? "Y".equals(String.valueOf(this.attributes.get("export_to_timeline_enabled"))) : false);
            this.setStartDate(this.attributes.containsKey("start_date") ? (Timestamp) this.attributes.get("start_date") : null);
            this.setEndDate(this.attributes.containsKey("end_date") ? (Timestamp) this.attributes.get("end_date") : null);
        }
    }

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Map<String, List<BusinessEntity>> getChildrens() {
        return childrens;
    }

    public boolean isExportToTimeLineEnabled() {
        return exportToTimeLineEnabled;
    }

    public void setExportToTimeLineEnabled(boolean exportToTimeLineEnabled) {
        this.exportToTimeLineEnabled = exportToTimeLineEnabled;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

}
