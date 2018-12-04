package com.devadmin.vicky.service.dto.jira;

public class IssueTypeDto {
  private String self;
  private String id;
  private String description;
  private String iconUrl;
  private String name;
  private Boolean subTask;
  private String avatarId;

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Boolean getSubTask() {
    return subTask;
  }

  public void setSubTask(Boolean subTask) {
    this.subTask = subTask;
  }

  public String getAvatarId() {
    return avatarId;
  }

  public void setAvatarId(String avatarId) {
    this.avatarId = avatarId;
  }
}
