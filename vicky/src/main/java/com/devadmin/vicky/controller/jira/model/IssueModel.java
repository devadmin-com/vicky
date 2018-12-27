package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.Task;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the object which contains the information about jira issue
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueModel implements Task {

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

  @Override
  public String getDescription() {
    return fields.getStatus().getDescription();
  }

  @Override
  public String getPriority() {
    return this.fields.getPriority().getName();
  }

  @Override
  public String getType() {
    //@todo implement me
    return null;
  }

  @Override
  public String getProject() {
    return getFields().getProject().getName();
  }

  @Override
  public List<String> getLabels() {
    ArrayList<String> labels = new ArrayList<>();
    if (getFields().getLabels() == null) {
      return labels;
    } else {
      return Arrays.asList(getFields().getLabels());
    }
  }

  /**
   * @return true if task is resolved
   */
  @Override
  public Boolean isResolved() {
    return "Resolved 解決済".equals(this.fields.getStatus().getName());
  }
}
