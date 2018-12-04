package com.devadmin.vicky.service.dto.jira;

public class UserDto {
  private String self;
  private String name;
  private String key;
  private String accountId;
  private String emailAddress;
  private AvatarUrlDto avatarUrl;
  private String displayName;
  private boolean active;
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
