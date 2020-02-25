package com.devadmin.vicky.model.jira.changelog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information about what changes occurred in the task.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeLogModel implements ChangeLog {

    @JsonProperty("id")
    private String id;

    @JsonProperty("items")
    private List<JiraChangeLogItemModel> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ChangeLogItem> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<JiraChangeLogItemModel> items) {
        this.items = items;
    }
}
