package com.devadmin.vicky.controller.jira.model;

import com.devadmin.vicky.AssignChangeLogItem;
import com.devadmin.vicky.ChangeLogItem;
import com.devadmin.vicky.ChangeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see ChangeLogItem
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraChangeLogItemModel implements AssignChangeLogItem {

  @JsonProperty("field")
  private String field;
  @JsonProperty("fieldtype")
  private String fieldType;
  @JsonProperty("fieldId")
  private String fieldId;
  @JsonProperty("from")
  private String from;
  @JsonProperty("fromString")
  private String fromString;
  @JsonProperty("to")
  private String to;
  @JsonProperty("toString")
  private String toString;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getFieldType() {
    return fieldType;
  }

  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }

  public String getFieldId() {
    return fieldId;
  }

  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getFromString() {
    return fromString;
  }

  public void setFromString(String fromString) {
    this.fromString = fromString;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getToString() {
    return toString;
  }

  public void setToString(String toString) {
    this.toString = toString;
  }

  /**
   * @return username of assignee
   */
  @Override
  public String getAssignedTo() {
    return "assignee".equals(field) ? this.to : "";
  }

  /**
   * @return what kind of change happened (ex: priority, reporter, status, etc.)
   */
  @Override
  public ChangeType getChangeType() {
    return "assignee".equals(field) ? ChangeType.ASSIGN : ChangeType.DEFAULT;
  }
}
