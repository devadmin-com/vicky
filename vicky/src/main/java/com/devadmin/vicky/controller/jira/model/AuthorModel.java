package com.devadmin.vicky.controller.jira.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.TimeZone;


/**
 * This is the object which contains the information related to Author (issue author, comment author, etc.)
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorModel {
  @JsonProperty("self")
  private String self;

  @JsonProperty("name")
  private String name;

  @JsonProperty("key")
  private String key;

  @JsonProperty("accountId")
  private String accountId;

  @JsonProperty("avatarUrls")
  private AvatarUrlModel avatarUrlModel;

  @JsonProperty("displayName")
  private String displayName;

  @JsonProperty("active")
  private Boolean active;

  @JsonProperty("timeZone")
  private TimeZone timeZone;

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

  public AvatarUrlModel getAvatarUrlModel() {
    return avatarUrlModel;
  }

  public void setAvatarUrlModel(AvatarUrlModel avatarUrlModel) {
    this.avatarUrlModel = avatarUrlModel;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public TimeZone getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }
}
