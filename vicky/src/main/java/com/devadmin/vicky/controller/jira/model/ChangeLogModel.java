package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.Changelog;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeLogModel implements Changelog {

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
        List<ChangeLogItem> changeLogItems = new ArrayList<>(items);
        return changeLogItems;
    }

    public void setItems(List<JiraChangeLogItemModel> items) {
        this.items = items;
    }

}
