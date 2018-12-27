package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the object which contains the information related task project
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectModel {

  @JsonProperty("self")
  private String self;
  @JsonProperty("id")
  private String id;
  @JsonProperty("key")
  private String key;
  @JsonProperty("name")
  private String name;
  @JsonProperty("projectTypeKey")
  private String projectTypeKey;
  @JsonProperty("avatarUrls")
  private AvatarUrlModel avatarUrl;

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

  public AvatarUrlModel getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(AvatarUrlModel avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
