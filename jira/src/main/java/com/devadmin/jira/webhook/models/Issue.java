package com.devadmin.jira.webhook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

  @JsonProperty("id")
  private String id;
  @JsonProperty("self")
  private String self;
  @JsonProperty("key")
  private String key;
  @JsonProperty("fields")
  private Field fields;

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

  public Field getFields() {
    return fields;
  }

  public void setFields(Field fields) {
    this.fields = fields;
  }
}
