package com.devadmin.vicky.controller.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the object which contains the information about jira issue
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueModel {
  @JsonProperty("id")
  private String id;
  @JsonProperty("self")
  private String self;
  @JsonProperty("key")
  private String key;
  @JsonProperty("fields")
  private FieldModel fields;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public FieldModel getFields() {
    return fields;
  }

  public void setFields(FieldModel fields) {
    this.fields = fields;
  }
}
