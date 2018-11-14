package com.devadmin.jira.webhook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
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
  private AvatarUrls avatarUrls;

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

  public AvatarUrls getAvatarUrls() {
    return avatarUrls;
  }

  public void setAvatarUrls(AvatarUrls avatarUrls) {
    this.avatarUrls = avatarUrls;
  }
}
