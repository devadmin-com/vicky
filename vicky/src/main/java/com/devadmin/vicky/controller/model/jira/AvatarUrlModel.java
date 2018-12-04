package com.devadmin.vicky.controller.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvatarUrlModel {
  @JsonProperty("48x48")
  private String avatarUrl48x48;
  @JsonProperty("32x32")
  private String avatarUrl32x32;
  @JsonProperty("24x24")
  private String avatarUrl24x24;
  @JsonProperty("16x16")
  private String avatarUrl16x16;

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

  public String getAvatarUrl16x16() {
    return avatarUrl16x16;
  }

  public void setAvatarUrl16x16(String avatarUrl16x16) {
    this.avatarUrl16x16 = avatarUrl16x16;
  }
}
