package com.devadmin.vicky.service.dto.jira;

public class ProjectDto {
  private String self;
  private String id;
  private String key;
  private String name;
  private String projectTypeKey;
  private AvatarUrlDto avatarUrl;

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

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProjectTypeKey() {
    return projectTypeKey;
  }

  public void setProjectTypeKey(String projectTypeKey) {
    this.projectTypeKey = projectTypeKey;
  }

  public AvatarUrlDto getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(AvatarUrlDto avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
