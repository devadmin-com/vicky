package com.devadmin.vicky.service.dto.jira;

import java.util.List;

public class ChangeLogDto {
  private String id;
  private List<ItemDto> items;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<ItemDto> getItems() {
    return items;
  }

  public void setItems(List<ItemDto> items) {
    this.items = items;
  }
}
