package com.devadmin.jira.webhook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvatarUrls {
  @JsonProperty("48x48")
  private String avatarUrl48x48;

  @JsonProperty("32x32")
  private String avatarUrl32x32;

  @JsonProperty("24x24")
  private String avatarUrl24x24;

  @JsonProperty("12x12")
  private String avatarUrl12x12;

  public String getAvatarUrl48x48() {
    return avatarUrl48x48;
  }

  public void setAvatarUrl48x48(String avatarUrl48x48) {
    this.avatarUrl48x48 = avatarUrl48x48;
  }

  public String getAvatarUrl32x32() {
    return avatarUrl32x32;
  }

  public void setAvatarUrl32x32(String avatarUrl32x32) {
    this.avatarUrl32x32 = avatarUrl32x32;
  }

  public String getAvatarUrl24x24() {
    return avatarUrl24x24;
  }

  public void setAvatarUrl24x24(String avatarUrl24x24) {
    this.avatarUrl24x24 = avatarUrl24x24;
  }

  public String getAvatarUrl12x12() {
    return avatarUrl12x12;
  }

  public void setAvatarUrl12x12(String avatarUrl12x12) {
    this.avatarUrl12x12 = avatarUrl12x12;
  }
}
