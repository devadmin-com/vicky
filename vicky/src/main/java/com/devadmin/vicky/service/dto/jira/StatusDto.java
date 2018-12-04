package com.devadmin.vicky.service.dto.jira;

public class StatusDto {
  private String self;
  private String description;
  private String iconUrl;
  private String name;
  private String id;
  private StatusCategoryDto statusCategory;

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public StatusCategoryDto getStatusCategory() {
    return statusCategory;
  }

  public void setStatusCategory(StatusCategoryDto statusCategory) {
    this.statusCategory = statusCategory;
  }
}
