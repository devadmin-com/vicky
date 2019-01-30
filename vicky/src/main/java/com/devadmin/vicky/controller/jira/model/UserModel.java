package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the object which contains the information about user (issue creator)
 * setting ignoreUnknown to true, to ignore fields which coming from json but don't have them in class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {

  @JsonProperty("self")
  private String self;
  @JsonProperty("name")
  private String name;
  @JsonProperty("key")
  private String key;
  @JsonProperty("accountId")
  private String accountId;
  @JsonProperty("emailAddress")
  private String emailAddress;
  @JsonProperty("avatarUrls")
  private AvatarUrlModel avatarUrl;
  @JsonProperty("displayName")
  private String displayName;
  @JsonProperty("active")
  private boolean active;
  @JsonProperty("timeZone")
  private String timeZone;

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public AvatarUrlModel getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(AvatarUrlModel avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }
}
