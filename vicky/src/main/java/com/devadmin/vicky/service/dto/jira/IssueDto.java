package com.devadmin.vicky.service.dto.jira;

public class IssueDto {
  private String id;
  private String self;
  private String key;
  private FieldDto fields;

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

  public FieldDto getFields() {
    return fields;
  }

  public void setFields(FieldDto fields) {
    this.fields = fields;
  }
}
