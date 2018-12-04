package com.devadmin.vicky.service.dto.jira;

import java.util.TimeZone;

public class AuthorDto {
  private String self;
  private String name;
  private String key;
  private String accountId;
  private AvatarUrlDto avatarUrl;
  private String displayName;
  private Boolean active;
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

  public AvatarUrlDto getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(AvatarUrlDto avatarUrl) {
    this.avatarUrl = avatarUrl;
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
