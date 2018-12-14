package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.Changelog;
import com.devadmin.vicky.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeLogModel implements Changelog {
  @JsonProperty("id")
  private String id;
  @JsonProperty("items")
  private List<Item> items;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}
